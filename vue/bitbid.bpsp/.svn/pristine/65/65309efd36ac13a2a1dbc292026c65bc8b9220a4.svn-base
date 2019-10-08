<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/cooperation' }">合作单位</el-breadcrumb-item>
        <el-breadcrumb-item>添加合作单位</el-breadcrumb-item>
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
          <upLoad-file @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName" :isImage="isImage"></upLoad-file>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
          <el-button @click="$router.go(-1)">取 消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import upLoadFile from '@/components/upload/publicFileUpload'
import { homeManage } from '@/api'
import {links} from '../../../assets/js/validate'
export default {
  data () {
    // 网址验证
    let validUrl = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请填写URL地址'))
      } else if (!links(value)) {
        callback(new Error('请输入正确的URL地址'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        url: [
          { required: true, message: '请填写URL地址', trigger: 'blur' },
          { validator: validUrl, trigger: ['blur', 'change'] }
        ],
        fileInformations: [
          { required: true, message: '请上传图片', trigger: 'change' }
        ]
      },
      fileType: '',
      oldFileName: '',
      isImage: '0'
    }
  },
  components: {
    upLoadFile
  },
  methods: {
    submitForm (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.ruleForm.type = 3
          this.ruleForm.status = 1
          homeManage.save(this.ruleForm).then((res) => {
            this.$router.push({path: '/index/home-manage/cooperation'})
          })
        } else {
          return false
        }
      })
    },
    upLoadFile (file) {
      this.ruleForm.fileInformations = file
      this.ruleForm.fileInformations[0].businessType = 3
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
