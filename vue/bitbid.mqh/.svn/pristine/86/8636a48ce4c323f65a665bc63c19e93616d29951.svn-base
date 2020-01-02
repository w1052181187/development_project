<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="contractrecord-page">
    <div v-if="this.$route.path == '/admin/contract/contractrecord'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>合同备案</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/contract/contractrecord' }">合同备案列表</el-breadcrumb-item>
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
                <el-button type="primary" @click="onSearch" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
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
            prop="sectionName"
            label="标段名称"
            align="center">
          </el-table-column>
          <el-table-column
            prop="tenderProjectName"
            label="所属项目"
            align="center">
          </el-table-column>
          <el-table-column
            prop="codeUser"
            label="所属项目编号"
            align="center">
          </el-table-column>
          <el-table-column
            prop="projectEnterpriseName"
            label="项目单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="winnerBidderName"
            label="中标单位"
            align="center">
          </el-table-column>
          <el-table-column
            prop="contractStatus"
            :formatter="filterStatus"
            label="状态"
            align="center">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作"
            width="110">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'edit')"
                         v-if="(scope.row.contractStatus === 0 || scope.row.contractStatus === 3) && $store.getters.permissions.includes('/contract/contractrecord/operation')" type="text" size="medium">编辑</el-button>
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/contract/contractrecord/detail')">查看</el-button>
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
        {label: '未提交', value: 0},
        {label: '待登记', value: 1},
        {label: '已登记', value: 2},
        {label: '退回', value: 3}
      ], // 状态list
      tableData: [],
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      pageNo: 0,
      pageSize: 10,
      total: 0
    }
  },
  watch: {
    $route: 'resetView'
  },
  created () {
  },
  filters: {
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    filterStatus (row, column) {
      if (row.contractStatus === 0) {
        return '未提交'
      } else if (row.contractStatus === 1) {
        return '待登记'
      } else if (row.contractStatus === 2) {
        return '已登记'
      } else if (row.contractStatus === 3) {
        return '退回'
      }
    },
    resetView () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.pageNo = parseInt(this.$route.query.pageNo)
        this.pageSize = parseInt(this.$route.query.pageSize)
      }
      this.getContractInfoList(this.pageNo)
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getContractInfoList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getContractInfoList(this.pageNo)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/contract/contractrecord/edit',
          query: {objectId: row.objectId, operate: type, pageNo: this.pageNo, pageSize: this.pageSize, currentPage: this.currentPage}})
      } else if (Object.is(type, 'edit')) {
        this.$router.push({path: '/admin/contract/contractrecord/edit',
          query: {objectId: row.objectId, operate: type, pageNo: this.pageNo, pageSize: this.pageSize, currentPage: this.currentPage}})
      }
    },
    onSearch () {
      this.getContractInfoList(0)
    },
    getContractInfoList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        tenderProjectNameLike: this.searchForm.tenderProjectNameLike || null,
        codeUserLike: this.searchForm.codeUserLike || null,
        sectionNameLike: this.searchForm.sectionNameLike || null,
        projectEnterpriseNameLike: this.searchForm.projectEnterpriseNameLike || null,
        winnerBidderNameLike: this.searchForm.winnerBidderNameLike || null,
        contractStatus: this.searchForm.contractStatus,
        projectEnterpriseId: this.$store.getters.authUser.enterpriseId
      }
      contractInfo.getList(query).then(res => {
        let data = res.data.contractInfoList
        if (data) {
          this.tableData = data.list
          this.total = data.total
          this.tableData.map(item => {
            if (item.bidInfoConfirm) {
              item.sectionName = item.bidInfoConfirm.sectionName
              item.projectEnterpriseName = item.bidInfoConfirm.projectEnterpriseName
              item.winnerBidderName = item.bidInfoConfirm.winnerBidderName
              if (item.bidInfoConfirm.tenderProject) {
                let tenderProject = item.bidInfoConfirm.tenderProject
                item.tenderProjectName = tenderProject.tenderProjectName
                item.codeUser = tenderProject.codeUser
              }
            }
            return item
          })
        }
      })
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
