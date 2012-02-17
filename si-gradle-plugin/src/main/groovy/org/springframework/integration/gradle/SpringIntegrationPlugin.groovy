package org.springframework.integration.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.GradleException

class SpringIntegrationPlugin implements Plugin<Project> {

    static final String lineSep = System.getProperty("line.separator")
    static final String inputPrompt = "${lineSep}>>"

    static final String logo = """
 _____            _               _____       _                        _   _
/  ___|          (_)             |_   _|     | |                      | | (_)
\\ `--. _ __  _ __ _ _ __   __ _    | |  _ __ | |_  ___  __ _ _ __ __ _| |_ _  ___  _ __
 `--. \\ '_ \\| '__| | '_ \\ / _` |   | | | '_ \\| __|/ _ \\/ _` | '__/ _` | __| |/ _ \\| '_ \\
/\\__/ / |_) | |  | | | | | (_| |  _| |_| | | | |_|  __/ (_| | | | (_| | |_| | (_) | | | |
\\____/| .__/|_|  |_|_| |_|\\__, |  \\___/|_| |_|\\__|\\___|\\__, |_|  \\__,_|\\__|_|\\___/|_| |_|
      | |                  __/ |                        __/ |
      |_|                 |___/                        |___/
           Gradle Plugin              https://github.com/ghillert/spring-integration-gradle-plugin
    """

    void apply(Project project) {

        def group = "Spring Integration";

        project.task('create-project-simple',     group: group, type: SimpleProjectTask, description: 'Creates a very basic Spring Integration project (command line based)')
        //project.task('create-project-standalone', group: group, type: SimpleProjectTask, description: 'Creates a command lined based project that uses file pollers')
        //project.task('create-project-war',        group: group, type: SimpleProjectTask, description: 'Creates a basic web-based (War) Spring Integration project.')
    }

    static String prompt(String message, String defaultValue = null) {
        if (defaultValue) {
            return System.console().readLine("${inputPrompt} ${message} [${defaultValue}] ") ?: defaultValue
        }
        return System.console().readLine("${inputPrompt} ${message} ") ?: defaultValue
    }

    static int promptOptions(String message, List options = []) {
        promptOptions(message, 0, options)
    }

    static int promptOptions(String message, int defaultValue, List options = []) {
        String consoleMessage = "${inputPrompt} ${message}"
        consoleMessage += "${lineSep}    Pick an option ${1..options.size()}"
        options.eachWithIndex { option, index ->
            consoleMessage += "${lineSep}     (${index + 1}): ${option}"
        }
        if (defaultValue) {
            consoleMessage += "${inputPrompt} [${defaultValue}] "
        } else {
            consoleMessage += "${inputPrompt} "
        }
        try {
            def range = 0..options.size() - 1
            int choice = Integer.parseInt(System.console().readLine(consoleMessage) ?: "${defaultValue}")
            if (choice == 0) {
                throw new GradleException('No option provided')
            }
            choice--
            if (range.containsWithinBounds(choice)) {
                return choice
            } else {
                throw new IllegalArgumentException('Option is not valid.')
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException('Option is not valid.', e)
        }
    }

    static boolean promptYesOrNo(String message, boolean defaultValue = false) {
        def defaultStr = defaultValue ? 'Y' : 'n'
        String consoleVal = System.console().readLine("${inputPrompt} ${message} (Y|n)[${defaultStr}] ")
        if (consoleVal) {
            return consoleVal.toLowerCase().startsWith('y')
        }
        return defaultValue
    }


}
