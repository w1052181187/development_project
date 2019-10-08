<template>
  <div class="cloudcontent">
    <div class="topmain">
      <div class="seacher_box">
        <span>角色名称：</span>
        <el-input class="search" placeholder="请输入角色名称" style="vertical-align: top"  v-model="queryModel.nameLike">
        </el-input>
        <div class="handBtn">
          <el-button  type="primary" @click="handlePage(1)">查询</el-button>
          <el-button  @click="reset">重置</el-button>
        </div>
      </div>
      <!--按钮-->
    </div>
    <div class="main">
      <!--按钮-->
      <div class="btnbigbox">
        <!--添加角色-->
          <el-button type="primary" class="addbutton" @click="showEditDialog(true, false)">
            <span> + 新增角色</span>
          </el-button>
          <edit-role :showVisible="showVisible" :isEdit="isEdit" :editObjectId="editObjectId" @showEditDialog="showEditDialog" @saveSuccess="handlePage(1)"></edit-role>
        <!--添加角色-->
      </div>
      <!--按钮-->
      <!--表格-->
      <div class="content_tablebox">
        <template>
          <el-table
            :data="roles"
            border
            v-loading="loading"
            style="width: 100%" header-cell-class-name="tableheader">
            <el-table-column
              type="index"
              label="序号"
              width="60px"
              :index="computedIndex"
              align="center">
            </el-table-column>
            <el-table-column
              prop="name"
              :formatter="simpleFormatData"
              label="角色名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="description"
              :formatter="simpleFormatData"
              label="角色描述"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="showEditDialog(true, true, scope.row.objectId)">编辑</el-button>
                <el-button type="text" size="small" @click="distribution(scope)">分配权限</el-button>
                <el-button type="text" size="small" @click="logoff(scope.row.objectId)">删除</el-button>
              </template>
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
import {role} from '@/api/system'
import editRole from './edit_role'
export default {
  components: {
    editRole
  },
  data () {
    return {
      loading: false,
      page: {
        pageSize: 10,
        pageNo: 0,
        total: 0, // 总条数
        currentPage: 1
      },
      queryModel: {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0,
        nameLike: ''
      },
      // 角色管理表格数据
      roles: [],
      // 角色弹框
      showVisible: false,
      isEdit: false,
      editObjectId: null
    }
  },
  methods: {
    // 列表展示
    queryRoleList () {
      this.loading = true
      this.queryModel.pageNo = this.page.pageNo
      this.queryModel.pageSize = this.page.pageSize
      role.queryList(this.queryModel).then(res => {
        this.loading = false
        this.roles = res.data.rolePageInfo.list
        this.page.total = res.data.rolePageInfo.total
      })
    },
    // 编辑用户弹框
    showEditDialog (isShow, isEdit, objectId) {
      this.showVisible = isShow
      this.isEdit = isEdit
      this.editObjectId = objectId || null
    },
    // 删除
    logoff (objectId) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        role.logoff(objectId).then((res) => {
          this.handlePage(1)
        })
      }).catch(() => {
        return false
      })
    },
    // 分配权限
    distribution (scope) {
      this.$router.push({path: `/system/roles/assignPermissions`, query: {objectId: scope.row.objectId}})
    },
    // 查询数据
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      role.roleadSeacher(
        {
          creator: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          nameLike: this.roleName,
          isDelete: 0,
          _t: new Date().getTime()
        }
      ).then((res) => {
        this.roletableData = res.data.rolePageInfo.list
        this.total = res.data.rolePageInfo.total
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
      })
    },
    // 普通格式化数据，空的时候展示"---"
    simpleFormatData (row, col, cellValue) {
      return cellValue || '---'
    },
    // 序号计算
    computedIndex (index) {
      return index + (this.page.currentPage - 1) * this.page.pageSize + 1
    },
    // 表单分页
    handlePage (nowNum) {
      this.page.currentPage = nowNum
      this.page.pageNo = (nowNum - 1) * this.page.pageSize
      this.queryRoleList()
    },
    reset () {
      this.queryModel.nameLike = ''
      this.page.currentPage = 1
      this.page.pageNo = 0
      this.queryRoleList()
    }
  },
  mounted () {
    this.queryRoleList()
  }
}
</script>
<style lang="less">
  #cloud_users {
    .main .el-form-item__label{
      padding: 0 12px 0 0;
    }
    .main .content_tablebox{
      margin-top: 20px;
    }
  }
</style>
