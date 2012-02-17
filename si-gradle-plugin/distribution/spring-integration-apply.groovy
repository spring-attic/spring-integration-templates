buildscript {
    repositories {

        mavenRepo url: "https://raw.github.com/ghillert/spring-integration-gradle-plugin/master/distribution"
       // mavenLocal()
    }

    dependencies {
        classpath 'org.springframework.integration.gradle:si-gradle-plugin:1.0.0.M1.BUILD-SNAPSHOT'
    }
}

if (!project.plugins.findPlugin(org.springframework.integration.gradle.SpringIntegrationPlugin)) {
    project.apply(plugin: org.springframework.integration.gradle.SpringIntegrationPlugin)
}

