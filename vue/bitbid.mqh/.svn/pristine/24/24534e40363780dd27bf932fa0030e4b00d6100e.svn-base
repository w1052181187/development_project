<template>
  <div class="news-list" id="news-list">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/news-list' }">行业资讯列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-table
      @cell-click="clickCell"
      :data="tableData"
      style="width: 100%"
      class="table-height">
      <el-table-column
        prop="title"
        label="标题">
      </el-table-column>
      <el-table-column
        prop="pubTime"
        :formatter="filterDate"
        label="发布日期"
        align="right">
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
</template>
<script>
import { industry } from 'api/index'
import {formatDate} from 'common/js/date'
export default {
  name: 'news-list',
  data () {
    return {
      tableData: [],
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50]
    }
  },
  // 实例创建
  created () {
    this.getIndustryData()
  },
  methods: {
    /**  点击单元格 */
    clickCell (row, column, cell, event) {
      if (row.type === 2) {
        this.$router.push({path: '/admin/news-details', query: {id: row.objectId}})
      } else {
        window.open(row.url)
      }
    },
    filterDate (value) {
      if (value.publishedTime) {
        let date = new Date(value.publishedTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    getIndustryData () {
      let query = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        status: 1,
        isPublished: 1
      }
      industry.getList(query).then((res) => {
        if (res.data.industryNewsList && res.data.industryNewsList.list.length > 0) {
          this.tableData = res.data.industryNewsList.list
          this.total = res.data.industryNewsList.total
        }
      })
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getIndustryData()
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getIndustryData()
    }
  }
}
</script>
<style lang="less">
  #news-list {
    width: 1200px;
    margin: auto;
    margin-top: 80px;
    .workdesk-mqh-tit {
      padding: 10px 0;
    }
    .table-height {
      min-height: 500px;
      padding: 10px 0;
      .el-table__body {
        width: 90%;
      }
    }
    .block-page {
      background: #fff;
      padding: 10px;
    }
  }
</style>
