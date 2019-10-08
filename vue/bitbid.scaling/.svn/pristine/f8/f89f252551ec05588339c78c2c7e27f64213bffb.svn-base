<template>
  <div class="table_box downoladfilebox">
    <span @click="downloadFile" style="color: #00aaff;cursor: pointer">{{this.fileObject.fileName}}</span>
  </div>
</template>
<script>
import {downloadFile, downloadFileByUrl} from '@/assets/js/common'
export default {
  data () {
    return {
    }
  },
  props: ['fileObject'],
  methods: {
    downloadFile () {
      let httpPosition = this.fileObject.fileUrl.indexOf('http')
      if (httpPosition === 0) {
        downloadFileByUrl(this.fileObject.fileUrl)
      } else {
        downloadFile(this.fileObject.fileName, this.fileObject.fileUrl)
      }
    }
  }
}
</script>
