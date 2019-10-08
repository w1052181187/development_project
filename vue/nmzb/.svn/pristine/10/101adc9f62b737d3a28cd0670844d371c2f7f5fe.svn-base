<template>
  <div id="rotate-edit" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/dynamic-info' }">动态信息</el-breadcrumb-item>
        <el-breadcrumb-item>修改动态</el-breadcrumb-item>
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
        <el-form-item label="关键字:">
          <el-input v-model="ruleForm.keywords" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="行业" prop="industryTypeFirst">
          <el-select v-model="ruleForm.industryTypeFirst" class="select">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地区" prop="cityId">
          <el-select v-model="ruleForm.cityId" class="select">
            <el-option
              v-for="item in addressOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件上传:" prop="fileInformations">
          <upload-file @uploadSuccess="uploadSuccess" @deleteSuccess="deleteSuccess" :fileArrays="ruleForm.fileInformations" :fileType="fileType" isImage="0" fileMaxNum="5" ></upload-file>
        </el-form-item>
        <el-form-item label="内容:" prop="content">
          <editor ref="ueditor" class="ueditor"></editor>
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
import * as industry from '../../../assets/js/industry'
import * as region from '../../../assets/js/nmRegion'
import uploadFile from '@/components/upload/publicFileUpload'
import editor from '@/components/ueditor/ueditor.vue'
import { dynamicNotice } from '@/api'
export default {
  data () {
    return {
      // 项目行业
      options: industry.industry,
      // 行政区域
      addressOptions: region.nmRegion,
      ruleForm: {
        title: '',
        keywords: '',
        industryTypeFirst: '',
        cityId: '',
        fileInformations: []
      },
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        industryTypeFirst: [
          {required: true, message: '请选择行业', trigger: 'change'}
        ],
        cityId: [
          {required: true, message: '请选择地区', trigger: 'change'}
        ]
      },
      fileType: 9
    }
  },
  components: {
    editor,
    uploadFile
  },
  methods: {
    // 数据
    detail () {
      dynamicNotice.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.dynamicNotice
        this.$refs.ueditor.setContent(res.data.dynamicNotice.content)
      })
    },
    submitForm (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.ruleForm.content = this.$refs.ueditor.getContent()
          dynamicNotice.update(this.ruleForm).then((res) => {
            this.$router.push({path: '/index/home-manage/dynamic-info'})
          })
        } else {
          return false
        }
      })
    },
    uploadSuccess (file) {
      this.ruleForm.fileInformations.push(file)
    },
    deleteSuccess (fileId) {
      this.ruleForm.fileInformations = this.ruleForm.fileInformations.filter(item => item.relativePath !== fileId)
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  #rotate-edit {
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
