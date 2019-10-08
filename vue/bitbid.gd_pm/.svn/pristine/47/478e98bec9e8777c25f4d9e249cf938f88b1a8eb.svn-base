<template>
  <div class="center_box" id="associated_box">
    <el-form :model="projectForm" :rules="rules" ref="projectForm" label-width="140px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">关联账号</div>
        <div class="coninfor_con">
          <div class="infor_side_conbigbox">
            <div class="dialog_cancel_infoson_father associated_conbig">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="代理机构名称：">
                    <span>{{enterpriseName}}</span>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="交易系统版本：" prop="versionId">
                    <el-select v-model="projectForm.versionId" placeholder="请选择" class="el_select" @change="selectVersion">
                      <el-option
                        v-for="item in projectData"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="推送地址：" prop="pushAddress">
                    <el-input placeholder="请输入推送地址" v-model="projectForm.pushAddress"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="开通期限：" class="mandatory">
                    <el-date-picker
                      class="contract_bigbox"
                      v-model="periodDataRange"
                      type="datetimerange"
                      value-format="yyyy-MM-dd HH:mm:ss"
                      range-separator="至"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间"
                      align="right">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <div class="dialog_cancel_infoson_father">
              <div class="dialog_cancel_infoson_title">
                <div class="dialog_cancel_infoson_titlewenzi">关联账号</div>
                <div class="dialog_cancel_infoson_titlexian"></div>
              </div>
              <div class="dialog_detail ass_select_btnbox">
                <div class="add_contract_btnbox">
                  <el-button type="primary" class="add_contract_btn" @click="selectBtn">选择</el-button>
                </div>
                <el-dialog title="选择账号" :visible.sync="accountVisible">
                  <div class="seacherbigboix">
                    <el-input class="search" style="vertical-align: top"  v-model.trim="seacherInput" placeholder="用户名/企业名称"></el-input>
                    <el-button  slot="append" icon="el-icon-search" class="seacher_btn" @click="secherAccount"></el-button>
                  </div>
                  <el-table
                    ref="multipleTable"
                    :data="accountTableData"
                    tooltip-effect="dark"
                    style="width: 100%"
                    border
                    height="250"
                    @select="handleSelectionChange"
                    @select-all="handleSelectionAll">
                    <el-table-column
                      type="selection"
                      width="55"  align="center">
                    </el-table-column>
                    <el-table-column
                      prop="name"
                      label="用户名">
                    </el-table-column>
                    <el-table-column
                      prop="namezh"
                      label="企业名称"
                      show-overflow-tooltip>
                    </el-table-column>
                  </el-table>
                  <div slot="footer" class="dialog-footer operationbtn">
                    <el-button type="primary" @click="determineBtn" class="queding_btn">确 定</el-button>
                    <el-button type="info" @click="accountVisible = false" class="quxiaoanniu_btn">取 消</el-button>
                  </div>
                </el-dialog>
              </div>
              <!--列表数据-->
              <div class="associated_data_box">
                <el-table
                  :data="associatedData"
                  border
                  height="280px"
                  header-cell-class-name="table_header">
                  <el-table-column
                    prop="name"
                    label="用户名"
                    align="left"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="namezh"
                    label="企业名称"
                    align="left"
                    show-overflow-tooltip>
                  </el-table-column>
                  <el-table-column
                    prop="isStartUsing"
                    label="状态"
                    align="left"
                    show-overflow-tooltip>
                    <template slot-scope="scope">
                      <div class="statusbigbox" v-if="scope.row.isStartUsing === 1">
                        <span class="statusbox">启用</span>
                      </div>
                      <div class="statusbigbox" v-if="scope.row.isStartUsing === 0">
                        <span class="statusbox">禁用</span>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column
                    label="操作" width="210" align="center">
                    <template slot-scope="scope">
                      <el-button type="text" size="small" class="handle_btn" @click="delectAccount(scope)">
                        删除
                      </el-button>
                      <el-button type="text" size="small" class="handle_btn" @click="enableBtn(scope)" v-if="scope.row.isStartUsing === 1">
                        禁用
                      </el-button>
                      <el-button type="text" size="small" class="handle_btn" @click="enableBtn(scope)" v-if="scope.row.isStartUsing === 0">
                        启用
                      </el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <!--列表数据-->
            </div>
          </div>
          <el-form-item class="operation_btn_box">
            <el-button type="primary" @click="submitForm('projectForm')" class="save_btn" :loading="subStatus">提交</el-button>
            <el-button @click="$router.go(-1)" class="cancel_btn" :loading="subStatus">取消</el-button>
          </el-form-item>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import uploadFile from '@/components/upload/publicFileUpload'
