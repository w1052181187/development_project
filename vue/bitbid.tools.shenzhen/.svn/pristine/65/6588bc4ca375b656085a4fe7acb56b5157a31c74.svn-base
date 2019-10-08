<template>
  <div id="forQuaSummNegHeadman" class="content_bgibox">
    <!--评审-->
    <div class="zztdemo_ruleForm">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">评审汇总</span>
      </div>
      <!--汇总结果数据-->
      <div class="tableDataSupplier_box">
        <div class="tableDataSupplier_sonbox">
          <el-table
            :data="tableReviewSummary"
            border
            style="width: 100%">
            <el-table-column
              prop="name"
              label="供应商名称"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="firstPrice"
              label="首轮报价"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="最终轮报价"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="score"
              label="综合得分"
              width="120"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="ranking"
              label="排名"
              width="120"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作"
              width="100"
              align="center">
              <template slot-scope="scope">
                <el-radio v-model="tableReviewRadios" :label="scope.row.key" class="checkRadio" @change="tableReviewSummaryClick">查看</el-radio>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--汇总结果数据-->
    </div>
    <!--评审-->
    <!--评审因素结果汇总-->
    <el-row class="project_summary_bigbox">
      <el-col :span="12" class="project_summary_fatherboxleft">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">评审因素结果汇总</span>
          </div>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="tabReviewProjectSummary"
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
                  prop="name"
                  label="评审因素"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="score"
                  label="得分"
                  width="120"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="100"
                  align="center">
                  <template slot-scope="scope">
                    <el-radio v-model="tabReviewProjectRadios" :label="scope.row.key" class="checkRadio" @change="tabReviewProjectClick">查看</el-radio>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!--评审因素结果汇总-->
        </div>
      </el-col>
      <el-col :span="12" class="project_summary_fatherboxright">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">评委评审情况</span>
          </div>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="tabReviewJudges"
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
                  prop="name"
                  label="评委姓名"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="score"
                  label="打分"
                  width="120"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!--汇总结果数据-->
        </div>
      </el-col>
    </el-row>
    <!--评审项目汇总结果-->
  </div>
</template>
<script>
import { consultingReview } from '@/api'
export default {
  data () {
    return {
      tableReviewRadios: '', // 评审汇总
      tabReviewProjectRadios: '', // 评审项目汇总结果
      // 自定义序号
      dataIndex: 1,
      tableReviewSummary: [], // 评审汇总
      tabReviewProjectSummary: [], // 评审项目汇总结果
      tabReviewJudges: [], // 评委评审情况
      tableReviewSummaryId: '' // 评审汇总id
    }
  },
  methods: {
    // 表格点击事件(评审汇总)
    tableReviewSummaryClick (row) {
      this.tableReviewSummaryId = row
      this.tabReviewJudgesListLists(row)
    },
    // 表格点击事件(评审项目汇总结果)
    tabReviewProjectClick (row) {
      this.tabReviewJudgesLists(this.tableReviewSummaryId, row)
    },
    // 评审汇总
    reviewSummaryLists () {
      let url = {
        checkType: 'HLXSH'
      }
      consultingReview.reviewSummaryList(url).then((res) => {
        this.tableReviewSummary = res.data.ResultList
        let objectId = this.tableReviewSummary[0].key
        this.tableReviewRadios = this.tableReviewSummary[0].key
        this.tableReviewSummaryId = this.tableReviewSummary[0].key
        this.tabReviewJudgesListLists(objectId)
      })
    },
    // 评审项目汇总结果
    tabReviewJudgesListLists (objectId) {
      let url = {
        checkType: 'HLXSH'
      }
      consultingReview.ReviewProjectSummaryList(objectId, url).then((res) => {
        this.tabReviewProjectSummary = res.data.ResultList
        let tenderId = this.tabReviewProjectSummary[0].key
        this.tabReviewProjectRadios = this.tabReviewProjectSummary[0].key
        this.tabReviewJudgesLists(this.tableReviewSummaryId, tenderId)
      })
    },
    // 评委评审情况
    tabReviewJudgesLists (tenderId, itemId) {
      let url = {
        checkType: 'HLXSH'
      }
      consultingReview.tabReviewJudgesList(tenderId, itemId, url).then((res) => {
        this.tabReviewJudges = res.data.ResultList
      })
    }
  },
  mounted () {
    this.reviewSummaryLists()
  }
}
</script>
<style lang="less">
  #forQuaSummNegHeadman{
    padding-left:20px;
    padding-right:20px;
    box-sizing: border-box;
    .tableDataSupplier_sonbox{
      width: 48%;
      padding-bottom: 30px;
    }
    .project_summary_fatherboxleft{
      padding-right: 10px;
      box-sizing: border-box;
    }
    .project_summary_fatherboxright{
      padding-left: 10px;
      box-sizing: border-box;
    }
    .project_summary_bigbox{
      margin-top: 20px;
    }
    .project_summary_box{
      width: 100%;
      min-height: 100px;
      padding: 20px;
      box-sizing: border-box;
      box-shadow: 0 0 10px rgba(0,0,0,0.2);
    }
    .el-table .warning-row {
      background: #eceffe;
    }
    // 表格hover事件
    .el-table--enable-row-hover .el-table__body tr:hover>td{
      background-color: transparent !important;
    }
  }
</style>
