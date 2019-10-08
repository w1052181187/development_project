<template>
  <div class="cloudcontent" id="otherIncomeAdd">
    <div class="main viewdetails">
      <el-form :model="otnerIncomeDetail">
        <el-form-item  label="支出项目：">
          <span>{{otnerIncomeDetail.bidderName}}</span>
        </el-form-item>
        <el-form-item  label="支付金额（元）：">
          <span>{{otnerIncomeDetail.amount}}</span>
        </el-form-item>
        <el-form-item  label="支付方式：">
          <span>{{otnerIncomeDetail.paymentMethod | filterPaymentMethod}}</span>
        </el-form-item>
        <el-form-item  label="支付时间：">
          <span>{{otnerIncomeDetail.paymentTime ? otnerIncomeDetail.paymentTime.slice(0, 10)  : ''}}</span>
        </el-form-item>
        <el-form-item  label="相关标段：">
          <el-table
            :data="bidSectionList"
            border
            style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column
              type="index"
              label="序号"
              width="60"
              align="center"
            ></el-table-column>
            <el-table-column
              prop="bidSectionCode"
              label="标段编号"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="bidSectionName"
              label="标段名称"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item  label="备注：">
          <span>{{otnerIncomeDetail.remarks}}</span>
        </el-form-item>
        <el-form-item label="操作记录：">
          <el-table :data="detailTableList" border style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
            <el-table-column prop="operation" label="操作" show-overflow-tooltip></el-table-column>
            <el-table-column prop="creatorName" label="操作人" show-overflow-tooltip></el-table-column>
            <el-table-column prop="createDate" label="操作时间" show-overflow-tooltip></el-table-column>
          </el-table>
        </el-form-item>
        <el-form-item class="submit-radio">
          <el-button class="cancal" @click="close()">关闭</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>
<script>
import {settlementItem} from '@/api/project'
export default {
  components: {
  },
  data () {
    return {
      otnerIncomeDetail: {},
      bidSectionList: [], // 页面上展示出来的可选择的标段信息
      detailTableList: [],
      currentPage: 1,
      tenderProjectName: ''
    }
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
  mounted () {
    this.getDetail()
  },
  methods: {
    getDetail () {
      // tenderProject.getOne(this.$route.query.tenderSystemCode).then((res) => {
      //   this.tenderProjectName = res.data.tenderProject.tenderProjectName
      // })
      settlementItem.getSettlementItem(this.$route.params.objectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.otnerIncomeDetail = res.data.settlementItem
          this.bidSectionList = res.data.settlementItem.bidSectionList
          this.detailTableList = res.data.settlementItem.itemOperationRecordList
        }
      })
    },
    close () {
      this.$router.go(-1)
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    }
  }
}
</script>
<style lang="less">
  #otherIncomeAdd {
    .el-cascader, .el-select{
      width: 100%;
    }
    .el-radio__label{
      display: none;
    }
    .choiceBtn{
      border: 1px solid #3f63f6;
      height: 32px;
      padding: 7px 12px;
      margin-bottom: 12px;
    }
    .bidSectionNameNumberSelect{
      width: 109px;
    }
    .detailTableTitle{
      border-left: 1px solid #dcdfe6;
      border-right: 1px solid #dcdfe6;
      border-top: 1px solid #dcdfe6;
      height: 35px;
      line-height: 35px;
      padding-left: 5px;
      box-sizing: border-box;
    }
  }
</style>
