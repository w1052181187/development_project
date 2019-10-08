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
        <el-form :model="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="标题:">
            <span>{{ruleForm.title}}</span>
          </el-form-item>
          <el-form-item label="关键字:">
            <span>{{ruleForm.keywords}}</span>
          </el-form-item>
          <el-form-item label="URL地址:">
            <span>{{ruleForm.url}}</span>
          </el-form-item>
          <el-form-item label="图片:">
            <filedown :fileObject="fileObject"></filedown>
          </el-form-item>
        </el-form>
      </div>
  </div>
</template>
<script>
import filedown from '@/components/file-download'
import { homeManage } from '@/api'
export default {
  data () {
    return {
      ruleForm: {},
      fileObject: {}
    }
  },
  components: {
    filedown
  },
  methods: {
    // 数据
    detail () {
      homeManage.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.pictureInfo
        this.fileObject = res.data.pictureInfo.fileInformations[0]
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
  }
</style>
