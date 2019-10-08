<template>
  <div>
    <div class="l_header">
      <div class="wrap">
        <div class="l_header_left">
          <span>您好，欢迎来到 山西广信建设项目管理有限公司</span>
        </div>
        <div class="l_header_right">
          <div class="username">
            <span :title="$store.getters.authUser.userName">{{$store.getters.authUser.userName}}</span>
            <span class="iconjian">
              <img src="../../../static/images/jiantou.png" alt="" class="jiantouimg">
            </span>
            <div class="update" @click="openPwdDialog">修改密码</div>
            <div class="layoutbtn" @click="logout">退出</div>
          </div>
          <!--<span class="message">-->
              <!--消息<em>(1)</em>-->
          <!--</span>-->
      </div>
      </div>
    </div>
    <div class="head">
      <div class="wrap">
        <div class="logo">
          <img src="../../../static/images/guangxin1.png"/>
        </div>
        <div class="head_right">
          <p>欢迎您，{{$store.getters.authUser.userName}}<span></span></p>
          <img src="../../../static/images/touxiang.png"/>
        </div>
      </div>
    </div>
    <!--修改密码弹出框-->
    <el-dialog title="修改密码" :visible.sync="updatePwdDialog" :before-close="cancleDialog"  width="500px">
      <el-form :model="updatePwdForm" :rules="rules" ref="updatePwdForm" :validate-on-rule-change="true">
        <el-form-item label="原始密码：" prop="oldPwd">
          <el-input v-model="updatePwdForm.oldPwd" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码：" prop="newPwd">
          <el-input v-model="updatePwdForm.newPwd" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="confirmPwd">
          <el-input v-model="updatePwdForm.confirmPwd" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancleDialog">取 消</el-button>
        <el-button type="primary" @click="submitDialog">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { Message } from 'element-ui'
