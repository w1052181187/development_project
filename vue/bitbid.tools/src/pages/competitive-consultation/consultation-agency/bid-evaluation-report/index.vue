<template>
    <div class="" id="look_baopdf">
      <div class="setup_bigbox">
        <div class="proinfobox">
          <span class="line_bigbox"></span>
          <span class="title_bigbox">报告签名</span>
        </div>
      </div>
      <div style="padding: 0 15px;box-sizing: border-box">
        <commonFileUpload @upLoadFile="upLoadFile" :oldFileName="oldFileName" v-if="!isStart"></commonFileUpload>
        <!--<el-button type="success" class="go_down_endbtnbox" @click="restoreBtn" size="mini" v-if="!isStart">恢复初始模板</el-button>-->
        <!--操作说明-->
        <div class="operation_bigbox" v-if="!isStart">
          <div class="operation_content">
            <span>操作说明：如需更换报告模板，请点击上传按钮，模板确认后可以进行签章。</span>
          </div>
        </div>
        <!--操作说明-->
        <div style="margin-top: 15px">
          <el-button type="success" class="go_down_endbtnbox jiechubtn" size="mini" @click="startBtn" v-if="!isStart">开始签章</el-button>
          <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" v-if="isStart" @click="signatureBtn">签章</el-button>
          <el-button type="success" class="go_down_endbtnbox" size="mini" v-if="isStart" @click="releaseBtn">发布</el-button>
          <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" @click="unlockedBtn">解除签章锁定</el-button>
        </div>
      </div>
      <div id="iframe_bigbox" v-loading="loading" element-loading-text="生成中，请等待" element-loading-spinner="el-icon-loading">
        <pdf ref="pdfInit" :objectId="objectIdQuery" :pdfData="pdfData"></pdf>
      </div>
    </div>
</template>
<script>
import { consultingReviewControl } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import pdf from '@/components/common/hz-pdf.vue'
export default {
  components: {
    commonFileUpload,
    pdf
  },
  inject: ['reload'],
  data () {
    return {
      pageSize: 1000,
      pageNo: 0,
      basePdfUrl: pdfUrl,
      pdfLink: '',
      pdfWidth: '',
      pdfHeight: '',
      loading: false,
      objectIdQuery: '',
      oldFileName: '', // 澄清与承诺名字
      briefFilePath: '', // 澄清与承诺路径
      fileArry: [], // 澄清与承诺数据
      isStart: false, // 是否开始签章
      pdfData: {}
    }
  },
  methods: {
    // 查询评标报告
    reportLists () {
      consultingReviewControl.reportView().then((res) => {
        if (res.data.resCode === '0000') {
          let report = res.data.BidOpenReport
          if (report && report.fileId) {
            this.objectIdQuery = report.objectId
            if (report.fileId !== '#') {
              let url = {
                doc: report.fileId,
                objectId: report.objectId,
                height: this.pdfHeight,
                width: this.pdfWidth
              }
              consultingReviewControl.pdfDetail(url).then((res) => {
                this.pdfData = res.data
                this.$refs.pdfInit.init(res.data.filePath)
              })
              this.loading = false
            }
          } else {
            let url = {
              fileName: '',
              fileId: '#'
            }
            consultingReviewControl.saveResponse(url).then((res) => {
              console.log(res)
              if (res.data.resCode === '0000') {
                this.reportLists()
              }
            })
          }
        }
      })
    },
    // 转换
    upLoadFile (file) {
      this.fileArry = file
      let url = {
        objectId: this.objectIdQuery,
        fileName: this.fileArry[0].fileInformation.fileName,
        fileId: this.fileArry[0].fileInformation.relativePath
      }
      this.loading = true
      consultingReviewControl.uploadResponse(url).then((res) => {
        console.log(res)
        if (res.data.resCode === '0000') {
          this.loading = false
        }
        this.reportLists()
      })
    },
    // 恢复初始模板
    restoreBtn () {
      consultingReviewControl.creatReport().then((res) => {
        if (res.data.resCode === '0000') {
          this.reportLists()
        }
      })
    },
    // 是否开始签章
    isStartReport () {
      consultingReviewControl.isStartEndReport(this.objectIdQuery).then((res) => {
        if (res.data.isLock === 0) {
          consultingReviewControl.startSignature(this.objectIdQuery).then((res) => {
            this.isStart = true
            this.reportLists()
          })
        } else {
          this.$message({
            type: 'warning',
            message: '其他用户正在签章，请稍候！'
          })
          return false
        }
      })
    },
    // 开始签章
    startBtn () {
      this.isStartReport(this.objectIdQuery)
    },
    // 解锁签章
    unlockedBtn () {
      consultingReviewControl.unlockReport(this.objectIdQuery).then((res) => {
        consultingReviewControl.isStartEndReport(this.objectIdQuery).then((res) => {
          this.reload()
          if (res.data.isLock === 0) {
            this.isStart = false
          } else {
            this.isStart = true
          }
        })
      })
    },
    // 签章
    signatureBtn () {
      this.$refs.pdfInit.addSeal()
    },
    // 发布
    releaseBtn () {
      this.$refs.pdfInit.saveToService(this.unlockedBtn)
      this.reload()
    }
  },
  mounted () {
    this.reportLists()
  }
}
</script>
<style lang="less">
  #look_baopdf{
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
    #iframe_bigbox{
      margin-top: 10px;
    }
    .setup_bigbox{
      margin-top: 40px;
      padding: 10px 20px;
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
    .file_bigbox{
      display: inline-block;
      vertical-align: top;
      margin-right: 21px;
    }
    .go_down_endbtnbox{
      margin-top: 0;
      width: 103px;
      height: 25px;
      font-size: 12px;
    }
    .webuploader-container{
      width: 100px;
    }
    .webuploader-pick{
      padding: 5px 25px;
      box-sizing: border-box;
    }
    .el-button+.el-button {
      margin-left: 18px;
    }
  }
</style>
