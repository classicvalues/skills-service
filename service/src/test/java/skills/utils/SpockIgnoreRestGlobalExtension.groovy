/**
 * Copyright 2021 SkillTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package skills.utils

import org.spockframework.runtime.extension.AbstractGlobalExtension
import org.spockframework.runtime.model.SpecInfo
import spock.lang.IgnoreRest

class SpockIgnoreRestGlobalExtension extends AbstractGlobalExtension {

    @Override
    void visitSpec(SpecInfo spec) {
        if (System.getenv("PREVENT_IGNOREREST") == "true") {
            spec.getFeatures().each {
                if (it.getFeatureMethod().getReflection().isAnnotationPresent(IgnoreRest)) {
                    System.err.println("!!!! @IgnoreRest cannot be used on [${it.spec.name}.${it.name}] !!!!")
                    throw new Error("@IgnoreRest cannot be used on  [${it.spec.name}.${it.name}]")
                }
            }
        }
    }
}
