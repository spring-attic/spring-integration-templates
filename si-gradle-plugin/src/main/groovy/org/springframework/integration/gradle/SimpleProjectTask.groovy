package org.springframework.integration.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 *
 *  @author Gunnar Hillert
 *  @since 1.0
 *
 */
class SimpleProjectTask extends DefaultTask {

    /**
     * Location for to copy the Template Jar to.
     */
    String temporaryTemplateDir = File.separator + "template-jar"

    /**
     * Name of the Template.
     */
    String templateName="si-template-standalone-simple-project"

    /**
     * Classpath Resource inside the Plugin Jar.
     */
    String sourceTemplateResourceLocation = "/templates/" + templateName + ".jar"

    /**
     * Temporary Template Location
     */
    String temporaryTemplateLocation    = File.separator + temporaryTemplateDir + File.separator + templateName

    String temporaryTemplateLocationJar = temporaryTemplateLocation + ".jar"

    /**
     * Template Task
     */
    @TaskAction
    def createIntegrationProject() {

        println(SpringIntegrationPlugin.logo)

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
                  dest: buidDirPath + temporaryTemplateDir,
                  overwrite:"true" )

        project.configure(project) {

            String destPackage = pi.basePackageName.replace('.' as char, File.separatorChar);
            
            //We need to adjust the package structure based on the provided base package name
            String baseSourcePackageFolder = buidDirPath + temporaryTemplateLocation + "/src/main/java/" + "/stsorg"
            String sourcePackageFolder = baseSourcePackageFolder + "/stsspringframework/stsintegration";
            String destPackageFolder   = buidDirPath + temporaryTemplateLocation + "/src/main/java/" + destPackage;

            println(destPackageFolder)

            copy{
                from sourcePackageFolder
                into destPackageFolder
                include '**/*'
            }

            println("Deleting baseSourcePackageFolder " + baseSourcePackageFolder + "..." + delete(baseSourcePackageFolder))

            println("Copying filtered template from '" + buidDirPath + temporaryTemplateLocation + "' to '" + project.rootDir +"'.");
            
            copy{
                    from buidDirPath + temporaryTemplateLocation
                    into project.rootDir
                    include '**/*'
                    filter { String line ->
                        line.replace("mavenGroupId", pi.projectGroup)
                        .replace("si-template-standalone-simple-artifactId", pi.artifactId)
                        .replace("mavenVersion", pi.projectVersion)
                        .replace("si-template-project-standalone-simple", pi.projectName)
                        .replace("org.springframework.integration.sts:",  pi.basePackageName)
                        .replace( "stsorg.stsspringframework.stsintegration", pi.basePackageName)
                    }
            }

            println("Deleting build directory " + buidDirPath + "..."
                                                + SpringIntegrationPluginUtils.deleteNonEmptyDirectory(project.buildDir))

        }

    }

}
