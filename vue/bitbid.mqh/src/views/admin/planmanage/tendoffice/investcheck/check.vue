<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="check-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/investcheck' }">投资建议计划审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/investmanage/investcheck/editcheck' }">审核</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box planaccept-cont">
      <el-tabs v-model="activeCode" type="card" @tab-click="handleTabsClick">
        <el-tab-pane :label="item.capitalSourceName?item.capitalSourceName:'工程建设'" v-for="(item, index) in classifyList" :key="index" :name="item.code">
          <component :is="currentView" @getChildValue="getChildValue" :activeCode="activeCode" :classify="classify">
            <!-- 组件在 vm.currentview 变化时改变！ -->
          </component>
        </el-tab-pane>
        <el-tab-pane label="项目储备库" name="recycle">
          <!--搜索区域start-->
          <div class="balance-search">
            <el-form :model="searchRecycleForm" :inline="true" class="demo-form-inline" style="text-align: left;">
              <el-form-item label="项目名称:">
                <el-input v-model="searchRecycleForm.messageLike" placeholder="请填写项目名称" clearable>
                </el-input>
              </el-form-item>
              <el-form-item label="资金来源:">
                <el-select v-model="searchRecycleForm.capitalSource" clearable placeholder="请选择资金来源">
                  <el-option
                    v-for="item in capitalSourceList"
                    :key="item.objectId"
                    :label="item.name"
                    :value="item.objectId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!--搜索区域end-->
          <div class="table">
            <el-table
              :data="recycleTableData"
              :row-class-name="tableRowClassName"
              border
              height="480"
              style="width: 100%">
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
                <template slot-scope="scope">
                <el-button type="text" size="mini" @click="clickCellCloumn(scope.row)" style="line-height: 0;overflow: hidden;width:100%;text-align: left;">{{scope.row.projectName}}</el-button>
                </template>
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
              <el-table-column
                prop="capitalSourceName"
                label="资金来源"
                align="center">
                <template slot-scope="scope">
                  {{scope.row.capitalSourceName?scope.row.capitalSourceName:capitalSourceName}}
                </template>
              </el-table-column>
              <el-table-column
                prop="fundPurpose"
                label="资金投向"
                align="center">
              </el-table-column>
              <el-table-column
                prop="lastUpdateName"
                label="操作人"
                align="center">
              </el-table-column>
              <el-table-column
                prop="lastUpdateDate"
                :formatter="filterDate"
                label="操作时间"
                align="center">
              </el-table-column>
              <el-table-column
                align="center"
                label="操作"
                width="100">
                <template slot-scope="scope">
                  <el-button @click="handleTableClick(scope.row,'recall')" type="text" size="medium">恢复</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        title="专项资金建议计划申报表"
        :visible.sync="dialogVisible"
        width="80%"
        center>
        <el-form :model="dialogBaseInfoForm" center>
          <div class="base-info">
            <table class="table-form">
              <tr>
                <td>项目名称</td>
                <td colspan="5">
                  <el-form-item prop="projectName">
                    <span>{{dialogBaseInfoForm.projectName}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>资金来源</td>
                <td colspan="2">
                  <el-form-item prop="capitalSource">
                    <span>{{dialogBaseInfoForm.capitalSourceName?dialogBaseInfoForm.capitalSourceName:this.capitalSourceName}}</span>
                  </el-form-item>
                </td>
                <td>计划费用(万元)</td>
                <td colspan="2">
                  <el-form-item prop="plannedCosts">
                    <span>{{dialogBaseInfoForm.plannedCosts  |filterPlannedCosts}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>资金投向</td>
                <td colspan="5">
                  <el-form-item prop="fundPurposeFirst" style="float: left">
                    <span>{{dialogBaseInfoForm.fundPurposeFirstName}}</span>
                  </el-form-item>
                  <div style="float: left">--</div>
                  <el-form-item prop="fundPurposeSecond" style="float: left">
                    <span>{{dialogBaseInfoForm.fundPurposeSecondtName}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>设备在籍(台)</td>
                <td>
                  <el-form-item prop="equipmentAbsentee">
                    <span>{{dialogBaseInfoForm.equipmentAbsentee}}</span>
                  </el-form-item>
                </td>
                <td>设备在用(台)</td>
                <td>
                  <el-form-item prop="equipmentInUse">
                    <span>{{dialogBaseInfoForm.equipmentInUse}}</span>
                  </el-form-item>
                </td>
                <td>备用(台)</td>
                <td>
                  <el-form-item prop="equipmentReserve">
                    <span>{{dialogBaseInfoForm.equipmentReserve}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>工程量</td>
                <td colspan="2">
                  <el-form-item prop="quantities">
                    <span>{{dialogBaseInfoForm.quantities}}</span>
                  </el-form-item>
                </td>
                <td>新增设备型号、参数</td>
                <td colspan="2">
                  <el-form-item prop="unitType">
                    <span>{{dialogBaseInfoForm.unitType}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>(大修、更新)周期</td>
                <td colspan="2">
                  <el-form-item prop="maintenanceCycle">
                    <span>{{dialogBaseInfoForm.maintenanceCycle}}</span>
                  </el-form-item>
                </td>
                <td>上次(大修、更新)时间</td>
                <td colspan="2">
                  <el-form-item prop="lastMaintenanceTime">
                    <span>{{dialogBaseInfoForm.lastMaintenanceTime  |filterDate}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>工程项目是否有方案</td>
                <td colspan="2">
                  <el-form-item prop="isPlan">
                    <span>{{dialogBaseInfoForm.isPlan |filterIsPlan}}</span>
                  </el-form-item>
                </td>
                <td>项目负责人</td>
                <td colspan="2">
                  <el-form-item prop="projectManager">
                    <span>{{dialogBaseInfoForm.projectManager}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>采购方式</td>
                <td colspan="2">
                  <el-form-item prop="procurementMethod">
                    <span>{{dialogBaseInfoForm.procurementMethod}}</span>
                  </el-form-item>
                </td>
                <td>工期</td>
                <td colspan="2">
                  <el-form-item prop="timeLimit">
                    <span>{{dialogBaseInfoForm.timeLimit |filterTimeLimit}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目责任部门</td>
                <td colspan="2">
                  <el-form-item prop="responsibleDepartment">
                    <span>{{dialogBaseInfoForm.responsibleDepartment}}</span>
                  </el-form-item>
                </td>
                <td>拟执行时间</td>
                <td colspan="2">
                  <el-form-item prop="executionTime">
                    <span>{{dialogBaseInfoForm.executionTime |filterDate}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td colspan="5">
                  <el-form-item prop="projectType">
                    <span>{{dialogBaseInfoForm.projectType |filterProjectType}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>提出原因</td>
                <td colspan="5">
                  <el-form-item prop="reason">
                    <span>{{dialogBaseInfoForm.reason}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="5">
                  <el-form-item prop="remark">
                    <span>{{dialogBaseInfoForm.remark}}</span>
                  </el-form-item>
                </td>
              </tr>
            </table>
            <span>注:表中内容请各单位认真填写，内容不完善，将不考虑列入</span>{{this.$route.query.year}}<span>年计划中。设备现有状况填写时，每（台或个）设备填写一行</span>
          </div>
          <div class="equipment-info">
            <h3>设备现有状况（设备购置及大修理项目填写）</h3>
            <el-table
              :data="dialogBaseInfoForm.equipmentTableData"
              border
              style="width: 100%">
              <el-table-column
                align="center"
                prop="type"
                label="型号">
              </el-table-column>
              <el-table-column
                align="center"
                prop="number"
                label="数量">
              </el-table-column>
              <el-table-column
                align="center"
                prop="purchaseTime"
                label="购置时间"
                :formatter="filterDates">
              </el-table-column>
              <el-table-column
                align="center"
                prop="durableYears"
                label="使用年限">
              </el-table-column>
              <el-table-column
                align="center"
                prop="usePlace"
                label="使用地点">
              </el-table-column>
              <el-table-column
                align="center"
                prop="remark"
                label="备注">
              </el-table-column>
            </el-table>
          </div>
          <div class="engineer-info">
            <h3>工程主要内容及备件主材（工程项目及大修理项目填写）</h3>
            <el-table
              :data="dialogBaseInfoForm.engineerTableData"
              border
              style="width: 100%">
              <el-table-column
                align="center"
                prop="name"
                label="项目名称">
              </el-table-column>
              <el-table-column
                align="center"
                prop="specification"
                label="规格型号">
              </el-table-column>
              <el-table-column
                align="center"
                prop="unit"
                label="单位">
              </el-table-column>
              <el-table-column
                align="center"
                prop="amount"
                label="数量">
              </el-table-column>
              <el-table-column
                align="center"
                prop="unitPrice"
                label="单价">
              </el-table-column>
              <el-table-column
                align="center"
                prop="total"
                label="合计">
              </el-table-column>
              <el-table-column
                align="center"
                prop="remark"
                label="备注">
              </el-table-column>
            </el-table>
          </div>
        </el-form>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import treeGrid from '@/components/treetable/TreeGrid.vue'
import {investmanage} from 'api/index'
import {formatDate} from 'common/js/date.js'
// 实例
export default {
  data () {
    return {
      currentView: 'checkPlan',
      activeCode: '',
      searchQueryForm: {}, // 查询建议计划-查询表单
      fundsFromList: [], // 搜索表单-资金来源
      dialogSourceList: [], // 弹窗-资金来源
      currentPage: 1,
      searchRecycleForm: {}, // 回收查询表单
      capitalSourceList: [], // 资金来源
      classifyList: [],
      classify: {},
      recycleTableData: [], // 回收站表格数据
      dialogVisible: false,
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况
        engineerTableData: [] // 工程主要内容及备件主材
      }, // 弹窗-基本信息表单
      tabsNewValue: null,
      capitalSourceName: '', // 工程建设项目资金来源
      capitalSourceClassify: {}, // 资金来源分类表
      suggestedPlanList: [] // 建议计划
    }
  },
  computed: {
  },
  watch: {
    tabsNewValue: function (newV, oldV) {
      this.getClassifyList()
    }
  },
  created () {
  },
  filters: {
    /** 申报表-计划费用 */
    filterPlannedCosts (value) {
      let plannedCosts = value
      if (plannedCosts !== '' && plannedCosts !== null) {
        return plannedCosts + '万元'
      }
    },
    /** 申报表-计划工期 */
    filterTimeLimit (value) {
      let timeLimit = value
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
      }
    },
    // 项目类型
    filterProjectType (value) {
      let projectType = value
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    },
    /** 是否有方案 */
    filterIsPlan (val) {
      let isPlan = val
      if (isPlan === 1) {
        return '是'
      } else if (isPlan === 0) {
        return '否'
      }
    },
    filterApproveResult (row) {
      let result = row
      if (result === 4) {
        return '通过'
      } else if (result === 5) {
        return '退回'
      } else if (result === 0) {
        return '发起申请'
      }
    },
    filterDate (val) {
      if (val === null) {
        return false
      } else {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    // 计划金额
    filterCurrentPlanAmount (val) {
      let currentPlanAmount = val
      if (currentPlanAmount !== '' && currentPlanAmount !== null) {
        return currentPlanAmount + '万元'
      }
    }
  },
  methods: {
    filterDate (row, column) {
      if (row.lastUpdateDate) {
        let date = new Date(row.lastUpdateDate)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    // 申报表-格式化购置时间
    filterDates (row, column) {
      if (!row.purchaseTime) {
        return ''
      } else {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /* tabs切换 */
    handleTabsClick (tab, event) {
      if (tab.name === 'recycle') {
        this.getSuggestedPlanList()
        this.getFirstClassifyCode()
      } else {
        this.activeCode = tab.name
      }
    },
    /** 分页 */
    handleSizeChange (val) {
    },
    handleCurrentChange (nowNum) {
    },
    /** 回收站搜索 */
    searchFun () {
      this.getSuggestedPlanList()
    },
    /** 获得子组件串个来的值 */
    getChildValue (val) {
      this.tabsNewValue = val
    },
    /** 表格操作-回收站 */
    handleTableClick (row, type) {
      if (Object.is(type, 'recall')) {
        // 查看
        this.recoveryClick(row)
      }
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row) {
      if (row.declarationForm) {
        this.dialogBaseInfoForm = row.declarationForm
        this.dialogBaseInfoForm.equipmentTableData = row.declarationForm.equipmentActualityList
        this.dialogBaseInfoForm.engineerTableData = row.declarationForm.accessoriesList
        this.dialogVisible = true
      } else {
        this.$message.warning('此项目下无申报表信息！')
        this.dialogVisible = false
      }
    },
    /** 根据项目code查询申报表信息 */
    getDeclarationForm (code) {
      let obj = {
        suggestedPlanCode: code
      }
      investmanage.getDeclarationForm(obj).then((res) => {
        if (res) {
          this.dialogBaseInfoForm = res.data.declarationForm
          this.dialogBaseInfoForm.equipmentTableData = this.dialogBaseInfoForm.equipmentActualityList
          this.dialogBaseInfoForm.engineerTableData = this.dialogBaseInfoForm.accessoriesList
        } else {
          return false
        }
      })
    },
    // 恢复-询问
    recoveryClick (row) {
      this.$confirm('确定恢复此数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行方法
        this.recoveryData(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    recoveryData (row) {
      let query = {
        code: row.capitalSourceClassifyCode
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
        this.getList(row)
      })
    },
    /***/
    getList (row) {
      let query2 = {
        capitalSourceClassifyCode: row.capitalSourceClassifyCode
      }
      investmanage.getSuggestedPlanList(query2).then(res => {
        this.suggestedPlanList = res.data.suggestedPlanList
      }).then(() => {
        if (this.capitalSourceClassify.isLocked === 1) {
          // 新增维护记录
          row.isInRecycleBin = 0
          row.isDelete = 0
          this.suggestedPlanList.push(row)
          this.createModifyData()
        } else {
          // 修改维护记录
          row.isInRecycleBin = 0
          row.isDelete = 0
          investmanage.saveSuggestedPlan(row).then(res => {
            this.getSuggestedPlanList()
          })
        }
      })
    },
    /** 新增修改记录 */
    createModifyData () {
      let newClassify = this.capitalSourceClassify
      newClassify.operate = 2
      newClassify.objectId = null
      newClassify.firstClassifyCode = this.firstClassifyCode
      this.suggestedPlanList.map(suggestedPlan => {
        suggestedPlan.objectId = null
        suggestedPlan.capitalSource = this.capitalSourceClassify.capitalSourceId
        suggestedPlan.creator = null
        if (suggestedPlan.declarationForm) {
          suggestedPlan.declarationForm.objectId = null
          suggestedPlan.declarationForm.projectName = suggestedPlan.projectName
          suggestedPlan.declarationForm.creator = null
          suggestedPlan.declarationForm.equipmentActualityList.map((equipmentActuality) => {
            equipmentActuality.objectId = null
            return equipmentActuality
          })
          suggestedPlan.declarationForm.accessoriesList.map((accessories) => {
            accessories.objectId = null
            return accessories
          })
        }
        return suggestedPlan
      })
      this.$set(newClassify, 'suggestedPlanList', this.suggestedPlanList)
      investmanage.saveClassify(newClassify).then((res) => {
        this.getClassifyList()
      })
    },
    /** 查询资金来源分类数据 */
    getClassifyList () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        operate: 6
      }
      investmanage.getClassifyList(query).then((res) => {
        this.classifyList = res.data.capitalSourceClassifyList
        if (this.classifyList.length) {
          if (this.tabsNewValue) {
            this.classifyList.map(classify => {
              if (this.tabsNewValue === classify.code) {
                this.activeCode = classify.code
                this.classify = classify
              }
            })
          } else {
            this.activeCode = this.classifyList[0].code
            this.classify = this.classifyList[0]
          }
        }
      })
    },
    /** 查询建议计划列表 */
    getSuggestedPlanList () {
      // 查询工程建设项目资金来源
      let query = {
        classify: 2,
        isAvailableFunds: 1
      }
      investmanage.getCapitalSourceList(query).then(res => {
        this.capitalSourceName = ''
        let capitalSourceList = res.data.capitalSourceList
        capitalSourceList.map(capitalSource => {
          this.capitalSourceName += capitalSource.name + '、'
        })
        this.capitalSourceName = this.capitalSourceName.substring(0, this.capitalSourceName.length - 1)
      }).then(res => {
        let query = {
          investmentProposalSchemeCode: this.$route.query.code,
          isInRecycleBin: 1,
          isDelete: 1,
          messageLike: this.searchRecycleForm.messageLike || null,
          capitalSource: this.searchRecycleForm.capitalSource
        }
        investmanage.getNewestSuggestedPlanList(query).then((res) => {
          if (res.data.suggestedPlanList) {
            this.recycleTableData = res.data.suggestedPlanList
            this.recycleTableData.map(suggestedPlan => {
              if (suggestedPlan.fundPurposeFirstName) {
                if (suggestedPlan.fundPurposeSecondtName) {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
                } else {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
                }
              } else {
                suggestedPlan.fundPurpose = ''
              }
            })
          }
        })
      })
    },
    /** 查询资金来源 */
    getCapitalSourceList () {
      let query = {
        classify: 1,
        isAvailableFunds: 1
      }
      investmanage.getCapitalSourceList(query).then((res) => {
        this.capitalSourceList = res.data.capitalSourceList
        let capitalSource = {}
        capitalSource.name = '工程建设'
        capitalSource.objectId = 0
        this.capitalSourceList.push(capitalSource)
      })
    },
    /** 查询同一资金来源下首个资源管理分类编号 */
    getFirstClassifyCode () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        capitalSourceId: this.classify.capitalSourceId,
        isDelete: 0
      }
      investmanage.getFirstClassifyCode(query).then(res => {
        this.firstClassifyCode = res.data.firstClassifyCode
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getClassifyList()
    this.getCapitalSourceList()
  },
  components: {
    treeGrid,
    // 编辑
    checkPlan: resolve => require(['./checkplan.vue'], resolve)
  }
}
</script>
<style lang="less">
  #check-page {
    .balance-search {
      padding-bottom: 15px;
    }
    .submit-btn {
      padding-top: 20px;
    }
    /** 弹窗-表格样式 */
    .dialog-box {
      .base-info .table-form {
        width: 100%;
        border-collapse: collapse;
        border: 1px solid #eee;
        text-align: center;
        display: inline-block;
        overflow: hidden;
        tr {
          width: 100%;
        }
        tr td {
          min-height: 50px;
          line-height: 50px;
          border: 1px solid #eee;
        }
        tr td:nth-child(odd) {
          min-width: 150px;
          background: #F6F7F9;
        }
        tr td:nth-child(even) {
          width: 320px;
          text-align: left;
          padding: 0 10px;
        }
      }
      .el-form-item__error {
        display: none;
      }
      .engineer-info, .equipment-info, .base-info {
        text-align: center;
        .el-form-item {
          margin-bottom: 0;
        }
      }
    }
  }
</style>
