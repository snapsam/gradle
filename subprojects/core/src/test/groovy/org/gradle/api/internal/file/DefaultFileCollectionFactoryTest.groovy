/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.api.internal.file

import org.gradle.api.Task
import org.gradle.api.internal.file.collections.MinimalFileSet
import org.gradle.api.tasks.TaskDependency
import spock.lang.Specification

class DefaultFileCollectionFactoryTest extends Specification {
    def factory = new DefaultFileCollectionFactory()

    def "lazily queries contents of collection"() {
        def contents = Mock(MinimalFileSet)
        def file = new File("a")

        when:
        def collection = factory.create(contents)

        then:
        0 * contents._

        when:
        def files = collection.files

        then:
        files == [file] as Set
        1 * contents.files >> [file]
        0 * contents._
    }

    def "lazily queries dependencies of collection"() {
        def contents = Mock(MinimalFileSet)
        def builtBy = Mock(TaskDependency)
        def task = Stub(Task)

        when:
        def collection = factory.create(builtBy, contents)

        then:
        0 * builtBy._

        when:
        def tasks = collection.buildDependencies.getDependencies(null)

        then:
        tasks == [task] as Set
        1 * builtBy.getDependencies(_) >> [task]
        0 * _
    }
}
