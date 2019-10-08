<template>
  <div id="negRevSum" class="content_bgibox">
    <!--谈判纪要-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">谈判纪要</span>
      </div>
      <div class="score_bigbox" style="padding: 20px;border: none">
        <el-form :model="ruleForm" ref="ruleForm" label-width="240px" class="demo-ruleForm">
          <el-form-item label="谈判纪要：" prop="content">
            <div class="editor">
              <editor ref="ueditor" class="ueditor" :content="briefContent" :editread="editread"></editor>
              <div class="zhezhao_box"></div>
            </div>
          </el-form-item>
          <el-form-item label="附件：" prop="content">
            <commonFileUpload @upLoadFile="upLoadFile" :oldFileName="oldFileName" v-if="!subStatus && headmanStatus"></commonFileUpload>
            <fileDownload :oldFileName="oldFileName" :filePath="briefFilePath" v-else></fileDownload>
          </el-form-item>
          <el-form-item label="上传澄清与承诺文件截止时间：" class="bitianicon">
            <el-date-picker
              v-model="ruleForm.submitTime"
              type="datetime"
              :picker-options="pickerOptions"
              value-format="yyyy-MM-dd HH:mm:ss"
              start-placeholder="选择日期时间" v-if="!subStatus && headmanStatus">
            </el-date-picker>
            <span v-else>{{ruleForm.submitTime}}</span>
          </el-form-item>
          <el-form-item label="发布状态:" prop="content">
            <span>{{ruleForm.briefStatusName}}</span>
          </el-form-item>
        </el-form>
      </div>
      <!--按钮-->
      <div class="sub_btnbigbox" style="padding-bottom: 50px" v-if="ruleForm.briefStatus !== 2 && headmanStatus">
        <el-button type="primary" class="confirm_btn" @click="publish">确认并发布</el-button>
      </div>
      <!--按钮-->
    </div>
    <!--谈判纪要-->
  </div>
</template>
<script>
import fileDownload from '@/components/common/file-download'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/common/ueditor/ueditor.vue'
import { negotiatingPoints, agencyReviewSummary } from '@/api'
import $ from 'jquery'
export default {
  components: {
    fileDownload,
    commonFileUpload,
    editor
  },
  data () {
    return {
      // 谈判纪要数据
      ruleForm: {
        briefStatusName: '未发布',
        isEnd: 0
      },
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      rules: {
        briefReason: [
          { required: true, message: '请输入驳回理由', trigger: ['blur', 'change'] },
          {min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: ['blur', 'change']}
        ]
      },
      // 是否进入下一轮
      roundData: [
        {
          nextName: '是',
          label: 1
        },
        {
          nextName: '否',
          label: 0
        }
      ],
      editread: false,
      subStatus: false, // 是否发布
      // 富文本编辑
      dialogFormVisible: false, // 驳回弹框
      rejectedFrom: {}, // 驳回表单
      briefContent: ' ',
      headmanStatus: false, // 是否是组长
      numberObjectId: '', // 轮数id
      fileArry: [], // 附件数据
      oldFileName: '', // 附件名字
      briefFilePath: '' // 附件路径
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      agencyReviewSummary.viewSummaryPage(this.$route.params.num).then((res) => {
      })
    },
    // 谈判纪要
    negotiationBriefLists () {
      negotiatingPoints.negotiationBriefList(this.$route.params.num).then((res) => {
        if (res.data.BidOpenNegotiatingBrief) { // 如果是有数据
          this.numberObjectId = res.data.BidOpenNegotiatingBrief.objectId
        }
        if (res.data.BidOpenNegotiatingBrief.briefStatus) {
          this.ruleForm = res.data.BidOpenNegotiatingBrief
          if (res.data.BidOpenNegotiatingBrief.briefContent) {
            this.briefContent = res.data.BidOpenNegotiatingBrief.briefContent // 获取富文本内容
            this.$refs.ueditor.setContent(this.briefContent) // 进行赋值
          }
          if (this.ruleForm.briefFile) { // 附件
            this.oldFileName = this.ruleForm.briefFile
            this.briefFilePath = this.ruleForm.briefFilePath
          }
          if (res.data.BidOpenNegotiatingBrief.briefStatus === 2) { // 发布后
            $('.edui-editor-toolbarbox').addClass('edit_function')
            $('.zhezhao_box').addClass('zhezhao_box_show')
            this.editread = true
            this.subStatus = true
          } else {
            $('.edui-editor-toolbarbox').removeClass('edit_function')
            $('.zhezhao_box').removeClass('zhezhao_box_show')
            this.editread = false
            this.subStatus = false
          }
        }
      })
    },
    // 附件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 发布
    publish () {
      if (!this.ruleForm.submitTime) {
        this.$message({
          type: 'warning',
          message: '请选择时间'
        })
        return false
      }
      let times = this.ruleForm.submitTime
      let startTimes = new Date(times.substring(0, 4), parseInt(times.substring(5, 7)) - 1, times.substring(8, 10), times.substring(11, 13), times.substring(14, 16), times.substring(17)).getTime()
      if (startTimes < new Date().getTime()) {
        this.$message({
          type: 'warning',
          message: '时间不能选择当前之前的时间'
        })
        return false
      }
      this.ruleForm.briefContent = this.$refs.ueditor.getContent() // 富文本内容获取
      this.ruleForm.briefStatus = 2 // 发布状态
      this.ruleForm.briefNum = this.$route.params.num // 轮数
      this.ruleForm.objectId = this.numberObjectId // 轮数id
      if (this.fileArry.length !== 0) { // 附件
        this.ruleForm.briefFile = this.fileArry[0].fileInformation.fileName
        this.ruleForm.briefFilePath = this.fileArry[0].fileInformation.relativePath
        this.ruleForm.briefFileObject = this.fileArry[0].fileInformation.toString()
      }
      negotiatingPoints.publish(this.$route.params.num, this.ruleForm).then((res) => {
        this.negotiationBriefLists()
        this.$store.commit('RELEASE_STATUS', 'true')
      })
    }
  },
  watch: {
    $route: function () {
      this.negotiationBriefLists()
      this.viewPageList()
    }
  },
  mounted () {
    this.negotiationBriefLists()
    this.viewPageList()
    this.$store.getters.authUser.roleIds.map((its) => {
      if (its === 2 || its === 9) {
        this.headmanStatus = true
        $('.edui-editor-toolbarbox').removeClass('edit_function')
        $('.zhezhao_box').removeClass('zhezhao_box_show')
      } else {
        this.editread = true
        $('.edui-editor-toolbarbox').addClass('edit_function')
        $('.zhezhao_box').addClass('zhezhao_box_show')
      }
    })
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
