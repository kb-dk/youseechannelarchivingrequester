FROM maven:3.9-eclipse-temurin-21 AS builder

# Set working directory in the container
WORKDIR /app

RUN mkdir -p /root/.m2/repository

# Copy maven settings (ignored by git), containing repository credentials
COPY /settings/settings.xml /root/.m2
COPY . .
COPY files-for-docker/web.xml yousee-channel-archiving-requester-web/src/main/webapp/WEB-INF/web.xml
# Build the application using Maven
RUN mvn package

FROM tomcat:9-jdk11-temurin-noble

# Update installed packages
RUN apt -y update && apt -y upgrade

# Copy our built WAR into Tomcat
COPY --from=builder /app/yousee-channel-archiving-requester-web/target/*.war ${CATALINA_HOME}/webapps/

RUN mkdir -p ${CATALINA_HOME}/conf/channelarchivingrequester/
COPY files-for-docker/hibernate.cfg.xml ${CATALINA_HOME}/conf/channelarchivingrequester/hibernate.cfg.xml
COPY files-for-docker/logback.xml ${CATALINA_HOME}/conf/channelarchivingrequester/logback.xml
