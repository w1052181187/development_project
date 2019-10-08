<template>
  <div id="negBri" class="content_bgibox">
    <!--评分-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">谈判纪要</span>
      </div>
    </div>
    <div class="score_bigbox" style="padding: 20px;box-sizing: border-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="240px" class="demo-ruleForm">
        <el-form-item label="谈判纪要：" prop="content">
          <div class="editor">
            <editor ref="ueditor" class="ueditor" :content="content" :editread="true"></editor>
          </div>
        </el-form-item>
        <el-form-item label="附件：" prop="content">
          <fileDownload :oldFileName="oldFileName" :filePath="briefFilePath"></fileDownload>
        </el-form-item>
        <el-form-item label="上传澄清与承诺文件截止时间：" prop="content">
          <span>{{ruleForm.submitTime}}</span>
        </el-form-item>
      </el-form>
    </div>
    <!--评分-->
  </div>
</template>
<script>
import fileDownload from '@/components/common/file-download'
import editor from '@/components/common/ueditor/ueditor.vue'
import { agencyReviewSummary } from '@/api'
export default {
  components: {
    fileDownload,
    editor
  },
  data () {
    return {
      ruleForm: {},
      fileEnclosure: '', // 附件
      otherFiles: '', // 采购文件
      content: '我的富文本',
      negotiationNum: this.$route.params.num,
      oldFileName: '', // 附件
      briefFilePath: '', // 附件
      fileArry: [] // 附件
    }
  },
  methods: {
    // 获取谈判人员列表数据
    reviewAgencySummaryLists () {
      agencyReviewSummary.reviewAgencySummaryList(this.$route.params.num).then((res) => {
        if (res.data.BidOpenNegotiatingBrief.briefStatus === 2) {
          this.content = res.data.BidOpenNegotiatingBrief.briefContent
          this.ruleForm = res.data.BidOpenNegotiatingBrief
          this.$refs.ueditor.setContent(this.content)
          if (this.ruleForm.briefFile) {
            this.oldFileName = this.ruleForm.briefFile
            this.briefFilePath = this.ruleForm.briefFilePath
          } else {
            this.oldFileName = ''
            this.briefFilePath = ''
          }
        }
      })
    }
  },
  watch: {
    $route () {
      this.reviewAgencySummaryLists()
    }
  },
  mounted () {
    this.reviewAgencySummaryLists()
  }
}
</script>
<style lang="less">
  #negBri{
    margin-top: 20px;
    .bitianicon .el-form-item__content{
      margin-left: 0!important;
    }
  }
</style>
