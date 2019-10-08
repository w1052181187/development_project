<template>
  <div class="table_box">
    <span @click="downloadFile" style="color: #00aaff; float: left; cursor: pointer;" :title="fileObject.fileName">{{fileObject.fileName}}</span>
  </div>
</template>
<script>
import {downloadFile} from '@/assets/js/common'
export default {
  data () {
    return {
    }
  },
  props: ['fileObject'],
  methods: {
    downloadFile () {
      downloadFile(this.fileObject.fileName, this.fileObject.relativePath)
    }
  }
}
</script>
