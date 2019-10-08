<template>
  <div id="header-home" class="header-home">
    <div class="head-cont">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="logo-box">
            <img src="/static/images/home/logo.png" alt="logo">
          </div>
        </el-col>
        <el-col :span="18">
          <el-menu :default-active="activeNav" :router="true" class="el-menu-demo" mode="horizontal">
            <el-menu-item index="/home">首页</el-menu-item>
            <el-menu-item index="/home-deal-notice">成交公示</el-menu-item>
            <el-menu-item index="/home-expert-notice">异常公示</el-menu-item>
            <el-menu-item index="/home-policy-law">政策法规</el-menu-item>
          </el-menu>
          <el-button type="danger" size="small" @click="login" class="login-btn">登 录</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  name: 'header-home',
  data () {
    return {
      activeNav: '/home'
    }
  },
  watch: {
    '$route': 'setActiveNav'
  },
  methods: {
    login () {
      this.$router.push({path: '/login'})
    },
    setActiveNav () {
      if (this.$route.meta) {
        this.activeNav = this.$route.meta.active
      }
    }
  },
  mounted () {
    this.setActiveNav()
  }
}
</script>
<style lang="less">
#header-home {
  border-bottom: solid 1px #e6e6e6;
  .head-cont {
    width: 1200px;
    margin: 0 auto;
    .logo-box {
      height: 60px;
      line-height: 60px;
    }
    .logo-box img {
      vertical-align: middle;
    }
    .login-btn {
      position: absolute;
      right: 15px;
      top: 15px;
      background-color: #D42C32;
      border-color: #D42C32;
    }
  }
  .el-menu.el-menu--horizontal {
    border-bottom: 0;
    text-align: right;
    float: right;
    padding-right: 145px;
  }
  .el-menu--horizontal>.el-menu-item.is-active {
    border-top: 2px solid #FFAE00;
    color: #fff;
    background: #D42C32 !important;
    border-bottom: 0;
  }
  .el-menu-item.is-active {
    background: #D42C32 !important;
  }
  .el-menu-demo.el-menu {
    background-color: transparent;
  }
}
</style>
