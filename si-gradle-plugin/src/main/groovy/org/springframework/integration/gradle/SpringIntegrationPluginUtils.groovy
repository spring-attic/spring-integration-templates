/*
* Copyright 2002-2012 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.springframework.integration.gradle

import java.util.List;

/**
 *
 *  @author Gunnar Hillert
 *  @since 1.0
 *
 */
class SpringIntegrationPluginUtils {

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
           Gradle Plugin         https://github.com/SpringSource/spring-integration-templates
"""
	
    public static void retrieveClassResource(String inputClassPathResource, String outputFileLocation) {

        println("Copy resource from '" + inputClassPathResource + "' "
              + "to '" + outputFileLocation + "'.");

        InputStream is = SpringIntegrationPluginUtils.getResourceAsStream(inputClassPathResource)

        OutputStream out = new FileOutputStream(outputFileLocation);

        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = is.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        is.close();
        out.flush();
        out.close();

    }

    public static boolean deleteNonEmptyDirectory(File directory) {

        if (directory.isDirectory()) {
            for (File child : directory.listFiles()) {
                deleteNonEmptyDirectory(child);
            }
        }

        directory.delete()

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
				throw new IllegalArgumentException('No option provided')
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
