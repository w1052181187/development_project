<template>
  <div id="refundassmarignrefundbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/assignee/post-bid-business/margin-return?roledsType=2' }">申请保证金退回</el-breadcrumb-item>
        <el-breadcrumb-item>修改保证金</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" ref="refundedSecurityForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：" prop="sectionName">
                  <span>{{sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：" prop="sectionNumber">
                  <span>{{sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：" prop="annoNumber">
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
                <el-form-item label="退费方式：">
                  <span v-if="Number(this.$route.query.payMethod) === 0">在线缴纳</span>
                  <span v-if="Number(this.$route.query.payMethod) === 1">保函</span>
                  <span v-if="Number(this.$route.query.payMethod) === 2">其他</span>
                  <span v-if="Number(this.$route.query.payMethod) === 3">支票</span>
                  <span v-if="Number(this.$route.query.payMethod) === 4">线下汇款</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金缴费证明：" prop="" class="bitianicon">
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
          <el-button type="primary" @click="submitForm('refundedSecurityForm')">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import { refundedSecurity, fileInfo } from '@/api'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      refundedSecurityForm: {},
      sectionName: '',
      annoNumber: '',
      sectionNumber: '',
      bidBond: '',
      fileList: [],
      // 业务类型 （1.项目 2.公告 3.出让文件 4.澄清修改文件 13 保证金）
      fileType: 13,
      // 文件上传保存的数组
      fileArry: [],
      // 附件是否修改
      updateStatus: false,
      originalId: '',
      oldFileName: ''
    }
  },
  methods: {
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.updateStatus = true
      this.fileArry = file
    },
    list () {
      refundedSecurity.queryOne(this.$route.query.objectId).then((res) => {
        this.sectionName = res.data.refundedSecurityOneInfo.section.sectionName
        this.sectionNumber = res.data.refundedSecurityOneInfo.section.sectionNumber
        this.annoNumber = res.data.refundedSecurityOneInfo.section.announcement.annoNumber
        this.refundedSecurityForm.content = res.data.refundedSecurityOneInfo.content
        this.refundedSecurityForm.code = res.data.refundedSecurityOneInfo.code
        this.bidBond = res.data.refundedSecurityOneInfo.section.bidBond
        if (res.data.refundedSecurityOneInfo.fileInformation) {
          this.oldFileName = res.data.refundedSecurityOneInfo.fileInformation.fileName
          this.originalId = res.data.refundedSecurityOneInfo.fileInformation.objectId
        }
      })
    },
    // 提交
    submitForm (refundedSecurityForm) {
      if (this.updateStatus) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传文件'
          })
          return false
        }
      }
      this.refundedSecurityForm.status = 1
      this.refundedSecurityForm.sectionId = this.$route.query.sectionId
      this.refundedSecurityForm.assigneeId = this.$route.query.assigneeId
      this.refundedSecurityForm.objectId = this.$route.query.objectId
      let that = this
      this.$refs[refundedSecurityForm].validate((valid) => {
        if (valid) {
          refundedSecurity.update(this.refundedSecurityForm).then((res) => {
            // 将附件信息入库
            if (that.fileArry.length !== 0) {
              that.fileArry[0].relatedCode = that.refundedSecurityForm.code
              fileInfo.deleteFile(this.originalId).then((res) => {
                fileInfo.saveFile(this.fileArry).then((res) => {
                  this.fileArry = []
                })
              })
            }
            this.$router.push({path: '/mr/assignee/post-bid-business/margin-return?roledsType=2'})
          })
        } else {
          return false
        }
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/mr/assignee/post-bid-business/margin-return?roledsType=2'})
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
