<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="investlook-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/investmanage' }" v-if="checkDetails !=='approve'">投资建议计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/investcheck' }" v-if="checkDetails==='approve'">投资建议计划审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/investmanage/investlook' }">投资建议计划查看</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!--搜索区域start-->
    <div class="search-box investlook-search">
      <el-form :model="searchForm" :inline="true" class="demo-form-inline" style="text-align: left;">
        <el-form-item label="项目名称:">
          <el-input type="text" v-model="searchForm.messageLike"></el-input>
        </el-form-item>
        <el-form-item label="资金投向:">
          <el-select v-model="searchForm.fundPurposeFirst" clearable placeholder="请选择" @change="getSecondByFirstList">
            <el-option
              v-for="item in fundPurposeFirstList"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
          <span style="color: #ddd">--</span>
          <el-select v-model="searchForm.fundPurposeSecond" clearable placeholder="请选择">
            <el-option
              v-for="item in fundPurposeSecondList"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchFun()" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--搜索区域end-->
    <!--主体内容start-->
    <div class="maincont-box investlook-box">
      <div class="title-box">
        <span class="span-name span-l" v-if="this.$route.query.operate === 'manage'">资金来源：{{this.capitalSourceClassify.capitalSourceName ? this.capitalSourceClassify.capitalSourceName : this.capitalSourceName}}</span>
        <span class="span-name span-r" v-if="this.$route.query.operate === 'manage'">剩余额度：{{this.capitalSourceClassify.surplusUsageQuota}}万元</span>
        <span class="span-name span-r" v-if="this.$route.query.operate === 'manage'">投资总额：{{this.capitalSourceClassify.availableQuota}}万元</span>
        <span class="span-name span-r" v-if="this.$route.query.operate === 'approve'">剩余额度：{{(Number(this.$route.query.fixedQuota) - Number(this.$route.query.usedQuota)).toFixed(2)}}万元</span>
        <span class="span-name span-r" v-if="this.$route.query.operate === 'approve'">投资总额：{{this.$route.query.fixedQuota}}万元</span>
      </div>
      <div class="tree-table">
        <tree-grid
          border
          :columns="columns"
          :tree-structure="true"
          :data="queryTableData"
          @operate="tableBtnClick">
        </tree-grid>
      </div>
    </div>
    <!--主体内容end-->
    <!-- 审批记录start -->
    <div class="planapp-shenpi" v-if="this.$route.query.operate === 'manage'">
      <el-row :gutter="20">
        <el-col :span="2" style="text-align: left;">
          <span class="apply-name-color">审核历史:</span>
        </el-col>
        <el-col :span="21">
          <el-steps direction="vertical" :active="1" :space="100">
            <el-step
              v-for="(item,index) in classifyRecordList"
              :key="index"
              :status='fliteredStatus(item.recordType)'>
              <span slot="title" class="step-span span-color span-first">{{item.userName}}</span>
              <span slot="title" class="step-span span-color span-second">{{item.organization}}</span>
              <span slot="title" class="step-span span-third">{{item.recordType | filterRecordType}}</span>
              <span slot="title" class="step-span span-third">
                <el-button type="text" @click="querySingleData(item)">查询</el-button>
              </span>
              <span slot="title" class="step-span span-color span-last">{{item.createDate | filterDate}}</span>
              <div slot="title" class="apply-option" v-if="item.returnReason">
                <span>退回原因:{{item.returnReason}}</span>
              </div>
            </el-step>
          </el-steps>
        </el-col>
      </el-row>
    </div>
    <!-- 审批记录end -->

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
                  <el-form-item prop="capitalSourceName">
                    <span>{{dialogBaseInfoForm.capitalSourceName?dialogBaseInfoForm.capitalSourceName:this.capitalSourceName}}</span>
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
                  <el-form-item style="float: left" v-if="dialogBaseInfoForm.fundPurposeSecondtName">
                    <span>/</span>
                  </el-form-item>
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
                    <span>{{dialogBaseInfoForm.lastMaintenanceTime | filterTime}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>工程项目是否有方案</td>
                <td colspan="2">
                  <el-form-item prop="isPlan">
                    <span>{{dialogBaseInfoForm.isPlan | filterIsPlan}}</span>
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
                    <span>{{dialogBaseInfoForm.timeLimit | filterTimeLimit}}</span>
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
                <td>招标时间</td>
                <td colspan="2">
                  <el-form-item prop="executionTime">
                    <span>{{dialogBaseInfoForm.executionTime | filterTime}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td colspan="5">
                  <el-form-item prop="projectType">
                    <span>{{dialogBaseInfoForm.projectType | filterProjectType}}</span>
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
                :formatter="filterPurchaseTime"
                label="购置时间">
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
      <!--审核记录-查询start-->
      <el-dialog
        title="查询"
        :visible.sync="recordDialogVisible"
        width="60%"
        center>
        <div class="tree-table">
          <tree-grid
            border
            :columns="columns"
            :tree-structure="true"
            :data="queryHistoryTableData"
            @operate="tableBtnClick">
          </tree-grid>
        </div>
      </el-dialog>
      <!--审核记录-查询end-->

      <!-- 工程建设建议计划申报表start -->
      <el-dialog
        title="工程建设建议计划申报表"
        :visible.sync="enginerDialogViseble"
        width="80%"
        center>
        <el-form :model="dialogBaseInfoForm" ref="enginerRuleForm" label-width="140px" class="demo-ruleForm">
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
                  {{dialogBaseInfoForm.projectType  | filterProjectType}}
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
                <td colspan="2">
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
    <!--弹窗区域end-->
  </div>
</template>
<script>
import treeGrid from '@/components/treetable/TreeGridQuery.vue'
import {investmanage} from 'api/index'
import {formatDate} from 'common/js/date'
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
      checkDetails: this.$route.query.type, // 审核跳转过来
      searchForm: {},
      parentList: [],
      childList: [],
      classifyRecordList: [], // 审批记录
      columns: [
        {
          text: '投向',
          value: 'label',
          width: 300
        }
      ],
      queryTableData: [],
      queryHistoryTableData: [],
      dialogVisible: false,
      dialogBaseInfoForm: {}, // 弹窗表单
      recordDialogVisible: false, // 审核记录-查询-弹窗
      firstClassifyCode: '',
      capitalSourceClassify: {},
      fundPurposeFirstList: [], // 一级资金投向
      fundPurposeSecondList: [], // 二级资金投向
      capitalSourceName: '' // 工程建设项目资金来源
    }
  },
  computed: {
  },
  created () {
  },
  filters: {
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterDate (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return ''
      }
    },
    filterRecordType (val) {
      if (val === 1) {
        return '提交'
      } else if (val === 2) {
        return '修改维护'
      } else if (val === 3) {
        return '退回'
      }
    },
    filterProjectType (val) {
      if (val === 1) {
        return '工程'
      } else if (val === 2) {
        return '货物'
      } else if (val === 3) {
        return '服务'
      }
    },
    filterIsPlan (val) {
      if (val === 1) {
        return '是'
      } else if (val === 0) {
        return '否'
      }
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
    filterNature (val) {
      let constructiveNature = val
      if (constructiveNature === 1) {
        return '新建'
      } else if (constructiveNature === 2) {
        return '扩建'
      } else if (constructiveNature === 3) {
        return '改建'
      } else if (constructiveNature === 4) {
        return '迁建'
      } else if (constructiveNature === 5) {
        return '恢复重建'
      } else if (constructiveNature === 6) {
        return '技改'
      }
    },
    filterTimeLimit (val) {
      let timeLimit = val
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
      }
    }
  },
  methods: {
    tableBtnClick (row) {
      this.getDeclarationForm(row.suggestedPlan.code)
      if (row.classify === 1) {
        // 专项资金
        this.dialogVisible = true
      } else {
        // 工程建设
        this.enginerDialogViseble = true
      }
    },
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'wait'
      } else if (status === 3) {
        return 'error'
      }
    },
    filterPurchaseTime (row, column) {
      if (row.purchaseTime) {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    searchFun () {
      this.getElementTree(this.$route.query.code, 1)
    },
    /** 查询建议计划列表 */
    getElementTree (capitalSourceClassifyCode, operate) {
      let query = {
        messageLike: this.searchForm.messageLike || null,
        fundPurposeFirst: this.searchForm.fundPurposeFirst || null,
        fundPurposeSecond: this.searchForm.fundPurposeSecond || null,
        isInRecycleBin: 0
      }
      if (Object.is(this.$route.query.operate, 'approve')) {
        query.investmentProposalSchemeCode = this.$route.query.code
      } else if (Object.is(this.$route.query.operate, 'manage')) {
        query.capitalSourceClassifyCode = capitalSourceClassifyCode
      }
      investmanage.selectElementTree(query).then((res) => {
        let tempData = res.data.suggestedPlanList
        if (tempData) {
          if (operate === 1) {
            this.queryTableData = this.transTreeData(tempData)
          } else if (operate === 2) {
            this.queryHistoryTableData = this.transTreeData(tempData)
          }
        }
      })
    },
    /**
     * 方法 transTreeData
     * param [原始data]
     * return [treeData]
     **/
    transTreeData (data) {
      let treeDate = []
      let orderNumber = 1
      if (data.length) {
        data.map((item) => {
          item.orderNumber = orderNumber++
          // 二级以下
          if (item.children) {
            this.transTreeData(item.children)
          } else {
            Object.assign(item, item.suggestedPlan)
            return item
          }
        })
      }
      treeDate = data
      return treeDate
    },
    /** 查询申报表 */
    getDeclarationForm (code) {
      let query = {
        suggestedPlanCode: code
      }
      investmanage.getDeclarationForm(query).then((res) => {
        if (res.data.declarationForm) {
          this.dialogBaseInfoForm = res.data.declarationForm
          if (!this.dialogBaseInfoForm.fundPurposeSecond) {
            this.dialogBaseInfoForm.fundPurposeSecond = ''
          }
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
          this.getFundPurposeSecondList(this.dialogBaseInfoForm.fundPurposeFirst)
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
    /** 查询审核记录 */
    getRecordList () {
      let query = {
        code: this.$route.query.code
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
        return this.capitalSourceClassify
      }).then(capitalSourceClassify => {
        let query = {
          investmentProposalSchemeCode: capitalSourceClassify.investmentProposalSchemeCode,
          capitalSourceId: capitalSourceClassify.capitalSourceId,
          isDelete: 0
        }
        investmanage.getFirstClassifyCode(query).then(res => {
          return res.data.firstClassifyCode
        }).then(firstClassifyCode => {
          let query = {
            relatedFirstClassifyCode: firstClassifyCode
          }
          investmanage.getRecordList(query).then((res) => {
            this.classifyRecordList = res.data.sourceClassifyRecordList
          })
        })
      })
    },
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeFirstList = res.data.fundPurposeList
      })
    },
    /** 查询资金投向 - 二级 */
    getFundPurposeSecondList (fundPurposeFirst) {
      let query = {
        level: 2,
        parentId: fundPurposeFirst
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = res.data.fundPurposeList
      })
    },
    /** 查询资金投向 - 二级 */
    getSecondByFirstList () {
      if (this.searchForm.fundPurposeSecond) {
        this.searchForm.fundPurposeSecond = ''
      }
      let query = {
        level: 2,
        parentId: this.searchForm.fundPurposeFirst
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = res.data.fundPurposeList
      })
    },
    /** 审核历史-查询按钮 */
    querySingleData (item) {
      this.getElementTree(item.relatedClassifyCode, 2)
      this.recordDialogVisible = true
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
        this.capitalSourceName = this.capitalSourceName.substring(0, this.capitalSourceName.length-1)
      })
    }
  },
  // 实例创建完成
  mounted () {
    this.getElementTree(this.$route.query.code, 1)
    if (Object.is(this.$route.query.operate, 'manage')) {
      this.getRecordList()
    }
    this.getFundPurposeFirstList()
    this.getCapitalSource()
  },
  components: {
    treeGrid
  }
}
</script>
<style lang="less">
#investlook-page {
  .investlook-search {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .investlook-box {
    margin: 20px;
    margin-bottom: 130px;
  }
  .title-box {
    padding-bottom: 10px;
    overflow: hidden;
    .span-name {

    }
    .span-l {
      float: left;
    }
    .span-r {
      float: right;
      padding-left: 30px;
    }
  }
  /*审批记录css*/
  .planapp-shenpi {
    margin: 20px;
    margin-bottom: 130px;
    margin-top: -110px;
    height: 300px;
    overflow: auto;
    padding: 20px;
    background: #fff;
    .step-span {
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
    .span-last {
      float: right;
    }
    .apply-option {
      span {
        font-size: 14px;
        color: #999;
      }
    }
    .span-third {
      width: 180px;
    }
    .span-color {
      color: #999;
    }
  }
  /** 弹窗-表格样式 */
  .dialog-box {
    .base-info .table-form {
      width: 100%;
      overflow: hidden;
      display: inline-block;
      border-collapse: collapse;
      border: 1px solid #eee;
      text-align: center;
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
        margin-bottom: 0;
      }
    }
  }
}
</style>
