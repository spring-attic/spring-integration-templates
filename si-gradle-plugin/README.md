Spring Integration Gradle Plugin
================================

The Spring Integration Gradle Plugin provides templates to jumpstart Spring Integration projects. The following templates are available:

* Standalone Simple - Creates a very basic Spring Integration project (command line based)
* Standalone - Creates a command lined based project that uses file pollers
* War - Creates a basic web-based (War) Spring Integration project.

# Getting Started

## Setup the Plugin

1. Create (if not exists) **init.gradle** (usually under *~/.gradle*)
2. Add the following lines

    gradle.beforeProject { prj ->
        prj.apply from: 'https://raw.github.com/SpringSource/spring-integration-templates/master/si-gradle-plugin/distribution/spring-integration-apply.groovy'
    }

This should make the plugin available globally in your system. You should get a list of task when you execute:

    $ gradle tasks

    ...
	Spring Integration tasks
	------------------------
	create-project-simple - Creates a very basic Spring Integration project (command line based)
	create-project-standalone - Creates a command lined based project that uses file pollers
	create-project-war - Creates a basic web-based (War) Spring Integration project.
    ...

## Standalone Projects

### Create

In order to create stand-alone template projects execute either:

    $ gradle create-project-simple

or you can execute:

    $ gradle create-project

### Run

    $ gradle run

## War Project

### Create

In order to create a WAR project execute:

    $ gradle create-project-war

### Run

    $ gradle jettyRun

In your browser go to: http://localhost:8080/your-project-name

# Additional Plugin Parameters

## deleteBuildDir

During Plugin execution an internal template file is extracted to the project's build directory and the files and directories are reconfigured so that they match the package structure as defined by the user input. By default the build is deleted at the end of the plugin execution. However, you can prevent that but setting:

    -PdeleteBuildDir=false

# For developers

This section is for template developers. For just using the Spring Integration Gradle Plugin, this section may not be that important to you. The source code for the plugin is located at:

* https://github.com/SpringSource/spring-integration-templates

### Check out repository from Git

    $ git clone git://github.com/ghillert/spring-integration-gradle-plugin.git

### Compile and Install Plugin to local Maven Repo

    $ gradle clean install

### Using the locally installed plugin

    $ mkdir myProject
    $ cd myProject

Create a file **build.gradle** with the following initial configuration:

    buildscript {
        repositories {
            mavenLocal()
        }
        dependencies {
            classpath group: 'org.springframework.integration.gradle', name: 'si-gradle-plugin', version: '1.0.0.M1.BUILD-SNAPSHOT'
        }
    }

    apply plugin: 'spring-integration'

Now the gradle plugin should show up when you execute:

    $ gradle tasks

    ...

    Spring Integration tasks
    ------------------------
    create-project-simple - Creates a very basic Spring Integration project (command line based)
    ...

### Making the Gradle plugin available globally

You can also add the plugin to **init.gradle** (plugin will be available globally)

Create (if not exists) **init.gradle** (usually under *~/.gradle*)

	gradle.beforeProject { prj ->
	   prj.apply from: 'file:///Users/ghillert/.gradle/spring-integration-apply.groovy'
	}

Create **spring-integration-apply.groovy**

    buildscript {
        repositories {
            mavenLocal()
        }

        dependencies {
            classpath group: 'org.springframework.integration.gradle', name: 'si-gradle-plugin', version: '1.0.0.M1.BUILD-SNAPSHOT'
        }
    }

    if (!project.plugins.findPlugin(org.springframework.integration.gradle.SpringIntegrationPlugin)) {
        project.apply(plugin: org.springframework.integration.gradle.SpringIntegrationPlugin)
    }

### Alternative

Create (if not exists) **init.gradle** (usually under *~/.gradle*) with the following contents:

	initscript { 
	       repositories {
	            mavenLocal()
	        }

	        dependencies {
	            classpath group: 'org.springframework.integration.gradle', name: 'si-gradle-plugin', version: '1.0.0.M1.BUILD-SNAPSHOT'
	        }
	}

	gradle.beforeProject { prj ->
	    prj.apply(plugin: org.springframework.integration.gradle.SpringIntegrationPlugin)
	}
	
## Building the Project

Jar-up the Template projects files and place them under **src/main/resources/templates**

    $ gradle zipTemplates

Build and install the Plugin:

    $ gradle clean build install



	