export default {
  data () {
    // 验证旧密码
    let validateOldPwd = (rule, value, callback) => {
      value = value.trim()
      if (!value) {
        callback(new Error('请输入旧密码'))
      } else if (this.updatePwdForm.newPwd) {
        this.$refs.updatePwdForm.validateField('newPwd')
        callback()
      } else {
        callback()
      }
    }
    // 验证新密码
    let validateNewPwd = (rule, value, callback) => {
      value = value.trim()
      if (!value) {
        callback(new Error('请输入新密码'))
      } else if (this.updatePwdForm.oldPwd && value === this.updatePwdForm.oldPwd) {
        callback(new Error('新密码与原密码重复！'))
      } else if (this.updatePwdForm.confirmPwd) {
        this.$refs.updatePwdForm.validateField('confirmPwd')
        callback()
      } else {
        callback()
      }
    }
    // 验证确认密码
    let validateConfirmPwd = (rule, value, callback) => {
      value = value.trim()
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.updatePwdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      // 弹窗
      updatePwdDialog: false,
      updatePwdForm: {
        oldPwd: '',
        newPwd: '',
        confirmPwd: ''
      },
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { validator: validateOldPwd, trigger: ['blur', 'change'] },
          { min: 3, max: 20, message: '长度在 3到 20 个字符', trigger: ['blur', 'change'] }
        ],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validateNewPwd, trigger: ['blur', 'change'] },
          { min: 3, max: 20, message: '长度在 3到 20 个字符', trigger: ['blur', 'change'] }
        ],
        confirmPwd: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validateConfirmPwd, trigger: ['blur', 'change'] },
          { min: 3, max: 20, message: '长度在 3到 20 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    openPwdDialog () {
      this.updatePwdDialog = true
    },
    // 关闭修改密码弹出框
    cancleDialog () {
      this.updatePwdDialog = false
      this.updatePwdForm = {}
      this.$refs['updatePwdForm'].resetFields()
    },
    // 修改密码提交
    submitDialog () {
      this.$refs['updatePwdForm'].validate((valid) => {
        if (valid) {
          this.$store.dispatch('UpdatePwd', this.updatePwdForm).then(() => {
            this.cancleDialog()
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
          }).catch(() => {
          })
        } else {
          return false
        }
      })
    },
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    }
  }
}
</script>
<style lang="less">
  body,p,h1,h2,h3,h4,h5,h6,ul,li,ol,dl,dt,dd,input,form{
    margin:0px;
    list-style:none;/*清除小圆点*/
    padding:0px;
    font-family:Arial,"微软雅黑","黑体";
  }
    .wrap {
      width: 1200px;
      margin: 0 auto;
    }

    .wrap:after {
      content: " ";
      display: block;
      clear: both;
    }
    .l_header {
      font-size: 12px;
      color: #666666;
      height: 40px;
      line-height: 40px;
    }

    .l_header_left {
      float: left;
    }

    .l_header_left > span {
      cursor: pointer;
    }
    .l_header_right {
      float: right;
      position: relative;
    }
    .l_header_right >  span {
      margin-left: 30px;
    }

    .l_header_right >  span em{
      font-style:normal;
    }

    /* l_header end*/

    /* -------------------heading start-----------------------*/

    .head{
      width: 100%;
      height: 80px;
      overflow: hidden;
      margin: 0 auto;
      background: #3f63f6;
      position: relative;
    }
    .head .wrap{
      width: 1200px;
      height: 80px;
      overflow: hidden;
      margin:0 auto;
    }
    .head .wrap .logo{
      float: left;
      overflow: hidden;
      line-height: 80px;
    }
    .head .wrap .logo img{ vertical-align: middle;}
    .head .wrap .logo span{ color:#ffffff; font-size: 16px; font-weight: bold; margin-left:20px; display: inline-block;}

    .head_right{
      float: right;
      overflow: hidden;
      line-height:80px;
    }
    .head_right p{
      float: left;
      margin: 0 20px;
      color:#ffffff;
      font-size: 14px;
      line-height: 80px;
    }
    .head_right img{ vertical-align: middle;}
    /* new  header */

    /* ----------content start--------- */
    #content:after {
      content: " ";
      display: block;
      clear: both;
    }
    .content_bigbox{
      width: 1200px;
      margin: 18px auto 0;
      background: #fff;
    }
    .table-header{
      background: #f5f7f8;
      line-height: 48px
    }
    .breadcrumb {
      background: none;
      margin-top: 20px;
    }

    .breadcrumb > li + li:before {
      color: #CCCCCC;
      content: "> ";
      font-size: 14px;
      padding: 0 5px;
    }

    .breadcrumb > li > a {
      color: #999999;
    }

    .breadcrumb > .active {
      color: #333333;
    }
    /* ---------------------------------------- */
    /* --------------footer start----------- */
    #footer {
      width: 1200px;
      margin: 0 auto;
      font-size: 12px;
      color: #666666;
    }
    .l_footer_bottom{
      line-height: 28px;
      margin: 20px 0;
      text-align: center;
    }
    /*分页样式*/
    .el-pagination {
      white-space: nowrap;
      padding: 30px 5px!important;
      color: #303133;
      font-weight: 700;
      margin: 0 auto;
      text-align: center!important;
    }
    .username{
      display: inline-block;
      height: 40px;
      text-align: left;
      cursor: pointer;
      position: relative;
      border: 1px solid #eaedf1;
      box-sizing: border-box;
      border-bottom: none;
      border-top: none;
      text-indent: 10px;
    }
  .iconjian{
    height: 100%;
    width: 20px;
    display: inline-block;
    float: right;
    position: relative;
    margin-right: 10px;
    margin-left: 5px;
  }
  .iconjian>img{
    width: 100%;
    position: absolute;
    left: 0;
    right: 0;
    top:0;
    bottom: 0;
    margin: auto;
  }
  .update{
    width:100%;
    height: 30px;
    line-height: 30px;
    text-align: left;
    font-size: 14px;
    position: absolute;
    bottom: -25px;
    z-index: 10;
    background:#fff;
    border: 1px solid #b5b5b5;
    text-indent: 10px;
    display: none;
    margin-left: -1px;
  }
  .layoutbtn{
    width:100%;
    height: 30px;
    line-height: 30px;
    text-align: left;
    font-size: 14px;
    position: absolute;
    bottom: -50px;
    z-index: 10;
    background:#fff;
    border: 1px solid #b5b5b5;
    border-top: none;
    text-indent: 10px;
    display: none;
    margin-left: -1px;
  }
  .jiantouimg{
    transition: all .8s ease;
  }
  .username:hover{
    background:#fff;
    border: 1px solid #b5b5b5;
    box-sizing: border-box;
    border-bottom: none;
    border-top: none;
  }
  .username:hover .layoutbtn{
    border: 1px solid #b5b5b5;
    border-top: none;
    display: block;
    padding-right: 1px;
    text-indent: 10px;
  }
  .username:hover .update{
    border: 1px solid #b5b5b5;
    border-top: none;
    display: block;
    padding-right: 1px;
    text-indent: 10px;
  }
  .username:hover .jiantouimg{
    transform: rotate(180deg);
  }
</style>
