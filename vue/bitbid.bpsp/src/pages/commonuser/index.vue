<template>
  <div id="commonuser" class="smaincontent">
    <div class="headertitle">
      <!--面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>会员管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
    </div>
    <div class="contentbigbox">
      <template>
        <!--搜索 & 添加按钮-->
        <el-row>
          <el-button type="success"  class="addbutton" @click="addDialog = true"> + 添加会员</el-button>
          <div class="seacher_box">
            <span>用户名：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入用户名" v-model="search_input"></el-input>
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </div>
        </el-row>
        <!--搜索 & 添加按钮-->
        <!--添加会员弹出框-->
        <el-dialog title="添加会员" :visible.sync="addDialog" :before-close="handleAddClose"  width="600px">
          <el-form :model="addForm" :rules="rules" ref="addForm" :validate-on-rule-change="true">
            <el-form-item label="用户名："  prop="user.account">
              <el-input v-model="addForm.user.account"></el-input>
            </el-form-item>
            <el-form-item label="企业名称："  prop="name">
              <el-input v-model="addForm.name"></el-input>
            </el-form-item>
            <el-form-item label="登录密码："  prop="user.password">
              <el-input v-model="addForm.user.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码："  prop="user.userConfirmPwd">
              <el-input v-model="addForm.user.userConfirmPwd" type="password"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleAddClose">取 消</el-button>
            <el-button type="primary" @click="handleAddSubmit('addForm')">确 定</el-button>
          </div>
        </el-dialog>
        <!--添加会员弹出框-->
        <!--修改会员弹出框-->
        <el-dialog title="修改会员" :visible.sync="editDialog" :before-close="handleEditClose"  width="600px">
          <el-form :model="editForm" :rules="rules" ref="editForm" :validate-on-rule-change="true">
            <el-form-item label="用户名："  prop="user.account">
              <el-input v-model="editForm.user.objectId" type="hidden"></el-input>
              <el-input v-model="editForm.user.account" disabled></el-input>
            </el-form-item>
            <el-form-item label="企业名称："  prop="name">
              <el-input v-model="editForm.objectId" type="hidden"></el-input>
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
            <el-form-item label="联系人姓名："  prop="user.name">
              <el-input v-model="editForm.user.name"></el-input>
            </el-form-item>
            <el-form-item label="手机号："  prop="user.cellphone">
              <el-input v-model="editForm.user.cellphone"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="handleEditClose">取 消</el-button>
            <el-button type="primary" @click="handleEditSubmit('editForm')">确 定</el-button>
          </div>
        </el-dialog>
        <!--修改会员弹出框-->
        <!--重置密码弹出框-->
        <el-dialog title="重置密码" :visible.sync="changePassword" :before-close="handlePwdClose"  width="600px">
          <el-form :model="pwdForm" :rules="rules" ref="pwdForm" :validate-on-rule-change="true">
            <el-form-item label="新密码："  prop="resetPassword">
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
            width="80"
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
            prop="enterprise.name"
            label="企业名称"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="enterprise.status"
            label="审核状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.enterprise.status === 0">
                <span class="statusbox">未提交</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.enterprise.status === 1">
                <span class="statusbox">审核中</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.enterprise.status === 2">
                <span class="statusbox">审核通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.enterprise.status === 3">
                <span class="statusbox">审核不通过</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="账号状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">已启用</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">已冻结</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作" width="240" align="center">
            <template slot-scope="scope">
              <div class="statusbigbox">
                <el-button type="text" size="small" v-if="scope.row.enterprise.status === 1" @click="check(scope)">
                  审核
                </el-button>
                <el-button type="text" size="small" @click="detail(scope)" v-if="scope.row.enterprise.status !== 0">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="resetPassword(scope)">
                  重置密码
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 1" @click="isEnable('disable', scope)">
                  冻结
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 0" @click="isEnable('enable', scope)">
                  启用
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
import {commonUser} from '@/api'
export default {
  data () {
    // 电话号码
    let validPhoneUser = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isvalidPhone(value)) {
        callback(new Error('请输入正确的11位手机号码或带区号的固话'))
      } else {
        callback()
      }
    }
    // 密码(添加)
    let validatePwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入密码'))
      } else if (this.addForm.user.userConfirmPwd !== '') {
        this.$refs.addForm.validateField('user.userConfirmPwd')
        callback()
      }
    }
    // 确认密码(添加)
    let validateConfirmPwd = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.addForm.user.password) {
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
      addForm: {
        user: {
          account: '',
          name: '',
          password: '',
          userConfirmPwd: '',
          cellphone: ''
        },
        name: ''
      },
      editForm: {
        user: {
          account: '',
          name: '',
          password: '',
          userConfirmPwd: '',
          cellphone: ''
        },
        name: ''
      },
      pwdForm: {
        resetPassword: '',
        resetUserConfirmPwd: ''
      },
      rules: {
        'user.account': [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 4, max: 16, message: '长度在 4到 16 个字符', trigger: 'blur' },
          {validator: validateAccount, trigger: ['blur', 'change']}
        ],
        name: [
          { required: true, message: '请输入企业名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1到 100 个字符', trigger: 'blur' }
        ],
        'user.name': [
          // { required: true, message: '请输入联系人姓名', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1到 100 个字符', trigger: 'blur' }
        ],
        'user.cellphone': [
          // { required: true, message: '请输入手机号', trigger: 'blur' },
          {validator: validPhoneUser, trigger: 'blur'}
        ],
        'user.password': [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validatePwd, trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: 'blur' }
        ],
        'user.userConfirmPwd': [
          { required: true, message: '请重新输入密码', trigger: 'blur' },
          { validator: validateConfirmPwd, trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: 'blur' }
        ],
        status: [
          { required: true, message: '请选择审批结果', trigger: 'change' }
        ],
        'recordOfApprovals[0].opinion': [
          { required: true, message: '请输入审批意见', trigger: 'blur' }
        ],
        resetPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validateresetPwd, trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ],
        resetUserConfirmPwd: [
          { required: true, message: '请重新输入密码', trigger: 'blur' },
          { validator: validateresetConfirmPwd, trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6到 20 个字符', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    // 列表数据
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        type: 3,
        messageLike: this.search_input === '' ? null : this.search_input
      }
      commonUser.queryList(params).then((res) => {
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
    // 新建会员弹窗关闭 取消按钮
    handleAddClose (done) {
      this.addDialog = false
      this.$refs['addForm'].resetFields()
    },
    // 新建会员弹窗表单 确认按钮
    handleAddSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 默认未审核
          this.addForm.user.status = 0
          // 业务用户
          this.addForm.user.type = 3
          commonUser.save(this.addForm).then((res) => {
            this.addDialog = false
            this.$refs['addForm'].resetFields()
            this.list()
          })
        } else {
          return false
        }
      })
    },
    // 编辑会员弹窗
    edit (objectId) {
      commonUser.enterpriseDetail(objectId).then((res) => {
        this.editForm = res.data.enterprise
        this.editDialog = true
      })
    },
    // 编辑会员弹窗 关闭 取消按钮
    handleEditClose (done) {
      this.editDialog = false
      this.$refs['editForm'].resetFields()
    },
    // 编辑会员弹窗表单 确认按钮
    handleEditSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 修改状态为未审核
          this.editForm.user.status = 0
          commonUser.update(this.editForm).then((res) => {
            this.editDialog = false
            this.list()
          })
        } else {
          return false
        }
      })
    },
    // 审核
    check (scope) {
      this.$router.push({path: '/index/commonuser/check', query: {objectId: scope.row.enterprise.objectId}})
    },
    // 重置密码弹框
    resetPassword (scope) {
      this.changePassword = true
      this.pwdForm.objectId = scope.row.objectId
    },
    // 重置密码弹窗表单 确认按钮
    handleChangePwdSubmit (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let form = {
            objectId: this.pwdForm.objectId,
            password: this.pwdForm.resetPassword
          }
          commonUser.resetPwd(form).then((res) => {
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
      this.$refs['pwdForm'].resetFields()
    },
    // 查看
    detail (scope) {
      this.$router.push({path: '/index/commonuser/detail', query: {objectId: scope.row.enterprise.objectId}})
    },
    /** 删除 */
    deleteBtn (scope) {
      this.$confirm('确定要删除吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除
        commonUser.remove(scope.row.objectId).then((res) => {
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
    isEnable (type, scope) {
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
          commonUser.freeze(scope.row.objectId).then((res) => {
            this.list()
          })
        } else {
          // 执行启用
          commonUser.enabled(scope.row.objectId).then((res) => {
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
  #commonuser{
    .search{
      width: 76%;
    }
    .el-form-item__label{
      width: 168px !important;
    }
    .el-form-item__content{
      margin-left: 168px !important;
      width: 60%;
      text-align: left;
    }
    .el-form-item__content span{
      float: left;
    }
    .el-textarea__inner{
      height: 100px;
    }
  }
</style>
