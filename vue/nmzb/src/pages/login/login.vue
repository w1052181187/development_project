<template>
  <div id="login">
    <div class="login-top">
      <div class="topmain"><a href="#"><img src="../../../static/images/user/logo.png"/> </a></div>
    </div>
    <div class="maincontain">
      <div class="logmain">
        <div class="fl">
          <img src="../../../static/images/user/user_img.png"/>
          <div class="fltext">
            <p style="font-size: 16px; line-height: 80px;">内蒙古招投标协会</p>
            <p>内蒙古自治区依法招投标服务平台</p>
            <p>内蒙古自治区综合评标专家库</p>
          </div>
        </div>
        <div class="fr">
          <div class="title">
            <p>还没有账号？ <a @click="toRegister">马上注册</a></p>
            <span>
                会员登录
            </span>
          </div>
          <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="loginForm">
            <el-form-item prop="account" :class="IEVersion === 1? 'ie9box':''">
              <span v-if="IEVersion === 1" class="formtip">用户名：</span>
              <el-input :class="IEVersion === 1? 'shortinput':''" v-model="loginForm.account" placeholder="用户名"></el-input>
              <i v-if="IEVersion !== 1" class="icon_img"><img src="../../../static/images/user/user.png"/> </i>
            </el-form-item>
            <el-form-item prop="password" :class="IEVersion === 1? 'ie9box':''">
              <span v-if="IEVersion === 1" class="formtip">密码：</span>
              <el-input :class="IEVersion === 1? 'shortinput':''" type="password" v-model="loginForm.password" placeholder="密码" @keyup.enter.native="loginFormbtn('loginForm')"></el-input>
              <i v-if="IEVersion !== 1" class="icon_img"><img src="../../../static/images/user/password.png"/> </i>
            </el-form-item>
          </el-form>
          <div class="loginbtn">
            <!--<el-checkbox v-model="checked">记住用户名</el-checkbox>-->
            <el-button type="primary" @click="loginFormbtn('loginForm')">登 录</el-button>
          </div>
        </div>
      </div>
    </div>
    <!--登录注册 底部-->
    <div class="login-bottom">
      内蒙古招标投标协会&nbsp;&nbsp;&nbsp;&nbsp;版权所有&nbsp;&nbsp;&nbsp;&nbsp;蒙ICP备07000600号-1
    </div>
    <!--登录注册 底部-->
  </div>
</template>
<script>
export default {
  data () {
    return {
      checked: false,
      loginForm: {
        account: '',
        password: ''
      },
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    loginFormbtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('Login', this.loginForm).then(userType => {
            this.$router.push({path: '/main'})
          })
        } else {
          return false
        }
      })
    },
    toRegister () {
      this.$router.push({path: '/register'})
    }
  },
  computed: {
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      if (version[1] !== undefined) { // ie浏览器上使用
        let trimVersion = version[1].replace(/[ ]/g, '')
        if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
          return 1
        }
      }
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  body, html{
    background: #ffffff;
  }
  #login {
    .login-top{
      width: 100%;
      min-width: 1200px;
      height: 103px;
      overflow: hidden;
      background: #0084ff;
    }
    .login-top .topmain{
      width: 1200px;
      overflow: hidden;
      margin: 0 auto;
      text-align: left;
    }
    .login-top .topmain a{
      margin-top: 25px;
      display: block;
      border: 0px;
    }
    .login-top .topmain a img{
      border:none;
    }
    .maincontain{
      width: 100%;
      min-width: 1200px;
      height: 647px;
      overflow: hidden;
      background: url("../../../static/images/user/maincontain.jpg") no-repeat;
    }
    .logmain{
      width: 861px;
      box-shadow: #dddddd 0px 0px 10px;
      overflow: hidden;
      margin: 50px auto;
    }
    .logmain .fl{
      float: left;
      position: relative;
    }
    .logmain .fl .fltext{
      position: absolute;
      top: 107px;
      left: 37px;
      color: #ffffff;
      text-align: left;
    }
    .logmain .fr{
      width: 589px;
      height: 541px;
      background: #ffffff;
      float: right;
      border: 1px solid #eeeeee;
      -webkit-box-sizing: border-box;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .logmain .fr .title{
      overflow: hidden;
      padding: 0px;
      margin: 0px;
    }
    .logmain .fr .title p{
      padding: 0 26px;
      margin: 20px 0;
      text-align: right;
      line-height: 24px;
      font-size: 12px;
    }
    .logmain .fr .title p a{
      float: right;
      padding: 0 13px;
      margin-left: 5px;
      -webkit-border-radius: 15px;
      -moz-border-radius: 15px;
      border-radius: 15px;
      display: inline-block;
      line-height: 22px;
      font-size: 12px;
      color: #666666;
      border: 1px solid #999999;
      cursor: pointer;
    }
    .logmain .fr .title span{
      float: left;
      margin: 70px 0 0 107px;
      display: inline-block;
      line-height: 40px;
      color: #333333;
      font-size: 20px;
    }
    .loginForm{
      width: 365px;
      margin: 30px auto;
    }
    .login .el-form-item__content{
      margin-left: 0px!important;
      position: relative;
    }
    .el-input__inner{
      padding: 0 30px;
    }
    .icon_img{
      position: absolute;
      left: 5px;
      top: 0px;
      display: inline-block;
      height: 40px;
      line-height: 40px;
    }
    .icon_img img{
      vertical-align: middle;
    }
    .loginbtn {
      width: 365px;
      margin: 80px auto 0 auto;
    }
    .el-checkbox{
      float: left;
      margin-bottom: 13px;
    }
    .loginbtn .el-button--primary {
      width: 100%;
      color: #fff;
      -webkit-border-radius: 20px;
      -moz-border-radius: 20px;
      border-radius: 20px;
      background-color: #0084ff;
      border-color: #0084ff;
    }
    .formtip{
      width: 24%;
      display: inline-block;
      float: left;
      color: #2c3e50;
      text-align: right;
    }
    .shortinput{
      width: 72%;
      float: left;
      margin-left: 2%;
      padding-left: 2%;
    }
    .shortinput .el-input__inner{
      padding-left: 10px;
    }
    .ie9box .el-form-item__error{
      left: 28%;
    }
    .login-bottom{
      width: 1200px;
      margin: 30px auto;
      font-size: 12px;
      color: #999999;
      line-height: 30px;
      text-align: center;
    }
  }
</style>
