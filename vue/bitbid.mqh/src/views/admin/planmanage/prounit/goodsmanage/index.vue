<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="goodsmanage-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/goodsmanage' }">货物采购管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索区域start -->
    <div class="planmanage-search">
      <el-form :model="ruleSearchForm" ref="ruleSearchForm" label-width="100px" class="demo-ruleForm">
        <el-row :gutter="20">
          <el-col :span="5" style="width:300px">
            <el-form-item label="年度:">
              <el-select v-model="ruleSearchForm.year" clearable placeholder="请选择">
                <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="width:300px">
            <el-form-item label="项目单位:">
              <el-input v-model="ruleSearchForm.executeEnterpriseName" style="width:215px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="width:300px">
            <el-form-item label="采购方式:">
              <el-select v-model="ruleSearchForm.purchaseType" clearable placeholder="请选择">
                <el-option
                  v-for="item in purchaseTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="width:300px">
            <el-form-item label="需求方:">
              <el-input v-model="ruleSearchForm.enterpriseName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2" style="padding-left:30px;">
            <el-form-item>
              <el-button type="primary" @click="onSearch(ruleSearchForm)" icon="el-icon-search" style="margin-left:-70px;">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!-- 搜索区域end -->

    <!-- 主体内容区域start -->
    <div class="maincont-box planmanage-cont">
      <div class="planmanage-cont-tit">
        <el-row :gutter="20">
          <el-col :span="12" style="text-align: left;">
            <span class="pre-dowm-l">单位名称:</span><span>{{this.$store.getters.authUser.enterpriseName}}</span>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <span class="pre-dowm-l">投资单位:</span><span>万元</span>
          </el-col>
        </el-row>
      </div>
      <!-- 表格start -->
      <el-table
        :data="tableData"
        :row-class-name="tableRowClassName"
        border
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="55">
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="projectPlanName"
          label="采购项目名称">
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="projectList"
          label="项目名称"
          width="260">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.projectList" :key="index" class="dresult-span" v-if="item.projectName">
              <el-button type="text" size="mini" @click="clickCellCloumn(item, scope.row.year)">{{item.projectName}}</el-button>
            </p>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="executeEnterpriseName"
          label="项目单位"
          width="260">
        </el-table-column>
        <el-table-column
          align="center"
          prop="year"
          label="年度"
          width="80">
        </el-table-column>
        <el-table-column
          align="right"
          prop="planTotalAmount"
          label="计划总金额(万元)"
          width="100">
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="enterpriseName"
          label="需求方">
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium"  v-if="$store.getters.permissions.includes('/planmanage/goodsmanage/detail')">查看</el-button>
            <span>
              <el-button @click="handleTableClick(scope.row,'self')" type="text" size="medium" :disabled="operationDisabled" v-if="scope.row.purchaseType === 2 && $store.getters.permissions.includes('/planmanage/goodsmanage/operation')">转自采</el-button>
              <el-button @click="handleTableClick(scope.row,'self')" type="text" size="medium" v-else disabled>转自采</el-button>
              <el-button @click="handleTableClick(scope.row,'group')" type="text" size="medium" :disabled="operationDisabled" v-if="scope.row.purchaseType === 1 && $store.getters.permissions.includes('/planmanage/goodsmanage/operation')">转集采</el-button>
              <el-button @click="handleTableClick(scope.row,'group')" type="text" size="medium" v-else disabled>转集采</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <!--分页-->
      <div class="block-page">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="pageSizeList"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
      <!-- 表格end -->
    </div>
    <!-- 主体内容区域end -->

    <!-- 弹窗区域start -->
    <div class="planmanage-dialog">
      <el-dialog
        title="查看详情"
        :visible.sync="dialogRequestVisible"
        width="50%">
        <el-form ref="manageFormInfo" :model="manageFormInfo" label-width="130px">
          <el-form-item label="招标计划年度:">
            <span>{{manageFormInfo.year}}</span>
          </el-form-item>
          <el-form-item label="招标计划名称:">
            <span>{{manageFormInfo.projectPlanName}}</span>
          </el-form-item>
          <el-form-item label="项目名称:">
            <template>
              <span v-for = "(item,index) in manageTableData" :key="index" class="dresult-span" v-if=" manageTableData.length > (index+1)">{{item.projectName + ','}}</span>
              <span v-else>{{item.projectName}}</span>
            </template>
          </el-form-item>
          <el-form-item label="包含项目详情:">
            <el-table
              :data="manageTableData"
              border
              style="width: 100%">
              <el-table-column
                prop="projectName"
                label="项目名称"
                align="left">
              </el-table-column>
              <el-table-column
                prop="content"
                label="项目主要内容"
                align="left">
              </el-table-column>
              <el-table-column
                prop="currentPlanQuantities"
                label="工程量"
                align="left">
              </el-table-column>
              <el-table-column
                prop="currentPlanAmount"
                label="计划金额(万元)"
                align="left">
              </el-table-column>
              <el-table-column
                prop="capitalSourceName"
                label="资金来源"
                align="left">
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="计划总金额(万元):">
            <span>{{manageFormInfo.planTotalAmount}}</span>
          </el-form-item>
          <el-form-item label="计划工期:">
            <span>{{manageFormInfo.planLimit |filterPlanLimit}}</span>
          </el-form-item>
          <el-form-item label="拟招标时间:">
            <span>{{manageFormInfo.bidStartTime | filterDate}}--{{manageFormInfo.bidEndTime | filterDate}}</span>
          </el-form-item>
          <el-form-item label="拟实施时间:">
            <span>{{manageFormInfo.executeStartTime | filterDate}}--{{manageFormInfo.executeEndTime | filterDate}}</span>
          </el-form-item>
          <el-form-item label="拟招标方式:">
            <span>{{manageFormInfo.bidType |filterBidType}}</span>
          </el-form-item>
          <el-form-item label="项目类型:">
            <span>{{manageFormInfo.projectType |filterProjectType}}</span>
          </el-form-item>
          <el-form-item label="需求方:">
            <span>{{manageFormInfo.enterpriseName}}</span>
          </el-form-item>
          <el-form-item label="附件:">
           <span class="file-list" v-for="(item, index) in manageFormInfo.fileInformationList" :key="index">
              <!-- <img src="./image/1.png" alt=""> -->
              <span><el-button type="text" @click="downloadFile(item.fileName,item.relativePath)">{{item.fileName}}</el-button></span>
            </span>
          </el-form-item>
          <el-form-item label="是否允许多次招标:">
            <span>{{manageFormInfo.isAgain |filterIsAgain}}</span>
          </el-form-item>
        </el-form>
      </el-dialog>
      <!--点击项目弹窗start-->
      <div class="planmanage-dialog">
        <el-dialog
          title="专项资金建议计划申报表"
          :visible.sync="dialogVisible"
          width="80%"
          center>
          <el-form :model="dialogBaseInfoForm" center>
            <div class="base-info">
              <h3>基本信息</h3>
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
                      <span>{{dialogBaseInfoForm.capitalSourceName}}</span>
                    </el-form-item>
                  </td>
                  <td>计划费用(万元)</td>
                  <td colspan="2">
                    <el-form-item prop="plannedCosts">
                      <span>{{dialogBaseInfoForm.plannedCosts}}</span>
                    </el-form-item>
                  </td>
                </tr>
                <tr>
                  <td>资金投向</td>
                  <td colspan="5">
                    <el-form-item prop="fundPurposeFirst" style="float: left">
                      <span>{{dialogBaseInfoForm.fundPurposeFirstName}}</span>
                    </el-form-item>
                    <div style="float: left" v-if="dialogBaseInfoForm.fundPurposeSecondtName">--</div>
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
              <span>注:表中内容请各单位认真填写，内容不完善，将不考虑列入</span>{{year}}<span>年计划中。设备现有状况填写时，每（台或个）设备填写一行</span>
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
      <!--点击项目弹窗end-->
    </div>

    <!-- 弹窗区域end -->
  </div>
