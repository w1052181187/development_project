<template>
    <div class="">
      <div id="iframe_bigbox" v-loading="loading" element-loading-text="报表生成中，请等待" element-loading-spinner="el-icon-loading">
        <iframe id="pdf_file1"
                :src="pdfLink" frameborder="0"
                style="width: 100%;height: 86vh;margin: 0 auto">
        </iframe>
      </div>
    </div>
</template>
<script>
import { reviewControl } from '@/api'
import {pdfUrl} from '@/assets/js/common'
export default {
  data () {
    return {
      pageSize: 1000,
      pageNo: 0,
      basePdfUrl: pdfUrl,
      pdfLink: '',
      pdfWidth: '',
      pdfHeight: '',
      loading: true
    }
  },
  methods: {
    // 评标报告签名
    reportLists () {
      reviewControl.reportView().then((res) => {
        console.log(res)
        if (res.data.resCode === '0000') {
          let report = res.data.BidOpenReport
          if (report && report.fileId) {
            this.pdfLink = this.basePdfUrl + '?doc=' + report.fileId + '&objectId=' +
              report.objectId + '&isSign=1' +
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
<style scoped>
</style>
