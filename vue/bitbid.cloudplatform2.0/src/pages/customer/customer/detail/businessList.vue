<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      v-loading="loading"
      header-cell-class-name="tableheader">
      <el-table-column
        type="index"
        label="序号"
        width="60"
        :index="computedIndex"
        align="center">
      </el-table-column>
      <el-table-column
        prop="projectName"
        width="500"
        :formatter="simpleFormatData"
        label="项目名称"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="dicStageCode"
        label="商机阶段"
        :formatter="formatStage"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="status"
        label="商机状态"
        show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">未评估</span>
          <span v-if="scope.row.status === 1">评估中</span>
          <span v-if="scope.row.status === 2">跟进中</span>
          <span v-if="scope.row.status === 3">赢单</span>
          <span v-if="scope.row.status === 4">输单</span>
          <span v-if="scope.row.status === 5">商机关闭</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="登记时间"
        :formatter="formatDate"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="chargePersonName"
        label="负责人"
        :formatter="simpleFormatData"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="name"
        label="操作"
        width="80">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="lookBtn(scope)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
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
</template>
<script>
import {business} from '@/api/customer'
import { dataDictionary } from '@/api/system'
import {dateFormat} from '@/assets/js/common'
export default {
  data () {
    return {
      loading: false,
      tableData: [],
      page: {
        pageSize: 10,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        customerManagementCode: this.relatedCode,
        isDelete: 0
      },
      // 商机阶段字典项Id
      dicOpportunityStageId: 10005,
      // 商机阶段集合
      dicOpportunityStageList: []
    }
  },
  props: ['relatedCode'],
  methods: {
    getTableData () {
      this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      business.queryList(this.queryModel).then(res => {
        setTimeout(() => {
          this.loading = false
          this.tableData = res.data.data.list
          this.page.total = res.data.data.total
        }, 300)
      })
    },
    // 初始化字典项商机阶段
    initOpportunityStage () {
      let query = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        dictionaryDirectoryId: this.dicOpportunityStageId
      }
      dataDictionary.getDetailList(query).then(res => {
        this.dicOpportunityStageList = res.data.dictionaryDetails
      })
    },
    // 查看
    lookBtn (scope) {
      this.$router.push({path: `/customerMan/business/detail/${scope.row.code}`})
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 格式化列表中的行业数据
    formatStage (row, col, cellValue) {
      if (cellValue) {
        let obj = this.dicOpportunityStageList.find(item => item.code === cellValue)
        return obj ? obj.name : '---'
      } else {
        return '---'
      }
    },
    // 格式化时间
    formatDate (row, col, cellValue) {
      return dateFormat(cellValue, 'yyyy-MM-dd')
    },
    // 序号计算
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    }
  },
  mounted () {
    // 客户价值初始化
    this.initOpportunityStage()
    // 初始化表格数据
    this.getTableData()
  }
}
</script>
<style lang="less">
</style>
