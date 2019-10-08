<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style lang='less'>
  @import './assets/css/cloudcommon.css';
  #app {
    font-family: '微软雅黑';
    color: #333333;
  }
</style>
