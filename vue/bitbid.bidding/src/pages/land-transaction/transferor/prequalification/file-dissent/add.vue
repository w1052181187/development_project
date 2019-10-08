<template>
  <div id="marginappaddbox"  class="maincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/lt/index?roledsType=1&showStatus=false'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>资格预审会</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/lt/transferor/prequalification/file-dissent/index?roledsType=1&showStatus=false'}">资格预审申请文件异议</el-breadcrumb-item>
        <el-breadcrumb-item>添加异议</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox detailcontentbigbox">
      <el-form :model="refundedSecurityForm" :rules="rules" ref="refundedSecurityForm" label-width="160px" class="demo-ruleForm">
        <!--项目信息-->
        <div class="proinfobox">
          <span class="title">项目信息</span>
          <div class="formmian">
            <el-row>
              <el-col :span="12">
                <el-form-item label="标题：" prop="title">
                  <el-input v-model="refundedSecurityForm.title" placeholder="请输入标题"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="地块编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称：">
                  <span>{{refundedSecurityForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号：">
                  <span>{{refundedSecurityForm.sectionNumber}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="异议内容：" class="bitianicon">
                  <div class="editor">
                    <editor ref="ueditor" class="ueditor" :editread="false" :content="content"></editor>
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24" class="filebigbox">
                <el-form-item label="附件：">
                  <commonFileUpload @upLoadFile="upLoadFile" :fileType="fileType" :oldFileName="oldFileName"></commonFileUpload>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <!--项目信息-->
        <el-form-item class="submit-radio">
          <el-button type="primary" @click="submitForm('refundedSecurityForm')" :loading="isSubmit">提交</el-button>
          <el-button class="cancel" @click="$router.go(-1)" :loading="isSubmit">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import commonFileUpload from '@/components/upload/publicFileUpload.vue'
import editor from '@/components/public-resource/land-transaction/common/ueditor/ueditor.vue'
export default {
  components: {
    commonFileUpload,
    editor
  },
  data () {
    return {
      refundedSecurityForm: {
        approvrResult: 1
      },
      content: '',
      isSubmit: false, // 是否提交
      fileObjecit: {
        fileName: '测试数据'
      },
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1~500个字符', trigger: ['blur', 'change'] }
        ]
      },
      // 业务类型
      fileType: 1,
      // 文件上传保存的数组
      fileArry: [],
      oldFileName: ''
    }
  },
  methods: {
    list () {},
    // 将子组件获取到的数据传给父组件
    upLoadFile (file) {
      this.fileArry = file
    },
    submitForm (from) {
      this.$refs[from].validate((valid) => {
        if (valid) {
          if (this.fileArry.length === 0) {
            this.$message({
              type: 'warning',
              message: '请上传附件'
            })
            return false
          }
        } else {
          return false
        }
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  @import '../../../../../assets/css/land-register/common.css';
</style>
