<template>
    <div class="">
      <div id="iframe_bigbox" style="padding-right: 200px;">
        <iframe id="pdf_file1" :src="pdfLink" frameborder="0" style="width: 70%;height: 86vh"></iframe>
      </div>
    </div>
</template>
<script>
import {pdfUrl} from '@/assets/js/common'
export default {
  data () {
    return {
      pageSize: 1000,
      pageNo: 0,
      basePdfUrl: pdfUrl,
      pdfLink: '',
      fileId: this.$route.query.fileId,
      pdfWidth: '',
      pdfHeight: ''
    }
  },
  methods: {
    // 查询评标报告
    reportLists () {
      this.pdfLink = this.basePdfUrl + '?doc=' + this.fileId +
        '&height=' + this.pdfHeight + '&width=' + this.pdfWidth
    }
  },
  mounted () {
    let o = document.getElementById('pdf_file1')
    this.pdfHeight = (o.clientHeight || o.offsetHeight) - 20
    this.pdfWidth = (o.clientWidth || o.offsetWidth)
    this.reportLists()
  }
}
</script>
<style scoped>

</style>
