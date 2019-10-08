<template>
  <div class="center_box">
    <div class="con_title">轮换信息</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--添加按钮-->
        <el-button type="primary" size="medium" class="add_btn" @click="addBtn">添加</el-button>
        <!--添加按钮-->
        <!--模糊搜索-->
        <div class="seacher_box">
          <el-input class="search" style="vertical-align: top"  placeholder="请输入标题" v-model.trim="searchInput"></el-input>
          <el-button  slot="append" icon="el-icon-search" class="seacher_btn" @click="search"></el-button>
        </div>
        <!--模糊搜索-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="tableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="title"
          label="标题"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="left"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.status === 1">
              <span class="statusbox">已禁用</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.status === 0">
              <span class="statusbox">已启用</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="handle_btn" @click="updateBtn(scope)">
              修改
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="deleteBtn(scope)">
              删除
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="disableBtn(scope)" v-if="scope.row.status === 0">
              禁用
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="disableBtn(scope)" v-if="scope.row.status === 1">
              启用
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="detailBtn(scope)">
              查看
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
import {rotationInfo} from '@/api'
export default {
  name: '',
  data () {
    return {
      searchInput: '', // 搜索input值
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
        isDelete: 0,
        messageLike: this.searchInput ? this.searchInput : null
      }
      rotationInfo.queryList(params).then((res) => {
        this.tableData = res.data.pageInfo.list
        this.total = res.data.pageInfo.total
      })
    },
    // 搜索
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.list()
    },
    // ***************************************************************自己方法**********************************************************************
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        rotationInfo.remove(scope.row.objectId).then(() => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/admin/portal/rotation-info/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/admin/portal/rotation-info/update', query: {objectId: scope.row.objectId}})
    },
    detailBtn (scope) {
      this.$router.push({path: `/admin/portal/rotation-info/detail/${scope.row.objectId}`})
    },
    // 禁用启用
    disableBtn (scope) {
      let alertStr = scope.row.status === 0 ? '禁用' : '启用'
      this.$confirm('确认' + alertStr + '吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }).then(() => {
        let status = scope.row.status === 0 ? 1 : 0
        rotationInfo.disableEnabled(scope.row.objectId, status).then(() => {
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
