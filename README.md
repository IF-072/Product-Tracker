# Product Tracker

[![Build Status](https://api.travis-ci.org/IF-072/Product-Tracker.svg?branch=develop)](https://travis-ci.org/IF-072/Product-Tracker)
***

## To run this project:

1. clone the project & open it in your IDE;
3. configure database connection properties in *RESTserver/src/main/resources/database.properties*:
	3. db.driver=com.mysql.cj.jdbc.Driver
	3. db.url=jdbc:mysql://your_db_address:3306/your_db_name
	3. db.username=your_db_username
	3. db.password=your_db_password
4. build both *REST-server* and *MVC-application* maven modules using `mvn clean package`;
5. run both the modules on your web-server (e.g. Tomcat)
