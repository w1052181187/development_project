<template>
    <div class="login_bigbox">
      <div class="top_bigbox">
        <div class="logo_imgbox">
          <img src="../../../static/img/login.png" alt="">
        </div>
      </div>
      <div class="login_con_bigbox">
        <div class="login_conboxfather">
          <div class="login_conboxtop">欢迎登录</div>
          <div class="login_conboxcon">
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
          </div>
          <div class="login_bottombox" style="text-align: center">
            <el-button type="primary" class="login_btn" @click="logonFormbtn('logonForm')">登 录</el-button>
          </div>
        </div>
      </div>
      <div class="login_bottom_bigbox">
        <span class="login_bottom">Copyright©2014-2017 比比网络 版权所有 bibenet.com 晋ICP备14006571号-7</span>
      </div>
    </div>
</template>
<script>
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
      }
    }
  },
  methods: {
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
    init () {
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
<style scoped>

</style>
