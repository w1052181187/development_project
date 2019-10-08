<template>
  <div id="projectdetailbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file' }">采购邀请备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file/check-confirm' + handleCheckList(this.$route.query.gongGaoId)}">查看确认情况</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/procurement-invite-file/check-confirm/supplier-confirmation' + handleList(this.$route.query.biaoDuanId, this.$route.query.gongGaoId)}">供应商确认情况</el-breadcrumb-item>
        <el-breadcrumb-item>详情</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/procurement-invite-file/check-confirm/supplier-confirmation">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.biaoDuanBianHao}}</td>
          <td>包组名称：</td>
          <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
        </tr>
        <tr>
          <td>供应商名称：</td>
          <td colspan="3">{{proForm.nameZH}}</td>
          <td>联系人：</td>
          <td colspan="3">{{proForm.lianXiRen}} </td>
        </tr>
        <tr>
          <td>联系人固定电话：</td>
          <td colspan="3">{{proForm.mobile}}</td>
          <td>联系人移动电话：</td>
          <td colspan="3">{{proForm.phone}}</td>
        </tr>
        <tr>
          <td>联系人电子邮件：</td>
          <td colspan="3">{{proForm.email}}</td>
          <td>确认状态：</td>
          <td colspan="3" v-if="proForm.queRenZhuangTai === 1">已确认</td>
          <td colspan="3" v-if="proForm.queRenZhuangTai === 2">已拒绝</td>
        </tr>
        <tr>
          <td>说明：</td>
          <td colspan="7">{{proForm.reason}}</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
import { pushData } from '@/api'
export default {
  components: {
  },
  data () {
    return {
      proForm: {}
    }
  },
  methods: {
    // 列表
    detail () {
      pushData.supplierDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.gongYsAffirmDetail)
      })
    },
    handleCheckList (id) {
      return '/' + id
    },
    handleList (id, gongGaoId) {
      return '/' + id + '?gongGaoId=' + gongGaoId
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