</template>

<script>
import {planproject, enterprise, investmanage} from 'api/index'
import {formatDate} from 'common/js/date.js'
import {downloadFile} from 'common/js/common'
export default {
  data () {
    return {
      dialogRequestVisible: false, // 弹框-查看
      dialogVisible: false, // 弹框-表格单元格
      approveRecordList: [
        {applyName: '123'},
        {applyName: '321'}
      ], // 审核记录数据
      manageFormInfo: {
        fileInformationList: [],
        projectNameList: []
      }, // 弹窗表单对象
      // 操作自定义
      operationDisabled: false,
      manageTableData: [],
      // 弹框表格数据结束
      // 表格数据开始
      tableData: [],
      turnName: '',
      yearList: [],
      enterpriseList: [],
      purchaseTypeList: [
        {
          label: '自主采购',
          value: '1'
        },
        {
          label: '集中采购',
          value: '2'
        }
      ],
      ruleSearchForm: {}, // 搜索表单
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况
        engineerTableData: [] // 工程主要内容及备件主材
      }, // 弹窗-基本信息表单
      year: ''
    }
  },
  created () {
    this.getYearMethed()
    this.planManageData()
    this.getAllEnterpriseList()
  },
  filters: {
    /** 格式化时间 */
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    /** 申报表-计划工期 */
    filterTimeLimit (value) {
      let timeLimit = value
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
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
    /** 计划工期 */
    filterPlanLimit (value) {
      let planLimit = value
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
      }
    },
    /** 招标方式 */
    filterBidType (value) {
      let bidType = value
      if (bidType === 1) {
        return '公开招标'
      } else if (bidType === 2) {
        return '邀请招标'
      } else if (bidType === 3) {
        return '单一来源采购'
      } else if (bidType === 4) {
        return '竞争性磋商'
      } else if (bidType === 5) {
        return '竞价采购'
      }
    },
    /** 是否招标 */
    filterIsAgain (value) {
      let isAgain = value
      if (isAgain === 0) {
        return '否'
      } else if (isAgain === 1) {
        return '是'
      }
    },
    /** 项目类型 */
    filterProjectType (value) {
      let projectType = value
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    }
  },
  methods: {
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 计算年份-初始值为2017 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let currentYear = date.getFullYear() + 1
      for (let i = 2017; i <= currentYear; i++) {
        arr.push(i)
      }
      this.yearList = arr.reverse()
      return this.yearList
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.planManageData()
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.planManageData()
    },
    /** 格式化 */
    fliteredStatus (status) {
      // 审批状态
      // 0.发起审批申请 1.通过 2.退回 3.终止
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 0) {
        return 'wait'
      } else if (status === 3) {
        return 'finish'
      }
    },
    /** 申报表-格式化购置时间 */
    filterDates (row, column) {
      if (!row.purchaseTime) {
        return ''
      } else {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    /* /!** 获取所有得项目单位信息 *!/
    getAllEnterpriseList  () {
      enterprise.getAll({
        types: '1,2,3'
      }).then((res) => {
        if (res.data.enterprises.length > 0) {
          let info = []
          for (var i = 0; i < res.data.enterprises.length; i++) {
            var obj = {}
            obj.value = res.data.enterprises[i].objectId
            obj.label = res.data.enterprises[i].name
            info[i] = obj
          }
          this.enterpriseList = info
        }
      })
    }, */
    /* /!** 弹窗-表格添加一行 *!/
    addDialogTableRow (type) {
      if (Object.is(type, 'equip')) {
        let obj = {name: '123'}
        this.equipmentTableData.push(obj)
      } else if (Object.is(type, 'enginer')) {
        let obj = {name: '123'}
        this.engineerTableData.push(obj)
      }
    }, */
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'look') {
        // 查看-打开弹窗
        this.dialogRequestVisible = true
        this.planManageOneData(row)
      } else if (type === 'self') {
        row.purchaseType = 1
        row.executeEnterpriseId = row.requiresEnterpriseId
        this.trunClick(row)
        this.turnName = '自采'
      } else if (type === 'group') {
        this.turnName = '集采'
        row.purchaseType = 2
        row.executeEnterpriseId = this.$store.getters.authUser.enterpriseId
        this.trunClick(row)
      }
    },
    /** 下载附件文件 */
    downloadFile,
    /** 搜索 */
    onSearch () {
      this.planManageData()
    },
    /** 列表数据展示 */
    planManageData () {
      planproject.getList({
        PlanManage: true,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        projectType: 2,
        year: this.ruleSearchForm.year || null,
        purchaseType: this.ruleSearchForm.purchaseType || null,
        enterpriseLike: this.ruleSearchForm.enterpriseName,
        executeEnterpriseLike: this.ruleSearchForm.executeEnterpriseName
      }).then((res) => {
        this.tableData = res.data.projectPlanList.list
        this.total = res.data.projectPlanList.total
      })
    },
    /** 单条数据展示 */
    planManageOneData (row) {
      planproject.getOne(row.objectId).then((res) => {
        let data = res.data.projectPlan
        if (data) {
          this.manageFormInfo = data
          this.manageTableData = data.projectList
          this.manageFormInfo.fileInformationList = data.fileInformationList
        } else {
          return false
        }
      })
    },
    /** 转自采或集采 */
    trunClick (data) {
      planproject.update(data).then(() => {
        this.planManageData()
      })
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row, year) {
      if (row) {
        this.year = year
        this.getDeclarationForm(row.code)
      }
    },
    /** 根据项目code查询申报表信息 */
    getDeclarationForm (code) {
      let obj = {
        suggestedPlanCode: code
      }
      investmanage.getDeclarationForm(obj).then((res) => {
        if (res) {
          this.dialogVisible = true
          this.dialogBaseInfoForm = res.data.declarationForm
          this.dialogBaseInfoForm.equipmentTableData = this.dialogBaseInfoForm.equipmentActualityList
          this.dialogBaseInfoForm.engineerTableData = this.dialogBaseInfoForm.accessoriesList
        } else {
          this.$message.warning('此项目下无申报表信息！')
          this.dialogVisible = false
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
#goodsmanage-page {
  .planmanage-cont {
    .planmanage-cont-tit {
      padding-bottom: 5px;
    }
    .pre-dowm-l {
      color: #999;
      padding-right: 10px;
    }
    .el-table__body .el-table__row {
      td:nth-child(3) {
        div.cell {
          padding:0;
        }
        p {
          margin: 0;
          padding: 10px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
        .dresult-blank-span {
          height: 22px;
        }
        p:not(:last-child) {
          border-bottom: 1px solid #eee;
        }
      }
    }
  }
  /*搜索css*/
  .planmanage-search {
    padding: 20px;
    margin: 20px;
    background-color: #fff;
    .el-form-item {
      margin-left: -50px;
      margin-bottom: 0;
    }
  }
  /** 弹窗 */
  .planmanage-dialog {
    .base-info .table-form {
      width: 100%;
      display: inline-block;
      overflow: hidden;
      border-collapse: collapse;
      border: 1px solid #eee;
      text-align: center;
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      tr td:nth-child(odd) {
        width: 150px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        width: 320px;
        text-align: left;
        padding: 0 10px;
      }
    }
    .engineer-info, .equipment-info, .base-info {
      text-align: center;
      .el-form-item {
        margin-bottom: 0;
      }
    }
    .file-list {
      button span {
        padding-right: 20px;
      }
    }
  }
  /*审批记录css*/
  .submit-apply-flag {
    min-height: 200px;
    margin: 20px 20px 130px;
    padding-top: 20px;
    background: #fff;
    .step-span {
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      padding-right: 20px;
      box-sizing: border-box;
      display: inline-block;
      font-weight: normal;
      font-size: 14px;
    }
    .span-first {
      width: 100px;
    }
    .span-second {
      width: 160px;
    }
    .span-third {
      width: 160px;
    }
    .span-last {
      float: right;
    }
    .apply-option {
      span {
        font-size: 14px;
        color: #999;
      }
    }
    .span-color {
      color: #999;
    }
  }
  .el-form-item__content {
    text-align: left;
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
