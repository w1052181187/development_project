<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/policylaw' }">政策法规</el-breadcrumb-item>
        <el-breadcrumb-item>添加政策法规</el-breadcrumb-item>
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
        <el-form-item label="发布日期:" prop="publishDate">
          <el-date-picker
            style="width:300px"
            v-model="ruleForm.publishDate"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实施日期:" prop="materialDate">
          <el-date-picker
            style="width:300px"
            v-model="ruleForm.materialDate"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="内容:" prop="content" class="form-ueditor bitianicon">
          <ueditor ref="content"></ueditor>
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
import ueditor from '@/components/ueditor/ueditor'
import {infoManageLaw} from '@/api'
export default {
  data () {
    return {
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写标题', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        publishDate: [
          { required: true, message: '请选择发布日期', trigger: 'change' }
        ],
        materialDate: [
          { required: true, message: '请选择实施日期', trigger: 'change' }
        ]
      },
      fileType: '',
      oldFileName: ''
    }
  },
  components: {
    ueditor
  },
  methods: {
    submitForm (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.ruleForm.type = 3
          this.ruleForm.content = this.$refs.content.getContent()
          if (!this.ruleForm.content) {
            this.$message({
              message: '请填写内容',
              type: 'warning'
            })
            return false
          }
          infoManageLaw.save(this.ruleForm).then((res) => {
            this.$router.push({path: '/index/info-manage/policylaw'})
          })
        } else {
          return false
        }
      })
    },
    upLoadFile () {}
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
      .ueditor-wrap {
        padding: 0;
      }
      .btns {
        margin-top: 0;
      }
    }
    .form-ueditor {
      .el-form-item__content {
        line-height: 0;
      }
    }
    .bitianicon .el-form-item__label::before{
      content:"*";
      color:#f66c6c;
      margin-right: 5px;
    }
  }
</style>
