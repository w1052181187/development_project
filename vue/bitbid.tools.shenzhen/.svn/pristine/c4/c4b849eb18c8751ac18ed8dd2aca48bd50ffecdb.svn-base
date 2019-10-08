<template>
    <div id="Judgessumm_big">
      <!--评审-->
      <div class="zztdemo_ruleForm">
        <div class="proinfobox">
          <span class="line_bigbox"></span>
          <span class="title_bigbox">{{negName}}</span>
        </div>
        <!--汇总结果数据-->
        <div class="tableDataSupplier_box" style="padding-bottom: 20px">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px" class="demo-ruleForm">
            <el-form-item label="谈判要点：" prop="content">
              <div class="editor">
                <editor ref="essentials" class="ueditor" :content="essentialsContent" :editread="true"></editor>
              </div>
            </el-form-item>
            <el-form-item label="谈判纪要：" prop="content">
              <div class="editor">
                <editor ref="negotiation" class="ueditor" :content="negotiationBrief" :editread="true"></editor>
              </div>
            </el-form-item>
            <el-form-item label="谈判结果：">
              <el-table
                :data="tableDataNegotiation"
                border
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  width="80"
                  align="center"
                  :index='dataIndex'>
                </el-table-column>
                <el-table-column
                  prop="tenderName"
                  label="供应商名称">
                </el-table-column>
                <el-table-column
                  prop="logResult"
                  label="谈判结果">
                </el-table-column>
                <el-table-column
                  label="操作1"
                  width="280"
                  align="center">
                  <template slot-scope="scope">
                    <el-radio v-model="tableReviewRadios" :label="scope.row.tenderId" class="checkRadio" @change="tableReviewSummaryClick">查看</el-radio>
                    <el-button type="text" size="small" v-if="scope.row.offerFile && scope.row.isSubmit === 1" @click="downOfferFileBtn(scope)">下载澄清与承诺</el-button>
                    <el-button type="text" size="small" disabled v-else>下载澄清与承诺</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="评委评审情况：">
              <el-table
                :data="tableDataJudges"
                border
                style="width: 100%">
                <el-table-column
                  prop="name"
                  label="评委姓名"
                  width="140"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="result"
                  label="评审情况"
                  width="140"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="理由"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>
        </div>
        <!--汇总结果数据-->
      </div>
      <!--评审-->
    </div>
</template>
<script>
import fileDownload from '@/components/common/file-download'
import editor from '@/components/common/ueditor/ueditor.vue'
import {downloadFile} from '@/assets/js/common'
import { agencyReviewSummary } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  props: ['negName', 'frequency'],
  data () {
    return {
      tableReviewRadios: '', // 评审汇总
      essentialsContent: '', // 谈判要点
      negotiationBrief: '', // 谈判纪要
      // 谈判纪要from
      ruleForm: {},
      rules: {},
      // 自定义序号
      dataIndex: 1,
      // 谈判结果
      tableDataNegotiation: [],
      // 评委情况数据
      tableDataJudges: [],
      tenderIds: ''
    }
  },
  methods: {
    // 获取谈判要点
    preservationLists () {
      agencyReviewSummary.negotiatingPointsList(this.frequency).then((res) => {
        if (res.data.BidOpenNegotiatingPoints.pointContent !== null) {
          this.essentialsContent = res.data.BidOpenNegotiatingPoints.pointContent
          this.$refs.essentials.setContent(this.essentialsContent)
        } else {
          this.essentialsContent = ''
          this.$refs.essentials.setContent(this.essentialsContent)
        }
      })
    },
    // 获取谈判纪要
    reviewAgencySummaryLists () {
      agencyReviewSummary.reviewAgencySummaryList(this.frequency).then((res) => {
        let isPublished = res.data.BidOpenNegotiatingBrief.briefStatus === 2
        if (isPublished && res.data.BidOpenNegotiatingBrief.briefContent !== null) {
          this.negotiationBrief = res.data.BidOpenNegotiatingBrief.briefContent
          this.$refs.negotiation.setContent(this.negotiationBrief)
        } else {
          this.negotiationBrief = ''
          this.$refs.negotiation.setContent(this.negotiationBrief)
        }
      })
    },
    // 谈判结果
    reviewSummaryLists () {
      agencyReviewSummary.resultList(this.frequency).then((res) => {
        console.log(res)
        this.tableDataNegotiation = res.data.ResultList
        if (this.tableDataNegotiation.length === 0) {
          this.tableDataJudges = []
        } else {
          this.tableReviewRadios = this.tableDataNegotiation[0].tenderId
          this.tenderIds = this.tableDataNegotiation[0].tenderId
          this.tabReviewJudgesLists()
        }
      })
    },
    // 评审汇总切换
    tableReviewSummaryClick (ids) {
      this.tenderIds = ids
      this.tabReviewJudgesLists()
    },
    // 评委评审情况
    tabReviewJudgesLists () {
      agencyReviewSummary.tabReviewJudgesList(this.tenderIds, this.frequency).then((res) => {
        this.tableDataJudges = res.data.ResultList
      })
    },
    // 澄清与承诺文件
    downOfferFileBtn (scope) {
      let index1 = scope.row.offerFilePath.lastIndexOf('.')
      let index2 = scope.row.offerFilePath.length
      let suffixName = scope.row.offerFilePath.substring(index1, index2)
      downloadFile('澄清与承诺文件' + suffixName, scope.row.offerFilePath)
    }
  },
  watch: {
    frequency: function () {
      this.reviewAgencySummaryLists()
      this.preservationLists()
      this.reviewSummaryLists()
    }
  },
  mounted () {
    this.preservationLists()
    this.reviewAgencySummaryLists()
    this.reviewSummaryLists()
  }
}
</script>
<style lang="less">
  #Judgessumm_big{
    .el-radio {
      color: #606266;
      font-weight: 500;
      line-height: 1;
      cursor: pointer;
      white-space: nowrap;
      outline: 0;
      margin-right: 10px;
    }
  }
</style>
