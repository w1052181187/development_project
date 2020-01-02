<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="proplancheck-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/proplancheck' }">项目计划审核</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索区域start -->
    <div class="projectapp-search">
      <el-form :model="ruleSearchForm" :inline="true" ref="ruleSearchForm" class="demo-form-inline" style="text-align: left;">
        <el-form-item label="年度:">
          <el-select v-model="ruleSearchForm.year" clearable placeholder="请选择">
            <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="项目单位:">
          <el-input v-model="ruleSearchForm.enterpriseName" placeholder="请填写项目单位"></el-input>
        </el-form-item>
        <el-form-item label="审核状态:">
          <el-select v-model="ruleSearchForm.status" clearable placeholder="请选择状态">
            <el-option
              v-for="item in statusList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSearch(ruleSearchForm)" icon="el-icon-search">搜索</el-button>
        </el-form-item>
        <!--<el-row :gutter="20" style="margin-left: -35px;">-->
          <!--<el-col :span="6" style="width: 220px">-->
            <!---->
          <!--</el-col>-->
          <!--<el-col :span="6" style="width: 220px">-->
            <!---->
          <!--</el-col>-->
          <!--<el-col :span="6" style="width: 270px">-->
            <!---->
          <!--</el-col>-->
          <!--<el-col :span="3" style="width: 100px">-->
            <!---->
          <!--</el-col>-->
        <!--</el-row>-->
      </el-form>
    </div>
    <!-- 搜索区域end -->

    <div class="maincont-box projectapp-cont">
      <!-- 表格start -->
      <div class="projectapp-tab">
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
            align="center"
            prop="enterpriseName"
            label="项目单位">
          </el-table-column>
          <el-table-column
            align="center"
            prop="projectPlanName"
            label="项目名称">
          </el-table-column>
          <el-table-column
            align="center"
            prop="year"
            label="计划年度"
            width="80">
          </el-table-column>
          <el-table-column
            align="center"
            prop="userName"
            label="提交人">
          </el-table-column>
          <el-table-column
            align="center"
            prop="submitTime"
            label="提交时间"
            :formatter="filterDate">
          </el-table-column>
          <el-table-column
            align="center"
            prop="approveName"
            label="审批人">
          </el-table-column>
          <el-table-column
            align="center"
            prop="status"
            label="审核状态"
            :formatter="filterStatuses">
          </el-table-column>
          <el-table-column
            align="center"
            prop="approveTime"
            label="审批时间"
            :formatter="filterDates">
          </el-table-column>
          <el-table-column
            align="left"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button @click="handleTableClick(scope.row,'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/planmanage/proplancheck/detail')">查看</el-button>
              <el-button v-if="scope.row.status == 2 && $store.getters.permissions.includes('/planmanage/proplancheck/detail')" @click="handleTableClick(scope.row,'apply')" type="text" size="medium">审批</el-button>
              <!--<el-button v-if="scope.row.status == 5 && $store.getters.permissions.includes('/plan/projectapp/operation')"  @click="handleTableClick(scope.row,'del')" type="text" size="medium" >删除</el-button>-->
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
      </div>
      <!-- 表格end -->
    </div>

    <!-- 弹窗区域start -->
    <div class="projectapp-dialog">
      <!-- 审批弹窗start -->
      <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%">
        <el-form ref="applyFormInfo" :model="applyFormInfo" :rules="rules" label-width="130px">
          <el-form-item label="招标计划年度:">
            <span>{{applyFormInfo.year}}</span>
          </el-form-item>
          <el-form-item label="招标计划名称:">
            <span>{{applyFormInfo.projectPlanName}}</span>
          </el-form-item>
          <el-form-item label="项目名称:">
            <span v-for = "(item,index) in projectTableData" :key="index" class="dresult-span" v-if="projectTableData.length > (index+1)">{{item.projectName+', '}}</span>
            <span class="dresult-span" v-else>{{item.projectName}}</span>
          </el-form-item>
          <el-form-item label="包含项目详情:">
            <el-table
              :data="projectTableData"
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
                prop="quantitiesUnit"
                label="工程量单位"
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
              <el-table-column
                label="操作"
                align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="dailogLookForm(scope.row)">查看申报表</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
          <el-form-item label="计划总金额(万元):">
            <span>{{applyFormInfo.planTotalAmount}}</span>
          </el-form-item>
          <el-form-item label="计划工期:">
            <span>{{applyFormInfo.planLimit |filterPlanLimit}}</span>
          </el-form-item>
          <el-form-item label="拟招标时间:">
            <span>{{applyFormInfo.bidStartTime | filterDate}}--{{applyFormInfo.bidEndTime | filterDate}}</span>
          </el-form-item>
          <el-form-item label="拟实施时间:">
            <span>{{applyFormInfo.executeStartTime | filterDate}}--{{applyFormInfo.executeEndTime | filterDate}}</span>
          </el-form-item>
          <el-form-item label="采购方式:">
            <span>{{applyFormInfo.bidType |filteredBidType}}</span>
          </el-form-item>
          <el-form-item label="项目类型:">
            <span>{{applyFormInfo.projectType |filteredProjectType}}</span>
          </el-form-item>
          <el-form-item label="附件:">
            <span class="file-list" v-for="(item, index) in applyFormInfo.fileInformationList" :key="index">
              <span>
                <el-button
                  type="text"
                  @click="downloadFile(item.fileName,item.relativePath)"
                  style="padding-right: 30px;">
                  <span style="display:inline-block;text-align:left;overflow:hidden;text-overflow:ellipsis;white-space: nowrap;width:400px;">
                    {{item.fileName}}
                  </span>
                </el-button>
              </span>
            </span>
          </el-form-item>
          <el-form-item label="需求方:">
            <span>{{applyFormInfo.enterpriseName}}</span>
          </el-form-item>
          <el-form-item label="项目单位负责人:">
            <span>{{applyFormInfo.projectLeader}}</span>
          </el-form-item>
          <el-form-item label="负责人所属部门:">
            <span>{{applyFormInfo.projectLeaderDept}}</span>
          </el-form-item>
          <el-form-item label="集团公司负责人:">
            <span>{{applyFormInfo.groupLeader}}</span>
          </el-form-item>
          <el-form-item label="负责人所属部门:">
            <span>{{applyFormInfo.groupLeaderDept}}</span>
          </el-form-item>
          <el-form-item class="app-line" label="是否允许多次招标:">
            <span v-if="lookStatus">{{applyFormInfo.isAgain |filteredIsAgain}}</span>
            <el-select v-model="applyFormInfo.isAgain" placeholder="请选择" v-if="applyStatus" @change="changeStatus">
              <el-option
                v-for="item in tableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="app-line app-result" label="审批结果:" v-if="applyStatus" prop="approveResult">
            <el-select v-model="applyFormInfo.approveResult" placeholder="请选择">
              <el-option
                v-for="item in stateList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="app-line" label="审批意见:" v-if="applyStatus && applyFormInfo.approveResult !== 1" prop="approveOpinion">
            <el-input type="textarea" v-model="applyFormInfo.approveOpinion" style="width: 220px;height: 100px"> {{applyFormInfo.approveResult}}</el-input>
          </el-form-item>
          <el-form-item class="app-line" label="审批意见:" v-if="applyStatus && applyFormInfo.approveResult === 1">
            <el-input type="textarea" v-model="applyFormInfo.approveOpinion" style="width: 220px;height: 100px">{{applyFormInfo.approveResult}}</el-input>
          </el-form-item>
          <el-form-item class="app-btn-set" v-if="applyStatus">
            <el-button type="primary" @click="onFormSubmit('applyFormInfo')">确定</el-button>
            <el-button  @click="cancel">取消</el-button>
          </el-form-item>
        </el-form>

        <!-- 审批记录start -->
        <div class="pro-record" v-if="lookStatus">
          <el-row :gutter="20">
            <el-col :span="3"><span class="apply-name-color">审批记录:</span></el-col>
            <el-col :span="21">
              <el-steps direction="vertical" :active="1" :space="100">
                <el-step
                  v-for="(item,index) in approveRecordList"
                  :key="index"
                  :status='fliteredStatus(item.approveResult)'>
                  <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
                  <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
                  <span slot="title" class="step-span span-color span-second" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
                  <span slot="title" class="step-span span-color span-second" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
                  <span slot="title" class="step-span span-third" >
                    {{item.approveResult | filteredResult}}
                  </span>
                  <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterDate}}</span>
                  <div slot="title" class="apply-option" v-if="item.approveOpinion">
                    审批意见:<span>{{item.approveOpinion}}</span>
                  </div>
                </el-step>
              </el-steps>
            </el-col>
          </el-row>
        </div>
        <!-- 审批记录end -->
      </el-dialog>
      <!-- 审批弹窗end -->
      <!--申报表弹窗start-->
      <el-dialog
        title="专项资金建议计划申报表"
        :visible.sync="dialogExpertVisible"
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
                    <span>{{dialogBaseInfoForm.lastMaintenanceTime | filterDate}}</span>
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
            <span>注:表中内容请各单位认真填写，内容不完善，将不考虑列入</span>{{this.applyFormInfo.year}}<span>年计划中。设备现有状况填写时，每（台或个）设备填写一行</span>
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
      <!--申报表弹窗end-->
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
                  <span>{{dialogBaseInfoForm.capitalSourceName}}</span>
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
                <td>{{this.applyFormInfo.year - 1}}年累计完成</td>
                <td style="text-align:center;background:#F6F7F9;">{{this.applyFormInfo.year - 1}}年预计累计完成</td>
                <td>{{this.applyFormInfo.year}}年投资建议计划</td>
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
                <td>{{this.applyFormInfo.year}}年项目审批目标</td>
                <td colspan="4">
                  {{dialogBaseInfoForm.approveTarget}}
                </td>
              </tr>
              <tr>
                <td>{{this.applyFormInfo.year}}年项目建设目标</td>
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
    <!-- 弹窗end -->
  </div>
