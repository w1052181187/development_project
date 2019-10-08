<template>
  <div id="quaSumm" class="content_bgibox">
    <!--评审-->
    <div class="zztdemo_ruleForm">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">评审汇总</span>
      </div>
      <!--操作按钮-->
      <div style="margin-top: 10px" v-if="btnStatus">
        <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" @click="btnConfirmt">确认</el-button>
        <el-button type="success" class="go_down_endbtnbox" size="mini" @click="btnReview">复核</el-button>
        <!--操作说明-->
        <div class="operation_bigbox">
          <div class="operation_content">
            <span>操作说明：请点击确认进入下一步，如结果提交有误，则点击复核重新提交。</span>
          </div>
        </div>
        <!--操作说明-->
      </div>
      <!--操作按钮-->
      <!--汇总结果数据-->
      <div class="tableDataSupplier_box">
        <div class="tableDataSupplier_sonbox">
          <el-table
            :data="tableReviewSummary"
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
              label="供应商名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="result"
              label="结论"
              width="120"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span class="red_tag" v-if="scope.row.result === '不合格'">{{scope.row.result}}</span>
                <span v-if="scope.row.result === '合格'">{{scope.row.result}}</span>
              </template>
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
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="result"
                  label="结果"
                  width="120"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <span class="red_tag" v-if="scope.row.result === '不合格'">{{scope.row.result}}</span>
                    <span v-if="scope.row.result === '合格'">{{scope.row.result}}</span>
                  </template>
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
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="result"
                  label="评审结果"
                  width="120"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <span class="red_tag" v-if="scope.row.result === '不合格'">{{scope.row.result}}</span>
                    <span v-if="scope.row.result === '合格'">{{scope.row.result}}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="理由"
                  width="120"
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
import { qualificationReview } from '@/api'
export default {
  data () {
    return {
      tableReviewRadios: '', // 评审汇总
      tabReviewProjectRadios: '', // 评审项目汇总结果
      // 自定义序号
      dataIndex: 1,
      btnStatus: false,
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
      let params = {
        checkType: 'ZGXSH'
      }
      qualificationReview.reviewSummaryList(params).then((res) => {
        if (res.data.status === '' || res.data.status === 'CONFIRM') {
          this.btnStatus = false
        } else if (res.data.status === 'SUBMIT') {
          this.btnStatus = true
        }
        this.tableReviewSummary = res.data.ResultList
        let objectId = this.tableReviewSummary[0].key
        this.tableReviewRadios = this.tableReviewSummary[0].key
        this.tableReviewSummaryId = this.tableReviewSummary[0].key
        this.tabReviewJudgesListLists(objectId)
      })
    },
    // 评审项目汇总结果
    tabReviewJudgesListLists (objectId) {
      qualificationReview.ReviewProjectSummaryList(objectId).then((res) => {
        this.tabReviewProjectSummary = res.data.ResultList
        let tenderId = this.tabReviewProjectSummary[0].key
        this.tabReviewProjectRadios = this.tabReviewProjectSummary[0].key
        this.tabReviewJudgesLists(this.tableReviewSummaryId, tenderId)
      })
    },
    // 评委评审情况
    tabReviewJudgesLists (tenderId, itemId) {
      qualificationReview.tabReviewJudgesList(tenderId, itemId).then((res) => {
        this.tabReviewJudges = res.data.ResultList
      })
    },
    init () {
      this.reviewSummaryLists()
      this.tableReviewSummary = []
      this.tabReviewProjectSummary = []
      this.tabReviewJudges = []
    },
    // 复核按钮
    btnReview () {
      qualificationReview.zgshReview().then((res) => {
        this.init()
      })
    },
    // 确认按钮
    btnConfirmt () {
      qualificationReview.zgshConfirm().then((res) => {
        this.reviewSummaryLists()
      })
    }
  },
  mounted () {
    this.reviewSummaryLists()
  }
}
</script>
<style lang="less">
  #quaSumm{
    padding-left:20px;
    padding-right:20px;
    padding-top: 0;
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
    /*操作按钮样式*/
    .go_down_endbtnbox{
      margin-top: 0;
      width: 103px;
      height: 25px;
      font-size: 12px;
    }
    .operation_bigbox{
      width: 100%;
      padding: 12px 30px;
      border: 1px solid #5dc35f;
      background:#f2ffea;
      box-sizing: border-box;
      margin-top: 10px;
    }
    .operation_title{
      color: #333;
      font-size: 14px;
    }
    .operation_content{
      color: #333;
      font-size: 14px;
      line-height: 2.2em;
    }
  }
</style>
