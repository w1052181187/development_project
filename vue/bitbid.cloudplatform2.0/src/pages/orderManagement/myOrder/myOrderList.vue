<template>
  <div class="my-order-list cloudcontent" id="tenderFeeList">
    <div class="topmain">
      <el-row :gutter="20" style="margin-bottom:20px;" class="row-span">
        <el-col :span="7">
          <span>服务内容：</span>
          <el-select v-model="ruleForm.serviceContent" placeholder="请选择" style="width:275px;" clearable>
            <el-option v-for="item in serviceList" :key="item.serviceContent" :label="item.value" :value="item.serviceContent">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="10">
          <span>订单日期：</span>
          <el-date-picker v-model="ruleForm.orderDate" type="daterange" range-separator="至"
                          start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-col>
      </el-row>
      <el-row :gutter="20" class="row-span">
        <el-col :span="15">
          <span>订单状态：</span>
          <el-radio v-model="orderStatus" :label="0">全部</el-radio>
          <el-radio v-model="orderStatus" :label="1">待审核</el-radio>
          <el-radio v-model="orderStatus" :label="2">待支付</el-radio>
          <el-radio v-model="orderStatus" :label="3">已支付</el-radio>
          <el-radio v-model="orderStatus" :label="4">已到期</el-radio>
          <el-radio v-model="orderStatus" :label="5">已取消</el-radio>
        </el-col>
        <el-col :span="6">
          <el-button type="primary" size="mini" @click="tenderFeeSrh">查询</el-button>
          <el-button  size="mini" @click="resetList">重置</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="main">
      <el-table :data="tableData" border tooltip-effect="dark" style="width: 100%"
        header-cell-class-name="tableheader" class="table-view">
        <el-table-column type="index" label="序号" width="60" align="center" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="orderNumber" label="订单编号" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderDate" label="订单日期" :formatter="formatDate" show-overflow-tooltip></el-table-column>
        <el-table-column prop="serviceContent" label="服务内容" :formatter="formatContent" show-overflow-tooltip></el-table-column>
        <el-table-column prop="maturityDate" label="到期日期" :formatter="formatDate" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderPrice" label="订单金额（元）" show-overflow-tooltip></el-table-column>
        <el-table-column prop="discountPrice" label="折后金额（元）" show-overflow-tooltip></el-table-column>
        <el-table-column prop="paymentPrice" label="实付金额（元）" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderStatus" label="订单状态" show-overflow-tooltip>
          <template slot-scope="scope">
            <span :class="{'wait-pay':scope.row.orderStatus == 2, 'end-date': scope.row.orderStatus == 4}">{{scope.row.orderStatus | formatData}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" align="center" width="240">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="seeRowDetail(scope.row)">详情</el-button>
            <el-button type="text" size="small" @click="cancelRowLine(scope.row)"
                       v-if="scope.row.orderStatus === 1 || scope.row.orderStatus === 2">取消</el-button>
            <el-button type="text" size="small" @click="paymentRowLine(scope.row)"
                       v-if="scope.row.orderStatus === 2">支付</el-button>
            <el-button type="text" size="small" @click="delRowLine(scope.row)"
                       v-if="scope.row.orderStatus === 5">删除</el-button>
            <el-button type="text" size="small" @click="renewRowLine(scope.row)"
                       v-if="scope.row.orderStatus === 4">续订</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background layout="prev, pager, next" :total="total" :page-size='pageSize'
        :current-page.sync="currentPage" @current-change="handleCurrentChange"
        @next-click="handleCurrentNext">
      </el-pagination>
    </div>
  </div>
</template>
<script>
import {serviceSubscription} from '@/api/system'
import {dateFormat} from '@/assets/js/common'
export default {
  components: {
  },
  data () {
    return {
      ruleForm: {
        serviceId: '',
        orderDate: ''
      },
      serviceList: [
        {serviceContent: 1, value: '平台'},
        {serviceContent: 2, value: '招标寻源'},
        {serviceContent: 3, value: 'APP'},
        {serviceContent: 4, value: '机构门户'}
      ], // 全部标段下拉列表
      orderStatus: 0,
      tableData: [],
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      total: null
    }
  },
  filters: {
    formatData (cellValue) {
      let obj = {
        1: '待审核',
        2: '待支付',
        3: '已支付',
        4: '已到期',
        5: '已取消'
      }
      return obj[cellValue]
    }
  },
  created () {
    this.getList(this.pageNo)
  },
  methods: {
    formatDate (row, column, cellValue, index) {
      return dateFormat(cellValue, 'yyyy-MM-dd')
    },
    formatContent (row, column, cellValue, index) {
      let obj = {
        1: '平台',
        2: '招标寻源',
        3: 'APP',
        4: '机构门户'
      }
      return obj[cellValue]
    },
    // 查询按钮
    tenderFeeSrh () {
      this.currentPage = 1
      this.getList(0)
    },
    // 重置按钮
    resetList () {
      this.ruleForm.serviceContent = ''
      this.ruleForm.orderDate = ''
      this.orderStatus = 0
      this.currentPage = 1
      this.getList(0)
    },
    // 查看按钮
    seeRowDetail (rowData) {
      this.$router.push({path: `/orderManagement/myOrder/myOrderDetail/${rowData.objectId}`})
    },
    // 取消按钮
    cancelRowLine (rowData) {
      this.$confirm('确认取消吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        rowData.orderStatus = 5
        serviceSubscription.update(rowData).then(res => {
          this.getList(this.pageNo)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    // 支付按钮
    paymentRowLine (rowData) {
      serviceSubscription.getPaymentUrl(rowData.code).then(res => {
        if (res) {
          let url = res.data.url
          window.open(url)
          this.$confirm('是否支付成功?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.getList(this.pageNo)
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
            this.getList(this.pageNo)
          })
        }
      })
    },
    // 删除按钮
    delRowLine (rowData) {
      this.$confirm('确认删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        rowData.isDelete = 1
        serviceSubscription.update(rowData).then(res => {
          this.getList(this.pageNo)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    // 续订按钮
    renewRowLine (rowData) {
      this.$router.push({path: '/system/serviceSubscription'})
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList(this.pageNo)
    },
    handleCurrentNext (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getList(this.pageNo)
    },
    getList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        remoteCall: false,
        serviceContent: this.ruleForm.serviceContent || null,
        orderStatus: this.orderStatus || null
      }
      if (this.ruleForm.orderDate) {
        query.orderDateLowerLimit = this.ruleForm.orderDate[0]
        query.orderDateUpperLimit = this.ruleForm.orderDate[1]
      }
      serviceSubscription.getList(query).then(res => {
        let data = res.data.serviceSubscriptionList
        if (data) {
          this.tableData = data.list
          this.total = data.total
        }
      })
    }
  }
}
</script>
<style lang="less">
#tenderFeeList {
  .topmain {
    padding: 20px;
    .row-span {
      width: 1300px;
      span {
        font-size: 14px;
      }
    }
  }
  .table-view {
    .wait-pay {
      color: #008000;
    }
    .end-date {
      color: #FF0000;
    }
  }
}
.my-order-list{
  .el-select{
    width: 120px;
  }
}
</style>
