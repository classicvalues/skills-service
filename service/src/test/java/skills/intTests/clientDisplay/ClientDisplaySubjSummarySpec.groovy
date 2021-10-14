/**
 * Copyright 2020 SkillTree
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
package skills.intTests.clientDisplay

import skills.intTests.utils.DefaultIntSpec
import skills.intTests.utils.SkillsFactory

class ClientDisplaySubjSummarySpec extends DefaultIntSpec {

    def "load subject summary"(){
        def proj1 = SkillsFactory.createProject(1)
        def proj1_subj = SkillsFactory.createSubject(1, 1)
        proj1_subj.helpUrl = "http://foo.org"
        proj1_subj.description = "This is a description"
        List<Map> allSkills = SkillsFactory.createSkills(10, 1, 1)
        List<Map> proj1_skills = allSkills[0..2]

        skillsService.createProject(proj1)
        skillsService.createSubject(proj1_subj)
        skillsService.createSkills(proj1_skills)

        // skills group1 - enabled
        def skillsGroup1 = allSkills[3]
        skillsGroup1.type = 'SkillsGroup'
        skillsGroup1.enabled = 'true'
        skillsGroup1.numSkillsRequired = 2
        skillsService.createSkill(skillsGroup1)
        String skillsGroup1Id = skillsGroup1.skillId
        def group1Children = allSkills[4..6]
        group1Children.each { skill ->
            skillsService.assignSkillToSkillsGroup(skillsGroup1Id, skill)
        }

        // skills group2 - disabled (should not be included in the summary)
        def skillsGroup2 = allSkills[7]
        skillsGroup2.type = 'SkillsGroup'
        skillsGroup2.enabled = 'false'
        skillsService.createSkill(skillsGroup2)
        String skillsGroup2Id = skillsGroup2.skillId
        def group2Children = allSkills[8..9]
        group2Children.each { skill ->
            skillsService.assignSkillToSkillsGroup(skillsGroup2Id, skill)
        }

        when:
        def summary = skillsService.getSkillSummary("user1", proj1.projectId, proj1_subj.subjectId)
        then:
        summary.skills.size() == 4
        summary.skills[0..2].every { it.type == 'Skill' && it.maxOccurrencesWithinIncrementInterval == 1}
        summary.skills[3].type == 'SkillsGroup'
        summary.skills[3].enabled == 'true'
        summary.skills[3].numSkillsRequired == 2
        summary.skills[3].children
        summary.skills[3].children.size() == 3
        summary.skills[3].children.every { it.type == 'Skill' }

        summary.description == "This is a description"
        summary.helpUrl == "http://foo.org"
    }
}
