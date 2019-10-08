<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/tender-file/look-dissent/index?roledsType=1&showStatus=false' }">查看异议</el-breadcrumb-item>
        <el-breadcrumb-item>受理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="160px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="标题：">
                  <span>{{refundedSecurityForm.title}}</span>
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
            <el-col :span="24">
              <el-form-item label="依据和理由：" prop="remark">
                <span>{{refundedSecurityForm.reason}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" class="filebigbox">
              <el-form-item label="附件：">
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
              </el-form-item>
            </el-col>
          </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--受理信息-->
        <div class="proinfobox">
          <span class="title">受理信息</span>
          <el-row>
            <el-row>
              <el-col>
                <el-form-item label="是否受理：" class="bitianicon">
                  <template>
                    <el-radio v-model="refundedSecurityForm.isAccept" :label="acceptResultsRadio.label" v-for="(acceptResultsRadio,index) in radioAcceptResults" :key="index">{{acceptResultsRadio.name}}</el-radio>
                  </template>
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="受理意见：" prop="approvrOpinion">
                  <el-input type="textarea" v-model="refundedSecurityForm.acceptOpinion"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-row>
        </div>
        <!--受理信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {question, fileInfo} from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      refundedSecurityForm: {
        isAccept: 1
      },
      content: '',
      fileObjecit: {
        fileName: ''
      },
      radioAcceptResults: [
        {
          label: 1,
          name: '是'
        },
        {
          label: 0,
          name: '否'
        }
      ],
      rules: {
        acceptOpinion: [
          { required: true, message: '请输入受理意见', trigger: ['blur'] }
        ]
      }
    }
  },
  methods: {
    detail () {
      question.detail(this.$route.query.objectId).then((res) => {
        this.refundedSecurityForm = res.data.question
        this.refundedSecurityForm.sectionNumber = res.data.question.landInformation.sectionNumber
        this.refundedSecurityForm.projectName = res.data.question.landInformation.landAnnoMation.projectName
        this.refundedSecurityForm.projectNumber = res.data.question.landInformation.landAnnoMation.projectNumber
        if (this.refundedSecurityForm.content) {
          this.content = this.refundedSecurityForm.content
          this.$refs.ueditor.setContent(this.content) // 设置富文本内容
        }
        let url = {
          relatedCode: this.refundedSecurityForm.code,
          businessType: this.fileType,
          type: 2
        }
        fileInfo.get(url).then((res) => {
          if (res.data.fileInformation) {
            this.fileObjecit = res.data.fileInformation
          }
        })
      })
    },
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.refundedSecurityForm.acceptPeople = this.$store.getters.authUser.userName
          question.accept(this.refundedSecurityForm).then((res) => {
            this.$router.go(-1)
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
