Spring Integration - War Template
================================================================================

This template is meant for running Spring Integration inside of an Servlet
Container. This template by default uses Twitter Integration to show some basic
functionality.

Please keep in mind, that the provided Web UI is not necessary. You can in fact
run Spring Integration processes without any MVC integration. Thus, you can
run your Spring Integration components as mere backend processes.

You can run the application from:

* within STS (Right-click on the WAR project --> Run As --> Run on Server)
* the command line:
	- `mvn tomcat7:run`
	- Open your browser at `http://localhost:8080/`
* or alternatively:
	- `mvn package`
	- deploy the created war file (under `/target/*.war`) to a Servlet Container such as Tomcat

--------------------------------------------------------------------------------

For help please take a look at the Spring Integration documentation:

http://www.springsource.org/spring-integration

