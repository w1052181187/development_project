<template>
  <div id="forgetPwd">
    <div class="logobigbox">
      <div class="logobox">
        <div class="logoboximg">
          <img src="../../../static/images/login_logo.png" alt="">
        </div>
      </div>
    </div>
    <div class="login_bannerimg">
      <div class="login_inputbox">
        <div class="login_intbox">
          <div class="login_titlebox">
            忘记密码
            <span>你可以通过手机验证方式找回密码</span>
            <router-link :to="{ path: '/login' }" class="return_login">
              返回登录
            </router-link>
          </div>
          <div class="step_box">
            <ul class="forget_step">
            <li :class="(active===1)? 'active' :'prev'">
              <span>输入手机号</span>
              <i></i>
            </li>
            <li :class="(active===2)? 'active' : (active===3 || active===4)?'prev':''">
              <span>短信验证</span>
              <i></i>
            </li>
            <li :class="(active===3)? 'active': (active===4)?'prev':''">
              <span>重置密码</span>
              <i></i>
            </li>
            <li :class="(active===4)? 'active':''">
              <span>重置成功</span>
              <i></i>
            </li>
          </ul>
          </div>
          <el-form :model="stepFirstForm" :rules="rules" ref="stepFirstForm" :validate-on-rule-change="true" v-if="this.active === 1">
            <el-form-item  prop="users" >
              <el-input v-model="stepFirstForm.users"  placeholder="请输入手机号码">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/images/phone.png" alt="">
                </i>
              </el-input>
            </el-form-item>
          </el-form>
          <el-form :model="stepSecondForm" :rules="rules2" ref="stepSecondForm" :validate-on-rule-change="true" v-if="this.active === 2">
            <el-form-item  prop="smsCheckCode">
              <el-input v-model="stepSecondForm.smsCheckCode"  placeholder="请输入短信验证码">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/images/code.png" alt="">
                </i>
              </el-input>
            </el-form-item>
          </el-form>
          <el-form :model="stepThirdForm" :rules="rules3" ref="stepThirdForm" :validate-on-rule-change="true" v-if="this.active === 3">
            <el-form-item  prop="newPwd">
              <el-input v-model="stepThirdForm.newPwd" type="password" placeholder="请输入新密码">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/images/pwd.png" alt="">
                </i>
              </el-input>
            </el-form-item>
            <el-form-item  prop="ConfirmPwd">
              <el-input v-model="stepThirdForm.ConfirmPwd" type="password" placeholder="请确认新密码">
                <i slot="suffix" class="icon_imgbox_login">
                  <img src="../../../static/images/pwd.png" alt="">
                </i>
              </el-input>
            </el-form-item>
          </el-form>
          <div class="setsuccess" v-if="this.active === 4">
            <img src="../../../static/images/success.png"/>
            <div>
              <strong>密码重置成功！</strong>
              <p><span>{{count}}s</span> 后自动跳转登录页面</p>
            </div>
          </div>
          <div class="loginbtn">
            <el-button type="primary" @click="nextStep()" v-if="this.active !== 4">{{steptext}}</el-button>
          </div>
        </div>
      </div>
    </div>
    <p class="banquan">平台运营单位：中采国信（北京）科技发展有限公司</p>
  </div>
