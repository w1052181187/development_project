<template>
    <div class="login_bigbox" id="login_bigbox">
      <div class="top_bigbox">
        <div class="logo_imgbox">
          <img src="../../../static/img/login.png" alt="">
        </div>
      </div>
      <div class="login_con_bigbox">
        <div class="login_conboxfather">
          <div class="login_conboxtop">
            <div class="login_conbox_son" v-for="(item,index) in items" :key="index" :class="{active:isTrue==index}" @click="chooseAddr(index)">{{item.name}}</div>
          </div>
          <div class="login_conboxcon" v-if="isTrue === 0">
            <el-form  :model="logonForm" :rules="rules" ref="logonForm"  class="demo-ruleForm login_conbox">
              <el-form-item prop="sectionStr">
                <el-input v-model="logonForm.sectionStr" suffix-icon="iconfont"  placeholder="标段识别号" class="icon_imgbox_loginfather">
                  <i slot="suffix" class="icon_imgbox_login">
                    <img src="../../../static/img/shibie.png" alt="">
                  </i>
                </el-input>
              </el-form-item>
              <el-form-item prop="userName">
                <el-input v-model="logonForm.userName" suffix-icon="iconfont"  placeholder="用户名" class="icon_imgbox_loginfather">
                  <i slot="suffix" class="icon_imgbox_login">
                    <img src="../../../static/img/yonghuming.png" alt="">
                  </i>
                </el-input>
              </el-form-item>
              <el-form-item prop="passWord">
                <el-input type="password" v-model="logonForm.passWord" suffix-icon="iconfont" placeholder="密码" class="icon_imgbox_loginfather" @keyup.enter.native="logonFormbtn('logonForm')">
                  <i slot="suffix" class="icon_imgbox_login">
                    <img src="../../../static/img/mima.png" alt="">
                  </i>
                </el-input>
              </el-form-item>
            </el-form>
            <div class="login_bottombox" style="text-align: center">
              <el-button type="primary" class="login_btn" @click="logonFormbtn('logonForm')">登 录</el-button>
            </div>
          </div>
          <div class="login_conboxcon" v-if="isTrue === 1">
            <el-form  :model="logonForm" :rules="rules" ref="logonForm"  class="demo-ruleForm login_conbox">
              <el-form-item prop="sectionStr">
                <el-input v-model="logonForm.sectionStr" suffix-icon="iconfont"  placeholder="标段识别号" class="icon_imgbox_loginfather">
                  <i slot="suffix" class="icon_imgbox_login">
                    <img src="../../../static/img/shibie.png" alt="">
                  </i>
                </el-input>
              </el-form-item>
            </el-form>
            <div class="login_bottombox" style="text-align: center">
              <el-button type="primary" class="login_btn" @click="logonCaFormbtn('logonForm')">登 录</el-button>
            </div>
          </div>
        </div>
      </div>
      <div class="login_bottom_bigbox">
        <span class="login_bottom">Copyright©2014-2017 比比网络 版权所有 bibenet.com 晋ICP备14006571号-7</span>
      </div>
    </div>
</template>
<script>
import {OnLoad, SignOnClick} from '@/assets/js/signature_login'
export default {
  data () {
    return {
      // 提交时的数据
      logonForm: {
        sectionStr: '',
        userName: '',
        passWord: ''
      },
      rules: {
        sectionStr: [
          { required: true, message: '标段识别号不能为空', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '账号不能为空', trigger: 'blur' }
        ],
        passWord: [
          { required: true, message: '密码不能为空', trigger: 'blur' }
        ]
      },
      isTrue: 0, // 默认登录方式
      // 登录方式
      items: [
        {name: '工作人员 / 评委登录'},
        {name: '供应商登录'}
      ]
    }
  },
  methods: {
    // 代理/评委登录
    logonFormbtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('Login', this.logonForm).then(userType => {
            if (userType === 2) {
              this.$router.push({path: '/welcome'})
            }
          })
        } else {
          return false
        }
      })
    },
    // CA验证方法
    callBack (certDN) {
      this.logonForm.userName = certDN
      this.$store.dispatch('LoginCA', this.logonForm).then(userType => {
        if (userType === 2) {
          this.$router.push({path: '/welcome'})
        }
      })
    },
    chooseAddr (index) {
      this.isTrue = index
      this.logonForm.userName = ''
    },
    // CA登录
    logonCaFormbtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (SignOnClick(this.callBack)) {
            this.$router.push({path: '/welcome'})
          }
        } else {
          return false
        }
      })
    },
    init () {
      OnLoad()
      if (this.$route.query.bdsbh) {
        this.logonForm.sectionStr = this.$route.query.bdsbh
      }
    }
  },
  mounted () {
    this.init()
  },
  watch: {
    $route: function () {
      this.init()
    }
  }
}
</script>
<style lang="less">
  #login_bigbox{
    .login_conboxtop{
      width: 100%;
      height: 50px;
      line-height: 50px;
      text-align: center;
      font-size: 18px;
      font-weight: bold;
      color:#333333;
      background-size: 100% auto;
    }
    .login_conbox_son{
      display: inline-block;
      width: 50%;
      height: 100%;
      float: left;
      background: #efefef;
      font-size: 16px;
      font-weight: bold;
      color: #333333;
      cursor: pointer;
    }
    .active{
      background: #fff;
      background: url("../../../static/img/jianbian.png") bottom center no-repeat;
    }
  }
</style>
