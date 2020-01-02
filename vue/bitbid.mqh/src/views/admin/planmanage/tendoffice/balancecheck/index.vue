<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="balancecheck-page">
    <div v-if="this.$route.path == '/admin/planmanage/balancecheck'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancemanage' }">资金平衡表审核</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!--搜索区域start-->
      <div class="search-box balance-search">
        <el-form :model="searchForm" :inline="true" class="demo-form-inline" style="text-align: left;">
          <el-form-item label="年度:">
            <el-select v-model="searchForm.year" clearable placeholder="请选择">
              <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目单位:">
            <el-input v-model="searchForm.projectEnterpriseName" placeholder="请输入项目单位名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--搜索区域end-->
      <div class="maincont-box planaccept-cont">
        <!-- 表格区域start -->
        <el-table
          :data="tableData"
          :row-class-name="tableRowClassName"
          border
          style="width: 100%">
          <el-table-column
            type="index"
            label="序号"
            align="center"
            :index="indexMethod"
            width="55">
          </el-table-column>
          <el-table-column
            prop="projectEnterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="year"
            label="年度"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column
            prop="fixedQuota"
            label="专项资金额度（万元）"
            align="right">
          </el-table-column>
          <el-table-column
            prop="constructionQuota"
            label="工程建设额度（万元）"
            align="right">
          </el-table-column>
          <el-table-column
            prop="submitterName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="submitDate"
            label="提交时间"
            align="center"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="160">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'check')" type="text" size="medium" v-if="scope.row.checkFlag && $store.getters.permissions.includes('/planmanage/balancecheck/approval')">审核</el-button>
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/planmanage/balancecheck/detail')">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 表格区域end -->
        <!--分页-->
        <div class="block-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="pageSizeList"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
      </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
// 实例
import {balanceSheet} from 'api/index'
import {formatDate} from 'common/js/date.js'
export default {
  data () {
    return {
      yearList: this.getYearMethed(),
      createYearList: this.getYearMethed(),
      projectUnitList: [], // 项目单位
      statusList: [
        {label: '已提交', value: 1},
        {label: '未提交', value: 0}
      ],
      dialogVisible: false,
      dialogForm: {},
      searchForm: {}, // 搜索
      tableData: [],
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  watch: {
    '$route': 'getTableData'
  },
  created () {
    this.getTableData()
  },
  methods: {
    /** 格式化申请时间 */
    filterDate (row, column) {
      if (row.submitDate) {
        let date = new Date(row.submitDate)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    /** 获取页面数据 */
    getTableData () {
      balanceSheet.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        year: this.searchForm.year || null,
        messageLike: this.searchForm.projectEnterpriseName || null,
        status: 1
      }).then((res) => {
        this.tableData = res.data.balanceSheetList.list
        this.total = res.data.balanceSheetList.total
        if (this.tableData.length > 0) {
          this.tableData.forEach(item => {
            if (item.annualProjectPlan) {
              if (item.annualProjectPlan.status === 3) {
                item.checkFlag = false
              } else {
                item.checkFlag = true
              }
            } else {
              item.checkFlag = true
            }
          })
        }
        if (this.total <= this.pageSize) {
          this.pageNo = 0
        }
      })
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 计算年份-初始值为2017 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let nextYear = date.getFullYear() + 1
      let previousYear = date.getFullYear() - 1
      for (let i = previousYear; i <= nextYear; i++) {
        arr.push(i)
      }
      arr = arr.reverse()
      return arr
    },
    /** 搜索 */
    searchFun () {
      this.getTableData()
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.getTableData(this.pageNo, this.pageSize, function (resp) {
        likeThis.total = resp.data.Data.Total
      })
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/planmanage/balancecheck/details', query: {code: row.code, year: row.year, projectEnterpriseId: row.enterpriseId, enterpriseClassify: row.enterpriseClassify, riskRatio: row.riskRatio}})
      } else if (Object.is(type, 'check')) {
        // 审核
        this.$router.push({path: '/admin/planmanage/balancecheck/check', query: {code: row.code, year: row.year, projectEnterpriseId: row.enterpriseId, enterpriseClassify: row.enterpriseClassify, riskRatio: row.riskRatio}})
      }
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #balancecheck-page {
    .balance-search {
      .el-form .el-form-item {
        margin-bottom: 0;
      }
    }
    .balance-add {
      text-align: left;
      padding-bottom: 20px;
    }
    // 分页
    .block-page {
      padding-top:15px;
    }
  }
</style>
