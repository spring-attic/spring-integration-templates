package org.springframework.integration.gradle

/**
 * Created by IntelliJ IDEA.
 * User: ghillert
 * Date: 12/14/11
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
class ProjectInformation {

    String projectName
    String basePackageName
    String projectGroup;
    String artifactId;
    String projectVersion;

    public ProjectInformation collect() {

        println("Please anser a few questions...")

        projectName     = SpringIntegrationPlugin.prompt('Project Name:')
        basePackageName = SpringIntegrationPlugin.prompt('Base Package:')

        if (projectName) {
            projectGroup   = SpringIntegrationPlugin.prompt('Group:',      basePackageName.toLowerCase())
            artifactId     = SpringIntegrationPlugin.prompt('ArtifactId:', projectName.toLowerCase())
            projectVersion = SpringIntegrationPlugin.prompt('Version:',    '1.0.BUILD-SNAPSHOT')
        } else {
            println 'No project name provided.'
            return null;
        }

        return this;

    }

}
