<template>
  <div id="portalexcept-notice-page" class="cont-view">
    <!-- 面包屑区域start -->
    <div class="bread-box">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>门户信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>异常公示管理</el-breadcrumb-item>
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
          <el-col :span="5">
            <el-form-item label="电商平台名称:">
              <el-select v-model="searchForm.beComplainantId" clearable placeholder="请选择">
                <el-option
                  v-for="item in ecPlatform"
                  :key="item.id"
                  :label="item.userName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="发布状态:">
              <el-select v-model="searchForm.publishStatus" clearable placeholder="请选择">
                <el-option
                  v-for="item in publishStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="成交时间:">
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
          prop="orderNum"
          label="订单编号"
          :formatter="simpleFormatData"
          show-overflow-tooltip
          width="220"
          align="center">
        </el-table-column>
        <el-table-column
          prop="ecPlatform"
          label="电商平台名称"
          :formatter="simpleFormatData"
          show-overflow-tooltip
          width="160"
          align="center">
        </el-table-column>
        <el-table-column
          prop="buyName"
          label="采购人"
          :formatter="simpleFormatData"
          width="160"
          show-overflow-tooltip
          align="center">
        </el-table-column>
        <el-table-column
          label="商品名称"
          :formatter="formatGoodsName"
          show-overflow-tooltip
          align="center">
        </el-table-column>
        <el-table-column
          prop="orderAmount"
          label="订单金额"
          :formatter="simpleFormatData"
          align="center"
          show-overflow-tooltip
          width="140">
        </el-table-column>
        <el-table-column
          prop="dealTime"
          label="成交时间"
          :formatter="simpleFormatData"
          align="center"
          width="160">
        </el-table-column>
        <el-table-column
          prop="publishStatus"
          label="发布状态"
          :formatter="simpleFormatData"
          align="center"
          width="80">
          <template slot-scope="scope">
            {{publishStatus[scope.row.publishStatus]}}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.publishStatus === 1"
              size="mini"
              type="text"
              @click="handleTableBtn(scope.row, 'back')">
              撤回
            </el-button>
            <template v-if="scope.row.publishStatus === 2">
              <el-button
                size="mini"
                type="text"
                @click="handleTableBtn(scope.row, 'recover')">
                恢复
              </el-button>
              <el-button
                size="mini"
                type="text"
                @click="handleTableBtn(scope.row, 'remove')">
                移除到正常公示
              </el-button>
            </template>
            <el-button
              size="mini"
              type="text"
              @click="handleTableBtn(scope.row, 'details')">
              详情
            </el-button>
          </template>
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
import {complain, except} from '@/api'
export default {
  name: 'except-notice',
  data () {
    return {
      searchForm: {},
      ecPlatform: [],
      tableData: [],
      page: {
        total: 0,
        pageSize: 10,
        pageNo: 0,
        currentPage: 1
      },
      publishStatusOptions: [
        {value: 1, label: '已发布'},
        {value: 2, label: '已撤回'}
      ],
      publishStatus: {
        1: '已发布',
        2: '已撤回'
      }
    }
  },
  methods: {
    formatGoodsName (row, col, cellValue) {
      if (!row.articleNameList) {
        return '-----'
      } else {
        return row.articleNameList.join()
      }
    },
    /** 搜索 */
    onSearch () {
      this.page.pageNo = 0
      this.page.currentPage = 1
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
    /** 分页 */
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    },
    handleTableBtn (row, type) {
      let params = {
        id: row.id
      }
      if (Object.is(type, 'back')) {
        params.publishStatus = 2
        this.handleBack(params)
      } else if (Object.is(type, 'recover')) {
        params.publishStatus = 1
        this.handleBack(params)
      } else if (Object.is(type, 'remove')) {
        params.publishStatus = 1
        params.errorState = 0
        this.handleBack(params, 'remove')
      } else if (Object.is(type, 'details')) {
        this.$router.push({path: `/index/exceptdetails/${row.id}`})
      }
    },
    // 处理撤回 恢复
    handleBack (params) {
      except.update(params).then(res => {
        if (res.data.resCode === '0000') {
          this.getTableData()
        }
      })
    },
    getTableData () {
      this.searchForm.pageNo = this.page.pageNo
      this.searchForm.pageSize = this.page.pageSize
      if (this.searchForm.time && this.searchForm.time.length > 1) {
        this.searchForm.dealTimeBegin = this.searchForm.time[0]
        this.searchForm.dealTimeEnd = this.searchForm.time[1]
      } else {
        delete this.searchForm.dealTimeBegin
        delete this.searchForm.dealTimeEnd
      }
      let queryModel = Object.assign({}, this.searchForm)
      queryModel.errorState = 1
      delete queryModel.time
      except.queryList(queryModel).then(res => {
        this.tableData = res.data.transactionRecordList.list
        this.page.total = res.data.transactionRecordList.total
      })
    },
    getEcPlatformData () {
      complain.merchantName().then(res => {
        this.ecPlatform = res.data.users
      })
    }
  },
  mounted () {
    this.getEcPlatformData()
    this.getTableData()
  }
}
</script>
<style lang="less">
#portalexcept-notice-page {
  .demo-ruleForm {
    margin-left: -50px;
  }
}
</style>
