<template>
  <div>
    <div class="btns" :id="btnId">
      <div id="picker">选择文件</div>
    </div>
    <ul v-for="(item, index) in fileList" class="ul-class" :key="index">
      <li style="width: 100%; overflow:hidden; float: left;">
        <i class="iconfont icon-wenjian wenjian"></i>
        <span class="filename" :title="item.fileName" v-text="item.fileName" @click=nameClick(item.fileName,item.relativePath)></span>
        <span  v-on:mouseenter="mouseenter($event)" v-on:mouseleave="mouseleave($event)">
          <i class="iconfont icon-gouxuan gouxuan" style="display: inline-block;"></i>
          <i class="iconfont icon-chahao chahao" :file-id="item.relativePath" style="display: none;" @click="deleteFile($event)"></i>
        </span>
      </li>
    </ul>
    <div class="progressContainer" :style="{display: display}">
      <div class="progress" :style="{width:parseInt(progress)+'%'}">
        <b>{{parseInt(progress)}}%</b>
      </div>
    </div>
    <vue-upload-web :url="cdnUrl" :form-data="cdnParams" :accept="accept" :key-generator="keyGenerator"
                    @progress="uploadProgress" @success="handleSuccess" @before="beforeUpload"
                    @error="error" @complete="handleComplete" :upload-button="btnId" :multiple=true>
    </vue-upload-web>
  </div>
</template>

<script>
import {fileUploadUrl, downloadFile} from '@/assets/js/common'
export default {
  name: 'publicFileUpload',
  data () {
    return {
      display: 'none',
      progress: 0,
      // 接收文件类型
      accept: {
        title: 'Images,zip,gif,rar,doc,docx,pdf,txt,xls,xlsx',
        extensions: 'jpg,jpeg,png,gif,zip,rar,doc,docx,pdf,txt,xls,xlsx',
        mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,zip,rar,doc,docx,pdf,txt,xls,xlsx'
      },
      cdnUrl: fileUploadUrl,
      host: '查看上传文件地址',
      cdnParams: {
        token: '上传如果需要的token认证',
        key: '',
        name: '',
        chunk: 0,
        chunks: 1
      },
      isImagetrue: 0,
      btnId: 'btns_' + Date.parse(new Date()) + Math.floor(Math.random() * 10000), // 动态id
      showoldfile: true,
      fileList: []
    }
  },
  props: ['fileType', 'fileArrays', 'fileMaxNum', 'isImage'],
  methods: {
    beforeUpload (file) {
      if (this.isImage === '1') {
        if (file.type.substring(0, 5) !== 'image') {
          this.$message({
            type: 'warning',
            message: '图片格式不正确，请重新上传'
          })
          this.isImagetrue = 1
        } else {
          this.isImagetrue = 0
        }
      }
    },
    handleSuccess (file, res) {
      if (this.isImagetrue === 0) {
        let maxNum = this.fileMaxNum || 5
        if (this.fileList.length >= maxNum) {
          this.$message({
            message: `最多可上传${maxNum}个文件`,
            type: 'warning'
          })
        } else {
          let fileInformation = res.fileInformation
          if (fileInformation) {
            fileInformation['businessType'] = this.fileType
            this.$emit('uploadSuccess', fileInformation, this.fileType)
          }
        }
      }
    },
    uploadProgress (file, percentage) {
      this.display = 'block'
      this.progress = percentage * 100
    },
    error (message) {
      this.$message.error(message)
    },
    handleComplete () {
      this.display = 'none'
    },
    keyGenerator (file) {
      const currentTime = new Date().getTime()
      this.cdnParams.key = 'test/cdn/ie9/' + currentTime + '.' + file.name
      return this.cdnParams.key
    },
    mouseenter (event) {
      event.currentTarget.getElementsByClassName('gouxuan')[0].style.display = 'none'
      event.currentTarget.getElementsByClassName('chahao')[0].style.display = 'inline-block'
    },
    mouseleave (event) {
      event.currentTarget.getElementsByClassName('gouxuan')[0].style.display = 'inline-block'
      event.currentTarget.getElementsByClassName('chahao')[0].style.display = 'none'
    },
    deleteFile (event) {
      const relativePath = event.currentTarget.getAttribute('file-id')
      this.$emit('deleteSuccess', relativePath, this.fileType)
    },
    nameClick (fileName, filePath) {
      // 下载
      downloadFile(fileName, filePath)
    },
    init () {
      if (this.fileArrays.length !== 0) {
        this.fileList = Array.isArray(this.fileArrays) ? (this.fileType ? this.fileArrays.filter(item => Number(item.businessType) === Number(this.fileType)) : this.fileArrays) : [this.fileArrays]
      }
    }
  },
  watch: {
    fileArrays () {
      this.fileList = Array.isArray(this.fileArrays) ? (this.fileType ? this.fileArrays.filter(item => Number(item.businessType) === Number(this.fileType)) : this.fileArrays) : [this.fileArrays]
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style>
  .progressContainer{
    height: 14px;
    width: 26%;
    border-radius: 5px;
    background-color: #ddd;
    margin-left: 120px;
    position: relative;
  }
  .progress{
    position: absolute;
    left: 0px;
    top: 0px;
    background-color: #1C8DE0;
    border-radius: 5px;
    height:14px;
    line-height: 14px;
  }
  b{
    color:#fff;
    font-weight: 100;
    font-size: 12px;
    position:absolute;
    left:40%;
  }

  .btns {
    height: 40px;
    line-height: 40px;
    float: left;
    overflow: hidden;
    position: relative;
  }
  .btns #picker{
    width: 86px;
    height: 40px;
    overflow: hidden;
    position: relative;
  }
  .wenjian {
    font-size: 18px;
    color: #bfbfbf;
  }

  .gouxuan {
    width: 10px;
    height: 10px;
    font-size: 15px;
    color: #70C34C;
    float: right;
    margin-top: 15px;
    background: url("../../assets/ico_upload_gou.png") left no-repeat;
    -webkit-background-size: contain;
    background-size: contain;
    display: block;
  }

  .chahao {
    width: 10px;
    height: 10px;
    font-size: 16px;
    color: #bfbfbf;
    float: right;
    margin-top: 15px;
    background: url("../../assets/ico_upload_cha.png") right no-repeat;
    -webkit-background-size: contain;
    background-size: contain;
    display: block;
  }

  .ul-class {
    width: 200px;
    height: 40px;
    line-height: 40px;
    margin-top: 0px;
    float: left;
    padding-left: 0px;
    margin-left: 10px;
    cursor: pointer;
    margin-bottom: 0px;
    list-style: none;
  }
  .ul-class li{
    list-style: none;
  }
  span.ul-class,.ul-class li>span.filename{
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
