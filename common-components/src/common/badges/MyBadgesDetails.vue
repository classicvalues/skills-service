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
    <div class="card" data-cy="myBadges">
        <div class="card-header">
            <h6 class="card-title mb-0 float-left text-uppercase">
                My Earned Badges
            </h6>
            <span v-if="badges && badges.length > 0" class="text-muted float-right">
                <span class="badge badge-info">{{ badges.length }}</span> Badge<span v-if="badges.length > 1">s</span> Earned
            </span>
        </div>
        <div class="card-body">
            <no-data-yet v-if="!badges || badges.length === 0" title="No badges earned yet." sub-title="Take a peak at the catalog below to get started!"/>

            <div v-if="badges && badges.length > 0" class="row justify-content-md-center">
                <div v-for="(badge, index) in badges" v-bind:key="badge.badgeId" class="col-lg-3 col-sm-6 my-2">
                    <div class="card h-100 skills-card-theme-border skills-navigable-item">
                        <router-link  :to="badgeRouterLinkGenerator(badge)"
                                      tag="div" class="card-body earned-badge"
                                      :data-cy="`earnedBadgeLink_${badge.badgeId}`">
                            <i class="fa fa-check-circle position-absolute text-success" style="right: 10px; top: 10px;"/>
                            <i v-if="badge.gem" class="fas fa-gem position-absolute" style="top: 10px; left: 10px; color: purple"></i>
                            <i v-if="badge.global" class="fas fa-globe position-absolute" style="top: 10px; left: 10px; color: blue"></i>
                            <i :class="getIconCss(badge.iconClass, index)" style="font-size: 5em;"/>
                            <div class="card-title mb-0 text-truncate">
                                {{ badge.badge }}
                            </div>
                            <div v-if="displayBadgeProject && badge.projectName" class="text-muted text-center text-truncate" data-cy="badgeProjectName">
                              <small>Proj<span class="d-md-none d-xl-inline">ect</span>: {{badge.projectName}}</small>
                            </div>
                            <div data-cy="dateBadgeAchieved" class="text-muted mb-2"><i class="far fa-clock text-secondary" style="font-size: 0.8rem;"></i> {{ badge.dateAchieved | relativeTime() }}</div>
                        </router-link>
                    </div>

                </div>
            </div>

        </div>
    </div>
</template>

<script>
  import NoDataYet from '../utilities/NoDataYet';

  export default {
    name: 'MyBadgesDetails',
    components: { NoDataYet },
    data() {
      return {
        colors: ['text-info', 'text-warning', 'text-danger', 'text-primary'],
      };
    },
    props: {
      badges: {
        type: Array,
        required: true,
      },
      badgeRouterLinkGenerator: {
        type: Function,
        required: true,
      },
      displayBadgeProject: {
        type: Boolean,
        required: false,
        default: false,
      },
    },
    methods: {
      getIconCss(icon, index) {
        const colorIndex = index % this.colors.length;
        const color = this.colors[colorIndex];
        return `${icon} ${color}`;
      },
    },
  };
</script>

<style>
  .earned-badge {
    cursor: pointer;
  }
</style>
