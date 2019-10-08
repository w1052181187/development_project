<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/bid-docu/index'}">招标文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>招标文件查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--招标文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">招标文件信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="招标文件：">
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
        <!--招标文件信息-->
        <!--文件信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid rgb(45, 150, 49)">文件信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="答疑澄清时间：">
                  <span>{{sectionForm.answerStartTime}}</span> 至 <span>{{sectionForm.answerEndTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="投标有效期（天）：">
                  <span>{{sectionForm.validityOfBid}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="评标办法：">
                  <span v-if="sectionForm.bidEvaluationMethod === 1">综合评估法</span>
                  <span v-if="sectionForm.bidEvaluationMethod === 2">经评审最高价中标法</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="开标时间：">
                  <span>{{sectionForm.bidOpeningTime}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标方式：">
                  <span v-if="sectionForm.bidOpeningWay === 1">在线开标</span>
                  <span v-if="sectionForm.bidOpeningWay === 2">线下开标</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="开标地点：">
                  <span>{{sectionForm.placeOfBidOpening}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--文件信息-->
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
              label="地块编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="methodOfSupply"
              label="供应方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfSupply === 1">
                  <span class="statusbox">招拍挂出让</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfSupply === 2">
                  <span class="statusbox">协议出让</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              label="出让方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 0">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
              </template>
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
                <div v-if="Number(scope.row.approvrResult) === 3">
                  <span class="statusbox">审批通过</span>
                </div>
                <div v-if="Number(scope.row.approvrResult) === 4">
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
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
import { tdocuments } from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      sectionForm: {},
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {
        fileName: ''
      },
      fileObjecitClarify: {
        fileName: ''
      },
      projectData: [],
      // 审批记录
      recordApprovalTable: []
    }
  },
  methods: {
    detail () {
      tdocuments.detail(this.$route.params.objectId).then((res) => {
        if (res.data.transferDocuments.fileInformations) {
          this.fileObjecit = res.data.transferDocuments.fileInformations
        }
        this.sectionForm = res.data.transferDocuments
        if (res.data.transferDocuments.approveRecords12) { // 审批记录
          this.recordApprovalTable = res.data.transferDocuments.approveRecords12
        }
        let url = {
          transDocId: res.data.transferDocuments.objectId,
          type: 2
        }
        tdocuments.detailRelation(url).then((res) => {
          this.projectData = res.data.refTrandocSectionList.map(function (item) {
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
