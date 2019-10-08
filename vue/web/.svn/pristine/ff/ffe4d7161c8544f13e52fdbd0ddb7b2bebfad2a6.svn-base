<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/violation' }">违规公示</el-breadcrumb-item>
        <el-breadcrumb-item>查看违规公示</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="公示名称:">
          <span>{{ruleForm.title}}</span>
        </el-form-item>
        <el-form-item label="被处理单位名称:">
          <span>{{ruleForm.nameOfProcessedUnit}}</span>
        </el-form-item>
        <el-form-item label="被处理单位地址:">
          <span>{{ruleForm.addressOfProcessedUnit}}</span>
        </el-form-item>
        <el-form-item label="公示内容:" class="form-ueditor">
          <ueditor ref="content"></ueditor>
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
      ruleForm: {}
    }
  },
  components: {
    ueditor
  },
  methods: {
    detail () {
      infoManageViolation.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.violationNotice
        this.$refs.content.setContent(res.data.violationNotice.content)
      })
    }
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
  }
</style>
