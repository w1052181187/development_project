<template>
  <div>
    <div class="btns">
      <div id="picker">选择文件</div>
    </div>
    <span v-if="oldFileName.length !== 0 && this.imgList.length === 0" class="ul-class" @click="downloadFile" :title="oldFileName">{{oldFileName}}</span>
    <ul v-else v-for="(item, index) in imgList" class="ul-class" :key="index">
      <i class="iconfont icon-wenjian wenjian"></i>
      <span class="filename" :title="item.name" v-text="item.name" @click=nameClick(item.file)></span>
      <span  v-on:mouseenter="mouseenter($event)" v-on:mouseleave="mouseleave($event)">
        <i class="iconfont icon-gouxuan gouxuan" style="display: inline-block;"></i>
        <i class="iconfont icon-chahao chahao" :img-url="item.imageUrl" style="display: none;" @click="deleteImg($event)"></i>
      </span>
    </ul>
    <!--<div class="progressContainer" :style="{display: display}">loadUrl-->
      <!--<div class="progress" :style="{width:progress+'%'}">-->
        <!--<b>{{progress}}%</b>-->
      <!--</div>-->
    <!--</div>-->
    <vue-upload-web :url="cdnUrl" :form-data="cdnParams" :accept="accept" :key-generator="keyGenerator"
                    @progress="uploadProgress" @success="handleSuccess" @before="beforeUpload"
                    @error="error" @complete="handleComplete" upload-button=".btns" :multiple=true>
    </vue-upload-web>
  </div>
</template>

<script>
import {fileUploadUrl, downloadFile} from '@/assets/js/common'
export default {
  name: 'app',
  data () {
    return {
      display: 'none',
      progress: 0,
      imgList: [],
      // 接收文件类型
      accept: {
        title: 'Images,zip,gif,rar,doc,docx,pdf,txt,xlsx',
        extensions: 'jpg,jpeg,png,gif,zip,rar,doc,docx,pdf,txt,xlsx',
        mimeTypes: 'image/jpg,image/jpeg,image/png,image/gif,zip,rar,doc,docx,pdf,txt,xlsx'
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
      saveFileInfo: [],
      isImagetrue: 0
    }
  },
  props: ['fileType', 'oldFileName', 'relativePath', 'isImage'],
  methods: {
    // 下载
    downloadFile () {
      downloadFile(this.oldFileName, this.relativePath)
    },
    beforeUpload (file) {
      if (this.isImage === '0') {
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
        const imageUrl = this.host.concat(res.key)
        this.imgList = []
        this.imgList.push({
          imageUrl: imageUrl,
          name: file.name,
          file: file
        })
        let tempV = res.fileInformation
        tempV['businessType'] = this.fileType
        this.saveFileInfo.push(tempV)
        // this.$emit('upLoadFile', this.saveFileInfo)
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
    deleteImg (event) {
      this.saveFileInfo = []
      this.oldFileName = ''
      const imageUrl = event.currentTarget.getAttribute('img-url')
      this.imgList = this.imgList.filter(img => img.imageUrl !== imageUrl)
    },
    nameClick (file) {
    }
  },
  watch: {
    saveFileInfo: function () {
      this.$emit('upLoadFile', this.saveFileInfo)
    }
  }
}
</script>

<style>
  .progressContainer{
    height: 20px;
    width: 96%;
    border-radius: 10px;
    background-color: #ddd;
    margin-left: 2%;
  }
  .progress{
    background-color: #1C8DE0;
    border-radius: 10px;
    height:20px;
    line-height: 20px;
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
  }
  span.ul-class,.ul-class>span.filename{
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
