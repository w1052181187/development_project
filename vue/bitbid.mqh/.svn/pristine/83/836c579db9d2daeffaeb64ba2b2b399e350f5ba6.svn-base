// 登录
<template>
<div class="login" id="login-page">
  <div class="top" style="background-color: #fff;">
    <span class="login-tit">
      <span class="logo-span">
        <img :src="logoPath" alt="" style="width: 45px; height:45px;vertical-align:middle;">
      </span>
      <span>{{systemName}}</span>
    </span>
  </div>
  <div class="middle" style="background-color: #fff;">
    <img src="./image/login.png" alt="">
    <div class="login-form">
    <el-form :model="LoginForm" :rules="rules" ref="LoginForm" label-width="100px" class="demo-ruleForm">
      <h1>登录</h1>
      <el-form-item prop="account">
        <el-input v-model="LoginForm.account" prefix-icon="iconfont mqh-icon-mobilephone" class="login-input" placeholder="账号" @keyup.enter.native="Enter"></el-input>
      </el-form-item>
      <el-form-item prop="password" class="rem-pass">
        <el-input v-model="LoginForm.password" type="password"  prefix-icon="iconfont mqh-icon-lock" class="login-lock" placeholder="密码" @keyup.enter.native="Enter"></el-input>
      </el-form-item>
      <el-form-item class="rem-pass">
        <el-checkbox v-model="LoginForm.remPassword" @change="rememberPw">记住密码</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onLogin('LoginForm')" @keyup.enter.native="Enter" style="width: 100%;">登录</el-button>
      </el-form-item>
    </el-form>
    </div>
  </div>
  <div class="bottom" style="background-color: #fff;">
    <span style="padding-top: 30px;display: inline-block;">Copyright@2014-2017 比比网络 版权所有 bibenet.com 晋ICP备14006571号-7</span>
  </div>
</div>
</template>
<script>
import {setUserInfo, getUserInfo, removeUserInfo} from 'common/js/auth'
import {systemInfo} from '../../../api/index'
import {commonJs} from 'common/js/common'
export default {
  name: 'login',
  data () {
    return {
      LoginForm: {
        account: '',
        password: ''
      },
      systemName: '', // 系统名称
      logoPath: '', // 系统logo路径
      rules: {
        account: [
          { required: true, message: '账号不能为空', trigger: 'blur' },
          { max: 20, message: '长度在20个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', trigger: 'blur' },
          { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getUserLoginInfo()
    this.getSystemInfo()
  },
  methods: {
    // 回车
    Enter: function (ev) {
      this.onLogin('LoginForm')
    },
    // 记住密码
    rememberPw (val) {
      if (val) {
        // 设置cookie值
        let obj = {
          account: this.LoginForm.account,
          password: this.LoginForm.password,
          remPassword: this.LoginForm.remPassword
        }
        setUserInfo(obj, 7)
      } else {
        // 清除cookie值
        removeUserInfo()
      }
    },
    getSystemInfo () {
      systemInfo.query().then((res) => {
        let logoFilePath = res.data.systemInfo.fileInformation.relativePath
        this.logoPath = `${commonJs.imageReviewUrl}?filePath=${logoFilePath}`
        this.systemName = res.data.systemInfo.systemName
      })
    },
    // 获得用户登录信息
    getUserLoginInfo () {
      let userInfo = getUserInfo()
      if (userInfo) {
        // 将字符串转成json对象
        this.LoginForm = JSON.parse(userInfo)
      }
    },
    // 登录
    onLogin (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 如果账号更换，清除之前的cookie
          let userInfo = getUserInfo()
          let currInfo = JSON.stringify(this.LoginForm)
          if (userInfo !== currInfo && userInfo !== undefined) {
            removeUserInfo()
            setUserInfo(currInfo, 7)
          }
          this.$store.dispatch('Login', this.LoginForm).then(userType => {
            this.$router.push(userType === 1 ? {path: '/admin/infoset'} : {path: '/admin'})
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
#login-page {
  .login-tit {
    color: #3F63F7;
    display: inline-block;
    width: 1200px;
    height: 80px;
    line-height: 80px;
    font-size: 26px;
    float: left;
    .logo-span {
      display: inline-block;
      height: 80px;
      line-height: 80px;
    }
  }
  h1 {
    padding: 20px;
    color: #999;
  }
  .middle{
    overflow: hidden;
    height: 100%;
    margin-top: 80px;
    img {
      background-repeat: no-repeat center center;
      height: 814px;
      float: left;
    }
    .login-input {
      i {
        font-size: 26px;
        color: #3F63F7;
        margin-right: 10px;
      }
    }
    .login-lock {
      i {
        font-size: 32px;
        color: #3F63F7;
        margin-right: 10px;
      }
    }
    .login-form {
      width: 430px;
      height: 380px;
      background: #fff;
      position: absolute;
      right: 20%;
      margin-top: 8%;
    }
    .rem-pass {
      margin-bottom: 0;
      .el-form-item__content {
        text-align: right;
      }
    }
  }
  .el-input--prefix .el-input__inner {
    padding-left: 45px;
  }
  .el-form-item__content {
    margin-left: 0 !important;
    padding: 0 40px;
  }
  .el-form-item__error {
    padding-left: 10%;
  }
  .top, .bottom{
    width: 100%;
    height: 80px;
    position: absolute;
  }
  .top {
    top: 0;
  }
  .bottom {
    position: fixed;
    bottom: 0;
  }
}
</style>
