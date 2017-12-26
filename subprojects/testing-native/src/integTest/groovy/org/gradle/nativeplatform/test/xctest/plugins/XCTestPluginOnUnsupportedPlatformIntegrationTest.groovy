/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.nativeplatform.test.xctest.plugins

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.util.Requires
import org.gradle.util.TestPrecondition

@Requires(TestPrecondition.WINDOWS)
class XCTestPluginOnUnsupportedPlatformIntegrationTest extends AbstractIntegrationSpec {
    def setup() {
        buildFile << "apply plugin: 'xctest'"
    }

    def "fails to build and run tests on unsupported platform"() {
        fails "check"

        expect:
        failure.assertHasCause("""No tool chain is available to build for platform 'current':
  - Tool chain 'swiftc' (Swift Compiler): Could not find Swift compiler 'swiftc' in system path.""")
    }
}
