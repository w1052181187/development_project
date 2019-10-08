<template>
    <div id="orgGroupTrial" class="content_bgibox">
      <!--项目信息-->
      <commonProject></commonProject>
      <!--项目信息-->
      <!--组建资审小组-->
      <div class="setup_bigbox" style="padding-bottom: 70px">
        <div class="proinfobox">
          <span class="line_bigbox"></span>
          <span class="title_bigbox">组建资审小组</span>
          <div class="formmian_projectbox">
            <!--添加按钮-->
            <el-button type="success" icon="el-icon-plus" class="addbtn_box" @click="addExpert" v-if="!subStatus">
              添加
            </el-button>
            <!--添加按钮-->
            <!--添加弹窗-->
            <el-dialog :title="titileName" :visible.sync="dialogFormVisible" width="30%" top="auto" :before-close="cancelBtn">
              <el-form :model="expertForm" :rules="expertRules" ref="expertForm">
                <el-form-item label="登录名：" prop="userName" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.userName"></el-input>
                </el-form-item>
                <el-form-item label="专家姓名：" prop="realName" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.realName"></el-input>
                </el-form-item>
                <el-form-item label="登录密码：" prop="passWord" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.passWord"></el-input>
                </el-form-item>
                <el-form-item label="工作单位：" prop="companyName" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.companyName"></el-input>
                </el-form-item>
                <el-form-item label="职务：" prop="userDuty" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.userDuty"></el-input>
                </el-form-item>
                <el-form-item label="身份证号：" prop="cardId" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.cardId"></el-input>
                </el-form-item>
                <el-form-item label="从事行业：" prop="profession" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.profession"></el-input>
                </el-form-item>
                <el-form-item label="联系方式：" prop="contact" :label-width="formLabelWidth">
                  <el-input v-model.trim="expertForm.contact"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer expertsubbox">
                <el-button type="primary" @click="setUpNegotiationAdd('expertForm',flag)">提 交</el-button>
              </div>
            </el-dialog>
            <!--添加弹窗-->
            <!--谈判组数据-->
            <div class="tableData_box">
              <template>
               <el-table
                :data="tableDataExpert"
                border
                style="width: 100%">
                <el-table-column
                  label="勾选专家组长"
                  width="120" align="center">
                  <template slot-scope="scope">
                    <el-radio v-model="radio" :label='scope.row.objectId' @change='getCurrentRow' :disabled="subStatus">&nbsp;</el-radio>
                  </template>
                </el-table-column>
                <el-table-column
                  type="index"
                  label="序号"
                  width="80"
                  align="center"
                  :index='dataIndex'>
                </el-table-column>
                <el-table-column
                  prop="userName"
                  label="登录名"
                  width="120"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="realName"
                  label="专家姓名"
                  width="120"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="passWord"
                  label="登录密码"
                  width="120"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="companyName"
                  label="工作单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="userDuty"
                  label="职务"
                  width="120"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="cardId"
                  label="身份证号"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="profession"
                  label="从事专业"
                  width="120"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="contact"
                  label="联系方式"
                  width="160"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="100"
                  align="center" v-if="!subStatus">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editBtn(scope)">编辑</el-button>
                    <el-button type="text" size="small" @click="deleteBtn(scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              </template>
            </div>
            <!--谈判组数据-->
            <!--提交按钮-->
            <div class="sub_btnbigbox">
              <el-button type="primary" class="sub_btn" @click="subExpert" v-if="!subStatus">提 交</el-button>
              <el-button type="primary" class="sub_btn" disabled v-else>已提交</el-button>
            </div>
            <!--提交按钮-->
          </div>
        </div>
      </div>
      <!--组建资审小组-->
    </div>
