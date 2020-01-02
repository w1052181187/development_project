<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="prorequest-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/proplanapp' }">项目计划申请</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 搜索区域start -->
    <div class="prorequest-search">
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
              <el-input v-model="ruleSearchForm.enterpriseName" style="width:215px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="5" style="width:300px">
            <el-form-item label="审核状态:">
              <el-select v-model="ruleSearchForm.status" clearable placeholder="请选择">
                <el-option
                  v-for="item in statusList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
    <div class="maincont-box prorequest-cont">
      <div class="prorequest-add">
        <el-button type="warning" @click="addClickBtn" icon="el-icon-plus"  v-if="$store.getters.permissions.includes('/planmanage/proplanapp/operation') && dialogAdd">新增</el-button>
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
          width="80"
          prop="year"
          label="计划年度">
        </el-table-column>
        <el-table-column
          align="center"
          prop="submitName"
          label="提交人">
        </el-table-column>
        <el-table-column
          align="center"
          prop="submitTime"
          :formatter="filterSubDate"
          label="提交时间"
          width="170">
        </el-table-column>
        <el-table-column
          align="center"
          prop="approveName"
          label="审核人">
        </el-table-column>
        <el-table-column
          align="left"
          header-align="center"
          prop="approveTime"
          label="审核时间"
          :formatter="filterAppDate">
        </el-table-column>
        <el-table-column
          align="center"
          prop="status"
          :formatter="filterStatus"
          label="审核状态">
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="140">
          <template slot-scope="scope">
            <el-button @click="handleTableClick(scope.row, 'look')" type="text" size="medium" v-if="$store.getters.permissions.includes('/planmanage/proplanapp/detail')">查看</el-button>
            <el-button @click="handleTableClick(scope.row, 'edit')" type="text" size="medium" v-if="(scope.row.status === 1 || scope.row.status === 4) && $store.getters.permissions.includes('/planmanage/proplanapp/operation') ">修改</el-button>
            <el-button @click="handleTableClick(scope.row, 'del')" type="text" size="medium" v-if="(scope.row.status === 1 || scope.row.status === 4 || scope.row.status === 5) && $store.getters.permissions.includes('/planmanage/proplanapp/operation')" >删除</el-button>
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
    <div class="dialog-box">
      <el-dialog
        :title="dialogRequestTitle"
        :visible.sync="dialogRequestVisible"
        @close="onFormCancel"
        width="50%">
        <el-form ref="requestFormInfo" :model="requestFormInfo" :rules="rules" label-width="130px">
          <el-form-item label="招标计划年度:">
            <span>{{requestFormInfo.year}}</span>
          </el-form-item>
          <el-form-item label="招标计划名称:" prop="projectPlanName">
            <span v-if="dialogDetails">{{requestFormInfo.projectPlanName}}</span>
            <el-input v-model="requestFormInfo.projectPlanName" style="width:200px;" v-else>招标计划名称</el-input>
          </el-form-item>
          <el-form-item label="采购方式:" prop="bidType">
            <span v-if="dialogDetails">{{requestFormInfo.bidType | filterBidType}}</span>
            <el-select v-model="requestFormInfo.bidType" placeholder="请选择" v-else>
              <el-option
                v-for="item in bidTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="项目类型:" prop="projectType">
            <span v-if="dialogDetails">{{requestFormInfo.projectType | filterProjectType}}</span>
            <el-select v-model="requestFormInfo.projectType" placeholder="请选择" v-else>
              <el-option
                v-for="item in projectTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="包含项目详情:" ref="projectForm" prop="project">
            <el-table
              :data="requestFormInfo.requestTableData"
              border
              style="width: 100%"
              id="dialogTable">
              <el-table-column
                prop="projectName"
                label="项目名称"
                align="left">
                <template slot-scope="scope">
                  <el-form :model="scope.row" :rules="projectNameRules" :ref="'projectNameForm'+scope.$index" class="demo-ruleForm">
                    <el-form-item prop="projectName" v-if="!dialogDetails">
                      <el-input type="text" v-model="scope.row.projectName" placeholder="请输入内容"></el-input>
                    </el-form-item>
                   <span v-else>
                    <el-button
                      type="text"
                      size="mini"
                      @click="clickCellCloumn(scope.row)"
                      style="line-height: 0;overflow: hidden;width:100%;text-align: left;">
                      {{scope.row.projectName}}
                    </el-button>
                   </span>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                prop="content"
                label="项目主要内容"
                align="left">
                <template slot-scope="scope">
                  <el-form :model="scope.row" :rules="contentRules" class="demo-ruleForm">
                    <el-form-item prop="content"  v-if="!dialogDetails">
                      <el-input type="text" v-model="scope.row.content"></el-input>
                    </el-form-item>
                    <span v-else>{{scope.row.content}}</span>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                prop="quantitiesUnit"
                label="工程量单位"
                align="left">
                <template slot-scope="scope">
                  <el-form :model="scope.row" :rules="quantitiesRules"  class="demo-ruleForm">
                    <el-form-item prop="quantitiesUnit"  v-if="!dialogDetails">
                      <el-input type="text" v-model="scope.row.quantitiesUnit"></el-input>
                    </el-form-item>
                    <span v-else>{{scope.row.quantitiesUnit}}</span>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                prop="currentPlanQuantities"
                label="工程量"
                align="left">
                  <template slot-scope="scope">
                    <el-form :model="scope.row" :rules="quantitiesRules" class="demo-ruleForm">
                      <el-form-item prop="currentPlanQuantities"  v-if="!dialogDetails">
                        <el-input type="text" v-model="scope.row.currentPlanQuantities"></el-input>
                      </el-form-item>
                      <span v-else>{{scope.row.currentPlanQuantities}}</span>
                    </el-form>
                </template>
              </el-table-column>
              <el-table-column
                prop="currentPlanAmount"
                label="计划金额(万元)"
                align="left">
                <template slot-scope="scope">
                  <el-form :model="scope.row" :rules="currentPlanAmountRules" :ref="'currentPlanAmountForm'+scope.$index" class="demo-ruleForm">
                    <el-form-item prop="currentPlanAmount" v-if="!dialogDetails">
                      <el-input type="text" v-model="scope.row.currentPlanAmount"></el-input>
                    </el-form-item>
                  <span v-else>{{scope.row.currentPlanAmount}}</span>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                prop="capitalSourceName"
                label="资金来源"
                align="left">
                <template slot-scope="scope">
                  <el-form :model="scope.row" :rules="capitalSourceRules" :ref="'capitalSourceForm'+scope.$index" class="demo-ruleForm"
                           :disabled="scope.row.objectId !== null && scope.row.objectId !== '' && scope.row.objectId !== undefined">
                    <el-form-item prop="capitalSource" v-if="!dialogDetails">
                      <el-select v-model="scope.row.capitalSource" placeholder="请选择">
                        <el-option
                          v-for="item in capitalSourceNameList"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                   <span v-else>{{scope.row.capitalSourceName}}</span>
                  </el-form>
                </template>
              </el-table-column>
              <el-table-column
                align="left"
                label="操作"
                width="200"
                v-if="dialogShow">
                <template slot-scope="scope">
                  <el-button
                    @click="handleTableClick(scope.row,'dialogsave',scope.$index)"
                    type="text"
                    size="small">
                    保存
                  </el-button>
                  <el-button
                    @click="handleTableClick(scope.row,'dialogedit', scope.$index)"
                    type="text"
                    size="small"
                    v-show="scope.row.objectId && requestFormInfo.projectType && requestFormInfo.bidType">
                    编辑项目申报表
                  </el-button>
                  <el-button
                    @click="handleTableClick(scope.row,'dialogdel', scope.$index)"
                    type="text"
                    size="small">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="add-row-tab" @mouseleave = "validProject" v-if="dialogShow">
              <el-button icon="el-icon-plus" @click="addDialogTableRow" style="width:100%;line-height:0;border-top: 0;"></el-button>
            </div>
          </el-form-item>
          <el-form-item label="计划总金额(万元):">
            <span>{{requestFormInfo.planTotalAmount}}</span>
          </el-form-item>
          <el-form-item label="计划工期:" prop="planLimit">
            <span v-if="dialogDetails">{{requestFormInfo.planLimit | filterPlanLimit}}</span>
            <el-select v-model="requestFormInfo.planLimit" placeholder="请选择" v-else>
              <el-option
                v-for="item in planLimitList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="拟招标时间:" prop="bidTime">
            <span v-if="dialogDetails">{{requestFormInfo.bidTime | filterBidTime}}</span>
            <el-date-picker
              v-else
              v-model="bidTime"
              type="daterange"
              @change="bidTimePicker"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width:50%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="拟实施时间:" prop="executeTime">
            <span v-if="dialogDetails">{{requestFormInfo.executeTime | filterExecuteTime}}</span>
            <el-date-picker
              v-else
              v-model="executeTime"
              type="daterange"
              @change="executeTimePicker"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              style="width:50%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="需求方:" prop="enterpriseName">
            <span>{{requestFormInfo.enterpriseName}}</span>
          </el-form-item>
          <el-form-item label="项目单位负责人:" prop="projectLeader">
            <span v-if="dialogDetails">{{requestFormInfo.projectLeader}}</span>
            <el-input v-model="requestFormInfo.projectLeader" style="width:200px;"  v-else></el-input>
          </el-form-item>
          <el-form-item label="负责人所属部门:" prop="projectLeaderDept">
            <span v-if="dialogDetails">{{requestFormInfo.projectLeaderDept}}</span>
            <el-input v-model="requestFormInfo.projectLeaderDept" style="width:200px;"  v-else></el-input>
          </el-form-item>
          <el-form-item label="集团公司负责人:" prop="groupLeader">
            <span v-if="dialogDetails">{{requestFormInfo.groupLeader}}</span>
            <el-input v-model="requestFormInfo.groupLeader" style="width:200px;"  v-else></el-input>
          </el-form-item>
          <el-form-item label="负责人所属部门:" prop="groupLeaderDept">
            <span v-if="dialogDetails">{{requestFormInfo.groupLeaderDept}}</span>
            <el-input v-model="requestFormInfo.groupLeaderDept" style="width:200px;"  v-else></el-input>
          </el-form-item>
          <el-form-item label="附件:" prop="fileList" ref="fileList">
            <el-upload
              v-if="dialogShow"
              drag
              class="upload-demo"
              :action="upLoad"
              :auto-upload="true"
              :on-success="uploadSuccess"
              :file-list="fileList"
              :show-file-list="false"
              multiple>
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            </el-upload>
            <!-- 上传附件列表 -->
            <div class="apply-upload">
              <el-row>
                <el-col :span="24">
                  <span class="file-list" v-for="(item, index) in fileList" :key="index" @click="downloadFile(item.fileName,item.relativePath)" v-if="dialogDetails">
                    <img :src="item.fileSrc" alt="">
                    <span>{{item.fileName}}</span>
                  </span>
                  <span class="file-list" v-for="(item, index) in fileList" :key="index" v-if="dialogShow">
                    <img :src="item.fileSrc" alt="">
                    <span>{{item.fileName}}</span>
                    <i class="el-icon-error upload-file" @click="clickDel(index)"></i>
                  </span>
                </el-col>
              </el-row>
            </div>
          </el-form-item>
          <el-form-item label="是否允许多次招标:" prop="noOrYes" v-if="dialogDetails">
            <span>{{requestFormInfo.isAgain | filterIsAgain}}</span>
          </el-form-item>
          <el-form-item class="app-btn-set" v-if="dialogShow">
            <el-button type="success" @click="onFormSubmit('requestFormInfo')">提交</el-button>
            <el-button type="primary" @click="onFormSave('requestFormInfo')">保存</el-button>
            <el-button @click="onFormCancel">取消</el-button>
          </el-form-item>
        </el-form>
        <!-- 审批记录start -->
        <div class="prorequest-shenpi" v-if="dialogDetails">
          <el-row :gutter="20">
            <el-col :span="3" style="text-align: left;">
              <span class="apply-name-color">审批记录:</span>
            </el-col>
            <el-col :span="21">
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
                    审批意见:<span class="step-des">{{item.approveOpinion}}</span>
                  </div>
                </el-step>
              </el-steps>
            </el-col>
          </el-row>
        </div>
        <!-- 审批记录end -->
      </el-dialog>
      <!--申报表弹窗start-->
      <el-dialog
        title="专项资金建议计划申报表"
        :visible.sync="dialogVisible"
        width="80%"
        center>
        <div style="margin-bottom: 15px;" v-if="dialogShow">
          <span>历史申报项目:</span>
          <el-select v-model="autoSearch" filterable placeholder="请选择" style="width: 198px">
            <el-option
              v-for="item in projectNameList"
              :key="item.code"
              :label="item.projectName"
              :value="item.code">
            </el-option>
          </el-select>
        </div>
        <el-form :model="dialogBaseInfoForm" ref="dialogBaseInfoForm" :rules="dialogBaseInfoRules" center>
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
                    <el-select v-model="dialogBaseInfoForm.fundPurposeFirst"  v-if="dialogShow " clearable placeholder="请选择" @change="getSecondByFirstList('dialogedit')">
                      <el-option
                        v-for="item in fundPurposeFirstList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.fundPurposeFirstName}}</span>
                  </el-form-item>
                  <span v-if="fundPurposeSecondList.length>0 && dialogBaseInfoForm.fundPurposeFirst " style="display: inline-block;float: left;line-height: 40px;">-</span>
                  <el-form-item prop="fundPurposeSecond" style="float: left" v-if="fundPurposeSecondList.length>0" >
                    <el-select v-model="dialogBaseInfoForm.fundPurposeSecond" clearable placeholder="请选择" v-if="dialogShow">
                      <el-option
                        v-for="item in fundPurposeSecondList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.fundPurposeSecondtName}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>设备在籍(台)</td>
                <td>
                  <el-form-item prop="equipmentAbsentee">
                    <el-input v-model="dialogBaseInfoForm.equipmentAbsentee"  v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.equipmentAbsentee}}</span>
                  </el-form-item>
                </td>
                <td>设备在用(台)</td>
                <td>
                  <el-form-item prop="equipmentInUse">
                    <el-input v-model="dialogBaseInfoForm.equipmentInUse" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.equipmentInUse}}</span>
                  </el-form-item>
                </td>
                <td>备用(台)</td>
                <td>
                  <el-form-item prop="equipmentReserve">
                    <el-input v-model="dialogBaseInfoForm.equipmentReserve" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.equipmentReserve}}</span>
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
                    <el-input v-model="dialogBaseInfoForm.unitType" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.unitType}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>(大修、更新)周期</td>
                <td colspan="2">
                  <el-form-item prop="maintenanceCycle">
                    <el-input v-model="dialogBaseInfoForm.maintenanceCycle" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.maintenanceCycle}}</span>
                  </el-form-item>
                </td>
                <td>上次(大修、更新)时间</td>
                <td colspan="2">
                  <el-form-item prop="lastMaintenanceTime">
                    <el-date-picker
                      style="width: 100%;"
                      v-model="dialogBaseInfoForm.lastMaintenanceTime"
                      type="date"
                      placeholder="选择日期" v-if="dialogShow">
                    </el-date-picker>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.lastMaintenanceTime  |filterDate}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>工程项目是否有方案</td>
                <td colspan="2">
                  <el-form-item prop="isPlan">
                    <el-select v-model="dialogBaseInfoForm.isPlan" placeholder="请选择" clearable  v-if="dialogShow">
                      <el-option
                        v-for="item in isPlanList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.isPlan |filterIsPlan}}</span>
                  </el-form-item>
                </td>
                <td>项目负责人</td>
                <td colspan="2">
                  <el-form-item prop="projectManager">
                    <el-input v-model="dialogBaseInfoForm.projectManager"  v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.projectManager}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>采购方式</td>
                <td colspan="2">
                  <el-form-item prop="procurementMethod">
                    <span>{{dialogBaseInfoForm.procurementMethod |filterProcurementMethod}}</span>
                  </el-form-item>
                </td>
                <td>工期</td>
                <td colspan="2">
                  <el-form-item prop="timeLimit">
                    <el-select v-model="dialogBaseInfoForm.timeLimit" placeholder="请选择" clearable  v-if="dialogShow">
                      <el-option
                        v-for="item in planLimitList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.timeLimit |filterTimeLimit}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目责任部门</td>
                <td colspan="2">
                  <el-form-item prop="responsibleDepartment">
                    <el-input v-model="dialogBaseInfoForm.responsibleDepartment"  v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.responsibleDepartment}}</span>
                  </el-form-item>
                </td>
                <td>招标时间</td>
                <td colspan="2">
                  <el-form-item prop="executionTime">
                    <el-date-picker
                      style="width:100%;"
                      v-model="dialogBaseInfoForm.executionTime"
                      type="date"
                      placeholder="选择日期"  v-if="dialogShow">
                    </el-date-picker>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.executionTime |filterDate}}</span>
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
                    <el-input v-model="dialogBaseInfoForm.reason"  v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.reason}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="5">
                  <el-form-item prop="remark">
                    <el-input v-model="dialogBaseInfoForm.remark"  v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.remark}}</span>
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
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.type'"
                    :rules="[
                      { required: true, message: '型号不能为空', trigger: 'blur'},
                      { min: 1, max: 200, message: '请输入1~200个字符'}
                    ]">
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].type" v-if="dialogShow"></el-input>
                    <!-- <el-input v-model="scope.row.type" v-if="dialogShow"></el-input> -->
                    <span v-if="!dialogShow">{{scope.row.type}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="number"
                label="数量">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.number'"
                    :rules='dialogBaseInfoRules.number'
                    class="vilitate-col">
                    <el-input v-model="scope.row.number" v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.number}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="purchaseTime"
                label="购置时间"
                :formatter="filterDates">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.purchaseTime'"
                    :rules="[{ required: true, message: '购置时间不能为空', trigger: 'change'}]">
                    <el-date-picker
                      style="width: 100%;"
                      v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].purchaseTime"
                      type="date"
                      placeholder="选择日期"
                      v-if="dialogShow">
                    </el-date-picker>
                    <span v-if="!dialogShow">{{scope.row.purchaseTime |filterDate}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="durableYears"
                label="使用年限">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.durableYears'"
                    :rules="[
                      { required: true, message: '使用年限不能为空', trigger: 'blur'},
                      { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].durableYears"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.durableYears}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="usePlace"
                label="使用地点">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.usePlace'"
                    :rules="[
                      { required: true, message: '备注不能为空', trigger: 'blur'},
                      { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].usePlace"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.usePlace}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="remark"
                label="备注">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.remark'"
                    :rules="[
                      { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].remark"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.remark}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="80"
                v-if="dialogShow">
                <template slot-scope="scope">
                  <el-button @click="deleteTableBtnClick(scope.row, 'equip')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-add-btn" v-if="dialogShow">
              <el-button icon="el-icon-plus" @click="addDiaFormTableRow('equip')" style="width:100%"></el-button>
            </div>
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
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.name'"
                    :rules="[
                      { required: true, message: '名称不能为空', trigger: 'blur'},
                      { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].name"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.name}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="specification"
                label="规格型号">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.specification'"
                    :rules="[
                      { required: true, message: '规格型号不能为空', trigger: 'blur'},
                      { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].specification"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.specification}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="unit"
                label="单位">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.unit'"
                    :rules="[
                      { required: true, message: '单位不能为空', trigger: 'blur'},
                      { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
                    ]">
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].unit"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.unit}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="amount"
                label="数量">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.amount'"
                    :rules='dialogBaseInfoRules.amount'
                    class="vilitate-col">
                    <el-input v-model="scope.row.amount"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.amount}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="unitPrice"
                label="单价">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.unitPrice'"
                    :rules='dialogBaseInfoRules.unitPrice'
                    class="vilitate-col">
                    <el-input v-model="scope.row.unitPrice"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.unitPrice}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="total"
                label="合计">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.total'"
                    :rules='dialogBaseInfoRules.total'
                    class="vilitate-col">
                    <el-input v-model="scope.row.total"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.total}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="remark"
                label="备注">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'engineerTableData.' + scope.$index + '.remark'"
                    :rules="[{ min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }]">
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].remark"  v-if="dialogShow"></el-input>
                    <span v-if="!dialogShow">{{scope.row.remark}}</span>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="80"
                v-if="dialogShow">
                <template slot-scope="scope">
                  <el-button @click="deleteTableBtnClick(scope.row, 'enginer')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-add-btn" v-if="dialogShow">
              <el-button icon="el-icon-plus" @click="addDiaFormTableRow('enginer')" style="width:100%"></el-button>
            </div>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveDeclarationForm('dialogBaseInfoForm')"  v-if="dialogShow">保存</el-button>
          <el-button @click="cancelDeclarationForm('dialogBaseInfoForm')"  v-if="dialogShow">取消</el-button>
        </span>
      </el-dialog>
      <!--申报表弹窗end-->

      <!-- 工程建设建议计划申报表start -->
      <el-dialog
        title="工程建设建议计划申报表"
        :visible.sync="enginerDialogViseble"
        width="80%"
        center>
        <el-form :model="dialogBaseInfoForm" :rules="enginerRules" ref="enginerRuleForm" class="demo-ruleForm">
          <div style="margin-bottom: 15px;" v-if="dialogShow">
            <span>历史申报项目:</span>
            <el-select v-model="autoSearch" filterable placeholder="请选择">
              <el-option
                v-for="item in projectNameList"
                :key="item.code"
                :label="item.projectName"
                :value="item.code">
              </el-option>
            </el-select>
          </div>
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
                <td>
                  <span>{{dialogBaseInfoForm.capitalSourceName}}</span>
                </td>
                <td>计划费用（万元）</td>
                <td colspan="2">{{dialogBaseInfoForm.plannedCosts}}</td>
              </tr>
              <tr>
                <td>资金投向</td>
                <td>
                  <el-form-item prop="fundPurposeFirst" style="float: left">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeFirst" v-if="dialogShow" clearable placeholder="请选择" @change="getSecondByFirstList('edit')">
                      <el-option
                        v-for="item in fundPurposeFirstList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.fundPurposeFirstName}}</span>
                  </el-form-item>
                  <span v-if="fundPurposeSecondList.length>0" style="display: inline-block;float: left;line-height: 40px;">-</span>
                  <el-form-item prop="fundPurposeSecond" style="float: left" v-if="fundPurposeSecondList.length>0">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeSecond" v-if="dialogShow" clearable placeholder="请选择">
                      <el-option
                        v-for="item in fundPurposeSecondList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.fundPurposeSecondName}}</span>
                  </el-form-item>
                </td>
                <td>工程量</td>
                <td colspan="2">{{dialogBaseInfoForm.quantities}}</td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td>
                  <el-form-item prop="projectType">
                    <span>{{dialogBaseInfoForm.projectType |filterProjectType}}</span>
                  </el-form-item>
                </td>
                <td>采购方式</td>
                <td colspan="2">
                  <el-form-item prop="procurementMethod">
                    <span>{{dialogBaseInfoForm.procurementMethod |filterProcurementMethod}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>投资主体</td>
                <td>
                  <el-form-item prop="investor">
                    <el-input v-model="dialogBaseInfoForm.investor" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.investor}}</span>
                  </el-form-item>
                </td>
                <td>建设性质</td>
                <td colspan="2">
                  <el-form-item prop="constructiveNature">
                    <el-select v-model="dialogBaseInfoForm.constructiveNature" placeholder="请选择" clearable v-if="dialogShow">
                      <el-option
                        v-for="item in constructiveList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.constructiveNature | filterNature}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>建设规模</td>
                <td>
                  <el-form-item prop="constructiveScale">
                    <el-input v-model="dialogBaseInfoForm.constructiveScale" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.constructiveScale}}</span>
                  </el-form-item>
                </td>
                <td>建设起止时间</td>
                <td colspan="2" style="line-height:40px;">
                  <el-form-item prop="constructiveStartTime" style="float:left" v-if="dialogShow">
                    <el-date-picker
                      v-model="dialogBaseInfoForm.constructiveStartTime"
                      type="date"
                      placeholder="选择开始日期">
                    </el-date-picker>
                  </el-form-item>
                  <span v-if="dialogDetails" style="display:block;float:left;">{{dialogBaseInfoForm.constructiveStartTime | filterDate}}</span>
                  <span style="display:block;float:left;">至</span>
                  <el-form-item prop="constructiveEndTime" style="float:left" v-if="dialogShow">
                    <el-date-picker
                      v-model="dialogBaseInfoForm.constructiveEndTime"
                      type="date"
                      placeholder="选择结束日期">
                    </el-date-picker>
                  </el-form-item>
                  <span v-if="dialogDetails" style="display:block;float:left;">{{dialogBaseInfoForm.constructiveEndTime | filterDate}}</span>
                </td>
              </tr>
              <tr>
                <td>建设地点</td>
                <td colspan="4">
                  <el-form-item prop="area" style="float:left;" v-if="dialogShow">
                    <el-cascader
                      :options="addressList"
                      v-model="dialogBaseInfoForm.area"
                      change-on-select>
                    </el-cascader>
                  </el-form-item>
                  <span v-if="dialogDetails" style="display:block;float:left;">{{dialogBaseInfoForm.place}}</span>
                  <el-form-item prop="address" style="float:left;width:50%;">
                    <el-input v-model="dialogBaseInfoForm.address" placeholder="详细地址" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails" style="display:block;float:left;line-height: 50px;">{{dialogBaseInfoForm.address}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>投资构成</td>
                <td style="text-align:center;background:#F6F7F9;">概算(估算)总投资</td>
                <td>{{year - 1}}年累计完成</td>
                <td style="text-align:center;background:#F6F7F9;">{{year - 1}}年预计累计完成</td>
                <td>{{year}}年投资建议计划</td>
              </tr>
              <tr>
                <td>小计</td>
                <td>{{totalForm.budgetInvestment}}</td>
                <td style="background:#fff;text-align:left;padding-left:10px;">{{totalForm.cumulativeComplete}}</td>
                <td>{{totalForm.expectedCumulativeComplete}}</td>
                <td style="background:#fff;text-align:left;padding-left:10px;">{{totalForm.investmentProposalPlan}}</td>
              </tr>
              <tr v-for="(item, index) in dialogBaseInfoForm.investmentCompositionList" :key="index">
                <td>{{index | filterName}}</td>
                <td>
                  <el-form-item :prop="'investmentCompositionList.' + index + '.budgetInvestment'"
                                :rules='enginerRules.budgetInvestment'>
                    <el-input v-model="item.budgetInvestment" @blur="calculateTotal" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{item.budgetInvestment}}</span>
                  </el-form-item>
                </td>
                <td style="background:#fff;">
                  <el-form-item :prop="'investmentCompositionList.' + index + '.cumulativeComplete'"
                                :rules='enginerRules.cumulativeComplete'>
                    <el-input v-model="item.cumulativeComplete" @blur="calculateTotal" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{item.cumulativeComplete}}</span>
                  </el-form-item>
                </td>
                <td>
                  <el-form-item :prop="'investmentCompositionList.' + index + '.expectedCumulativeComplete'"
                                :rules='enginerRules.expectedCumulativeComplete'>
                    <el-input v-model="item.expectedCumulativeComplete" @blur="calculateTotal" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{item.expectedCumulativeComplete}}</span>
                  </el-form-item>
                </td>
                <td style="background:#fff;">
                  <el-form-item :prop="'investmentCompositionList.' + index + '.investmentProposalPlan'"
                                :rules='enginerRules.investmentProposalPlan'>
                    <el-input v-model="item.investmentProposalPlan" @blur="calculateTotal" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{item.investmentProposalPlan}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>{{year}}年项目审批目标</td>
                <td colspan="4">
                  <el-form-item prop="approveTarget">
                    <el-input v-model="dialogBaseInfoForm.approveTarget" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.approveTarget}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>{{year}}年项目建设目标</td>
                <td colspan="4">
                  <el-form-item prop="constructiveTarget">
                    <el-input v-model="dialogBaseInfoForm.constructiveTarget" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.constructiveTarget}}</span>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="4">
                  <el-form-item prop="remark">
                    <el-input v-model="dialogBaseInfoForm.remark" v-if="dialogShow"></el-input>
                    <span v-if="dialogDetails">{{dialogBaseInfoForm.remark}}</span>
                  </el-form-item>
                </td>
              </tr>
            </table>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveDeclarationForm('enginerRuleForm')" v-if="dialogShow">保存</el-button>
          <el-button @click="cancelDeclarationForm" v-if="dialogShow">取消</el-button>
        </span>
      </el-dialog>
      <!-- 工程建设建议计划申报表end -->
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import {plansubmit, prorequest, investmanage, enterprise} from 'api/index'
import {commonJs, downloadFile} from 'common/js/common'
import {capitalSourceShortNameList} from 'common/js/balanceSheet'
import {formatDate} from 'common/js/date.js'
import {fileIcon} from 'common/js/base'
import {CityInfo} from 'common/js/region'
export default {
  data () {
    var checkNum = (rule, value, callback) => {
      if (value && (!(/^\d+(\.\d+)?$/).test(value))) {
        callback(new Error('计划金额必须为正数'))
      } else {
        callback()
      }
    }
    var checkQuantities = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,4}$/).test(value))) {
        callback(new Error('工程量（概算）为不超过五位的数值'))
      } else {
        callback()
      }
    }
    var checkAmount = (rule, value, callback) => {
      if (value && (!(/^\d{1,18}(\.\d{1,2})?$/).test(value))) {
        callback(new Error('概算金额为小数点前不超过十八位小数点后不超过两位的数值'))
      } else {
        callback()
      }
    }
    var checkUnitPrice = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,5}$/).test(value))) {
        callback(new Error('单价为不超过六位的数值'))
      } else {
        callback()
      }
    }
    var checkTotal = (rule, value, callback) => {
      if (value && (!(/^[1-9]\d{0,9}$/).test(value))) {
        callback(new Error('合计为不超过十位的数值'))
      } else {
        callback()
      }
    }
    return {
      addressList: CityInfo,
      enginerDialogViseble: false, // 工程建设建议计划申报表-弹窗
      totalForm: {}, // 小计
      enginerRules: {
        fundPurposeFirst: { required: true, message: '请选择资金投向', trigger: ['blur', 'change'] },
        fundPurposeSecond: { required: true, message: '请选择资金投向', trigger: ['blur', 'change'] },
        investor: [
          { required: true, message: '请填写投资主体', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~100个字符', trigger: 'blur' }
        ],
        constructiveNature: [
          { required: true, message: '请选择建设性质', trigger: ['blur', 'change'] }
        ],
        constructiveScale: [
          { required: true, message: '请填写建设规模', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        constructiveStartTime: [
          { required: true, message: '请选择建开始时间', trigger: 'change' }
        ],
        constructiveEndTime: [
          { required: true, message: '请选择建结束时间', trigger: 'change' }
        ],
        area: [
          { required: true, message: '请选择地址', trigger: ['blur', 'change'] }
        ],
        address: [
          { required: true, message: '请填写地址详情', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        approveTarget: [
          { required: true, message: '请填写项目审核目标', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        constructiveTarget: [
          { required: true, message: '请填写项目建设目标', trigger: 'blur' },
          { min: 1, max: 100, message: '请输入1~500个字符', trigger: 'blur' }
        ],
        budgetInvestment: [
          {validator: checkAmount}
        ],
        cumulativeComplete: [
          {validator: checkAmount}
        ],
        expectedCumulativeComplete: [
          {validator: checkAmount}
        ],
        investmentProposalPlan: [
          {validator: checkAmount}
        ]
      }, // 工程建设建议计划申报表-校验规则
      autoSearch: '', // 搜索-弹窗
      projectNameList: [],
      projectPlanCode: '', // 招标计划code
      suggestedPlan: {}, // 项目信息
      dialogVisible: false, // 专项资金建议计划申报表-弹窗
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [], // 工程主要内容-表格
        investmentCompositionList: [
          {}, {}, {}, {}, {}
        ],
      },
      projectNameRules: {
        projectName: [
          { required: true, message: '请填写项目名称', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 项目名称校验
      // 项目名称校验
      contentRules: {
        content: [
          // { required: true, message: '请填写工程简要内容及设备型号', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 工程内容
      quantitiesRules: {
        quantitiesUnit: [
          // { required: true, message: '请填写工程量单位', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ],
        currentPlanQuantities: [
          // { required: true, message: '请填写本年计划工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ]
      }, // 工程量（本年计划）
      currentPlanAmountRules: {
        currentPlanAmount: [
          { required: true, message: '请填写本年计划金额', trigger: 'blur' },
          {validator: checkAmount}
        ]
      }, // 本年计划金额
      capitalSourceRules: {
        capitalSource: [
          { required: true, message: '请选择资金来源', trigger: 'blur' }
        ]
      }, // 本年计划金额
      dialogBaseInfoRules: {
        /* fundPurposeFirst: { required: true, message: '请选择资金投向', trigger: 'blur' },
        fundPurposeSecond: { required: true, message: '请选择资金投向', trigger: 'blur' }, */
        equipmentAbsentee: {validator: checkQuantities},
        equipmentInUse: {validator: checkQuantities},
        equipmentReserve: {validator: checkQuantities},
        quantities: [
          // { required: true, message: '请填写工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ],
        unitType: [
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        maintenanceCycle: [
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        isPlan: { required: true, message: '请选择工程项目是否有方案', trigger: 'change' },
        projectManager: [
          { required: true, message: '请填写项目责任人', trigger: 'blur' },
          { min: 1, max: 20, message: '请输入1~20个字符', trigger: 'blur' }
        ],
        procurementMethod: { required: true, message: '请选择采购方式', trigger: 'change' },
        timeLimit: { required: true, message: '请选择工期', trigger: 'change' },
        responsibleDepartment: [
          { required: true, message: '请填写项目责任部门', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        executionTime: { required: true, message: '请选择拟执行时间', trigger: 'change' },
        projectType: { required: true, message: '请选择项目类型', trigger: 'change' },
        reason: [
          { required: true, message: '请填写提出原因', trigger: 'blur' },
          { min: 10, max: 200, message: '请输入10~200个字符', trigger: 'blur' }
        ],
        remark: { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' },
        number: [
          { required: true, message: '请填写数量', trigger: 'blur' },
          {validator: checkQuantities}
        ],
        amount: [
          { required: true, message: '请填写数量', trigger: 'blur' },
          {validator: checkQuantities}
        ],
        unitPrice: [
          { required: true, message: '请填写单价', trigger: 'blur' },
          {validator: checkUnitPrice}
        ],
        total: [
          { required: true, message: '请填写合计', trigger: 'blur' },
          {validator: checkTotal}
        ]
      },
      fundPurposeFirstList: [], // 资金投向 - 一级
      fundPurposeSecondList: [], // 资金投向 - 二级
      isPlanList: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ], // 申报表--是否有方案
      bidTypeList: [
        {
          label: '公开招标',
          value: 1
        },
        {
          label: '邀请招标',
          value: 2
        },
        {
          label: '谈判采购',
          value: 3
        },
        {
          label: '询比采购',
          value: 4
        },
        {
          label: '竞价采购',
          value: 5
        },
        {
          label: '直接采购',
          value: 6
        },
        {
          label: '框架协议',
          value: 7
        }], // 申报表--采购方式
      projectTypeList: [
        {
          label: '工程',
          value: 1
        },
        {
          label: '货物',
          value: 2
        },
        {
          label: '服务',
          value: 3
        }], // 申报表--项目类型
      planAmountRules: [
        {required: true, message: '请填写计划金额', trigger: 'blur'},
        {validator: checkNum}
      ],
      approveRecordList: [], // 审批记录
      dialogShow: true,
      dialogDetails: false, // 查看详情显示隐藏
      dialogRequestTitle: '新增',
      dialogRequestVisible: false, // 新增修改弹框
      requestFormInfo: {
        year: new Date().getFullYear(),
        requestTableData: []
      }, // 弹窗表单对象
      planTotalAmount: 0,
      bidTime: null, // 拟招标时间
      executeTime: null, // 拟实施时间
      bidStartTime: null, // 拟招标开始时间
      bidEndTime: null, // 拟招标结束时间
      executeStartTime: null, // 拟实施开始时间
      executeEndTime: null, // 拟实施结束时间
      submitStartTime: null, // 提交开始时间
      submitEndTime: null, // 提交结束时间
      approveStartTime: null, // 审批开始时间
      approveEndTime: null, // 审批结束时间
      upLoad: commonJs.fileUploadUrl,
      fileList: [],
      year: new Date().getFullYear(),
      enterpriseName: '',
      enterpriseClassify: '',
      // 计划工期
      planLimitList: [
        {
          label: '1个月',
          value: '1'
        },
        {
          label: '2个月',
          value: '2'
        },
        {
          label: '3个月',
          value: '3'
        },
        {
          label: '4个月',
          value: '4'
        },
        {
          label: '5个月',
          value: '5'
        },
        {
          label: '6个月',
          value: '6'
        },
        {
          label: '7个月',
          value: '7'
        },
        {
          label: '8个月',
          value: '8'
        },
        {
          label: '9个月',
          value: '9'
        },
        {
          label: '10个月',
          value: '10'
        },
        {
          label: '11个月',
          value: '11'
        },
        {
          label: '12个月',
          value: '12'
        },
        {
          label: '13个月',
          value: '13'
        },
        {
          label: '14个月',
          value: '14'
        },
        {
          label: '15个月',
          value: '15'
        },
        {
          label: '16个月',
          value: '16'
        },
        {
          label: '17个月',
          value: '17'
        },
        {
          label: '18个月',
          value: '18'
        },
        {
          label: '19个月',
          value: '19'
        },
        {
          label: '20个月',
          value: '20'
        },
        {
          label: '21个月',
          value: '21'
        },
        {
          label: '22个月',
          value: '22'
        },
        {
          label: '23个月',
          value: '23'
        },
        {
          label: '24个月',
          value: '24'
        }
      ],
      // 状态
      statusList: [
        {
          label: '未提交',
          value: 1
        },
        {
          label: '审批中',
          value: 2
        },
        {
          label: '通过',
          value: 3
        },
        {
          label: '退回',
          value: 4
        },
        {
          label: '终止',
          value: 5
        }],
      // 资金来源
      capitalSourceNameList: [],
      rules: {
        projectPlanName: [
          { required: true, message: '请填写招标计划名称', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        capitalSource: [
          { required: true, message: '请填写资金来源', trigger: 'change' }
        ],
        planLimit: [
          { required: true, message: '请填写计划工期', trigger: 'change' }
        ],
        bidTime: [
          { required: true, message: '请选择拟招标时间', trigger: 'change' }
        ],
        executeTime: [
          { required: true, message: '请选择拟实施时间', trigger: 'change' }
        ],
        bidType: [
          { required: true, message: '请选择拟招标方式', trigger: 'change' }
        ],
        projectType: [
          { required: true, message: '请选择项目类型', trigger: 'change' }
        ],
        project: [
          { required: true, message: '请填写项目详情', trigger: 'blur' }
        ],
        projectLeader: [
          { required: true, message: '请输入项目单位负责人名称', trigger: 'blur' }
        ],
        projectLeaderDept: [
          { required: true, message: '请输入负责人所属部门名称', trigger: 'blur' }
        ],
        groupLeader: [
          { required: true, message: '请输入集团公司负责人名称', trigger: 'blur' }
        ],
        groupLeaderDept: [
          { required: true, message: '请输入负责人所属部门名称', trigger: 'blur' }
        ],
        fileList: [
          {required: true, message: '请添加附件', trigger: 'blur'}
        ]
      },
      tableData: [{year: 2019}],
      newRowCont: '',
      yearList: [],
      ruleSearchForm: {}, // 搜索表单
      formInfo: {}, // 弹窗表单
      total: null,
      pageNo: 0,
      pageSize: 10,
      currentPage: 1,
      pageSizeList: [10, 20, 30, 40, 50],
      dialogAdd: false,
      isSave: false,
      constructiveList: [
        {label: '新建', value: 1},
        {label: '扩建', value: 2},
        {label: '改建', value: 3},
        {label: '迁建', value: 4},
        {label: '恢复重建', value: 5},
        {label: '技改', value: 6}
      ]
    }
  },
  watch: {
    // 对象
    requestFormInfo: {
      handler (newValue, oldValue) {
        let num = 0
        this.requestFormInfo.requestTableData.map((item) => {
          num += Number(item.currentPlanAmount)
        })
        this.$set(this.requestFormInfo, 'planTotalAmount', num)
      },
      deep: true
    },
    // 字符串
    autoSearch: function (newValue, oldValue) {
      if (newValue) {
        let obj = {
          code: newValue
        }
        investmanage.getDeclarationForm(obj).then((res) => {
          if (res) {
            let declarationForm = res.data.declarationForm
            declarationForm.suggestedPlanCode = this.dialogBaseInfoForm.suggestedPlanCode
            declarationForm.projectType = this.requestFormInfo.projectType
            declarationForm.procurementMethod = this.requestFormInfo.bidType
            declarationForm.objectId = this.dialogBaseInfoForm.objectId
            declarationForm.code = this.dialogBaseInfoForm.code
            declarationForm.projectName = this.dialogBaseInfoForm.projectName
            declarationForm.plannedCosts = this.dialogBaseInfoForm.plannedCosts
            declarationForm.quantities = this.dialogBaseInfoForm.quantities
            this.dialogBaseInfoForm = declarationForm
            this.dialogBaseInfoForm.equipmentTableData = res.data.declarationForm.equipmentActualityList
            this.dialogBaseInfoForm.engineerTableData = res.data.declarationForm.accessoriesList
            this.dialogBaseInfoForm.area = [this.dialogBaseInfoForm.provinceId, this.dialogBaseInfoForm.cityId, this.dialogBaseInfoForm.countyId]
            let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
            let newInvestmentCompositionList = [{}, {}, {}, {}, {}]
            investmentCompositionList.map(item => {
              newInvestmentCompositionList[item.type - 1] = item
            })
            this.$set(this.dialogBaseInfoForm, 'investmentCompositionList', newInvestmentCompositionList)
            this.calculateTotal()
          }
        })
      }
    }
  },
  created () {
    this.getYearMethed()
    this.getAunnalStatus()
  },
  filters: {
    filterName (val) {
      if (val === 0) {
        return '矿建工程'
      } else if (val === 1) {
        return '土建工程'
      } else if (val === 2) {
        return '安装工程'
      } else if (val === 3) {
        return '设备购置'
      } else if (val === 4) {
        return '其他费用'
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
    filterPlanLimit (val) {
      let planLimit = val
      if (planLimit !== '' && planLimit !== null) {
        return planLimit + '个月'
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
    },
    filterBidType (val) {
      let bidType = val
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
    filterBidTime (val) {
      if (!val) {
        return ''
      } else {
        let bidStartTime = new Date(val[0])
        let bidEndTime = new Date(val[1])
        return formatDate(bidStartTime, 'yyyy-MM-dd') + ' 至 ' + formatDate(bidEndTime, 'yyyy-MM-dd')
      }
    },
    filterExecuteTime (val) {
      if (!val) {
        return ''
      } else {
        let executeStartTime = new Date(val[0])
        let executeEndTime = new Date(val[1])
        return formatDate(executeStartTime, 'yyyy-MM-dd') + ' 至 ' + formatDate(executeEndTime, 'yyyy-MM-dd')
      }
    },
    filterIsAgain (val) {
      let isAgain = val
      if (isAgain === 0) {
        return '否'
      } else if (isAgain === 1) {
        return '是'
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
      if (!val) {
        return ''
      } else {
        let date = new Date(val)
        return formatDate(date, 'yyyy-MM-dd')
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
    /** 行 */
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 下载 */
    downloadFile,
    /** 状态-审批状态：0.发起审批申请 1.通过 2.退回 3.终止 */
    fliteredStatus (status) {
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
    /** 检验附件 */
    validFile () {
      if (this.fileList.length > 0) {
        this.$set(this.requestFormInfo, 'fileList', 'fileList')
      } else {
        this.$set(this.requestFormInfo, 'fileList', '')
      }
      if (this.requestFormInfo.fileList) {
        this.$refs['fileList'].clearValidate()
      }
    },
    /** 删除上传附件 */
    clickDel (index) {
      this.fileList.splice(index, 1)
      this.validFile()
    },
    /** 上传附件 */
    uploadSuccess (response, file, fileList) {
      if (fileList.length > 0) {
        let fileInformations = []
        let existFileSize = 0
        //  得到已经保存的附件信息
        for (var i = 0; i < fileList.length; i++) {
          if (!fileList[i].response) {
            existFileSize++
            fileInformations[i] = fileList[i]
          }
        }
        // 添加新的附件信息
        if (existFileSize < fileList.length) {
          for (var j = existFileSize; j < fileList.length; j++) {
            if (fileList[j].response) {
              fileInformations[j] = fileList[j].response.fileInformation
            }
          }
        }
        this.fileList = fileInformations // 获得目前项目中所有的附件信息
        this.getUploadFileType(this.fileList)
      }
      this.validFile()
    },
    /** 格式化表格数据 */
    filterSubDate (row, column) {
      if (!row.submitTime) {
        return ''
      } else {
        let date = new Date(row.submitTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    filterAppDate (row, column) {
      if (!row.approveTime) {
        return ''
      } else {
        let date = new Date(row.approveTime)
        return formatDate(date, 'yyyy-MM-dd')
      }
    },
    filterSubmitTime (value) {
      if (value) {
        this.submitStartTime = value[0]
        this.submitEndTime = value[1]
      } else {
        this.submitStartTime = null
        this.submitEndTime = null
      }
    },
    filterApplyTime (value) {
      if (value) {
        this.approveStartTime = value[0]
        this.approveEndTime = value[1]
      } else {
        this.approveStartTime = null
        this.approveEndTime = null
      }
    },
    filterStatus (row, column) {
      let status = row.status
      if (status === 1) {
        return '未提交'
      } else if (status === 2) {
        return '审批中'
      } else if (status === 3) {
        return '通过'
      } else if (status === 4) {
        return '退回'
      } else if (status === 5) {
        return '终止'
      }
    },
    bidTimePicker (value) {
      if (value) {
        this.bidTime = value
        this.requestFormInfo.bidTime = value
        this.bidStartTime = value[0]
        this.bidEndTime = value[1]
      } else {
        this.bidStartTime = null
        this.bidEndTime = null
      }
    },
    executeTimePicker (value) {
      if (value) {
        this.executeTime = value
        this.requestFormInfo.executeTime = value
        this.executeStartTime = value[0]
        this.executeEndTime = value[1]
      } else {
        this.executeStartTime = null
        this.executeEndTime = null
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
    /** 弹框-表格新增行 */
    addDialogTableRow () {
      let obj = {
        projectName: '',
        content: '',
        currentPlanQuantities: '',
        currentPlanAmount: '',
        capitalSource: '',
        projectPlanCode: this.projectPlanCode || ''
      }
      this.requestFormInfo.requestTableData.push(obj)
    },
    /** 操作后返回当前页 */
    indexMethod (index) {
      return index + (this.currentPage - 1) * this.pageSize + 1
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getProjectPlanList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getProjectPlanList(this.pageNo)
    },
    /**  表格操作 */
    handleTableClick (row, type, index) {
      if (type === 'edit') {
        this.getCapitalSourceList()
        this.dialogRequestVisible = true
        this.dialogRequestTitle = '编辑'
        this.dialogDetails = false
        this.dialogShow = true
        this.getProjectPlan(row)
        this.projectPlanCode = row.code
      } else if (type === 'look') {
        // 查看
        this.dialogRequestVisible = true
        this.dialogRequestTitle = '查看详情'
        this.dialogDetails = true
        this.dialogShow = false
        this.getProjectPlan(row)
      } else if (type === 'del') {
        // 删除
        this.deleteClick(row)
      } else if (type === 'dialogdel') {
        this.deleteProjectClick(row)
      } else if (Object.is(type, 'dialogedit')) {
        // 打开弹窗将历史申报与申报表表单和里面的数组置空
        this.autoSearch = ''
        this.dialogBaseInfoForm = {
          equipmentTableData: [], // 设备现有状况-表格
          engineerTableData: [], // 工程主要内容-表格
          investmentCompositionList: [
            {}, {}, {}, {}, {}
          ]
        }
        this.totalForm = {} // 小计
        this.suggestedPlan = row
        // 查询历史申报表信息
        this.getDeclarationFormList(row)
        // 查询此条项目下是否有申报表信息
        this.getDeclarationForm(row, 'dialogedit')
        // 打开弹窗
        if (row.classify === 1) {
          // 专项资金
          this.dialogVisible = true
        } else {
          // 工程建设
          this.enginerDialogViseble = true
        }
      } else if (Object.is(type, 'dialogsave')) {
        this.saveProjectClick(row, index)
      }
    },
    /** 如果没有数据就执行 */
    getDialogFormInfo (row) {
      if (this.dialogBaseInfoForm && !this.dialogBaseInfoForm.code) {
        investmanage.getDeclarationForm({suggestedPlanCode: row.code}).then((res) => {
          this.dialogBaseInfoForm = res.data.declarationForm
          if (!this.dialogBaseInfoForm) {
            this.dialogBaseInfoForm = {
              equipmentTableData: [], // 设备现有状况-表格
              engineerTableData: [], // 工程主要内容-表格
              investmentCompositionList: [
                {}, {}, {}, {}, {}
              ]
            }
            this.dialogBaseInfoForm.projectType = this.requestFormInfo.projectType
            this.dialogBaseInfoForm.procurementMethod = this.requestFormInfo.bidType
            this.dialogBaseInfoForm.suggestedPlanCode = row.code
            this.dialogBaseInfoForm.projectName = row.projectName
            this.dialogBaseInfoForm.plannedCosts = row.currentPlanAmount
            this.dialogBaseInfoForm.quantities = row.currentPlanQuantities
            if (row.capitalSource) {
              this.dialogBaseInfoForm.capitalSource = row.capitalSource
              this.getCapitalSourceName(row.capitalSource)
            }
            this.getFundPurposeFirstList()
          }
        })
      }
    },
    /** 查询招标计划详情 */
    getProjectPlan (row) {
      prorequest.getProjectPlan(row.objectId).then((res) => {
        if (res.data.projectPlan) {
          this.requestFormInfo = res.data.projectPlan
          this.requestFormInfo.requestTableData = res.data.projectPlan.projectList
          let projectList = res.data.projectPlan.projectList
          if (projectList) {
            for (let i = 0; i < projectList.length; i++) {
              this.requestFormInfo.requestTableData[i].currentPlanAmount = Number(projectList[i].currentPlanAmount)
            }
          }
          let bidTime = []
          bidTime[0] = this.requestFormInfo.bidStartTime
          bidTime[1] = this.requestFormInfo.bidEndTime
          this.bidTime = bidTime
          this.requestFormInfo.bidTime = bidTime
          let executeTime = []
          executeTime[0] = this.requestFormInfo.executeStartTime
          executeTime[1] = this.requestFormInfo.executeEndTime
          this.executeTime = executeTime
          this.requestFormInfo.executeTime = executeTime
          this.requestFormInfo.enterpriseName = this.enterpriseName
          // 查看页面展示所有审批记录
          this.approveRecordList = this.requestFormInfo.approveRecordList
          if (this.requestFormInfo.fileInformationList) {
            if (this.requestFormInfo.fileInformationList.length > 0) {
              let fileInformations = []
              for (var i = 0; i < this.requestFormInfo.fileInformationList.length; i++) {
                fileInformations[i] = this.requestFormInfo.fileInformationList[i]
              }
              this.fileList = fileInformations // 获得目前项目中所有的附件信息
            }
            //  附件文件信息
            this.getUploadFileType(this.requestFormInfo.fileInformationList)
          } else {
            return false
          }
        } else {
          return false
        }
      })
    },
    /** 新增 */
    addClickBtn () {
      this.getCapitalSourceList()
      this.projectPlanCode = ''
      this.dialogRequestVisible = true
      this.dialogRequestTitle = '新增'
      this.dialogDetails = false
      this.dialogShow = true
    },
    /** 弹窗-取消 */
    onFormCancel () {
      this.$refs['requestFormInfo'].resetFields()
      this.requestFormInfo = {
        requestTableData: []
      }
      this.suggestedPlan = {}
      this.bidTime = []
      this.executeTime = []
      this.fileList = []
      this.requestFormInfo.year = this.year
      this.requestFormInfo.enterpriseName = this.enterpriseName
      this.dialogRequestVisible = false
    },
    /** 弹窗-提交 */
    onFormSubmit (val) {
      this.requestFormInfo.flag = 1
      this.save(val)
    },
    /** 弹窗-保存 */
    onFormSave (val) {
      this.requestFormInfo.flag = 0
      this.save(val)
    },
    /** 取消--申报表 */
    cancelDeclarationForm () {
      // this.$refs['dialogBaseInfoForm'].resetFields()
      this.dialogBaseInfoForm = {
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [], // 工程主要内容-表格
        investmentCompositionList: [
          {}, {}, {}, {}, {}
        ]
      }
      this.suggestedPlan = {}
      this.totalForm = {} // 小计
      this.autoSearch = ''
      this.dialogVisible = false
      this.enginerDialogViseble = false
    },
    /** 保存-申请表 */
    saveDeclarationForm (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (!this.dialogBaseInfoForm.fundPurposeSecond) {
            this.dialogBaseInfoForm.fundPurposeSecond = null
          }
          // 设备现有状况
          this.$set(this.dialogBaseInfoForm, 'equipmentActualityList', this.dialogBaseInfoForm.equipmentTableData)
          // 备件主材
          this.$set(this.dialogBaseInfoForm, 'accessoriesList', this.dialogBaseInfoForm.engineerTableData)
          // 建设地点
          if (this.dialogBaseInfoForm.area) {
            this.dialogBaseInfoForm.provinceId = this.dialogBaseInfoForm.area[0]
            this.dialogBaseInfoForm.cityId = this.dialogBaseInfoForm.area[1]
            this.dialogBaseInfoForm.countyId = this.dialogBaseInfoForm.area[2]
          }
          // 投资构成
          let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
          // 非空对象集合
          let newInvestmentCompositionList = []
          investmentCompositionList.map((item, index) => {
            if (Object.keys(item).length) {
              item.type = index + 1
              newInvestmentCompositionList.push(item)
            }
          })
          this.$set(this.dialogBaseInfoForm, 'investmentCompositionList', newInvestmentCompositionList)
          this.dialogBaseInfoForm.objectId = null
          this.dialogBaseInfoForm.projectType = this.requestFormInfo.projectType
          investmanage.saveDeclarationForm(this.dialogBaseInfoForm).then((res) => {
            this.dialogBaseInfoForm = res.data.declarationForm
            this.dialogBaseInfoForm.equipmentTableData = this.dialogBaseInfoForm.equipmentActualityList
            this.dialogBaseInfoForm.engineerTableData = this.dialogBaseInfoForm.accessoriesList
            this.dialogVisible = false
            this.enginerDialogViseble = false
            this.updateSuggestedPlan(this.dialogBaseInfoForm)
          })
        }
      })
    },
    /** 保存申报表后--更新项目 */
    updateSuggestedPlan (declarationForm) {
      this.suggestedPlan.fundPurposeFirst = declarationForm.fundPurposeFirst
      this.suggestedPlan.currentPlanQuantities = declarationForm.quantities
      if (declarationForm.fundPurposeSecond) {
        this.suggestedPlan.fundPurposeSecond = declarationForm.fundPurposeSecond
      } else {
        this.suggestedPlan.fundPurposeSecond = ''
      }
      this.suggestedPlan.declarationForm = null
      return investmanage.saveSuggestedPlan(this.suggestedPlan).then((res) => {
        this.getSuggestedPlanList()
      })
    },
    /** 查询项目计划 */
    getSuggestedPlanList () {
      let projectLeader = []
      if (this.requestFormInfo.projectLeader) {
        projectLeader = this.requestFormInfo.projectLeader.split('、')
      }
      let projectLeaderDept = []
      if (this.requestFormInfo.projectLeaderDept) {
        projectLeaderDept = this.requestFormInfo.projectLeaderDept.split('、')
      }
      let obj = {
        projectPlanCode: this.projectPlanCode,
        isInRecycleBin: 0
      }
      investmanage.getSuggestedPlanList(obj).then((res) => {
        if (res) {
          this.requestFormInfo.requestTableData = res.data.suggestedPlanList
        }
        for (let i = 0; i < this.requestFormInfo.requestTableData.length; i++) {
          let declarationForm = this.requestFormInfo.requestTableData[i].declarationForm
          if (declarationForm) {
            if (declarationForm.projectManager) {
              if (!projectLeader.includes(declarationForm.projectManager)) {
                projectLeader.push(declarationForm.projectManager)
              }
            }
            if (declarationForm.responsibleDepartment) {
              if (!projectLeaderDept.includes(declarationForm.responsibleDepartment)) {
                projectLeaderDept.push(declarationForm.responsibleDepartment)
              }
            }
            let projectLeaderStr = ''
            projectLeader.map(item => {
              if (!projectLeaderStr) {
                projectLeaderStr = item
              } else {
                projectLeaderStr += `、${item}`
              }
            })
            this.requestFormInfo.projectLeader = projectLeaderStr
            let projectLeaderDeptStr = ''
            projectLeaderDept.map(item => {
              if (!projectLeaderDeptStr) {
                projectLeaderDeptStr = item
              } else {
                projectLeaderDeptStr += `、${item}`
              }
            })
            this.requestFormInfo.projectLeader = projectLeaderStr
            this.requestFormInfo.projectLeaderDept = projectLeaderDeptStr
          }
        }
      })
    },
    validProject () {
      if (this.requestFormInfo.requestTableData.length > 0) {
        this.$set(this.requestFormInfo, 'project', 'project')
      } else {
        this.$set(this.requestFormInfo, 'project', '')
      }
      if (this.requestFormInfo.project) {
        this.$refs['projectForm'].clearValidate()
      }
    },
    /** 表格校验 */
    validateForm (formName, index) {
      let result = false
      this.$refs[formName + index].validate((valid) => {
        if (valid) {
          result = true
        } else {
          result = false
        }
      })
      return result
    },
    /** 弹窗-新增/修改 */
    save (val) {
      this.validProject()
      this.validFile()
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          this.requestFormInfo.code = this.projectPlanCode
          this.requestFormInfo.bidStartTime = this.bidStartTime
          this.requestFormInfo.bidEndTime = this.bidEndTime
          this.requestFormInfo.executeStartTime = this.executeStartTime
          this.requestFormInfo.executeEndTime = this.executeEndTime
          this.requestFormInfo.projectPlanType = 3
          if (this.requestFormInfo.requestTableData) {
            this.requestFormInfo.projectList = this.requestFormInfo.requestTableData
          }
          let fileInformationList = []
          if (this.fileList.length > 0) {
            for (var i = 0; i < this.fileList.length; i++) {
              fileInformationList[i] = this.fileList[i]
            }
          }
          this.requestFormInfo.fileInformationList = fileInformationList
          if (this.requestFormInfo.projectList) {
            if (this.requestFormInfo.projectList.length > 0) {
              for (let i = 0; i < this.requestFormInfo.projectList.length; i++) {
                if (this.requestFormInfo.projectList[i].declarationForm) {
                  this.isSave = true
                } else {
                  this.isSave = false
                  this.$message.warning('此项目下无申报表信息,不可保存！')
                }
              }
              if (this.isSave === true) {
                plansubmit.saveProjectPlan(this.requestFormInfo).then(() => {
                  this.dialogRequestVisible = false
                  this.getProjectPlanList(this.pageNo)
                })
              } else if (this.isSave === false) {
                this.$message.warning('此项目下无申报表信息,不可保存！')
              }
            }
          }
        }
      })
    },
    /** 保存--项目 */
    saveProjectClick (formName, index) {
      let projectNameValidate = this.validateForm('projectNameForm', index)
      /* let contentValidate = this.validateForm('contentForm', index)
      let currentPlanQuantitiesValidate = this.validateForm('quantitiesForm', index)
      let quantitiesUnitValidate = this.validateForm('quantitiesUnitForm', index) */
      let currentPlanAmountValidate = this.validateForm('currentPlanAmountForm', index)
      let capitalSourceValidate = this.validateForm('capitalSourceForm', index)
      if (formName.declarationForm) {
        formName.declarationForm.projectName = formName.projectName
        formName.declarationForm.plannedCosts = formName.currentPlanAmount
        formName.declarationForm.quantities = formName.currentPlanQuantities
      }
      formName.projectType = this.requestFormInfo.projectType
      formName.projectCreateType = 3
      if (projectNameValidate && currentPlanAmountValidate && capitalSourceValidate) {
        investmanage.saveSuggestedPlan(formName).then((res) => {
          if (res) {
            if (res.data.projectPlanCode) {
              this.projectPlanCode = res.data.projectPlanCode
            }
            this.requestFormInfo.requestTableData.splice(index, 1, res.data.suggestedPlan)
            this.suggestedPlan = res.data.suggestedPlan
          }
        })
      }
    },
    /** 删除--项目 */
    deleteProjectClick (row) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (row.objectId) {
          investmanage.deleteSuggestedPlan(row.objectId).then(() => {
            this.requestFormInfo.requestTableData.splice(this.requestFormInfo.requestTableData.indexOf(row), 1)
            this.getSuggestedPlanList()
          })
        } else {
          this.requestFormInfo.requestTableData.splice(this.requestFormInfo.requestTableData.indexOf(row), 1)
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 删除--招标项目 */
    deleteClick (data) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        plansubmit.delete(data).then(() => {
          this.getProjectPlanList(this.pageNo)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 搜索 */
    onSearch (ruleSearchForm) {
      this.pageNo = 0
      this.currentPage = 1
      this.getProjectPlanList(this.pageNo)
    },
    /** 列表数据展示 */
    getProjectPlanList (pageNo) {
      // 查找招标计划项目信息
      let obj = {
        pageNo: pageNo,
        pageSize: this.pageSize,
        year: this.ruleSearchForm.year || null,
        status: this.ruleSearchForm.status || null,
        enterpriseLike: this.ruleSearchForm.enterpriseName || null,
        requiresEnterpriseId: this.$store.getters.authUser.enterpriseId,
        projectPlanType: 3
      }
      this.getList(obj)
    },
    getList (obj) {
      plansubmit.getProjectPlan(obj).then((res) => {
        this.requestFormInfo.enterpriseName = this.$store.getters.authUser.enterpriseName
        this.enterpriseName = this.$store.getters.authUser.enterpriseName
        if (res.data.projectPlanList) {
          this.tableData = res.data.projectPlanList.list
          this.total = res.data.projectPlanList.total
          // 列表展示审批通过的相关信息
          if (this.tableData) {
            for (let i = 0; i < this.tableData.length; i++) {
              if (this.tableData[i].approveRecordList) {
                for (let j = 0; j < this.tableData[i].approveRecordList.length; j++) {
                  if (this.tableData[i].approveRecordList[j].approveResult === 1 || this.tableData[i].approveRecordList[j].approveResult === 3) {
                    this.tableData[i].approveOrganization = this.tableData[i].approveRecordList[j].approveOrganization
                    this.tableData[i].approveName = this.tableData[i].approveRecordList[j].approveName
                    this.tableData[i].approveTime = this.tableData[i].approveRecordList[j].approveTime
                  }
                }
              }
            }
          }
        } else {
          return false
        }
      })
    },
    /** 根据附件类型确认显示图标 */
    getUploadFileType (fileList) {
      if (fileList.length > 0) {
        fileList.forEach(item => {
          item.fileSrc = ''
          if (/(doc|docx|DOC|DOCX)$/.test(item.extName)) {
            item.fileSrc = fileIcon.doc
          } else if (/(xls|xlsx|XLS|XLSX)$/.test(item.extName)) {
            item.fileSrc = fileIcon.excel
          } else if (/(pdf|PDF)$/.test(item.extName)) {
            item.fileSrc = fileIcon.pdf
          } else if (/(txt|TXT)$/.test(item.extName)) {
            item.fileSrc = fileIcon.txt
          } else if (/(rar|RAR)$/.test(item.extName)) {
            item.fileSrc = fileIcon.rar
          } else if (/(zip|ZIP)$/.test(item.extName)) {
            item.fileSrc = fileIcon.zip
          } else if (/(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(item.extName)) {
            item.fileSrc = fileIcon.picture
          } else {
            item.fileSrc = fileIcon.unknow
          }
        })
      }
    },
    /** 项目--查询资金来源 */
    getCapitalSourceList () {
      enterprise.getOne(this.$store.getters.authUser.enterpriseId).then((res) => {
        this.enterpriseClassify = res.data.enterprise.enterpriseClassify
        if (this.enterpriseClassify) {
          investmanage.selectCapitalSourceByEnterpriseType(Number(this.enterpriseClassify)).then((res) => {
            let CapitalSourceList = res.data.enterpriseTypeCapitalSourceList
            if (CapitalSourceList) {
              let info = []
              for (let i = 0; i < CapitalSourceList.length; i++) {
                let obj = {}
                if (CapitalSourceList[i].capitalSourceShortName !== capitalSourceShortNameList[0]) {
                  obj.value = CapitalSourceList[i].capitalSourceId
                  obj.label = CapitalSourceList[i].capitalSourceName
                  info.push(obj)
                }
              }
              this.capitalSourceNameList = info
            }
          })
        } else {
          let obj = {
            isDelete: 0,
            isAvailableFunds: 1
          }
          investmanage.getCapitalSourceList(obj).then((res) => {
            if (res.data.capitalSourceList) {
              let info = []
              for (let i = 0; i < res.data.capitalSourceList.length; i++) {
                let obj = {}
                if (res.data.capitalSourceList[i].shortName !== capitalSourceShortNameList[0]) {
                  obj.value = res.data.capitalSourceList[i].objectId
                  obj.label = res.data.capitalSourceList[i].name
                  info.push(obj)
                }
              }
              this.capitalSourceNameList = info
            }
          })
        }
      })
    },
    /** 申报表--查询资金来源 */
    getCapitalSourceName (id) {
      investmanage.getOneCapitalSource(id).then((res) => {
        if (res) {
          this.dialogBaseInfoForm.capitalSourceName = res.data.capitalSource.name
        }
        if (res.data.capitalSource.classify === 2) {
          this.getFundPurposeFirstList(res.data.capitalSource.classify)
        } else {
          this.getFundPurposeFirstList(res.data.capitalSource.classify, id)
        }
      })
    },
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList (type, id) {
      let query = {
        level: 1,
        parentId: 0,
        capitalSourceId: id,
        type: type
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeFirstList = []
        this.fundPurposeFirstList = res.data.fundPurposeList
      })
    },
    /** 查询资金投向 - 二级 */
    getSecondByFirstList (operate) {
      let parentId = null
      if (Object.is(operate, 'dialogedit')) {
        if (this.dialogBaseInfoForm.fundPurposeSecond) {
          this.dialogBaseInfoForm.fundPurposeSecond = null
        }
        parentId = this.dialogBaseInfoForm.fundPurposeFirst
      }
      if (parentId === null) {
        return false
      } else {
        let query = {
          level: 2,
          parentId: parentId
        }
        investmanage.getFundPurposeList(query).then((res) => {
          this.fundPurposeSecondList = []
          this.fundPurposeSecondList = res.data.fundPurposeList
        })
      }
    },
    /** 查询资金投向 - 二级 */
    getFundPurposeSecondList (fundPurposeFirst) {
      let query = {
        level: 2,
        parentId: fundPurposeFirst
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = []
        this.fundPurposeSecondList = res.data.fundPurposeList
      })
    },
    /** 查询申报表 */
    getDeclarationForm (row, operate) {
      this.autoSearch = ''
      let query = {
        suggestedPlanCode: row.code
      }
      investmanage.getDeclarationForm(query).then((res) => {
        let tempInfo = res.data.declarationForm
        if (tempInfo) {
          if (!tempInfo.fundPurposeSecond) {
            this.dialogBaseInfoForm.fundPurposeSecond = null
          }
          this.dialogBaseInfoForm.capitalSource = this.suggestedPlan.capitalSource
          if (tempInfo.capitalSource) {
            this.getCapitalSourceName(tempInfo.capitalSource)
          }
          this.dialogBaseInfoForm = tempInfo
          this.dialogBaseInfoForm.projectType = this.requestFormInfo.projectType
          this.dialogBaseInfoForm.procurementMethod = this.requestFormInfo.bidType
          this.dialogBaseInfoForm.projectName = row.projectName
          this.dialogBaseInfoForm.capitalSourceName = row.capitalSourceName
          this.dialogBaseInfoForm.plannedCosts = row.currentPlanAmount
          this.dialogBaseInfoForm.quantities = row.currentPlanQuantities
          // 设备现有状况
          this.dialogBaseInfoForm.equipmentTableData = [].concat(tempInfo.equipmentActualityList)
          // 工程主要内容
          this.dialogBaseInfoForm.engineerTableData = [].concat(tempInfo.accessoriesList)
          if (Object.is(operate, 'dialogedit')) {
            this.dialogBaseInfoForm.area = [this.dialogBaseInfoForm.provinceId, this.dialogBaseInfoForm.cityId, this.dialogBaseInfoForm.countyId]
          } else if (Object.is(operate, 'look')) {
            this.dialogBaseInfoForm.place = ''
            let provinceId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.provinceId)
            let cityId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.cityId)
            let countyId = this.getLabelName(CityInfo, this.dialogBaseInfoForm.countyId)
            if (provinceId.node) {
              this.dialogBaseInfoForm.place += `${provinceId.node.label}`
            }
            if (cityId.node) {
              this.dialogBaseInfoForm.place += `${cityId.node.label}`
            }
            if (countyId.node) {
              this.dialogBaseInfoForm.place += `${countyId.node.label}`
            }
          }
          let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
          let newInvestmentCompositionList = [{}, {}, {}, {}, {}]
          investmentCompositionList.map(item => {
            newInvestmentCompositionList[item.type - 1] = item
          })
          this.$set(this.dialogBaseInfoForm, 'investmentCompositionList', newInvestmentCompositionList)
          this.calculateTotal()
          this.getFundPurposeSecondList(this.dialogBaseInfoForm.fundPurposeFirst)
        }
        if (!this.dialogBaseInfoForm.code) {
          this.getDialogFormInfo(row)
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
      let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
      investmentCompositionList.map((item, index) => {
        if (item.budgetInvestment) {
          if (!this.totalForm.budgetInvestment) {
            this.totalForm.budgetInvestment = Number(item.budgetInvestment)
          } else {
            this.totalForm.budgetInvestment = Number(this.totalForm.budgetInvestment) + Number(item.budgetInvestment)
          }
        }
        if (item.cumulativeComplete) {
          if (!this.totalForm.cumulativeComplete) {
            this.totalForm.cumulativeComplete = Number(item.cumulativeComplete)
          } else {
            this.totalForm.cumulativeComplete = Number(this.totalForm.cumulativeComplete) + Number(item.cumulativeComplete)
          }
        }
        if (item.expectedCumulativeComplete) {
          if (!this.totalForm.expectedCumulativeComplete) {
            this.totalForm.expectedCumulativeComplete = Number(item.expectedCumulativeComplete)
          } else {
            this.totalForm.expectedCumulativeComplete = Number(this.totalForm.expectedCumulativeComplete) + Number(item.expectedCumulativeComplete)
          }
        }
        if (item.investmentProposalPlan) {
          if (!this.totalForm.investmentProposalPlan) {
            this.totalForm.investmentProposalPlan = Number(item.investmentProposalPlan)
          } else {
            this.totalForm.investmentProposalPlan = Number(this.totalForm.investmentProposalPlan) + Number(item.investmentProposalPlan)
          }
        }
      })
      if (this.totalForm.budgetInvestment) {
        this.totalForm.budgetInvestment = this.totalForm.budgetInvestment.toFixed(2)
      }
      if (this.totalForm.cumulativeComplete) {
        this.totalForm.cumulativeComplete = this.totalForm.cumulativeComplete.toFixed(2)
      }
      if (this.totalForm.expectedCumulativeComplete) {
        this.totalForm.expectedCumulativeComplete = this.totalForm.expectedCumulativeComplete.toFixed(2)
      }
      if (this.totalForm.investmentProposalPlan) {
        this.totalForm.investmentProposalPlan = this.totalForm.investmentProposalPlan.toFixed(2)
      }
    },
    /** 申报表-删除 */
    deleteTableBtnClick (row, type) {
      if (Object.is(type, 'equip')) {
        this.dialogBaseInfoForm.equipmentTableData.splice(this.dialogBaseInfoForm.equipmentTableData.indexOf(row), 1)
      } else if (Object.is(type, 'enginer')) {
        this.dialogBaseInfoForm.engineerTableData.splice(this.dialogBaseInfoForm.engineerTableData.indexOf(row), 1)
      }
    },
    /** 申报表-新增一行 */
    addDiaFormTableRow (type) {
      let obj = {
        rowKey: Math.random(),
        type: '',
        number: '',
        purchaseTime: '',
        durableYears: '',
        usePlace: '',
        name: '',
        specification: '',
        unit: '',
        amount: '',
        unitPrice: '',
        total: ''
      }
      if (Object.is(type, 'equip')) {
        // 如果有数据
        if (this.dialogBaseInfoForm.equipmentTableData) {
          this.dialogBaseInfoForm.equipmentTableData.push(obj)
        } else { // 如果没有数据
          this.dialogBaseInfoForm.equipmentTableData = []
          this.$nextTick(() => {
            this.$set(this.dialogBaseInfoForm.equipmentTableData, 0, obj)
          })
        }
      } else if (Object.is(type, 'enginer')) {
        // 如果有数据
        if (this.dialogBaseInfoForm.equipmentTableData) {
          this.dialogBaseInfoForm.engineerTableData.push(obj)
        } else { // 如果没有数据
          this.dialogBaseInfoForm.engineerTableData = []
          this.$nextTick(() => {
            this.$set(this.dialogBaseInfoForm.engineerTableData, 0 , obj)
          })
        }
      }
    },
    /** 点击表格中的特定单元格 */
    clickCellCloumn (row) {
      if (row.declarationForm) {
        this.getDeclarationForm(row, 'look')
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
    /** 判断年度计划是否已经审核通过 */
    getAunnalStatus () {
      let obj = {
        year: this.year,
        enterpriseId: this.$store.getters.authUser.enterpriseId
      }
      plansubmit.getList(obj).then((res) => {
        let annualData = res.data.annualProjectPlanList.list
        for (let i = 0; i < annualData.length; i++) {
          if (annualData[i].status !== 3) {
            this.dialogAdd = false
          } else {
            this.dialogAdd = true
          }
        }
      })
    },
    /** 根据企业id和资金来源id查询历史申报项目 */
    getDeclarationFormList (row) {
      investmanage.getHistoryDeclList(row.enterpriseId, row.capitalSource).then((res) => {
        if (res) {
          this.projectNameList = res.data.declarationFormList
        }
      })
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
    this.getProjectPlanList(this.pageNo)
  }
}
</script>
<style lang="less">
#prorequest-page {
  .prorequest-cont {
    .prorequest-add {
      text-align: left;
      padding-bottom: 20px;
    }
  }
  /*搜索css*/
  .prorequest-search {
    padding: 20px;
    margin: 20px;
    background-color: #fff;
    .el-form-item {
      margin-left: -50px;
      margin-bottom: 0;
    }
  }
  /*附件列表*/
  .apply-upload {
    .file-tit {
      display: inline-block;
      float: left;
      font-size: 14px;
      color: #999;
      margin-top: 25px;
    }
    .file-list {
      width: 280px;
      height: 65px;
      margin-right: 20px;
      margin-bottom: 10px;
      display: inline-block;
      border: 1px solid #eeeeee;
      span {
        display: block;
        width: 160px;
        font-size: 14px;
        padding: 5px 20px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .el-icon-error.upload-file {
        font-size: 22px;
        color: red;
        float: right;
        margin-top: -30px;
        margin-right: 10px;
        cursor: pointer;
      }
      img {
        display: inline-block;
        float: left;
        margin: 8px auto;
        padding-left: 5px;
      }
    }
    .file-list:hover {
      cursor: pointer;
    }
  }
  .dialog-box {
    .base-info .table-form {
      width: 100%;
      border-collapse: collapse;
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
    .el-form-item__error {
      display: none;
    }
    .el-select-dropdown {
      width: 198px;
    }
  }
  /*审批记录*/
  .prorequest-shenpi {
    margin: 20px;
    padding: 20px;
    border-top: 1px solid #eee;
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
      width: 120px;
    }
    .span-last {
      float: right;
    }
    .span-third {
      width: 140px;
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
  .el-dialog__header {
    border-bottom: 1px solid #eeeeee;
  }
  .app-btn-set {
    .el-form-item__content {
      text-align: center;
    }
  }
  .block-page {
    padding-top: 20px;
  }
}
</style>
