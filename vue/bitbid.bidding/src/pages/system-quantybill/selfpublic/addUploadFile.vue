<template>
  <div class="openfile" title="请上传.cmz格式文件">
    <div :class="btns" id="btns">
      <div id="picker">点击上传</div>
    </div>
    <ul v-for="(item, index) in imgList" class="ul-class" :key="index">
      <i class="iconfont icon-wenjian wenjian"></i>
      <span v-text="item.name" @click=nameClick(item.file) :title="item.name"></span>
      <span  v-on:mouseenter="mouseenter($event)" v-on:mouseleave="mouseleave($event)">
        <i class="iconfont icon-gouxuan gouxuan" style="display: inline-block;"></i>
        <i class="iconfont icon-chahao chahao" :img-url="item.imageUrl" style="display: none;" @click="deleteImg($event)"></i>
      </span>
    </ul>
    <vue-upload-web :url="cdnUrl" :form-data="cdnParams" :accept="accept" :key-generator="keyGenerator"
                    @progress="uploadProgress" @success="handleSuccess" @before="beforeUpload"
                    @error="error" @complete="handleComplete" :upload-button="'.' + btns" :multiple=true>
    </vue-upload-web>
  </div>
</template>

<script>
import {loadUrl} from '@/assets/js/common'
export default {
  name: 'app',
  data () {
    return {
      // 动态class
      btns: 'btns_' + Date.parse(new Date()) + Math.floor(Math.random() * 10000),
      display: 'none',
      progress: 0,
      imgList: [],
      // 文件类型。
      accept: {
        title: 'cmz',
        extensions: 'cmz',
        mimeTypes: 'cmz'
      },
      cdnUrl: `${loadUrl}comptend/project/localFile/bidding/open`,
      host: '查看上传文件地址',
      cdnParams: {
        token: '上传如果需要的token认证',
        key: '',
        name: '',
        chunk: 0,
        chunks: 1
      },
      saveFileInfo: []
    }
  },
  methods: {
    handleSuccess (file, res) {
      if (res.resCode === '0000') {
        this.imgList = []
        const imageUrl = this.host.concat(res.key)
        this.imgList.push({
          imageUrl: imageUrl,
          name: file.name,
          file: file
        })
        this.saveFileInfo.push(res)
        this.$emit('upLoadFile', this.saveFileInfo)
      } else {
        this.$message({
          type: 'warning',
          message: res.resMessage
        })
      }
    },
    beforeUpload (file) {

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
      this.imgList = this.imgList.filter(img => img.imageUrl != imageUrl)
    },
    nameClick (file) {
    },
    getObject (source) {
      let data = source
      let indexBegin = data.indexOf('<section>')
      let indexEnd = data.indexOf('</section>')
      data = data.substring(indexBegin + '<section>'.length, indexEnd)
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
  .openfile{
    height: 40px;
    display: inline-block;
    float: left;
    margin-right: 10px;
    font-size: 14px;
  }
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

  #btns {
    height: 40px;
    line-height: 40px;
    float: left;
    margin-top: 0px;
    font-size: 14px;
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
    width: 336px;
    height: 40px;
    line-height: 40px;
    margin-top: 0px;
    float: right;
    overflow: hidden;
  }
  .ul-class span{
    width: 100%;
    height: 40px;
    line-height: 40px;
    overflow: hidden;
    -ms-text-overflow: ellipsis;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .webuploader-container{
    width: auto!important;
  }
</style>
