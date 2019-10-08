<template>
  <div id="negBri" class="content_bgibox">
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">上传澄清与承诺</span>
      </div>
    </div>
    <div class="score_bigbox" style="padding-left: 20px;padding-right: 20px;box-sizing: border-box">
      <div style="padding-top: 20px;" class="filebox" v-if="!subStatus">
        <span style="color: #f56c6c;display: inline-block">*</span>
        <div style="display: inline-block">
          <commonFileUpload @upLoadFile="upLoadFile" :oldFileName="oldFileName" :fileTypeStatus="fileTypeStatus"></commonFileUpload>
        </div>
      </div>
      <el-dialog
        title="查看签章"
        :visible.sync="dialogVisibleIfrm"
        width="60%" id="dialog">
        <span style="color: red;font-size: 14px">* 提示：签章完成后点击发布，如不需签章，可直接点击关闭</span>
        <div id="iframe_bigbox" v-loading="loading" element-loading-text="生成中，请等待" element-loading-spinner="el-icon-loading">
          <iframe id="pdf_file1"
                  :src="pdfLink" frameborder="0"
                  style="width: 100%;height: 60vh;margin: 0 auto;margin-top: 20px">
          </iframe>
        </div>
      </el-dialog>
      <div style="padding: 20px 0">
        <el-button type="primary" class="sub_btn_next" @click="lookSignatureBtn">查看签章</el-button>
      </div>
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">本轮报价</span>
      </div>
      <div style="padding: 20px 0;width: 70%">
        <div style="text-align: center;margin-top: 35px">
          <el-form :model="ruleForm" :rules="expertRules" ref="ruleForm" label-width="180px" class="demo-ruleForm">
            <el-form-item label="投标报价小写（元）：" prop="tenderPrice" style="text-align: left">
              <el-input v-model="ruleForm.tenderPrice" @change="inputChange" v-if="!subStatus"></el-input>
              <span v-else>{{ruleForm.tenderPrice}}</span>
            </el-form-item>
            <el-form-item label="投标报价大写：" style="text-align: left">
              <span>{{upperPrice}}</span>
            </el-form-item>
            <el-form-item label="提交截止时间：" style="text-align: left">
              <span>{{submitTimePice}}</span><span style="color: red">（请在截止时间前提交文件及报价，否则无法进入下一轮谈判）</span>
            </el-form-item>
          </el-form>
          <el-button type="primary" class="sub_btn_next" @click="submitBtn('ruleForm')" v-if="!subStatus">提交</el-button>
          <el-button type="primary" class="sub_btn_next" disabled v-else>已提交</el-button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import fileDownload from '@/components/common/file-download'
