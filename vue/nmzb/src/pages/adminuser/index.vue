<template>
  <div id="adminmanage" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>管理员管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索 & 添加按钮-->
        <el-row>
          <el-button type="success"  class="addbutton" @click="addDialog = true"> + 添加管理员</el-button>
          <div class="seacher_box">
            <span>用户名/姓名：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入用户名或姓名" v-model="search_input"></el-input>
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </div>
        </el-row>
        <!--搜索 & 添加按钮-->
        <!--添加管理员弹出框-->
        <el-dialog title="添加管理员" :visible.sync="addDialog" :before-close="handleAddClose"  width="600px">
          <el-form :model="addForm" :rules="rules" ref="addForm" :validate-on-rule-change="true">
            <el-form-item label="用户名："  prop="account">
              <el-input v-model="addForm.account"></el-input>
            </el-form-item>
            <el-form-item label="登录密码："  prop="password">
              <el-input v-model="addForm.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码："  prop="userConfirmPwd">
              <el-input v-model="addForm.userConfirmPwd" type="password"></el-input>
            </el-form-item>
            <el-form-item label="联系人姓名："  prop="name">
              <el-input v-model="addForm.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号："  prop="registeredCellPhone">
              <el-input v-model="addForm.registeredCellPhone"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleAddClose">取 消</el-button>
            <el-button type="primary" @click="handleAddSubmit('addForm')">确 定</el-button>
          </div>
        </el-dialog>
        <!--添加管理员弹出框-->
        <!--修改管理员弹出框-->
        <el-dialog title="修改管理员" :visible.sync="editDialog" :before-close="handleEditClose"  width="600px">
          <el-form :model="editForm" :rules="rules" ref="editForm" :validate-on-rule-change="true">
            <el-form-item label="用户名："  prop="account">
              <el-input v-model="editForm.objectId" type="hidden"></el-input>
              <el-input v-model="editForm.account" disabled></el-input>
            </el-form-item>
            <el-form-item label="联系人姓名："  prop="name">
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号："  prop="registeredCellPhone">
              <el-input v-model="editForm.registeredCellPhone"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleEditClose">取 消</el-button>
            <el-button type="primary" @click="handleEditSubmit('editForm')">确 定</el-button>
          </div>
        </el-dialog>
        <!--修改管理员弹出框-->
        <!--查看管理员弹出框-->
        <el-dialog title="查看管理员" :visible.sync="lookDialog" :before-close="handleLookClose"  width="600px">
          <el-form :model="lookForm"  ref="lookForm" :validate-on-rule-change="true">
            <el-form-item label="用户名："  prop="account">
              <span>{{lookForm.account}}</span>
            </el-form-item>
            <el-form-item label="联系人姓名："  prop="name">
              <span>{{lookForm.name}}</span>
            </el-form-item>
            <el-form-item label="手机号："  prop="registeredCellPhone">
              <span>{{lookForm.registeredCellPhone}}</span>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleLookClose">关 闭</el-button>
          </div>
        </el-dialog>
        <!--查看管理员弹出框-->
        <!--重置密码弹出框-->
        <el-dialog title="重置密码" :visible.sync="changePassword" :before-close="handlePwdClose"  width="600px">
          <el-form :model="pwdForm" :rules="rules" ref="pwdForm" :validate-on-rule-change="true">
            <el-form-item label="新密码："  prop="resetPassword">
              <el-input v-model="pwdForm.objectId" type="hidden"></el-input>
              <el-input v-model="pwdForm.resetPassword" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码："  prop="resetUserConfirmPwd">
              <el-input v-model="pwdForm.resetUserConfirmPwd" type="password"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handlePwdClose">取 消</el-button>
            <el-button type="primary" @click="handleChangePwdSubmit('pwdForm')">确 定</el-button>
          </div>
        </el-dialog>
        <!--重置密码弹出框-->
        <el-table
          :data="tableData"
          border
          header-cell-class-name="tabletitles">
          <el-table-column
            type="index"
            width="50"
            label="序号"
            :index='dataIndex'
            align="center">
          </el-table-column>
          <el-table-column
            prop="account"
            label="用户名"
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
            prop="registeredCellPhone"
            label="手机号"
            align="left">
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">已启用</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">未启用</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 3">
                <span class="statusbox">已冻结</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="240" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" @click="detail(scope.row.objectId)">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="edit(scope.row.objectId)">
                  编辑
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope.row.objectId)">
                  删除
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 1" @click="isEnable('disable', scope.row.objectId)">
                  冻结
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 2 || scope.row.status === 3" @click="isEnable('enable', scope.row.objectId)">
                  启用
                </el-button>
                <el-button type="text" size="small" @click="resetPassword(scope.row.objectId)">
                  重置密码
                </el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pagebox"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :total="total"
          :page-size='pageSize'
          layout="prev, pager, next, jumper">
        </el-pagination>
      </template>
    </div>
  </div>
