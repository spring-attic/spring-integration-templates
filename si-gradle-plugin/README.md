Spring Integration Gradle Plugin
================================

# Setup

## Quick Start

1. Create (if not exists) **init.gradle** (usually under *~/.gradle*)
2. Add the following lines

    gradle.beforeProject { prj ->
        prj.apply from: 'https://raw.github.com/ghillert/spring-integration-gradle-plugin/master/distribution/spring-integration-apply.groovy'
    }

## Local Install (compiling from Source) - Only for the brave

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
            classpath group: 'org.springframework.integration.gradle', name: 'si-gradle-plugin', version: '1.0.BUILD-SNAPSHOT'
        }
    }

    if (!project.plugins.findPlugin(org.springframework.integration.gradle.SpringIntegrationPlugin)) {
        project.apply(plugin: org.springframework.integration.gradle.SpringIntegrationPlugin)
    }




