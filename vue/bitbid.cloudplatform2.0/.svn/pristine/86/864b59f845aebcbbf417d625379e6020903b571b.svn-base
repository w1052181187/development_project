<template>
  <div class="cloudcontent" id="cloud_information">
    <div class="main">
      <el-form :model="updateForm" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="信息标题：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="信息类型：">
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
        <el-form-item class="submit-radio">
          <el-button type="primary">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
export default {
  components: {
    editor
  },
  data () {
    return {
      updateForm: {
        fileInformations: {}
      },
      // 富文本
      editread: true
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_information {
    .formselect{
      width: 100%;
    }
  }
</style>
