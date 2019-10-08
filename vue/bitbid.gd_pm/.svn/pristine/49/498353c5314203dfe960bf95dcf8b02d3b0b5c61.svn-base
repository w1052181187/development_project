<template>
  <div class="center_box" id="contract_box_collection">
    <el-form :model="dataForm" :rules="rules" ref="dataForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">修改轮换信息</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father">
              <div class="dialog_detail">
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="标题：" prop="title">
                      <el-input type="text" v-model="dataForm.title" placeholder="请输入标题"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="URL地址：" prop="url">
                      <el-input type="text" v-model="dataForm.url" placeholder="请输入URL地址"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-form-item label="图片：" prop="fileInformations">
                      <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileArrays="dataForm.fileInformations"
                                   fileType="1" isImage="1"></upload-file>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button type="primary" @click="submitForm('dataForm')" class="save_btn" :loading="subStatus">提交</el-button>
              <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import {rotationInfo} from '@/api'
import uploadFile from '@/components/upload/publicFileUpload'
import {links} from '../../../../assets/js/validate'
export default {
  name: '',
  components: {
    uploadFile
  },
  data () {
    // 网址验证
    let validUrl = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!links(value)) {
        callback(new Error('请输入正确的URL地址'))
      } else {
        callback()
      }
    }
    return {
      dataForm: {
        title: '',
        url: '',
        fileInformations: []
      },
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: ['blur', 'change'] },
          {min: 1, max: 256, message: '长度在 1 到 256 个字符', trigger: ['blur', 'change']}
        ],
        url: [
          {min: 1, max: 512, message: '长度在 1 到 512 个字符', trigger: ['blur', 'change']},
          { validator: validUrl, trigger: ['blur', 'change'] }
        ],
        fileInformations: [
          { required: true, message: '请上传图片', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 上传文件
    uploadSuccess (obj, file, fileType) {
      this.dataForm.fileInformations = this.dataForm.fileInformations.filter(item => item.businessType !== fileType)
      this.dataForm.fileInformations.push(file)
    },
    deleteSuccess (obj, fileId) {
      this.dataForm.fileInformations = this.dataForm.fileInformations.filter(item => item.relativePath !== fileId)
    },
    // 添加
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.subStatus = true
          rotationInfo.save(this.dataForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.$router.push({path: '/admin/portal/rotation-info/index'})
            } else {
              this.subStatus = false
              return false
            }
          })
        } else {
          return false
        }
      })
    },
    detail () {
      rotationInfo.detail(this.$route.query.objectId).then((res) => {
        this.dataForm = res.data.info
      })
    }
  },
  'dataForm.fileInformations' () {
    this.$refs['dataForm'].clearValidate('fileInformations')
  },
  mounted () {
    this.detail()
  }
}
</script>

<style lang="less">
  .el-textarea__inner{
    min-height: 150px!important;
  }
</style>
