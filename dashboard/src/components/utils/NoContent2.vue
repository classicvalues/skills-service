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
  <div class="text-secondary text-center">
    <div class="row">
      <div class="col">
        <span class="fa-stack" :class="iconSize" style="vertical-align: top;">
          <i class="fas fa-circle fa-stack-2x" :class="iconColor"></i>
          <i class="fa-stack-1x fa-inverse" :class="icon"></i>
        </span>
      </div>
      <div class="w-100"></div>
      <div class="col pt-2">
        <div class="h3 no-content">{{ title }}</div>
      </div>
    </div>
    <div class="row justify-content-center ">
      <div class="col col-md-8 col-lg-7 col-xl-5 no-content">
        <slot>
          {{ message }}
        </slot>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'NoContent2',
    props: {
      title: String,
      message: {
        type: String,
        default: '',
      },
      icon: {
        type: String,
        default: 'fas fa-dragon',
      },
      iconSize: {
        type: String,
        default: 'fa-3x',
      },
      iconColor: {
        type: String,
        default: 'text-secondary',
      },
    },
  };
</script>

<style scoped>
  .icon-style {
    font-size: 4rem;
    color: #969696;
  }

  .icon-container {
    background: #ededed;
  }

  .no-content {
    color: #3f5971;
  }
</style>
