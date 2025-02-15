/*
Copyright 2020 SkillTree

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
<template>
    <div>
        <skills-spinner :loading="loading" />

        <div v-if="!loading">
            <skills-title>Badge Details</skills-title>

            <div class="card">
                <div class="card-body">
                    <badge-details-overview :badge="badgeOverview"></badge-details-overview>
                </div>
                <div v-if="badge.helpUrl" class="card-footer text-left">
                  <a :href="badge.helpUrl" target="_blank" rel="noopener" class="btn btn-sm btn-outline-info skills-theme-btn">
                    Learn More <i class="fas fa-external-link-alt"></i>
                  </a>
                </div>
            </div>

            <skills-progress-list @self_report="refreshHeader" v-if="badge" :subject="badge" :show-descriptions="showDescriptions" type="badge"/>
        </div>
    </div>
</template>

<script>
  import BadgeDetailsOverview from '@/common-components/badges/BadgeDetailsOverview';
  import SkillsProgressList from '@/userSkills/skill/progress/SkillsProgressList';
  import SkillsSpinner from '@/common/utilities/SkillsSpinner';

  import UserSkillsService from '@/userSkills/service/UserSkillsService';

  import SkillsTitle from '@/common/utilities/SkillsTitle';

  export default {
    components: {
      SkillsTitle,
      SkillsProgressList,
      BadgeDetailsOverview,
      SkillsSpinner,
    },
    data() {
      return {
        loading: true,
        badge: null,
        badgeOverview: null,
        initialized: false,
        showDescriptions: false,
      };
    },
    watch: {
      $route: 'fetchData',
    },
    mounted() {
      this.fetchData();
    },
    methods: {
      fetchData() {
        UserSkillsService.getBadgeSkills(this.$route.params.badgeId)
          .then((badgeSummary) => {
            this.badge = badgeSummary;
            this.badgeOverview = badgeSummary;
            this.loading = false;
          });
      },
      refreshHeader(event) {
        if (event.badgeId && event.badgeId === this.badge.badgeId) {
          UserSkillsService.getBadgeSkills(this.$route.params.badgeId, false, false)
            .then((badgeSummary) => {
              this.badgeOverview = badgeSummary;
            });
        }
      },
    },
  };
</script>

<style scoped>

</style>
