Changelog
=========

Old Changes:  https://sbprojects.statsbiblioteket.dk/display/INFRA/Yousee+Channel+Archiving+Requester+Releases


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
