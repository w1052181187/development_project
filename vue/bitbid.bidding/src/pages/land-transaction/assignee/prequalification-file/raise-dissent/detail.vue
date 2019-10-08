<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/prequalification-file/raise-dissent/index?roledsType=2&showStatus=false'}">提出异议</el-breadcrumb-item>
        <el-breadcrumb-item>查看质疑</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="标题：" prop="title">
                  <span>{{refundedSecurityForm.title}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.projectNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="异议与投诉内容：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item label="依据和理由：">
                <span>{{refundedSecurityForm.reason}}</span>
              </el-form-item>
            </el-row>
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-form-item label="受理状态：">
                <span v-if="refundedSecurityForm.isAccept === 1">未受理</span>
                <span v-if="refundedSecurityForm.isAccept === 2">已受理</span>
              </el-form-item>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import {question, fileInfo} from '@/api'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    return {
      refundedSecurityForm: {},
      content: '',
      // 业务类型
      fileType: 15,
      // 文件内容
      fileObjecit: {
        fileName: ''
      }
    }
  },
  methods: {
    detail () {
      question.detail(this.$route.params.objectId).then((res) => {
        this.refundedSecurityForm = res.data.question
        if (this.refundedSecurityForm.content) {
          this.content = this.refundedSecurityForm.content
          this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        }
        this.refundedSecurityForm.sectionNumber = res.data.question.landInformation.sectionNumber
        this.refundedSecurityForm.projectName = res.data.question.landInformation.landAnnoMation.projectName
        this.refundedSecurityForm.projectNumber = res.data.question.landInformation.landAnnoMation.projectNumber
        let url = {
          relatedCode: this.refundedSecurityForm.code,
          businessType: this.fileType,
          type: 2
        }
        fileInfo.get(url).then((res) => {
          this.fileObjecit = res.data.fileInformation
        })
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
