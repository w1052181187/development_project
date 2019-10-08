<template>
  <div>
    <div class="table_box" v-for="(item, index) in fileList" :key="index">
      <span class="filename" @click="downloadFile(item);" style="color: #00aaff; float: left; cursor: pointer;" :title="item.fileName">{{item.fileName}} &nbsp; &nbsp;</span>
    </div>
  </div>
</template>
<script>
import {downloadFile} from '@/assets/js/common'
export default {
  data () {
    return {
    }
  },
  props: ['fileArrays', 'fileType'],
  methods: {
    downloadFile (file) {
      downloadFile(file.fileName, file.relativePath)
    }
  },
  computed: {
    fileList () {
      return this.fileArrays ? (Array.isArray(this.fileArrays) ? this.fileArrays.filter(item => item.businessType === this.fileType) : [this.fileArrays]) : []
    }
  }
}
</script>
<style scoped>
  .filename{
    width: 170px;
    height: 40px;
    line-height: 40px;
    overflow: hidden;
    -ms-text-overflow: ellipsis;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
    color: rgb(0, 170, 255);
  }
</style>
