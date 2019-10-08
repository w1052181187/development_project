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
          <el-button type="success"  class="addbutton" @click="handleAdd"> + 添加会员</el-button>
          <div class="anno_seacher_box">
            <span>用户名：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入用户名" v-model="userName"></el-input>
            <span>联系人：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入联系人" v-model="linkman"></el-input>
            <span>手机号：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入手机号" v-model="cellPhone"></el-input>
            <span>企业名称：</span>
            <el-input class="search" style="vertical-align: top"  placeholder="请输入企业名称" v-model="enterpriseName"></el-input>
            <el-button  slot="append" icon="el-icon-search" type="" @click="search"></el-button>
          </div>
        </el-row>
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
        <!--搜索 & 添加按钮-->
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
            prop="registeredCellPhone"
            label="注册手机"
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
            prop="name"
            label="联系人"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="createDate"
            label="注册时间"
            align="left"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="status"
            label="审核状态"
            align="left">
            <template slot-scope="scope">
              <div class="statusbigbox" v-if="scope.row.status === 0">
                <span class="statusbox">未审核</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 1">
                <span class="statusbox">审核通过</span>
              </div>
              <div class="statusbigbox" v-if="scope.row.status === 2">
                <span class="statusbox">审核不通过</span>
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
                <el-button type="text" size="small" @click="detail(scope)">
                  查看
                </el-button>
                <el-button type="text" size="small" @click="edit(scope)">
                  编辑
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 0" @click="check(scope)">
                  审核
                </el-button>
                <el-button type="text" size="small" @click="deleteBtn(scope)">
                  删除
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 1" @click="isEnable('disable', scope)">
                  冻结
                </el-button>
                <el-button type="text" size="small" v-if="scope.row.status === 3" @click="isEnable('enable', scope)">
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
import {commonUser, adminUser} from '@/api'
export default {
  data () {
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
    return {
      userName: '',
      linkman: '',
      cellPhone: '',
      enterpriseName: '',
      tableData: [],
      // 当前页
      currentPage: 1,
      pageNo: 0,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      dataIndex: 1,
      changePassword: false,
      pwdForm: {
        resetPassword: '',
        resetUserConfirmPwd: ''
      },
      rules: {
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
    // 列表数据
    list () {
      let params = {
        pageNo: this.pageNo,
        pageSize: 10,
        isDelete: 0,
        type: 3,
        accountLike: this.userName === '' ? null : this.userName,
        nameLike: this.linkman === '' ? null : this.linkman,
        registeredCellPhone: this.cellPhone === '' ? null : this.cellPhone,
        enterpriseName: this.enterpriseName === '' ? null : this.enterpriseName
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
    // 跳转新增会员页面
    handleAdd () {
      this.$router.push({path: '/index/commonuser/add'})
    },
    // 编辑会员
    edit (scope) {
      this.$router.push({path: `/index/commonuser/update/${scope.row.objectId}`})
    },
    // 审核
    check (scope) {
      this.$router.push({path: '/index/commonuser/check', query: {objectId: scope.row.objectId}})
    },
    // 查看
    detail (scope) {
      this.$router.push({path: '/index/commonuser/detail', query: {objectId: scope.row.objectId}})
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
    },
    // 重置密码弹框
    resetPassword (objectId) {
      this.changePassword = true
      this.pwdForm.objectId = objectId
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
      width: 14%;
      margin-right: 10px;
    }
    .el-textarea__inner{
      height: 100px;
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
