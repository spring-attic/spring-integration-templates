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

import groovy.lang.Closure;

import org.gradle.api.DefaultTask
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction
import org.springframework.integration.gradle.support.TemplateType;

/**
 *
 *  @author Gunnar Hillert
 *  @since 1.0
 *
 */
class SimpleProjectTask extends DefaultTask {
	
    /**
     * Template Task
     */
    @TaskAction
    def createIntegrationProject() {

        ProjectGenerator pg = new ProjectGenerator();
		pg.createIntegrationProject(project, TemplateType.STANDALONE_SIMPLE);

    }

}
