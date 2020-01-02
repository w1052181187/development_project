<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="journal-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/system/journal' }"><span>操作日志</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!--搜素区域start-->
    <div class="search-box">
      <el-form :model="searchForm" :inline="true" class="demo-form-inline">
        <el-form-item label="用户名:">
          <el-input type="text" v-model="searchForm.accountLike" placehoder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="姓名:">
          <el-input type="text" v-model="searchForm.userNameLike" placehoder="请输入姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="登录时间起止时间:">
          <el-date-picker
            @change = "datePicker"
            v-model="searchForm.time"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--搜索区域end-->
    <!-- 主体内容区域开始 -->
    <div class="maincont-box">
      <!-- 表格展示区域start -->
      <div class="journal-tab">
        <el-table
          border
          :data="tableData"
          :row-class-name="tableRowClassName"
          style="width: 100%"
          >
          <el-table-column
            type="index"
            label="序号"
            :index="indexMethod"
            align="center"
            width="55">
          </el-table-column>
          <el-table-column
            prop="account"
            label="用户名"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="userName"
            label="姓名"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="enterpriseName"
            label="企业名称"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="loginTime"
            :formatter="filterLoginDate"
            label="登录时间"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="logoutTime"
            :formatter="filterLogoutDate"
            label="退出时间"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="ip"
            label="登录IP"
            header-align="center">
          </el-table-column>
          <el-table-column
            prop="ipAddress"
            label="登录地址"
            header-align="center">
          </el-table-column>
        </el-table>
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
      <!-- 表格展示区域end -->
    </div>
    <!-- 主体内容区域结束 -->
  </div>
</template>
<script>
import {loginLog} from '../../../api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      searchForm: {},
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageSize: 10,
      pageNo: 0,
      total: null, // 总条数
      tableData: [],
      loginStartTime: null, // 搜索开始时间
      loginEndTime: null // 搜索结束时间
    }
  },
  computed: {
  },
  created () {
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    // 过滤时间
    filterLoginDate (row, column) {
      if (row.loginTime) {
        let date = new Date(row.loginTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterLogoutDate (row, column) {
      if (row.logoutTime) {
        let date = new Date(row.logoutTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    datePicker (value) {
      if (value) {
        this.loginStartTime = value[0]
        this.loginEndTime = value[1]
      } else {
        this.loginStartTime = null
        this.loginEndTime = null
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getLoginLogList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getLoginLogList(this.pageNo)
    },
    onSearch () {
      this.currentPage = 1
      this.getLoginLogList(0)
    },
    getLoginLogList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        accountLike: this.searchForm.accountLike,
        userNameLike: this.searchForm.userNameLike,
        loginStartTime: this.loginStartTime,
        loginEndTime: this.loginEndTime
      }
      loginLog.getList(query).then(res => {
        let data = res.data.loginLogList
        if (data) {
          this.tableData = data.list
          this.total = data.total
        }
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getLoginLogList(this.pageNo)
  }
}
</script>
<style lang="less">
#journal-page {
  .search-box {
    .el-form {
      text-align: left;
    }
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .block-page {
    padding-top: 15px;
  }
}
</style>
