<template>
  <div id="lookresindexbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>矿权交易备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/mineral-rights/result-disclosure-file' }">成交结果公示备案</el-breadcrumb-item>
        <el-breadcrumb-item>出让公告公示审批</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <!--公告内容-->
        <div class="proinfobox">
          <span class="title">公示信息</span>
          <el-row>
            <el-col :span="12">
              <el-form-item label="公示名称：" prop="sectionName">
                <span>{{ruleForm.publicityName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="公示时间：" prop="sectionNumber">
                <span>{{ruleForm.publicityStartTime}}</span> 至 <span>{{ruleForm.publicityEndTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item label="公示正文：" prop="sectionName">
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
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm()">提交</el-button>
          <el-button class="cancel" @click="cancelBtn">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/system-management/ueditor/ueditor.vue'
import commonFileDownload from '@/components/system-management/file-download.vue'
import examineApprove from '@/components/system-management/examine-approve.vue'
import {stransResAnno, sApproveRecord} from '@/api'

export default {
  components: {
    editor,
    commonFileDownload,
    examineApprove
  },
  data () {
    return {
      // 富文本
      editread: true, // 富文本状态
      publicityText: '', // 富文本内容
      ruleForm: {},
      dataRange: [], // 时间
      // 关联相关信息
      tableDataSeletion: [],
      multipleSelection: [], // 关联信息提交时的数组
      approveRecords: [], // 审批记录信息
      // 文件
      fileObjecit: {}
    }
  },
  methods: {
    // 内容获取
    detail () {
      stransResAnno.queryOne(this.$route.query.objectId).then((res) => {
        if (res.data.transResultsAnno.fileInformations) {
          this.fileObjecit = res.data.transResultsAnno.fileInformations
        }
        this.ruleForm = res.data.transResultsAnno
        this.$refs.ueditor.setContent(this.ruleForm.publicityText)
        this.approveRecords = res.data.transResultsAnno.approveRecords
        let url = {
          transResultsId: res.data.transResultsAnno.objectId,
          _t: new Date().getTime()
        }
        // 查询关联的项目信息
        stransResAnno.detailRelation(url).then((res) => {
          this.tableDataSeletion = res.data.refTranresSections.map(function (item) {
            return item.section
          })
        })
      })
    },
    approveForm (val) {
      this.checkResult = val
      this.checkResult.approvalId = this.$route.query.objectId
      this.checkResult.approvalType = 3
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
        if (this.checkResult.approvrResult === 0) {
          this.ruleForm.status = 3
        } else {
          this.ruleForm.status = 2
        }
        this.ruleForm.objectId = this.$route.query.objectId
        stransResAnno.update(this.ruleForm).then((res) => {
          this.$router.push({path: '/system-management/mineral-rights/result-disclosure-file'})
        })
      })
    },
    // 取消
    cancelBtn () {
      this.$router.push({path: '/system-management/mineral-rights/result-disclosure-file'})
    }
  },
  computed: {},
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