import {agencyEnterprise, versionManagement} from '@/api'
export default {
  name: '',
  components: {
    uploadFile
  },
  data () {
    return {
      periodDataRange: [], // 开通期限
      projectForm: {},
      subStatus: false, // 是否保存
      // 表单验证
      rules: {
        versionId: [
          { required: true, message: '请选择交易系统版本', trigger: ['blur', 'change'] }
        ],
        pushAddress: [
          { required: true, message: '请输入推送地址', trigger: ['blur', 'change'] },
          {min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: ['blur', 'change']}
        ]
      },
      // 交易系统版本
      projectData: [],
      // 关联账号列表
      associatedData: [],
      multipleSelection: [], // 关联账户选择数据
      accountVisible: false, // 关联弹框
      seacherInput: '', // 弹框搜索默认值
      accountTableData: [],
      enterpriseName: '',
      accountArray: [] // 关联账户提交时的信息
    }
  },
  methods: {
    // **************************************************************关联账户选择数据**************************************************************
    handleSelectionChange (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    handleSelectionAll (selection) {
      if (selection.length === 0) {
        this.multipleSelection = []
      } else {
        this.multipleSelection = selection
      }
    },
    // ******************************************************************弹框中方法*******************************************
    // 选择关联账户
    selectBtn () {
      this.accountVisible = true
    },
    // 选择版本号
    selectVersion () {
      this.agencyList()
      this.queryRefList()
    },
    // 关联账户数据
    agencyList () {
      let url = {}
      url = {
        isvalid: 1,
        type: 2,
        versionId: this.projectForm.versionId
      }
      if (this.seacherInput !== '') { // 当搜索内容不为空时执行
        url.messageLike = this.seacherInput
      }
      agencyEnterprise.refUser(url).then((res) => {
        this.accountTableData = res.data.sysUsers
      })
    },
    // *************************************************搜索*************************
    // 账户搜索
    secherAccount () {
      this.agencyList()
    },
    // 确定
    determineBtn () {
      this.accountVisible = false
      let tempArry = []
      this.multipleSelection.map((item) => {
        if (this.associatedData.indexOf(item) === -1) {
          tempArry.push(item)
        }
      })
      this.associatedData = this.associatedData.concat(tempArry)
    },
    // 删除一条
    delectAccount (scope) {
      this.associatedData.splice(scope.$index, 1)
    },
    queryAgency () {
      // 查询所有的版本
      let url = {
        isvalid: 1
      }
      versionManagement.queryNotPageList(url).then((res) => {
        console.log(res)
        res.data.versionManagements.forEach((item) => {
          let versionManagement = {}
          versionManagement.value = item.id
          versionManagement.label = item.versionName
          this.projectData.push(versionManagement)
        })
      })
      // 查询合作代理机构名称
      agencyEnterprise.detail(this.$route.query.objectId).then((res) => {
        this.enterpriseName = res.data.agencyEnterprise.enterpriseName
        this.projectForm.agencyId = res.data.agencyEnterprise.id
      })
    },
    // 关联后查询
    queryRefList () {
      let url = {}
      if (this.projectForm.versionId) {
        url = {
          isvalid: 1,
          agencyId: this.$route.query.objectId,
          versionId: this.projectForm.versionId
        }
      } else {
        url = {
          isvalid: 1,
          agencyId: this.$route.query.objectId
        }
      }
      agencyEnterprise.queryRefVersionUserList(url).then((res) => {
        if (res.data.refVersionUsers.length !== 0) {
          let newObject = { // 把后台返回的关联信息取出放在新对象
            versionId: res.data.refVersionUsers[0].versionId,
            pushAddress: res.data.refVersionUsers[0].pushAddress
          }
          this.projectForm = newObject // 把组合好的数据赋值给页面
          this.periodDataRange = [res.data.refVersionUsers[0].dredgeStartDate, res.data.refVersionUsers[0].dredgeEndDate] // 时间
          let newAccoun = res.data.refVersionUsers.map((ite) => { // 把后台返回的数据进行组合
            ite.sysUser.isStartUsing = ite.isStartUsing
            ite.sysUser.enableId = ite.id
            return ite.sysUser
          })
          this.associatedData = newAccoun // 取出后台返回的选中的数据赋值给页面表格
          this.multipleSelection = newAccoun // 取出后台返回的选中的数据赋值给弹框表格
        } else {
          this.associatedData = []
        }
      })
    },
    // ****************************************添加**********************************
    submitForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.periodDataRange) {
            this.projectForm.dredgeStartDate = this.periodDataRange[0]
            this.projectForm.dredgeEndDate = this.periodDataRange[1]
          }
          if (this.periodDataRange.length === 0) {
            this.$message({
              type: 'warning',
              message: '开通期限不能为空'
            })
            return false
          }
          if (this.accountArray.length === 0) {
            this.$message({
              type: 'warning',
              message: '请至少关联一个用户'
            })
            return false
          }
          this.accountArray.map((ite) => {
            ite = Object.assign(ite, this.projectForm)
            return ite
          })
          console.log(this.accountArray)
          agencyEnterprise.associatedSave(this.accountArray).then((res) => {
            this.$router.push({path: '/admin/contract-agency/contract/index'})
          })
        } else {
          return false
        }
      })
    },
    // *************************************************启用*************************
    enableBtn (scope) {
      // 启用的时候进行状态判断前端进行页面变化
      let isStartUsing = ''
      if (scope.row.isStartUsing === 0) {
        isStartUsing = 1
      } else {
        isStartUsing = 0
      }
      this.accountArray.map((ite) => { // 把查询出后的数据状态进行改变
        if (ite.userId === scope.row.id) {
          ite.isStartUsing = isStartUsing
        }
      })
      scope.row.isStartUsing = isStartUsing
    }
  },
  watch: {
    associatedData: function () {
      this.accountArray = []
      this.associatedData.map((its) => { // 提交时如果是原有出来的没有给它赋值为启动状态
        let account = {
          userId: its.id,
          isStartUsing: 1,
          agencyId: this.$route.query.objectId
        }
        if (its.hasOwnProperty('isStartUsing')) { // 提交时如果是原有出来的有那么保留状态
          account.isStartUsing = its.isStartUsing
        }
        this.accountArray.push(account)
      })
    }
  },
  mounted () {
    this.queryRefList() // 关联后查询到的数据
    this.queryAgency() // 查询版本信息
    this.agencyList() // 关联账户数据
  }
}
</script>

<style lang="less">
  #associated_box{
    .contract_bigbox{
      width: 100%;
    }
    .seacherbigboix{
      margin-bottom: 15px;
    }
  }
</style>
