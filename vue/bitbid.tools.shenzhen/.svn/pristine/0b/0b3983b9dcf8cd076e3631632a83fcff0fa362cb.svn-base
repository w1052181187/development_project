<template>
    <div class="Judges_big">
      <!--评审-->
      <div class="zztdemo_ruleForm">
        <div class="proinfobox">
          <span class="line_bigbox"></span>
          <span class="title_bigbox">{{negName}}</span>
        </div>
        <!--汇总结果数据-->
        <div class="tableDataSupplier_box" style="padding-bottom: 20px">
          <el-form :model="ruleForm" label-width="180px" class="demo-ruleForm">
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
                  label="操作"
                  width="280"
                  align="center">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" v-if="scope.row.offerFile && scope.row.isSubmit === 1" @click="downOfferFileBtn(scope)">下载澄清与承诺</el-button>
                    <el-button type="text" size="small" disabled v-else>下载澄清与承诺</el-button>
                  </template>
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
import editor from '@/components/common/ueditor/ueditor.vue'
import {agencyReviewSummary} from '@/api'
import {downloadFile} from '@/assets/js/common'
export default {
  components: {
    editor
  },
  props: ['negName', 'frequency'],
  data () {
    return {
      essentialsContent: '', // 谈判要点
      negotiationBrief: '', // 谈判纪要
      // 谈判纪要from
      ruleForm: {},
      // 自定义序号
      dataIndex: 1,
      // 谈判结果
      tableDataNegotiation: []
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
        if (res.data.BidOpenNegotiatingBrief.briefContent !== null) {
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
        this.tableDataNegotiation = res.data.ResultList
      })
    },
    // 下载报价
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
<style scoped>

</style>
