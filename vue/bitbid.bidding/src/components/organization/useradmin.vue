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
                <el-dialog :title="titleName" width="40%" :before-close="btnClose" :visible.sync="addRoleVisible" >
                  <el-form :model="roleForm" :rules="rules" :disabled="isDisabled"  ref="roleForm">
                    <el-form-item label="用户名" prop="name" :label-width="formLabelWidth">
                      <el-input v-model.trim="roleForm.name" auto-complete="off" placeholder="请输入手机号格式的用户名"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="nameZH" :label-width="formLabelWidth">
                      <el-input v-model.trim="roleForm.nameZH" auto-complete="off" placeholder="请输入姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="联系方式" prop="phone" :label-width="formLabelWidth">
                      <el-input v-model="roleForm.phone" auto-complete="off" placeholder="请输入联系方式"></el-input>
                    </el-form-item>
                    <el-form-item label="部门" prop="dept" :label-width="formLabelWidth">
                      <el-select class="selectinput" v-model="roleForm.dept" placeholder="请选择" @change="deptSelet">
                        <el-option
                          v-for="item in departmentform"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
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
                    prop="name"
                    label="用户名"
                    width="150">
                  </el-table-column>
                  <el-table-column
                    prop="nameZH"
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
                        <el-button type="text" icon="el-icon-zoom-in" :disabled="operationDisabled" @click="userLook(scope)" size="small"></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="编辑" placement="top-start">
                        <el-button type="text" size="small" :disabled="operationDisabled" @click="userEdit(scope)" icon="el-icon-edit-outline" style="margin-right: 10px" ></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="分配角色" placement="top-start">
                        <el-button type="text" :disabled="operationDisabled" @click="distributionRole(scope)" icon="el-icon-share" size="small"></el-button>
                      </el-tooltip>
                      <el-tooltip class="item" effect="dark" content="删除" placement="top-start">
                        <el-button type="text" :disabled="operationDisabled" size="small" icon="el-icon-delete" @click="userDelect(scope)"></el-button>
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
    // 用户名电话号码
    var validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'))
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
      deperpId: this.$store.getters.authUser.enterpriseId,
      searchIs: false,
      // 搜索是的选择
      useradmins: [{
        value: 'deptName',
        label: '部门名称'
      }, {
        value: 'nameZH',
        label: '姓名'
      }, {
        value: 'name',
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
        name: '',
        nameZH: '',
        dept: '',
        phone: '',
        remark: ''
      },
      disable: '',
      // 部门id
      deptIds: '',
      // 部门选择
      departmentform: [],
      formLabelWidth: '120px',
      // 新增用户提交验证
      rules: {
        name: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ],
        nameZH: [
          { required: true, message: '姓名不能为空', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在1~200个字符', trigger: 'blur' }
        ],
        phone: [
          {validator: validPhone, trigger: 'blur'}
        ],
        dept: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
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
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
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
        } else if (this.searchSel == 'nameZH') {
          url.nameZH = this.roleseacher
        } else if (this.searchSel == 'name') {
          url.name = this.roleseacher
        }
      } else {
        url = {
          enterpriseId: this.$store.getters.authUser.userId,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
          _t: new Date().getTime()
        }
      }
      useradmin.userList(url).then((res) => {
        this.usertableData = res.data.userList
        this.total = res.data.total
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
      this.pageNo = nowNum
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.userformList(this.searchIs)
    },
    // 部门列表数据
    depformList () {
      useradmin.detList(this.deperpId).then((res) => {
        this.departmentform = res.data.deptList
      })
    },
    // 选择列表时进行id赋值
    deptSelet (val) {
      this.deptIds = val
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
      useradmin.userEditlook(scope.row.id).then((res) => {
        this.roleForm = res.data.user
        this.roleForm.dept = res.data.user.dept.name
        this.deptIds = scope.row.dept.id
        if (res.data.user.phone === '"null"') {
          res.data.user.phone = ''
        }
        if (res.data.user.remark === '"null"') {
          res.data.user.remark = ''
        }
      })
    },
    // 提交
    addDetermine (formName, flag) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.roleForm.dept = {id : this.deptIds}
          this.roleForm.enterpriseId = this.$store.getters.authUser.enterpriseId
          this.roleForm.enterpriseName = this.$store.getters.authUser.enterpriseName
          this.roleForm.ownerUserID = this.$store.getters.authUser.userId
          if (flag) { // 进行添加用户
            useradmin.userAdd(this.roleForm).then((res) => {
              if (res.data.status === 'repeat') {
                this.$message({
                  type: 'warning',
                  message: '数据不能重复'
                })
                this.roleForm = {}
                this.addRoleVisible = false
                return false
              }
              this.addedit()
            })
          } else { // 进行修改用户
            useradmin.userEdit(this.roleForm).then((res) => {
              if (res.data.status === 'repeat') {
                this.$message({
                  type: 'warning',
                  message: '数据不能重复'
                })
                this.roleForm = {}
                this.addRoleVisible = false
                return false
              }
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
      this.roleForm = {}
      this.addRoleVisible = false
      this.userformList()
    },
    // 删除
    userDelect (scope) {
      this.$confirm('确认删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        useradmin.userDelete(scope.row.id).then((res) => {
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
      useradmin.userEditlook(scope.row.id).then((res) => {
        if (res.data.user.phone === '"null"') {
          res.data.user.phone = ''
        }
        if (res.data.user.remark === '"null"') {
          res.data.user.remark = ''
        }
        this.roleForm = res.data.user
        this.roleForm.dept = res.data.user.dept.name
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
        enterpriseId: this.$store.getters.authUser.userId,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
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
      } else if (this.searchSel == 'nameZH') {
        searchs.nameZH = this.roleseacher
      } else if (this.searchSel == 'name') {
        searchs.name = this.roleseacher
      }
      useradmin.userSearch(searchs).then((res) => {
        this.usertableData = res.data.userList
        this.total = res.data.total
        this.usertableData.map((item) => {
          if (!item.phone || item.phone == '"null"') {
            item.phone = '-----------'
          }
        })
        addtitle(this)
        this.currentPage = 1
        this.dataIndex = 1
        this.pageNo = (this.currentPage - 1) * this.pageSize
        this.searchIs = true
      })
    },
    // 分配角色
    distributionRole (scope) {
      this.$router.push({name: 'organ/user/distribution', params: {enterpriseId: this.$store.getters.authUser.enterpriseId, objectIds: scope.row.id}})
    },
    // 关闭
    btnClose (done) {
      this.addRoleVisible = false
      this.roleForm = {}
      this.$refs['roleForm'].resetFields()
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
      border-left: 0px solid transparent;
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
      height: 30px;
      line-height: 30px;
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
