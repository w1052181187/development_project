<template>
  <div>
    <div id="addItem" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item>首页</el-breadcrumb-item>
        <el-breadcrumb-item>
          <el-button size="mini" @click="returnBtn" class="return">项目收支管理</el-button>
        </el-breadcrumb-item>
        <el-breadcrumb-item class="active_bread">新增线下项目</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <!-- form表单 -->
      <el-row style="margin-top:48px">
          <el-form :model="ruleForm"  :rules="rules" ref="ruleForm" label-width="160px">
            <el-col :span="12">
              <el-form-item label="招标项目编号：" prop="bidProjectNumber">
                <el-input v-model="ruleForm.bidProjectNumber"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标项目名称：" prop="bidProjectName">
                <el-input v-model="ruleForm.bidProjectName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="招标方式：" prop="bidType">
                  <el-select class="zzt_zbfs_selectbox" v-model="ruleForm.bidType" placeholder="请选择招标方式" @change="bidsMethodSelect">
                    <el-option
                      v-for="item in bidsMethodSelet"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item v-show='ruleForm.bidType=="0"' label="资审方式：" prop="inforOnMethod">
                  <el-select class="zzt_zbfs_selectbox" v-model="ruleForm.inforOnMethod" placeholder="请选择资审方式">
                    <el-option
                      v-for="item in inforOnMethodSelet"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                      :disabled="item.disabled">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-form-item label="招标人：" prop="bidder">
                <el-input v-model="ruleForm.bidder"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-col :span="12">
                <el-form-item label="是否委托招标代理：" prop="isEntrustAgency">
                  <el-select class="zzt_zbfs_selectbox"  v-model="ruleForm.isEntrustAgency" placeholder="请选择">
                    <el-option label="否" value="0"></el-option>
                    <el-option label="是" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item v-show="ruleForm.isEntrustAgency=='1'" prop="agentStructure" label="代理机构：">
                  <el-input v-model="ruleForm.agentStructure"></el-input>
                </el-form-item>
              </el-col>
            </el-col>
            <el-col :span="12">
              <el-form-item label="项目经理：" prop="projectManager">
                <el-select class="zzt_zbfs_selectbox" v-model="ruleForm.projectManager" placeholder="请选择">
                  <el-option
                    v-for="item in projectOpfrom"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="所属部门：" prop="department">
                <el-select class="zzt_zbfs_selectbox"  v-model='ruleForm.department'  placeholder="请选择">
                  <el-option
                    v-for="item in departmentOpfrom"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-form>
      </el-row>
      <el-row>
        <el-form label-width="140px">
          <el-form-item label="标段信息：">
            <el-button type="primary"  class="addBiaoDuan gongneng_btn bitianicon" @click="addBiaoDuan">
              <span class="jia_zzt">+</span>
              <router-link to=""><span>增加标段</span></router-link>
            </el-button>
            <!--填写数据弹框-->
            <el-dialog :title="titleName" :visible.sync="dialogFormVisible" width='40%' @click="CancelSection">
              <el-form :model="sectionForm" :rules="sectionRules" ref="sectionForm">
                <el-form-item label="标段编号" :label-width="formLabelWidth" prop="sectionNumber">
                  <el-input v-model="sectionForm.sectionNumber" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="标段名称" :label-width="formLabelWidth" prop="sectionName">
                  <el-input v-model="sectionForm.sectionName" auto-complete="off"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="CancelSection">取 消</el-button>
                <el-button type="primary" @click="DetermineSection('sectionForm')">确 定</el-button>
              </div>
            </el-dialog>
            <!--填写数据弹框-->
            <el-table
              border
              header-cell-class-name="header"
              :data="tableData"
              style="width: 100%">
              <el-table-column
                label="序号"
                type="index"
                :index="1"
                width="80" align="center">
              </el-table-column>
              <el-table-column
                prop="sectionNumber"
                label="标段编号">
              </el-table-column>
              <el-table-column
                prop="sectionName"
                label="标段名称">
              </el-table-column>
              <el-table-column
                label="操作"
                width="120" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="editDetermineSection(scope)" v-if="$store.getters.permissions.includes('/projectpayments/update')">
                    修改
                  </el-button>
                  <el-button type="text" size="small" @click="delDetermineSection(scope)" v-if="$store.getters.permissions.includes('/projectpayments/delete')">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-form>
      </el-row>
      <!-- form表单 -->
      <el-row class="gongneng_btnbox">
        <el-button @click="submitpostData('ruleForm',flag)" class="gongneng_btn" size='medium' type="primary">提交</el-button>
        <el-button class="gongneng_btn" size='medium' type="info" @click="quxiaoBtn">
          取消
        </el-button>
      </el-row>
    </div>
  </div>
