<template>
  <div id="submitregistration"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>修改报名信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <template>
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="150px" class="demo-ruleForm">
          <!--项目信息-->
          <div class="proinfobox">
            <span class="title">项目信息</span>
            <div class="formmian">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="项目名称：">
                    <span>{{ruleForm.sectionName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="项目编号：">
                    <span>{{ruleForm.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="地块编号：">
                    <span>{{ruleForm.sectionNumber}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="公告名称：">
                    <span>{{ruleForm.annoNumber}}</span>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="24">
                  <el-form-item label="受让人资格条件：">
                    <span>{{ruleForm.conditions}}</span>
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
                    <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :typeFile="typeFile"></commonFileUpload>
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
                  <el-form-item label="联系人：" prop="contacts">
                    <el-input v-model="ruleForm.contacts" placeholder="请输入联系人"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="联系电话：" prop="contactsNumber">
                    <el-input v-model="ruleForm.contactsNumber" placeholder="请输入联系电话"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="电子邮箱：" prop="email" :rules="[
                    { required: false, message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                  ]">
                    <el-input v-model="ruleForm.email" placeholder="请输入电子邮箱"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="地址：" prop="name">
                    <el-input v-model="ruleForm.address" placeholder="请输入地址"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!--联系人信息-->
          <el-form-item class="submit-radio">
            <el-button type="primary" @click="submitForm('ruleForm')" :loading="subStatus">提交</el-button>
            <el-button class="cancel" @click="$router.go(-1)" :loading="subStatus">取消</el-button>
          </el-form-item>
        </el-form>
      </template>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {isvalidFixedPhone} from '@/assets/js/validate'
import {apply, fileInfo} from '@/api'
export default {
  components: {
    commonFileUpload
  },
  data () {
    // 电话号码
    var validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入联系电话'))
      } else if (!isvalidFixedPhone(value)) {
        callback(new Error('请输入正确的11位手机号码或带区号的固话'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      // 文件上传保存的数组
      fileArry: [],
      // 业务类型
      fileType: 7,
      // 文件类型：土地
      typeFile: 2,
      subStatus: false,
      oldFileName: '',
      rules: {
        contacts: [
          { required: true, message: '请输入联系人', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1~20个字符', trigger: ['blur', 'change'] }
        ],
        contactsNumber: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ]
      },
      updateStatus: false,
      applyObjectId: '', // 报名信息id
      fileObjectId: '',
      applyForm: {} // 修改报名提交时的数据
    }
  },
  methods: {
    detail () {
      apply.applyDetail(this.$route.query.objectId).then((res) => {
        console.log(res)
        let a = {}
        let b = {}
        a = res.data.Apply
        this.applyForm = res.data.Apply
        console.log(this.applyForm)
        b.sectionNumber = res.data.Apply.landInformation.sectionNumber
        b.sectionName = res.data.Apply.landInformation.landAnnoMation.projectName
        b.sectionNumber = res.data.Apply.landInformation.landAnnoMation.projectNumber
        b.annoNumber = res.data.Apply.landInformation.landAnnoMation.annoNumber === null ? res.data.Apply.landInformation.landAnnoMation.suppleAnnoNumber : res.data.Apply.landInformation.landAnnoMation.annoNumber
        a.conditions = res.data.Apply.landInformation.prerequisites
        if (res.data.Apply.fileInformations) {
          this.oldFileName = res.data.Apply.fileInformations.fileName
          this.fileObjectId = res.data.Apply.fileInformations.objectId
        }
        this.applyObjectId = res.data.Apply.objectId
        this.ruleForm = Object.assign(a, b)
      })
    },
    // 文件上传组件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    submitForm (ruleForm) {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      this.applyForm.remark = this.ruleForm.remark
      this.applyForm.email = this.ruleForm.email
      this.applyForm.contacts = this.ruleForm.contacts
      this.applyForm.contactsNumber = this.ruleForm.contactsNumber
      this.applyForm.address = this.ruleForm.address
      this.applyForm.status = 1
      this.applyForm.acceptResults = 2
      this.subStatus = true
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          apply.applyUpdate(this.applyForm).then((res) => {
            if (this.fileArry.length !== 0) {
              this.fileArry[0].relatedCode = res.data.apply.code
              this.fileArry[0].objectId = this.applyObjectId
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            this.subStatus = false
            this.$router.push({path: '/lt/assignee/online-registration/online-registration?roledsType=2'})
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
