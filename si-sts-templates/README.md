Spring Integration STS Plugin and Templates
===========================================

This project provides an plugin and various projects for SpringSource Tool Suite™. 

# How to build the project

In order to build the entire project run:

    $ mvn clean package

This will result in *3 artifacts* being created under **target/out**:

* descriptor.xml
* org.springframework.integration.sts.templates_1.0.0.M2.jar
* si-template-1.0.0.M2.zip

In order to deploy to STS you have 2 options:

1. drop the Eclipse plugin **org.springframework.integration.sts.templates_1.0.0.M2.jar**
   to your Eclipse STS **dropins/plugins folder**. Restart Eclipse STS.

2. Option 2 is good for developing/changing the Eclipse STS template.

Take the **si-template-1.0.0.M2.zip** jar file and unpack it in your Eclipse workspace in a directory called
**.metadata/.sts/content/${pom.artifactId}-${pom.version}**. Then add or edit the
template descriptor in **.metadata/.plugins/com.springsource.sts.content.core/content.xml**
so it has this form:

	<descriptor id="${pom.artifactId}" kind="template" local="true"
	      name="${pom.name}" size="0" version="${pom.version}">
	   <description>...</description>
	</descriptor>

Make sure there are no conflicting templates with the same name and a different version.

## Important

A few files in the template project require absolute URLs. By default the pom is setup to use:

    <base.location>file://${project.parent.basedir}/target/out</base.location>

Therefore, if you prefer a custom base location (e.g.: http://www.mysite.com/si-templates/), then you must set the
base.location property such as:

    $ mvn package -Dbase.location=http://www.mysite.com/si-templates

