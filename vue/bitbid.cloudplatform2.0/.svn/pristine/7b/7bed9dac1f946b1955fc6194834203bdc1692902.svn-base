<template>
  <div id="noauth">
    <img src="../../../static/images/wuquanxian.png" alt="" @click="returnBtn">
  </div>
</template>
<script>
export default {
  data () {
    return {
      meg: 'Not found'
    }
  },
  methods: {
    returnBtn () {
      if (this.$store.getters.authUser.userType === 1) {
        this.$router.push({path: '/admin'})
      } else {
        this.$router.push({path: '/index'})
      }
    }
  }
}
</script>
<style lang="less">
  #noauth{
    text-align: center;padding: 200px 0;line-height:1.5;
    .retuenbtn{
      width: 65px;
      height: 30px;
      position: absolute;
      left: 42.5%;
      top: 35%;
      cursor: pointer;
    }
  }
</style>
