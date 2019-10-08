<template>
    <div class="upload">
      <div class="upload" v-if="IEVersion === 1">
        <input type="file"  class="file" name="file"  id="produceFileOther"/>
        <span class="upload_btn">上传文件</span>
        <span class="fileNames_other" style="margin-left: 20px;margin-right: 20px" v-if="ieFileObjectOther.name !== ''">
          {{ieFileObjectOther.name}}
        </span>
        <span class="fileNames_other" style="margin-left: 20px;margin-right: 20px" v-else></span>
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
import $ from 'jquery'
import '../../../../assets/js/jquery.form'
export default {
  data () {
    return {
      // 业务类型（1.项目 2.公告 3.出让文件 4.澄清修改文件）
      fileList: [], // 文件列表
      upLoad: `${miningLoadUrl}file/upload`, // 上传地址
      loading: false, // 文件上传过程中
      save: [],
      deleteStatus: false,
      deleteOtherFileList: []
    }
  },
  props: ['fileOtherType', 'ieFileObjectOther'],
  methods: {
    handleRemove (file, fileList) {
      this.deleteOtherFileList.push(file)
      this.save = []
    },
    handlePreview (file) {
    },
    handleExceed (files, fileList) {
      this.$message.warning(`删除已有文件后才可以上传新文件`)
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
      res.fileInformation.businessType = this.fileOtherType
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
      if (version[1] !== undefined) { // ie浏览器上使用
        let trimVersion = version[1].replace(/[ ]/g, '')
        if (browser === 'Microsoft Internet Explorer' && trimVersion === 'MSIE9.0') {
          return 1
        }
      }
    }
  },
  watch: {
    save: function () {
      this.$emit('upLoadOtherFile', this.save)
    }
  },
  mounted () {
    let that = this
    $('#produceFileOther').on('change', function (e) {
      // 上传时先把原有的删除掉
      var iedelother = []
      iedelother.push(that.ieFileObjectOther)
      that.$emit('ieDelectFileOther', iedelother)
      // 开始从新创建新的from
      var $form = $('<form></form>')
      $form.attr('action', that.upLoad)
      $form.attr('enctype', 'multipart/form-data')
      $form.attr('method', 'post')
      $form.append($(this))
      $form.ajaxSubmit({
        dataType: 'json',
        success: function (data) {
          var result = data
          if (result.resCode === '0000') {
            $('.fileNames_other').html(data.fileInformation.fileName)
            var arryFile = []
            data.fileInformation.businessType = that.fileOtherType
            arryFile.push(data.fileInformation)
            that.$emit('upLoadOtherFile', arryFile)
          }
        }
      })
    })
  }
}
</script>
