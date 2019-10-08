<template>
  <div class="cloudcontent" id="cloud_notice">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公告名称："  prop="agencyContractCode">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标项目编号："  prop="agencyContractCode">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标项目名称："  prop="agencyContractCode">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标方式：">
              <el-select v-model="value" class="formselect">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公告内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="图片："  prop="agencyContractName">
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess" :fileArrays="updateForm.fileInformations" fileType="9" isImage="1"></upload-file>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitBtn('updateForm')">提交</el-button>
          <el-button type="primary" @click="saveBtn('updateForm')">保存</el-button>
          <el-button class="cancal" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import uploadFile from '@/components/upload/publicFileUpload'
import editor from '@/components/ueditor/ueditor.vue'
export default {
  components: {
    uploadFile,
    editor
  },
  data () {
    return {
      updateForm: {
        fileInformations: {}
      },
      options: [{
        value: 1,
        label: '公开招标'
      }, {
        value: 2,
        label: '询价'
      }, {
        value: 3,
        label: '邀请招标'
      }, {
        value: 4,
        label: '竞争性谈判'
      }, {
        value: 5,
        label: '竞争性磋商'
      }, {
        value: 6,
        label: '单一来源'
      }, {
        value: 7,
        label: '全部'
      }],
      value: 1,
      // 富文本
      editread: false
    }
  },
  methods: {
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformations.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformations = this.tenderBulletin.fileInformations.filter(item => item.relativePath !== fileId)
    }
  }
}
</script>
<style lang="less">
  #cloud_notice {
    .formselect{
      width: 100%;
    }
  }
</style>
