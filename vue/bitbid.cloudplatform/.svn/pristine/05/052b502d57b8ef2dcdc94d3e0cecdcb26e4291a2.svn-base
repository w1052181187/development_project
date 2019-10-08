<template>
  <div id="homeouseradmin">
    <div class="homepagecontent">
      <publicside></publicside>
      <div class="homepageconright">
        <!--面包屑-->
        <el-breadcrumb class="breadcrumbbox" separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/organ/department' }">组织结构</el-breadcrumb-item>
          <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
        <!--内容开始-->
        <div class="penserviceconbox">
          <div class="penservicecontitle">组织结构</div>
          <div class="penserviceconconbox">
            <el-menu class="el-menu-demo zzjgbox" mode="horizontal" :router="true" :default-active="$route.path">
              <el-menu-item index="/organ/department">部门管理</el-menu-item>
              <el-menu-item index="/organ/role">角色管理</el-menu-item>
              <el-menu-item index="/organ/user">用户管理</el-menu-item>
            </el-menu>
            <div class="seacherbigboix">
              <el-select class="searchuser" v-model="userValue" placeholder="请选择" @change="userChange">
                <el-option
                  v-for="item in useradmins"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input placeholder="" v-model="roleseacher" class="input-with-select roleseacher">
                <el-button @click="search" class="roleseacherbtn" slot="append" icon="el-icon-search">搜索</el-button>
              </el-input>
            </div>
            <!--按钮-->
            <div class="btnbigbox">
              <!--添加角色-->
              <div class="addrole">
                <el-button class="anniubtn" icon="el-icon-plus" @click="addUser">
                  <span>新增用户</span>
                </el-button>
                <el-dialog :title="titleName" width="40%" :before-close="btnClose" :visible.sync="addRoleVisible">
                  <el-form :model="roleForm" :rules="rules" :disabled="isDisabled"  ref="roleForm">
                    <el-form-item label="用户名" prop="account" :label-width="formLabelWidth">
                      <el-input v-model.trim="roleForm.account" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
                      <el-input v-model.trim="roleForm.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="联系方式" prop="phone" :label-width="formLabelWidth">
                      <el-input v-model="roleForm.phone" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="部门" prop="departmentId" :label-width="formLabelWidth">
                      <el-select class="selectinput" v-model="roleForm.departmentId" placeholder="请选择" @change="deptSelet">
                        <el-option
                          v-for="item in departmentform"
                          :key="item.objectId"
                          :label="item.name"
                          :value="item.objectId">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="状态" prop="disable" :label-width="formLabelWidth">
                      <el-select class="selectinput" v-model="roleForm.disable" placeholder="请选择" @change="disableSelet">
                        <el-option
                          v-for="item in statusform"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="备注" prop="remark"  :label-width="formLabelWidth" :rules="[
                      { required: false, trigger: 'blur' },
                      { min: 1, max: 200, message: '长度在1~200个字符', trigger: ['blur', 'change'] }
                    ]">
                      <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        v-model="roleForm.remark">
                      </el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer operationbtn" v-show="isDisabled==false">
                    <el-button type="info" @click="addcancele('roleForm')">取 消</el-button>
                    <el-button type="primary" @click="addDetermine('roleForm',flag)">确 定</el-button>
                  </div>
                </el-dialog>
              </div>
              <!--添加角色-->
            </div>
            <!--按钮-->
            <!--表格-->
            <div class="content_tablebox">
              <template>
                <el-table
                  :data="usertableData"
                  border
                  style="width: 100%" header-cell-class-name="tableheader">
                  <el-table-column
                    type="index"
                    label="序号"
                    width="60"
                    :index='dataIndex'
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="account"
                    label="用户名"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    prop="phone"
                    label="联系电话"
                    width="200">
                  </el-table-column>
                  <el-table-column
                    prop="dept.name"
                    label="所属部门"
                    width="170">
                  </el-table-column>
                  <el-table-column
                    label="操作" align="center">
                    <template slot-scope="scope" >
                      <el-tooltip class="item" effect="dark" content="查看" placement="top-start">
                        <el-button type="text" icon="el-icon-zoom-in" :disabled="operationDisabled" @click="userLook(scope)" size="small" v-if="scope.row.disable == 1"></el-button>
                        <el-button type="text" icon="el-icon-zoom-in" disabled @click="userLook(scope)" size="small" v-else></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="编辑" placement="top-start">
                        <el-button type="text" size="small" :disabled="operationDisabled" @click="userEdit(scope)" icon="el-icon-edit-outline" style="margin-right: 10px" v-if="scope.row.disable == 1"></el-button>
                        <el-button type="text" size="small" disabled @click="userEdit(scope)" icon="el-icon-edit-outline" style="margin-right: 10px" v-else></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" :content="scope.row.disable?'禁用':'启用'" placement="top-start">
                        <el-button type="text" icon="el-icon-circle-close-outline" @click="userDisables(scope)" size="small" v-if="scope.row.disable == 1"></el-button>
                        <el-button type="text" icon="el-icon-view" @click="userDisables(scope)" size="small" v-if="scope.row.disable == 0"></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="分配角色" placement="top-start">
                        <el-button type="text" :disabled="operationDisabled" @click="distributionRole(scope)" icon="el-icon-share" size="small" v-if="scope.row.disable == 1"></el-button>
                        <el-button type="text" disabled @click="distributionRole(scope)" icon="el-icon-share" size="small" v-else></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                        <el-button type="text" :disabled="operationDisabled" size="small" icon="el-icon-delete" @click="userDelect(scope)" v-if="scope.row.disable == 1"></el-button>
                        <el-button type="text" disabled size="small" icon="el-icon-delete" @click="userDelect(scope)" v-else></el-button>
                      </el-tooltip>
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
              :total="total"
              :page-size='pageSize'
              :current-page.sync="currentPage"
              @current-change="handleCurrentChange">
            </el-pagination>
            <!--分页-->
          </div>
        </div>
        <!--内容开始-->
      </div>
    </div>
  </div>