</template>
<script>
import commonProject from '@/components/common/competitive-consultation/project'
import {isvalidId} from '@/assets/js/validate'
import { consultingReviewControl } from '@/api'
export default {
  components: {
    commonProject
  },
  data () {
    // 身份证验证
    var validID = (rule, value, callback) => {
      if (value === '' || value === undefined || value === null) {
        callback()
      } else if (!isvalidId(value)) {
        callback(new Error('请输入正确的18位身份证号码'))
      } else {
        callback()
      }
    }
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      flag: true, // 自定义编辑和增加状态
      titileName: '增加',
      // 选择组长的单选定义
      radio: '',
      // 自定义序号
      dataIndex: 1,
      // 谈判组数据
      tableDataExpert: [],
      // 专家弹框from
      expertForm: {},
      // 专家弹框lable宽度
      formLabelWidth: '100px',
      // 是否提交
      subStatus: false,
      // 弹框定义
      dialogFormVisible: false,
      // 专家id自定义
      expertId: '',
      // 提交专家信息的验证
      expertRules: {
        userName: [
          { required: true, message: '请输入登录名', trigger: ['blur', 'change'] },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']}
        ],
        realName: [
          { required: true, message: '请输入专家姓名', trigger: ['blur', 'change'] },
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']}
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: ['blur', 'change']}
        ],
        companyName: [
          { required: false, message: '请输入工作单位', trigger: ['blur', 'change'] },
          {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: ['blur', 'change']}
        ],
        userDuty: [
          { required: false, message: '请输入职务', trigger: ['blur', 'change'] },
          {min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: ['blur', 'change']}
        ],
        cardId: [
          { trigger: ['blur', 'change'], validator: validID }
        ],
        profession: [
          { required: false, message: '请输入从事行业', trigger: ['blur', 'change'] },
          {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change']}
        ],
        contact: [
          { required: false, message: '请输入联系方式', trigger: ['blur', 'change'] },
          {min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // 处理重复数据提示
    repeatDataErr (code) {
      if (code !== '0000') {
        return false
      }
      this.setUpNegotiationLists()
      this.dialogFormVisible = false
      this.$refs['expertForm'].resetFields()
    },
    // 获取磋商人员列表数据
    setUpNegotiationLists () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        groupBy: 1
      }
      consultingReviewControl.setUpNegotiationList(url).then((res) => {
        this.tableDataExpert = res.data.BidOpenUserList
        this.tableDataExpert.map((its) => {
          if (its.initRoleId === 4) {
            this.radio = its.objectId
            this.expertId = its.objectId
          }
          if (res.data.isHide === '1') {
            this.subStatus = true
          }
        })
      })
    },
    // 增加谈判人员
    setUpNegotiationAdd (expertForm, flag) {
      this.$refs[expertForm].validate((valid) => {
        if (valid) {
          if (flag) { // 增加
            this.expertForm.objectId = ''
            this.expertForm.initRoleId = 5
            consultingReviewControl.setUpNegotiationAdd(this.expertForm).then((res) => {
              this.repeatDataErr(res.data.resCode)
            })
          } else { // 编辑
            consultingReviewControl.setUpNegotiationUpdate(this.expertForm).then((res) => {
              this.repeatDataErr(res.data.resCode)
            })
          }
        } else {
          return false
        }
      })
    },
    // 编辑时查看人员信息
    editBtn (scope) {
      consultingReviewControl.setUpNegotiationLook(scope.row.objectId).then((res) => {
        this.flag = false
        this.titileName = '编辑'
        this.dialogFormVisible = true
        this.expertForm = res.data.BidOpenUser
      })
    },
    // 删除人员信息
    deleteBtn (scope) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        consultingReviewControl.setUpNegotiationDelete(scope.row.objectId).then((res) => {
          this.setUpNegotiationLists()
          if (this.expertId === scope.row.objectId) {
            this.expertId = ''
          }
        })
      }).catch(() => {
        return false
      })
    },
    // 选择组长事件
    getCurrentRow (val) {
      this.expertId = val
    },
    // 点击添加显示弹框
    addExpert () {
      this.flag = true
      this.expertForm = {}
      this.titileName = '添加'
      this.expertForm.passWord = '123'
      this.dialogFormVisible = true
    },
    // 关闭内容
    cancelBtn () {
      this.dialogFormVisible = false
      this.$refs['expertForm'].resetFields()
    },
    // 提交专家组长信息
    subExpert () {
      if (this.tableDataExpert.length % 2 === 0) {
        this.$message({
          type: 'warning',
          message: '专家数必须为奇数'
        })
        return false
      }
      if (this.expertId === '') {
        this.$message({
          type: 'warning',
          message: '请选择一个组长'
        })
        return false
      }
      consultingReviewControl.setLeader(this.expertId, 5, 4).then((res) => {
        this.setUpNegotiationLists()
      })
    }
  },
  mounted () {
    this.setUpNegotiationLists()
  }
}
</script>
<style lang="less">
  #orgGroupTrial{
    .tableData_box{
      margin-top: 20px;
    }
    .el-radio__label {
      padding-left: 0px;
    }
    .expertsubbox{
      text-align: center;
    }
    .expertsubbox>.el-button--primary {
      color: #fff;
      background-color: #2d76ed;
      border-color: #2d76ed;
    }
    .setup_bigbox{
      box-shadow: 0 2px 0px 0px rgba(0,0,0,0.08) inset;
    }
    .el-dialog__body{
      padding: 10px 20px;
      padding-bottom: 0;
    }
    .el-dialog__header {
      padding: 15px 20px 5px;
    }
    .el-form-item {
      margin-bottom: 20px;
    }
    .el-dialog__footer {
      padding: 0px 20px 15px;
    }
    .el-form-item__content{
      line-height: 30px;
    }
    .el-form-item__label{
      line-height: 30px;
    }
    .el-input__inner{
      height: 30px;
      line-height: 30px;
    }
    .el-dialog{
      position: fixed;
      left: 0;
      right: 0;
      top: 0;
      bottom: 0;
      margin: auto;
      height: 560px;
    }
  }
</style>
