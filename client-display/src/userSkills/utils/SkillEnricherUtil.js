/*
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

export default {

    updateSkillPtsInList(skills, pts, skillId) {
        const index = skills.findIndex((item) => item.skillId === skillId);
        const skill = skills[index];
        const updatedSkill = this.addPts(skill, pts);
        skills.splice(index, 1, updatedSkill);
    },

    addPts(skill, pts) {
        const copy = { ...skill };
        copy.points += pts;
        copy.todaysPoints += pts;
        if (copy.points === copy.totalPoints) {
            copy.meta.complete = true;
            copy.achievedOn = new Date();
        }

        return copy;
    },

    addMeta(skill) {
        const copy = { ...skill };
        copy.meta = {
            complete: skill.points >= skill.totalPoints,
            selfReported: skill.selfReporting !== null,
            withPointsToday: skill.todaysPoints > 0,
            withoutProgress: skill.points === 0,
            inProgress: skill.points > 0 && skill.points < skill.totalPoints,
        };
        return copy;
    },

    addMetaToSummary(summary) {
        const res = summary;
        res.skills = res.skills.map((item) => {
            const skillRes = this.addMeta(item);
            if (item.type === 'SkillsGroup' && item.children) {
                skillRes.children = skillRes.children.map((child) => this.addMeta(child));
            }

            return skillRes;
        });
        return res;
    },

};
