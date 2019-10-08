<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招标、投标文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/tender-file/raise-dissent/index?roledsType=2&showStatus=false'}">提出异议</el-breadcrumb-item>
        <el-breadcrumb-item>提出质疑</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="标题：" prop="title">
                  <el-input v-model="refundedSecurityForm.title" placeholder="请输入标题"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="异议与投诉内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item label="依据和理由：" class="bitianicon">
                <el-input type="textarea" v-model="refundedSecurityForm.reason"></el-input>
              </el-form-item>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="附件：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :type="typeFile"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)" :loading="subStatus">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import {question, landProject, fileInfo} from '@/api'
export default {
  components: {
    editor,
    commonFileUpload
  },
  data () {
    return {
      refundedSecurityForm: {
        sectionId: this.$route.query.sectionId
      },
      content: '',
      // 业务类型
      fileType: 15,
      // 文件类型：土地
      typeFile: 2,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: '',
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: ['blur', 'change']},
          {min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change']}
        ]
      },
      subStatus: false,
      sectionNumber: '',
      projectNumber: '',
      projectName: ''
    }
  },
  methods: {
    list () {
      landProject.detail(this.$route.query.sectionId).then(res => {
        this.sectionNumber = res.data.landInformation.sectionNumber
        this.projectNumber = res.data.landInformation.landAnnoMation.projectNumber
        this.projectName = res.data.landInformation.landAnnoMation.projectName
      })
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 提交
    submitForm (form) {
      if (this.$refs.ueditor.getContent() === '') {
        this.$message({
          type: 'warning',
          message: '异议与投诉内容不能为空'
        })
        return false
      }
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.refundedSecurityForm.questionPeople = this.$store.getters.authUser.userName
          this.refundedSecurityForm.content = this.$refs.ueditor.getContent() // 富文本内容获取
          this.subStatus = true
          question.save(this.refundedSecurityForm).then((res) => {
            if (this.fileArry.length !== 0) {
              this.fileArry[0].relatedCode = res.data.questionData.code
              // 将附件信息入库
              fileInfo.saveFile(this.fileArry).then((res) => {
                this.fileArry = []
              })
            }
            this.subStatus = false
            this.$router.go(-1)
          })
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
