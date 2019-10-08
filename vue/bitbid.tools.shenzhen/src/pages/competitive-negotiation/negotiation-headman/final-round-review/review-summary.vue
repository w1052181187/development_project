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
              prop="price"
              label="最终轮报价"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="ranking"
              label="排序"
              width="120"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--汇总结果数据-->
    </div>
    <!--评审-->
  </div>
</template>
<script>
import { qualificationReview } from '@/api'
export default {
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      isGroupStatus: false,
      isGroupData: false,
      tableReviewSummary: [] // 评审汇总
    }
  },
  methods: {
    // 评审汇总
    reviewSummaryLists () {
      let url = {
        checkType: 'HLXSH'
      }
      qualificationReview.reviewSummaryList(url).then((res) => {
        this.tableReviewSummary = res.data.ResultList
        if (this.tableReviewSummary.length === 0) {
          this.isGroupData = true
        } else {
          this.isGroupData = false
        }
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
    margin-top: 40px;
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
    .go_addbtnbox{
      width: 90px;
      height: 30px;
      background:#2c972f;
      border-color: #2c972f;
      font-size: 14px;
      color:#fff;
      padding: 0;
      margin-top: 15px;
    }
    .go_addbtnbox:focus, .go_addbtnbox:hover {
      background: #2c972f;
      border-color: #2c972f;
      color: #fff;
    }
    .tableDataSupplier_box{
      margin-top: 15px;
    }
  }
</style>
