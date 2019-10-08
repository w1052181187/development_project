<template>
  <div id="refundassmarignrefundbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/post-bid-business/margin-return?roledsType=2&showStatus=false' }">申请保证金退回</el-breadcrumb-item>
        <el-breadcrumb-item>修改申请</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" ref="refundedSecurityForm" label-width="150px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
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
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="公告名称：">
                  <span>{{annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--退回申请信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">退回申请信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="退回金额(万元)：" prop="bidBond">
                  <span>{{bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="退费方式：" prop="contacts">
                  <span v-if="payMethod === 0">在线缴纳</span>
                  <span v-if="payMethod === 1">保函</span>
                  <span v-if="payMethod === 2">其他</span>
                  <span v-if="payMethod === 3">支票</span>
                  <span v-if="payMethod === 4">线下汇款</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金缴费证明：" prop="" class="bitianicon">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：" prop="content">
                  <el-input type="content" v-model="refundedSecurityForm.content"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')" :loading="subStatus">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { refundedSecurity, fileInfo } from '@/api'
export default {
  components: {
    commonFileUpload,
    commonFileDownload
  },
  data () {
    return {
      refundedSecurityForm: {},
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 13 保证金）
      fileType: 13,
      projectNumber: {},
      projectName: {},
      annoNumber: {},
      sectionNumber: {},
      bidBond: {},
      // 文件上传保存的数组
      fileArry: [],
      originalId: '',
      oldFileName: '',
      fileObjecit: {},
      payMethod: Number(this.$route.query.payMethod),
      // 附件是否修改
      updateStatus: false
    }
  },
  methods: {
    list () {
      refundedSecurity.queryOne(this.$route.query.objectId).then((res) => {
        this.refundedSecurityForm = res.data.refundedSecurityOneInfo
        if (res.data.refundedSecurityOneInfo.fileInformation) {
          this.fileObjecit = res.data.refundedSecurityOneInfo.fileInformation
          this.originalId = res.data.refundedSecurityOneInfo.fileInformation.objectId
        }
        this.projectName = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.projectName
        this.projectNumber = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.projectNumber
        this.sectionNumber = res.data.refundedSecurityOneInfo.landInformation.sectionNumber
        this.annoNumber = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.annoNumber
        this.bidBond = res.data.refundedSecurityOneInfo.landInformation.bidBond
      })
    },
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileObjecit = []
      this.fileArry = file
    },
    // 提交
    submitForm () {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      this.subStatus = true
      this.refundedSecurityForm.status = 1
      refundedSecurity.save(this.refundedSecurityForm).then((res) => {
        if (this.fileArry.length !== 0) {
          this.fileArry[0].relatedCode = res.data.refundedSecurity.code
          if (this.originalId != '') {
            this.fileArry[0].objectId = this.originalId
          }
          // 将附件信息入库
          fileInfo.saveFile(this.fileArry).then((res) => {
            this.fileArry = []
          })
        }
        this.subStatus = false
        this.$router.push({path: '/lt/assignee/post-bid-business/margin-return?roledsType=2'})
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
