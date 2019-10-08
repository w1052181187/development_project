<template>
  <div class="cloudcontent" id="cloud_agencyContract">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/agencyContract' }">代理合同管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">查看代理合同</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <el-form :model="updateForm" :rules="rules" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="代理合同编号：">
              <span>{{updateForm.agencyContractCode}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代理合同名称：">
              <span>{{updateForm.agencyContractName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="委托单位：">
              <span>{{updateForm.entrustEnterpriseName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="委托单位联系人：">
              <span>{{updateForm.entrustEnterpriseContacts}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="委托单位联系电话：">
              <span>{{updateForm.entrustEnterpriseContactsPhone}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同签订日期：">
              <span>{{updateForm.contractSigningDate}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24" class="ueditor_box">
            <el-form-item label="合同内容：">
              <editor ref="ueditor" class="ueditor" :editread="editread" :content="content"></editor>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="附件：">
              <el-table
                :data="updateForm.fileInformationList"
                border
                style="width: 100%" header-cell-class-name="tableheader">
                <el-table-column
                  type="index"
                  label="序号"
                  width="60"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="fileName"
                  label="文件名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" align="center" width="200">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="lookFile(scope.row)">查看</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import editor from '@/components/ueditor/ueditor.vue'
import {agencyContract} from '@/api/cloudplatform'
import {downloadFile} from '@/assets/js/common'
export default {
  components: {
    editor
  },
  data () {
    return {
      updateForm: {},
      rules: {},
      // 富文本
      editread: true,
      content: ''
    }
  },
  methods: {
    lookFile (file) {
      console.log(file)
      downloadFile(file.fileName, file.relativePath)
    },
    getOne () {
      agencyContract.getOne(this.$route.params.code).then(res => {
        this.updateForm = res.data.agencyContract
        if (this.updateForm.contractContent) {
          this.$refs.ueditor.setContent(this.updateForm.contractContent)
        }
      })
    }
  },
  mounted () {
    this.getOne()
  }
}
</script>
<style lang="less">
  #cloud_agencyContract {
    .el-select{
      width: 100%;
    }
  }
</style>
