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

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.GradleException


/**
*
*  @author Gunnar Hillert
*  @since 1.0
*
*/
class SpringIntegrationPlugin implements Plugin<Project> {

    void apply(Project project) {

        def group = "Spring Integration";

        project.task('create-project-simple',     group: group, type: SimpleProjectTask, description: 'Creates a very basic Spring Integration project (command line based)')
        project.task('create-project-standalone', group: group, type: SimpleProjectTask, description: 'Creates a command lined based project that uses file pollers')
        project.task('create-project-war',        group: group, type: WarProjectTask, description: 'Creates a basic web-based (War) Spring Integration project.')
    
	}

}
