<template>
  <div id="scaling_index">
    <!--项目信息-->
    <project :projectInfoForm="projectForm"></project>
    <!--项目信息-->
    <!--组建定标委员会-->
    <div class="scaling-box">
      <div class="title-box">
        <p>组建定标委员会</p>
      </div>
      <div class="formmian_projectbox">
        <!--添加按钮-->
        <div class="addbtn_box">
          <el-button type="warning" @click="addExpert" :disabled="subStatus">
            添加
          </el-button>
        </div>
        <!--添加按钮-->
        <!--添加弹窗-->
        <el-dialog :title="titileName" :visible.sync="dialogScalingVisible" width="30%" :before-close="cancelScalingBtn" :close-on-click-modal="false">
          <el-form :model="expertForm" :rules="expertRules" ref="expertForm">
            <el-form-item label="登录名：" prop="userAccount" :label-width="formLabelWidth">
              <el-input v-model="expertForm.userAccount" :disabled="!flag"></el-input>
            </el-form-item>
            <el-form-item label="定标人姓名：" prop="userName" :label-width="formLabelWidth">
              <el-input v-model="expertForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="登录密码：" prop="userPassword" :label-width="formLabelWidth">
              <el-input v-model.trim="expertForm.userPassword"></el-input>
            </el-form-item>
            <el-form-item label="工作单位：" prop="companyName" :label-width="formLabelWidth">
              <el-input v-model.trim="expertForm.companyName"></el-input>
            </el-form-item>
            <el-form-item label="职务：" prop="userDuty" :label-width="formLabelWidth">
              <el-input v-model.trim="expertForm.userDuty"></el-input>
            </el-form-item>
            <el-form-item label="联系方式：" prop="telephone" :label-width="formLabelWidth">
              <el-input v-model.trim="expertForm.telephone"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer expertsubbox">
            <el-button size="small" class="btn-sub-bg" @click="scalingSub('expertForm',flag)" :loading="isExpertLoading">提 交</el-button>
          </div>
        </el-dialog>
        <!--添加弹窗-->
        <!--组建定标委员会数据-->
        <div class="tableData_box">
          <template>
            <el-table
              :data="tableDataExpert"
              border
              style="width: 100%">
              <el-table-column
                label="勾选定标组长"
                width="120" align="center">
                <template slot-scope="scope">
                  <el-radio v-model="radio" :label='scope.row.objectId' @change='getCurrentRow' :disabled="subStatus">&nbsp;</el-radio>
                </template>
              </el-table-column>
              <el-table-column
                prop="userAccount"
                label="登录名"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="userName"
                label="定标人姓名"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="userPassword"
                label="登录密码"
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
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="telephone"
                label="联系方式"
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
        <!--组建定标委员会数据-->
      </div>
    </div>
    <!--组建定标委员会-->
    <!--监标人-->
    <div class="scaling-box">
      <div class="title-box">
        <p>监标人</p>
      </div>
      <div class="formmian_projectbox">
        <!--添加按钮-->
        <div class="addbtn_box">
          <el-button type="warning" @click="addInspect" :disabled="subStatus">
            添加
          </el-button>
        </div>
        <!--添加按钮-->
        <!--添加弹窗-->
        <el-dialog :title="titileName" :visible.sync="dialogInspectVisible" width="30%" :close-on-click-modal="false" :before-close="inspectBtn">
          <el-form :model="inspectForm" :rules="inspectRules" ref="inspectForm">
            <el-form-item label="登录名：" prop="userAccount" :label-width="formLabelWidth">
              <el-input v-model="inspectForm.userAccount" :disabled="!flag"></el-input>
            </el-form-item>
            <el-form-item label="监标人姓名：" prop="userName" :label-width="formLabelWidth">
              <el-input v-model="inspectForm.userName"></el-input>
            </el-form-item>
            <el-form-item label="登录密码：" prop="userPassword" :label-width="formLabelWidth">
              <el-input v-model.trim="inspectForm.userPassword"></el-input>
            </el-form-item>
            <el-form-item label="工作单位：" prop="companyName" :label-width="formLabelWidth">
              <el-input v-model.trim="inspectForm.companyName"></el-input>
            </el-form-item>
            <el-form-item label="职务：" prop="userDuty" :label-width="formLabelWidth">
              <el-input v-model.trim="inspectForm.userDuty"></el-input>
            </el-form-item>
            <el-form-item label="联系方式：" prop="telephone" :label-width="formLabelWidth">
              <el-input v-model.trim="inspectForm.telephone"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer expertsubbox">
            <el-button size="small" class="btn-sub-bg" @click="inspectSub('inspectForm',flag)" :loading="isInspectLoading">提 交</el-button>
          </div>
        </el-dialog>
        <!--添加弹窗-->
        <!--监标人数据-->
        <div class="tableData_box">
          <template>
            <el-table
              :data="tableDataInspect"
              border
              style="width: 100%">
              <el-table-column
                prop="userAccount"
                label="登录名"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="userName"
                label="监标人姓名"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="userPassword"
                label="登录密码"
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
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="telephone"
                label="联系方式"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                label="操作"
                width="100"
                align="center" v-if="!subStatus">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="editInspectBtn(scope)">编辑</el-button>
                  <el-button type="text" size="small" @click="deleteInspectBtn(scope)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </div>
        <!--监标人数据-->
        <!--提交按钮-->
        <div class="sub_btnbigbox">
          <el-button size="small" class="btn-sub-bg" @click="subExpert" v-if="!subStatus" :loading="isLoading">提 交</el-button>
          <el-button type="primary" class="sub_btn" disabled v-else>已提交</el-button>
        </div>
        <!--提交按钮-->
      </div>
    </div>
    <!--监标人-->
    <!--底部-->
    <footers></footers>
    <!--底部-->
  </div>
