<template>
  <div id="zuqianzhang">
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">报告签名</span>
      </div>
    </div>
    <div style="padding: 0 20px;box-sizing: border-box">
      <el-button type="success" class="jiechubtn" size="mini" @click="startBtn" v-if="!isStart">开始签章</el-button>
      <el-button type="success" class="go_down_endbtnbox filedownbtn" size="mini" v-if="isStart" @click="signatureBtn">签章</el-button>
      <el-button type="success" class="go_down_endbtnbox" size="mini" v-if="isStart" @click="releaseBtn">发布</el-button>
    </div>
    <div id="iframe_bigbox" v-loading="loading" element-loading-text="生成中，请等待" element-loading-spinner="el-icon-loading">
      <pdf ref="pdfInit" :objectId="objectIdQuery" :pdfData="pdfData"></pdf>
    </div>
  </div>
</template>
<script>
import { consultingReviewControl } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import pdf from '@/components/common/hz-pdf.vue'
export default {
  components: {
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
      pdfData: {},
      isStart: false // 是否开始签章
    }
  },
  methods: {
    // 评标报告签名
    reportLists () {
      consultingReviewControl.reportView().then((res) => {
        if (res.data.resCode === '0000') {
          let report = res.data.BidOpenReport
          if (report && report.fileId) {
            this.objectIdQuery = report.objectId
            let url = {
              doc: report.fileId,
              objectId: report.objectId
            }
            consultingReviewControl.pdfDetail(url).then((res) => {
              this.pdfData = res.data
              this.$refs.pdfInit.init(res.data.filePath)
            })
            this.loading = false
          } else {
            //            consultingReviewControl.creatReport().then((res) => {
            //              if (res.dat.resCode === '0000') {
            //                this.reportLists()
            //              }
            //            })
          }
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
  #zuqianzhang{
    .setup_bigbox{
      margin-top: 40px;
      padding: 10px 20px;
    }
    #iframe_bigbox{
      margin-top: 10px;
    }
    .jiechubtn{
      margin-top: 0;
      width: 103px;
      height: 25px;
      font-size: 12px;
    }
    .go_down_endbtnbox{
      margin-top: 0;
      width: 103px;
      height: 25px;
      font-size: 12px;
    }
    object{
      height: 70vh!important;
    }
  }
</style>
