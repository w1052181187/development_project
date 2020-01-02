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
        <el-tab-pane label="审核建议计划" name="first">
          <component ref="mychild" :is="currentView" @cardToMain="cardToMain" :classifyCode="classifyCode" @editToMain="editToMain" @childToParent="childToParent">
            <!-- 组件在 vm.currentview 变化时改变！ -->
          </component>
        </el-tab-pane>
        <el-tab-pane label="查询建议计划" name="second">
          <!--搜索区域start-->
          <div class="balance-search">
            <el-form :model="searchQueryForm" :inline="true" class="demo-form-inline" style="text-align: left;">
              <el-form-item label="资金来源:">
                <el-select v-model="searchQueryForm.capitalSource" clearable placeholder="请选择资金来源">
                  <el-option
                    v-for="item in capitalSourceList"
                    :key="item.objectId"
                    :label="item.name"
                    :value="item.objectId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="searchPlanFun" icon="el-icon-search">搜索</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!--搜索区域end-->
          <div class="tree-table">
            <tree-grid-query
              border
              :columns="columns"
              :tree-structure="true"
              :data="queryPlanTableData"
              @operate="tableBtnClick">
            </tree-grid-query>
          </div>
        </el-tab-pane>
        <el-tab-pane label="项目储备库" name="third">
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
                <el-button type="primary" @click="searchRecycleFun" icon="el-icon-search">搜索</el-button>
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
                  {{scope.row.capitalSourceName?dialogBaseInfoForm.capitalSourceName:capitalSourceName}}
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
                  <el-button @click="handleTableClick(scope.row,'remove')" type="text" size="medium">移除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        <el-tab-pane label="回收站" name="fourth">
          <!--搜索区域start-->
          <div class="balance-search">
            <el-form :model="searchReserveForm" :inline="true" class="demo-form-inline" style="text-align: left;">
              <el-form-item label="项目名称:">
                <el-input v-model="searchReserveForm.messageLike" placeholder="请填写项目名称" clearable>
                </el-input>
              </el-form-item>
              <el-form-item label="资金来源:">
                <el-select v-model="searchReserveForm.capitalSource" clearable placeholder="请选择资金来源">
                  <el-option
                    v-for="item in capitalSourceList"
                    :key="item.objectId"
                    :label="item.name"
                    :value="item.objectId">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="searchReserveFun" icon="el-icon-search">搜索</el-button>
              </el-form-item>
            </el-form>
          </div>
          <!--搜索区域end-->
          <div class="table">
            <el-table
              :data="reserveTableData"
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
                  {{scope.row.capitalSourceName?dialogBaseInfoForm.capitalSourceName:capitalSourceName}}
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
                  <el-button @click="handleTableClick(scope.row,'back')" type="text" size="medium">恢复</el-button>
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
                    <span>{{dialogBaseInfoForm.capitalSourceName?dialogBaseInfoForm.capitalSourceName:capitalSourceName}}</span>
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
                :formatter="filterPurchaseTime"
                prop="purchaseTime"
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
      <el-dialog
        title="请选择恢复目的地"
        :visible.sync="recallDialogVisible"
        width="30%"
        center>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="recallBtnClick('recycle')">项目储备库</el-button>
          <el-button type="primary" @click="recallBtnClick('pro')">投资计划</el-button>
        </span>
      </el-dialog>
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
                <td>
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
    <!--弹窗区域end-->
  </div>
