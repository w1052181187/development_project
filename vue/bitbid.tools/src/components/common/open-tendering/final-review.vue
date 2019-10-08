<template>
  <div class="">
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
              prop="firstPrice"
              label="首次报价"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="最终报价"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="result"
              label="结论"
              width="120"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="ranking"
              label="排序"
              width="120"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作"
              width="100"
              align="center" v-if="!roleStatus">
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
    <!--评审项目汇总结果-->
    <el-row class="project_summary_bigbox" v-if="!roleStatus">
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
          <!--汇总结果数据-->
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
  props: ['checkType', 'role'],
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      tableReviewRadios: '', // 评审汇总
      tabReviewProjectRadios: '', // 评审项目汇总结果
      tableReviewSummary: [], // 评审汇总
      tabReviewProjectSummary: [], // 评审项目汇总结果
      tabReviewJudges: [], // 评委评审情况
      tableReviewSummaryId: '', // 评审汇总id
      roleStatus: false
    }
  },
  methods: {
    // 表格点击事件(评审汇总)
    tableReviewSummaryClick (row) {
      this.tabReviewJudgesListLists(row)
    },
    // 表格点击事件(评审项目汇总结果)
    tabReviewProjectClick (row) {
      this.tabReviewJudgesLists(this.tableReviewSummaryId, row)
    },
    reviewSummaryLists () {
      let params = {
        checkType: this.$props.checkType
      }
      qualificationReview.reviewSummaryList(params).then((res) => {
        this.tableReviewSummary = res.data.ResultList
        if (this.tableReviewSummary.length !== 0) {
          let objectId = this.tableReviewSummary[0].key
          this.tableReviewRadios = this.tableReviewSummary[0].key
          this.tableReviewSummaryId = this.tableReviewSummary[0].key
          this.tabReviewJudgesListLists(objectId)
        }
      })
    },
    tabReviewJudgesListLists (objectId) {
      let url = {
        checkType: this.$props.checkType
      }
      qualificationReview.ReviewProjectSummaryList(objectId, url).then((res) => {
        this.tabReviewProjectSummary = res.data.ResultList
        let tenderId = this.tabReviewProjectSummary[0].key
        this.tabReviewProjectRadios = this.tabReviewProjectSummary[0].key
        this.tabReviewJudgesLists(this.tableReviewSummaryId, tenderId)
      })
    },
    tabReviewJudgesLists (tenderId, itemId) {
      qualificationReview.tabReviewJudgesList(tenderId, itemId).then((res) => {
        this.tabReviewJudges = res.data.ResultList
      })
    }
  },
  mounted () {
    // 组长(group)、组员(crew)、代理(agent)判断
    this.roleStatus = Object.is(this.role, 'crew')
    this.reviewSummaryLists()
  }
}
</script>
<style scoped>

</style>
