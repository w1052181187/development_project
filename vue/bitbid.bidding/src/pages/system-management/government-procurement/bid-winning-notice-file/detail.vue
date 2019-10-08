<template>
  <div id="bidwinlbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/bid-winning-notice-file' }">中标通知书备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/bid-winning-notice-file">返回</router-link>
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
          <td>中标人：</td>
          <td colspan="3">{{proForm.zhongBiaoRenName}}</td>
          <td>中标价格(元)：</td>
          <td colspan="3">￥{{proForm.zhongBiaoJiaGe}}</td>
        </tr>
        <tr>
          <td>附件：</td>
          <td colspan="7">
            <file-down :fileName="proForm.fuJianFile" :filePath = "proForm.fuJianFileName" ></file-down>
          </td>
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
      proForm: {},
      currentPage: 1
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    detail () {
      pushData.adviceDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.adviceDetail)
        this.total = JSON.parse(res.data.total)
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
  #bidwinlbox{
    .file-tab {
      th {
        background-color: #f7f8fa;
      }
    }
  }
</style>
