<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="bidpurcheck-page">
    <div v-if="this.$route.path == '/admin/planmanage/bidpurcheck'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurcheck' }">招采建议计划审核</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="plansubmit-search">
        <el-form :model="ruleSearchForm" ref="ruleSearchForm" label-width="100px" class="demo-ruleForm">
          <el-row :gutter="20">
            <el-col :span="6" style="width:300px">
              <el-form-item label="年度:">
                <el-select v-model="ruleSearchForm.year" clearable placeholder="请选择">
                  <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:300px">
              <el-form-item label="项目单位:">
                  <el-input type="text" v-model="ruleSearchForm.enterpriseName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item>
                <el-button type="primary" @click="onSearch(ruleSearchForm)" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->
      <div class="maincont-box plansubmit-cont">
        <!-- 表格start -->
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
            align="left"
            header-align="center"
            prop="enterpriseName"
            label="项目单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="year"
            label="年度"
            width="80">
          </el-table-column>
          <el-table-column
            prop="stagingNumber"
            label="分期"
            align="center">
          </el-table-column>
          <el-table-column
            align="right"
            prop="fixedQuota"
            label="可用投资额度（万元）">
          </el-table-column>
          <el-table-column
            align="right"
            prop="usedQuota"
            label="已用投资额度（万元）">
          </el-table-column>
          <el-table-column
            align="center"
            prop="userName"
            label="提交人">
          </el-table-column>
          <el-table-column
            align="center"
            prop="submitTime"
            :formatter="filterDate"
            label="提交时间"
            width="200">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text" size="medium"
                v-if="$store.getters.permissions.includes('/planmanage/bidpurcheck/detail')"
              >查看
              </el-button>
              <el-button v-if="scope.row.status == 2 && $store.getters.permissions.includes('/planmanage/bidpurcheck/approval')"
                @click="handleTableClick(scope.row,'check')"
                type="text" size="medium"
              >审核
              </el-button>
            </template>
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
        <!-- 表格end -->
      </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {plansubmit} from 'api/index'
import {formatDate} from 'common/js/date.js'
export default {
  data () {
    return {
      tableData: [],
      yearList: [],
      ruleSearchForm: {}, // 搜索表单
      enterpriseList: [],
      formInfo: {}, // 弹窗表单
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  watch: {
    $route: 'refreshData'
  },
  created () {
    this.getYearMethed()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
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
      this.yearList = arr.reverse()
      return this.yearList
    },
    /** 格式化 */
    filterStatus (row, column) {
      let status = row.status
      if (status === 1) {
        return '未提交'
      } else if (status === 2) {
        return '审批中'
      } else if (status === 3) {
        return '通过'
      } else if (status === 4) {
        return '退回'
      } else if (status === 5) {
        return '终止'
      }
    },
    filterDate (row, column) {
      let submitTime = row.submitTime || null
      if (submitTime === null) {
        return ''
      } else {
        let date = new Date(submitTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    filterApproveDate (row, column) {
      let approveTime = row.approveTime || null
      if (approveTime === null) {
        return ''
      } else {
        let date = new Date(approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.onSearch()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getAnnualProjectPlanList()
    },
    /** 进行操作后返回当前页 */
    getCurrentValue () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getAnnualProjectPlanList()
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'check')) {
        this.$router.push({path: '/admin/planmanage/bidpurcheck/bidpurapply', query: {year: row.year, enterpriseName: row.enterpriseName, code: row.code, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      } else if (Object.is(type, 'look')) {
        // 查看
        this.$router.push({path: '/admin/planmanage/bidpurcheck/details', query: {objectId: row.objectId, year: row.year, enterpriseName: row.enterpriseName, code: row.code, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      }
    },
    /** 搜索 */
    onSearch (ruleSearchForm) {
      let obj = {
        pageNo: 0,
        pageSize: this.pageSize,
        year: this.ruleSearchForm.year || null,
        NameMessageLike: this.ruleSearchForm.enterpriseName || null,
        statuses: '2,3'
      }
      this.currentPage = 1
      plansubmit.getList(obj).then((res) => {
        if (res.data.annualProjectPlanList) {
          this.tableData = res.data.annualProjectPlanList.list
          this.total = res.data.annualProjectPlanList.total
        }
      })
    },
    /** 列表数据展示 */
    getAnnualProjectPlanList () {
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        year: this.ruleSearchForm.year || null,
        NameMessageLike: this.ruleSearchForm.enterpriseName || null,
        statuses: '2,3'
      }
      plansubmit.getList(obj).then((res) => {
        if (res.data.annualProjectPlanList) {
          this.tableData = res.data.annualProjectPlanList.list
          this.total = res.data.annualProjectPlanList.total
        }
      })
    },
    /** 重新加载页面 */
    refreshData () {
      this.getCurrentValue()
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getCurrentValue()
  }
}
</script>
<style lang="less">
  #bidpurcheck-page {
    /*搜索css*/
    .plansubmit-search {
      padding: 20px;
      margin: 20px;
      background-color: #fff;
      .el-form-item {
        margin-left: -50px;
        margin-bottom: 0;
      }
    }
    .el-form-item__content {
      text-align: left;
    }
    .block-page {
      padding-top: 20px;
    }
  }
</style>
