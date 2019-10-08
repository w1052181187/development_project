<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <div class="main">
      <el-form :model="updateForm" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="24">
            <el-form-item label="类型：">
              <span>公司地址</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司地图图片：">
              <downloadFile :fileArrays="updateForm.fileInformations" fileType="9"></downloadFile>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司地址：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="邮编：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button class="primary" @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import downloadFile from '@/components/download/file-download'
export default {
  components: {
    downloadFile
  },
  data () {
    return {
      updateForm: {
        fileInformations: {},
        agencyContractCode: ''
      }
    }
  },
  methods: {
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
  }
</style>