</template>
<script>
import {useradmin} from '../../api/index'
import { checkResponse } from '../../assets/js/common'
export default{
  data () {
    return {
      tableData: [],
      // 项目经理数据
      projectOpfrom: [],
      departmentOpfrom: [],
      // 提交表单数据
      ruleForm: {
        bidProjectNumber: '',
        bidProjectName: '',
        bidType: '',
        bidder: '',
        projectManager: '',
        sectionName: '',
        agentStructure: '',
        department: '',
        isEntrustAgency: '',
        inforOnMethod: ''
      },
      rules: {
        bidProjectNumber: [
          { required: true, message: '招标项目编号不能为空', trigger: 'blur' },
          {min: 1, max: 23, message: '请输入1~23个字符', trigger: ['blur', 'change']}
        ],
        bidProjectName: [
          { required: true, message: '招标项目名称不能为空', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ],
        bidType: [
          { required: true, message: '请选择招标方式', trigger: 'blur' }
        ],
        bidder: [
          { required: true, message: '招标人名称不能为空', trigger: 'blur' },
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ],
        inforOnMethod: [
          { required: true, message: '请选择资审方式', trigger: 'blur' }
        ],
        isEntrustAgency: [
          { required: true, message: '请选择是否委托招标代理', trigger: 'blur' }
        ],
        agentStructure: [
          { required: true, message: '代理机构名称不能为空', trigger: 'blur' },
          {min: 1, max: 100, message: '请输入1~100个字符', trigger: ['blur', 'change']}
        ],
        projectManager: [
          { required: true, message: '项目经理不能为空', trigger: 'blur' }
        ],
        department: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ]
      },
      dialogFormVisible: false,
      formLabelWidth: '120px',
      titleName: '增加标段',
      bidsMethodSelet: [{
        value: 0,
        label: '公开招标'
      }, {
        value: 1,
        label: '邀请招标'
      }],
      inforOnMethodSelet: [{
        value: 0,
        label: '资格后审'
      }, {
        value: 1,
        label: '资格预审'
      }],
      incExpenseId: null,
      // 提交弹框数据
      sectionForm: {
        sectionNumber: '',
        sectionName: ''
      },
      addStatus: 0,
      // 编辑添加自定义
      flag: true,
      // 添加标段自定义状态
      biaoduanStatus: false,
      sectionRules: {
        sectionNumber: [
          { required: true, message: '标段编号不能为空', trigger: 'blur' },
          {min: 1, max: 23, message: '请输入1~23个字符', trigger: ['blur', 'change']}
        ],
        sectionName: [
          { required: true, message: '标段名称不能为空', trigger: 'blur' },
          {min: 1, max: 200, message: '请输入1~200个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // 增加标段点击显示弹框
    addBiaoDuan () {
      this.dialogFormVisible = true
      this.titleName = '增加标段'
    },
    // 修改
    editDetermineSection (scope) {
      this.dialogFormVisible = true
      this.titleName = '编辑标段'
      this.flag = false
      this.axios.get(`section-information/${scope.row.objectId}`).then((res) => {
        this.sectionForm = res.data.sectionInformation
      })
    },
    // 添加标段
    DetermineSection (formName, flag) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.flag) {
            this.biaoduanStatus = true
            if (this.addStatus === 0) {
              this.addStatus += 1
              this.axios.post('income-expenses', {}).then((res) => {
                console.log(res)
                this.incExpenseId = res.data.incomeExpense.objectId
                this.sectionForm.incExpenseId = this.incExpenseId
                this.axios.post('section-information', this.sectionForm).then((res) => {
                  console.log(this.sectionForm)
                  this.sectioninformationList()
                  this.empty()
                })
              })
            } else {
              this.sectionForm.incExpenseId = this.incExpenseId
              this.axios.post('section-information', this.sectionForm).then((res) => {
                console.log(this.sectionForm)
                this.sectioninformationList()
                this.empty()
              })
            }
          } else {
            this.axios.put('section-information', this.sectionForm).then((res) => {
              this.sectioninformationList()
              this.empty()
            })
          }
        } else {
          return false
        }
      })
    },
    // 标段列表接口
    sectioninformationList () {
      this.axios.get(`section-information?incExpenseId=${this.incExpenseId}&_t=${new Date().getTime()}`).then((res) => {
        console.log(res)
        this.tableData = res.data.sectionInformationPageInfo.list
      })
    },
    // 删除
    delDetermineSection (scope) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.axios.delete(`section-information/${scope.row.objectId}`).then((res) => {
          checkResponse(this, res, () => {
            this.sectioninformationList()
          })
        })
      }).catch(() => {
        return false
      })
    },
    // 标段取消
    CancelSection () {
      this.empty()
    },
    // 招标方式选择
    bidsMethodSelect (val) {
      if (val == 1) {
        this.ruleForm.inforOnMethod = ''
      }
    },
    // 提交form表单数据
    submitpostData (formName) {
      this.$confirm('是否提交数据吗？', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.ruleForm.bidType == 1) {
          this.rules.inforOnMethod = [
            { required: false, message: '请选择资审方式', trigger: 'change' }
          ]
          this.ruleForm.inforOnMethod = ''
        } else {
          this.rules.inforOnMethod = [
            { required: true, message: '请选择资审方式', trigger: 'change' }
          ]
        }
        if (this.ruleForm.isEntrustAgency == 0) {
          delete this.ruleForm.agentStructure
          delete this.rules.agentStructure
        } else {
          this.rules.agentStructure = [
            { required: true, message: '代理机构名称不能为空', trigger: 'blur' },
            { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
          ]
        }
        this.$refs[formName].validate((valid) => {
          if (this.tableData.length === 0) {
            this.$message({
              type: 'warning',
              message: '请至少添加一个标段'
            })
          } else if (valid) {
            this.ruleForm.projectNature = 0
            this.ruleForm.objectId = this.incExpenseId
            this.axios.put('income-expenses', this.ruleForm).then((res) => {
              console.log(this.ruleForm)
              checkResponse(this, res, () => {
                this.$router.push({path: '/projectpayments'})
              })
            })
          } else {
            return false
          }
        })
      }).catch(() => {
        return false
      })
    },
    // form表单数据取消
    quxiaoBtn () {
      if (this.biaoduanStatus) {
        this.axios.delete(`income-expenses/${this.incExpenseId}`).then((res) => {
          this.$router.push({path: '/projectpayments'})
        })
      } else {
        this.$router.push({path: '/projectpayments'})
      }
    },
    // 面包屑返回
    returnBtn () {
      if (this.biaoduanStatus) {
        this.axios.delete(`income-expenses/${this.incExpenseId}`).then((res) => {
          this.$router.push({path: '/projectpayments'})
        })
      } else {
        this.$router.push({path: '/projectpayments'})
      }
    },
    empty () {
      this.dialogFormVisible = false
      this.sectionForm = {}
    },
    // 部门和项目经理
    deptManager () {
      var managerObj = {
        value: '',
        label: ''
      }
      var deptObj = {
        value: '',
        label: ''
      }
      useradmin.deptManagerlistbibi(this.$store.getters.authUser.userId).then((res) => {
        managerObj.value = res.data.user.id
        managerObj.label = res.data.user.name
        this.projectOpfrom.push(managerObj)
        deptObj.value = res.data.user.dept.id
        deptObj.label = res.data.user.dept.name
        this.departmentOpfrom.push(deptObj)
      })
    },
    // 调用河南网数据
    deptManagerhebibi () {
      var bibimanagerObj = {
        value: '',
        label: ''
      }
      var bibideptObj = {
        value: '',
        label: ''
      }
      useradmin.deptManagerlist(this.$store.getters.authUser.userId).then((res) => {
        bibimanagerObj.value = res.data.user.objectId
        bibimanagerObj.label = res.data.user.name
        this.projectOpfrom.push(bibimanagerObj)
        bibideptObj.value = res.data.user.dept.objectId
        bibideptObj.label = res.data.user.dept.name
        this.departmentOpfrom.push(bibideptObj)
      })
    }
  },
  mounted () {
    // 比比网
    this.deptManagerhebibi()
  }
}

