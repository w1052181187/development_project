<template>
  <div class="center_box">
    <div class="con_title">入驻申请</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--添加按钮-->
        <!--<el-button type="primary" size="medium" class="add_btn" @click="addBtn">添加</el-button>-->
        <!--添加按钮-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="tableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="enterpriseName"
          label="企业名称"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="contactNumber"
          label="联系电话"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="项目状态"
          align="left"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">已确认</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 0">
              <span class="statusbox">未确认</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="handle_btn" @click="check(scope)" v-if="scope.row.status === 0">
              确认
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--列表数据-->
      <!--分页-->
      <div class="page_box">
        <el-pagination
          class="pagebox"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </div>
      <!--分页-->
    </div>
  </div>
</template>

<script>
import {enterApply} from '@/api'
export default {
  name: '',
  data () {
    return {
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 20, // 总条数
      currentPage: 1,
      tableData: []
    }
  },
  methods: {
    // ***************************************************************自己方法**********************************************************************
    handleSizeChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.list()
    },
    // 列表显示
    list () {
      let params = {}
      params = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0
      }
      enterApply.queryList(params).then((res) => {
        this.tableData = res.data.pageInfo.list
        this.total = res.data.pageInfo.total
      })
    },
    // 确认
    check (scope) {
      this.$confirm('是否确认项目?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        enterApply.check(scope.row.objectId).then(() => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    }
  },
  mounted () {
    this.list()
  }
}
</script>

<style scoped>
</style>
