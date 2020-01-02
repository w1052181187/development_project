<template>
  <!-- 标段 -->
  <div class="admin-layout" id="bidphase-page">
    <div class="bidphase-cont">
      <div class="bidphase-tit">
        <span>登记标段</span>
      </div>
      <div class="bidpsase-table">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="项目信息" name="first">
            <div class="register-btn">
              <el-button type="warning" @click="registerBidPhase" v-if="this.$store.getters.authUser.enterpriseType === 4">登记标段</el-button>
            </div>
            <el-table
              ref="multipleTable"
              :data="proInfoTableData"
              :row-class-name="tableRowClassName"
              border
              style="width: 100%"
              @selection-change="handleSelectionChange">
              <el-table-column
                type="selection"
                width="55"
                align="center">
              </el-table-column>
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                prop="projectName"
                label="项目名称"
                align="center">
              </el-table-column>
              <el-table-column
                prop="content"
                label="工程简要内容及设备型号"
                align="center">
              </el-table-column>
              <el-table-column
                prop="quantitiesUnit"
                label="工程量单位"
                align="center">
              </el-table-column>
              <el-table-column
                prop="address"
                label="概算"
                align="center">
                <el-table-column
                  prop="budgetQuantities"
                  label="工程量"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="budgetAmount"
                  label="金额"
                  align="center">
                </el-table-column>
              </el-table-column>
              <el-table-column
                prop="address"
                label="本年计划"
                align="center">
                <el-table-column
                  prop="currentPlanQuantities"
                  label="工程量"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="currentPlanAmount"
                  label="金额"
                  align="center">
                </el-table-column>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="标段信息" name="second">
            <el-form :model="bidphaseForm" :rules="bidphaseForm.rules" ref="bidphaseForm">
              <el-table
                border
                :data="bidphaseForm.tableData"
                :row-class-name="tableRowClassName"
                style="width: 100%">
                <el-table-column
                  label="标段名称"
                  align="center">
                  <template slot-scope="scope">
                    <el-form-item
                      :prop="'tableData.' + scope.$index + '.name'"
                      :rules="bidphaseForm.rules.name">
                      <el-input v-model="scope.row.name" v-if="scope.row.flagIndex === 0"></el-input>
                      <span v-else>{{scope.row.name}}</span>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="计划金额(万元)"
                  align="center">
                  <template slot-scope="scope">
                   <!-- <el-form-item
                      :prop="'tableData.' + scope.$index + '.planAmount'"
                      :rules="bidphaseForm.rules.planAmount" >
                      <el-input v-model="scope.row.planAmount" v-if="scope.row.flagIndex === 0"></el-input>
                      <span v-else>{{scope.row.planAmount}}</span>
                    </el-form-item>-->
                    <el-form-item>
                      <span>{{scope.row.planAmount}}</span>
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  align="center"
                  width="180">
                  <template slot-scope="scope">
                    <el-button @click="handleOneTableClick(scope.row,'look', scope.$index)" type="text" size="medium">查看</el-button>
                    <el-button @click="handleOneTableClick(scope.row,'edit', scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex !==0 && scope.row.biddingStatus === 0 && $store.getters.authUser.enterpriseType === 4 && operationCheckFlag">编辑</el-button>
                    <el-button @click="handleOneTableClick(scope.row,'confirm', scope.$index)" type="text" size="medium" v-if="scope.row.flagIndex ===0 && scope.row.biddingStatus === 0 && $store.getters.authUser.enterpriseType === 4 && operationCheckFlag">确定</el-button>
                    <el-button @click="handleOneTableClick(scope.row,'del', scope.$index)" type="text" size="medium" v-if="scope.row.biddingStatus === 0 && $store.getters.authUser.enterpriseType === 4 && operationCheckFlag">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-form>
             <div class="bidphase-btn" v-if="this.$store.getters.authUser.enterpriseType === 4 && this.operationCheckFlag">
              <el-button type="primary" @click="submitForm('bidphaseForm')">保 存</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <!-- 弹窗区域start -->
    <div class="dialog-box">
      <el-dialog
        title="登记标段"
        :visible.sync="visibleDialog"
        width="50%"
        center>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
          <el-form-item label="标段名称:" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="包含项目详情:">
            <el-table
              border
              :data="ruleForm.tableData"
              :row-class-name="tableRowClassName"
              style="width: 100%">
              <el-table-column
                prop="projectName"
                label="项目名称"
                align="center">
              </el-table-column>
              <el-table-column
                prop="content"
                label="项目主要内容"
                align="center">
              </el-table-column>
              <el-table-column
                prop="quantitiesUnit"
                label="工程量单位"
                align="center">
              </el-table-column>
              <el-table-column
                prop="budgetQuantities"
                label="工程量"
                align="center">
                <template slot-scope="scope">
                  {{scope.row.checkType ? scope.row.budgetQuantities : scope.row.currentPlanQuantities}}
                </template>
              </el-table-column>
              <el-table-column
                prop="budgetAmount"
                label="计划金额（万元）"
                align="center">
                <template slot-scope="scope">
                  {{scope.row.checkType ? scope.row.budgetAmount : scope.row.currentPlanAmount}}
                </template>
              </el-table-column>
              <!--<el-table-column-->
                <!--prop="currentPlanQuantities"-->
                <!--label="工程量"-->
                <!--align="center"-->
                <!--v-if="!checkType">-->
              <!--</el-table-column>-->
              <!--<el-table-column-->
                <!--prop="currentPlanAmount"-->
                <!--label="计划金额（万元）"-->
                <!--align="center"-->
                <!--v-if="!checkType">-->
              <!--</el-table-column>-->
            </el-table>
          </el-form-item>
          <el-form-item label="计划总金额（万元）:" style="text-align:left;">
            <span>{{ruleForm.totalPlanAmount}}</span>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelDialog">取 消</el-button>
          <el-button type="primary" @click="clickDialogConfirm('ruleForm')" :loading="isLoading">确 定</el-button>
        </span>
      </el-dialog>
      <!--查看标段start-->
      <el-dialog
        title="查看登记标段"
        :visible.sync="lookDialogVisible"
        width="40%"
        center>
        <el-form :model="lookRuleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="标段名称:">
            <span>{{lookRuleForm.name}}</span>
          </el-form-item>
          <el-form-item label="包含项目详情:">
            <el-table
              :data="lookRuleForm.tableData"
              border
              style="width: 100%">
              <el-table-column
                prop="projectName"
                label="项目名称">
              </el-table-column>
              <el-table-column
                prop="content"
                label="项目主要内容">
              </el-table-column>
              <el-table-column
                prop="quantitiesUnit"
                label="工程量单位">
              </el-table-column>
              <el-table-column
                prop="quantities"
                label="工程量">
                <template slot-scope="scope">
                  {{scope.row.checkType ? scope.row.budgetQuantities : scope.row.currentPlanQuantities}}
                </template>
              </el-table-column>
              <el-table-column
                prop="amount"
                label="计划金额(万元)">
                <template slot-scope="scope">
                  {{scope.row.checkType ? scope.row.budgetAmount : scope.row.currentPlanAmount}}
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--查看标段end-->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {section, tenderproject} from 'api/index'
import {isvalidPrice} from '../../../../assets/js/validate'
export default {
  data () {
    let validPrice = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPrice(value)) {
        callback(new Error('请输入数字'))
      } else {
        callback()
      }
    }
    return {
      lookDialogVisible: false,
      lookRuleForm: {},
      visibleDialog: false,
      activeName: 'first',
      proInfoTableData: [], // 项目信息
      ruleForm: {
        name: '',
        tableData: [],
        totalPlanAmount: ''
      },
      rules: {
        name: [
          {required: true, message: '请填写标段名称', trigger: ['blur', 'change']},
          {min: 1, max: 500, message: '请输入1~500个字符', trigger: ['blur', 'change']}
        ]
      },
      bidphaseForm: {
        rules: {
          name: {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']},
          planAmount: [
            {type: 'string', required: true, message: '必填字段', trigger: ['blur', 'change']},
            {validator: validPrice, trigger: 'blur'}
          ]
        },
        tableData: [
          {
            name: '',
            planAmount: ''
          }
        ]
      },
      // tableData: [],
      currentTenderProject: {
        selectApply: {
          entrustEnterprise: {
            objectId: null
          }
        }
      },
      buttonFlag: true,
      operationFlag: 'save',
      deleteRowInfoList: [],
      changeFlag: false, // 判断是否进行任何操作
      isLoading: false
    }
  },
  props: ['callbackdata', 'noticeCode', 'sectionOperationFlag', 'operationCheckFlag'],
  computed: {
  },
  created () {
    this.checkNoticeStage()
    this.getSuggestedPlanList()
  },

  /** 方法集 */
  methods: {
    // tabs切换
    handleClick (data) {
      this.activeName = data.name
      // 页面切换后重新获取数据
      if (this.activeName === 'first') {
        this.getSuggestedPlanList()
      } else {
        section.getList({
          pageNo: 1,
          pageSize: 0,
          tenderProjectCode: this.callbackdata
        }).then((res) => {
          this.bidphaseForm.tableData = []
          this.bidphaseForm.tableData = res.data.sectionList.list
        })
      }
    },
    // 表格多选
    handleSelectionChange (val) {
      this.ruleForm.totalPlanAmount = '' // 清空
      this.ruleForm.tableData = val
      if (this.ruleForm.tableData.length > 0) {
        // 计算计划总金额
        this.ruleForm.tableData.forEach(item => {
          if (item.budgetAmount !== null) {
            item.checkType = true // 判断列所绑定的数据来源
            this.ruleForm.totalPlanAmount = Number(this.ruleForm.totalPlanAmount) + Number(item.budgetAmount)
          } else {
            item.checkType = false
            this.ruleForm.totalPlanAmount = Number(this.ruleForm.totalPlanAmount) + Number(item.currentPlanAmount)
          }
        })
      }
    },
    /** 弹窗-确定 */
    clickDialogConfirm (formName) {
      this.isLoading = false
      this.$refs[formName].validate().then((vaild) => {
        if (vaild) {
          this.isLoading = true
          let sectionInfo = {
            tenderProjectCode: this.callbackdata,
            name: this.ruleForm.name,
            planAmount: this.ruleForm.totalPlanAmount,
            suggestedPlanSectionInfoList: []
          }
          this.ruleForm.tableData.forEach(item => {
            let obj = {
              suggestedPlanCode: item.code,
              sectionCode: '', // 后台处理
              number: item.checkType ? item.budgetQuantities : item.currentPlanQuantities,
              price: item.checkType ? item.budgetAmount : item.currentPlanAmount
            }
            sectionInfo.suggestedPlanSectionInfoList.push(obj)
          })
          section.save(sectionInfo).then((res) => {
            if (res.data.resCode === '0000') {
              this.getSuggestedPlanList()
              this.visibleDialog = false
              this.isLoading = false
            }
          })
        } else {
          return false
        }
      })
    },
    /** 取消 */
    cancelDialog () {
      this.visibleDialog = false
      this.$refs['ruleForm'].resetFields()
      this.$refs.multipleTable.clearSelection() // 清空上次的选择信息
    },
    indexMethod (index) {
      return index + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 获取当前公告流程状态 */
    checkNoticeStage () {
      if ((this.callbackdata && this.noticeCode) || !this.sectionOperationFlag) {
        this.buttonFlag = false
      }
    },
    /** 获取选中项目的所有标段 */
    getAllSectionInfo () {
      section.getList({
        pageNo: 1,
        pageSize: 0,
        tenderProjectCode: this.callbackdata
      }).then((res) => {
        this.bidphaseForm.tableData = []
        this.bidphaseForm.tableData = res.data.sectionList.list
        this.$emit('sectionlist', this.bidphaseForm.tableData)
      })
    },
    /** 获取对应的建议计划信息 */
    getSuggestedPlanList () {
      tenderproject.getSuggestedPlan(this.callbackdata).then((res) => {
        if (res && res.data.suggestedPlans) {
          this.proInfoTableData = res.data.suggestedPlans
        }
      })
    },
    /** 获取登记标段中的默认数据 */
    registerBidPhase () {
      if (this.ruleForm.tableData.length === 0) {
        this.$message.warning('请选择项目信息！')
        return false
      } else {
        this.visibleDialog = true
      }
    },
    // 添加一行
    // handleAddRow () {
    //   let obj = {
    //     objectId: null,
    //     name: '',
    //     planAmount: '',
    //     flagIndex: 0
    //   }
    //   this.changeFlag = true
    //   this.operationFlag = 'save'
    //   this.bidphaseForm.tableData.push(obj)
    // },
    /** 表格操作 */
    handleOneTableClick (row, type, index) {
      this.changeFlag = true
      if (Object.is(type, 'edit')) {
        this.$refs['bidphaseForm'].validate((valid) => {
          if (valid) {
            this.bidphaseForm.tableData = this.setTogglebutton(this.bidphaseForm.tableData, index, type)
            this.operationFlag = 'edit'
          } else {
            return false
          }
        })
      } else if (Object.is(type, 'confirm')) {
        this.$refs['bidphaseForm'].validate((valid) => {
          if (valid) {
            this.bidphaseForm.tableData = this.setTogglebutton(this.bidphaseForm.tableData, index, type)
            this.bidphaseForm.tableData[index] = row
            this.operationFlag = 'confirm'
          } else {
            return false
          }
        })
      } else if (Object.is(type, 'del')) {
        // this.deleteRow(index, this.bidphaseForm.tableData)
        this.deleteRow(index, this.bidphaseForm.tableData)
        this.operationFlag = 'del'
        this.deleteRowInfoList.push(row)
      } else if (Object.is(type, 'look')) {
        this.getSectionInfo(row.code)
      }
    },
    /** 查看单条记录 */
    getSectionInfo (code) {
      section.getOne(code).then((res) => {
        if (res.data.section) {
          this.lookRuleForm.name = res.data.section.name
        }
        if (res.data.suggestedPlanList.length > 0) {
          this.lookRuleForm.tableData = res.data.suggestedPlanList
          // 计算计划总金额
          this.lookRuleForm.tableData.forEach(item => {
            if (item.budgetAmount !== null) {
              item.checkType = true // 判断列所绑定的数据来源
            } else {
              item.checkType = false
            }
          })
        }
        this.lookDialogVisible = true
      })
    },
    deleteSection (rows) {
      rows.forEach(row => {
        if (row.name && row.planAmount) {
          section.delete(row.objectId).then(() => {
            this.getAllSectionInfo()
          })
        }
      })
    },
    /** 删除 */
    deleteRow (index, rows) {
      rows.splice(index, 1)
    },
    /** 保存 */
    submitForm (formName) {
      if (Object.is(this.operationFlag, 'save') || Object.is(this.operationFlag, 'edit') || Object.is(this.operationFlag, 'confirm')) {
        if (this.changeFlag) {
          this.changeFlag = false
          this.$refs[formName].validate().then((vaild) => {
            if (vaild) {
              this.bidphaseForm.tableData.forEach(item => {
                section.save(item).then(() => {
                  this.getAllSectionInfo()
                })
              })
            } else {
              return false
            }
          })
        }
      } else if (Object.is(this.operationFlag, 'del')) {
        this.deleteSection(this.deleteRowInfoList)
      }
    },
    /** 取消操作 */
    cancel () {
      this.getAllSectionInfo()
      /* if (this.bidphaseForm.tableData.length > 0) {
        for (let i = 0; i < this.bidphaseForm.tableData.length; i++) {
          if (this.bidphaseForm.tableData[i].objectId === null) {
            this.deleteRow(i, this.bidphaseForm.tableData)
          }
        }
      } */
    },
    /** 设置按钮切换 */
    setTogglebutton (arr, index, type) {
      let newArr = []
      // 将设置好的数据拷贝一份
      newArr = arr.map((item, current) => {
        if (index === current) {
          if (Object.is(type, 'edit') && arr[index].name && arr[index].planAmount) {
            item.flagIndex = 0
          } else if (Object.is(type, 'confirm') && arr[index].name && arr[index].planAmount) {
            item.flagIndex = 1
          }
          return item
        } else {
          return item
        }
      })
      return newArr
    }
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
#bidphase-page {
  .bidphase-cont {
    .bidphase-tit {
      height: 50px;
      line-height: 50px;
      text-align:left;
      padding: 0 20px;
      background:#F7F6FB;
      span {
        font-weight: 800;
      }
    }
    .bidpsase-table {
      padding: 20px;
      .certif-box {
        background-color: #ECEFFE;
        text-align: center;
        .certif-add-btn {
          width: 100%;
        }
      }
      .el-form-item {
        margin-bottom: 0;
      }
      .el-tabs__item.is-active {
        border: 0;
      }
      .register-btn {
        text-align: left;
        padding-bottom: 10px;
      }
    }
    .bidphase-btn {
      padding-bottom: 30px;
    }
  }
}
</style>
