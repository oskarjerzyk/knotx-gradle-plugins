/*
 * Copyright (C) 2019 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.release.docker

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class DockerBuildImageTask : DefaultTask() {

    @Input
    open lateinit var imageName: String

    @get:Input
    val version: String by lazy {
        project.version.toString()
    }

    @InputDirectory
    val dockerfileDir: File = project.projectDir

    @TaskAction
    fun execute() {
        val imageNameWithVersion = "$imageName:$version"
        logger.lifecycle("Building image: `$imageNameWithVersion` from ${dockerfileDir.path}")
        project.exec {
            workingDir = dockerfileDir
            commandLine = "docker build -t $imageNameWithVersion .".split(" ")
        }
    }
}