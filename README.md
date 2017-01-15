## To run this project:

1. clone the project & open it in your IDE;
3. configure database connection properties in *RESTserver/src/main/resources/application.properties*:
	db.driver=com.mysql.cj.jdbc.Driver
	db.url=jdbc:mysql://<your_db_address>:3306/<your_db_name>
	db.username=<your_db_username>
	db.password=<your_db_password>
4. build both *REST-server* and *MVC-application* maven modules using `mvn clean package`;
5. run both the modules on your web-server (e.g. Tomcat)