import { agencyReviewSummary } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import {OnLoad, SignOnClick} from '@/assets/js/signature'
import {sumType} from '@/assets/js/validate'
export default {
  components: {
    commonFileUpload,
    fileDownload
  },
  data () {
    // 身份证验证
    var sumTypeNum = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入投标报价'))
      } else if (!sumType(value)) {
        callback(new Error('请输入正确的格式（数字或不能超过二位小数）'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      subStatus: false,
      dialogVisibleIfrm: false,
      negotiateNum: this.$route.params.num,
      upperPrice: '', // 价钱大写
      fileTypeStatus: false,
      oldFileName: '', // 澄清与承诺名字
      briefFilePath: '', // 澄清与承诺路径
      fileArry: [], // 澄清与承诺数据
      submitTimePice: '', // 提交时间
      pageSize: 1000,
      pageNo: 0,
      basePdfUrl: pdfUrl,
      pdfLink: '',
      pdfWidth: 900,
      pdfHeight: 350,
      objectIdQuery: '',
      loginName: this.$store.getters.authUser.userName,
      loading: true,
      dataObject: {},
      isFile: false,
      // 自定义序号
      dataIndex: 1,
      expertRules: {
        tenderPrice: [
          { required: true, message: '请输入投标报价', trigger: ['blur', 'change'] },
          { validator: sumTypeNum }
        ]
      }
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      OnLoad()
      agencyReviewSummary.viewClarifyPage(this.$route.params.num).then((res) => {
        if (res.data.isHide === '1') {
          this.subStatus = true
        } else {
          this.subStatus = false
        }
        if (res.data.resCode === '0000') {
          this.list()
        }
      })
    },
    list () {
      this.oldFileName = ''
      this.pdfLink = ''
      this.objectIdQuery = ''
      this.dataObject = {}
      agencyReviewSummary.obtainResponse(this.$route.params.num).then((res) => {
        this.submitTimePice = res.data.submitTime
        this.objectIdQuery = res.data.BidOpenNegotiatingAnswer.objectId
        if (res.data.BidOpenNegotiatingAnswer) {
          if (res.data.BidOpenNegotiatingAnswer.offerFile) {
            this.oldFileName = res.data.BidOpenNegotiatingAnswer.offerFile
            this.isFile = true
          }
          if (res.data.BidOpenNegotiatingAnswer.pdfPath) {
            let linkUrl = res.data.BidOpenNegotiatingAnswer
            this.dataObject = res.data.BidOpenNegotiatingAnswer
            if (!this.subStatus) {
              this.pdfLink = this.basePdfUrl + '?doc=' + linkUrl.pdfPath + '&objectId=' +
                linkUrl.objectId + '&isSign=1' +
                '&height=' + this.pdfHeight + '&width=' + this.pdfWidth + '&signType=answer'
            }
          }
          this.loading = false
        }
      })
    },
    // 下一轮
    nextRound () {
      agencyReviewSummary.gainOffer(this.$route.params.num).then((res) => {
        this.ruleForm = res.data.BidOpenTender
        this.upperPrice = res.data.UpperPrice
      })
    },
    // 澄清与承诺
    upLoadFile (file) {
      if (file[0].fileInformation.extName === 'docx' || file[0].fileInformation.extName === 'doc' || file[0].fileInformation.extName === 'pdf') {
        this.fileArry = file
      } else {
        this.$message({
          type: 'warning',
          message: '只能上传word格式和pdf格式'
        })
        this.fileTypeStatus = true
        return false
      }
      if (this.fileArry.length !== 0) {
        this.dialogVisibleIfrm = true
        let url = {
          objectId: this.objectIdQuery,
          answerNum: this.$route.params.num,
          offerFile: this.fileArry[0].fileInformation.fileName,
          offerFilePath: this.fileArry[0].fileInformation.relativePath,
          offerFileObject: this.fileArry[0].fileInformation.toString()
        }
        this.loading = true
        agencyReviewSummary.uploadResponse(url).then((res) => {
          if (res.data.resCode === '0000') {
            this.loading = false
            this.list()
          }
        })
      }
    },
    inputChange (scope) {
      let flagRex = /^0/
      let reg = /^0$|^[1-9]\d{0,15}$|^[1-9]\d{0,15}\.{1}\d{1,2}$|^0\.{1}\d{1,2}$/g
      if (scope >= 1) {
        if (flagRex.test(scope)) {
          this.$message({
            type: 'warning',
            message: '不能输入小数点后两位以上的数字'
          })
          return false
        }
      }
      if (reg.test(scope)) {
        agencyReviewSummary.offerUpdate(this.ruleForm).then((res) => {
          this.nextRound()
        })
      }
    },
    lookSignatureBtn () {
      this.dialogVisibleIfrm = true
      if (this.subStatus) {
        this.pdfLink = this.basePdfUrl + '?doc=' + this.dataObject.pdfPath + '&objectId=' +
          this.dataObject.objectId + '&height=' + this.pdfHeight + '&width=' + this.pdfWidth + '&signType=answer'
      } else {
        this.pdfLink = this.basePdfUrl + '?doc=' + this.dataObject.pdfPath + '&objectId=' +
          this.dataObject.objectId + '&isSign=1' +
          '&height=' + this.pdfHeight + '&width=' + this.pdfWidth + '&signType=answer'
      }
    },
    // 提交文件
    submitBtn (ruleForm) {
      if (this.submitTimePice) {
        let summitTime = new Date(Date.parse(this.submitTimePice.replace(/-/g, '/'))).getTime()
        let curTime = new Date().getTime()
        if (curTime > summitTime) {
          this.$message({
            type: 'warning',
            message: '上传文件截止时间已过，无法提交'
          })
          return false
        }
      }
      if (!this.isFile) {
        if (this.fileArry.length === 0) {
          this.$message({
            type: 'warning',
            message: '请上传澄清承诺文件'
          })
          return false
        }
      }
      if (this.ruleForm.tenderPrice === '0') {
        this.$message({
          type: 'warning',
          message: '投标报价不能为0'
        })
        return false
      }
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          if (SignOnClick(this.loginName)) {
            agencyReviewSummary.viewClarifySub(this.$route.params.num).then((res) => {
              this.viewPageList()
            })
          }
        } else {
          return false
        }
      })
    },
    init () {
      this.viewPageList()
      this.nextRound()
    }
  },
  watch: {
    $route () {
      this.init()
    },
    negotiateNum: function () {
      this.init()
    }
  },
  mounted () {
    let o = document.getElementById('pdf_file1')
    if (o) {
      this.pdfWidth = (o.clientWidth || o.offsetWidth) - 20
      this.pdfHeight = (o.clientHeight || o.offsetHeight) - 10
    }
    this.init()
  }
}
</script>
<style lang="less">
  #negBri{
    margin-top: 20px;
    .webuploader-container {
      width: 115px;
      vertical-align: middle;
    }
    .webuploader-pick {
      position: relative;
      display: inline-block;
      cursor: pointer;
      background: #00b7ee;
      padding: 5px 15px;
      color: #fff;
      text-align: center;
      border-radius: 3px;
      overflow: hidden;
    }
    .ul-class {
      vertical-align: middle;
    }
    #iframe_bigbox{
      margin-top: 0px;
    }
    .el-dialog{
      margin-top: 10vh!important;
    }
    .el-dialog__body {
      padding: 0px 20px;
      color: #606266;
      line-height: 24px;
      font-size: 14px;
    }
    .el-icon-close{
      display: inline-block;
      width: 30px;
      height: 30px;
      background: url("../../../../static/img/close_icon.png");
      background-size: 30px auto;
    }
    .el-icon-close::before {
      content: "\E60F";
      display: none;
    }
  }
</style>
