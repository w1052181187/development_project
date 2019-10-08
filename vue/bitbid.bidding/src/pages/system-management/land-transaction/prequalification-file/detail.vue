<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/prequalification-file/index'}">资格预审文件备案</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审文件查看</el-breadcrumb-item>
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
              prop="projectNumber"
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
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileDownload from '@/components/public-resource/land-transaction/common/file-download.vue'
import { landPreDoc } from '@/api'
export default {
  components: {
    commonFileDownload
  },
  data () {
    return {
      sectionForm: {},
      relevantMassifData: [ // 相关块地信息
        {
          massifNumber: '关于安徽省铜陵市盛冲金多',
          RecordNumber: '4109917BA0017',
          Bond: '356 ',
          FloorPrice: 0,
          LandUse: '工业用地',
          EstimatedPrice: '11111'
        },
        {
          massifNumber: '关于安徽省铜陵市盛冲金多',
          RecordNumber: '4109917BA0017',
          Bond: '356 ',
          FloorPrice: 0,
          LandUse: '工业用地',
          EstimatedPrice: '11111'
        }
      ],
      isAllowedBidding: 1,
      bidEvaluationMethod: 1,
      fileOpenMode: 1,
      reviewTheWay: 1,
      reviewMethod: 1,
      bidOpeningWay: 1,
      bidEvaluationMethods: 1,
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {
        fileName: '测试数据'
      },
      projectData: [],
      recordApprovalTable: []
    }
  },
  methods: {
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
          this.projectData = res.data.refLandPreSectionList.map(function (item) {
            return item.landInformation
          })
        })
        console.log(this.projectData)
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
