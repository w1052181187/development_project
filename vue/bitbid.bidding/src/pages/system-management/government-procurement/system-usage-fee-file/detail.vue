<template>
  <div id="systemfeebox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/system-usage-fee-file' }">系统使用费缴纳情况备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/system-usage-fee-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>采购项目编号：</td>
          <td  colspan="3">{{proForm.name}}</td>
          <td>采购公告名称：</td>
          <td  colspan="3">{{proForm.num}}</td>
        </tr>
        <tr>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.address}}</td>
          <td>包组名称：</td>
          <td colspan="3">{{proForm.detailedaddress}}</td>
        </tr>
        <tr>
          <td>投标人：</td>
          <td colspan="3">{{proForm.legalperson}}</td>
          <td>费用类型：</td>
          <td colspan="3">{{proForm.classification}}</td>
        </tr>
        <tr>
          <td>缴费状态：</td>
          <td colspan="3">{{proForm.funding}}{{proForm.funding2}}</td>
          <td>缴费方式：</td>
          <td colspan="3">{{proForm.scale}}</td>
        </tr>
        <tr>
          <td>缴费金额：</td>
          <td colspan="3">{{proForm.approvalname}}</td>
          <td>确认时间：</td>
          <td colspan="3">{{proForm.approvalnum}}</td>
        </tr>
        <tr>
          <td>说明：</td>
          <td colspan="7">{{proForm.approvalname}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  components: {
  },
  data () {
    return {
      proForm: {}
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>
<style lang="less">
  @import '../../../../assets/css/system/common.css';
  #systemfeebox{
  }
</style>
