<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
// import '@/assets/css/common.scss'
import '@/assets/css/common.less'
export default {
  name: 'App',
  created () {
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem('store'))
        )
      )
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    // beforeunload事件在页面刷新时先触发
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
    })
  }
}
</script>
<style lang='less'>
  /*@import './assets/css/main.css';*/
  #app {
    font-family: '微软雅黑';
    color: #333333;
  }
</style>
