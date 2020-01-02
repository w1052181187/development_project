<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="investcheck-page">
    <div v-if="this.$route.path == '/admin/planmanage/investcheck'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/investcheck' }">投资建议计划审核</el-breadcrumb-item>
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
            <el-input v-model="searchForm.messageLike" placeholder="请填写项目单位" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchFun()" icon="el-icon-search">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!--搜索区域end-->
      <div class="maincont-box planaccept-cont">
        <!-- 表格区域start -->
        <el-table
          class="table-box"
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
            prop="enterpriseName"
            label="项目单位"
            show-overflow-tooltip
            align="center">
          </el-table-column>
          <el-table-column
            prop="year"
            label="年度"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column
            prop="stagingNumber"
            :formatter="filterNumber"
            label="分期"
            align="center">
          </el-table-column>
          <el-table-column
            prop="fixedQuota"
            label="可用投资额度（万元）"
            align="center">
          </el-table-column>
          <el-table-column
            prop="usedQuota"
            label="已用投资额度（万元）"
            align="center">
          </el-table-column>
          <el-table-column
            prop="submitName"
            label="提交人"
            align="center">
          </el-table-column>
          <el-table-column
            prop="submitTime"
            :formatter="filterSubmitTime"
            label="提交时间"
            align="center">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="180">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'check')" type="text" size="medium" v-if="!scope.row.annualProjectPlan && $store.getters.permissions.includes('/planmanage/investcheck/approval')">审核</el-button>
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/planmanage/investcheck/detail')">查看</el-button>
              <el-button @click="handleTableClick(scope.row,'confirm')" type="text" size="medium" v-if="!scope.row.annualProjectPlan && $store.getters.permissions.includes('/planmanage/investcheck/operation')">确认下达</el-button>
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
import {investmanage, enterprise} from 'api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      yearList: this.getYearMethed(),
      statusList: [
        {label: '未提交', value: 0},
        {label: '已提交', value: 1},
        {label: '已核查', value: 2},
        {label: '驳回', value: 3}
      ],
      searchForm: {}, // 搜索
      tableData: [],
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  created () {
  },
  watch: {
    $route: 'resetView'
  },
  methods: {
    resetView () {
      this.getInvestManageList(this.pageNo)
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 计算年份-初始值为2017 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let currentYear = date.getFullYear() + 1
      for (let i = 2017; i <= currentYear; i++) {
        arr.push(i)
      }
      arr = arr.reverse()
      return arr
    },
    filterStatus (row, column) {
      let status = row.status
      if (status === 0) {
        return '未提交'
      } else if (status === 1) {
        return '已提交'
      } else if (status === 2) {
        return '已核查'
      } else if (status === 3) {
        return '退回'
      }
    },
    filterSubmitTime (row, column) {
      if (row.submitTime) {
        let date = new Date(row.submitTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterNumber (row, column) {
      if (row.stagingNumber) {
        return '第' + row.stagingNumber + '期'
      }
    },
    /** 搜索 */
    searchFun () {
      this.pageNo = 0
      this.currentPage = 1
      this.getInvestManageList(this.pageNo)
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getInvestManageList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getInvestManageList(this.pageNo)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        // 审核用管理的查看页面
        this.$router.push({path: '/admin/planmanage/investmanage/investlook',
          query: {operate: 'approve', type: 'approve', fixedQuota: row.fixedQuota, usedQuota: row.usedQuota, code: row.code, year: row.year}})
      } else if (Object.is(type, 'check')) {
        // 审核
        this.$router.push({path: '/admin/planmanage/investcheck/checkmain', query: {code: row.code, year: row.year, enterpriseId: row.enterpriseId}})
      } else if (Object.is(type, 'confirm')) {
        // 确认
        this.$confirm('确认下达后无法修改计划，是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then((res) => {
          investmanage.confirm(row).then(res => {
            this.getInvestManageList(this.pageNo)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      }
    },
    /** 查询投资建议计划信息 */
    getInvestManageList (pageNo) {
      let obj = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        statuses: '1,2',
        year: this.searchForm.year || null,
        messageLike: this.searchForm.messageLike || null
      }
      this.getList(obj)
    },
    getList (obj) {
      investmanage.getList(obj).then((res) => {
        if (res.data.investmentProposalSchemeList) {
          this.tableData = res.data.investmentProposalSchemeList.list
          this.total = res.data.investmentProposalSchemeList.total
        } else {
          return false
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getInvestManageList(this.pageNo)
  }
}
</script>
<style lang="less">
  #investcheck-page {
    .balance-search {
      .el-form .el-form-item {
        margin-bottom: 0;
      }
    }
    // 分页
    .block-page {
      padding-top:15px;
    }
  }
</style>
