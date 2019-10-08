<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/pre-qualification-results-file' }">资格预审结果通知书备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看报告</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/pre-qualification-results-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>采购项目编号：</td>
          <td  colspan="3">{{proForm.faBaoBianHao}}</td>
          <td>公告名称：</td>
          <td  colspan="3">{{proForm.gongGaoMingCheng}}</td>
        </tr>
        <tr>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.biaoDuanBianHao}}</td>
          <td>包组名称：</td>
          <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
        </tr>
        <tr>
          <td>资格审查报告：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fileName_local" :filePath = "proForm.fileName_server" ></file-down>
          </td>
        </tr>
        <tr>
          <td>提交人：</td>
          <td colspan="3">{{proForm.creater}}</td>
          <td>提交时间：</td>
          <td colspan="3">{{proForm.createTime}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import fileDown from '../../../../components/system-management/FileDownload'
import { pushData } from '@/api'
export default {
  components: {
    fileDown
  },
  data () {
    return {
      // 详情
      proForm: {}
    }
  },
  methods: {
    detail () {
      pushData.zgscBgFileDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.zgscBgFileDetail)
      })
    }
  },
  mounted () {
    this.detail()
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
</style>
