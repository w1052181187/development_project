<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/violation' }">违规公示</el-breadcrumb-item>
        <el-breadcrumb-item>编辑违规公示</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="公告名称:" prop="title">
          <el-input v-model="ruleForm.title" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="被处理单位名称:" prop="nameOfProcessedUnit">
          <el-input v-model="ruleForm.nameOfProcessedUnit" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="被处理单位地址:" prop="addressOfProcessedUnit">
          <el-input v-model="ruleForm.addressOfProcessedUnit" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="公示内容:" prop="content" class="form-ueditor bitianicon">
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
import {infoManageViolation} from '@/api'
export default {
  data () {
    return {
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写公告名称', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        nameOfProcessedUnit: [
          { required: true, message: '请填写被处理单位名称', trigger: 'change' }
        ],
        addressOfProcessedUnit: [
          { required: true, message: '请填写被处理单位地址', trigger: 'change' }
        ]
      }
    }
  },
  components: {
    ueditor
  },
  methods: {
    detail () {
      infoManageViolation.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.violationNotice
        this.$refs.content.setContent(res.data.violationNotice.content)
      })
    },
    submitForm (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.ruleForm.content = this.$refs.content.getContent()
          if (!this.ruleForm.content) {
            this.$message({
              message: '请填写内容',
              type: 'warning'
            })
            return false
          }
          infoManageViolation.update(this.ruleForm).then((res) => {
            this.$router.push({path: '/index/info-manage/violation'})
          })
        } else {
          return false
        }
      })
    },
    upLoadFile () {}
  },
  mounted () {
    this.detail()
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
