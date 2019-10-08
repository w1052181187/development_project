<template>
  <div id="land_ann_lookdetailbox" class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>土地交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/land-transaction/make-result/index'}">成交结果公示备案</el-breadcrumb-item>
        <el-breadcrumb-item>成交结果公示查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="sectionForm" label-width="200px" class="demo-ruleForm">
        <!--公示信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid rgb(45, 150, 49)">公示信息</span>
          <div class="formmain">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公示名称：">
                  <span>{{sectionForm.publicityName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="公示时间：">
                  <span>{{sectionForm.publicityStartTime}}</span> 至 <span>{{sectionForm.publicityEndTime}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="公示正文：">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="true" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="fujianbox">
                <el-form-item label="附件：">
                  <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--公示信息-->
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
              prop="landAnnoMation.annoNumber"
              align="left"
              label="公告名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="methodOfTransfer"
              label="出让方式"
              align="left">
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === null">
                  <span class="statusbox">----</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 1">
                  <span class="statusbox">拍卖</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 2">
                  <span class="statusbox">挂牌</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 4">
                  <span class="statusbox">资格预审</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.methodOfTransfer === 3">
                  <span class="statusbox">资格后审</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column
              prop="ageLimit"
              align="left"
              label="出让年限"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.bidderName"
              align="left"
              label="竞得人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="salesConfirmation.strikePrice"
              align="left"
              label="成交价(万元)"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="lastUpdateDate"
              align="left"
              label="成交时间"
              show-overflow-tooltip>
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
import {stransResAnno, transResAnno} from '@/api'
export default {
  components: {
    commonFileDownload,
    editor
  },
  data () {
    return {
      sectionForm: {},
      projectData: [],
      content: '', // 公告内容
      // 文件内容
      fileObjecit: {},
      recordApprovalTable: []
    }
  },
  methods: {
    detail () {
      stransResAnno.queryOne(this.$route.params.objectId).then((res) => {
        if (res.data.transResultsAnno.fileInformations) {
          this.fileObjecit = res.data.transResultsAnno.fileInformations
        }
        this.sectionForm = res.data.transResultsAnno
        this.recordApprovalTable = res.data.transResultsAnno.approveRecords
        this.$refs.ueditor.setContent(this.sectionForm.publicityText)
        let url = {
          transResultsId: res.data.transResultsAnno.objectId
        }
        // 查询关联的项目信息
        transResAnno.detailRelation(url).then((res) => {
          this.projectData = res.data.refTranresSections.map(function (item) {
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