</template>
<script>
import $ from 'jquery'
import {validateMobilePhone} from '@/assets/js/validate'
import {base} from '@/api/base'
export default {
  data () {
    // 密码
    let validatePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (this.stepThirdForm.ConfirmPwd !== '') {
        this.$refs.stepThirdForm.validateField('ConfirmPwd')
        callback()
      }
    }
    // 确认密码
    let validateConfirmPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.stepThirdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 手机号码
    let validPhone = (rule, value, callback) => {
      if (value && !validateMobilePhone(value)) {
        callback(new Error('请输入正确的手机号码'))
      } else {
        callback()
      }
    }
    return {
      active: 1,
      steptext: '下一步',
      stepFirstForm: {
        users: ''
      },
      stepSecondForm: {
        smsCheckCode: ''
      },
      stepThirdForm: {
        newPwd: '',
        ConfirmPwd: ''
      },
      // 验证
      rules: {
        users: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          { validator: validPhone, trigger: ['blur', 'change'] }
        ]
      },
      rules2: {
        smsCheckCode: [
          { required: true, message: '验证码不能为空', trigger: 'blur' }
        ]
      },
      rules3: {
        newPwd: [
          { required: true, message: '新密码不能为空', trigger: 'blur' },
          {min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur']},
          { validator: validatePwd, trigger: ['blur'] }
        ],
        ConfirmPwd: [
          { required: true, message: '确认密码不能为空', trigger: 'blur' },
          {min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur']},
          { validator: validateConfirmPwd, trigger: ['blur'] }
        ]
      },
      // 倒计时
      count: 5,
      timer: null,
      isPass: false // 接口是否通过
    }
  },
  created () {},
  methods: {
    mousedownFn: function (e) {
      this.mouseMoveStata = true
      this.beginClientX = e.clientX
    },
    // 下一步
    nextStep () {
      let formName
      if (this.active === 1) {
        formName = 'stepFirstForm'
        let submitForm = {
          cellPhone: this.stepFirstForm.users,
          type: 2
        }
        base.sendVerCode(submitForm).then((res) => {
          if (res.data.resCode === '0000') {
            this.isPass = true
          }
        })
      } else if (this.active === 2) {
        this.isPass = false
        formName = 'stepSecondForm'
        // 检查验证码
        let submitForm = {
          cellPhone: this.stepFirstForm.users,
          smsCode: this.stepSecondForm.smsCheckCode
        }
        base.authCode(submitForm).then((res) => {
          if (res.data.resCode === '0000') {
            this.isPass = true
          }
        })
        this.steptext = '重置密码'
      } else if (this.active === 3) {
        this.isPass = false
        formName = 'stepThirdForm'
        let submitFomr = {
          account: this.stepFirstForm.users,
          password: this.stepThirdForm.newPwd
        }
        base.resetPwd(submitFomr).then((res) => {
          if (res.data.resCode === '0000') {
            this.isPass = true
          }
        })
        this.timeCount()
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (!this.isPass) {
            this.active++
          }
        } else {
          return false
        }
      })
    },
    // 倒计时
    timeCount () {
      let that = this
      this.timer = setInterval(() => {
        if (this.count > 1 && this.count <= 5) {
          this.count--
        } else {
          this.$router.push({path: '/login'})
          clearInterval(that.timer)
          this.timer = null
        }
      }, 1000)
    }
  },
  mounted () {
    $('body').on('mousemove', (e) => {
      if (this.mouseMoveStata) {
        var width = e.clientX - this.beginClientX
        if (width > 0 && width <= this.maxwidth) {
          $('.handler').css({'left': width})
          $('.drag_bg').css({'width': width})
        } else if (width > this.maxwidth) {
          this.successFunction()
        }
      }
    })
    $('body').on('mouseup', (e) => {
      this.mouseMoveStata = false
      var width = e.clientX - this.beginClientX
      if (width < this.maxwidth) {
        $('.handler').css({'left': 0})
        $('.drag_bg').css({'width': 0})
      }
    })
  }
}
</script>
<style lang="less">
  #forgetPwd {
    .login_banner {
      width: 100%;
    }
    .logobigbox {
      background: #fff;
      height: 90px;
      width: 100%;
    }
    .logobox {
      width: 1220px;
      height: 90px;
      margin: 0 auto;
    }
    .login_bannerimg {
      width: 100%;
      height: 740px;
      background: url("../../../static/images/banner/banner3.png");
    }
    .logoboximg {
      width: 311px;
      height: 100%;
      position: relative;
    }
    .logoboximg > img {
      width: 311px;
      height: 62px;
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      margin: auto;
    }
    .login_inputbox {
      width: 1220px;
      height: 100%;
      margin: 0 auto;
      position: relative;
    }
    .login_intbox {
      width: 911px;
      height: 526px;
      background: #fff;
      position: absolute;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      margin-right: 155px;
      border-radius: 3px;
    }
    .login_titlebox {
      width: 100%;
      color: #666666;
      font-size: 28px;
      line-height: 30px;
      padding: 30px 35px 20px 35px;
      box-sizing: border-box;
      text-align: center;
      border: 1px solid #dcdfe6;
    }
    .login_titlebox span{
      width: 100%;
      overflow: hidden;
      display: inline-block;
      font-size: 14px;
    }
    .login_titlebox span a{
      color: #3f63f6;
    }
    .return_login{
      position: absolute;
      font-size: 14px;
      right: 20px;
      top: 30px;
      color: #3f63f6;
      line-height: 24px;
    }

    // 步骤条
    .step_box{
      overflow: hidden;
      margin: 30px auto;
      text-align: center;
    }
    ul.forget_step{
      display: inline-block;
      list-style: none;
      height: 45px;
      cursor: pointer;
      text-align: center;
      background: url("../../../static/images/line.png") 81px bottom no-repeat;
    }
    ul.forget_step:after {
      display: block;
      content: "";
      clear: both;
    }
    ul.forget_step li{
      float: left;
      height: 45px;
      margin: 0 57px;
      position: relative;
      text-align: center;
    }
    ul.forget_step li i{
      display: inline-block;
      position: absolute;
      bottom: -3px;
      left: 50%;
      width: 9px;
      height: 9px;
      border-radius: 50%;
      background: #cacaca;
      color: #ffffff;
      font-style: normal;
    }
    ul.forget_step li span{
      display: inline-block;
      line-height: 45px;
      margin-left: 10px;
      font-size: 14px;
    }
    ul.forget_step li:first-child{
      margin-left: 0px;
    }
    ul.forget_step li:last-child{
      margin-right: 0px;
    }
    ul.forget_step li.active i{
      background: #3f63f6;
    }
    ul.forget_step li.active span{
      color: #3f63f6;
    }
    ul.forget_step li.prev i{
      background: #0084ff;
    }

    .el-form{
      width: 50%;
      margin: 80px auto 10px auto;
      overflow: hidden;
    }

    .icon_imgbox_login{
      display: inline-block;
      width: 15px;
      height: 15px;
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
      width: 80%;
    }
    .getSmsCheckCode{
      position: absolute;
      right: 0px;
      top: 0px;
      background: #3f63f6;
      border-color: #3f63f6;
    }
    .loginbtn{
      width: 50%;
      margin: 0 auto;
      text-align: center;
    }
    .loginbtn .el-button--primary{
      width: 100%;
      margin: 10px auto;
      background: #3f63f6;
      border-color: #3f63f6;
    }
    .userName .el-form-item__content{
      margin-left: 10px;
    }
    .el-input__prefix, .el-input__suffix {
      position: absolute;
      top: 0;
      left: 10px;
      -webkit-transition: all .3s;
      height: 100%;
      color: #c0c4cc;
      text-align: center;
      font-size: 21px;
      right: auto;
    }
    .iconfont {
      font-family: "iconfont" !important;
      font-size: 20px;
      font-style: normal;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
      color: #75b6f3;
    }
    .el-input__inner{
      padding: 0 35px;
    }
    .setsuccess{
      width: 227px;
      height: 90px;
      margin: 80px auto;
    }
    .setsuccess img{
      float: left;
    }
    .setsuccess div{
      width: 155px;
      float: right;
    }
    .setsuccess div strong{
      color: #43bf2b;
      font-size: 20px;
    }
    .setsuccess div p{
      font-size: 14px;
      color: #666666;
      line-height: 30px;
    }
    .setsuccess div p span{
      color: #3f63f6;
      font-weight: bold;
      font-size: 16px;
    }
    .banquan{
      text-align: center;
      line-height: 36px;
      font-size: 14px;
    }
    /*修改input 高度*/
    .el-input__inner{
      height: 40px;
      line-height: 40px;
    }
    .el-input__icon{
      line-height: 40px;
    }
    .el-button{
      padding: 12px 20px;
    }
    .el-form-item__label{
      line-height: 40px;
    }
    .el-form-item__content{
      line-height: 40px;
    }
    .el-form-item{
      margin-bottom: 22px;
    }
    .el-form-item__error{
      padding-top: 4px;
    }
  }
</style>
