<template>
  <div id="lookresindexbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=1&showStatus=false' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>成交确认</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/mr/transferor/business-confirmation/look-results-transaction?roledsType=1' }">查看成交结果公示</el-breadcrumb-item>
        <el-breadcrumb-item>查看成交结果公示</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #099cff">公示信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公示名称：" prop="publicityName">
                <span>{{ruleForm.publicityName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公示时间：" prop="sectionNumber">
                <span>{{ruleForm.publicityStartTime}}</span> 至 <span>{{ruleForm.publicityEndTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="公示正文：" prop="publicityText">
                <div class="editor">
                  <editor ref="ueditor" class="ueditor" :editread="editread" :content="publicityText"></editor>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="附件：" class="filebigbox">
                <!--文件列表展示组件-->
                <commonFileDownload :fileObjecit="fileObjecit"></commonFileDownload>
                <!--文件列表展示组件-->
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--公告内容-->
        <!--关联项目信息-->
        <div class="proinfobox">
          <span class="title" style="border-left: 3px solid #2d9631">相关项目信息</span>
          <el-table
            ref="multipleTable"
            :data="tableDataSeletion"
            tooltip-effect="dark"
            style="width: 100%"
            border
            header-cell-class-name="tabletitles">
            <el-table-column
              prop="sectionNumber"
              align="left"
              label="项目编号">
            </el-table-column>
            <el-table-column
              prop="sectionName"
              align="left"
              label="项目名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="transferType"
              align="left"
              label="出让类型"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <div class="statusbigbox" v-if="scope.row.transferType === 1">
                  <span class="statusbox">探矿权</span>
                </div>
                <div class="statusbigbox" v-if="scope.row.transferType === 2">
                  <span class="statusbox">采矿权</span>
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
              prop="salesConfirmation.dealTime"
              align="left"
              label="成交时间"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--关联项目信息-->
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
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/public-resource/mineral-rights/common/ueditor/ueditor.vue'
import commonFileDownload from '@/components/public-resource/mineral-rights/common/file-download.vue'
import {transResAnno} from '@/api'
export default {
  components: {
    editor,
    commonFileDownload
  },
  data () {
    return {
      // 富文本
      editread: true, // 富文本状态
      publicityText: '', // 富文本内容
      ruleForm: {},
      fileList: [], // 文件列表
      EnclosuretableData: [], // 表格
      appendix: false, // 弹框定义
      Enclosure: {}, // 文件上传绑定
      loading: false, // 文件上传过程中
      dataRange: [], // 时间
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      approveRecords: [], // 审批记录信息
      fileObjecit: {}
    }
  },
  methods: {
    detail () {
      transResAnno.queryOne(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.transResultsAnno
        if (res.data.transResultsAnno.fileInformations) {
          this.fileObjecit = res.data.transResultsAnno.fileInformations
        }
        this.approveRecords = res.data.transResultsAnno.approveRecords
        this.$refs.ueditor.setContent(this.ruleForm.publicityText)
        let url = {
          transResultsId: res.data.transResultsAnno.objectId,
          _t: new Date().getTime()
        }
        // 查询关联的项目信息
        transResAnno.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTranresSections.map(function (item) {
            return item.section
          })
        })
      })
    }
  },
  computed: {},
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/mineral/common.css';
</style>
