<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="query-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/planquery' }">计划备案查询</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/planquery/details' }">详情页</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box query-cont">
      <div class="query-cont-tit">
        <h3>{{this.$route.query.year}}年固定资产投资建议采购计划</h3>
        <el-row :gutter="20">
          <el-col :span="12" style="text-align: left;">
            <span class="pre-dowm-l">单位名称:</span><span>{{this.$route.query.enterpriseName}}</span>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <span class="pre-dowm-l">当前共有</span>{{this.total}}<span class="pre-dowm-l">个项目</span>
          </el-col>
        </el-row>
      </div>
      <!-- 表格区域start -->
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        :row-class-name="tableRowClassName">
        <el-table-column
          type="index"
          label="序号"
          align="center"
          :index="indexMethod"
          width="55">
        </el-table-column>
        <el-table-column
          prop="projectPlanName"
          label="招采项目"
          header-align="center"
          align="left">
        </el-table-column>
        <el-table-column
          prop="projectName"
          label="项目名称"
          header-align="center"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.projectName" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.projectName">
              <p class="dresult-span">
                <el-button type="text" size="mini" @click="clickCellCloumn(item)" style="line-height: 0;overflow: hidden;">{{item.projectName}}</el-button>
              </p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="content"
          label="项目主要内容"
          header-align="center"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.content" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.content">
              <p class="dresult-span">{{item.content}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="currentPlanQuantities"
          label="工程量"
          header-align="center"
          align="left">
          <template slot-scope="scope" v-if="scope.row.projectList">
            <el-tooltip effect="dark" :content="item.currentPlanQuantities?item.currentPlanQuantities:item.budgetQuantities" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.currentPlanQuantities">
              <p class="dresult-span" >{{item.currentPlanQuantities}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else>{{item.budgetQuantities}}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="capitalSourceName"
          label="资金来源"
          header-align="center"
          align="left">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.capitalSourceName" placement="top" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.capitalSourceName">
              <p class="dresult-span">{{item.capitalSourceName}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="fundPurpose"
          label="资金投向">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.fundPurpose" placement="top" v-for="(item,index) in scope.row.fundPurpose" :key="index" v-if="item.fundPurpose">
              <p class="dresult-span">{{item.fundPurpose}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="planLimit"
          label="计划工期"
          header-align="center"
          align="left"
          :formatter="filterPlanLimit">
        </el-table-column>
        <el-table-column
          prop="planTotalAmount"
          label="计划总金额(万元)"
          align="right">
        </el-table-column>
        <el-table-column
          prop="executeTime"
          label="拟招标时间"
          align="center">
        </el-table-column>
        <el-table-column
          prop="executeTime"
          label="拟实施时间"
          align="center">
        </el-table-column>
        <el-table-column
          prop="bidType"
          label="拟招标方式"
          align="center"
          :formatter="filterBidType">
        </el-table-column>
        <el-table-column
          prop="projectType"
          label="项目类型"
          align="center"
          :formatter="filterProjectType">
        </el-table-column>
        <el-table-column
          prop="enterpriseName"
          label="需求方"
          align="center">
        </el-table-column>
      </el-table>
      <!-- 表格区域end -->
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
    </div>
    <!--弹窗start-->
    <div class="dialog-box">
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
                    <span v-if="dialogBaseInfoForm.capitalSourceName">{{dialogBaseInfoForm.capitalSourceName}}</span>
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
                    <span>{{dialogBaseInfoForm.lastMaintenanceTime |filterDate}}</span>
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
                    <span>{{dialogBaseInfoForm.procurementMethod | filterProcurementMethod}}</span>
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
                    <span>{{dialogBaseInfoForm.executionTime | filterDate}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td colspan="5">
                  <el-form-item prop="projectType">
                    <span>{{dialogBaseInfoForm.projectType | filteredProjectType}}</span>
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
      <!-- 工程建设建议计划申报表start -->
      <el-dialog
        title="工程建设建议计划申报表"
        :visible.sync="enginerDialogViseble"
        width="80%"
        center>
        <el-form :model="dialogBaseInfoForm" ref="enginerRuleForm" class="demo-ruleForm">
          <div class="base-info enginer">
            <table class="table-form">
              <tr>
                <td>项目名称</td>
                <td colspan="4">
                  <span>{{dialogBaseInfoForm.projectName}}</span>
                </td>
              </tr>
              <tr>
                <td>资金来源</td>
                <td colspan="2">
                  <span>{{dialogBaseInfoForm.capitalSourceName?dialogBaseInfoForm.capitalSourceName:this.capitalSourceName}}</span>
                </td>
                <td>计划费用（万元）</td>
                <td>{{dialogBaseInfoForm.plannedCosts}}</td>
              </tr>
              <tr>
                <td>资金投向</td>
                <td colspan="2">
                  <span>{{dialogBaseInfoForm.fundPurposeFirstName}}</span>
                  <span v-if="dialogBaseInfoForm.fundPurposeSecondtName">/</span>
                  <span>{{dialogBaseInfoForm.fundPurposeSecondtName}}</span>
                </td>
                <td>工程量</td>
                <td>{{dialogBaseInfoForm.quantities}}</td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td colspan="2">
                  {{dialogBaseInfoForm.projectType  | filteredProjectType}}
                </td>
                <td>采购方式</td>
                <td colspan="2">
                  {{dialogBaseInfoForm.procurementMethod  | filterProcurementMethod}}
                </td>
              </tr>
              <tr>
                <td>投资主体</td>
                <td colspan="2">
                  {{dialogBaseInfoForm.investor}}
                </td>
                <td>建设性质</td>
                <td>
                  {{dialogBaseInfoForm.constructiveNature | filterNature}}
                </td>
              </tr>
              <tr>
                <td>建设规模</td>
                <td  colspan="2">
                  {{dialogBaseInfoForm.constructiveScale}}
                </td>
                <td>建设起止时间</td>
                <td>
                  {{dialogBaseInfoForm.constructiveStartTime | filterTime}}至{{dialogBaseInfoForm.constructiveEndTime | filterTime}}
                </td>
              </tr>
              <tr>
                <td>建设地点</td>
                <td colspan="4">
                  {{dialogBaseInfoForm.area}}{{dialogBaseInfoForm.address}}
                </td>
              </tr>
              <tr>
                <td>投资构成</td>
                <td style="text-align:center;background:#F6F7F9;">概算(估算)总投资</td>
                <td>{{this.$route.query.year - 1}}年累计完成</td>
                <td style="text-align:center;background:#F6F7F9;">{{this.$route.query.year - 1}}年预计累计完成</td>
                <td>{{this.$route.query.year}}年投资建议计划</td>
              </tr>
              <tr>
                <td>小计</td>
                <td>{{totalForm.budgetInvestment}}</td>
                <td style="background:#fff;text-align:left;padding-left:10px;">{{totalForm.cumulativeComplete}}</td>
                <td>{{totalForm.expectedCumulativeComplete}}</td>
                <td style="background:#fff;text-align:left;padding-left:10px;">{{totalForm.investmentProposalPlan}}</td>
              </tr>
              <tr>
                <td>矿建工程</td>
                <td>
                  {{firstMineForm.budgetInvestment}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{firstMineForm.cumulativeComplete}}
                </td>
                <td>
                  {{firstMineForm.expectedCumulativeComplete}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{firstMineForm.investmentProposalPlan}}
                </td>
              </tr>
              <tr>
                <td>土建工程</td>
                <td>
                  {{secondCivilForm.budgetInvestment}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{secondCivilForm.cumulativeComplete}}
                </td>
                <td>
                  {{secondCivilForm.expectedCumulativeComplete}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{secondCivilForm.investmentProposalPlan}}
                </td>
              </tr>
              <tr>
                <td>安装工程</td>
                <td>
                  {{thirdInstallForm.budgetInvestment}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{thirdInstallForm.cumulativeComplete}}
                </td>
                <td>
                  {{thirdInstallForm.expectedCumulativeComplete}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{thirdInstallForm.investmentProposalPlan}}
                </td>
              </tr>
              <tr>
                <td>设备购置</td>
                <td>
                  {{fourthEquipForm.budgetInvestment}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{fourthEquipForm.cumulativeComplete}}
                </td>
                <td>
                  {{fourthEquipForm.expectedCumulativeComplete}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{fourthEquipForm.investmentProposalPlan}}
                </td>
              </tr>
              <tr>
                <td>其他费用</td>
                <td>
                  {{fifthOtherForm.budgetInvestment}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{fifthOtherForm.cumulativeComplete}}
                </td>
                <td>
                  {{fifthOtherForm.expectedCumulativeComplete}}
                </td>
                <td style="background:#fff;text-align:left;padding-left:10px;">
                  {{fifthOtherForm.investmentProposalPlan}}
                </td>
              </tr>
              <tr>
                <td>{{this.$route.query.year}}年项目审批目标</td>
                <td colspan="4">
                  {{dialogBaseInfoForm.approveTarget}}
                </td>
              </tr>
              <tr>
                <td>{{this.$route.query.year}}年项目建设目标</td>
                <td colspan="4">
                  {{dialogBaseInfoForm.constructiveTarget}}
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="4">
                  {{dialogBaseInfoForm.remark}}
                </td>
              </tr>
            </table>
          </div>
        </el-form>
      </el-dialog>
      <!-- 工程建设建议计划申报表end -->
    </div>
    <!--弹窗end-->
  </div>
</template>
<script>
import {formatDate} from 'common/js/date.js'
import {planproject, investmanage} from 'api/index'
import {CityInfo} from 'common/js/region'
// 实例
export default {
  data () {
    return {
      enginerDialogViseble: false,
      totalForm: {}, // 小计
      firstMineForm: {},
      secondCivilForm: {},
      thirdInstallForm: {},
      fourthEquipForm: {},
      fifthOtherForm: {},
      yearValue: '',
      tableData: [],
      currentPage: 1, // 前台页面展示当前页
      total: 0,
      pageNo: 0, // 后台需要提交的参数
      pageSize: 10,
      pageSizeList: [10, 20, 30, 40, 50],
      dialogVisible: false,
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况
        engineerTableData: [] // 工程主要内容及备件主材
      }, // 弹窗表单
      capitalSourceName: ''
    }
  },
  created () {
    this.getCapitalSource()
  },
  filters: {
    filterNature (val) {
      let status = val
      if (!status) {
        return ''
      }
      let mapData = new Map([
        [1, ['新建']],
        [2, ['扩建']],
        [3, ['改建']],
        [4, ['迁建']],
        [5, ['恢复重建']],
        [6, ['技改']]
      ])
      const getStatus = status => {
        let arrStatus = mapData.get(status)
        return arrStatus[0]
      }
      return getStatus(status)
    },
    filterProcurementMethod (val) {
      let procurementMethod = val
      if (procurementMethod === '1') {
        return '公开招标'
      } else if (procurementMethod === '2') {
        return '邀请招标'
      } else if (procurementMethod === '3') {
        return '谈判采购'
      } else if (procurementMethod === '4') {
        return '询比采购'
      } else if (procurementMethod === '5') {
        return '竞价采购'
      } else if (procurementMethod === '6') {
        return '直接采购'
      } else if (procurementMethod === '7') {
        return '框架协议'
      }
    },
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    /** 格式化项目类型 */
    filteredProjectType (projectType) {
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    },
    /** 格式化时间 */
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    /** 计划工期 */
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
    }
  },
  methods: {
    /** 行颜色 */
    tableRowClassName ({row, rowIndex}) {
      if (row.capitalSource === 5 && row.projectPlanType === 2) {
        return 'warning-row'
      }
    },
    filterBidType (row, column) {
      let bid = row.bidType
      if (bid === 1) {
        return '公开招标'
      } else if (bid === 2) {
        return '邀请招标'
      } else if (bid === 3) {
        return '谈判采购'
      } else if (bid === 4) {
        return '询比采购'
      } else if (bid === 5) {
        return '竞价采购'
      } else if (bid === 6) {
        return '直接采购'
      } else if (bid === 7) {
        return '框架协议'
      }
    },
    filterProjectType (row, column) {
      let project = row.projectType
      if (project === 1) {
        return '工程'
      } else if (project === 2) {
        return '货物'
      } else if (project === 3) {
        return '服务'
      }
    },
    filterIsAgain (row, column) {
      let is = row.isAgain
      if (is === 0) {
        return '否'
      } else if (is === 1) {
        return '是'
      }
    },
    /** 计划工期 */
    filterPlanLimit (row, column) {
      let planLimit = row.planLimit
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
      }
    },
    /** 格式化 */
    filterDates (row, column) {
      if (!row.purchaseTime) {
        return ''
      } else {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    /** 进行查看等操作后返回当前页 */
    indexMethod (index) {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.$route.query.currentPage = ''
      }
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 表单分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.planProjectData()
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.planProjectData()
    },
    /** 列表数据展示 */
    planProjectData () {
      planproject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        annualProjectPlanCode: this.$route.query.code,
        status: 3
      }).then((res) => {
        let arr = res.data.projectPlanList.list
        if (arr.length > 0) {
          arr.map((item, index) => {
            let date1 = new Date(item.bidStartTime)
            let date2 = new Date(item.bidEndTime)
            let date3 = new Date(item.executeStartTime)
            let date4 = new Date(item.executeEndTime)
            arr[index].bidTime = `${formatDate(date1, 'yyyy-MM-dd')}至${formatDate(date2, 'yyyy-MM-dd')}`
            arr[index].executeTime = `${formatDate(date3, 'yyyy-MM-dd')}至${formatDate(date4, 'yyyy-MM-dd')}`
            let childArr = item.projectList.map((child) => {
              let str = ''
              if (child.declarationForm && child.declarationForm.hasOwnProperty('fundPurposeFirstName') && child.declarationForm.fundPurposeFirstName) {
                str += child.declarationForm.fundPurposeFirstName
              }
              if (child.declarationForm && child.declarationForm.hasOwnProperty('fundPurposeSecondtName') && child.declarationForm.fundPurposeSecondtName) {
                str += child.declarationForm.fundPurposeSecondtName
              }
              child.fundPurpose = str
              if (child.capitalSource === 0) {
                child.capitalSourceName = this.capitalSourceName
              }
              return child
            })
            item.fundPurpose = childArr
            return item
          })
        }
        this.tableData = arr
        this.total = res.data.projectPlanList.total
      })
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row) {
      if (row.declarationForm) {
        this.getDeclarationForm(row.code)
        if (row.classify === 1) {
        // 专项资金
          this.dialogVisible = true
        } else {
          // 工程建设
          this.enginerDialogViseble = true
        }
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
          this.dialogBaseInfoForm.area = ''
          let provinceId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.provinceId)
          let cityId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.cityId)
          let countyId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.countyId)
          if (provinceId.node) {
            this.dialogBaseInfoForm.area += `${provinceId.node.label}`
          }
          if (cityId.node) {
            this.dialogBaseInfoForm.area += `${cityId.node.label}`
          }
          if (countyId.node) {
            this.dialogBaseInfoForm.area += `${countyId.node.label}`
          }
          let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
          this.firstMineForm = {}
          this.secondCivilForm = {}
          this.thirdInstallForm = {}
          this.fourthEquipForm = {}
          this.fifthOtherForm = {}
          investmentCompositionList.map(item => {
            if (item.type === 1) {
              // 矿建工程
              this.firstMineForm = item
            } else if (item.type === 2) {
              // 土建工程
              this.secondCivilForm = item
            } else if (item.type === 3) {
              // 安装工程
              this.thirdInstallForm = item
            } else if (item.type === 4) {
              // 设备购置
              this.fourthEquipForm = item
            } else if (item.type === 5) {
              // 其他费用
              this.fifthOtherForm = item
            }
          })
          this.calculateTotal()
        } else {
          return false
        }
      })
    },
    /** treeData [原始数据集]， key 传入的key值 */
    getLabelName (treeData, key) {
      let parentNode = null
      let node = null
      function getTreeDeepArr (treeData, key) {
        for (let i = 0; i < treeData.length; i++) {
          // 1.如果没有子节点 beark
          if (node) {
            break
          }
          // 定义需要操作的数据
          let obj = treeData[i]
          // 没有节点就下一个
          if (!obj || !obj.value) {
            continue
          }
          // 2.有节点就继续找，一直递归
          if (obj.value === key) {
            node = obj
            break
          } else {
            // 3.如果有子节点就继续找
            if (obj.children) {
              // 4.递归前，记录当前节点，作为parentNode
              parentNode = obj
              // 递归
              getTreeDeepArr(obj.children, key)
            } else {
              // 跳出当前递归
              continue
            }
          }
        }
        // 如果没有找到父节点，置为null
        if (!node) {
          parentNode = null
        }
        // 返回结果
        return {
          node: node,
          parentNode: parentNode
        }
      }
      return getTreeDeepArr(treeData, key)
    },
    calculateTotal () {
      this.totalForm = {}
      this.totalForm.budgetInvestment = ''
      this.totalForm.cumulativeComplete = ''
      this.totalForm.expectedCumulativeComplete = ''
      this.totalForm.investmentProposalPlan = ''
      if (this.firstMineForm.budgetInvestment) {
        this.totalForm.budgetInvestment = Number(this.firstMineForm.budgetInvestment)
      }
      if (this.secondCivilForm.budgetInvestment) {
        this.totalForm.budgetInvestment = Number(this.totalForm.budgetInvestment) + Number(this.secondCivilForm.budgetInvestment)
      }
      if (this.thirdInstallForm.budgetInvestment) {
        this.totalForm.budgetInvestment = Number(this.totalForm.budgetInvestment) + Number(this.thirdInstallForm.budgetInvestment)
      }
      if (this.fourthEquipForm.budgetInvestment) {
        this.totalForm.budgetInvestment = Number(this.totalForm.budgetInvestment) + Number(this.fourthEquipForm.budgetInvestment)
      }
      if (this.fifthOtherForm.budgetInvestment) {
        this.totalForm.budgetInvestment = Number(this.totalForm.budgetInvestment) + Number(this.fifthOtherForm.budgetInvestment)
      }
      if (this.totalForm.budgetInvestment) {
        this.totalForm.budgetInvestment = this.totalForm.budgetInvestment.toFixed(2)
      }
      if (this.firstMineForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = Number(this.firstMineForm.cumulativeComplete)
      }
      if (this.secondCivilForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = Number(this.totalForm.cumulativeComplete) + Number(this.secondCivilForm.cumulativeComplete)
      }
      if (this.thirdInstallForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = Number(this.totalForm.cumulativeComplete) + Number(this.thirdInstallForm.cumulativeComplete)
      }
      if (this.fourthEquipForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = Number(this.totalForm.cumulativeComplete) + Number(this.fourthEquipForm.cumulativeComplete)
      }
      if (this.fifthOtherForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = Number(this.totalForm.cumulativeComplete) + Number(this.fifthOtherForm.cumulativeComplete)
      }
      if (this.totalForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = this.totalForm.cumulativeComplete.toFixed(2)
      }
      if (this.firstMineForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = Number(this.firstMineForm.expectedCumulativeComplete)
      }
      if (this.secondCivilForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = Number(this.totalForm.expectedCumulativeComplete) + Number(this.secondCivilForm.expectedCumulativeComplete)
      }
      if (this.thirdInstallForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = Number(this.totalForm.expectedCumulativeComplete) + Number(this.thirdInstallForm.expectedCumulativeComplete)
      }
      if (this.fourthEquipForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = Number(this.totalForm.expectedCumulativeComplete) + Number(this.fourthEquipForm.expectedCumulativeComplete)
      }
      if (this.fifthOtherForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = Number(this.totalForm.expectedCumulativeComplete) + Number(this.fifthOtherForm.expectedCumulativeComplete)
      }
      if (this.totalForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = this.totalForm.expectedCumulativeComplete.toFixed(2)
      }
      if (this.firstMineForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = Number(this.firstMineForm.investmentProposalPlan)
      }
      if (this.secondCivilForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = Number(this.totalForm.investmentProposalPlan) + Number(this.secondCivilForm.investmentProposalPlan)
      }
      if (this.thirdInstallForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = Number(this.totalForm.investmentProposalPlan) + Number(this.thirdInstallForm.investmentProposalPlan)
      }
      if (this.fourthEquipForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = Number(this.totalForm.investmentProposalPlan) + Number(this.fourthEquipForm.investmentProposalPlan)
      }
      if (this.fifthOtherForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = Number(this.totalForm.investmentProposalPlan) + Number(this.fifthOtherForm.investmentProposalPlan)
      }
      if (this.totalForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = this.totalForm.investmentProposalPlan.toFixed(2)
      }
    },
    /** 查询工程建设项目资金来源 */
    getCapitalSource () {
      this.capitalSourceName = ''
      let query = {
        classify: 2,
        isAvailableFunds: 1
      }
      investmanage.getCapitalSourceList(query).then(res => {
        let capitalSourceList = res.data.capitalSourceList
        capitalSourceList.map(capitalSource => {
          this.capitalSourceName += capitalSource.name + '、'
        })
        this.capitalSourceName = this.capitalSourceName.substring(0, this.capitalSourceName.length - 1)
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.planProjectData()
  }
}
</script>
<style lang="less">
#query-details-page {
  /*表格区域css*/
  .query-cont {
    .query-cont-tit {
      padding-bottom: 5px;
    }
    .pre-dowm-l {
      color: #999;
      padding-right: 10px;
    }
    /** 表格红色行 */
    .warning-row {
      color: red;
    }
    .el-table__body .el-table__row {
      td:nth-child(3),
      td:nth-child(4),
      td:nth-child(5),
      td:nth-child(6),
      td:nth-child(7){
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
    .el-table--scrollable-x .el-table__body-wrapper {
      overflow-x: hidden;
    }
  }
  /** 弹窗-表格样式 */
  .dialog-box {
    .base-info .table-form {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      text-align: center;
      overflow: hidden;
      display: inline-block;
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
    .engineer-info, .equipment-info, .base-info {
      text-align: center;
      .el-form-item {
        margin-left: 0;
        margin-bottom: 0;
      }
    }
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
