<template>
  <div class="cloudcontent">
    <div class="main viewdetails">
      <el-form :model="detailForm">
        <el-row>
          <el-form-item label="缴费单位：">
            <span>{{detailForm.bidderName}}</span>
          </el-form-item>
        </el-row>
        <el-row v-if="pageText === '保证金'">
          <el-form-item label="缴纳金额（元）：">
            <span>{{detailForm.amount}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="缴费方式：">
            <span>{{detailForm.paymentMethod | filterPaymentMethod}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="缴费时间：">
            <span>{{detailForm.paymentTime ? detailForm.paymentTime.slice(0, 10) : ''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="缴费标段：">
            <!-- <span>{{detailForm.paymentTenders}}</span> -->
            <el-table :data="detailForm.bidSectionList" border style="width: 100%" header-cell-class-name="tableheader">
              <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
              <el-table-column prop="bidSectionCode" label="标段编号" show-overflow-tooltip></el-table-column>
              <el-table-column prop="bidSectionName" label="标段名称" show-overflow-tooltip></el-table-column>
            </el-table>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="退还金额（元）：">
            <span>{{detailForm.returnAmount}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="退还方式：">
            <span>{{detailForm.returnMethod | filterPaymentMethod}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="退还时间：">
            <span>{{detailForm.returnTime ? detailForm.returnTime.slice(0, 10) : ''}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注：">
            <span>{{detailForm.remarks}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="操作记录：">
            <el-table :data="detailForm.itemOperationRecordList" border style="width: 100%" header-cell-class-name="tableheader">
              <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
              <el-table-column prop="operation" label="操作" show-overflow-tooltip></el-table-column>
              <el-table-column prop="creatorName" label="操作人" show-overflow-tooltip></el-table-column>
              <el-table-column prop="createDate" label="操作时间" show-overflow-tooltip></el-table-column>
            </el-table>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item class="submit-radio">
            <el-button class="cancal" @click="$router.go(-1)">关闭</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
// import editor from '@/components/ueditor/ueditor.vue'
// import { qualificationSubject } from '@/api/resource'

import { settlementItem } from '@/api/project'
export default {
  name: '',
  components: {
    // editor
  },
  data () {
    return {
      pageText: '', // 哪种收支项目详情
      detailForm: {
        ruleForm: '',
        amountPayable: '',
        paymentMethod: '',
        paymentTime: '',
        paymentTendersData: [],
        returnAmount: '',
        returnType: '',
        returnTime: '',
        remarks: '',
        detailTableData: []
      },
      detailTableData: [
        {operation: '收费', operationPerson: '李三', operationTime: '2018-11-10'}
      ]
    }
  },
  mounted () {
    this.pageText = this.$route.query.text
    this.getDetailData()
  },
  filters: {
    filterPaymentMethod (paymentMethod) {
      if (paymentMethod === 1) {
        return '支付宝'
      } else if (paymentMethod === 2) {
        return '微信'
      } else if (paymentMethod === 3) {
        return '现金'
      } else if (paymentMethod === 4) {
        return '银联'
      } else if (paymentMethod === 5) {
        return '支票'
      } else if (paymentMethod === 6) {
        return '保函'
      } else if (paymentMethod === 9) {
        return '其他'
      }
    }
  },
  methods: {
    getDetailData () {
      settlementItem.getSettlementItem(this.$route.params.objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.detailForm = res.data.settlementItem
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
