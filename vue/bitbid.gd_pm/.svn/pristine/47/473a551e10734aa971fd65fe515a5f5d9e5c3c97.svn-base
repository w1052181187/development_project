<template>
  <div class="center_box" id="contract_box">
    <!--CA记录-->
    <el-form v-show="projectForm.chargeitem === 0" :model="projectForm" ref="projectForm" label-width="140px" class="demo-ruleForm">
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
                    <span :title="projectForm.appForChaVerMana.versionName">{{projectForm.appForChaVerMana.versionName}}</span>
                  </td>
                  <td>申请单位：</td>
                  <td colspan="2">
                    <span :title="projectForm.applUnitSysUser">{{projectForm.applUnitSysUser}}</span>
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
                    <span  v-if="Number(projectForm.status) === 1">已支付</span>
                    <!--<span  v-if="Number(projectForm.sysUserOrderCa.dingdanzhuangtai) === 2">已支付</span>-->
                  </td>
                </tr>
                <tr>
                  <td>缴费金额：</td>
                  <td colspan="2">
                    <span :title="projectForm.price">{{projectForm.price}}</span>
                  </td>
                  <td>缴费方式：</td>
                  <td colspan="2">
                    <span v-if="Number(projectForm.paymentchannel) === 1">线下支付</span>
                    <span v-if="Number(projectForm.paymentchannel) === 2">线上支付</span>
                    <span v-if="Number(projectForm.paymentchannel) === 3">其他支付</span>
                    <span v-if="Number(projectForm.paymentchannel) === 4">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 5">线下汇款</span>
                    <span v-if="Number(projectForm.paymentchannel) === 6">投标保函</span>
                    <span v-if="Number(projectForm.paymentchannel) === 7">支票</span>
                    <span v-if="Number(projectForm.paymentchannel) === 8">其他</span>
                  </td>
                </tr>
              </table>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
    <!--1.资格预审文件费用、2.招标文件费用 1和2合为标书费-->
    <el-form v-show="projectForm.chargeitem === 1 || projectForm.chargeitem === 2" :model="projectForm" ref="projectForm" label-width="140px" class="demo-ruleForm">
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
                    <span :title="projectForm.appForChaVerMana.versionName">{{projectForm.appForChaVerMana.versionName}}</span>
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
                    <span :title="projectForm.biaoDuanNumber">{{projectForm.biaoDuanNumber}}</span>
                  </td>
                </tr>
                <tr>
                  <td>包组名称：</td>
                  <td colspan="2">
                    <span :title="projectForm.biaoDuanMingCheng">{{projectForm.biaoDuanMingCheng}}</span>
                  </td>
                  <td>投标人：</td>
                  <td colspan="2">
                    <span :title="projectForm.payUser.name">{{projectForm.payUser.name}}</span>
                  </td>
                </tr>
                <tr>
                  <td>费用类型：</td>
                  <td colspan="2">
                    <span  v-if="Number(projectForm.chargeitem) === 0">CA费用</span>
                    <span  v-if="Number(projectForm.chargeitem) === 1">标书费</span>
                    <span  v-if="Number(projectForm.chargeitem) === 2">标书费</span>
                    <span  v-if="Number(projectForm.chargeitem) === 3">投标保证金</span>
                    <span  v-if="Number(projectForm.chargeitem) === 4">平台使用费</span>
                  </td>
                  <td>缴费状态：</td>
                  <td colspan="2">
                    <span  v-if="Number(projectForm.status) === 1">未支付</span>
                    <span  v-if="Number(projectForm.status) === 2">已支付</span>
                  </td>
                </tr>
                <tr>
                  <td>缴费金额：</td>
                  <td colspan="2">
                    <span :title="projectForm.price">{{projectForm.price}}</span>
                  </td>
                  <td>缴费方式：</td>
                  <td colspan="2">
                    <span v-if="Number(projectForm.paymentchannel) === 1">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 2">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 3">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 4">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 5">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 6">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 7">支付宝</span>
                    <span v-if="Number(projectForm.paymentchannel) === 8">支付宝</span>
                  </td>
                </tr>
              </table>
            </div>
            <el-form-item class="operation_btn_box">
              <el-button @click="$router.go(-1)" class="cancel_btn">返回</el-button>
            </el-form-item>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import { invoiceAdmin } from '@/api'
export default {
  name: '',
  data () {
    return {
      projectForm: {
        agencyEnterprise: {},
        appForChaVerMana: {},
        zbOrder: {},
        payUser: {},
        enterpriseName: ' '
      }
    }
  },
  methods: {
    list () {
      invoiceAdmin.noapplyinvoiceAdminDetail(this.$route.params.objectId).then((res) => {
        console.log(res)
        this.projectForm = res.data.zbOrderFlow
        if (this.projectForm.zbOrder !== null) {
          this.projectForm.status = this.projectForm.zbOrder.status
        } else if (this.projectForm.sysUserOrderCa !== null) {
          this.projectForm.status = this.projectForm.sysUserOrderCa.caiwudingdanzhuangtai
        }
        if (this.projectForm.agencyEnterprise !== null) {
          this.projectForm.enterpriseName = this.projectForm.agencyEnterprise.enterpriseName
        }
        if (this.projectForm.chargeitem === 0) {
          this.projectForm.applUnitSysUser = this.projectForm.payusername
        }
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style lang="less">
</style>
