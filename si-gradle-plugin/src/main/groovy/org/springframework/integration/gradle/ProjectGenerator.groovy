/*
* Copyright 2002-2012 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springframework.integration.gradle

import groovy.lang.Closure;

import org.gradle.api.DefaultTask
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction
import org.springframework.integration.gradle.support.TemplateType;

/**
 *
 *  @author Gunnar Hillert
 *  @since 1.0
 *
 */
class ProjectGenerator {
	
	/**
     * Location for to copy the Template Jar to.
     */
    String temporaryTemplateDir = File.separator + "template-jar"

	/**
	 * 
	 */
    def createIntegrationProject(Project project, TemplateType template) {

		/**
		* Classpath Resource inside the Plugin Jar.
		*/
	    String sourceTemplateResourceLocation = "/templates/" + template.templateName + ".jar"
		
		/**
		* Temporary Template Location
		*/
	    String temporaryTemplateLocation    = File.separator + temporaryTemplateDir + File.separator + template.templateName
		String temporaryTemplateLocationJar = temporaryTemplateLocation + ".jar"
	   
        println(SpringIntegrationPluginUtils.logo)

        String buidDirPath = project.buildDir.absolutePath
        
        //Collect some Information from the Users
        ProjectInformation pi = new ProjectInformation().collect()

        //Make sure temporary template directory exists
        new File(buidDirPath + temporaryTemplateDir).mkdirs()

        //Extract the template for the class path resource
        SpringIntegrationPluginUtils.retrieveClassResource(sourceTemplateResourceLocation,
                                                           buidDirPath + temporaryTemplateLocationJar)

        // create an antbuilder
        def ant = new AntBuilder()

        //Let's un-jar the Template
        
        println("Un-jaring template from '" + buidDirPath + temporaryTemplateLocationJar + "' to '" + buidDirPath + temporaryTemplateDir + "'.")
        
        ant.unjar(src:  buidDirPath + temporaryTemplateLocationJar,
                  dest: buidDirPath + temporaryTemplateLocation,
                  overwrite:"true" )

        project.configure(project) {

            String destPackage = pi.basePackageName.replace('.' as char, File.separatorChar);

			println("[Reconfigure '/src/main/java']")
			
            //We need to adjust the package structure based on the provided base package name
            String baseSourcePackageFolder = buidDirPath + temporaryTemplateLocation + "/src/main/java" + "/stsorg"
            String sourcePackageFolder     = baseSourcePackageFolder + "/stsspringframework/stsintegration"
            String destPackageFolder       = buidDirPath + temporaryTemplateLocation + "/src/main/java/" + destPackage

			reconfigureProject(project, pi, baseSourcePackageFolder, sourcePackageFolder, destPackageFolder, temporaryTemplateLocation)

			println("[Reconfigure  /src/test/java]")
			
			String baseTestPackageFolder = buidDirPath + temporaryTemplateLocation + "/src/test/java" + "/stsorg"
			String testPackageFolder     = baseTestPackageFolder + "/stsspringframework/stsintegration"
			String destTestPackageFolder = buidDirPath + temporaryTemplateLocation + "/src/test/java/" + destPackage
			
			reconfigureProject(project, pi, baseTestPackageFolder, testPackageFolder, destTestPackageFolder, temporaryTemplateLocation)
						
			String deleteBuildDir = "true"

			if (project.hasProperty('deleteBuildDir')) {
				deleteBuildDir = project.deleteBuildDir
			} 
			
			if ("true".equalsIgnoreCase(deleteBuildDir)) {
			
				println("Deleting build directory " + buidDirPath + "..."
					   + SpringIntegrationPluginUtils.deleteNonEmptyDirectory(project.buildDir))

			}
			
			
        }

    }

	def reconfigureProject(Project project, ProjectInformation pi,
		                  String baseSourcePackageFolder, String sourcePackageFolder, String destPackageFolder, String temporaryTemplateLocation) {
		
						  
						  
		println("baseSourcePackageFolder: " + baseSourcePackageFolder + ", sourcePackageFolder: " + sourcePackageFolder + ", destPackageFolder: " + destPackageFolder + ", temporaryTemplateLocation: " + temporaryTemplateLocation)
		
		project.configure(project) {
					copy{
						from sourcePackageFolder
						into destPackageFolder
						include '**/*'
					}
		
					println("Deleting baseSourcePackageFolder " + baseSourcePackageFolder + "..." + delete(baseSourcePackageFolder))
		
					println("Copying filtered template from '" + project.buildDir.absolutePath + temporaryTemplateLocation + "' to '" + project.rootDir +"'.")
					
					copy{
							from project.buildDir.absolutePath + temporaryTemplateLocation
							into project.rootDir
							include '**/*'
							filter { String line ->
								line.replace("mavenGroupId", pi.projectGroup)
								.replace("mavenVersion", pi.projectVersion)
								.replace("si-template-project-standalone-simple", pi.projectName)
								.replace("org.springframework.integration.sts:",  pi.basePackageName)
								.replace("stsorg.stsspringframework.stsintegration", pi.basePackageName)
							}
					}
		}
	}
	
}
