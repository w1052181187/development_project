<template>
  <div id="negRevSum" class="content_bgibox">
    <!--汇总结果-->
    <el-row class="project_summary_bigbox">
      <el-col :span="12" class="project_summary_fatherboxleft">
        <div class="project_summary_box">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">谈判结果</span>
          </div>
          <!--汇总结果数据-->
          <div class="tableDataSupplier_box">
            <div class="tableDataSupplier_sonbox_prject">
              <el-table
                :data="tableDataSupplierNeg"
                border
                style="width: 100%">
                <el-table-column
                  prop="logIndex"
                  label="谈判顺序"
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
                  width="150"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="isPass"
                  label="是否进入下一阶段"
                  width="150"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <div class="statusbigbox" v-if="scope.row.isPass === 1">
                      <span class="statusbox">是</span>
                    </div>
                    <div class="statusbigbox red_tag" v-if="scope.row.isPass === 0">
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
                :data="tableDataSupplier"
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
                  width="150"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <span class="red_tag" v-if="scope.row.result === '不合格'">{{scope.row.result}}</span>
                    <span v-if="scope.row.result === '合格'">{{scope.row.result}}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="reason"
                  label="理由"
                  width="150"
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
    <!--谈判纪要-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">谈判纪要</span>
      </div>
      <div class="score_bigbox" style="padding: 20px;border: none">
        <el-form :model="ruleForm" ref="ruleForm" label-width="180px" class="demo-ruleForm">
          <el-form-item label="谈判纪要：" prop="content">
            <div class="editor">
              <editor ref="ueditor" class="ueditor" :content="briefContent" :editread="true"></editor>
            </div>
          </el-form-item>
          <el-form-item label="附件：" prop="content">
            <fileDownload :oldFileName="oldFileName" :filePath="briefFilePath"></fileDownload>
          </el-form-item>
          <el-form-item label="发布状态:" prop="content">
            <span>{{ruleForm.briefStatusName}}</span>
          </el-form-item>
          <el-form-item label="上传澄清与承诺文件截止时间：">
            <span>{{ruleForm.submitTime}}</span>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--谈判纪要-->
  </div>
</template>
<script>
import fileDownload from '@/components/common/file-download'
import editor from '@/components/common/ueditor/ueditor.vue'
import { negotiatingPoints } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  data () {
    return {
      // 谈判结果汇总
      tableDataSupplierNeg: [],
      // 评委评审情况
      tableDataSupplier: [],
      // 谈判纪要数据
      ruleForm: {},
      dataIndex: 1,
      // 富文本编辑
      briefContent: '',
      resultListId: '',
      tabReviewProjectRadios: '',
      oldFileName: '', // 附件名字
      briefFilePath: '', // 附件路径
      oldFileOtherName: '', // 采购文件名字
      briefOtherFilePath: '' // 采购文件路径
    }
  },
  methods: {
    // 谈判结果汇总
    negotiationHeadSummaryLists () {
      negotiatingPoints.negotiationHeadSummaryList(this.$route.params.num).then((res) => {
        this.tableDataSupplierNeg = res.data.ResultList
        this.tabReviewProjectRadios = this.tableDataSupplierNeg[0].tenderId
        this.resultListId = this.tableDataSupplierNeg[0].tenderId
        this.JudgesLists()
      })
    },
    // 表格点击事件(评审汇总)
    tabReviewProjectClick (id) {
      this.resultListId = id
      this.JudgesLists()
    },
    // 评委评审情况
    JudgesLists () {
      negotiatingPoints.JudgesList(this.resultListId, this.$route.params.num).then((res) => {
        this.tableDataSupplier = res.data.ResultList
      })
    },
    // 谈判纪要
    negotiationBriefLists () {
      negotiatingPoints.negotiationBriefList(this.$route.params.num).then((res) => {
        this.ruleForm = res.data.BidOpenNegotiatingBrief
        this.briefContent = res.data.BidOpenNegotiatingBrief.briefContent // 获取富文本内容
        this.$refs.ueditor.setContent(this.briefContent) // 进行赋值
        if (this.ruleForm.briefFile) { // 附件
          this.oldFileName = this.ruleForm.briefFile
          this.briefFilePath = this.ruleForm.briefFilePath
        }
      })
    },
    init () {
      this.negotiationHeadSummaryLists()
      this.negotiationBriefLists()
    }
  },
  mounted () {
    this.init()
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
  }
</style>
