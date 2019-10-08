<template>
  <div id="notFound" class="notFound">
    <div class="login-top">
      <div class="topmain"><a href=""><img src="../../../static/images/user/logo.png"/> </a></div>
    </div>
    <div class="main">
      <img src="../../../static/images/error.png"/>
      <div><el-button type="primary" @click="returnindex"> 返回 </el-button></div>
    </div>
    <div class="login-bottom">
      Copyright © 2005-2017 In800.com. All Rights Reserved. 内蒙古招标投标平台
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  methods: {
    returnindex () {
      this.$router.push({path: '/main'})
    }
  }
}
</script>
<style lang="less">
  body, html{
    background: #ffffff;
  }
  #notFound {
    .login-top {
      width: 100%;
      min-width: 1200px;
      height: 103px;
      overflow: hidden;
      background: url("../../../static/images/user/top_bg.png") repeat-x;
    }
    .login-top .topmain {
      width: 1200px;
      overflow: hidden;
      margin: 0 auto;
      text-align: left;
    }
    .login-top .topmain a {
      margin-top: 25px;
      display: block;
    }
    .main {
      width: 1200px;
      overflow: hidden;
      margin: 30px auto;
      padding: 70px 0;
    }
    .el-button--primary {
      margin-top: 20px;
      width: 100px;
    }
  }
</style>
