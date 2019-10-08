<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/contract/index'}">合同备案</el-breadcrumb-item>
        <el-breadcrumb-item>合同查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">项目信息</span>
          <div class="formmain">
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
                  <span>{{sectionForm.sectionNumber}}</span>
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
        <!--合同信息-->
        <div class="proinfobox">
          <span class="title">合同信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="转让方名称：">
                  <span>{{contractForm.transferorName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="受让方名称：">
                  <span>{{contractForm.publicityName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同金额(万元)：">
                  <span>{{contractForm.contractMoney}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="合同签署时间：">
                  <span>{{contractForm.contractDate}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同附件：" class="hetongbox">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="说明：">
                  <span>{{contractForm.content}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--合同信息-->
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
                <div v-if="Number(scope.row.approvrResult) === 2">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="Number(scope.row.approvrResult) === 3">
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
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import {contract} from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      contractForm: { },
      projectData: [],
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {},
      recordApprovalTable: [],
      annoNumber: '',
      projectName: '',
      projectNumber: '',
      sectionForm: {}
    }
  },
  methods: {
    list () {
      contract.queryOne(this.$route.params.objectId).then((res) => {
        this.contractForm = res.data.contractOneInfo
        if (res.data.contractOneInfo.fileInformations) {
          this.fileObjecit = res.data.contractOneInfo.fileInformations
        }
        this.sectionForm = res.data.contractOneInfo.landInformation
        this.annoNumber = res.data.contractOneInfo.landInformation.landAnnoMation.annoNumber
        this.projectNumber = res.data.contractOneInfo.landInformation.landAnnoMation.projectNumber
        this.projectName = res.data.contractOneInfo.landInformation.landAnnoMation.projectName
        this.recordApprovalTable = res.data.contractOneInfo.approveRecords
      })
    }
  },
  mounted () {
    this.list()
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
    .hetongbox .el-form-item__content .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
