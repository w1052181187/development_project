<template>
  <div id="negRevSum" class="content_bgibox">
    <!--汇总结果-->
    <el-row class="project_summary_bigbox">
      <el-col :span="24" class="project_summary_fatherboxleft">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">磋商汇总</span>
          </div>
          <el-button type="success" class="go_down_endbtnbox filedownbtn_shen" size="mini" @click="btnConfirmt" v-if="sendSta && tableDataSupplierNeg.length !== 0">发送</el-button>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="tableDataSupplierNeg"
                border
                style="width: 100%">
                <el-table-column
                  prop="logIndex"
                  label="磋商顺序"
                  width="150"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="tenderName"
                  label="供应商名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="logPrice"
                  label="报价"
                  width="270"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="isPass"
                  label="是否进入下一阶段"
                  width="270"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <div class="statusbigbox" v-if="scope.row.isPass === 1">
                      <span class="statusbox">是</span>
                    </div>
                    <div class="statusbigbox" v-if="scope.row.isPass === 0">
                      <span class="statusbox">否</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="100"
                  align="center">
                  <template slot-scope="scope">
                    <el-radio v-model="tabReviewProjectRadios" :label="scope.row.tenderId" class="checkRadio" @change="tabReviewProjectClick">查看</el-radio>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <!--汇总结果数据-->
        </div>
      </el-col>
      <el-col :span="24" class="project_summary_fatherboxright">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">评委评审情况</span>
          </div>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="tableDataSupplier"
                border
                style="width: 100%">
                <el-table-column
                  type="index"
                  label="序号"
                  width="150"
                  align="center"
                  :index='dataIndex'>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="评委姓名"
                  width="200"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="result"
                  label="评审结果"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="理由"
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
import { consultingPoints } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  data () {
    return {
      // 磋商结果汇总
      tableDataSupplierNeg: [],
      // 评委评审情况
      tableDataSupplier: [],
      resultListId: '',
      tabReviewProjectRadios: '',
      dataIndex: 1,
      sendSta: false // 是否发送
    }
  },
  methods: {
    // 磋商结果汇总
    consultationHeadSummaryLists () {
      this.tableDataSupplier = []
      consultingPoints.consultingHeadSummaryList(this.$route.params.num).then((res) => {
        if (res.data.isSend === '1') {
          this.sendSta = false
        } else {
          this.sendSta = true
        }
        this.tableDataSupplierNeg = res.data.ResultList
        if (this.tableDataSupplierNeg.length !== 0) {
          this.tabReviewProjectRadios = this.tableDataSupplierNeg[0].tenderId
          this.resultListId = this.tableDataSupplierNeg[0].tenderId
          this.JudgesLists()
        }
      })
    },
    // 表格点击事件(评审汇总)
    tabReviewProjectClick (id) {
      this.resultListId = id
      this.JudgesLists()
    },
    // 评委评审情况
    JudgesLists () {
      consultingPoints.JudgesList(this.resultListId, this.$route.params.num).then((res) => {
        this.tableDataSupplier = res.data.ResultList
      })
    },
    // 发送
    btnConfirmt () {
      consultingPoints.sendStatus(this.$route.params.num).then((res) => {
        this.consultationHeadSummaryLists()
      })
    }
  },
  watch: {
    '$route': 'consultationHeadSummaryLists'
  },
  mounted () {
    this.consultationHeadSummaryLists()
  }
}
</script>
<style lang="less">
  #negRevSum{
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
    .project_summary_fatherboxleft{
      padding-right:0px;
    }
    .project_summary_fatherboxright{
      margin-top: 20px;
      padding-left: 0;
    }
    .tableDataSupplier_box{
      margin-top: 10px;
    }
  }
</style>
