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
                  <td>采购项目编号：</td>
                  <td colspan="2">
                    <span :title="projectForm.faBaoNumber">{{projectForm.faBaoNumber}}</span>
                  </td>
                </tr>
                <tr>
                  <td>采购公告名称：</td>
                  <td colspan="2">
                    <span :title="projectForm.faBaoMingCheng">{{projectForm.faBaoMingCheng}}</span>
                  </td>
                  <td>包组编号：</td>
                  <td colspan="2">
                    <span :title="projectForm.projectName">{{projectForm.biaoDuanNumber}}</span>
                  </td>
                </tr>
                <tr>
                  <td>包组名称：</td>
                  <td colspan="2">
                    <span :title="projectForm.biaoDuanMingCheng">{{projectForm.biaoDuanMingCheng}}</span>
                  </td>
                  <td>投标人：</td>
                  <td colspan="2">
                    <span :title="projectForm.payUserName">{{projectForm.payUserName}}</span>
                  </td>
                </tr>
                <tr>
                  <td>费用类型：</td>
                  <td colspan="2">
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 0">CA费用</span>
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 1">资格预审文件费用</span>
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 2">招标文件费用</span>
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 3">投标保证金</span>
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 4">平台使用费</span>
                    <span :title="projectForm.chargeitem" v-if="projectForm.chargeitem === 5">中标服务费</span>
                  </td>
                  <td>缴费状态：</td>
                  <td colspan="2">
                    <span :title="projectForm.projectName">已支付</span>
                  </td>
                </tr>
                <tr>
                  <td>缴费方式：</td>
                  <td colspan="2" v-if="projectForm.zbOrder">
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 1">线下支付</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 2">线上支付</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 3">其他支付</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 4">现金支付</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 5">线下汇款</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 6">投标保函</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 7">支票</span>
                    <span :title="projectForm.zbOrder.paymentmethod" v-if="Number(projectForm.zbOrder.paymentmethod) === 8">其他</span>
                  </td>
                  <td>缴费金额：</td>
                  <td colspan="2">
                    <span :title="projectForm.price">{{projectForm.price}}</span>
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
        console.log(this.projectForm)
        if (res.data.zbOrderFlow.agencyEnterprise) {
          this.projectForm.enterpriseName = res.data.zbOrderFlow.agencyEnterprise.enterpriseName
        }
        if (res.data.zbOrderFlow.appForChaVerMana) {
          this.projectForm.versionName = res.data.zbOrderFlow.appForChaVerMana.versionName
        }
        this.projectForm.payUserName = this.projectForm.payUser.namezh
      })
    },
    // 返回
    returnBtn () {
      this.$router.push({path: `/admin/record/tender-fee/index`})
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
</style>
