<template>
  <div id="forQuaSummNegHeadman" class="content_bgibox">
    <!--评审-->
    <div class="zztdemo_ruleForm">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">评审汇总</span>
      </div>
      <!--操作按钮-->
      <div style="margin-top: 10px" v-if="btnStatus">
        <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" @click="btnConfirmt">确认</el-button>
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
      tableReviewSummary: [] // 评审汇总
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      qualificationReview.isView().then((res) => {
        if (res.data.isHave === '1') {
          this.btnStatus = true
        } else if (res.data.isHave === '0') {
          this.btnStatus = false
        }
      })
    },
    // 评审汇总
    reviewSummaryLists () {
      let url = {
        checkType: 'HLXSH'
      }
      qualificationReview.reviewSummaryList(url).then((res) => {
        this.tableReviewSummary = res.data.ResultList
        if (this.tableReviewSummary.length === 0) {
          this.btnStatus = false
        }
      })
    },
    // 确认按钮
    btnConfirmt () {
      qualificationReview.hlxshConfirm().then((res) => {
        this.reviewSummaryLists()
        this.viewPageList()
      })
    }
  },
  mounted () {
    this.reviewSummaryLists()
    this.viewPageList()
  }
}
</script>
<style lang="less">
  #forQuaSummNegHeadman{
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