</template>
<script>
import publicside from '../adminpublic/side.vue'
import {isvalidPhone} from '../../assets/js/validate'
import {useradmin} from '../../api/index'
import {addtitle} from '../../assets/js/common'
export default {
  components: {
    publicside
  },
  data () {
    // 电话号码
    var validPhone = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    return {
      pageSize: 10,
      pageNo: 0,
      total: 0, // 总条数
      currentPage: 1,
      dataIndex: 1,
      searchIs: false,
      // 搜索是的选择
      useradmins: [{
        value: 'deptName',
        label: '部门名称'
      }, {
        value: 'nameLike',
        label: '姓名'
      }, {
        value: 'account',
        label: '用户名'
      }],
      // 搜索框默认
      userValue: '',
      // 搜索框默认值
      roleseacher: '',
      // 新增用户弹框
      addRoleVisible: false,
      // 新增用户数据
      roleForm: {
        account: '',
        name: '',
        departmentId: '',
        phone: '',
        remark: ''
      },
      disable: '',
      // 部门id
      deptIds: '',
      // 部门选择
      departmentform: [],
      // 选择状态
      statusform: [{
        value: 0,
        label: '禁用'
      }, {
        value: 1,
        label: '启用'
      }],
      formLabelWidth: '120px',
      // 新增用户提交验证
      rules: {
        account: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1~20个字符', trigger: 'change' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在1~20个字符', trigger: 'change' }
        ],
        phone: [
          {validator: validPhone, trigger: 'blur'}
        ],
        departmentId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        disable: [
          { required: true, message: '状态不能为空', trigger: 'blur' }
        ]
      },
      // 自定义选择内容
      searchSel: '',
      isDisabled: false,
      titleName: '新增用户',
      // 用户管理表格数据
      usertableData: [],
      // 操作自定义
      operationDisabled: false,
      // 编辑添加自定义
      flag: true
    }
  },
  methods: {
    // *********************************************用户************************************************
    // 用户列表数据
    userformList (search) {
      let url = null
      if (search) {
        url = {
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          _t: new Date().getTime()
        }
        if (this.searchSel == '') {
          this.$message({
            type: 'warning',
            message: '请选择查询方式'
          })
          return false
        } else if (this.searchSel == 'deptName') {
          url.deptName = this.roleseacher
        } else if (this.searchSel == 'nameLike') {
          url.nameLike = this.roleseacher
        } else if (this.searchSel == 'account') {
          url.account = this.roleseacher
        }
      } else {
        url = {
          enterpriseId: this.$store.getters.authUser.enterpriseId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          isDelete: 0,
          _t: new Date().getTime()
        }
      }
      useradmin.henanuserList(url).then((res) => {
        this.usertableData = res.data.usersInfo.list
        this.total = res.data.usersInfo.total
        this.usertableData.map((item) => {
          if (!item.phone || item.phone == '"null"') {
            item.phone = '-----------'
          }
        })
        addtitle(this)
      })
    },
    // 用户列表表单分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.userformList(this.searchIs)
    },
    // 部门列表数据
    depformList () {
      useradmin.depthenandetList({
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        isDelete: 0
      }).then((res) => {
        this.departmentform = res.data.deptList
      })
    },
    // 选择列表时进行id赋值
    deptSelet (val) {
      this.deptIds = val
    },
    // 禁用状态的选择赋值
    disableSelet (val) {
      this.disable = val
    },
    // 增加弹框
    addUser () {
      this.addRoleVisible = true
      this.isDisabled = false
      this.titleName = '新增用户'
    },
    // 编辑弹框
    userEdit (scope) {
      this.titleName = '编辑用户'
      this.addRoleVisible = true
      this.flag = false
      this.isDisabled = false
      useradmin.henanuserEditlook(scope.row.objectId).then((res) => {
        this.roleForm = res.data.user
      })
    },
    // 提交
    addDetermine (formName, flag) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.roleForm.departmentId = this.deptIds
          this.roleForm.enterpriseId = this.$store.getters.authUser.enterpriseId
          this.roleForm.creator = this.$store.getters.authUser.userId
          if (flag) { // 进行添加部门
            useradmin.henanuserAdd(this.roleForm).then((res) => {
              this.addedit()
            })
          } else { // 进行修改部门
            useradmin.henanuserEdit(this.roleForm).then((res) => {
              this.addedit()
            })
          }
        } else {
          return false
        }
      })
    },
    // 添加和编辑的公共事件
    addedit () {
      this.userformList()
      this.roleForm = {}
      this.addRoleVisible = false
    },
    // 删除
    userDelect (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        useradmin.henanuserDelete(scope.row.objectId).then((res) => {
          this.userformList()
        })
      }).catch(() => {
        return false
      })
    },
    // 查看
    userLook (scope) {
      this.addRoleVisible = true
      this.isDisabled = true
      this.titleName = '查看用户'
      useradmin.henanuserEditlook(scope.row.objectId).then((res) => {
        this.roleForm = res.data.user
      })
    },
    // 禁用
    userDisables (scope) {
      this.$confirm(`确认进行该操作吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (scope.row.disable === 1) {
          useradmin.henanuserDisable(scope.row.objectId, 0).then((res) => {
            this.userformList()
          })
        } else if (scope.row.disable === 0) {
          useradmin.henanuserDisable(scope.row.objectId, 1).then((res) => {
            this.userformList()
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    // 搜索前选择搜索方式
    userChange (val) {
      this.searchSel = val
    },
    search () {
      this.currentPage = 1
      this.dataIndex = 1
      this.pageNo = 0
      let searchs = {
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        isDelete: 0,
        _t: new Date().getTime()
      }
      // 通过不同的查询进行参数赋值
      if (this.searchSel == '') {
        this.$message({
          type: 'warning',
          message: '请选择查询方式'
        })
        this.userformList()
        return false
      } else if (this.searchSel == 'deptName') {
        searchs.deptName = this.roleseacher
      } else if (this.searchSel == 'nameLike') {
        searchs.nameLike = this.roleseacher
      } else if (this.searchSel == 'account') {
        searchs.account = this.roleseacher
      }
      useradmin.henanuserSearch(searchs).then((res) => {
        this.usertableData = res.data.usersInfo.list
        this.total = res.data.usersInfo.total
        this.usertableData.map((item) => {
          if (!item.phone || item.phone == '"null"') {
            item.phone = '-----------'
          }
        })
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 分配角色
    distributionRole (scope) {
      this.$router.push({name: 'organ/user/distribution', params: {enterpriseId: this.$store.getters.authUser.enterpriseId, objectIds: scope.row.objectId}})
    },
    // 关闭
    btnClose (done) {
      this.addRoleVisible = false
      this.roleForm = {}
    },
    // 取消
    addcancele (roleForm) {
      this.addRoleVisible = false
      this.$refs[roleForm].resetFields()
    }
  },
  mounted () {
    this.userformList()
    this.depformList()
  }
}
</script>
<style lang="less">
  #homeouseradmin{
    .zzjgbox .is-active{
      border-bottom: 2px solid #3f63f6;
      background:transparent!important;
      color:#3f63f6!important;
    }
    .seacherbigboix{
      font-size: 14px;
      margin-top:10px;
      color:#7d7d7d;
      padding: 0 20px;
    }
    .roleseacher{
      width: 320px;
      vertical-align: top;
    }
    .roleseacherbtn{
      background:#3f63f6;
      color:#fff;
      border-radius: 0;
    }
    .btnbigbox{
      padding:20px;
    }
    .anniubtn{
      background:#3f63f6;
      color:#fff;
      border-radius: 0;
    }
    .operationbtn{
      text-align: center;
    }
    .addrole{
      display: inline-block;
    }
    .el-button--text {
      color: #a9a9a9;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-button--text:hover{
      color:#3f63f6;
    }
    .el-button--info {
      color: #bcbcbc;
      background-color:#ededed;
      border-color: #ededed;
    }
    .searchuser {
      width: 150px;
    }
    .selectinput{
      width: 100%;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .el-icon-view:before {
      content: "\E643";
      font-size: 16px;
    }
    .el-icon-zoom-in:before {
      content: "\E641";
      font-size: 16px;
    }
    .el-icon-circle-close-outline:before {
      content: "\E609";
      font-size: 16px;
    }
    .el-icon-share:before {
      content: "\E636";
      font-size: 16px;
    }
  }
</style>
