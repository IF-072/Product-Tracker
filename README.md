# Product Tracker

[![Build Status](https://api.travis-ci.org/IF-072/Product-Tracker.svg?branch=develop)](https://travis-ci.org/IF-072/Product-Tracker)
***

## To run this project:

1. clone the project & open it in your IDE;
2. create new local MySQL database using *RESTserver/src/main/resources/sql/create_db.sql* file
3. fill in created DB with data using *RESTserver/src/main/resources/sql/fill_in_db.sql* file 
4. set up database connection properties by creating file *RESTserver/src/main/resources/database.properties* contains following four lines:
```
	db.driver=com.mysql.cj.jdbc.Driver
	db.url=jdbc:mysql://YOUR_DB_ADDRESS:3306/YOUR_DB_NAME
	db.username=YOUR_DB_USERNAME
	db.password=YOUR_DB_PASSWORD
```
5. build both *REST-server* and *MVC-application* maven modules using `mvn clean package`;	
6. deploy & run both the modules on your web-server (e.g. Tomcat)
	- MVCapplication should be deployed under `/` context 
	- RESTserver should be deployed under `/rest` context
