<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/download' }">下载中心</el-breadcrumb-item>
        <el-breadcrumb-item>查看下载</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <el-form :model="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题:">
          <span>{{ruleForm.title}}</span>
        </el-form-item>
        <el-form-item label="类型:">
            <span v-if="ruleForm.type === 1">协会</span>
            <span v-if="ruleForm.type === 2">专家库</span>
            <span v-if="ruleForm.type === 3">交易平台</span>
            <span v-if="ruleForm.type === 4">其他</span>
        </el-form-item>
        <el-form-item label="文件:">
          <file-download :fileArrays="ruleForm.fileInformations" fileType="9"></file-download>
        </el-form-item>
        <el-form-item label="备注:">
          <ueditor ref="content" :editread="true"></ueditor>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import ueditor from '@/components/ueditor/ueditor'
import fileDownload from '@/components/file-download'
import {download} from '@/api'
export default {
  data () {
    return {
      ruleForm: {},
      fileObject: {}
    }
  },
  components: {
    ueditor,
    fileDownload
  },
  methods: {
    detail () {
      download.detail(this.$route.query.objectId).then((res) => {
        this.ruleForm = res.data.downloadInfo
        this.$refs.content.setContent(res.data.downloadInfo.remark)
        this.fileObject = res.data.downloadInfo.fileInformations[0]
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
