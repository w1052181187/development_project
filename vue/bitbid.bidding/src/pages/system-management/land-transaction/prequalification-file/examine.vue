<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>项目管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/prequalification-file/index'}">资格预审文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--资格预审文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">资格预审文件信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="资格预审文件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="备注：">
                  <span>{{sectionForm.remark}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--资格预审文件信息-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title">关联项目信息</span>
          <el-table
            :data="projectData"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="sectionNumber"
              align="left"
              label="项目编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="projectName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作" width="140" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="detailBtn(scope)">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
        <!--审批-->
        <div class="proinfobox landbigbox">
          <span class="title" style="border-left: 3px solid #0206fd">项目审批</span>
          <!--审批组件-->
          <examineApprove @approveForm="approveForm"></examineApprove>
          <!--审批组件-->
        </div>
        <!--审批-->
        <!--审批记录-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #b94aff">审批记录</span>
          <el-table
            :data="recordApprovalTable"
            border>
            <el-table-column
              prop="approvrResult"
              label="审批结果"
              align="left">
              <template slot-scope="scope">
                <div v-if="parseInt(scope.row.approvrResult) === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="parseInt(scope.row.approvrResult) === 3">
                  <span class="statusbox">审批不通过</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="approverName"
              label="操作人"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approvrDate"
              label="操作时间"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approvrOpinion"
              label="审批意见"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--审批记录-->
        <!--提交审核-->
        <div class="submit-radio">
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)">取消</el-button>
        </div>
        <!--提交审核-->
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import { landPreDoc, sApproveRecord } from '@/api'
export default {
  components: {
    commonFileDownload,
    examineApprove
  },
  data () {
    return {
      sectionForm: { },
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      projectData: [],
      // 审批记录
      recordApprovalTable: []
    }
  },
  methods: {
    // 子组件传过的审批信息
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.params.objectId
      this.checkResult.approvalType = 9
      this.checkResult.type = 2
    },
    // 提交数据
    submitForm () {
      if (this.checkResult === undefined || this.checkResult.approvrResult === undefined) {
        this.$message({
          type: 'warning',
          message: '请选择审批结果'
        })
        return false
      }
      sApproveRecord.save(this.checkResult).then((res) => {
        this.sectionForm.status = this.checkResult.approvrResult
        landPreDoc.updateDocuStatus(this.sectionForm).then((res) => {
          this.$router.push({path: '/system-management/land-transaction/prequalification-file/index'})
        })
      })
    },
    detail () {
      landPreDoc.detail(this.$route.query.objectId).then((res) => {
        if (res.data.landPreDoc.fileInformations) {
          this.fileObjecit = res.data.landPreDoc.fileInformations
        }
        if (res.data.landPreDoc.approveRecords) { // 审批记录
          this.recordApprovalTable = res.data.landPreDoc.approveRecords
        }
        this.sectionForm = res.data.landPreDoc
        let url = {
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          transDocId: res.data.landPreDoc.objectId,
          type: 2
        }
        landPreDoc.detailRelation(url).then((res) => {
          console.log(res)
          this.projectData = res.data.landInformationList.map(function (item) {
            return item.landInformation
          })
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
  @import '../../../../assets/css/land-register/common.css';
  #land_ann_lookdetailbox{
    .timesbox .el-form-item__content{
      width: 74%;
    }
    .hourbox .el-date-editor{
      width: 100%;
    }
    .fujianbox .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
