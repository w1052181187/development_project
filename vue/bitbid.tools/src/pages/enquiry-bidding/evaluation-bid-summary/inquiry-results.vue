<template>
  <div id="inquiryResults" class="content_bgibox">
    <!--汇总结果-->
    <el-row class="project_summary_bigbox">
      <el-col :span="24">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">询价结果</span>
          </div>
          <!--操作按钮-->
          <div style="margin-top: 10px" v-if="this.$route.params.role !== 'agent' && btnStatus && !btnSta">
            <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" @click="btnConfirmt">确认</el-button>
            <!--操作说明-->
            <div class="operation_bigbox">
              <div class="operation_content">
                <span>操作说明：请点击确认进入下一步。</span>
              </div>
            </div>
            <!--操作说明-->
          </div>
          <!--操作按钮-->
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
                  prop="ranking"
                  label="排序"
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
import { review } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  props: ['btnSta'],
  data () {
    return {
      // 询价结果
      inquiryResultsData: [
      ],
      btnStatus: false,
      dataIndex: 1
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      review.isView().then((res) => {
        if (res.data.isHide === '0') {
          this.btnStatus = true
        } else {
          this.btnStatus = false
        }
      })
    },
    getResult () {
      let checkType = 0
      if (this.btnSta) {
        checkType = -1
      }
      review.getInquiryResult(checkType).then((res) => {
        if (!this.btnStatus && checkType === -1) {
          this.inquiryResultsData = res.data.ResultList
        } else if (checkType === 0) {
          this.inquiryResultsData = res.data.ResultList
        } else {
          this.inquiryResultsData = []
        }
      })
    },
    // 确认按钮
    btnConfirmt () {
      review.xjConfirm().then((res) => {
        this.viewPageList()
        this.getResult()
      })
    }
  },
  mounted () {
    this.viewPageList()
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
