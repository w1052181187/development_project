<template>
  <div class="table_box">
     <span @click="downloadFileClick(fileName, filePath)" style="color: #00aaff; cursor: pointer;" :title="fileName">{{fileName}}</span>
  </div>
</template>
<script>
import {downloadFileMining} from '@/assets/js/common'
export default {
  name: 'FileDownload',
  data () {
    return {
    }
  },
  props: ['fileName', 'filePath'],
  methods: {
    downloadFileClick (fileName, filePath) {
      downloadFileMining(fileName, filePath)
    }
  }
}
</script>
