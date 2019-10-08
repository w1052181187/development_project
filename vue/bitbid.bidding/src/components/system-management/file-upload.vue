<template>
    <div class="upload">
      <div class="upload" v-if="IEVersion === 1">
        <input type="file"/>
        <span class="upload_btn">上传文件</span>
        <span class="upload_file">文件名</span>
      </div>
      <el-upload
        class="upload-demo"
        ref="upload"
        :before-upload='before'
        :action="upLoad"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :auto-upload="true"
        :on-success='success'
        :limit="1"
        :on-exceed="handleExceed"
        :file-list="fileList" v-else>
        <el-button slot="trigger" size="small" type="primary">上传文件</el-button>
      </el-upload>
    </div>
</template>
<script>
import {fileSize, miningLoadUrl} from '@/assets/js/common'
import {fileInfo} from '@/api'
export default {
  data () {
    return {
      // 业务类型（1.项目 2.公告 3.出让文件 4.澄清修改文件）
      fileList: [], // 文件列表
      upLoad: `${miningLoadUrl}file/upload`, // 上传地址
      loading: false, // 文件上传过程中
      save: []
    }
  },
  props: ['fileType'],
  methods: {
    handleRemove (file, fileList) {
      this.deleteFileList.push(file)
      this.save = []
    },
    handlePreview (file) {
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除${file.name}？`)
    },
    // 文件大小限制
    before (file) {
      return fileSize(this, file)
    },
    // 文件上传成功回调函数：
    success (res) {
      this.loading = false
      res.fileInformation.businessType = this.fileType
      delete res.fileInformation.storeName
      delete res.fileInformation.lastUpdateDate
      delete res.fileInformation.createDate
      this.save.push(res.fileInformation)
    }
  },
  computed: {
    IEVersion () {
      let browser = navigator.appName
      let bversion = navigator.appVersion
      let version = bversion.split(';')
      let trimVersion = version[1].replace(/[ ]/g, '')
      if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
        return 1
      }
    }
  },
  watch: {
    save: function () {
      this.$emit('upLoadFile', this.save)
    }
  },
  mounted () {}
}
</script>
<style scoped>

</style>
