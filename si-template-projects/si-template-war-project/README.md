Spring Integration - War Template
================================================================================

# Introduction

This template is meant for running Spring Integration inside a Servlet Container such as [Apache Tomcat][] or [Jetty][]. This template by default uses Twitter Integration to show some basic functionality.

Please keep in mind, that the provided Web UI is not necessary just to run Spring Integration application. You can in fact run Spring Integration processes without any MVC integration. Thus, you can run your Spring Integration components as mere backend processes.

# Running the Template

## Command Line using Tomcat 7

	mvn tomcat7:run

## Command Line using Jetty

	mvn jetty:run

## Eclipse

If you are using [Spring Tool Suite][] (STS) and the project is imported as Eclipse project into your workspace, you can just execute 'Run on Server'.

--------------------------------------------------------------------------------

For help please take a look at the Spring Integration documentation:

http://www.springsource.org/spring-integration

[Apache Tomcat]: http://tomcat.apache.org/
[Jetty]: http://www.eclipse.org/jetty/
[Spring Tool Suite]: http://www.springsource.org/sts

