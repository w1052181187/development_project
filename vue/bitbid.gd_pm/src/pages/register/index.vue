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
          <span>注册</span>
          <span class="have_account" @click="loginBtn">已有账号？立即登录</span>
        </div>
        <div class="logo_bottom_inputbox">
          <el-form :model="logonForm" ref="logonForm"  class="demo-ruleForm login_conbox">
            <el-form-item prop="type" v-model="logonForm.type">
            </el-form-item>
            <el-form-item prop="account" >
              <el-input v-model="logonForm.account" suffix-icon="iconfont"  placeholder="用户名" class="icon_imgbox_loginfather">
                <i slot="suffix" class="icon_imgbox_login yonghu">
                  <img src="../../../static/img/yonghu.png" alt="">
                </i>
              </el-input>
            </el-form-item>
            <el-form-item prop="phone">
              <el-input v-model="logonForm.phone" suffix-icon="iconfont"  placeholder="手机号码" class="icon_imgbox_loginfather">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/img/shouji.png" alt="">
                </i>
              </el-input>
            </el-form-item>
            <el-form-item prop="code">
              <el-input type="text" v-model="logonForm.code" suffix-icon="iconfont"  placeholder="短信验证码" class="icon_imgbox_loginfather icon_imgbox_code phone_inputbox" @keyup.enter.native="logonFormbtn('logonForm')">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/img/yanzhengma.png" alt="">
                </i>
              </el-input>
              <div class="phone_box" v-show="show" @click="getCode">获取验证码</div>
              <div class="phone_box" v-show="!show">{{count}} s</div>
            </el-form-item>
            <el-form-item prop="password">
              <el-input type="password" v-model="logonForm.password" suffix-icon="iconfont"  placeholder="密码" class="icon_imgbox_loginfather">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/img/mima.png" alt="">
                </i>
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="login_btnbigbox">立即注册</div>
      </div>
    </div>
    <!--底部-->
    <div class="footer_login_box">广东比比信息技术服务有限公司 版权所有</div>
    <!--底部-->
  </div>
</template>

<script>
export default {
  name: '',
  data () {
    return {
      logonForm: {},
      checked: false,
      show: true,
      count: '',
      timer: null
    }
  },
  methods: {
    // 验证码倒计时
    getCode () {
      let timeOut = 60
      if (!this.timer) {
        this.count = timeOut
        this.show = false
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= timeOut) {
            this.count--
          } else {
            this.show = true
            clearInterval(this.timer)
            this.timer = null
          }
        }, 1000)
      }
    },
    loginBtn () {
      this.$router.push({path: '/login'})
    }
  },
  mounted () {
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
    .have_account{
      font-size: 12px;
      color: #0096ff;
      float: right;
      cursor: pointer;
    }
  }
</style>
