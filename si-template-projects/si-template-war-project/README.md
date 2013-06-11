Spring Integration - War Template
=================================

# Introduction

This template is meant for running [Spring Integration][] inside Servlet Containers such as [Apache Tomcat][] or [Jetty][]. This template by default uses the Spring Integration [Twitter Adapters][] to show some basic functionality.

Please keep in mind, that the provided Web UI is not necessary just to run *Spring Integration* applications. You can in fact run *Spring Integration* processes without any MVC integration. Thus, you can run your *Spring Integration* components and flows as mere backend processes within Servlet Containers.

# Running the Template

*Twitter Search* requires authentication. Therefore, you must update the following properties in **oauth.properties** located at  `src/main/resources`:

* twitter.oauth.consumerKey
* twitter.oauth.consumerSecret
* twitter.oauth.accessToken
* twitter.oauth.accessTokenSecret

Alternatively, you can also pass in those properties using system properties via the command-line, e.g.:

	mvn jetty:run -Dtwitter.oauth.consumerKey=12345 \
	-Dtwitter.oauth.consumerSecret=12345 \
	-Dtwitter.oauth.accessToken=12345 \
	-Dtwitter.oauth.accessTokenSecret=12345 \

The keys can be setup at [http://dev.twitter.com/](http://dev.twitter.com/).

## Command Line using Tomcat 7

	mvn tomcat7:run

## Command Line using Jetty

	mvn jetty:run

## Eclipse

If you are using [Spring Tool Suite][] (STS) and the project is imported as Eclipse project into your workspace, you can just execute 'Run on Server'.

## Limitations

Due to [an issue](https://issuetracker.springsource.com/browse/STS-3301) in the STS template mechanism, all referenced *PNG* files are hosted, externally.

--------------------------------------------------------------------------------

For help please take a look at the [Spring Integration documentation][]. The Template projects are hosted at: https://github.com/SpringSource/spring-integration-templates

[Apache Tomcat]: http://tomcat.apache.org/
[Jetty]: http://www.eclipse.org/jetty/
[Spring Tool Suite]: http://www.springsource.org/sts
[Spring Integration]: http://www.springintegration.org/
[Spring Integration documentation]: http://static.springsource.org/spring-integration/reference/html/
[Twitter Adapters]: http://static.springsource.org/spring-integration/reference/html/twitter.html

