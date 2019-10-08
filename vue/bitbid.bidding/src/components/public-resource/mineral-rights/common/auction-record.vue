<template>
  <div>
    <el-table
      :data="auctiontableData"
      border
      header-row-class-name="gray">
      <el-table-column
        type="index"
        label="报价轮次"
        width="150"
        align="center">
      </el-table-column>
      <el-table-column
        prop="userName"
        label="竞买人编号"
        align="left">
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="报价时间"
        align="left">
      </el-table-column>
      <el-table-column
        prop="offer"
        label="报价金额（万元）"
        align="left">
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      class="pagebox"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="currentPage"
      :total="total"
      :page-size='pageSize'
      layout="prev, pager, next, jumper">
    </el-pagination>
    <!--分页-->
  </div>
</template>
<script>
import { room } from '@/api'
export default {
  data () {
    return {
      auctiontableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10 // 每页展示条数
    }
  },
  props: ['roomId'],
  methods: {
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        roomId: this.roomId
      }
      if (this.roomId && this.roomId.length > 0) {
        room.queryList(params).then(res => {
          this.auctiontableData = res.data.recordPageInfo.list
          this.total = res.data.recordPageInfo.total
        })
      }
    }
  },
  mounted () {
    this.list()
  }
}
</script>
