<template>
  <div class="example-simple">
    <div class="upload">
      <div class="example-btn">
        <file-upload
          class="btn btn-primary"
          v-model="files"
          :post-action="upLoad"
          @input-filter="inputFilter"
          @input-file="inputFile"
          ref="upload">
          <span v-if="oldFileName || files.length !== 0">重新上传</span>
          <span v-else>上传文件</span>
        </file-upload>
        <span class="finename" v-if="files.length === 0">{{oldFileName}}</span>
        <span class="finename" v-for="(file) in files" :key="file.id">
          <span :title="file.name">{{file.name}}</span>
        </span>
      </div>
    </div>
  </div>
</template>
<style>
  .example-simple label.btn {
    margin-bottom: 0;
    margin-right: 1rem;
  }
  .btn{
    display: inline-block;
    text-align: center;
    cursor: pointer;
    outline: 0;
    background-color: #2c972f;
    border-color: #2c972f;
    padding: 0px 15px;
    border-radius: 3px;
    color: #ffffff;
    float: left;
  }
  .btn span{
    color: #ffffff!important;
    padding: 0px;
    line-height: 20px!important;
    cursor: pointer;
  }
  .btn-primary label{
    cursor: pointer;
  }
  span.finename{
    line-height: 40px;
    float: left;
    margin-left: 13px;
    width: 200px;
    overflow: hidden;
    -ms-text-overflow: ellipsis;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>

<script>
import {miningLoadUrl} from '@/assets/js/common'
import FileUpload from 'vue-upload-component'
export default {
  components: {
    FileUpload
  },
  data () {
    return {
      uploadText: '',
      files: [],
      upLoad: `${miningLoadUrl}file/upload`, // 上传地址,
      save: [],
      childFileType: this.fileType
    }
  },
  props: ['fileType', 'oldFileName'],
  methods: {
    inputFilter (newFile, oldFile, prevent) {
      if (newFile && !oldFile) {
        // Before adding a file
        // 添加文件前
        // Filter system files or hide files
        // 过滤系统文件 和隐藏文件
        if (/(\/|^)(Thumbs\.db|desktop\.ini|\..+)$/.test(newFile.name)) {
          return prevent()
        }
        // Filter php html js file
        // 过滤 php html js 文件
        if (/\.(php5?|html?|jsx?)$/i.test(newFile.name)) {
          return prevent()
        }
      }
    },
    inputFile (newFile, oldFile) {
      // 自动上传
      if (Boolean(newFile) !== Boolean(oldFile) || oldFile.error !== newFile.error) {
        if (!this.$refs.upload.active) {
          this.$refs.upload.active = true
        }
      }
      // 上传成功
      if (newFile && oldFile && !newFile.active && oldFile.active) {
        // 获得相应数据
        console.log(111)
        let tempV = newFile.response.fileInformation
        console.log(222)
        tempV.businessType = this.childFileType
        console.log(333)
        this.save.push(tempV)
        console.log(444)
        this.$emit('upLoadFile', this.save)
        console.log(555)
      }
    }
  },
  watch: {
    save: function () {
      this.$emit('upLoadFile', this.save)
    }
  }
}
</script>
