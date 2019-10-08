<template>
  <div class="cloudcontent" id="cloud_notice">
    <div class="main">
      <el-form :model="updateForm" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="公示名称：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标项目编号：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="招标项目名称：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="招标方式：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公示内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="图片：">
              <file-download :fileArrays="updateForm.fileInformations" fileType="9"></file-download>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import fileDownload from '@/components/download/file-download'
import editor from '@/components/ueditor/ueditor.vue'
export default {
  components: {
    fileDownload,
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
      editread: true
    }
  },
  methods: {
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
