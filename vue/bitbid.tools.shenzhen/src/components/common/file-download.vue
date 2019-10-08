<template>
  <div class="table_box downoladfilebox">
    <span @click="doenloadFile" style="color: #00aaff;cursor: pointer">{{oldFileName}}</span>
  </div>
</template>
<script>
import {downloadFile} from '@/assets/js/common'
export default {
  data () {
    return {
      fileForm: {}
    }
  },
  props: ['oldFileName', 'filePath'],
  methods: {
    doenloadFile () {
      downloadFile(this.oldFileName, this.filePath)
    }
  }
}
</script>
