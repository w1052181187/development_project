<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="planquery-page">
    <div v-if="this.$route.path == '/admin/planmanage/planquery'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>计划管理</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/planmanage/planquery' }">招标计划备案查询</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="planquery-search">
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
                  <el-input v-model="ruleSearchForm.enterpriseName" placeholder="请填写项目单位"></el-input>
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
      <div class="maincont-box planquery-cont">
        <!-- 表格start -->
        <div class="planquery-tab">
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
              align="center"
              prop="year"
              label="年度"
              width="80">
            </el-table-column>
            <el-table-column
              align="left"
              header-align="center"
              prop="enterpriseName"
              label="项目单位">
            </el-table-column>
            <el-table-column
              align="center"
              prop="userName"
              label="提交人">
            </el-table-column>
            <el-table-column
              align="center"
              prop="submitTime"
              label="提交时间"
              :formatter="filterDate">
            </el-table-column>
            <el-table-column
              align="center"
              prop="approveName"
              label="审核人">
            </el-table-column>
            <el-table-column
              align="center"
              prop="approveTime"
              label="审批时间"
              :formatter="filterDate">
            </el-table-column>
            <el-table-column
              align="center"
              label="操作"
              width="80">
              <template slot-scope="scope">
                <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium"  v-if="$store.getters.permissions.includes('/planmanage/planquery/detail')">查看</el-button>
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
import {formatDate} from 'common/js/date.js'
import {planapp, enterprise} from 'api/index'
export default {
  data () {
    return {
      tableData: [],
      enterpriseList: [],
      yearList: [],
      ruleSearchForm: {}, // 搜索表单
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  created () {
    this.getYearMethed()
    this.planqueryData()
    this.getAllEnterpriseList()
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化时间 */
    filterDate (row, column) {
      let date = ''
      if (column.property === 'submitTime') {
        date = new Date(row.submitTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else if (row.approveTime && column.property === 'approveTime') {
        date = new Date(row.approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    /** 计算年份 */
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
    /** 进行查看等操作后返回当前页 */
    indexMethod (index) {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.$route.query.currentPage = ''
      }
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.planqueryData(function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.planqueryData()
    },
    /** 列表数据显示 */
    planqueryData () {
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        NameMessageLike: this.ruleSearchForm.enterpriseName || null,
        year: this.ruleSearchForm.year || null,
        statuses: 3
      }
      let enterpriseId = this.ruleSearchForm.enterpriseId || null
      if (enterpriseId === null) {
        obj.enterpriseId = this.$store.getters.authUser.enterpriseId
        obj.flag = 1
      } else {
        obj.enterpriseId = this.ruleSearchForm.enterpriseId
        obj.flag = 2
      }
      planapp.getList(obj).then((res) => {
        this.tableData = res.data.annualProjectPlanList.list
        this.total = res.data.annualProjectPlanList.total
      })
    },
    /** 搜索 */
    onSearch () {
      this.planqueryData()
    },
    /** 获取所有得申请单位信息 */
    getAllEnterpriseList  () {
      enterprise.getByType({
        objectId: this.$store.getters.authUser.enterpriseId
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          let info = []
          for (var i = 0; i < res.data.enterprises.length; i++) {
            var obj = {}
            obj.value = res.data.enterprises[i].objectId
            obj.label = res.data.enterprises[i].name
            info[i] = obj
          }
          this.enterpriseList = info
        }
      })
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'look') {
        this.$router.push({path: '/admin/planmanage/planquery/details', query: {code: row.code, year: row.year, enterpriseName: row.enterpriseName}})
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
#planquery-page {
  .planquery-search {
    background: #fff;
    margin: 20px;
    padding-top: 20px;
  }
  .el-form-item {
    margin-left: -30px;
  }
  .el-form-item__content {
    text-align: left;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
