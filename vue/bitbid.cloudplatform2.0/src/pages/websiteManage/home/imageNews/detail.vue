<template>
  <div class="cloudcontent" id="cloud_webhome">
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="标题："  prop="agencyContractCode">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="URL地址："  prop="agencyContractCode">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="图片："  prop="agencyContractName">
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
export default {
  components: {
    fileDownload
  },
  data () {
    return {
      updateForm: {
        fileInformations: {}
      }
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_webhome {
  }
</style>
