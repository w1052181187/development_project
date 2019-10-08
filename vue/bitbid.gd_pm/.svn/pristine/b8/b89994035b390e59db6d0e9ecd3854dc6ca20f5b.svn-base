<template>
  <div id="app">
    <router-view v-if="isRouter"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouter: true
    }
  },
  methods: {
    reload () { // 实现局部刷新
      this.isRouter = false
      this.$nextTick(function () {
        this.isRouter = true
      })
    }
  }
}
</script>

<style>
  @import './assets/css/common.css';
</style>
