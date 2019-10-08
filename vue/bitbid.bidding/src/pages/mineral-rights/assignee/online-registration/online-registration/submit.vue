<template>
  <div id="submitregistration"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>提交报名信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <template>
          <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <!--项目信息-->
            <div class="proinfobox">
              <span class="title">项目信息</span>
              <div class="formmian">
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="项目名称：" prop="name">
                      <span>{{ruleForm.sectionName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="项目编号：" prop="name">
                      <span>{{ruleForm.sectionNumber}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="公告名称：" prop="name">
                      <span>{{ruleForm.annoNumber}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--项目信息-->
            <!--报名文件信息-->
            <div class="proinfobox">
              <span class="title" style="border-left: 3px solid #2d9631">报名文件信息</span>
              <div class="formmian">
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="竞买人名称：" prop="name">
                      <span>{{$store.getters.authUser.userName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="统一社会信用代码：" prop="name">
                      <span>{{$store.getters.authUser.uscCode}}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="报名文件：" prop="name" class="filetitle bitianicon">
                      <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                    </el-form-item>
                  </el-col>
                  <el-col>
                    <el-form-item label="备注：" prop="name">
                      <el-input type="textarea" v-model="ruleForm.remark"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--报名文件信息-->
            <!--联系人信息-->
            <div class="proinfobox">
              <span class="title" style="border-left: 3px solid #099cff">联系人信息</span>
              <div class="formmain">
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="联系人：" class="bitianicon">
                      <el-input v-model="apply.contacts" placeholder="请输入联系人"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系电话：" class="bitianicon">
                      <el-input v-model="apply.contactsNumber" placeholder="请输入联系电话"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="电子邮箱：" prop="applyEmail" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                      <el-input v-model="ruleForm.applyEmail" placeholder="请输入电子邮箱"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8">
                    <el-form-item label="地址：" prop="name">
                      <el-input v-model="apply.address" placeholder="请输入地址"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--联系人信息-->
            <el-form-item class="submit-radio">
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button class="cancel" @click="cancelBtn">取消</el-button>
            </el-form-item>
          </el-form>
      </template>
    </div>
  </div>
</template>
<script>
import {apply, fileInfo, prProject} from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      fileList: [], // 文件列表
      appendix: false, // 弹框定义
      Enclosure: {}, // 文件上传绑定
      upLoad: 'https://jsonplaceholder.typicode.com/posts/', // 上传地址
      loading: false, // 文件上传过程中
      EnclosuretableData: [],
      ruleForm: {},
      apply: {},
      // 受理意见 单选按钮
      radio: '1',
      // 文件上传保存的数组
      fileArry: [],
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 7.报名文件）
      fileType: 7,
      subStatus: 0,
      oldFileName: ''
    }
  },
  methods: {
    list () {
      prProject.queryOne(this.$route.query.ObjectId).then((res) => {
        let a = {}
        let b = {}
        let c = {}
        a = res.data.sectionOneInfo
        b = res.data.sectionOneInfo.section
        c.annoNumber = res.data.sectionOneInfo.announcement.annoNumber
        c.code = res.data.sectionOneInfo.applys === null ? '' : res.data.sectionOneInfo.applys.code
        c.applyEmail = res.data.sectionOneInfo.applys === null ? '' : res.data.sectionOneInfo.applys.email
        // Object.assign() 方法用于将所有属性的值从一个或多个源对象复制到目标对象
        this.ruleForm = Object.assign(a, b, c)
      })
    },
    upLoadFile (file) {
      this.fileArry = file
    },
    submitForm (ruleForm) {
      this.apply.sectionId = this.$route.query.ObjectId
      this.apply.status = 1
      this.apply.assigneeId = this.$store.getters.authUser.userId
      this.apply.bidderName = this.$store.getters.authUser.userName
      this.apply.remark = this.ruleForm.remark
      this.apply.email = this.ruleForm.applyEmail
      if (this.apply.contacts === undefined) {
        this.$message({
          type: 'warning',
          message: '联系人不能为空'
        })
        return false
      }
      if (this.apply.contactsNumber === undefined) {
        this.$message({
          type: 'warning',
          message: '联系电话不能为空'
        })
        return false
      }
      if (!(/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^0\d{2,3}-?\d{7,8}$/.test(this.apply.contactsNumber))) {
        this.$message({
          type: 'warning',
          message: '请输入正确的11位手机号码或带区号的固话'
        })
        return false
      }
      if (this.fileArry.length === 0) {
        this.$message({
          type: 'warning',
          message: '请上传文件'
        })
        return false
      }
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          apply.applyAdd(this.apply).then((res) => {
            if (this.fileArry.length !== 0) {
              this.fileArry[0].relatedCode = res.data.apply.code
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            this.$router.push({path: '/mr/assignee/online-registration/online-registration?roledsType=2'})
          })
        } else {
          return false
        }
      })
    },
    cancelBtn () {
      this.$router.push({path: '/mr/assignee/online-registration/online-registration/choose-project?roledsType=2'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
