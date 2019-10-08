<template>
  <div>
    <div class="time_search">
      <el-form :model="queryModel" ref="searchForm" label-width="80px" :validate-on-rule-change="true" v-loading="loading">
        <label>费用期间：</label>
        <el-date-picker
          v-model="queryModel.date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
        <el-button type="primary" onclick="getTableData()">
          <span>查询</span>
        </el-button>
      </el-form>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      show-summary
      header-cell-class-name="tableheader">
      <el-table-column
        prop="name"
        label="费用名称"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="count"
        label="收入"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="count"
        label="支出"
        show-overflow-tooltip>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  data () {
    return {
      loading: false,
      tableData: [],
      queryModel: {
        relatedCode: this.relatedCode,
        isDelete: 0,
        date: null
      },
      page: {
        pageSize: 10,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      }
    }
  },
  props: ['relatedCode'],
  methods: {
    getTableData () {
      // this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      // contacts.queryList(this.queryModel).then(res => {
      //   this.queryModel.pageNo = this.page.pageNo
      //   this.tableData = res.data.data.list
      //   this.page.total = res.data.data.total
      // })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.getTableData()
    }
  },
  mounted () {
    // 初始化
    this.getTableData()
  }
}
</script>
<style lang="less">
</style>
