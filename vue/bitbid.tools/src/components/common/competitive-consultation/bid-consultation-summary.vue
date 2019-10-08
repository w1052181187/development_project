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
            <el-form-item label="磋商要点：" prop="content">
              <div class="editor">
                <editor ref="essentials" class="ueditor" :content="essentialsContent" :editread="true"></editor>
              </div>
            </el-form-item>
            <el-form-item label="磋商纪要：" prop="content">
              <div class="editor">
                <editor ref="negotiation" class="ueditor" :content="consultationBrief" :editread="true"></editor>
              </div>
            </el-form-item>
            <!--<el-form-item label="磋商结果：">-->
              <!--<el-table-->
                <!--:data="tableDataConsultation"-->
                <!--border-->
                <!--style="width: 100%">-->
                <!--<el-table-column-->
                  <!--type="index"-->
                  <!--label="序号"-->
                  <!--width="80"-->
                  <!--align="center"-->
                  <!--:index='dataIndex'>-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                  <!--prop="tenderName"-->
                  <!--label="供应商名称">-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                  <!--prop="logResult"-->
                  <!--label="谈判结果">-->
                  <!--<template slot-scope="scope">-->
                    <!--<span class="red_tag" v-if="scope.row.logResult === '不合格'">{{scope.row.logResult}}</span>-->
                    <!--<span v-if="scope.row.logResult === '合格'">{{scope.row.logResult}}</span>-->
                  <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                  <!--label="操作"-->
                  <!--width="280"-->
                  <!--align="center">-->
                  <!--<template slot-scope="scope">-->
                    <!--<el-button type="text" size="small" v-if="scope.row.offerFile && scope.row.isSubmit === 1" @click="downOfferFileBtn(scope)">下载澄清与承诺</el-button>-->
                    <!--<el-button type="text" size="small" disabled v-else>下载澄清与承诺</el-button>-->
                  <!--</template>-->
                <!--</el-table-column>-->
              <!--</el-table>-->
            <!--</el-form-item>-->
          </el-form>
        </div>
        <!--汇总结果数据-->
      </div>
      <!--评审-->
    </div>
</template>
<script>
import editor from '@/components/common/ueditor/ueditor.vue'
import {consultingSummary} from '@/api'
import {downloadFile} from '@/assets/js/common'
export default {
  components: {
    editor
  },
  props: ['negName', 'frequency'],
  data () {
    return {
      essentialsContent: '', // 磋商要点
      consultationBrief: '', // 磋商纪要
      // 磋商纪要from
      ruleForm: {},
      // 自定义序号
      dataIndex: 1,
      // 谈判结果
      tableDataConsultation: []
    }
  },
  methods: {
    // 获取磋商要点
    preservationLists () {
      consultingSummary.consultingPointsList(this.frequency).then((res) => {
        if (res.data.BidOpenNegotiatingPoints.pointContent !== null) {
          this.essentialsContent = res.data.BidOpenNegotiatingPoints.pointContent
          this.$refs.essentials.setContent(this.essentialsContent)
        } else {
          this.essentialsContent = ''
          this.$refs.essentials.setContent(this.essentialsContent)
        }
      })
    },
    // 获取磋商纪要
    reviewAgencySummaryLists () {
      consultingSummary.reviewAgencySummaryList(this.frequency).then((res) => {
        if (res.data.BidOpenNegotiatingBrief.briefContent !== null) {
          this.consultationBrief = res.data.BidOpenNegotiatingBrief.briefContent
          this.$refs.negotiation.setContent(this.consultationBrief)
        } else {
          this.consultationBrief = ''
          this.$refs.negotiation.setContent(this.consultationBrief)
        }
      })
    },
    // 磋商结果
    reviewSummaryLists () {
      consultingSummary.resultList(this.frequency).then((res) => {
        this.tableDataConsultation = res.data.ResultList
      })
    },
    // 下载澄清
    downOfferFileBtn (scope) {
      let index1 = scope.row.offerFilePath.lastIndexOf('.')
      let index2 = scope.row.offerFilePath.length
      let suffixName = scope.row.offerFilePath.substring(index1, index2)
      downloadFile('报价文件' + suffixName, scope.row.offerFilePath)
    },
    // 下载澄清
    downClarifyFileBtn (scope) {
      let index1 = scope.row.commitmentFilePath.lastIndexOf('.')
      let index2 = scope.row.commitmentFilePath.length
      let suffixName = scope.row.commitmentFilePath.substring(index1, index2)
      downloadFile('澄清与承诺文件' + suffixName, scope.row.commitmentFilePath)
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
