<template>
  <div id="login_box">
    <div class="logo_imgbox">
      <div class="logo_imgsonbox">
        <img src="../../../static/img/admin_logo.png" alt="">
      </div>
    </div>
    <div class="logo_contentbox">
      <div class="logo_contentleftbox">
        <img src="../../../static/img/login_beijing.png" alt="">
      </div>
      <div class="logo_contentrightbox">
        <div class="logo_contentright_title">
          <div class="login_conbox_son" v-for="(item,index) in items" :key="index" :class="{active:isTrue==index}" @click="chooseAddr(index)">{{item.name}}</div>
        </div>
        <div class="logo_bottom_inputbox">
          <el-form :model="logonForm" :rules="rules" ref="logonForm"  class="demo-ruleForm login_conbox">
            <el-form-item prop="type" v-model="logonForm.type">
            </el-form-item>
            <el-form-item prop="account" >
              <el-input v-model="logonForm.account" suffix-icon="iconfont"  placeholder="用户名" class="icon_imgbox_loginfather">
                <i slot="suffix" class="icon_imgbox_login yonghu">
                  <img src="../../../static/img/yonghu.png" alt="">
                </i>
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" v-model="logonForm.password" suffix-icon="iconfont"  placeholder="密码" class="icon_imgbox_loginfather">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/img/mima.png" alt="">
                </i>
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-input type="text" v-model="logonForm.code" suffix-icon="iconfont"  placeholder="验证码" class="icon_imgbox_loginfather icon_imgbox_code" @keyup.enter.native="logBtn('logonForm')">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/img/yanzhengma.png" alt="">
                </i>
              </el-input>
              <div class="code_img">
                <img :src="this.validateImgSrc" alt="" v-on:click="reloadSrc()">
              </div>
            </el-form-item>
          </el-form>
          <div class="remember_bigbox">
            <div class="remember_box">
              <el-checkbox v-model="checked" @change="changeKeepStatus">记住用户名</el-checkbox>
            </div>
          </div>
        </div>
        <div class="login_btnbigbox" @click="logBtn('logonForm')">登录</div>
      </div>
    </div>
    <!--底部-->
    <div class="footer_login_box">广东比比信息技术服务有限公司 版权所有</div>
    <!--底部-->
  </div>
</template>

<script>
import {auth} from '@/api'
import {loginUrl} from '@/assets/js/common'
import {getIsKeepUser} from '@/utils/auth'
export default {
  name: '',
  data () {
    let validateCode = (rule, value, callback) => {
      if (!value || value.toLowerCase() !== this.validateCode.toLowerCase()) {
        callback(new Error('请输入正确的验证码'))
        this.reloadSrc()
      } else {
        callback()
      }
    }
    return {
      isTrue: 0, // 默认登录方式
      // 登录方式
      items: [
        {name: '运营'},
        {name: '代理'}
      ],
      logonForm: {
        account: '',
        password: '',
        isKeepUser: 0,
        type: 1
      },
      checked: false,
      validateImgSrc: '',
      validateCode: '',
      rules: {
        code: [
          {required: true, message: '请输入验证码', trigger: ['blur', 'change']},
          {validator: validateCode, trigger: []}
        ]
      },
      keepAccount: ''
    }
  },
  methods: {
    // 切换
    chooseAddr (index) {
      this.isTrue = index
      this.logonForm = {
        account: '',
        password: '',
        isKeepUser: 0
      }
      if (this.isTrue === 0) {
        this.logonForm.type = 1
      } else {
        this.logonForm.type = 2
      }
    },
    logBtn (logonForm) {
      this.$refs[logonForm].validate((valid) => {
        if (valid) {
          this.$store.dispatch('Login', this.logonForm).then((res) => {
            this.reloadSrc()
            this.$store.dispatch('GetLoginInfo').then((res) => {
              if (this.$store.getters.authUser.userType === 2) {
                if (this.$store.getters.authUser.isQyUser === 1) { // 表示个人 ，没有企业信息
                  this.$router.push({path: '/admin/collection/index'})
                } else {
                  this.$router.push({path: '/admin/enterprise/index'})
                }
              } else {
                this.$router.push({path: '/admin/contract-agency/contract/index'})
              }
            })
          })
        }
      })
    },
    reloadSrc () {
      let time = new Date().getTime()
      auth.getValidateCode(time).then((res) => {
        this.validateCode = res.data.validCode
        this.validateImgSrc = loginUrl + 'generateValidateCode?validCode=' + this.validateCode
      })
    },
    changeKeepStatus () {
      if (this.checked) {
        this.logonForm.isKeepUser = 1
      } else {
        this.logonForm.isKeepUser = 0
      }
    },
    getKeepAccount () {
      if (this.keepAccount) {
        this.logonForm.account = this.keepAccount
      }
    }
  },
  mounted () {
    if (typeof (getIsKeepUser()) !== 'undefined' && getIsKeepUser() !== '') {
      this.checked = true
      this.logonForm.isKeepUser = 1
      this.keepAccount = getIsKeepUser()
    }
    this.getKeepAccount()
    this.reloadSrc()
  }
}
</script>

<style lang="less">
  body{
    background: #f5f5f5;
  }
  #login_box{
    .icon_imgbox_loginfather>.el-input__inner{
      padding-left: 38px;
    }
    .icon_imgbox_loginfather>.el-input__suffix{
      left: 10px;
      text-align: left;
    }
    .icon_imgbox_login{
      display: inline-block;
      width: 17px;
      height: 17px;
      vertical-align: middle;
      position: relative;
    }
    .icon_imgbox_login>img{
      width: 20px;
      height: 20px;
      vertical-align: top;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .el-input__inner{
      vertical-align: middle;
      font-size: 14px;
    }
    .icon_imgbox_code{
      width: 70%;
      display: inline-block;
      float: left;
    }
    .code_img{
      display: inline-block;
      width: 70px;
      height: 40px;
      float: right;
      position: relative;
    }
    .code_img>img{
      width: 70px;
      height: 40px;
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      right: 0;
      margin: auto;
    }
    .login_conbox_son{
      display: inline-block;
      width: 50%;
      height: 100%;
      float: left;
      font-size: 15px;
      font-weight: bold;
      color: #333333;
      cursor: pointer;
      text-align: center;
    }
    .active{
      background: #fff;
      background: url("../../../static/img/jianbian.png") bottom center no-repeat;
    }
  }
</style>
