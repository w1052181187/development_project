<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="supplyblack-page">
    <div v-if="this.$route.path == '/admin/supply/supplyblack'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>供应商库</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/supply/supplyblack' }">供应商黑名单</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box search-warp">
        <el-form :model="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="6" style="width:300px">
              <el-form-item label="供应商:">
                <el-input v-model="searchForm.messageLike" clearable></el-input>
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
      <!-- 搜索区域end -->
      <!-- 主体内容区域start -->
      <div class="maincont-box intermange-cont">
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
            align="left"
            header-align="center"
            prop="supplierName"
            label="供应商">
          </el-table-column>
          <el-table-column
            align="center"
            prop="supplierAddress"
            show-overflow-tooltip
            label="地址">
          </el-table-column>
          <el-table-column
            align="center"
            prop="bidCount"
            label="参与投标数量">
          </el-table-column>
          <el-table-column
            align="center"
            prop="winBidCount"
            label="中标数量">
          </el-table-column>
          <el-table-column
            align="center"
            prop="winRate"
            label="中标率">
          </el-table-column>
          <el-table-column
            align="center"
            prop="blacklistEndDate"
            :formatter="filterDate"
            label="截止日期">
          </el-table-column>
          <el-table-column
            align="center"
            label="操作">
            <template slot-scope="scope">
              <el-button
                @click="handleTableClick(scope.row,'look')"
                type="text" size="medium">
                查看
              </el-button>
              <el-button
                @click="handleTableClick(scope.row,'black')"
                type="text" size="medium">
                移出黑名单
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
      </div>
      <!-- 主体内容区域end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {supplierBase} from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  data () {
    return {
      searchForm: {},
      tableData: [], // 表格数据
      pageNo: 0,
      currentPage: 1,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  computed: {},
  created () {
  },
  watch: {
  },
  /*
  * 方法集
  */
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    filterDate (row, column) {
      if (row.blacklistEndDate) {
        let date = new Date(row.blacklistEndDate)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    /** 搜索 */
    onSearch () {
      this.getSupplierBaseList(0)
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getSupplierBaseList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getSupplierBaseList(this.pageNo)
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (Object.is(type, 'look')) {
        this.$router.push({path: '/admin/supply/supplypool/details', query: {code: row.code, operate: 'black', currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
      } else if (Object.is(type, 'black')) {
        this.removeOut(row)
      }
    },
    /** 移除黑名单 */
    removeOut (row) {
      this.$confirm('是否将该企业解除黑名单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let query = {
          objectId: row.objectId
        }
        supplierBase.updateBlacklistEndDate(query).then(res => {
          this.getSupplierBaseList(this.pageNo)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消移出'
        })
      })
    },
    getSupplierBaseList (pageNo) {
      let query = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        isInBlacklist: 1,
        messageLike: this.searchForm.messageLike || null
      }
      supplierBase.getList(query).then(res => {
        let data = res.data.supplierBaseList
        if (data) {
          this.tableData = data.list
          this.tableData.map(supplierBase => {
            if (!supplierBase.bidCount) {
              supplierBase.winRate = 0 + '%'
            } else {
              supplierBase.winRate =
                Math.round(supplierBase.winBidCount / supplierBase.bidCount * 10000) / 100.00 + '%'
            }
          })
          this.total = data.total
        }
      })
    },
  },
  /*
  * 实例创建完成
  */
  mounted () {
    if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
      this.currentPage = parseInt(this.$route.query.currentPage)
      this.pageNo = parseInt(this.$route.query.pageNo)
      this.pageSize = parseInt(this.$route.query.pageSize)
    }
    this.getSupplierBaseList(this.pageNo)
  },
  /*
  * 过滤
  */
  filters: {}
}
</script>
<style lang="less">
  #supplyblack-page {
    /*搜索*/
    .search-warp {
      .el-form-item {
        margin-bottom: 0;
      }
    }
    .intermange-cont {
      .add-btn {
        text-align: left;
        padding-bottom: 15px;
      }
    }
    .block-page {
      padding-top: 20px;
    }
  }
</style>