</template>

<script>
import {planproject} from 'api/index'
import {formatDate} from 'common/js/date'
import {downloadFile} from 'common/js/common'
import {CityInfo} from 'common/js/region'
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
      dialogExpertVisible: false, // 专项资金建议计划申报表-弹窗
      dialogBaseInfoForm: {},
      tableData: [],
      dialogTitle: '审批',
      // 搜索表单开始
      ruleSearchForm: {},
      startTime: null, // 搜索开始时间
      endTime: null, // 搜素结束时间
      yearList: this.getYearMethed(),
      enterpriseList: {},
      statusList: [
        {
          label: '审核中',
          value: 2
        },
        {
          label: '通过',
          value: 3
        },
        {
          label: '终止',
          value: 5
        }
      ],
      // 搜索表单结束
      applyFormInfo: {}, // 审批表单
      applySubmitInfo: {}, // 审批表单时提交数据
      projectTableData: [], // 弹窗-包含项目详情表格数据
      dialogVisible: false, // 审批
      tableOption: [ // 是否多次招标
        {
          label: '是',
          value: 1
        },
        {
          label: '否',
          value: 0
        }
      ],
      stateList: [
        {
          label: '通过',
          value: 1
        },
        {
          label: '退回',
          value: 2
        },
        {
          label: '终止',
          value: 3
        }
      ],
      rules: {
        approveResult: [
          { required: true, message: '请选择审批状态', trigger: 'change' }],
        approveOpinion: [
          {required: true, message: '请输入审批意见', trigger: 'change'}]
      },
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      lookStatus: false, // 查看状态
      applyStatus: true, // 审批状态
      approveRecordList: [], // 审批记录数据
      projectNameList: []
    }
  },
  created () {
    this.dialogVisible = Boolean(this.$route.query.key)
    this.applyStatus = Boolean(this.$route.query.key)
    if (this.$route.query.objectId) {
      this.getProjectPlan(this.$route.query.objectId)
    }
    this.projectappData()
  },
  /** 弹窗过滤 */
  filters: {
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
    /**  格式化招标方式 */
    filteredBidType (bidType) {
      if (bidType === 1) {
        return '公开招标'
      } else if (bidType === 2) {
        return '邀请招标'
      } else if (bidType === 3) {
        return '谈判采购'
      } else if (bidType === 4) {
        return '询比采购'
      } else if (bidType === 5) {
        return '竞价采购'
      } else if (bidType === 6) {
        return '直接采购'
      } else if (bidType === 7) {
        return '框架协议'
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
    /** 格式化招标状态 */
    filteredIsAgain (isAgain) {
      if (isAgain === 0) {
        return '否'
      } else if (isAgain === 1) {
        return '是'
      }
    },
    /** 格式化审批状态 */
    filteredResult (value) {
      if (value === 1) {
        return '通过'
      } else if (value === 2) {
        return '退回'
      } else if (value === 3) {
        return '终止'
      } else if (value === 0) {
        return '发起申请'
      }
    },
    /** 格式化时间 */
    filterDate (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    },
    /** 格式化时间 */
    filterTime (value) {
      if (value) {
        let date = new Date(value)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    /** 计划工期 */
    filterPlanLimit (value) {
      let planLimit = value
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
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
    /** 申报表-计划工期 */
    filterTimeLimit (value) {
      let timeLimit = value
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
      }
    },
    filterProjectType (val) {
      let projectType = val
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    },
    filterProcurementMethod (val) {
      let procurementMethod = Number(val)
      if (procurementMethod === 1) {
        return '公开招标'
      } else if (procurementMethod === 2) {
        return '邀请招标'
      } else if (procurementMethod === 3) {
        return '谈判采购'
      } else if (procurementMethod === 4) {
        return '询比采购'
      } else if (procurementMethod === 5) {
        return '竞价采购'
      } else if (procurementMethod === 6) {
        return '直接采购'
      } else if (procurementMethod === 7) {
        return '框架协议'
      }
    }
  },
  methods: {
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
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 搜索时间 */
    datePicker (value) {
      if (value) {
        this.startTime = value[0]
        this.endTime = value[1]
      } else {
        this.startTime = null
        this.endTime = null
      }
    },
    /** 查看-申报表 */
    dailogLookForm (row) {
      if (row.declarationForm) {
        this.dialogBaseInfoForm = row.declarationForm
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
        if (row.classify === 1) {
          // 专项资金
          this.dialogExpertVisible = true
        } else {
          // 工程建设
          this.enginerDialogViseble = true
        }
      } else {
        this.$message.warning('此项目下无申报表信息！')
        if (row.classify === 1) {
          // 专项资金
          this.dialogExpertVisible = false
        } else {
          // 工程建设
          this.enginerDialogViseble = false
        }
      }
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
    /** 格式化 */
    filterDate (row, column) {
      if (!row.submitTime) {
        return ''
      } else {
        let date = new Date(row.submitTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    filterDates (row, column) {
      if (!row.approveTime) {
        return ''
      } else {
        let date = new Date(row.approveTime)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      }
    },
    /** 申报表-格式化购置时间 */
    filterPurchaseTime (row, column) {
      if (!row.purchaseTime) {
        return ''
      } else {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    filterStatuses (row, column) {
      let state = row.status
      if (state === 1) {
        return '未提交'
      } else if (state === 2) {
        return '审核中'
      } else if (state === 3) {
        return '通过'
      } else if (state === 4) {
        return '退回'
      } else if (state === 5) {
        return '终止'
      }
    },
    /**   进行编辑，查看等操作后返回到当前页 */
    indexMethod (index) {
      if (this.$route.query.pageNo && this.$route.query.pageSize && this.$route.query.currentPage) {
        this.currentPage = parseInt(this.$route.query.currentPage)
        this.$route.query.currentPage = ''
      }
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 分页 */
    handleSizeChange (val) {
      var likeThis = this
      var pageSize = `${val}`
      this.pageNo = 0
      this.currentPage = 1
      this.pageSize = parseInt(pageSize)
      this.$nextTick(() =>
        this.projectappData(function (resp) {
          likeThis.total = resp.data.Data.Total
        })
      )
    },
    handleCurrentChange (nowNum) {
      this.currentPage = nowNum
      this.pageNo = (nowNum - 1) * this.pageSize
      this.projectappData()
    },
    /** 表格操作 */
    handleTableClick (row, type) {
      if (type === 'del') {
        this.deleteClick(row)
      } else if (type === 'look') {
        // 打开弹框
        this.dialogVisible = true
        // 设置flag标识当前的操作类型
        this.lookStatus = true
        this.applyStatus = false
        // 设置弹窗标题
        this.dialogTitle = '查看'
        this.getProjectPlan(row.objectId)
      } else if (type === 'apply') {
        // 打开弹框
        this.dialogVisible = true
        this.lookStatus = false
        this.applyStatus = true
        // 设置弹窗标题
        this.dialogTitle = '审批'
        this.getProjectPlan(row.objectId)
      }
    },
    deleteClick (data) {
      this.$confirm('此操作将删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        planproject.delete(data.objectId).then((res) => {
          this.projectappData()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 弹窗操作 */
    onFormSubmit (data) {
      this.$refs[data].validate((valid) => {
        if (valid) {
          let approveRecord = {}
          let store = this.$store.getters.authUser
          approveRecord.relatedCode = this.applyFormInfo.code
          approveRecord.applyName = this.applyFormInfo.userName
          approveRecord.approveName = store.userName
          approveRecord.approveOrganization = store.enterpriseName
          approveRecord.approveResult = this.applyFormInfo.approveResult
          approveRecord.approveOpinion = this.applyFormInfo.approveOpinion
          approveRecord.approveType = 3
          planproject.saveApprovalRecords(approveRecord).then(() => {
            this.dialogVisible = false
            this.projectappData()
          })
        }
      })
    },
    /** 是否多次招标 */
    changeStatus () {
      this.applySubmitInfo.objectId = this.applyFormInfo.objectId
      this.applySubmitInfo.isAgain = this.applyFormInfo.isAgain
      planproject.update(this.applySubmitInfo)
    },
    /** 取消 */
    cancel () {
      this.dialogVisible = false
      this.projectappData()
    },
    /** 单条查询 */
    getProjectPlan (objectId) {
      planproject.getOne(objectId).then((res) => {
        this.applyFormInfo = res.data.projectPlan
        this.projectTableData = res.data.projectPlan.projectList
        this.approveRecordList = res.data.projectPlan.approveRecordList
      })
    },
    /** 设置审批状态颜色 */
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 3) {
        return 'wait'
      }
    },
    /** 列表数据展示 */
    projectappData () {
      planproject.getList({
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        startTime: this.startTime,
        endTime: this.endTime,
        year: this.ruleSearchForm.year || null,
        status: this.ruleSearchForm.status || null,
        enterpriseLike: this.ruleSearchForm.enterpriseName || null,
        projectPlanType: 3,
        statuses: '2,3,5'
      }).then((res) => {
        this.tableData = res.data.projectPlanList.list
        if (this.tableData.length > 0) {
          for (let i = 0; i < this.tableData.length; i++) {
            if (this.tableData[i].approveRecordList !== null) {
              for (let j = 0; j < this.tableData[i].approveRecordList.length; j++) {
                if (this.tableData[i].status === 3 || this.tableData[i].status === 4 || this.tableData[i].status === 5) {
                  if (this.tableData[i].approveRecordList[j].approveResult === 1 || this.tableData[i].approveRecordList[j].approveResult === 2 || this.tableData[i].approveRecordList[j].approveResult === 3) {
                    this.tableData[i].approveTime = this.tableData[i].approveRecordList[j].approveTime
                    this.tableData[i].approveName = this.tableData[i].approveRecordList[j].approveName
                  }
                } else {
                  this.tableData[i].approveTime = ''
                  this.tableData[i].approveName = ''
                }
              }
            }
          }
        }
        this.total = res.data.projectPlanList.total
      })
    },
    /** 搜索 */
    onSearch () {
      this.projectappData()
    },
    // 下载附件文件
    downloadFile
  },
  computed: {},
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
  #proplancheck-page {
    /*搜索表单*/
    .projectapp-search {
      padding: 20px;
      margin: 20px;
      background-color: #fff;
      .el-form-item {
        margin-bottom: 0;
      }
    }
    /*审批记录css*/
    .pro-record {
      margin-top: 20px;
      padding-top: 20px;
      border-top: 1px solid #eeeeee;
      max-height: 400px;
      overflow: hidden;
      overflow-y: auto;
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
      .span-third {
        width: 100px;
      }
      .span-first {
        width: 100px;
      }
      .span-second {
        width: 160px;
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
      .span-color {
        color: #999;
      }
    }
    .projectapp-cont {
      .add-plan {
        text-align: left;
        padding-bottom: 20px;
        .plan-down-btn {
          padding-left: 10px;
        }
      }
    }
    .el-date-editor--datetimerange {
      width: 82%;
    }
    .el-form-item__content {
      text-align: left;
    }
    .el-form-item {
      margin-bottom: 0;
    }
    .app-btn-set {
      .el-form-item__content {
        text-align: center;
      }
    }
    /*重置margin-bottom*/
    .app-line {
      margin-bottom: 5px;
    }
    .app-result {
      margin-bottom: 20px;
    }
    .block-page {
      padding-top: 20px;
    }
    /* 弹窗 */
    .projectapp-dialog {
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
          margin-bottom: 0;
        }
      }
    }
    }
</style>
