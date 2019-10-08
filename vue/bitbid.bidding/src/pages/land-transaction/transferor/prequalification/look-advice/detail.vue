<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification/look-advice/index?roledsType=1&showStatus=false' }">查看资格预审结果通知书</el-breadcrumb-item>
        <el-breadcrumb-item>查看通知书</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" label-width="200px" class="demo-ruleForm">
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
                <el-form-item label="公告名称：">
                  <span>{{refundedSecurityForm.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--报告信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">报告信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="资格审查报告：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交人：">
                  <span>{{refundedSecurityForm.subName}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="提交时间：">
                  <span>{{refundedSecurityForm.subTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--报告信息-->
        <!--时间信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">时间信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="招标文件获取时间：" class="timesbox">
                  <span>{{refundedSecurityForm.biddingDocStartTime}}</span> 至 <span>{{refundedSecurityForm.biddingDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="答疑澄清时间：" class="timesbox">
                  <span>{{refundedSecurityForm.answeringStartTime}}</span> 至 <span>{{refundedSecurityForm.answeringEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="递交投标文件截止时间：" prop="tenderDocEndTime" class="hourbox">
                  <span>{{refundedSecurityForm.tenderDocEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标文件递交方法：" prop="tenderDocEndMethod">
                  <span>{{refundedSecurityForm.tenderDocEndMethod}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期（天）：" prop="validityOfBid">
                  <span>{{refundedSecurityForm.validityOfBid}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="评标办法：" prop="bidEvaluationMethod">
                  <span v-if="refundedSecurityForm.bidEvaluationMethod === 1">综合评估法</span>
                  <span v-if="refundedSecurityForm.bidEvaluationMethod === 2">经评审最高价中标法</span>
                </el-form-item>
              </el-col>
              <el-col :span="8" class="hourbox">
                <el-form-item label="开标时间：" prop="bidOpeningTime">
                  <span>{{refundedSecurityForm.bidOpeningTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：" prop="placeOfBidOpening">
                  <span>{{refundedSecurityForm.placeOfBidOpening}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标方式：" prop="bidOpeningWay">
                  <span v-if="refundedSecurityForm.bidOpeningWay === 1">在线开标</span>
                  <span v-if="refundedSecurityForm.bidOpeningWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标方式：" prop="bidEvaluationMethods">
                  <span v-if="refundedSecurityForm.bidEvaluationMethods === 1">在线评标</span>
                  <span v-if="refundedSecurityForm.bidEvaluationMethods === 2">线下评标</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--时间信息-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      refundedSecurityForm: {},
      content: '',
      fileObjecit: {
        fileName: '测试数据'
      }
    }
  },
  methods: {
    list () {},
    // 发送
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          console.log(this.refundedSecurityForm)
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
  #marginappaddbox{
    .timesbox .el-form-item__content{
      width: 70%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
  }
</style>
