**Starting up the docker container with a tomcat server**
````bash
docker build -f car-web.Dockerfile -t kb/car-web ./
docker compose up
````
**Information about the application**
* The docker commands written above will start the server and host it on port 8080.
* Running them also starts a PostgreSQL docker image and seeds it with test data from the testdata.sql file in the folder docker-testdata (This is needed in order to run the PostgreSQL docker image).
* In order to run mvn package in the Dockerfile there need to exist a folder called "settings" with a file called settings.xml that contains the maven settings necessary to get packages from nexus.
* Docker needs these three files - hibernate.cfg.xml, logback.xml and web.xml in order to run. These are placed in the files-for-docker folder.
