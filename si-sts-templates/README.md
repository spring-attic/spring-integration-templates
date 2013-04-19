Spring Integration STS Templates
================================

This project provides various project templates for SpringSource Tool Suite™ (STS).

# Requirements

Starting with Spring Integration STS Template 1.0.0.M4, we rely on the template support provided by STS **3.0.0.M3** and later. The simplifications allowed us to significantly simplify the development and build process for the templates.

# How to build the project

In order to build the entire project run:

    $ mvn clean package

This will result in *5 artifacts* being created under **target/out**:

* descriptor.xml
* si-template-adapter-1.0.0.M5.zip
* si-template-standalone-1.0.0.M5.zip
* si-template-standalone-simple-1.0.0.M5.zip
* si-template-war-1.0.0.M5.zip

In order to deploy to STS for **development** you have **2 options**:

## Use the Self-Hosted Templates feature in STS

This approach is  **Best for Development**.

1. In STS - Go to **Preferences...** --> **Spring** --> **Template Projects**
2. Mark the checkbox **Show self-hosted templates in New Template Wizard**
3. Import the individual template projects into STS, they will be immediately available as templates

## Tell STS to use the descriptor.xml file (Best for hosting your )

This approach is **Best for Deploying** your own temapltes.

1. In STS - Go to **Preferences...** --> **Spring** --> **Template Projects**
2. Press the **Add** Button
3. Enter the URL that points to the **descriptor.xml** (Currently only HTTP addresses are supported, local file support will be available soon - see: [STS-2666](https://issuetracker.springsource.com/browse/STS-2666))

## Important 1

A few files in the template project require absolute URLs. By default the pom is setup to use:

    <base.location>file://${project.parent.basedir}/target/out</base.location>

Therefore, if you prefer a custom base location (e.g.: http://www.mysite.com/si-templates/), then you must set the
base.location property such as:

    $ mvn package -Dbase.location=http://www.mysite.com/si-templates

## Important 2

Templates are locally cached under *your_workspace/.metadata/.sts/content/*. At times during development you may need to delete the templates there for various reasons... 

