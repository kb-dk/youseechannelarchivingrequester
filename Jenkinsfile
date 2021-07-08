#!/usr/bin/env groovy


openshift.withCluster() { // Use "default" cluster or fallback to OpenShift cluster detection


    echo "Hello from the project running Jenkins: ${openshift.project()}"

    //Create template with maven settings.xml, so we have credentials for nexus
    podTemplate(
            inheritFrom: 'maven',
            cloud: 'openshift', //cloud must be openshift
            label: 'agent-with-settings.xml',
            name: 'agent-with-settings.xml',
            volumes: [ //mount the settings.xml
                       secretVolume(mountPath: '/etc/m2', secretName: 'maven-settings')
            ]) {

        //Stages outside a node declaration runs on the jenkins host

        String projectName = encodeName("${JOB_NAME}")
        echo "name=${projectName}"

        try {
            //GO to a node with maven and settings.xml
            node('agent-with-settings.xml') {
                //Do not use concurrent builds
                properties([disableConcurrentBuilds()])

                def mvnCmd = "mvn -s /etc/m2/settings.xml --batch-mode"

                stage('checkout') {
                    checkout scm
                }

                stage('Mvn clean package') {
                    sh "${mvnCmd} -PallTests clean package"
                }

                stage('Analyze build results') {            
                    recordIssues aggregatingResults: true, 
                        tools: [java(), 
                                javaDoc(),
                                mavenConsole(),
                                taskScanner(highTags:'FIXME', normalTags:'TODO', includePattern: '**/*.java', excludePattern: 'target/**/*')]                
                }

                stage('Create test project') {
                    recreateProject(projectName)

                    openshift.withProject(projectName) {

                        stage("Create build and deploy application") { 
                            openshift.newBuild("--strategy source", "--binary", "-i kb-infra/kb-s2i-tomcat90", "--name channel-archiving-requester")
                            openshift.startBuild("channel-archiving-requester", "--from-dir=.", "--follow")
                            openshift.newApp("channel-archiving-requester:latest")
                            openshift.create("route", "edge", "--service=channel-archiving-requester")
                        }
                    }
                }

                stage('Push to Nexus (if Master)') {
                    sh 'env'
                    echo "Branch name ${env.BRANCH_NAME}"
                    if (env.BRANCH_NAME == 'master') {
	                sh "${mvnCmd} clean deploy -DskipTests=true"
                    } else {
	                echo "Branch ${env.BRANCH_NAME} is not master, so no mvn deploy"
                    }
                }

// Promotion is currently disabled as there's no active setup to promote to. 
//                stage('Promote image') {
//                    if (env.BRANCH_NAME == 'master') {
//                        configFileProvider([configFile(fileId: "imagePromoter", variable: 'promoter')]) {
//                            def promoter = load promoter
//                            promoter.promoteImage("channel-archiving-requester", "${projectName}",  "yousee", "latest")
//                        }
//                    } else {
//                        echo "Branch ${env.BRANCH_NAME} is not master, so no mvn deploy"
//                    }
//                }


                stage('Cleanup') {
                    echo "Cleaning up"
                    openshift.selector("project/${projectName}").delete()
                }
            }
        } catch (e) {
            currentBuild.result = 'FAILURE'
            throw e
        } finally {
            configFileProvider([configFile(fileId: "notifier", variable: 'notifier')]) {  
                def notifier = load notifier             
                notifier.notifyInCaseOfFailureOrImprovement(true, "#playground")
            } 
        }
    }
}


private void recreateProject(String projectName) {
    echo "Delete the project ${projectName}, ignore errors if the project does not exist"
    try {
        openshift.selector("project/${projectName}").delete()

        openshift.selector("project/${projectName}").watch {
            echo "Waiting for the project ${projectName} to be deleted"
            return it.count() == 0
        }

    } catch (e) {

    }
//
//    //Wait for the project to be gone
//    sh "until ! oc get project ${projectName}; do date;sleep 2; done; exit 0"

    echo "Create the project ${projectName}"
    openshift.newProject(projectName)
}

/**
 * Encode the jobname as a valid openshift project name
 * @param jobName the name of the job
 * @return the jobname as a valid openshift project name
 */
private static String encodeName(groovy.lang.GString jobName) {
    def jobTokens = jobName.tokenize("/")
    def org = jobTokens[0]
    if(org.contains('-')) {
        org = org.tokenize("-").collect{it.take(1)}.join("")
    } else {
        org = org.take(3)
    }

    // Repository have a very long name, lets shorten it further
    def repo = jobTokens[1]
    if(repo.contains('-')) {
        repo = repo.tokenize("-").collect{it.take(1)}.join("")
    } else if(repo.contains('_')) {
        repo = repo.tokenize("_").collect{it.take(1)}.join("")
    } else {
        repo = repo.take(3)
    }


    def name = ([org, repo] + jobTokens.drop(2)).join("-")
            .replaceAll("\\s", "-")
            .replaceAll("_", "-")
            .replace("/", '-')
            .replaceAll("^openshift-", "")
            .toLowerCase()
    return name
}

