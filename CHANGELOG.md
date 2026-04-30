Changelog
=========

Old Changes:  https://sbprojects.statsbiblioteket.dk/display/INFRA/Yousee+Channel+Archiving+Requester+Releases

Version 1.1.15 
=============
1. Upgraded pgjdbc driver version to 42.7.7
2. Upgraded Derby to version 10.17.1.0
3. Added new endpoints for filtering channels and requests list by to and from date
4. Added working Dockerfile and docker-compose
5. Made a README with instructions on how to run the application in Docker with docker compose
6. Bugfix - Fixed the date being set to 00:00 when adding a Request even when the input time was correct

Version 1.1.14
=============
Upgraded pgjdbc driver version to 42.7.3


Version 1.1.13
=============
Bugfix. Fixed issue that prevented updating dates on requests.


Version 1.1.12
=============
Bugfix update

 1. Fix parsing of dates when no time information is present (i.e. allow to change dates for requests)

Version 1.1.11
=============
Bugfix update

 1. Fix parsing of various time and date parameters in the program

Version 1.1.10
=============

Maintenance Update
 1. Complying with forbidden api
 2. Update junit, and corresponding unit tests.
 3. Most instances of java.util.Date replaced with java.time counterpart.
 4. Update other dependencies
 5. Update used java version (1.6 -> OpenJDK 11)
 6. Change webservice framework to Apache CXF
 7. Change logging backend to logback

Version 1.1.9
=============

 1. Changed the clock from 12-hour to 24-hour
 2. Replaced source plugin in pom
 
Version 1.1.8
=============

 1. Robustified session handling in DAO classes
 2. Upgraded dependency on digitv to bring in the same postgres driver and c3p0 classes we use there.
 3. Updated for consistency with newer hibernate API
