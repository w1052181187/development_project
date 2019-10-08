<template>
  <div class="center_box" id="contract_box">
    <el-form :model="projectForm" ref="projectForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">详情</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father">
              <table class="detailtable">
                <tr>
                  <td>合作单位：</td>
                  <td colspan="2">
                    <span :title="projectForm.enterpriseName">{{projectForm.enterpriseName}}</span>
                  </td>
                  <td>订单号：</td>
                  <td colspan="2">
                    <span :title="projectForm.ordercode">{{projectForm.ordercode}}</span>
                  </td>
                </tr>
                <tr>
                  <td>申请渠道：</td>
                  <td colspan="2">
                    <span :title="projectForm.versionName">{{projectForm.versionName}}</span>
                  </td>
                  <td>申请单位：</td>
                  <td colspan="2">
                    <span :title="projectForm.namezh">{{projectForm.namezh}}</span>
                  </td>
                </tr>
                <tr>
                  <td>CA类型：</td>
                  <td colspan="2">
                    <span :title="projectForm.catype" v-if="Number(projectForm.catype) === 1">企业</span>
                    <span :title="projectForm.catype" v-if="Number(projectForm.catype) === 2">个人</span>
                  </td>
                  <td>开通年数：</td>
                  <td colspan="2">
                    <span :title="projectForm.kaiTongShu">{{projectForm.kaiTongShu}}</span>
                  </td>
                </tr>
                <tr>
                  <td>缴费时间：</td>
                  <td colspan="2">
                    <span :title="projectForm.createdate">{{projectForm.createdate}}</span>
                  </td>
                  <td>缴费状态：</td>
                  <td colspan="2">
                    <span :title="projectForm.projectName">已缴费</span>
                  </td>
                </tr>
                <tr>
                  <td>缴费金额：</td>
                  <td colspan="2">
                    <span :title="projectForm.price">{{projectForm.price}}</span>
                  </td>
                  <td>缴费方式：</td>
                  <td colspan="2">
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 1">转账</span>
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 2">支付宝</span>
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 3">网银</span>
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 4">现金</span>
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 5">汇款</span>
                    <span :title="projectForm.zhifufangshi" v-if="Number(projectForm.zhifufangshi) === 6">其他</span>
                  </td>
                </tr>
              </table>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button @click="returnBtn" class="cancel_btn">返回</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import {zbOrderFlow} from '@/api'
export default {
  name: '',
  data () {
    return {
      projectForm: {}
    }
  },
  methods: {
    list () {
      zbOrderFlow.queryOne(this.$route.params.objectId).then((res) => {
        this.projectForm = res.data.zbOrderFlow
        if (res.data.zbOrderFlow.agencyEnterprise !== null) {
          this.projectForm.enterpriseName = res.data.zbOrderFlow.agencyEnterprise.enterpriseName
        }
        this.$set(this.projectForm, 'versionName', res.data.zbOrderFlow.appForChaVerMana.versionName)
        this.$set(this.projectForm, 'namezh', res.data.zbOrderFlow.payUser.namezh)
        this.$set(this.projectForm, 'zhifufangshi', res.data.zbOrderFlow.sysUserOrderCa.zhifufangshi)
      })
    },
    // 返回
    returnBtn () {
      this.$router.push({path: `/admin/record/ca/index`})
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
</style>
