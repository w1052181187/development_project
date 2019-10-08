<template>
  <div id="rotate-add" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>信息管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/info-manage/policylaw' }">政策法规</el-breadcrumb-item>
        <el-breadcrumb-item>查看政策法规</el-breadcrumb-item>
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
        <el-form-item label="发布日期:">
          <span>{{timestampToTime(ruleForm.publishDate)}}</span>
        </el-form-item>
        <el-form-item label="实施日期:">
          <span>{{timestampToTime(ruleForm.materialDate)}}</span>
        </el-form-item>
        <el-form-item label="内容:" class="form-ueditor">
          <ueditor ref="content"></ueditor>
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
      ruleForm: {}
    }
  },
  components: {
    ueditor
  },
  methods: {
    detail () {
      infoManageLaw.detail(this.$route.params.objectId).then((res) => {
        this.ruleForm = res.data.lawsAndRegulations
        this.$refs.content.setContent(res.data.lawsAndRegulations.content)
      })
    },
    // 时间戳转换
    timestampToTime (row) {
      var date = new Date(row) // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-'
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-'
      var D = date.getDate() + ' '
      return Y + M + D
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
