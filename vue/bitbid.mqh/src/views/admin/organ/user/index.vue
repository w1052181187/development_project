<template>
  <!-- 后台主容器 -->
  <div class="admin-layout">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <!-- <el-breadcrumb-item :to="{ path: '/admin/1' }">首页</el-breadcrumb-item> -->
        <el-breadcrumb-item > 组织机构</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/users' }"><span>用户管理</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!--搜索start-->
    <div class="search-box">
      <el-form :model="searchForm" label-width="100px" class="demo-ruleForm">
        <el-row :gutter="20" style="margin-left: -60px;">
          <el-col :span="5" style="width:300px">
            <el-form-item label="姓名:">
              <el-input type="text" v-model="searchForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="width:300px">
            <el-form-item label="所属单位:">
              <el-input type="text" v-model="searchForm.enterpriseName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="width:300px">
            <el-form-item label="状态:">
              <el-select v-model="searchForm.status"  clearable placeholder="请选择" style="width:100%;">
                <el-option
                  v-for="item in statusList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="padding-left:30px;">
            <el-form-item>
              <el-button type="primary" @click="onSearch" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!--搜索end-->
    <div class="maincont-box user-main">
      <div class="user-btn"><el-button type="warning" icon="el-icon-plus" @click="addUser">新增用户</el-button></div>
      <el-table
        :data="tableData"
        border
        :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="55">
        </el-table-column>
        <el-table-column
          prop="account"
          label="用户名"
          align="center">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          align="center">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          align="center">
          <template slot-scope="scope">
             <el-switch
              :width="swithWidth"
              v-model="scope.row.status"
              name="scope.row.name"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
              @change="changeSwitch(scope.row)">
             </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          prop="enterpriseName"
          label="所属单位"
          header-align="center"
          align="left">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          header-align="center"
          align="left">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="300">
          <template slot-scope="scope">
            <el-button @click="initPassword(scope)" type="text" size="medium">初始密码</el-button>
            <el-button @click="editUserInfo(scope)" type="text" size="medium">编辑</el-button>
            <el-button @click="seeUserInfo(scope)" type="text" size="medium">查看</el-button>
            <el-button @click="deleteUserInfo(scope)" type="text" size="medium">删除</el-button>
          </template>
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
  </div>
</template>
<script>
import {user} from '../../../../api/index'
// 实例
export default {
  data () {
    return {
      searchForm: {}, // 搜索表单
      swithWidth: 40,
      tableData: [],
      pageSize: 10,
      pageNo: 0,
      currentPage: 1,
      total: 0, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      rolesLength: 0,
      statusList: [
        {
          value: 0,
          label: '可用'

        },
        {
          value: 1,
          label: '禁用'
        }
      ]
    }
  },
  computed: {
  },
  created () {
    this.getCurrentUserInfo()
  },
  methods: {
    /** 搜索 */
    onSearch () {
      this.userData()
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      //  进行编辑，查看等操作后返回到当前页
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.$route.query.currentPage = ''
      }
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 列表数据展示 */
    userData () {
      user.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        messageLike: this.searchForm.name || null,
        enterpriseNameLike: this.searchForm.enterpriseName || null,
        status: (this.searchForm.status === 0 || this.searchForm.status === 1) ? this.searchForm.status : null
      }).then((res) => {
        if (res.data.users && res.data.users.list) {
          this.tableData = res.data.users.list
          this.total = res.data.users.total
          this.tableData.map((tableItem) => {
            tableItem.role = ''
            for (var i = 0; i < tableItem.roles.length; i++) {
              tableItem.role += tableItem.roles[i].name
              if (i !== tableItem.roles.length - 1) {
                tableItem.role += ','
              }
            }
          })
        }
      })
    },
    /** 表单分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.userData(this.pageNo, this.pageSize, function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.userData(parseInt(this.pageNo), parseInt(this.pageSize))
    },
    /** 初始化密码 */
    initPassword (scope) {
      // 打开弹窗
      this.$confirm('此操作将重置密码,重置后的密码为123456, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        user.initPassword(scope.row.objectId).then(() => {
          this.userData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 新增用户-跳转 */
    addUser () {
      this.$router.push({path: '/admin/add-user', query: {currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
    },
    /** 用户的锁定激活功能 */
    changeSwitch (row) {
      user.update(row).then(() => {
        user.getOne(row.objectId).then((res) => {
          row = res.data.user
        })
      })
    },
    /** 编辑用户信息 */
    editUserInfo (scope) {
      this.$router.push({path: '/admin/add-user', query: {objectId: scope.row.objectId, btn: true, isShow: true, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
    },
    /** 删除用户信息 */
    deleteUserInfo (scope) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        user.delete(scope.row.objectId).then(() => {
          this.userData(parseInt(this.pageNo), parseInt(this.pageSize))
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 查看用户信息 */
    seeUserInfo (scope) {
      this.$router.push({path: '/admin/details', query: {data: scope, objectId: scope.row.objectId, code: scope.row.code, currentPage: this.currentPage, pageNo: this.pageNo, pageSize: this.pageSize}})
    },
    /** 进行编辑，查看等操作后返回到当前页 */
    getCurrentUserInfo () {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.pageNo = this.$route.query.pageNo
        this.pageSize = parseInt(this.$route.query.pageSize)
        this.userData(parseInt(this.pageNo), parseInt(this.pageSize))
      } else {
        this.userData()
      }
    }
  },
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  .user-btn {
    text-align: left;
    margin-bottom: 15px;
  }
  .block-page {
    padding-top: 10px;
  }
</style>