</template>
<script>
import {Numbertype, isvalidFixedPhone} from '@/assets/js/validate'
import footers from '@/views/admin/footer/footer.vue'
import project from '@/components/project.vue'
import { scalingPrepare } from '@/api'
export default {
  components: {
    footers,
    project
  },
  data () {
    // 数字验证
    var isNumber = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入登录密码'))
      } else if (!Numbertype(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    // 电话号码
    var isPhone = (rule, value, callback) => {
      if (!value) {
        callback()
      } else if (!isvalidFixedPhone(value)) {
        callback(new Error('请输入正确的格式'))
      } else {
        callback()
      }
    }
    return {
      pageSize: 10, // 每页展示条数
      pageNo: 0,
      flag: true, // 自定义编辑和增加状态
      titileName: '增加',
      // 选择组长的单选定义
      radio: '',
      projectForm: {},
      projectId: this.$store.getters.projectId,
      methodType: this.$route.query.methodType,
      expertGroupId: 1,
      expertMemberId: 2,
      inspectId: 4,
      // 定标委员会数据
      tableDataExpert: [
      ],
      // 定标委员会from
      expertForm: {
      },
      // 监标人数据
      tableDataInspect: [
      ],
      // 监标人from
      inspectForm: {
      },
      // 弹框lable宽度
      formLabelWidth: '120px',
      // 是否提交
      subStatus: false,
      // 弹框定义（定标委员）
      dialogScalingVisible: false,
      // 弹框定义（监标人）
      dialogInspectVisible: false,
      // 定标委员id自定义
      expertId: '',
      // 提交定标委员信息的验证
      expertRules: {
        userAccount: [
          { required: true, message: '请输入登录名', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        userName: [
          { required: true, message: '请输入定标人姓名', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        userPassword: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: ['blur', 'change']},
          { trigger: ['blur', 'change'], validator: isNumber }
        ],
        companyName: [
          { required: false, message: '请输入工作单位', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        userDuty: [
          { required: false, message: '请输入职务', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        telephone: [
          { required: false, message: '请输入联系方式', trigger: ['blur', 'change'] },
          { trigger: ['blur', 'change'], validator: isPhone }
        ]
      },
      inspectRules: {
        userAccount: [
          { required: true, message: '请输入登录名', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 2000 个字符', trigger: ['blur', 'change']}
        ],
        userName: [
          { required: true, message: '请输入监标人姓名', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 20 个字符', trigger: ['blur', 'change']}
        ],
        userPassword: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] },
          {min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: ['blur', 'change']},
          { trigger: ['blur', 'change'], validator: isNumber }
        ],
        companyName: [
          { required: false, message: '请输入工作单位', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        userDuty: [
          { required: false, message: '请输入职务', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ],
        telephone: [
          { required: false, message: '请输入联系方式', trigger: ['blur', 'change'] },
          { trigger: ['blur', 'change'], validator: isPhone }
        ]
      },
      isExpertLoading: false,
      isInspectLoading: false,
      isLoading: false
    }
  },
  methods: {
    detail () {
      scalingPrepare.getDetail(this.projectId).then((res) => {
        this.projectForm = res.data.ProjectView.project
        this.tableDataExpert = res.data.ProjectView.users
        this.tableDataExpert.map((its) => {
          if (its.initRoleId === this.expertGroupId) {
            this.radio = its.objectId
            this.expertId = its.objectId
          }
        })
        this.tableDataInspect = res.data.ProjectView.surveillance
      })
    },
    // **************************************************************************定标委员会数据接口******************************************************
    // 点击添加显示弹框（定标委员会）
    addExpert () {
      this.flag = true
      this.expertForm = {
        projectId: this.projectId,
        initRoleId: this.expertMemberId,
        objectId: '',
        userAccount: '',
        userName: '',
        companyName: '',
        userDuty: '',
        telephone: '',
        userPassword: '123'
      }
      this.titileName = '添加'
      this.dialogScalingVisible = true
    },
    // 处理
    handleData (code, type) {
      if (code === '0000') {
        this.detail()
        switch (type) {
          case 'expertForm':
            this.cancelScalingBtn()
            break
          case 'inspectForm':
            this.inspectBtn()
            break
        }
      }
    },
    // 提交定标委员会
    scalingSub (expertForm, flag) {
      let params = {
        methodType: this.methodType
      }
      this.$refs[expertForm].validate((valid) => {
        if (valid) {
          this.isExpertLoading = true
          if (flag) { // 增加
            scalingPrepare.add(this.expertForm, params).then((res) => {
              this.handleData(res.data.resCode, 'expertForm')
              this.isExpertLoading = false
            })
          } else { // 编辑
            console.log(this.expertForm)
            this.expertForm.initRoleId = this.expertMemberId
            console.log(this.expertForm)
            scalingPrepare.update(this.expertForm, params).then((res) => {
              this.isExpertLoading = false
              this.handleData(res.data.resCode, 'expertForm')
            })
          }
        } else {
          return false
        }
      })
    },
    // 编辑时查看人员信息（定标委员会）
    editBtn (scope) {
      let params = {
        methodType: this.methodType
      }
      scalingPrepare.getUserInfo(scope.row.objectId, params).then((res) => {
        if (res.data.resCode === '0000') {
          this.flag = false
          this.titileName = '编辑'
          this.dialogScalingVisible = true
          this.expertForm = res.data.User
        }
      })
    },
    // 删除人员信息（定标委员会）
    deleteBtn (scope) {
      let params = {
        methodType: this.methodType
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        scalingPrepare.delete(scope.row.objectId, params).then((res) => {
          this.detail()
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
    // 关闭内容（定标委员会）
    cancelScalingBtn () {
      this.dialogScalingVisible = false
      this.$refs['expertForm'].resetFields()
    },
    // **************************************************************************监标人数据接口******************************************************
    // 添加监标人按钮
    addInspect () {
      this.flag = true
      this.inspectForm = {
        projectId: this.projectId,
        initRoleId: this.inspectId,
        objectId: '',
        userAccount: '',
        userName: '',
        companyName: '',
        userDuty: '',
        telephone: '',
        userPassword: '123'
      }
      this.titileName = '添加'
      this.dialogInspectVisible = true
    },
    // 提交监标人
    inspectSub (inspectForm, flag) {
      let params = {
        methodType: this.methodType
      }
      this.$refs[inspectForm].validate((valid) => {
        if (valid) {
          this.isInspectLoading = true
          if (flag) { // 增加
            scalingPrepare.add(this.inspectForm, params).then((res) => {
              this.isInspectLoading = false
              this.handleData(res.data.resCode, 'inspectForm')
            })
          } else { // 编辑
            scalingPrepare.update(this.inspectForm, params).then((res) => {
              this.isInspectLoading = false
              this.handleData(res.data.resCode, 'inspectForm')
            })
          }
        } else {
          return false
        }
      })
    },
    // 编辑监标人
    editInspectBtn (scope) {
      let params = {
        methodType: this.methodType
      }
      scalingPrepare.getUserInfo(scope.row.objectId, params).then((res) => {
        if (res.data.resCode === '0000') {
          this.flag = false
          this.titileName = '编辑'
          this.dialogInspectVisible = true
          this.inspectForm = res.data.User
        }
      })
    },
    // 删除人员信息（监标人）
    deleteInspectBtn (scope) {
      let params = {
        methodType: this.methodType
      }
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        scalingPrepare.delete(scope.row.objectId, params).then((res) => {
          this.detail()
        })
      }).catch(() => {
        return false
      })
    },
    // 关闭内容（监标人）
    inspectBtn () {
      this.dialogInspectVisible = false
      this.$refs['inspectForm'].resetFields()
    },
    // ***********************************************************定标委员会和监标人**********************************************************************
    // 提交专家组长信息
    subExpert () {
      if (this.tableDataExpert.length <= 2) { // 判断定标委员会人数
        this.$message({
          type: 'warning',
          message: '定标委员会最少为三人'
        })
        return false
      }
      if (this.tableDataExpert.length % 2 === 0) { // 判断定标委员会人数为奇数还是偶数
        this.$message({
          type: 'warning',
          message: '定标委员会必须为奇数'
        })
        return false
      }
      if (this.expertId === '') { // 判断是否选择了组长
        this.$message({
          type: 'warning',
          message: '请选择一个组长'
        })
        return false
      }
      if (this.tableDataInspect.length <= 1) { // 判断监标人数
        this.$message({
          type: 'warning',
          message: '监标人数最少为二人'
        })
        return false
      }
      let params = {
        methodType: this.methodType
      }
      this.isLoading = true
      scalingPrepare.setLeader(this.projectId, this.expertId, this.expertGroupId, this.expertMemberId, params).then((res) => {
        this.isLoading = false
        // 更新流程
        this.init()
      })
    },
    // 流程控制
    getVoteRoomPage () {
      scalingPrepare.getVoteRoomPage(this.projectId).then(res => {
        // console.log(res)
        this.subStatus = res.data.isHide === '1'
      })
    },
    init () {
      this.detail()
      this.getVoteRoomPage()
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style lang="scss">
#scaling_index {
  .addbtn_box{
    text-align: left;
    padding-top: 10px;
    padding-bottom: 25px;
  }
  .scaling-box{
    background: #fff;
    margin-top: 15px;
    padding: 20px;
  }
  .sub_btnbigbox{
    padding-top: 36px;
  }
  .expertsubbox{
    text-align: center;
  }
}
</style>
