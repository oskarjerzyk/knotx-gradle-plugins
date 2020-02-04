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
package io.knotx.release

import io.knotx.release.docker.DockerBuildImageTask
import io.knotx.release.docker.DockerPublishImageTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

class KnotxReleaseDockerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            plugins.apply("io.knotx.release-base")

            tasks {
                named("updateChangelog") {
                    dependsOn(tasks.withType(DockerBuildImageTask::class.java))
                }
                register("prepare") {
                    group = "release"
                    dependsOn("updateChangelog")
                }
                register("publishArtifacts") {
                    group = "release"
                    dependsOn(tasks.withType(DockerPublishImageTask::class.java))
                }
            }
        }
    }
}