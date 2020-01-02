<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="submit-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurmanage' }">招采建议计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/bidpurmanage/bidpurdetails' }">详情页</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="submit-cont">
      <div class="submit-cont-tit">
        <h2>{{year}}年招标项目计划表</h2>
        <el-row :gutter="20">
          <el-col :span="12" style="text-align: left;">
            <span class="pre-dowm-l">单位名称:</span>{{enterpriseName}}
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <span class="pre-dowm-l">投资单位:</span><span>万元</span>
          </el-col>
        </el-row>
      </div>
      <!-- 表格区域start -->
      <el-table
        :data="tableData"
        border
        :row-class-name="tableRowClassName"
        height="450"
        style="width: 100%">
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
          align="left"
          width="110">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="clickCellRow(scope.row)" style="line-height: 0;overflow: hidden;width:100%;text-align: left;">{{scope.row.projectPlanName}}</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="projectList"
          label="项目名称"
          header-align="center"
          align="left"
          width="200">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.projectName" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.projectName && item.projectName!=''" placement="top">
              <p class="dresult-span">
                <el-button
                  type="text"
                  size="mini"
                  @click="clickCellCloumn(item)"
                  style="line-height: 0;overflow: hidden;width:100%;text-align: left;">
                  {{item.projectName}}
                </el-button>
              </p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="projectList"
          label="项目主要内容"
          header-align="center"
          align="left"
          width="200">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.content" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.content && item.content!=''" placement="top">
              <p class="dresult-span">{{item.content}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="projectList"
          label="工程量单位"
          align="center"
          width="100">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.quantitiesUnit" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.quantitiesUnit" placement="top">
              <p class="dresult-span" >{{item.quantitiesUnit}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="projectList"
          label="工程量"
          align="center"
          width="100">
          <template slot-scope="scope" v-if="scope.row.projectList">
            <el-tooltip effect="dark" :content="item.currentPlanQuantities?item.currentPlanQuantities:item.budgetQuantities" placement="top" v-for="(item,index) in scope.row.projectList" :key="index">
              <p class="dresult-span" v-if="item.currentPlanQuantities">{{item.currentPlanQuantities}}</p>
              <p class="dresult-span" v-else>{{item.budgetQuantities}}</p>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="projectList"
          label="资金来源"
          width="120">
          <template slot-scope="scope">
            <el-tooltip effect="dark" :content="item.capitalSourceName?item.capitalSourceName:capitalSourceName" v-for="(item,index) in scope.row.projectList" :key="index" v-if="item.capitalSource==0 || item.capitalSourceName" placement="top">
              <p class="dresult-span" >{{item.capitalSourceName?item.capitalSourceName:capitalSourceName}}</p>
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
              <el-tooltip effect="dark" :content="item.fundPurpose" v-for="(item,index) in scope.row.fundPurpose" :key="index" v-if="item.fundPurpose" placement="top">
              <p class="dresult-span" >{{item.fundPurpose}}</p>
            </el-tooltip>
            <p class="dresult-blank-span" v-else></p>
          </template>
        </el-table-column>
        <el-table-column
          prop="planLimit"
          label="计划工期"
          :formatter="filterPlanLimit"
          align="center">
        </el-table-column>
        <el-table-column
          prop="planTotalAmount"
          label="计划总金额"
          align="right">
        </el-table-column>
        <el-table-column
          prop="bidTime"
          label="拟招标时间"
          :formatter="filterBidTime"
          align="center"
          width="200">
        </el-table-column>
        <el-table-column
          prop="executeTime"
          :formatter="filterExecuteTime"
          label="拟实施时间"
          align="center"
          width="200">
        </el-table-column>
        <el-table-column
          align="center"
          prop="bidType"
          label="拟招标方式"
          :formatter="filterBidType">
        </el-table-column>
        <el-table-column
          prop="projectType"
          label="项目类型"
          :formatter="filterProjectType"
          align="center">
        </el-table-column>
        <el-table-column
          prop="enterpriseName"
          label="需求方"
          align="center">
        </el-table-column>
      </el-table>
      <!-- 表格区域end -->
    </div>

    <!-- 审批记录start -->
    <div class="submit-apply-flag">
      <el-row :gutter="20">
        <el-col :span="3"><span class="apply-name-color">审批记录:</span></el-col>
        <el-col :span="20">
          <el-steps direction="vertical" :active="1" :space="100">
            <el-step
              v-for="(item,index) in approveRecordList"
              :key="index"
              :status='fliteredStatus(item.approveResult)'>
              <span slot="title" class="step-span span-color span-first" v-if="item.approveResult === 0">{{item.applyName}}</span>
              <span slot="title" class="step-span span-color span-first" v-if="item.approveResult !== 0">{{item.approveName}}</span>
              <span slot="title" class="step-span span-second span-color" v-if="item.approveResult === 0">{{item.applyOrganization}}</span>
              <span slot="title" class="step-span span-second span-color" v-if="item.approveResult !== 0">{{item.approveOrganization}}</span>
              <span slot="title" class="step-span span-third">{{item.approveResult | filterResult}}</span>
              <span slot="title" class="step-span span-color span-last">{{item.approveTime | filterDate}}</span>
              <div slot="title" class="apply-option" v-if="item.approveOpinion">
                <span>审批意见:{{item.approveOpinion}}</span>
              </div>
            </el-step>
          </el-steps>
        </el-col>
      </el-row>
    </div>
    <!-- 审批记录end -->
    <div class="dialog-box">
      <!--招采项目弹窗-->
      <el-dialog
        title="查看"
        :visible.sync="dialogLookVisible"
        width="65%"
        center>
        <div class="form-box">
          <el-form :model="rulePlanForm" label-width="130px" class="demo-ruleForm">
            <el-form-item label="招标计划名称:">
              <span>{{rulePlanForm.projectPlanName}}</span>
            </el-form-item>
            <el-form-item label="包含项目详情:">
              <el-table
                :data="doPlanTableData"
                border
                style="width: 100%">
                <el-table-column
                  align="left"
                  prop="projectName"
                  label="项目名称">
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="content"
                  label="项目主要内容">
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="currentPlanQuantities"
                  label="工程量">
                  <template slot-scope="scope">
                    {{(scope.row.currentPlanQuantities!== null) ? scope.row.currentPlanQuantities : scope.row.budgetQuantities}}
                  </template>
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="currentPlanAmount"
                  label="计划金额（万元）">
                  <template slot-scope="scope">
                    {{(scope.row.currentPlanAmount!== null) ? scope.row.currentPlanAmount : scope.row.budgetAmount}}
                  </template>
                </el-table-column>
                <el-table-column
                  align="left"
                  prop="capitalSourceName"
                  label="资金来源">
                  <template slot-scope="scope">
                    <p class="dresult-span" >{{scope.row.capitalSourceName?scope.row.capitalSourceName:capitalSourceName}}</p>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="计划总金额(万元):">
              <span>{{rulePlanForm.planTotalAmount}}</span>
            </el-form-item>
            <el-form-item label="计划工期:">
              <span>{{rulePlanForm.planLimit |filterPlanLimit}}</span>
            </el-form-item>
            <el-form-item label="拟招标时间:">
              <span>{{rulePlanForm.bidStartTime |filterDate}}--{{rulePlanForm.bidEndTime |filterDate}}</span>
            </el-form-item>
            <el-form-item label="拟实施时间:">
              <span>{{rulePlanForm.executeStartTime|filterDate}}--{{rulePlanForm.executeEndTime |filterDate}}</span>
            </el-form-item>
            <el-form-item label="采购方式:">
              <span>{{rulePlanForm.bidType |filterBidType}}</span>
            </el-form-item>
            <el-form-item label="项目类型:">
              <span>{{rulePlanForm.projectType |filterProjectType}}</span>
            </el-form-item>
            <el-form-item label="需求方:">
              <span>{{rulePlanForm.enterpriseName}}</span>
            </el-form-item>
            <el-form-item label="项目单位负责人:">
              <span>{{rulePlanForm.projectLeader}}</span>
            </el-form-item>
            <el-form-item label="负责人所属部门:">
              <span>{{rulePlanForm.projectLeaderDept}}</span>
            </el-form-item>
            <el-form-item label="集团公司负责人:">
              <span>{{rulePlanForm.groupLeader}}</span>
            </el-form-item>
            <el-form-item label="负责人所属部门:">
              <span>{{rulePlanForm.groupLeaderDept}}</span>
            </el-form-item>
            <el-form-item label="备注:">
              <span>{{rulePlanForm.remark}}</span>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!--申报表弹窗-->
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
                    <span>{{dialogBaseInfoForm.procurementMethod | filterBidType}}</span>
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
                  {{dialogBaseInfoForm.procurementMethod  | filterBidType}}
                </td>
              </tr>
              <tr>
                <td>投资主体</td>
                <td  colspan="2">
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
  </div>
</template>
<script>
import {plansubmit, investmanage} from 'api/index'
import {formatDate} from 'common/js/date.js'
import {CityInfo} from 'common/js/region'
// 实例
export default {
  data () {
    return {
      enginerDialogViseble: false,
      totalForm: {},
      firstMineForm: {},
      secondCivilForm: {},
      thirdInstallForm: {},
      fourthEquipForm: {},
      fifthOtherForm: {},
      dialogLookVisible: false,
      rulePlanForm: {}, // 招采-查看表单
      doPlanTableData: [],
      capitalSourceName: '',
      tableData: [],
      year: '',
      enterpriseName: '',
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      approveRecordList: [], // 审批记录列表
      dialogVisible: false,
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况
        engineerTableData: [] // 工程主要内容及备件主材
      }// 弹窗-基本信息表单
    }
  },
  created () {
    this.getCapitalSource()
  },
  filters: {
    /** 申报表-计划工期 */
    filterTimeLimit (value) {
      let timeLimit = value
      if (timeLimit !== '' && timeLimit !== null) {
        return timeLimit + '个月'
      }
    },
    /** 招采项目-计划工期 */
    filterPlanLimit (value) {
      let planLimit = value
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
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
    filterNature (val) {
      let constructiveNature = val
      let obj = {
        1: '新建',
        2: '扩建',
        3: '改建',
        4: '迁建',
        5: '恢复重建',
        6: '技改'
      }
      return obj[constructiveNature]
    },
    filterTime (val) {
      if (val) {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return ''
      }
    },
    filterBidType (val) {
      let bidType = Number(val)
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
    filterResult (val) {
      let approveResult = val
      if (approveResult === 0) {
        return '发起审批申请'
      } else if (approveResult === 1) {
        return '通过'
      } else if (approveResult === 2) {
        return '退回'
      } else if (approveResult === 3) {
        return '终止'
      }
    },
    filterDate (val) {
      if (val === null) {
        return ''
      } else {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
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
    /** 表格行颜色设置 */
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 格式化 */
    // 申报表-格式化购置时间
    filterDates (row, column) {
      if (!row.purchaseTime) {
        return ''
      } else {
        let date = new Date(row.purchaseTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
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
    // 计划工期
    filterPlanLimit (row, column) {
      let planLimit = row.planLimit
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
      }
    },
    // 拟招标方式
    filterBidType (row, column) {
      let bidType = Number(row.bidType)
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
    // 项目类型
    filterProjectType (row, column) {
      let projectType = row.projectType
      if (projectType === 1) {
        return '工程'
      } else if (projectType === 2) {
        return '货物'
      } else if (projectType === 3) {
        return '服务'
      }
    },
    // 拟招标时间
    filterBidTime (row, column) {
      if (row.bidStartTime === null || row.bidEndTime === null) {
        return false
      } else {
        let stateDate = new Date(row.bidStartTime)
        let endDate = new Date(row.bidEndTime)
        return formatDate(stateDate, 'yyyy-MM-dd') + ' 至 ' + formatDate(endDate, 'yyyy-MM-dd')
      }
    },
    // 拟实施时间
    filterExecuteTime (row, column) {
      if (row.executeStartTime === null || row.executeEndTime === null) {
        return false
      } else {
        let stateDate = new Date(row.executeStartTime)
        let endDate = new Date(row.executeEndTime)
        return formatDate(stateDate, 'yyyy-MM-dd') + ' 至 ' + formatDate(endDate, 'yyyy-MM-dd')
      }
    },
    /** 进行查看操作后返回当前页 */
    goBack () {
      this.$router.push({path: './bidpurmanage', query: {currentPage: this.$route.query.currentPage, pageNo: this.$route.query.pageNo, pageSize: this.$route.query.pageSize}})
    },
    /** 列表数据展示 */
    getProjectPlanList () {
      this.year = this.$route.query.year
      this.enterpriseName = this.$route.query.enterpriseName
      // 查找招标计划项目信息
      let obj = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        annualProjectPlanCode: this.$route.query.code,
        projectPlanTypeList: '1, 2, 4'
      }
      plansubmit.getProjectPlan(obj).then((res) => {
        if (res.data.projectPlanList) {
          let tempTable = res.data.projectPlanList.list
          // this.tableData = res.data.projectPlanList.list
          this.total = res.data.projectPlanList.total
          if (tempTable.length) {
            tempTable.map((item) => {
              let childArr = item.projectList.map((child) => {
                let str = ''
                if (child.fundPurposeFirstName) {
                  str += child.fundPurposeFirstName
                }
                if (child.fundPurposeSecondtName) {
                  str += child.fundPurposeSecondtName
                }
                child.fundPurpose = str
                return child
              })
              item.fundPurpose = childArr
              return item
            })
            this.tableData = tempTable
          }
        } else {
          return false
        }
      })
    },
    /** 获取审批记录 */
    getApproveRecord () {
      let obj = {
        relatedCode: this.$route.query.code
      }
      plansubmit.getApproveRecord(obj).then((res) => {
        if (res.data.aproveRecord) {
          this.approveRecordList = res.data.aproveRecord
        } else {
          return false
        }
      })
    },
    /** 点击招标计划名称查看招标计划详情 */
    clickCellRow (row) {
      if (row) {
        this.rulePlanForm = row
        this.doPlanTableData = row.projectList
        this.dialogLookVisible = true
      }
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row) {
      if (row.declarationForm) {
        this.getDeclarationForm(row.code)
        if (row.classify === 1) {
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
  computed: {},
  // 实例创建完成
  mounted () {
    this.getProjectPlanList()
    this.getApproveRecord()
  }
}
</script>
<style lang="less">
#submit-details-page {
  /*表格区域css*/
  .submit-cont {
    margin: 20px;
    padding: 20px;
    background: #fff;
    .submit-cont-tit {
      padding-bottom: 5px;
    }
    .pre-dowm-l {
      color: #999;
      padding-right: 10px;
    }
    .el-table__body .el-table__row {
      td:nth-child(3),
      td:nth-child(4),
      td:nth-child(5),
      td:nth-child(6),
      td:nth-child(7),
      td:nth-child(8){
        div.cell {
          padding:0;
        }
        p {
          margin: 0;
          padding: 10px;
          height: 22px;
        }
        .dresult-blank-span {
          height: 22px;
        }
        p:not(:last-child) {
          border-bottom: 1px solid #eee;
        }
      }
      td:nth-child(3) p,
      td:nth-child(4) p,
      td:nth-child(6) p,
      td:nth-child(7) p,
      td:nth-child(8) p{
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
    .warning-row {
      color: red;
    }
  }
  /*审批记录css*/
  .submit-apply-flag {
    height: 600px;
    margin: 20px 20px 130px;
    padding-top: 20px;
    background: #fff;
    overflow-x: hidden;
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
    .span-first {
      width: 100px;
    }
    .span-second {
      width: 200px;
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
  .el-form-item {
    margin-left: -50px;
    margin-bottom: 0;
  }
  /** 弹窗-表格样式 */
  .dialog-box {
    .form-box {
      .el-form-item {
        margin-left: 0;
        margin-bottom: 0;
      }
    }
    .base-info .table-form {
      width: 100%;
      border-collapse: collapse;
      border: 1px solid #eee;
      text-align: center;
      overflow: hidden;
      display: inline-block;
      tr td {
        min-height: 50px;
        line-height: 50px;
        border: 1px solid #eee;
      }
      tr td:nth-child(odd) {
        min-width: 100px;
        background: #F6F7F9;
      }
      tr td:nth-child(even) {
        min-width: 320px;
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
}
</style>
