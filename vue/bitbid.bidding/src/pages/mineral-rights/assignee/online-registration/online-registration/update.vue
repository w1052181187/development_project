<template>
  <div id="submitregistration"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/online-registration/online-registration?roledsType=2' }">网上报名</el-breadcrumb-item>
        <el-breadcrumb-item>修改报名信息</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <template>
          <el-form :model="ruleForm"  label-width="100px" class="demo-ruleForm">
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
                      <span v-if="parseInt(this.isSupplement) === 0">{{ruleForm.annoNumber}}</span>
                      <span v-if="parseInt(this.isSupplement) === 1">{{ruleForm.suppleAnnoNumber}}</span>
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
                      <span>{{ruleForm.bidderName}}</span>
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
                      <el-input v-model="ruleForm.contacts" placeholder="请输入联系人"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="联系电话：" class="bitianicon">
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
                    <el-form-item label="地址：">
                      <el-input v-model="ruleForm.address" placeholder="请输入地址"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--联系人信息-->
            <el-form-item class="submit-radio">
              <el-button type="primary" @click="submitForm()">提交</el-button>
              <el-button class="cancel" @click="cancelBtn">取消</el-button>
            </el-form-item>
          </el-form>
      </template>
    </div>
  </div>
</template>
<script>
import {apply, fileInfo} from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      ruleForm: {},
      // 受理意见 单选按钮
      radio: '1',
      // 文件上传保存的数组
      fileArry: [],
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 7.报名文件）
      fileType: 7,
      acceptResults: [],
      isSupplement: '',
      subStatus: 0,
      originalId: '',
      oldFileName: ''
    }
  },
  methods: {
    list () {
      apply.applyDetail(this.$route.params.objectId).then((res) => {
        let a = {}
        let b = {}
        a = res.data.Apply
        b.sectionName = res.data.Apply.section.sectionName
        b.sectionNumber = res.data.Apply.section.sectionNumber
        b.annoNumber = res.data.Apply.section.announcement.annoNumber
        b.suppleAnnoNumber = res.data.Apply.section.announcement.suppleAnnoNumber
        this.ruleForm = Object.assign(a, b)
        this.isSupplement = res.data.Apply.section.announcement.isSupplement
        if (res.data.Apply.fileInformations) {
          this.oldFileName = res.data.Apply.fileInformations.fileName
          this.originalId = res.data.Apply.fileInformations.objectId
        }
      })
    },
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    submitForm () {
      if (this.ruleForm.contacts === undefined || this.ruleForm.contacts === '') {
        this.$message({
          type: 'warning',
          message: '联系人不能为空'
        })
        return false
      }
      if (this.ruleForm.contactsNumber === undefined || this.ruleForm.contactsNumber === '') {
        this.$message({
          type: 'warning',
          message: '联系电话不能为空'
        })
        return false
      }
      if (!(/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^0\d{2,3}-?\d{7,8}$/.test(this.ruleForm.contactsNumber))) {
        this.$message({
          type: 'warning',
          message: '请输入正确的11位手机号码或带区号的固话'
        })
        return false
      }
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      if (this.subStatus === 1) {
        this.$message({
          type: 'warning',
          message: '不能重复提交数据'
        })
        return false
      }
      this.subStatus = 1
      let that = this
      this.ruleForm.acceptResults = 2
      this.ruleForm.acceptOpinion = ''
      apply.applyUpdate(this.ruleForm).then((res) => {
        // 将附件信息入库
        if (that.fileArry.length !== 0) {
          that.fileArry[0].relatedCode = that.ruleForm.code
          fileInfo.deleteFile(this.originalId).then((res) => {
            fileInfo.saveFile(this.fileArry).then((res) => {
              this.fileArry = []
            })
          })
        }
        this.$router.push({path: '/mr/assignee/online-registration/online-registration?roledsType=2'})
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/assignee/online-registration/online-registration?roledsType=2'})
    }
  },
  computed: {
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      let trimVersion = version[1].replace(/[ ]/g, '')
      if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
        return 1
      }
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
