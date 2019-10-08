<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/notice'}">通知管理</el-breadcrumb-item>
        <el-breadcrumb-item>查看通知动态</el-breadcrumb-item>
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
        <table class="detailtable">
          <tr>
            <td>标题：</td>
            <td>
              <span>{{ruleForm.title}}</span>
            </td>
          </tr>
          <tr>
            <td>备注：</td>
            <td>
              <span>{{ruleForm.remarks}}</span>
            </td>
          </tr>
          <tr v-if="ruleForm.fileInformations.length">
            <td>附件信息：</td>
            <td>
              <file-download :fileArrays="ruleForm.fileInformations" fileType="9"></file-download>
            </td>
          </tr>
          <tr>
            <td>内容：</td>
            <td>
              <ueditor ref="ueditor" class="ueditor" :editread="true"></ueditor>
            </td>
          </tr>
        </table>
      </el-form>
    </div>
  </div>
</template>
<script>
import ueditor from '@/components/ueditor/ueditor'
import fileDownload from '@/components/file-download'
import {notice} from '@/api'
export default {
  data () {
    return {
      ruleForm: {
        fileInformations: []
      }
    }
  },
  components: {
    ueditor,
    fileDownload
  },
  methods: {
    detail () {
      notice.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.notice
        this.$refs.ueditor.setContent(res.data.notice.content)
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
    .detailtable>tr>td {
      width: inherit;
    }
    .detailtable>tr>td:nth-child(odd) {
      width: 8%;
      min-width: inherit;
      overflow: hidden;
      background: #f5f7f8;
      text-align: right;
      color: #999999;
    }
  }
</style>