</template>
<script>
import {isvalidPhone, isvalidAccount} from '@/assets/js/validate'
import { adminUser } from '@/api'
export default {
  data () {
    // 电话号码
    let validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入联系电话'))
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码'))
      } else {
        callback()
      }
    }
    // 密码（添加）
    let validatePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (this.addForm.userConfirmPwd !== '') {
        this.$refs.addForm.validateField('userConfirmPwd')
        callback()
      }
    }
    // 确认密码（添加）
    let validateConfirmPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.addForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 密码（重置密码）
    let validateresetPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (this.pwdForm.resetUserConfirmPwd !== '') {
        this.$refs.pwdForm.validateField('resetUserConfirmPwd')
        callback()
      }
    }
    // 确认密码（重置密码）
    let validateresetConfirmPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.pwdForm.resetPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    // 用户名
    let validateAccount = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入用户名'))
      } else if (!isvalidAccount(value)) {
        callback(new Error('请输入4-16位字母或数字'))
      } else {
        callback()
      }
    }
    return {
      search_input: '',
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      // 弹窗
      addDialog: false,
      editDialog: false,
      changePassword: false,
      lookDialog: false,
      addForm: {
        account: '',
        name: '',
        registeredCellPhone: '',
        password: '',
        userConfirmPwd: ''
      },
      editForm: {
        account: '',
        name: '',
        registeredCellPhone: ''
      },
      lookForm: {},
      pwdForm: {
        resetPassword: '',
        resetUserConfirmPwd: ''
      },
      rules: {
        account: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 16, message: '长度在 4到 16 个字符', trigger: 'blur' },
          {validator: validateAccount, trigger: ['blur', 'change']}
        ],
        name: [
          { required: true, message: '请输入联系人姓名', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1到 100 个字符', trigger: 'blur' }
        ],
        registeredCellPhone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          {validator: validPhoneUser, trigger: ['blur', 'change']}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validatePwd, trigger: ['blur', 'change'] },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        userConfirmPwd: [
          { required: true, message: '请重新输入密码', trigger: 'blur' },
          { validator: validateConfirmPwd, trigger: ['blur', 'change'] },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        resetPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validateresetPwd, trigger: ['blur', 'change'] },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        resetUserConfirmPwd: [
          { required: true, message: '请重新输入密码', trigger: 'blur' },
          { validator: validateresetConfirmPwd, trigger: ['blur', 'change'] },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 列表信息
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        type: 2,
        messageLike: this.search_input === '' ? null : this.search_input
      }
      adminUser.queryList(params).then((res) => {
        this.tableData = res.data.userPageInfo.list
        this.total = res.data.userPageInfo.total
      })
    },
    // 搜索
    search () {
      this.currentPage = 1
      this.pageNo = 0
      this.dataIndex = 1
      this.list()
    },
    // 新建管理员弹窗关闭 取消按钮
    handleAddClose (done) {
      this.addDialog = false
      this.addForm = {}
      this.$refs['addForm'].resetFields()
    },
    // 新建管理员弹窗表单 确认按钮
    handleAddSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 管理员默认启用
          this.addForm.status = 1
          this.addForm.type = 2
          adminUser.save(this.addForm).then((res) => {
            if (res.data.resCode === '0000') {
              this.addDialog = false
              this.addForm = {}
              this.list()
            } else {
              return false
            }
          })
        } else {
          return false
        }
      })
    },
    // 修改管理员弹框
    edit (objectId) {
      adminUser.detail(objectId).then((res) => {
        this.editForm = res.data.user
        this.editDialog = true
      })
    },
    // 查看管理员详情弹框
    detail (objectId) {
      adminUser.detail(objectId).then((res) => {
        this.lookForm = res.data.user
        this.lookDialog = true
      })
    },
    // 重置密码弹框
    resetPassword (objectId) {
      this.changePassword = true
      this.pwdForm.objectId = objectId
    },
    // 编辑管理员弹窗 关闭 取消按钮
    handleEditClose (done) {
      this.editDialog = false
      this.editForm = {}
      this.$refs['editForm'].resetFields()
    },
    // 编辑管理员弹窗表单 确认按钮
    handleEditSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          adminUser.update(this.editForm).then((res) => {
            this.editDialog = false
            this.editForm = {}
            this.list()
          })
        } else {
          return false
        }
      })
    },
    // 重置密码弹窗表单 确认按钮
    handleChangePwdSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let form = {
            objectId: this.pwdForm.objectId,
            password: this.pwdForm.resetPassword
          }
          adminUser.resetPwd(form).then((res) => {
            this.changePassword = false
            this.$refs['pwdForm'].resetFields()
          })
        } else {
          return false
        }
      })
    },
    // 重置密码弹窗表单 关闭 取消按钮
    handlePwdClose (done) {
      this.changePassword = false
      this.pwdForm = {}
      this.$refs['pwdForm'].resetFields()
    },
    // 查看弹窗表单 关闭 取消按钮
    handleLookClose (done) {
      this.lookDialog = false
    },
    /** 删除 */
    deleteBtn (objectId) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除
        adminUser.remove(objectId).then((res) => {
          this.list()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    /** 冻结/启用 */
    isEnable (type, objectId) {
      let statusV = (type === 'disable') ? '冻结' : '启用'
      let str = `确定要${statusV}该数据吗?`
      let message = `已取消${statusV}`
      this.$confirm(str, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行冻结
        if (type === 'disable') {
          adminUser.freeze(objectId).then((res) => {
            this.list()
          })
        } else {
          // 执行启用
          adminUser.enabled(objectId).then((res) => {
            this.list()
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: message
        })
      })
    },
    // 分页
    handleCurrentChange (nowNum) {
      this.pageNo = (nowNum - 1) * this.pageSize
      this.dataIndex = (nowNum - 1) * this.pageSize + 1
      this.list()
    }
  },
  mounted () {
    this.list()
  }
}
</script>
<style lang="less">
  #adminmanage{
    .search{
      width: 70%;
    }
    .el-form-item__label{
      width: 168px !important;
    }
    .el-form-item__content{
      margin-left: 168px !important;
      width: 60%;
    }
    .el-form-item__content span{
      float: left;
    }
  }
</style>
