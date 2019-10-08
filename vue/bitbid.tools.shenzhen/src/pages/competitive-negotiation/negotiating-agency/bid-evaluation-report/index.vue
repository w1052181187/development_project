<template>
    <div class="" id="look_baopdf">
      <div style="margin-top: 7vh;padding: 0 20px;box-sizing: border-box">
        <commonFileUpload @upLoadFile="upLoadFile" :oldFileName="oldFileName"></commonFileUpload>
      </div>
      <div id="iframe_bigbox" v-loading="loading" element-loading-text="报表生成中，请等待" element-loading-spinner="el-icon-loading">
        <iframe id="pdf_file1"
          :src="pdfLink" frameborder="0"
          style="width: 100%;height: 75vh;margin: 0 auto">
        </iframe>
      </div>
    </div>
</template>
<script>
import { reviewControl } from '@/api'
import {pdfUrl} from '@/assets/js/common'
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
export default {
  components: {
    commonFileUpload
  },
  data () {
    return {
      pageSize: 1000,
      pageNo: 0,
      basePdfUrl: pdfUrl,
      pdfLink: '',
      pdfWidth: '',
      pdfHeight: '',
      loading: true,
      objectIdQuery: '',
      oldFileName: '', // 澄清与承诺名字
      briefFilePath: '', // 澄清与承诺路径
      fileArry: [] // 澄清与承诺数据
    }
  },
  methods: {
    // 查询评标报告
    reportLists () {
      reviewControl.reportView().then((res) => {
        if (res.data.resCode === '0000') {
          let report = res.data.BidOpenReport
          if (report && report.fileId) {
            this.objectIdQuery = report.objectId
            this.pdfLink = this.basePdfUrl + '?doc=' + report.fileId + '&objectId=' + report.objectId + '&isSign=1' +
            '&height=' + this.pdfHeight + '&width=' + this.pdfWidth
            this.loading = false
          } else {
            reviewControl.creatReport().then((res) => {
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
      reviewControl.uploadResponse(url).then((res) => {
        if (res.data.resCode === '0000') {
          this.loading = false
        }
        this.reportLists()
      })
    }
  },
  mounted () {
    let o = document.getElementById('pdf_file1')
    if (o) {
      this.pdfHeight = (o.clientHeight || o.offsetHeight) - 40
      this.pdfWidth = (o.clientWidth || o.offsetWidth) - 20
    }
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
      margin-top: 20px;
    }
  }
</style>
