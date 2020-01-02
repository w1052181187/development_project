<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="projectquery-page">
    <div v-if="this.$route.path == '/admin/evaluate/projectquery'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>考评系统</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/evaluate/projectquery' }">项目单位考评查询</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!--搜索区域start-->
      <div class="search-box projectquery-search">
        <el-form :model="searchForm" :inline="true" class="demo-form-inline" style="text-align: left;">
          <el-form-item label="项目名称:">
            <el-input v-model="searchForm.projectNameLike"></el-input>
          </el-form-item>
          <el-form-item label="项目编号:">
            <el-input v-model="searchForm.codeUserLike"></el-input>
          </el-form-item>
          <el-form-item label="代理机构:">
            <el-input v-model="searchForm.entrustEnterpriseNameLike"></el-input>
          </el-form-item>
          <el-form-item label="项目类型:">
            <el-select v-model="searchForm.tenderProjectType" clearable placeholder="请选择">
              <el-option
                v-for="item in proTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
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
            prop="tenderProjectName"
            label="项目名称"
            align="center">
          </el-table-column>
          <el-table-column
            prop="codeUser"
            label="项目编号"
            align="center">
          </el-table-column>
          <el-table-column
            prop="tenderProjectType"
            label="项目类型"
            align="center"
            :formatter="filterType">
          </el-table-column>
          <el-table-column
            prop="biddingType"
            label="采购方式"
            align="center"
            :formatter="filterBiddingType">
          </el-table-column>
          <el-table-column
            prop="agencyEnterpriseName"
            label="招标代理机构"
            align="center">
          </el-table-column>
          <el-table-column
            prop="projectEnterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="160">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/evaluate/projectquery/detail')">查看</el-button>
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
      <!--弹窗区域start-->
      <div class="dialog-box">
      </div>
      <!--弹窗区域end-->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {tenderproject} from 'api/index'
// 实例
export default {
  data () {
    return {
      proTypeList: [
        {label: '工程', value: 1},
        {label: '货物', value: 2},
        {label: '服务', value: 3}
      ], // 项目类型
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
    filterType (row) {
      if (row.tenderProjectType === 1) {
        return '工程'
      } else if (row.tenderProjectType === 2) {
        return '货物'
      } else if (row.tenderProjectType === 3) {
        return '服务'
      }
    },
    filterBiddingType (row) {
      if (row.biddingType === 1) {
        return '公开招标'
      } else if (row.biddingType === 2) {
        return '邀请招标'
      } else if (row.biddingType === 3) {
        return '谈判采购'
      } else if (row.biddingType === 4) {
        return '询比采购'
      } else if (row.biddingType === 5) {
        return '竞价采购'
      } else if (row.biddingType === 6) {
        return '直接采购'
      } else if (row.biddingType === 7) {
        return '框架协议'
      }
    },
    /** 获取表单数据 */
    getTableData () {
      tenderproject.getCheckInfoInEvaluate({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        operationType: 3,
        projectNameLike: this.searchForm.projectNameLike || null,
        codeUserLike: this.searchForm.codeUserLike || null,
        entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || null,
        tenderProjectType: this.searchForm.tenderProjectType || null
      }).then((res) => {
        if (res.data.tenderProjectList && res.data.tenderProjectList.list) {
          this.tableData = res.data.tenderProjectList.list
          this.total = res.data.tenderProjectList.total
          if (this.total <= this.pageSize) {
            this.pageNo = 0
          }
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
    // 分页-handleSizeChange
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.getTableData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.tenderProjectList.list.total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getTableData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 搜索 */
    searchFun () {
      this.getTableData()
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/evaluate/projectquery/details', query: {code: row.code}})
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
  #projectquery-page {
    /** 搜索css */
    .projectquery-search {
      .el-form-item {
        margin-bottom: 0;
      }
    }
    /** 分页 */
    .block-page {
      padding-top: 15px;
    }
  }
</style>
