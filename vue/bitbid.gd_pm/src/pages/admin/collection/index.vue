<template>
  <div class="center_box">
    <div class="con_title">收款账号</div>
    <div class="con_table">
      <!--上边功能-->
      <div class="con_top_box">
        <!--添加按钮-->
        <el-button type="primary" size="medium" class="add_btn" @click="addBtn">添加</el-button>
        <!--添加按钮-->
      </div>
      <!--上边功能-->
      <!--列表数据-->
      <el-table
        :data="projecttableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="agency.enterpriseName"
          label="代理机构"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="isStartUsing"
          label="状态"
          align="left"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.isStartUsing === 1">
              <span class="statusbox">启用</span>
            </div>
            <div class="statusbigbox" v-if="scope.row.isStartUsing === 0">
              <span class="statusbox">禁用</span>
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
            <el-button type="text" size="small" class="handle_btn" @click="disableBtn(scope)" v-if="scope.row.isStartUsing === 1">
              禁用
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="disableBtn(scope)" v-if="scope.row.isStartUsing === 0">
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
import {shroffAccountNumber} from '@/api'
export default {
  name: '',
  data () {
    return {
      seacherInput: '', // 搜索input值
      seacherSelect: 0, // 选择默认值
      // 搜索框选择内容
      seacherData: [{
        value: 0,
        label: '合作代理机构'
      }],
      // 选择不同的搜索方式切换不同的提示
      seacherName: '合作代理机构',
      // 当前页
      pageSize: 10,
      pageNo: 0,
      total: 20, // 总条数
      currentPage: 1,
      projecttableData: []
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
      let url = {}
      url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isvalid: 1
      }
      if (this.$store.getters.authUser.userType === 2) {
        url.creater = this.$store.getters.authUser.userId
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        if (this.seacherName === '公告名称') {
          url.annoName = this.seacherInput
        } else if (this.seacherName === '项目编号') {
          url.projectNumber = this.seacherInput
        }
      }
      shroffAccountNumber.queryList(url).then((res) => {
        this.projecttableData = res.data.shroffAccountNumberPageInfo.list
        this.total = res.data.shroffAccountNumberPageInfo.total
      })
    },
    // ***************************************************************自己方法**********************************************************************
    deleteBtn (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        shroffAccountNumber.delete(scope.row.id).then((res) => {
          this.list()
        })
      }).catch(() => {
        return false
      })
    },
    // 添加
    addBtn () {
      this.$router.push({path: '/admin/collection/add'})
    },
    // 修改
    updateBtn (scope) {
      this.$router.push({path: '/admin/collection/update', query: {objectId: scope.row.id}})
    },
    detailBtn (scope) {
      this.$router.push({path: `/admin/collection/detail/${scope.row.id}`})
    },
    // 禁用
    disableBtn (scope) {
      let isStartUsing = ''
      if (scope.row.isStartUsing === 0) {
        isStartUsing = 1
      } else {
        isStartUsing = 0
      }
      shroffAccountNumber.forbidden(scope.row.id, isStartUsing).then((res) => {
        this.list()
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
