<template>
  <div id="negSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="4">
        <leftNavRecord :lefNavArry="lefNavArry" @scoreFlag="scoreFlag"></leftNavRecord>
      </el-col>
      <el-col :span="20" class="negSummrightbox">
        <!--评审-->
        <div class="zztdemo_ruleForm">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">磋商纪要</span>
          </div>
          <div class="editor negotiation_brief">
            <div class="negotiation_briefson">
              <el-form :model="ruleForm" ref="ruleForm" label-width="240px" class="demo-ruleForm">
                <el-form-item label="磋商纪要：" prop="content">
                  <div class="editor">
                    <editor ref="ueditorCon" class="ueditor" :content="briefContent" :editread="editread"></editor>
                    <div class="zhezhao_box"></div>
                  </div>
                </el-form-item>
                <el-form-item label="附件：" prop="content">
                  <commonFileUpload @upLoadFile="upLoadFile" :oldFileName="oldFileName" v-if="!subStatus"></commonFileUpload>
                  <fileDownload :oldFileName="oldFileName" :filePath="briefFilePath" v-else></fileDownload>
                </el-form-item>
                <el-form-item label="上传澄清与承诺文件截止时间：" class="bitianicon">
                  <el-date-picker
                    v-model="ruleForm.submitTime"
                    type="datetime"
                    :picker-options="pickerOptions"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    start-placeholder="选择日期时间" v-if="!subStatus">
                  </el-date-picker>
                  <span v-else>{{ruleForm.submitTime}}</span>
                </el-form-item>
                <el-form-item label="发布状态：">
                  <span>{{ruleForm.briefStatusName}}</span>
                </el-form-item>
                <!--提交按钮-->
                <div class="sub_btnbigbox">
                  <template v-if="!subStatus && !broadsideStatus">
                    <el-button type="primary" class="sub_btn" @click="reviewAgencySummaryAddBtn(1)" v-if="!curStatus">提 交</el-button>
                    <el-button type="primary" class="sub_btn" @click="reviewAgencySummaryAddBtn(0)">保 存</el-button>
                  </template>
                </div>
                <!--提交按钮-->
              </el-form>
            </div>
          </div>
        </div>
        <!--评审-->
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftNavRecord from '@/components/common/competitive-consultation/left-nav-record'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/common/ueditor/ueditor.vue'
import fileDownload from '@/components/common/file-download'
import { consultingSummary } from '@/api'
import $ from 'jquery'
export default {
  components: {
    leftNavRecord,
    editor,
    commonFileUpload,
    fileDownload
  },
  inject: ['reload'],
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      briefContent: '',
      editread: false,
      oldFileName: '', // 附件名字
      briefFilePath: '', // 附件路径
      fileArry: [], // 附件数据
      // 侧边导航数据
      lefNavArry: [],
      // 自定义侧边切换隐藏
      flagShow: true,
      firstNum: 1, // 第一次进入之后
      // 有数据后
      fromDeposit: {},
      // 谈判纪要from
      ruleForm: {
        isChangeFile: 0,
        briefStatusName: '未发布',
        isEnd: 0,
        briefContent: '' // 富文本编辑内容
      },
      briefReasonStatus: '',
      pickerOptions: {
        disabledDate (time) {
          return time.getTime() < Date.now() - 8.64e7
        }
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
      // 是否显示下一轮磋商
      isNextStatus: false,
      offsetWidth: '120px',
      valueTimes: '',
      // 是否提交
      subStatus: true,
      existenceId: '', // 是否存在objectId
      // 侧边是否有数据
      broadsideStatus: false,
      fileTypeStatus: false,
      curStatus: false, // 当前轮提交或者发布状态
      statusMap: new Map(), //   存储状态
      isRevoked: false,
      uploadFileType: ''
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      consultingSummary.viewSummaryPage(this.firstNum).then((res) => {
      })
    },
    initData () {
      this.existenceId = '' // 每次切换重新赋值id
      this.briefContent = '' // 清空每次的重新赋值
      this.$refs.ueditorCon.setContent(this.briefContent) // 进行赋值
      this.ruleForm.isChangeFile = 0
      this.ruleForm.briefStatusName = '未发布'
      this.subStatus = false
      this.isRevoked = false
      this.curStatus = false
      this.oldFileName = ''
      this.briefFilePath = ''
      this.oldFileOtherName = ''
      this.briefOtherFilePath = ''
      $('.edui-editor-toolbarbox').removeClass('edit_function')
    },
    // 获取磋商人员列表数据
    reviewAgencySummaryLists () {
      let briefStatus = 0
      this.subStatus = false
      this.curStatus = false
      consultingSummary.reviewAgencySummaryList(this.firstNum).then((res) => {
        this.ruleForm = res.data.BidOpenNegotiatingBrief
        this.uploadFileType = res.data.fileType
        if (res.data.IsMoreRound === 1) {
          this.isNextStatus = true
        } else {
          this.isNextStatus = false
        }
        // 判断是否有信息
        if (res.data.BidOpenNegotiatingBrief.objectId) { // 有信息时
          if (res.data.BidOpenNegotiatingBrief.briefFile) { // 附件
            this.oldFileName = res.data.BidOpenNegotiatingBrief.briefFile
            this.briefFilePath = res.data.BidOpenNegotiatingBrief.briefFilePath
          }
          // 第一次进入时判断是否提交
          if (res.data.BidOpenNegotiatingBrief.briefContent) {
            this.briefContent = res.data.BidOpenNegotiatingBrief.briefContent // 获取富文本内容
            this.$refs.ueditorCon.setContent(this.briefContent) // 进行赋值
          }
          this.existenceId = res.data.BidOpenNegotiatingBrief.objectId // 把获取到的id进行赋值，为以后判断是否是修改
          briefStatus = res.data.BidOpenNegotiatingBrief.briefStatus
          if (briefStatus === 1 || briefStatus === 2) { // 提交后
            this.subStatus = true // 是否提交
            $('.edui-editor-toolbarbox').addClass('edit_function')
            $('.zhezhao_box').addClass('zhezhao_box_show')
          } else {
            this.subStatus = false // 是否提交
            $('.edui-editor-toolbarbox').removeClass('edit_function')
            $('.zhezhao_box').removeClass('zhezhao_box_show')
          }
          this.isRevoked = briefStatus === 3
          this.curStatus = (briefStatus === 1 || briefStatus === 2)
        }
        this.statusMap.set(this.firstNum, briefStatus)
        if (this.firstNum > 1) {
          this.curStatus = !(this.statusMap.get(this.firstNum - 1) !== 0)
          this.subStatus = (this.statusMap.get(this.firstNum) !== 0)
        }
      })
    },
    // 侧边导航数据
    leftNavList () {
      consultingSummary.leftNav().then((res) => {
        this.lefNavArry = res.data.BidOpenResourceList
        if (this.lefNavArry.length === 0) {
          this.broadsideStatus = true
        }
      })
    },
    // 切换时数据改变
    scoreFlag (scoreProject) {
      this.firstNum = scoreProject.num
      this.initData()
      this.reviewAgencySummaryLists()
    },
    // 附件
    upLoadFile (file) {
      this.fileArry = file
    },
    // 新增谈判内容
    reviewAgencySummaryAddBtn (flag) {
      if (this.ruleForm.submitTime === null) {
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
      this.ruleForm.briefContent = this.$refs.ueditorCon.getContent() // 富文本内容获取
      this.ruleForm.briefStatus = flag
      this.ruleForm.briefNum = this.firstNum
      if (this.fileArry.length !== 0) { // 附件
        this.ruleForm.briefFile = this.fileArry[0].fileInformation.fileName
        this.ruleForm.briefFilePath = this.fileArry[0].fileInformation.relativePath
        this.ruleForm.briefFileObject = this.fileArry[0].fileInformation.toString()
      }
      if (this.existenceId) {
        consultingSummary.reviewAgencySummaryUpdate(this.ruleForm).then((res) => {
          this.reviewAgencySummaryLists()
          this.reload()
        })
      } else {
        consultingSummary.reviewAgencySummaryadd(this.ruleForm).then((res) => {
          this.reviewAgencySummaryLists()
          this.reload()
        })
      }
    },
    init () {
      this.reviewAgencySummaryLists()
      this.leftNavList()
      this.viewPageList()
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style lang="less">
  #negSumm{
    padding-left:20px;
    padding-right:20px;
    box-sizing: border-box;
    .tableDataSupplier_sonbox{
      width: 80%;
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
    .zztdemo_ruleForm{
      padding-bottom: 50px;
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
    .negotiation_brief{
      margin-top: 35px;
    }
    .cancel_btn{
      width: 83px;
      height: 32px;
      border-radius: 0px;
      background: #ededed;
      border: 1px solid #ededed;
      padding: 0;
      color:#828282;
    }
  }
</style>
