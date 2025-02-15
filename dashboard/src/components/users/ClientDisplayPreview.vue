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
  <div v-if="!loading.availableVersions && !loading.userInfo">
    <sub-page-header title="Client Display">
      <b-form class="float-right" inline>
        <label class="pr-3 d-none d-sm-inline font-weight-bold" for="version-select">Version: </label>
        <b-form-select
          id="version-select"
          class="version-select"
          v-model="selectedVersion"
          :options="versionOptions"
          @change="versionChanged"/>
        <inline-help
          class="pl-2"
          msg="Multiple skills versions can be defined if you have multiple versions of your application deployed." />
      </b-form>
    </sub-page-header>
    <skills-display
      :options="configuration"
      :version="selectedVersion"
      :user-id="userIdParam"
      :theme="theme"
      ref="skillsDisplayRef"
      @route-changed="skillsDisplayRouteChanged"/>
  </div>

</template>

<script>
  import { SkillsDisplay, SkillsReporter } from '@skilltree/skills-client-vue';
  import SkillsDisplayOptionsMixin from '../myProgress/SkillsDisplayOptionsMixin';
  import SubPageHeader from '../utils/pages/SubPageHeader';
  import UsersService from './UsersService';
  import InlineHelp from '../utils/InlineHelp';

  export default {
    name: 'ClientDisplayPreview',
    mixins: [SkillsDisplayOptionsMixin],
    components: {
      InlineHelp,
      SubPageHeader,
      SkillsDisplay,
    },
    data() {
      return {
        projectId: '',
        userIdParam: '',
        loading: {
          userInfo: true,
          availableVersions: true,
        },
        selectedVersion: 0,
        versionOptions: [],
        theme: {
          disableSkillTreeBrand: true,
          disableBreadcrumb: true,
          maxWidth: '100%',
          pageTitleFontSize: '1.5rem',
          backButton: {
            padding: '5px 10px',
            fontSize: '12px',
            lineHeight: '1.5',
          },
        },
      };
    },
    created() {
      this.projectId = this.$route.params.projectId;
      if (this.$store.getters.isPkiAuthenticated) {
        // dn is provided when routed form other pages
        if (this.$route.params.dn && !this.$route.params.userId) {
          this.userIdParam = this.$route.params.dn;
          this.loading.userInfo = false;
        } else {
          this.userIdParam = {
            id: this.$route.params.userId,
            idType: 'ID',
          };
          this.loading.userInfo = false;
        }
      } else {
        this.userIdParam = this.$route.params.userId;
        this.loading.userInfo = false;
      }

      this.totalPoints = this.$route.params.totalPoints;

      UsersService.getAvailableVersions(this.projectId)
        .then((result) => {
          this.versionOptions = result;
          this.selectedVersion = Math.max(...this.versionOptions);
        })
        .finally(() => {
          this.loading.availableVersions = false;
        });
    },
    computed: {
      configuration() {
        return {
          projectId: this.projectId,
          authenticator: this.authenticator,
          serviceUrl: this.serviceUrl,
        };
      },
      serviceUrl() {
        return window.location.origin;
      },
      authenticator() {
        if (this.$store.getters.isPkiAuthenticated) {
          return 'pki';
        }
        return `${this.serviceUrl}/admin/projects/${encodeURIComponent(this.projectId)}/token/${encodeURIComponent(this.userIdParam)}`;
      },
    },
    methods: {
      versionChanged(newValue) {
        const maxVersion = Math.max(...this.versionOptions);
        if (maxVersion !== newValue) {
          SkillsReporter.reportSkill('VisitClientDisplayForEarlierVersion');
        }
      },
    },
  };
</script>

<style scoped>

</style>
