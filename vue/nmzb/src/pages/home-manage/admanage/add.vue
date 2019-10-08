<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/admanage' }">广告管理</el-breadcrumb-item>
        <el-breadcrumb-item>添加广告</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题:" prop="title">
          <el-input v-model="ruleForm.title" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="URL地址:" prop="url">
          <el-input v-model="ruleForm.url" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="图片:" prop="fileInformations">
          <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileArrays="ruleForm.fileInformations" :fileType="fileType" isImage="1" fileMaxNum="5" ></upload-file>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
          <el-button @click="$router.go(-1)">取 消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import uploadFile from '@/components/upload/publicFileUpload'
import { homeManage } from '@/api'
import {links} from '../../../assets/js/validate'
export default {
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
      ruleForm: {
        title: '',
        url: '',
        fileInformations: []
      },
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        url: [
          { validator: validUrl, trigger: ['blur', 'change'] }
        ],
        fileInformations: [
          { required: true, message: '请上传图片', trigger: 'change' }
        ]
      },
      fileType: 9
    }
  },
  components: {
    uploadFile
  },
  methods: {
    submitForm (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.ruleForm.type = 2
          homeManage.save(this.ruleForm).then(() => {
            this.$router.push({path: '/index/home-manage/admanage'})
          })
        } else {
          return false
        }
      })
    },
    uploadSuccess (file) {
      this.ruleForm.fileInformations.splice(0, 1, file)
    },
    deleteSuccess (fileId) {
      this.ruleForm.fileInformations = this.ruleForm.fileInformations.filter(item => item.relativePath !== fileId)
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  #rotate-add {
    .contentbigbox .demo-ruleForm {
      .el-form-item__content {
        text-align: left;
      }
      .btns {
        margin-top: 0;
      }
    }
  }
</style>
