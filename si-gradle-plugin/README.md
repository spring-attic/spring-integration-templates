Spring Integration Gradle Plugin
================================

The Spring Integration [Gradle](http://www.gradle.org) Plugin provides templates to jumpstart [Spring Integration](http://www.springintegration.org) projects. The following templates are available:

* **Standalone Simple** - Creates a very basic Spring Integration project (command line based)
* **Standalone** - Creates a command lined based project that uses file pollers
* **War** - Creates a basic web-based (War) Spring Integration project.

# Getting Started

## Setup the Plugin

1. Create (if not exists) **init.gradle** (usually under *~/.gradle*)
2. Add the following lines

    gradle.beforeProject { prj ->
        prj.apply from: 'https://raw.github.com/SpringSource/spring-integration-templates/master/si-gradle-plugin/distribution/spring-integration-apply.groovy'
    }

This should make the plugin available globally in your system. You should get a list of available Spring Integration specific tasks when you execute:

    $ gradle tasks

    ...
	Spring Integration tasks
	------------------------
	create-project-simple - Creates a very basic Spring Integration project (command line based)
	create-project-standalone - Creates a command lined based project that uses file pollers
	create-project-war - Creates a basic web-based (War) Spring Integration project.
    ...

## Creating Projects

In order to create projects using the Spring Integration Gradle Plugin, simply execute one of the available 3 template task e.g.:

    $ gradle create-project-simple

    $ gradle create-project-standalone

    $ gradle create-project-standalone

Once executed you will need to answer a few questions:

* Project Name
* Base Package
* Group (Defaults to Base Package)
* Version (Defaults to 1.0.BUILD-SNAPSHOT)

## Running Projects

You can either import the newly created project into an IDE and run the project from there, or you can run the projects from the command-line using Gradle. For the stand-alone templates, execute:

    $ gradle run

For the WAR template you can start an embedded servlet container by executing:

    $ gradle jettyRun

Once started, open your web browser and go to: 

* http://localhost:8080/your-project-name

# Additional Plugin Parameters

Currently one additional plugin parameter can be specified when creating templates.

## deleteBuildDir

This plugin should only be used for debugging purposes. Some background information on how templates are applied:

During Plugin execution an internal template file is extracted (from the PL) to the project's build directory and the files and directories are reconfigured so that they match the package structure as defined by the user input. By default the build directory is deleted at the end of the plugin execution. However, you can prevent that but setting:

    -PdeleteBuildDir=false

For example you can create a template and apply this parameter by executing:

    $ gradle create-project-simple -PdeleteBuildDir=false

# For Developers

This section is for template developers and should provide some information on how to build the plugin and how to use locally built copies. Consequently, for just using the Spring Integration Gradle Plugin, this section may not be that important to you. The source code for the plugin is located at:

* https://github.com/SpringSource/spring-integration-templates

## Clone the Spring Integration Templates Project

The Spring Integration Gradle Plugin is part of the Spring Integration Templates Project:

    $ git clone https://ghillert@github.com/SpringSource/spring-integration-templates.git

Traverse to the Gradle Plugin directory:

    $ cd spring-integration-templates/si-gradle-plugin

## Compile and Install the Plugin to your local Repo

    $ gradle clean install

### Jar Template Project Files

The Gradle Plugin uses compressed template project template files. These template projects are located under **spring-integration-templates/si-template-projects**. In order to Jar-up/Zip the Template projects files and place them under **src/main/resources/templates** execute:

    $ gradle zipTemplates

## Using the Locally Installed Plugin

Create a folder for a new Gradle project, e.g.:

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

This is a simple setup but you may want to setup the Plugin Globally in your system.

### Making the Gradle Plugin Globally Available 

You can also add the plugin to **init.gradle** and by doing so the plugin will be available globally.

Create (if not exists) **init.gradle** (usually under *~/.gradle*)

	gradle.beforeProject { prj ->
	   prj.apply from: 'file:///path/to/spring-integration-apply.groovy'
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

You can setup the plugin so that it does not use the **spring-integration-apply.groovy** script:

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
	

## Encountered Issues

During plugin development I ran into issues with downloading the plugin dependency correctly. After deleting **~/.gradle/caches** the plugin was downloaded correctly from the remote repository.


