<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/bidstate' }">招标声明</el-breadcrumb-item>
        <el-breadcrumb-item>添加招标声明</el-breadcrumb-item>
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
        <el-form-item label="公告名称:" prop="title">
          <el-input v-model="ruleForm.title" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="招标编号:" prop="tenderCode">
          <el-input v-model="ruleForm.tenderCode" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="项目实施地:" prop="projectImplementationSite">
          <el-input v-model="ruleForm.projectImplementationSite" style="width:300px"></el-input>
        </el-form-item>
        <el-form-item label="行业:" prop="industryTypeFirst">
          <el-select v-model="ruleForm.industryTypeFirst" class="select" style="width:300px">
            <el-option
              v-for="item in industryList"
              :label="item.label"
              :value="item.value"
              :key="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资金来源:" prop="sourceOfFunds">
          <el-select v-model="ruleForm.sourceOfFunds" class="select" style="width:300px">
            <el-option label="自有资金" value="1"></el-option>
            <el-option label="专项资金" value="2"></el-option>
            <el-option label="吸收资金" value="3"></el-option>
            <el-option label="其他" value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公告内容:" prop="content" class="form-ueditor bitianicon">
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
import {industry} from '../../../assets/js/industry'
import {infoManageBidState} from '@/api'
export default {
  data () {
    return {
      industryList: industry,
      ruleForm: {},
      rules: {
        title: [
          { required: true, message: '请填写公告名称', trigger: 'blur' },
          { min: 1, max: 256, message: '长度在 1到 256 个字符', trigger: 'blur' }
        ],
        tenderCode: [
          { required: true, message: '请填写招标编号', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1到 20 个字符', trigger: 'blur' }
        ],
        projectImplementationSite: [
          { required: true, message: '请填写项目实施地', trigger: 'blur' }
        ],
        industryTypeFirst: [
          { required: true, message: '请选择行业', trigger: 'change' }
        ],
        sourceOfFunds: [
          { required: true, message: '请选择资金来源', trigger: 'change' }
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
          this.ruleForm.type = 1
          this.ruleForm.content = this.$refs.content.getContent().trim()
          if (!this.ruleForm.content) {
            this.$message({
              message: '请填写内容',
              type: 'warning'
            })
            return false
          }
          infoManageBidState.save(this.ruleForm).then((res) => {
            this.$router.push({path: '/index/info-manage/bidstate'})
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
