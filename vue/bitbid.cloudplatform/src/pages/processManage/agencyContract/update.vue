<template>
  <div class="cloudcontent" id="cloud_agencyContract">
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
      <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/processManage/agencyContract' }">代理合同管理</el-breadcrumb-item>
      <el-breadcrumb-item class="active_bread">修改代理合同</el-breadcrumb-item>
    </el-breadcrumb>
    <!--面包屑-->
    <div class="main">
      <el-form :model="updateForm" :rules="rules" ref="updateForm" :validate-on-rule-change="true">
        <el-row>
          <el-col :span="12">
            <el-form-item label="代理合同编号："  prop="agencyContractCode">
              <el-input v-model="updateForm.agencyContractCode"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="代理合同名称："  prop="agencyContractName">
              <el-input v-model="updateForm.agencyContractName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="委托单位："  prop="entrustEnterpriseName">
              <el-input v-model="updateForm.entrustEnterpriseName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="委托单位联系人："  prop="entrustEnterpriseContacts">
              <el-input v-model="updateForm.entrustEnterpriseContacts"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="委托单位联系电话："  prop="entrustEnterpriseContactsPhone">
              <el-input v-model="updateForm.entrustEnterpriseContactsPhone"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同签订日期："  prop="contractSigningDate">
              <el-date-picker
                style="width:100%;"
                v-model="updateForm.contractSigningDate"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
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
              <upload-file @uploadSuccess="uploadOtherSuccess" @deleteSuccess="deleteSuccess"
                           :fileArrays="updateForm.fileInformationList"
                           fileType="9"
                           isImage="0">
              </upload-file>
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
import editor from '@/components/ueditor/ueditor.vue'
import {agencyContract} from '@/api/cloudplatform'
import uploadFile from '@/components/upload/publicFileUpload'
export default {
  components: {
    editor,
    uploadFile
  },
  data () {
    var validatePhone = (rule, value, callback) => {
      if (value && (!(/^1(3|4|5|7|8)\d{9}$/.test(value)))) {
        callback(new Error('委托单位联系电话输入不合法，请重新录入！'))
      } else {
        callback()
      }
    }
    return {
      updateForm: {
        fileInformationList: []
      },
      rules: {
        agencyContractCode: {required: true, message: '请填写代理合同编号', trigger: 'blur'},
        agencyContractName: {required: true, message: '请填写代理合同名称', trigger: 'blur'},
        entrustEnterpriseName: {required: true, message: '请填写委托单位', trigger: 'blur'},
        contractSigningDate: {required: true, message: '请填写合同签订日期', trigger: 'change'},
        entrustEnterpriseContactsPhone: { validator: validatePhone, trigger: 'blur' }
      },
      // 富文本
      editread: false,
      content: ''
    }
  },
  methods: {
    // 上传附件
    uploadOtherSuccess (file) {
      this.updateForm.fileInformationList.push(file)
    },
    deleteSuccess (fileId) {
      this.updateForm.fileInformationList = this.updateForm.fileInformationList.filter(item => item.relativePath !== fileId)
    },
    getOne () {
      agencyContract.getOne(this.$route.params.code).then(res => {
        this.updateForm = res.data.agencyContract
        if (this.updateForm.contractContent) {
          this.$refs.ueditor.setContent(this.updateForm.contractContent)
        }
      })
    },
    saveBtn (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.updateForm.status = 1
          this.updateForm.contractContent = this.$refs.ueditor.getContent()
          let contractSigningDate = new Date(this.updateForm.contractSigningDate).getTime()
          this.updateForm.contractSigningDate = contractSigningDate
          this.updateForm.fileInformationList.map(item => {
            item.objectId = null
          })
          agencyContract.save(this.updateForm).then(() => {
            this.$router.push({path: `/processManage/agencyContract`})
          })
        } else {
          return false
        }
      })
    },
    submitBtn (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.updateForm.status = 2
          this.updateForm.submitTime = new Date().getTime()
          this.updateForm.contractContent = this.$refs.ueditor.getContent()
          this.updateForm.fileInformationList.map(item => {
            item.objectId = null
          })
          agencyContract.save(this.updateForm).then(() => {
            this.$router.push({path: `/processManage/agencyContract`})
          })
        } else {
          return false
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
