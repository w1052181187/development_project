<template>
  <div id="contractdetailebox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/record-the-contract-file' }">合同备案</el-breadcrumb-item>
        <el-breadcrumb-item>合同备案审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="contractForm"  ref="contractForm" label-width="100px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目名称：">
                  <span>{{sectionData.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目编号：">
                  <span>{{sectionData.sectionNumber}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公告名称：">
                  <span>{{announcement.annoNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <!--合同信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">合同信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="转让方名称：" prop="companyName">
                  <span>{{contractForm.transferorName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让方名称：" prop="companyName">
                  <span>{{contractForm.publicityName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同金额(万元)：" prop="companyName">
                  <span>{{contractForm.contractMoney}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="合同签署时间：" prop="companyName">
                  <span>{{contractForm.contractDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同附件：" prop="companyName" class="filebigbox">
                  <!--文件列表展示组件-->
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                  <!--文件列表展示组件-->
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="说明：" prop="PayStatus">
                  <span>{{contractForm.content}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--合同信息-->
      </el-form>
        <!--项目审批-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">项目审批</span>
          <!--审批组件-->
          <examineApprove @approveForm="approveForm"></examineApprove>
          <!--审批组件-->
        </div>
        <!--项目审批-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #0206fd">审批记录</span>
          <el-table
            :data="approveRecords"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="parseInt(scope.row.approvrResult) === 1">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="parseInt(scope.row.approvrResult) === 0">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left">
            </el-table-column>
            <el-table-column
              prop="approvrDate"
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
        <!--提交审核-->
        <div class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </div>
    </div>
  </div>
</template>
<script>
import examineApprove from '@/components/system-management/examine-approve.vue'
import {scontract, sApproveRecord} from '@/api'
import commonFileDownload from '@/components/system-management/file-download.vue'
export default {
  components: {
    commonFileDownload,
    examineApprove
  },
  data () {
    return {
      // 文件
      fileObjecit: {},
      contractForm: {},
      approveRecords: [],
      sectionData: {},
      announcement: {},
      sectionForm: {}
    }
  },
  methods: {
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 4
    },
    // 内容获取
    list () {
      let objectId = this.$route.query.objectId
      scontract.queryOne(objectId).then((res) => {
        if (res.data.contractOneInfo.fileInformations) {
          this.fileObjecit = res.data.contractOneInfo.fileInformations
        }
        this.contractForm = res.data.contractOneInfo
        this.sectionData = res.data.contractOneInfo.section
        this.approveRecords = res.data.contractOneInfo.approveRecords
        this.announcement = this.sectionData.announcement === null ? {} : this.sectionData.announcement
      })
    },
    // 提交
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      sApproveRecord.save(this.checkResult).then((res) => {
        if (this.checkResult.approvrResult === 0) {
          this.sectionForm.status = 3
        } else {
          this.sectionForm.status = 2
        }
        this.sectionForm.objectId = this.$route.query.objectId
        scontract.update(this.sectionForm).then((res) => {
          this.$router.push({path: '/system-management/mineral-rights/record-the-contract-file'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/system-management/mineral-rights/record-the-contract-file'})
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
