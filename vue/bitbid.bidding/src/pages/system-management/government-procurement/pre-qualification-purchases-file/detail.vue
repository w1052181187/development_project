<template>
  <div id="prequalipurbox" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system-management' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>政府采购备案</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system-management/government-procurement/pre-qualification-purchases-file' }">资格预审文件购买情况备案</el-breadcrumb-item>
        <el-breadcrumb-item>查看</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="returnboxbig">
        <router-link tag='span' to="/system-management/government-procurement/pre-qualification-purchases-file">返回</router-link>
      </div>
    </div>
    <div class="contentbigbox">
      <table class="detailtable">
        <tr>
          <td>采购项目编号：</td>
          <td  colspan="3">{{proForm.faBaoBianHao}}</td>
          <td>采购公告名称：</td>
          <td  colspan="3">{{proForm.gongGaoMingCheng}}</td>
        </tr>
        <tr>
          <td>包组编号：</td>
          <td colspan="3">{{proForm.biaoDuanBianHao}}</td>
          <td>包组名称：</td>
          <td colspan="3">{{proForm.biaoDuanMingCheng}}</td>
        </tr>
        <tr>
          <td>投标人：</td>
          <td colspan="3">{{proForm.tenderName}}</td>
          <td>费用类型：</td>
          <td colspan="3">
            <span v-if="proForm.type === 1">
              资格预审文件费用
            </span>
            <span v-if="proForm.type === 2">
              招标文件费用
            </span>
            <span v-if="proForm.type === 3">
              投标保证金
            </span>
          </td>
        </tr>
        <tr>
          <td>缴费状态：</td>
          <td colspan="3">
            <span v-if="proForm.status === 1">
              未支付
            </span>
            <span v-if="proForm.status === 2">
              已支付
            </span>
          </td>
          <td>缴费方式：</td>
          <td colspan="3">
            <span v-if="proForm.paymentMethod === 1">
              线下支付
            </span>
            <span v-if="proForm.paymentMethod === 2">
              线上支付
            </span>
            <span v-if="proForm.paymentMethod === 3">
              其他支付
            </span>
            <span v-if="proForm.paymentMethod === 4">
              现金支付
            </span>
            <span v-if="proForm.paymentMethod === 5">
              线下汇款
            </span>
            <span v-if="proForm.paymentMethod === 6">
             投标保函
            </span>
            <span v-if="proForm.paymentMethod === 7">
              支票
            </span>
            <span v-if="proForm.paymentMethod === 8">
              其他
            </span>
          </td>
        </tr>
        <tr>
          <td>缴费金额：</td>
          <td colspan="3">{{proForm.price}}</td>
          <td>确认时间：</td>
          <td colspan="3">{{proForm.confirmedDate}}</td>
        </tr>
        <tr>
          <td>说明：</td>
          <td colspan="5">{{proForm.reason}}</td>
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
    detail () {
      pushData.zbOrderDetail(this.$route.params.objectId).then((res) => {
        this.proForm = JSON.parse(res.data.zbOrderDetail)
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
  #prequalipurbox{
  }
</style>
