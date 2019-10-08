<template>
  <div id="rotate-detail" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>首页管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/home-manage/rotateinfo' }">轮换信息</el-breadcrumb-item>
        <el-breadcrumb-item>查看轮换图</el-breadcrumb-item>
      </el-breadcrumb>
      <div class="returnboxbig">
        <template>
          <el-button @click="$router.go(-1)">返回</el-button>
        </template>
      </div>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
        <el-form :model="ruleForm" class="demo-ruleForm">
          <table class="detailtable">
            <tr>
              <td>标题：</td>
              <td>
                <span>{{ruleForm.title}}</span>
              </td>
            </tr>
            <tr>
              <td>关键字：</td>
              <td>
                <span>{{ruleForm.keywords}}</span>
              </td>
            </tr>
            <tr>
              <td>图片：</td>
              <td>
                <img :src="imgUrl" height="600px"/>
              </td>
            </tr>
            <tr>
              <td>内容：</td>
              <td>
                <editor ref="ueditor" class="ueditor" :editread="true"></editor>
              </td>
            </tr>
          </table>
        </el-form>
      </div>
  </div>
</template>
<script>
import filedown from '@/components/file-download'
import editor from '@/components/ueditor/ueditor.vue'
import { homeManage } from '@/api'
import { fileReviewUrl } from '@/assets/js/common'
export default {
  data () {
    return {
      ruleForm: {
        title: '',
        keywords: '',
        content: '',
        fileInformations: ''
      },
      fileObject: {},
      imgUrl: ''
    }
  },
  components: {
    filedown,
    editor
  },
  methods: {
    // 数据
    detail () {
      homeManage.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.pictureInfo
        this.imgUrl = fileReviewUrl + res.data.pictureInfo.fileInformations[0].relativePath
        this.$refs.ueditor.setContent(this.ruleForm.content)
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  #rotate-detail {
    .contentbigbox .demo-ruleForm {
      .el-form-item__content {
        text-align: left;
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
