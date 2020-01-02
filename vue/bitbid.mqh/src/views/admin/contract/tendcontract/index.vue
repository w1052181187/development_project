<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="contractrecord-page">
    <div v-if="this.$route.path == '/admin/contract/contractcheck'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>合同备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/contract/contractcheck' }">登记合同</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!--搜索区域start-->
      <div class="search-box balance-search">
        <el-form :model="searchForm" ref="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:340px">
              <el-form-item label="招标项目名称:">
                <el-input v-model="searchForm.tenderProjectNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px">
              <el-form-item label="招标项目编号:">
                <el-input v-model="searchForm.codeUserLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px">
              <el-form-item label="标段名称:">
                <el-input v-model="searchForm.sectionNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="5" style="width:340px">
              <el-form-item label="项目单位:">
                <el-input v-model="searchForm.projectEnterpriseNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="5" style="width:340px">
              <el-form-item label="中标单位:">
                <el-input v-model="searchForm.winnerBidderNameLike" clearable></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="width:340px">
              <el-form-item label="状态:">
                <el-select v-model="searchForm.contractStatus" clearable placeholder="请选择状态" style="width:220px;">
                  <el-option
                    v-for="item in contractStatusList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2" style="padding-left:30px;">
              <el-form-item>
                <el-button type="primary" @click="onSearch('searchForm')" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
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
            prop="bidInfoConfirm.sectionName"
            label="标段名称"
            align="center">
          </el-table-column>
          <el-table-column
            prop="bidInfoConfirm.tenderProject.tenderProjectName"
            label="所属项目"
            align="center">
          </el-table-column>
          <el-table-column
            prop="bidInfoConfirm.tenderProject.codeUser"
            label="所属项目编号"
            align="center">
          </el-table-column>
          <el-table-column
            prop="bidInfoConfirm.projectEnterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="bidInfoConfirm.winnerBidderName"
            label="中标单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="contractStatus"
            label="状态"
            align="center"
            :formatter="filterStatus">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="110">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'check')" type="text" size="medium" v-if="scope.row.contractStatus === 1 && $store.getters.authUser.enterpriseType === 1 && $store.getters.permissions.includes('/contract/contractcheck/approval')">登记</el-button>
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/contract/contractcheck/detail')">查看</el-button>
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
import {contractInfo} from 'api/index'
// 实例
export default {
  data () {
    return {
      searchForm: {}, // 搜索
      contractStatusList: [
        {
          label: '未提交',
          value: 0
        },
        {
          label: '待登记',
          value: 1
        },
        {
          label: '已登记',
          value: 2
        },
        {
          label: '退回',
          value: 3
        }
      ], // 状态列表
      tableData: [],
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
  },
  filters: {
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 格式化审核状态 */
    filterStatus (row, column) {
      let status = row.contractStatus
      if (status === 0) {
        return '未提交'
      } else if (status === 1) {
        return '待登记'
      } else if (status === 2) {
        return '已登记'
      } else if (status === 3) {
        return '退回'
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getContractInfoList()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getContractInfoList()
    },
    /** 列表数据展示 */
    getContractInfoList () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        contractStatuses: '1,2',
        statuses: this.searchForm.contractStatus,
        tenderProjectNameLike: this.searchForm.tenderProjectNameLike || null,
        codeUserLike: this.searchForm.codeUserLike || null,
        sectionNameLike: this.searchForm.sectionNameLike || null,
        projectEnterpriseNameLike: this.searchForm.projectEnterpriseNameLike || null,
        winnerBidderNameLike: this.searchForm.winnerBidderNameLike || null
      }
      if (query.statuses === '') {
        query.statuses = null
      }
      contractInfo.getList(query).then((res) => {
        if (res) {
          this.tableData = res.data.contractInfoList.list
          this.total = res.data.contractInfoList.total
        }
      })
    },
    /** 搜索 */
    onSearch () {
      this.getContractInfoList()
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/contract/contractrecord/details', query: {objectId: row.objectId}})
      } else if (Object.is(type, 'check')) {
        this.$router.push({path: '/admin/contract/contractcheck/check', query: {objectId: row.objectId}})
      }
    },
    /** 重新加载页面 */
    refreshData () {
      this.getContractInfoList()
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getContractInfoList()
  }
}
</script>
<style lang="less">
  #contractrecord-page {
    // 分页
    .block-page {
      padding-top:15px;
    }
  }
</style>
