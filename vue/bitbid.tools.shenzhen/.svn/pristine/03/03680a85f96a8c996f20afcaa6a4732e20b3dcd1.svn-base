<template>
  <div id="inquiryResults" class="content_bgibox">
    <!--汇总结果-->
    <el-row class="project_summary_bigbox">
      <el-col :span="24">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">结果汇总</span>
          </div>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="inquiryResultsData"
                border
                style="width: 60%">
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
                  label="报价"
                  width="150"
                  align="left"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="score"
                  label="综合得分"
                  width="150"
                  align="left"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="ranking"
                  label="排名"
                  width="150"
                  align="left"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!--汇总结果数据-->
        </div>
      </el-col>
    </el-row>
    <!--汇总结果-->
  </div>
</template>
<script>
import fileDownload from '@/components/common/file-download'
import editor from '@/components/common/ueditor/ueditor.vue'
import { openTenderReview } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  data () {
    return {
      // 询价结果
      inquiryResultsData: [
      ],
      dataIndex: 1
    }
  },
  methods: {
    getResult () {
      openTenderReview.getInquiryResult().then((res) => {
        console.log(res)
        this.inquiryResultsData = res.data.ResultList
      })
    }
  },
  mounted () {
    this.getResult()
  }
}
</script>
<style lang="less">
  #inquiryResults{
    margin-top: 20px;
    .tableDataSupplier_sonbox{
      width: 80%;
      padding-bottom: 30px;
    }
    .project_summary_bigbox{
      margin-top: 20px;
    }
    .project_summary_box{
      width: 100%;
      min-height: 100px;
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
