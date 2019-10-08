<template>
  <div class="table_box">
     <span @click="doenloadFile" style="color: #00aaff; cursor: pointer;" :title="fileObjecit.fileName">{{fileObjecit.fileName}}</span>
  </div>
</template>
<script>
import {downloadFileMining} from '@/assets/js/common'
export default {
  data () {
    return {
      fileForm: {}
    }
  },
  props: ['fileObjecit'],
  methods: {
    doenloadFile () {
      downloadFileMining(this.fileObjecit.fileName, this.fileObjecit.relativePath)
    }
  }
}
</script>
