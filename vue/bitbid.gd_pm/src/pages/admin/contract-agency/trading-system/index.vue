<template>
  <div class="center_box">
    <div class="con_title">交易系统版本管理</div>
    <div class="con_table">
      <!--列表数据-->
      <el-table
        :data="projecttableData"
        border
        header-cell-class-name="table_header">
        <el-table-column
          prop="versionNumber"
          label="系统版本号"
          align="left"
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="versionName"
          label="系统名称"
          align="left"
          show-overflow-tooltip>
          <template  slot-scope="scope">
            <el-input v-model="scope.row.versionName" v-if="scope.row.updateStatus"></el-input>
            <span v-else>{{scope.row.versionName}}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" class="handle_btn" @click="updateBtn(scope)" v-if="!scope.row.updateStatus">
              修改
            </el-button>
            <el-button type="text" size="small" class="handle_btn" @click="saveBtn(scope)" v-else>
              保存
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
import {versionManagement} from '@/api'
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
      total: 0, // 总条数
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
        isDelete: 0
      }
      versionManagement.queryList(url).then((res) => {
        this.projecttableData = res.data.versionManagementPageInfo.list
      })
    },
    // 修改
    updateBtn (scope) {
      scope.row.updateStatus = true
    },
    // 保存
    saveBtn (scope) {
      versionManagement.update(scope.row).then((res) => {
        scope.row.updateStatus = false
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
