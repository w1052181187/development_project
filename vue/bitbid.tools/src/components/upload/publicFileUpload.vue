<template>
  <div class="file_bigbox">
    <div :class="btns">
      <div id="picker" style="font-size: 12px">上传文件</div>
    </div>
    <span v-if="oldFileName.length !== 0 && this.imgList.length === 0" class="ul-class">{{oldFileName}}</span>
    <ul v-else v-for="(item, index) in imgList" class="ul-class" :key="index">
      <i class="iconfont icon-wenjian wenjian"></i>
      <span v-text="item.name" :title="item.name" v-if="!fileTypeStatus" @click="doenloadFile(item.name, item.relativePath)"></span>
      <span  v-on:mouseenter="mouseenter($event)" v-on:mouseleave="mouseleave($event)">
        <i class="iconfont icon-gouxuan gouxuan" style="display: inline-block;"></i>
        <i class="iconfont icon-chahao chahao" :img-url="item.imageUrl" style="display: none;" @click="deleteImg($event)"></i>
      </span>
    </ul>
    <div class="progressContainer" :style="{display: display}">
      <div class="progress" :style="{width:progress+'%'}">
        <b>{{progress}}%</b>
      </div>
    </div>
    <vue-upload-web :url="cdnUrl" :form-data="cdnParams" :accept="accept" :key-generator="keyGenerator"
      @progress="uploadProgress" @success="handleSuccess" @before="beforeUpload"
      @error="error" @complete="handleComplete" :upload-button="'.' + btns" :multiple=true>
    </vue-upload-web>
  </div>
</template>

<script>
import {loadUrl, downloadFile} from '@/assets/js/common'
export default {
  name: 'app',
  data () {
    return {
      display: 'none',
      progress: 0,
      imgList: [],
      // 接收文件类型
      accept: {
        title: 'Images,zip,rar,sgtb,zgsq,zgys,sgzb,doc,docx,pdf,txt,xlsx,hwtb,hwzb,fwtb,fwzb',
        extensions: 'jpg,jpeg,png,zip,rar,sgtb,zgsq,zgys,sgzb,doc,docx,pdf,txt,xlsx,hwtb,hwzb,fwtb,fwzb',
        mimeTypes: 'image/jpg,image/jpeg,image/png,zip,rar,sgtb,zgsq,zgys,sgzb,doc,docx,pdf,txt,xlsx,hwtb,hwzb,fwtb,fwzb'
      },
      cdnUrl: `${loadUrl}file/upload`,
      host: '查看上传文件地址',
      cdnParams: {
        token: '上传如果需要的token认证',
        key: '',
        name: '',
        chunk: 0,
        chunks: 1
      },
      saveFileInfo: [],
      btns: 'btns_' + Date.parse(new Date()) + Math.floor(Math.random() * 10000) // 动态class
    }
  },
  props: ['oldFileName', 'fileTypeStatus'],
  methods: {
    handleSuccess (file, res) {
      this.saveFileInfo = []
      //      if (navigator.appName === 'Microsoft Internet Explorer' && navigator.appVersion.split(';')[1].replace(/[ ]/g, '') === 'MSIE9.0') {
      //        res = this.getObject(res._raw)
      //      } else {
      //        res = res.fileInformation
      //      }
      const imageUrl = this.host.concat(res.key)
      this.imgList = []
      this.imgList.push({
        imageUrl: imageUrl,
        name: file.name,
        file: file,
        relativePath: res.relativePath
      })
      let tempV = res
      this.saveFileInfo.push(tempV)
      this.$emit('upLoadFile', this.saveFileInfo)
    },
    beforeUpload (file) {

    },
    // 文件下载
    doenloadFile (fileName, relativePath) {
      downloadFile(fileName, relativePath)
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
      const imageUrl = event.currentTarget.getAttribute('img-url')
      this.imgList = this.imgList.filter(img => img.imageUrl !== imageUrl)
    },
    getObject (source) {
      let data = source
      let indexBegin = data.indexOf('<fileInformation>')
      let indexEnd = data.indexOf('</fileInformation>')
      data = data.substring(indexBegin + '<fileInformation>'.length, indexEnd)
      let result = {}
      while (data.length > 0) {
        let endIndex = data.indexOf('>')
        let curData = data.substring(0, endIndex + 1)
        let curEndIndex = curData.indexOf('/>')
        let key, value
        if (curEndIndex > -1) {
          key = curData.substring(0 + 1, curEndIndex)
          result[key] = ''
          data = data.substring(curEndIndex + 2)
        } else {
          curEndIndex = curData.indexOf('>')
          key = curData.substring(0 + 1, curEndIndex)
          let endTag = '</' + key + '>'
          curEndIndex = data.indexOf(endTag)
          value = data.substring(('<' + key + '>').length, curEndIndex)
          result[key] = value
          data = data.substring(curEndIndex + endTag.length)
        }
      }
      return result
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
    height: 5px;
    width: 96%;
    border-radius: 2.5px;
    background-color: #ddd;
    margin-left: 2%;
  }
  .progress{
    background-color: #1C8DE0;
    border-radius: 2.5px;
    height:5px;
    line-height: 5px;
  }
  b{
    color:#fff;
    font-weight: 100;
    font-size: 12px;
    position:absolute;
    left:40%;
  }

  .btns {
    margin-top: 20px;
  }

  .wenjian {
    font-size: 18px;
    color: #bfbfbf;
  }

  .gouxuan {
    font-size: 15px;
    color: #70C34C;
    float: right;
    margin-top: 6px;
  }

  .chahao {
    font-size: 16px;
    color: #bfbfbf;
    float: right;
    margin-top: 6px;
  }

  .ul-class {
    width: 50%;
    display: inline-block;
    vertical-align: top;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .ul-class>span{
    display: block;
    width: 100%;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
    cursor: pointer;
  }
  .ul-class>span:hover{
    display: block;
    width: 100%;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
    color: #409eff;
    cursor: pointer;
  }
</style>
