<template>
  <div id="asspostmargindetailebox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标后业务</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/assignee/post-bid-business/margin-return?roledsType=2&showStatus=false' }">申请保证金退回</el-breadcrumb-item>
        <el-breadcrumb-item>查看申请</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" label-width="100px" class="demo-ruleForm">
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
          <span class="title" style="border-left: 3px solid #2d9631">退回申请信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="退回金额(万元)：" prop="bidBond">
                  <span>{{bidBond}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="退费方式：" prop="contacts">
                  <span v-if="refundedSecurityForm.payMethod === 0">在线缴纳</span>
                  <span v-if="refundedSecurityForm.payMethod === 1">保函</span>
                  <span v-if="refundedSecurityForm.payMethod === 2">其他</span>
                  <span v-if="refundedSecurityForm.payMethod === 3">支票</span>
                  <span v-if="refundedSecurityForm.payMethod === 4">线下汇款</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="保证金缴费证明：" prop="" class="bitianicon filebigbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="说明：" prop="content">
                  <span>{{content}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--退回申请信息-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #fe8900">审批记录</span>
          <el-table
            :data="approvaltableData"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="Number(scope.row.approvrResult) === 0">
                  <span class="statusbox">不通过</span>
                </div>
                <div class="statusbigbox" v-if="Number(scope.row.approvrResult) === 1">
                  <span class="statusbox">通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="lastUpdateDate"
              label="操作时间"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left" width="700">
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { refundedSecurity } from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      refundedSecurityForm: {
        payMethod: Number(this.$route.query.payMethod)
      },
      content: '',
      projectNumber: {},
      projectName: {},
      annoNumber: {},
      sectionNumber: {},
      bidBond: {},
      approvaltableData: [],
      fileObjecit: {}
    }
  },
  methods: {
    list () {
      refundedSecurity.queryOne(this.$route.params.objectId).then((res) => {
        if (res.data.refundedSecurityOneInfo.fileInformation) {
          this.fileObjecit = res.data.refundedSecurityOneInfo.fileInformation
        }
        this.projectName = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.projectName
        this.projectNumber = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.projectNumber
        this.sectionNumber = res.data.refundedSecurityOneInfo.landInformation.sectionNumber
        this.annoNumber = res.data.refundedSecurityOneInfo.landInformation.landAnnoMation.annoNumber
        this.bidBond = res.data.refundedSecurityOneInfo.landInformation.bidBond
        this.content = res.data.refundedSecurityOneInfo.content
        this.approvaltableData = res.data.refundedSecurityOneInfo.approveRecords
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