</script>
<style lang='less'>
body{
  background:#eaedf1;
}
#addItem{
    padding: 0 20px;
    padding-bottom: 50px;
    .breadcrumb_box{
        width: 100%;
        height: 60px;
        border-bottom: 1px solid #eeeeee;
        padding: 0 20px;
        box-sizing: border-box;
        line-height: 60px;
    }
  .da_xz_ckbigbox {
    padding: 10px 20px;
  }

  a{
    text-decoration:none;
  }
  .gongneng_btn a{
    color:#fff;
  }
    .el-breadcrumb__inner{
        color: #999999!important;
    }
    .active_bread>.el-breadcrumb__inner{
        color:#333333!important;
    }
  .gongneng_btnbox{
    text-align: center;
  }
    .header{background: #f5f7f8}
    .da_xz_ckbigbox{
        border: none;
        line-height:60px;
        padding:0 0 0 20px;
        .el-form-item{padding: 0px}
        .el-dialog__header{padding-top: 0;padding-bottom: 0px}
        .el-dialog__body{padding-bottom: 0px}
    }
    .addBiaoDuan{
        margin-bottom: 10px;
    }
  .zzt_zbfs_selectbox{
    width: 100%;
  }
  .el-form-item .el-form-item {
    margin-bottom: 20px;
  }
  .return{
    border: none;
    font-size: 15px;
  }
  .return:hover {
    color: #333333;
    border-color: transparent;
    background-color: transparent;
  }
  .el-table .cell {
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    word-break: break-all;
    line-height: 23px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
}
</style>
