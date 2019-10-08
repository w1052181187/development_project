<template>
  <div class="cloudcontent">
    <div class="main">
      <div class="topmain" style="border-bottom: 0px;">
        <div class="seacher_box">
          <span style="display: inline-block;">姓名：</span>
          <el-input class="search" placeholder="请输入内容" style="vertical-align: top"  v-model="queryModel.account">
            <el-button slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </el-input>
        </div>
      </div>
      <!--表格-->
      <div class="content_tablebox">
        <template>
          <el-table
            ref="singleTable"
            :data="users"
            border
            highlight-current-row
            @current-change="handleCurrentChange"
            v-loading="laoding"
            style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column
              width="80px"
              align="center">
              <template slot-scope="scope">
                <el-radio v-model="curLabel" :label="scope.row.objectId">
                  &nbsp;
                </el-radio>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              :formatter="simpleFormatData"
              label="姓名"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="department.name"
              :formatter="simpleFormatData"
              label="所属部门"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </template>
      </div>
      <!--表格-->
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
</template>
<script>
import {archivesRoom} from '@/api/archives'

export default {
  props: ['initUser'],
  components: {
  },
  data () {
    return {
      laoding: false,
      page: {
        pageSize: 10,
        pageNo: 1,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId
      },
      // 表格数据
      users: [],
      curLabel: ''
    }
  },
  watch: {
    initUser: {
      handler (val) {
        this.setCurrent(val)
      },
      immediate: true
    }
  },
  methods: {
    setCurrent (objectId) {
      let row = {}
      this.users.map((item) => {
        if (item.objectId === objectId) {
          row = item
        }
      })
      if (this.$refs.singleTable) {
        this.$refs.singleTable.setCurrentRow(row)
      }
    },
    handleCurrentChange (val) {
      if (val && val.hasOwnProperty('objectId')) {
        this.$emit('changeRadio', val)
        this.curLabel = val.objectId
      } else {
        this.curLabel = ''
      }
    },
    // 搜索
    search () {
      this.handlePage(1)
    },
    // 用户列表数据
    queryUserList () {
      this.laoding = true
      this.queryModel.pageNum = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      archivesRoom.getAdminUserList(this.queryModel).then((res) => {
        this.laoding = false
        this.users = res.data.adminUserList.list
        this.page.total = res.data.adminUserList.total
        this.setCurrent(this.initRole)
      })
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = nowNum
      this.queryUserList()
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    }
  },
  mounted () {
    this.queryUserList()
  }
}
</script>
<style lang="less">
  #cloud_users {
    .main .el-form-item__label{
      padding: 0 12px 0 0;
    }
  }
</style>
