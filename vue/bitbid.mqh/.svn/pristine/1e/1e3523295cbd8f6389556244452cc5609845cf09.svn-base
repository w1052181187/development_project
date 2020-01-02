<template>
  <div class="info-main">
    <div class="set-info">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item :to="{ path: '/admin/infoset' }">系统名称和LOGO设置</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="set-cont set-pos">
      <el-form :model="formInfo" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
        <el-form-item label="系统名称:" prop="systemName">
          <el-input v-model="formInfo.systemName" :disabled="disValue" class="set-name"></el-input>
        </el-form-item>
        <el-form-item label="系统Logo上传:" prop="fileInformation">
          <el-upload
            class="upload-demo"
            :action="upLoad"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            :before-upload="beforeAvatarUpload"
            :show-file-list="false"
            :file-list="fileInfo">
            <el-button size="small" :disabled="disValue" type="primary">点击上传</el-button><span v-if="!disValue" class="upload-tip">建议上传图片尺寸为640*640，且不超过1M</span>
          </el-upload>
          <div class="apply-upload" v-if="this.fileInfo.length>0">
            <el-row>
              <el-col :span="12">
                    <el-button type="text" style="padding-right: 20px;" @click="downloadFile(fileInfo[0].fileName,fileInfo[0].relativePath)">{{fileInfo[0].fileName}}</el-button>
                    <i v-if="!disValue" class="el-icon-error upload-file" @click="clickDel()"></i>
              </el-col>
            </el-row>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :disabled="disValue" @click="saveSubmit('ruleForm')">保存</el-button>
          <el-button @click="editForm">编辑</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {systemInfo} from '../../../api/index'
import {commonJs, downloadFile} from 'common/js/common'
export default {
  name: 'infoset',
  data () {
    return {
      upLoad: commonJs.fileUploadUrl,
      disValue: true,
      formInfo: {}, // 表单对象
      fileInfo: [],
      rules: { // 校验规则
        systemName: [
          { required: true, message: '系统名称不能为空', trigger: 'blur' }
        ],
        fileInfo: [
          { required: true, message: '上传logo不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init () {
      systemInfo.query().then((res) => {
        this.formInfo = res.data.systemInfo
        if (this.formInfo.fileInformation) {
          this.fileInfo[0] = this.formInfo.fileInformation
        }
      })
    },
    downloadFile,
    /** 删除上传附件 */
    clickDel () {
      this.fileInfo = []
    },
    beforeAvatarUpload (file) {
      const isImage = (file.type === 'image/jpeg' || file.type === 'image/png' || file.type === 'image/jpg')
      const isLt2M = file.size / 1024 / 1024 < 1

      if (!isImage) {
        this.$message.error('只能上传jpeg/jpg/png格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 1MB!')
      }
      return isImage && isLt2M
    },
    // 保存
    saveSubmit (name) {
      this.$refs[name].validate((vaild) => {
        if (vaild) {
          this.disValue = true
          systemInfo.update(this.formInfo)
        } else {
          return false
        }
      })
    },
    // 编辑
    editForm () {
      this.disValue = false
    },
    // 上传成功
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileInfo = fileInformations // 获得目前项目中所有的附件信息
        this.formInfo.fileInformation = this.fileInfo[0]
      }
    },
    // 上传失败
    uploadError (err, file, fileInfo) {
      console.log(err)
    }
  },
  mounted () {
    this.init()
  },
  // 组件
  components: {
  }
}
</script>
<style lang="less">
  .set-name {
    width:30%;
  }
  .set-info {
    text-align: left;
    font-weight: 600;
    border-bottom: 1px solid #f1f1f1;
    padding: 24px;
    margin: 20px;
    background-color: #fff;
  }
  .set-cont {
    text-align: left;
    padding: 20px 0;
    margin: 20px;
    background-color: #fff;
    .upload-demo {
      width: 400px;
    }
  }
  .upload-tip {
    padding-left: 15px;
  }
</style>
