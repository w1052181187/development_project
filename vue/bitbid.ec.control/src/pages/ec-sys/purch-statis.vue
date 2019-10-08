<template>
  <div id="purch-statis-page"  class="cont-view">
    <!-- 面包屑区域start -->
    <div class="bread-box">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>电商管理系统</el-breadcrumb-item>
        <el-breadcrumb-item>采购人消费统计</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 面包屑区域end -->
    <!-- 搜索区域start -->
    <div class="search-box">
      <el-row :gutter="20">
        <el-form :model="searchForm" label-width="110px" class="demo-ruleForm">
          <el-col :span="5">
            <el-form-item label="采购人:">
              <el-input v-model="searchForm.messageLike" placeholder="采购人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="统计时间:">
              <el-date-picker
                style="width:260px;"
                v-model="searchForm.time"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['00:00:00', '23:59:59']"
                value-format="yyyy-MM-dd HH:mm:ss">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="onSearch">查询</el-button>
          </el-col>
        </el-form>
      </el-row>
    </div>
    <!-- 搜索区域end -->
    <div class="table-box">
      <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column
          label="序号"
          type="index"
          width="60"
          :index="computedIndex"
          align="center">
        </el-table-column>
        <el-table-column
          prop="buyName"
          label="采购人"
          :formatter="simpleFormatData"
          align="center">
        </el-table-column>
        <el-table-column
          prop="countNumber"
          label="成交订单数"
          align="center">
        </el-table-column>
        <el-table-column
          prop="countMoney"
          label="成交订单金额"
          :formatter="simpleFormatData"
          align="center">
        </el-table-column>
      </el-table>
      <div class="pagination-box">
        <!--分页-->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="page.total"
          :page-size='page.pageSize'
          :current-page.sync="page.currentPage"
          @current-change="handlePage"
          @next-click="handlePage">
        </el-pagination>
        <!--分页-->
      </div>
    </div>
  </div>
</template>
<script>
import {transactionRecord} from '@/api'
export default {
  name: 'purch-statis',
  data () {
    return {
      searchForm: {},
      tableData: [],
      page: {
        total: 0,
        pageSize: 10,
        pageNo: 0,
        currentPage: 1
      }
    }
  },
  methods: {
    /** 搜索 */
    onSearch () {
      this.page.pageNo = 0
      this.page.currentPage = 1
      this.getTableData()
    },
    /** 分页 */
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    },
    /** 普通格式化数据，空的时候展示"---" */
    simpleFormatData (row, col, cellValue) {
      return cellValue || '-----'
    },
    /** 序号计算 */
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    /** 获取列表数据 */
    getTableData () {
      this.searchForm.pageNo = this.page.pageNo
      this.searchForm.pageSize = this.page.pageSize
      this.searchForm.ecPlatformId = this.$store.getters.authUser.userId
      if (this.searchForm.time && this.searchForm.time.length > 1) {
        this.searchForm.startCountTime = this.searchForm.time[0]
        this.searchForm.endCountTime = this.searchForm.time[1]
      } else {
        delete this.searchForm.startCountTime
        delete this.searchForm.endCountTime
      }
      let queryModel = Object.assign({}, this.searchForm)
      delete queryModel.time
      transactionRecord.countProcurementByPlatformId(queryModel).then(res => {
        this.tableData = res.data.transactionRecordList.list
        this.page.total = res.data.transactionRecordList.total
      })
    }
  },
  mounted () {
    this.getTableData()
  }
}
</script>
<style lang="less">
#purch-statis-page {
  .demo-ruleForm {
    margin-left: -50px;
  }
}
</style>
