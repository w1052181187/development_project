<template>
<div id="header" class="headerbigbox">
  <el-row>
    <el-col :span="3">
      <div class="logobigbox logo">
        <img src="../../static/images/logo.png" alt="logo">
      </div>
    </el-col>
    <el-col :span="21">
      <el-dropdown trigger="click" class="setbigbox">
        <span class="el-dropdown-link">
          <span :title="$store.getters.authUser.userName">{{$store.getters.authUser.userName}}</span>
        <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <!--<div @click="indexBtn">个人中心</div>-->
          </el-dropdown-item>
          <el-dropdown-item>
            <div @click="pwdClick">修改密码</div>
          </el-dropdown-item>
          <!--<el-dropdown-item>帐号设置</el-dropdown-item>-->
          <!--<el-dropdown-item>意见反馈</el-dropdown-item>-->
          <el-dropdown-item>
            <div @click="logout">退出帐号</div>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <div>
        <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%" center @close="handleClose">
          <el-form :model="ruleForm2" label-width="100px" :rules="rules2" ref="ruleForm2">
            <el-form-item label="旧密码" prop="oldPass">
              <el-input type="password" v-model="ruleForm2.oldPass"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="pass">
              <el-input type="password" v-model="ruleForm2.pass"></el-input>
            </el-form-item>
            <el-form-item label="确认新密码" prop="checkPass">
              <el-input type="password" v-model="ruleForm2.checkPass"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="pwdOkClick">确 定</el-button>
            <el-button @click="handleClose">取 消</el-button>
          </div>
        </el-dialog>
      </div>
      <div class="headerimgbox">
        <img src="../../static/images/public/header.png" alt="">
      </div>
      <div class="bfunctionbox">
        <div class="bfunctionimg">
          <div class="btnimg_box_lianjie"></div>
        </div>
        <div class="bfunctionimg">
          <div class="btnimg_box_tishi"></div>
        </div>
        <div class="bfunctionimg">
          <div class="btnimg_box_youjian"></div>
        </div>
      </div>
    </el-col>
  </el-row>
</div>
</template>

<script>
import {login} from '@/api'
import { Message } from 'element-ui'
export default {
  name: 'commonhead',
  data () {
    var validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入旧密码'))
      } else {
        callback()
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'))
      } else {
        if (this.ruleForm2.oldPass === value) {
          callback(new Error('新密码不能与旧密码一致'))
        }
        if (this.ruleForm2.checkPass !== '') {
          this.$refs.ruleForm2.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入新密码'))
      } else if (value !== this.ruleForm2.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      ruleForm2: {
        pass: '',
        oldPass: '',
        checkPass: ''
      },
      rules2: {
        oldPass: [
          { required: true, message: '请输旧新密码', trigger: 'blur' },
          {validator: validateOldPass, trigger: 'blur', required: true},
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        pass: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          {validator: validatePass, trigger: 'blur', required: true},
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        checkPass: [
          { required: true, message: '请重新输入密码', trigger: 'blur' },
          {validator: validatePass2, trigger: 'blur', required: true},
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    handleClose () {
      this.$refs['ruleForm2'].resetFields()
      this.dialogFormVisible = false
    },
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({
          path: '/login'
        })
      })
    },
    pwdClick () {
      this.dialogFormVisible = true
    },
    pwdOkClick () {
      this.$refs['ruleForm2'].validate((valid) => {
        if (valid) {
          let params = {
            oldPass: this.ruleForm2.oldPass,
            newPass: this.ruleForm2.pass,
            checkPass: this.ruleForm2.checkPass
          }
          login.updatePwd(params).then((res) => {
            if (res.data.resCode === '0000') {
              this.dialogFormVisible = false
              // 重新登录
              Message({
                showClose: true,
                message: '密码修改成功，将重新登录',
                type: 'success',
                duration: 5 * 1000
              })
              let _this = this
              setTimeout(() => {
                _this.logout()
              }, 3000)
            } else {
              return false
            }
          })
        } else {
          this.$message({
            type: 'warning',
            message: '请检查密码是否填写正确'
          })
          return false
        }
      })
    }
    // indexBtn () {
    // this.$router.push({path: '/main'})
    // }
  },
  mounted () {
  }
}
</script>

<style lang="less">
.v-modal {
  background: transparent !important;
  z-index: 98!important;
}

.el-dialog__wrapper {
  background: rgba(0, 0, 0, .3) !important;
}
.logobigbox {
  width: 100%;
  line-height: 50px;
  font-size: 18px;
  color: #fff;
  float: left;
  background: rgb(25, 35, 60);
}
.logo>img{
  width: 80%;
  vertical-align: middle;
}
#app {
  .headerbigbox {
    display: inline-block;
    width: 100%;
    height: 50px;
    background: #ffffff;
    padding: 0 20px 0 0;
    box-sizing: border-box;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
    position: fixed;
    top: 0;
    right: 0;
    z-index: 99;
  }
  .setbigbox {
    height: 100%;
    display: inline-block;
    float: right;
    line-height: 50px;
    font-size: 14px;
    cursor: pointer;
  }
  .headerimgbox {
    width: 30px;
    height: 100%;
    float: right;
    margin-right: 25px;
    position: relative;
  }
  .headerimgbox>img {
    width: 30px;
    position: absolute;
    left: 0;
    right: 0;
    top: 45px;
    bottom: 0;
    margin: auto;
    border-radius: 50%;
    cursor: pointer;
  }
  .bfunctionbox {
    height: 100%;
    float: right;
    margin-right: 25px;
  }
  .bfunctionimg {
    display: inline-block;
    width: 70px;
    height: 100%;
    vertical-align: top;
    cursor: pointer;
    transition: all .8s ease;
    position: relative;
  }
  .btnimg_box_tishi {
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    /*background: url("../../../static/images/systemmanagement/tishi.png")no-repeat;*/
    background-size: auto 25px;
  }
  .btnimg_box_youjian {
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    /*background: url("../../../static/images/systemmanagement/youjian.png")no-repeat;*/
    background-size: 25px auto;
  }
  .btnimg_box_lianjie {
    width: 25px;
    height: 25px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    /*background: url("../../../static/images/systemmanagement/lianjian.png")no-repeat;*/
    background-size: 25px auto;
  }
  .bfunctionimg:hover {
    background: #eeeeee;
  }
  .bfunctionimg:hover .btnimg_box_tishi {
    /*background: url("../../../static/images/systemmanagement/tishi_l.png")no-repeat;*/
    background-size: auto 25px;
  }
  .bfunctionimg:hover .btnimg_box_youjian {
    /*background: url("../../../static/images/systemmanagement/youjian_l.png")no-repeat;*/
    background-size: 25px auto;
  }
  .bfunctionimg:hover .btnimg_box_lianjie {
    /*background: url("../../../static/images/systemmanagement/lianjie.png")no-repeat;*/
    background-size: 25px auto;
  }
}
</style>