</template>
<script>
import treeGridQuery from '@/components/treetable/TreeGridQuery.vue'
import {investmanage} from 'api/index'
import {formatDate} from 'common/js/date'
import {CityInfo} from 'common/js/region'
// 实例
export default {
  data () {
    return {
      recallDialogVisible: false,
      searchReserveForm: {}, // 回收站-搜索-表单
      reserveTableData: [], // 回收站-表格
      currentView: 'checkCard',
      activeCode: 'first',
      searchQueryForm: {}, // 查询建议计划-查询表单
      capitalSourceList: [], // 资金来源
      columns: [
        {
          text: '投向',
          value: 'label',
          width: 300
        }
      ],
      queryPlanTableData: [], // 树数据
      searchRecycleForm: {}, // 储备库搜索
      recycleTableData: [],
      dialogVisible: false, // 申报表弹窗
      dialogBaseInfoForm: {
        equipmentTableData: [{}], // 设备现有状况-表格
        engineerTableData: [{}] // 工程主要内容-表格
      },
      classifyCode: '', // // 资源管理分类编号
      capitalSourceName: '',
      upLoadData: {},
      recycleFlag: null,
      tabsNewValue: null,
      currentPage: 1,
      operate: 'recycle',
      reserveRow: {}, // 回收站行数据
      enginerDialogViseble: false,
      totalForm: {}, // 小计
      firstMineForm: {},
      secondCivilForm: {},
      thirdInstallForm: {},
      fourthEquipForm: {},
      fifthOtherForm: {}
    }
  },
  computed: {
  },
  watch: {
    tabsNewValue: function (newV, oldV) {
      this.classifyCode = newV
    }
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
    filterTimeLimit (val) {
      let timeLimit = val
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
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
    }
  },
  methods: {
    /** 恢复-事件 */
    recallBtnClick (type) {
      if (Object.is(type, 'pro')) {
        this.reserveRow.isInRecycleBin = 0
      } else if (Object.is(type, 'recycle')) {
        this.reserveRow.isInRecycleBin = 1
      }
      investmanage.saveSuggestedPlan(this.reserveRow).then(res => {
        this.operate = 'reserve'
        this.getSuggestedPlanList()
        this.recallDialogVisible = false
      })
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    filterDate (row, column) {
      if (row.lastUpdateDate) {
        let date = new Date(row.lastUpdateDate)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
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
    childToParent (val) {
      this.currentView = val
    },
    /** 进行查看操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** tabs切换 */
    handleTabsClick (tab, event) {
      if (tab.name === 'first') {
        if (Object.is(this.currentView, 'checkEdit')) {
          this.$refs.mychild.getSuggestedPlanList()
        }
      } else if (tab.name === 'second') {
        this.getElementTree()
        this.getCapitalSourceList()
      } else if (tab.name === 'third') {
        this.operate = 'recycle'
        this.getSuggestedPlanList()
        this.getCapitalSourceList()
      } else if (tab.name === 'fourth') {
        this.operate = 'reserve'
        this.getSuggestedPlanList()
        this.getCapitalSourceList()
      }
    },
    /** 查询建议计划-搜索 */
    searchPlanFun () {
      this.getElementTree()
    },
    /** 项目储备库-搜索 */
    searchRecycleFun () {
      this.operate = 'recycle'
      this.getSuggestedPlanList()
    },
    /** 回收站-搜索 */
    searchReserveFun () {
      this.operate = 'reserve'
      this.getSuggestedPlanList()
    },
    /** 恢复 */
    handleTableClick (row, type) {
      if (Object.is(type, 'recall')) {
        // 恢复
        this.recoveryClick(row)
      } else if (Object.is(type, 'remove')) {
        // 移除
        this.removeClick(row)
      } else if (Object.is(type, 'back')) {
        this.reserveRow = row
        this.recallDialogVisible = true
      }
    },
    // 恢复-询问
    recoveryClick (row) {
      this.$confirm('确定恢复此数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行方法
        this.recoveryData(row, 'recovery')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    // 移除-询问
    removeClick (row) {
      this.$confirm('确定移除此数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行方法
        this.recoveryData(row, 'remove')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    recoveryData (row, operate) {
      let query = {
        code: row.capitalSourceClassifyCode
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
        this.classifyCode = res.data.capitalSourceClassify.code
        this.getList(row, operate)
      })
    },
    /***/
    getList (row, operate) {
      this.suggestedPlanList = []
      let query2 = {
        capitalSourceClassifyCode: row.capitalSourceClassifyCode,
        isInRecycleBin: 0
      }
      investmanage.getSuggestedPlanList(query2).then(res => {
        this.suggestedPlanList = res.data.suggestedPlanList
        let query = {
          investmentProposalSchemeCode: this.$route.query.code,
          capitalSourceClassifyCode: row.capitalSourceClassifyCode,
          isDelete: 0
        }
        // 如果现在操作的是项目储备库，则查询回收站，否则相反
        if (Object.is(this.operate, 'recycle')) {
          query.isInRecycleBin = 2
        } else if (Object.is(this.operate, 'reserve')) {
          query.isInRecycleBin = 1
        }
        investmanage.getNewestSuggestedPlanList(query).then((res) => {
          if (res.data.suggestedPlanList) {
            if (Object.is(this.operate, 'recycle')) {
              this.reserveTableData = res.data.suggestedPlanList
            } else if (Object.is(this.operate, 'reserve')) {
              this.recycleTableData = res.data.suggestedPlanList
            }
          }
          if (Object.is(operate, 'recovery')) {
            row.isInRecycleBin = 0
          } else if (Object.is(operate, 'remove')) {
            row.isDelete = 1
          }
          if (this.capitalSourceClassify.isLocked === 1) {
            // 新增维护记录
            this.suggestedPlanList.push(row)
            this.createModifyData(row)
          } else {
            // 修改维护记录
            investmanage.saveSuggestedPlan(row).then(res => {
              this.getSuggestedPlanList()
            })
          }
        })
      })
    },
    /** 新增修改记录 */
    createModifyData (row) {
      let newClassify = this.capitalSourceClassify
      newClassify.operate = 2
      newClassify.objectId = null
      newClassify.firstClassifyCode = this.firstClassifyCode
      this.recycleTableData.map(suggestedPlan => {
        if (!Object.is(suggestedPlan.objectId, row.objectId)) {
          this.suggestedPlanList.push(suggestedPlan)
        }
      })
      this.reserveTableData.map(suggestedPlan => {
        if (!Object.is(suggestedPlan.objectId, row.objectId)) {
          this.suggestedPlanList.push(suggestedPlan)
        }
      })
      this.suggestedPlanList.map(suggestedPlan => {
        suggestedPlan.objectId = null
        suggestedPlan.capitalSource = this.capitalSourceClassify.capitalSourceId
        if (suggestedPlan.declarationForm) {
          suggestedPlan.declarationForm.objectId = null
          suggestedPlan.declarationForm.projectName = suggestedPlan.projectName
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
        if (res.data.capitalSourceClassify) {
          this.classifyCode = res.data.capitalSourceClassify.code
          this.recycleFlag = 1
          this.getSuggestedPlanList()
          this.getCapitalSourceList()
        }
      })
    },
    /** 树表格操作 */
    tableBtnClick (row) {
      // 查看
      this.getCapitalSource()
      this.getDeclarationForm(row)
      if (row.classify === 1) {
        // 专项资金
        this.dialogVisible = true
      } else {
        // 工程建设
        this.enginerDialogViseble = true
      }
    },
    /** 接受card的传值 */
    cardToMain (val) {
      if (Object.is(val.checkEdit, 'checkEdit')) {
        this.currentView = 'checkEdit'
      }
      this.classifyCode = val.code
    },
    editToMain (val) {
      this.tabsNewValue = val
    },
    /** 查询建议计划列表 */
    getSuggestedPlanList () {
      // 查询工程建设项目资金来源
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
      }).then(res => {
        let query = {
          investmentProposalSchemeCode: this.$route.query.code,
          isDelete: 0
        }
        if (Object.is(this.operate, 'recycle')) {
          query.isInRecycleBin = 1
          query.messageLike = this.searchRecycleForm.messageLike || null
          query.capitalSource = this.searchRecycleForm.capitalSource || null
        } else if (Object.is(this.operate, 'reserve')) {
          query.isInRecycleBin = 2
          query.messageLike = this.searchReserveForm.messageLike || null
          query.capitalSource = this.searchReserveForm.capitalSource || null
        }
        if (this.classifyCode) {
          query.capitalSourceClassifyCode = this.classifyCode
        }
        investmanage.getNewestSuggestedPlanList(query).then((res) => {
          if (res.data.suggestedPlanList) {
            res.data.suggestedPlanList.map(suggestedPlan => {
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
            if (Object.is(this.operate, 'recycle')) {
              this.recycleTableData = res.data.suggestedPlanList
            } else if (Object.is(this.operate, 'reserve')) {
              this.reserveTableData = res.data.suggestedPlanList
            }
          }
        })
      })
    },
    /** 查询建议计划列表(树结构) */
    getElementTree () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        capitalSource: this.searchQueryForm.capitalSource,
        capitalSourceClassifyCode: this.classifyCode,
        isInRecycleBin: 0
      }
      investmanage.selectElementTree(query).then((res) => {
        if (res.data.suggestedPlanList) {
          let tempData = res.data.suggestedPlanList
          if (tempData) {
            this.queryPlanTableData = this.transTreeData(tempData)
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
    /** 查询资金来源下拉选项 */
    getCapitalSourceList () {
      let query = {
        classify: 1,
        isAvailableFunds: 1,
        isDelete: 0
      }
      investmanage.getCapitalSourceList(query).then(res => {
        this.capitalSourceList = res.data.capitalSourceList
        let capitalSource = {}
        capitalSource.name = '工程建设'
        capitalSource.objectId = 0
        this.capitalSourceList.push(capitalSource)
      })
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row) {
      if (row.declarationForm) {
        this.dialogBaseInfoForm = row.declarationForm
        this.dialogBaseInfoForm.equipmentTableData = row.declarationForm.equipmentActualityList
        this.dialogBaseInfoForm.engineerTableData = row.declarationForm.accessoriesList
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
        if (row.classify === 1) {
          // 专项资金
          this.dialogVisible = true
        } else {
          // 工程建设
          this.enginerDialogViseble = true
        }
      } else {
        this.$message.warning('此项目下无申报表信息！')
        if (row.classify === 1) {
          // 专项资金
          this.dialogVisible = false
        } else {
          // 工程建设
          this.enginerDialogViseble = false
        }
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
    },
    /** 查询申报表 */
    getDeclarationForm (row) {
      let query = {
        suggestedPlanCode: row.code
      }
      investmanage.getDeclarationForm(query).then((res) => {
        if (res.data.declarationForm) {
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
    }
  },
  // 实例创建完成
  mounted () {
  },
  components: {
    treeGridQuery,
    // 卡片
    checkCard: resolve => require(['./checkcard.vue'], resolve),
    // 编辑
    checkEdit: resolve => require(['./checkedit'], resolve)
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
      .enginer .table-form {
        .el-form-item .el-form-item__content {
          margin-left: 0 !important;
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
