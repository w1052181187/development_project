<template>
  <div class="cloudcontent" id="cloud_webconpany">
    <div class="main">
      <el-form :model="updateForm" :rules='rules' ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="24">
            <el-form-item label="类型：">
              <span>联系方式</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="联系人：">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="电话：">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="邮箱：">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitBtn('updateForm')">确定</el-button>
          <el-button class="cancal" @click="$router.go(-1)">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      updateForm: {
        fileInformations: {},
        agencyContractCode: ''
      },
      rules: {},
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
      this.updateForm.fileInformations = this.updateForm.fileInformations.filter(item => item.relativePath !== fileId)
    }
  }
}
</script>
<style lang="less">
  #cloud_webconpany {
  }
</style>
