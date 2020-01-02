<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="cardedit-page">
    <!--创建建议计划-搜索区域start-->
    <div class="balance-search">
      <el-form :model="searchCreateForm" :inline="true" class="demo-form-inline" style="text-align: left;">
        <el-form-item label="项目名称:">
          <el-input v-model="searchCreateForm.messageLike" clearable placeholder="请填写项目名称">
          </el-input>
        </el-form-item>
        <el-form-item label="资金投向:">
          <el-select v-model="searchCreateForm.fundPurposeFirst" clearable placeholder="请选择资金投向" @change="getSecondByFirstList('query')">
            <el-option
              v-for="item in fundPurposeFirstList"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
          <span v-if="fundPurposeSecondList.length>0">-</span>
          <el-select v-model="searchCreateForm.fundPurposeSecond" clearable placeholder="请选择资金投向" v-if="fundPurposeSecondList.length>0">
            <el-option
              v-for="item in fundPurposeSecondList"
              :key="item.objectId"
              :label="item.name"
              :value="item.objectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="转入渠道:">
          <el-select v-model="searchCreateForm.transferToSource" clearable placeholder="请选择转入渠道">
            <el-option
              v-for="item in classifyList"
              :key="item.capitalSourceId"
              :label="item.capitalSourceName"
              :value="item.capitalSourceId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--创建建议计划-搜索区域end-->
    <div class="table">
      <div class="add-row" style="text-align: right;">
        <el-button type="warning" icon="el-icon-plus" style="float: left;margin-right: 15px;" @click="addTableRow">新增一行</el-button>
        <el-upload
          style="float: left;"
          class="upload-demo"
          :action="upLoad"
          :data="upLoadData"
          :show-file-list="false"
          :auto-upload="true"
          :on-success="handleSuccess"
          :file-list="fileList"
          multiple>
          <el-button type="primary" style="float: left;">导入建议计划</el-button>
        </el-upload>
        <el-button type="primary" style="float: left;margin-left: 10px;" @click="exportTemplate">导出计划模板</el-button>
      </div>
      <el-table
        class="table-cont"
        :data="createPlanTableData"
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
            <el-form :model="scope.row" :rules="projectNameRules" :ref="'projectNameForm'+scope.$index" class="demo-ruleForm">
              <el-form-item prop="projectName">
                <el-input type="text" v-model="scope.row.projectName"></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="content"
          label="工程简要内容及设备型号"
          align="center">
          <template slot-scope="scope">
            <el-form :model="scope.row" :rules="contentRules" :ref="'contentForm'+scope.$index" class="demo-ruleForm">
              <el-form-item prop="content">
                <el-input type="text" v-model="scope.row.content"></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          prop="quantitiesUnit"
          label="工程量单位"
          align="center">
          <template slot-scope="scope">
            <el-form :model="scope.row" :rules="quantitiesUnitRules" :ref="'quantitiesUnitForm'+scope.$index" class="demo-ruleForm">
              <el-form-item prop="quantitiesUnit">
                <el-input type="text" v-model="scope.row.quantitiesUnit"></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column
          v-if="this.capitalSourceClassify.capitalSourceId === 0"
          label="概算"
          align="center">
          <el-table-column
            prop="budgetQuantities"
            label="工程量"
            align="center">
            <template slot-scope="scope">
              <el-form :model="scope.row" :rules="budgetQuantitiesRules" :ref="'budgetQuantitiesForm'+scope.$index" class="demo-ruleForm">
                <el-form-item prop="budgetQuantities">
                  <el-input type="text" v-model="scope.row.budgetQuantities"></el-input>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="budgetAmount"
            label="金额"
            align="center">
            <template slot-scope="scope">
              <el-form :model="scope.row" :rules="budgetAmountRules" :ref="'budgetAmountForm'+scope.$index" class="demo-ruleForm">
                <el-form-item prop="budgetAmount">
                  <el-input type="text" v-model="scope.row.budgetAmount"></el-input>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column
          v-if="this.capitalSourceClassify.capitalSourceId !== 0"
          label="本年计划"
          align="center">
          <el-table-column
            prop="currentPlanQuantities"
            label="工程量"
            align="center">
            <template slot-scope="scope">
              <el-form :model="scope.row" :rules="quantitiesRules" :ref="'quantitiesForm'+scope.$index" class="demo-ruleForm">
                <el-form-item prop="currentPlanQuantities">
                  <el-input type="text" v-model="scope.row.currentPlanQuantities"></el-input>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="currentPlanAmount"
            label="金额"
            align="center">
            <template slot-scope="scope">
              <el-form :model="scope.row" :rules="amountRules" :ref="'amountForm'+scope.$index" class="demo-ruleForm">
                <el-form-item prop="currentPlanAmount">
                  <el-input type="text" v-model="scope.row.currentPlanAmount"></el-input>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
        </el-table-column>
        <el-table-column
          prop="fundPurpose"
          label="资金投向"
          align="center">
        </el-table-column>
        <el-table-column
          prop="transferToSourceName"
          label="转入渠道"
          align="center">
          <template slot-scope="scope">
            {{scope.row.transferToSourceName?scope.row.transferToSourceName:(scope.row.transferToSource === 0?'工程建设': '')}}
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="240">
          <template slot-scope="scope">
            <el-button @click="handleTableClick(scope.row,'save', scope.$index)" type="text" size="medium">保存</el-button>
            <el-button @click="handleTableClick(scope.row,'del', scope.$index)" type="text" size="medium">删除</el-button>
            <el-button @click="handleTableClick(scope.row,'edit', scope.$index)" type="text" size="medium" v-show="scope.row.objectId">编辑申报表</el-button>
            <el-dropdown @command="handleCommand" v-show="scope.row.objectId">
              <span class="el-dropdown-link" style="color: #409eff;">
                转出<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="{'row': scope.row, 'classify':item}" v-for="(item, index) in  classifyList" :key="index">
                  {{item.capitalSourceName}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="submit-btn">
      <el-button type="primary" @click="submitCreatePlan">提 交</el-button>
      <el-button type="primary" @click="returnPlan">退 回</el-button>
    </div>
    <!--弹窗区域start-->
    <div class="dialog-box">
      <!--退回弹窗start-->
      <el-dialog
        title="提示"
        :visible.sync="dialogReturnVisible"
        width="30%"
        center>
        <el-form ref="returnRorm" :model="returnRorm" :rules="rules" label-width="90px">
          <el-form-item label="退回原因:" prop="returnReason">
            <el-input type="textarea" :rows="4" v-model="returnRorm.returnReason"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="confirmReturnReason('returnRorm')">确 定</el-button>
          <el-button @click="dialogReturnVisible = false">取 消</el-button>
        </span>
      </el-dialog>
      <!--退回弹窗end-->
      <el-dialog
        title="专项资金建议计划申报表"
        @close="cancelBtn"
        :visible.sync="dialogVisible"
        width="80%"
        center>
        <div style="margin-bottom: 15px;">
          <span>历史申报项目:</span>
          <el-select v-model="autoSearch" filterable placeholder="请选择">
            <el-option
              v-for="item in declarationFormList"
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
                    <el-select v-model="dialogBaseInfoForm.fundPurposeFirst" clearable placeholder="请选择" @change="getSecondByFirstList('edit')">
                      <el-option
                        v-for="item in fundPurposeFirstList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <span v-if="fundPurposeSecondList.length>0" style="display: inline-block;float: left;line-height: 40px;">-</span>
                  <el-form-item prop="fundPurposeSecond" style="float: left" v-if="fundPurposeSecondList.length>0">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeSecond" clearable placeholder="请选择">
                      <el-option
                        v-for="item in fundPurposeSecondList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>设备在籍(台)</td>
                <td>
                  <el-form-item prop="equipmentAbsentee">
                    <el-input v-model="dialogBaseInfoForm.equipmentAbsentee"></el-input>
                  </el-form-item>
                </td>
                <td>设备在用(台)</td>
                <td>
                  <el-form-item prop="equipmentInUse">
                    <el-input v-model="dialogBaseInfoForm.equipmentInUse"></el-input>
                  </el-form-item>
                </td>
                <td>备用(台)</td>
                <td>
                  <el-form-item prop="equipmentReserve">
                    <el-input v-model="dialogBaseInfoForm.equipmentReserve"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.unitType"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>(大修、更新)周期</td>
                <td colspan="2">
                  <el-form-item prop="maintenanceCycle">
                    <el-input v-model="dialogBaseInfoForm.maintenanceCycle"></el-input>
                  </el-form-item>
                </td>
                <td>上次(大修、更新)时间</td>
                <td colspan="2">
                  <el-form-item prop="lastMaintenanceTime">
                    <el-date-picker
                      style="width: 100%;"
                      v-model="dialogBaseInfoForm.lastMaintenanceTime"
                      type="date"
                      placeholder="选择日期">
                    </el-date-picker>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>工程项目是否有方案</td>
                <td colspan="2">
                  <el-form-item prop="isPlan">
                    <el-select v-model="dialogBaseInfoForm.isPlan" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in isPlanList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
                <td>项目负责人</td>
                <td colspan="2">
                  <el-form-item prop="projectManager">
                    <el-input v-model="dialogBaseInfoForm.projectManager"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>采购方式</td>
                <td colspan="2">
                  <el-form-item prop="procurementMethod">
                    <el-select v-model="dialogBaseInfoForm.procurementMethod" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in procurementMethodList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
                <td>工期</td>
                <td colspan="2">
                  <el-form-item prop="timeLimit">
                    <el-select v-model="dialogBaseInfoForm.timeLimit" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in timeLimitList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目责任部门</td>
                <td colspan="2">
                  <el-form-item prop="responsibleDepartment">
                    <el-input v-model="dialogBaseInfoForm.responsibleDepartment"></el-input>
                  </el-form-item>
                </td>
                <td>招标时间</td>
                <td colspan="2">
                  <el-form-item prop="executionTime">
                    <el-date-picker
                      style="width:100%;"
                      v-model="dialogBaseInfoForm.executionTime"
                      type="date"
                      placeholder="选择日期">
                    </el-date-picker>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td colspan="5">
                  <el-form-item prop="projectType">
                    <el-select v-model="dialogBaseInfoForm.projectType" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in projectTypeList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>提出原因</td>
                <td colspan="5">
                  <el-form-item prop="reason">
                    <el-input v-model="dialogBaseInfoForm.reason"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="5">
                  <el-form-item prop="remark">
                    <el-input v-model="dialogBaseInfoForm.remark"></el-input>
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
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.type'"
                    :rules="[
                          { required: true, message: '型号不能为空', trigger: 'blur'},
                          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                        ]">
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].type"></el-input>
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
                    <el-input v-model="scope.row.number"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                prop="purchaseTime"
                label="购置时间">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'equipmentTableData.' + scope.$index + '.purchaseTime'"
                    :rules="[
                          { required: true, message: '购置时间不能为空', trigger: 'change'}
                        ]">
                    <el-date-picker
                      style="width: 100%;"
                      v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].purchaseTime"
                      type="date"
                      placeholder="选择日期">
                    </el-date-picker>
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
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].durableYears"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].usePlace"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.equipmentTableData[scope.$index].remark"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="80">
                <template slot-scope="scope">
                  <el-button @click="deleteTableBtnClick(scope.row, 'equip')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-add-btn">
              <el-button icon="el-icon-plus" @click="addDialogTableRow('equip')" style="width:100%"></el-button>
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
                          { required: true, message: '项目名称不能为空', trigger: 'blur'},
                          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
                        ]">
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].name"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].specification"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].unit"></el-input>
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
                    <el-input v-model="scope.row.amount"></el-input>
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
                    <el-input v-model="scope.row.unitPrice"></el-input>
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
                    <el-input v-model="scope.row.total"></el-input>
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
                    <el-input v-model="dialogBaseInfoForm.engineerTableData[scope.$index].remark"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                label="操作"
                width="80">
                <template slot-scope="scope">
                  <el-button @click="deleteTableBtnClick(scope.row, 'enginer')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-add-btn">
              <el-button icon="el-icon-plus" @click="addDialogTableRow('enginer')" style="width:100%"></el-button>
            </div>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveBtn('dialogBaseInfoForm')">保存</el-button>
          <el-button @click="cancelBtn">取消</el-button>
        </span>
      </el-dialog>
      <!-- 工程建设建议计划申报表start -->
      <el-dialog
        title="工程建设建议计划申报表"
        :visible.sync="enginerDialogViseble"
        width="80%"
        center>
        <el-form :model="dialogBaseInfoForm" :rules="enginerRules" ref="enginerRuleForm" label-width="140px" class="demo-ruleForm">
          <div style="margin-bottom: 15px;">
            <span>历史申报项目:</span>
            <el-select v-model="autoSearch" filterable placeholder="请选择">
              <el-option
                v-for="item in declarationFormList"
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
                  <span>{{dialogBaseInfoForm.capitalSource?dialogBaseInfoForm.capitalSourceName:this.capitalSourceName}}</span>
                </td>
                <td>计划费用（万元）</td>
                <td colspan="2">{{dialogBaseInfoForm.plannedCosts}}</td>
              </tr>
              <tr>
                <td>资金投向</td>
                <td>
                  <el-form-item prop="fundPurposeFirst" style="float: left">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeFirst" clearable placeholder="请选择" @change="getSecondByFirstList('edit')">
                      <el-option
                        v-for="item in fundPurposeFirstList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <span v-if="fundPurposeSecondList.length>0" style="display: inline-block;float: left;line-height: 40px;">-</span>
                  <el-form-item prop="fundPurposeSecond" style="float: left" v-if="fundPurposeSecondList.length>0">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeSecond" clearable placeholder="请选择">
                      <el-option
                        v-for="item in fundPurposeSecondList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
                <td>工程量</td>
                <td colspan="2">{{dialogBaseInfoForm.quantities}}</td>
              </tr>
              <tr>
                <td>项目类型</td>
                <td>
                  <el-form-item prop="projectType">
                    <el-select v-model="dialogBaseInfoForm.projectType" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in projectTypeList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
                <td>采购方式</td>
                <td colspan="2">
                  <el-form-item prop="procurementMethod">
                    <el-select v-model="dialogBaseInfoForm.procurementMethod" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in procurementMethodList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>投资主体</td>
                <td>
                  <el-form-item prop="investor">
                    <el-input v-model="dialogBaseInfoForm.investor"></el-input>
                  </el-form-item>
                </td>
                <td>建设性质</td>
                <td colspan="2">
                  <el-form-item prop="constructiveNature">
                    <el-select v-model="dialogBaseInfoForm.constructiveNature" placeholder="请选择" clearable>
                      <el-option
                        v-for="item in constructiveList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>建设规模</td>
                <td>
                  <el-form-item prop="constructiveScale">
                    <el-input v-model="dialogBaseInfoForm.constructiveScale"></el-input>
                  </el-form-item>
                </td>
                <td>建设起止时间</td>
                <td colspan="2" style="line-height:40px;">
                  <el-form-item prop="constructiveStartTime" style="float:left">
                    <el-date-picker
                      v-model="dialogBaseInfoForm.constructiveStartTime"
                      type="date"
                      placeholder="选择开始日期">
                    </el-date-picker>
                  </el-form-item>
                  <span style="display:block;float:left;">至</span>
                  <el-form-item prop="constructiveEndTime" style="float:left">
                    <el-date-picker
                      v-model="dialogBaseInfoForm.constructiveEndTime"
                      type="date"
                      placeholder="选择结束日期">
                    </el-date-picker>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>建设地点</td>
                <td colspan="4">
                  <el-form-item prop="area" style="float:left;">
                    <el-cascader
                      :options="addressList"
                      v-model="dialogBaseInfoForm.area"
                      change-on-select>
                    </el-cascader>
                  </el-form-item>
                  <el-form-item prop="address" style="float:left;width:50%;">
                    <el-input v-model="dialogBaseInfoForm.address" placeholder="详细地址"></el-input>
                  </el-form-item>
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
              <tr v-for="(item, index) in dialogBaseInfoForm.investmentCompositionList" :key="index">
                <td>{{index | filterName}}</td>
                <td>
                  <el-form-item :prop="'investmentCompositionList.' + index + '.budgetInvestment'"
                                :rules='enginerRules.budgetInvestment'>
                    <el-input v-model="item.budgetInvestment" @blur="calculateTotal"></el-input>
                  </el-form-item>
                </td>
                <td style="background:#fff;">
                  <el-form-item :prop="'investmentCompositionList.' + index + '.cumulativeComplete'"
                                :rules='enginerRules.cumulativeComplete'>
                    <el-input v-model="item.cumulativeComplete" @blur="calculateTotal"></el-input>
                  </el-form-item>
                </td>
                <td>
                  <el-form-item :prop="'investmentCompositionList.' + index + '.expectedCumulativeComplete'"
                                :rules='enginerRules.expectedCumulativeComplete'>
                    <el-input v-model="item.expectedCumulativeComplete" @blur="calculateTotal"></el-input>
                  </el-form-item>
                </td>
                <td style="background:#fff;">
                  <el-form-item :prop="'investmentCompositionList.' + index + '.investmentProposalPlan'"
                                :rules='enginerRules.investmentProposalPlan'>
                    <el-input v-model="item.investmentProposalPlan" @blur="calculateTotal"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>{{this.$route.query.year}}年项目审批目标</td>
                <td colspan="4">
                  <el-form-item prop="approveTarget">
                    <el-input v-model="dialogBaseInfoForm.approveTarget"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>{{this.$route.query.year}}年项目建设目标</td>
                <td colspan="4">
                  <el-form-item prop="constructiveTarget">
                    <el-input v-model="dialogBaseInfoForm.constructiveTarget"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>备注</td>
                <td colspan="4">
                  <el-form-item prop="remark">
                    <el-input v-model="dialogBaseInfoForm.remark"></el-input>
                  </el-form-item>
                </td>
              </tr>
            </table>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveBtn('enginerRuleForm')">保存</el-button>
          <el-button @click="cancelBtn">取消</el-button>
        </span>
      </el-dialog>
      <!-- 工程建设建议计划申报表end -->
      <!--删除弹窗start-->
      <el-dialog
        title="请选择移除目的地"
        :visible.sync="deleteDialogVisible"
        width="30%"
        center>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="removeBtnClick('pro')">项目储备库</el-button>
          <el-button type="primary" @click="removeBtnClick('recycle')">回收站</el-button>
        </span>
      </el-dialog>
      <!--删除弹窗end-->
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
import treeGrid from '@/components/treetable/TreeGrid.vue'
import {commonJs} from 'common/js/common'
import {investmanage} from 'api/index'
import {CityInfo} from 'common/js/region'
// 实例
export default {
  data () {
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
        projectType: [
          { required: true, message: '请选择项目类型', trigger: ['blur', 'change'] }
        ],
        procurementMethod: [
          { required: true, message: '请选择采购方式', trigger: ['blur', 'change'] }
        ],
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
      declarationFormList: [],
      deleteDialogVisible: false, // 删除-弹窗
      projectNameRules: {
        projectName: [
          { required: true, message: '请填写项目名称', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 项目名称校验
      contentRules: {
        content: [
          // { required: true, message: '请填写工程简要内容及设备型号', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      },
      quantitiesUnitRules: {
        quantitiesUnit: [
          // { required: true, message: '请填写工程量单位', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ]
      },
      // 工程简要内容及设备型号校验
      budgetQuantitiesRules: {
        budgetQuantities: [
          // { required: true, message: '请填写概算工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ]
      }, // 工程量（概算）
      budgetAmountRules: {
        budgetAmount: [
          { required: true, message: '请填写概算金额', trigger: 'blur' },
          {validator: checkAmount}
        ]
      }, // 概算金额
      quantitiesRules: {
        currentPlanQuantities: [
          // { required: true, message: '请填写本年计划工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ]
      }, // 工程量（本年计划）
      amountRules: {
        currentPlanAmount: [
          { required: true, message: '请填写本年计划金额', trigger: 'blur' },
          {validator: checkAmount}
        ]
      }, // 本年计划金额
      upLoad: commonJs.investExcelUploadUrl,
      upLoadData: {},
      activeName: 'first',
      currentPage: 1,
      searchCreateForm: {}, // 搜索-创建建议计划表单
      searchQueryForm: {}, // 搜索-查询建议计划表单
      fundPurposeFirstList: [], // 资金投向 - 一级
      fundPurposeSecondList: [], // 资金投向 - 二级
      createPlanTableData: [], // 建议计划表格
      oldPlanTableData: [], // 原有建议计划
      queryPlanTableData: [],
      columns: [
        {
          text: '投向',
          dataIndex: 'name'
        }
      ],
      dialogVisible: false, // 申报表弹窗
      dialogBaseInfoForm: {
        investmentCompositionList: [
          {}, {}, {}, {}, {}
        ],
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [] // 工程主要内容-表格
      }, // 弹窗-基本信息表单
      dialogBaseInfoRules: {
        plannedCosts: [
          { required: true, message: '请填写计划费用', trigger: 'blur' },
          {validator: checkAmount}
        ],
        fundPurposeFirst: { required: true, message: '请选择资金投向', trigger: ['blur', 'change'] },
        fundPurposeSecond: { required: true, message: '请选择资金投向', trigger: ['blur', 'change'] },
        equipmentAbsentee: {validator: checkQuantities},
        equipmentInUse: {validator: checkQuantities},
        equipmentReserve: {validator: checkQuantities},
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
        executionTime: { required: true, message: '请选择招标时间', trigger: 'change' },
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
      fileList: [], // 上传列表
      suggestedPlan: {}, // 建议计划
      projectTypeList: [
        {label: '工程', value: 1},
        {label: '货物', value: 2},
        {label: '服务', value: 3}
      ],
      isPlanList: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      // 采购方式
      procurementMethodList: [
        {label: '公开招标', value: '1'},
        {label: '邀请招标', value: '2'},
        {label: '谈判采购', value: '3'},
        {label: '询比采购', value: '4'},
        {label: '竞价采购', value: '5'},
        {label: '直接采购', value: '6'},
        {label: '框架协议', value: '7'}
      ],
      // 计划工期
      timeLimitList: [
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
      firstClassifyCode: '', // 首个资金来源分类编号
      newCode: '',
      capitalSourceName: '', // 工程建设项目资金来源
      capitalSourceClassify: {}, // 资金来源分类表数据
      returnRorm: {},
      rules: {
        returnReason: [
          { required: true, message: '请填写退回原因', trigger: 'blur' }
        ]
      },
      dialogReturnVisible: false, // 退回-弹窗
      row: {},
      recycleTableData: [], // 处在项目储备库/回收站的数据
      constructiveList: [
        {label: '新建', value: 1},
        {label: '扩建', value: 2},
        {label: '改建', value: 3},
        {label: '迁建', value: 4},
        {label: '恢复重建', value: 5},
        {label: '技改', value: 6}
      ],
      classifyList: [] // 卡片信息
    }
  },
  props: {
    classifyCode: String,
    recycleFlag: Number
  },
  computed: {
    newQuery () {
      return this.$route.query.handleType
    }
  },
  watch: {
    editToMain: function (newV, oldV) {
      let query = {
        code: newV
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
      }).then(res => {
        this.createUpLoadData()
        this.getFundPurposeFirstList()
        this.getFirstClassifyCode()
      })
    },
    classifyCode: function (newV, oldV) {
      let query = {
        code: newV
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
      }).then(res => {
        this.createUpLoadData()
        this.getFundPurposeFirstList()
        this.getFirstClassifyCode()
        if (this.recycleFlag === 1) {
          this.getSuggestedPlanList()
        }
      })
    },
    autoSearch: function (newV, oldV) {
      let query = {
        code: newV
      }
      investmanage.getDeclarationForm(query).then(res => {
        let declarationForm = res.data.declarationForm
        if (declarationForm) {
          this.dialogBaseInfoForm.fundPurposeFirst = res.data.declarationForm.fundPurposeFirst
          this.getSecondByFirstList('edit')
          declarationForm.objectId = this.dialogBaseInfoForm.objectId
          declarationForm.code = this.dialogBaseInfoForm.code
          declarationForm.projectName = this.dialogBaseInfoForm.projectName
          declarationForm.plannedCosts = this.dialogBaseInfoForm.plannedCosts
          declarationForm.quantities = this.dialogBaseInfoForm.quantities
          this.dialogBaseInfoForm = res.data.declarationForm
          this.$set(this.dialogBaseInfoForm, 'equipmentTableData', this.dialogBaseInfoForm.equipmentActualityList)
          this.$set(this.dialogBaseInfoForm, 'engineerTableData', this.dialogBaseInfoForm.accessoriesList)
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
  },
  created () {
    if (Object.is(this.newQuery, 'look')) {
      this.activeName = 'second'
    }
  },
  methods: {
    /** 转出 */
    handleCommand (val) {
      let arr = ['该项目将转到“' + val.classify.capitalSourceName + '”中, 确认转出吗?']
      let el = this.$createElement
      let newD = []
      for (const i in arr) {
        newD.push(el('p', null, arr[i]))
      }
      this.$confirm('提示', {
        title: '提示',
        message: el('div', null, newD),
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 确定转出
        let row = val.row
        let classify = val.classify
        let index = this.createPlanTableData.indexOf(row)
        let objectId = row.objectId
        // 转出后的卡片
        if (classify.isLocked === 1) {
          // 新增维护记录
          this.transferSuggestedPlan(row, classify)
          let query = {
            capitalSourceClassifyCode: classify.code
          }
          investmanage.getSuggestedPlanList(query).then(res => {
            let saveData = res.data.suggestedPlanList
            let query = {
              investmentProposalSchemeCode: this.capitalSourceClassify.investmentProposalSchemeCode,
              capitalSourceId: classify.capitalSourceId,
              isDelete: 0
            }
            investmanage.getFirstClassifyCode(query).then(res => {
              saveData.push(row)
              let firstClassifyCode = res.data.firstClassifyCode
              let newClassify = classify
              newClassify.operate = 2
              newClassify.objectId = null
              newClassify.firstClassifyCode = firstClassifyCode
              saveData.map(suggestedPlan => {
                this.initData(suggestedPlan, classify)
              })
              this.$set(newClassify, 'suggestedPlanList', saveData)
              investmanage.saveClassify(newClassify)
            })
          })
        } else {
          // 修改维护记录
          this.transferSuggestedPlan(row, classify)
          this.initData(row, classify)
          investmanage.saveSuggestedPlan(row)
        }
        // 转出前的卡片
        if (this.capitalSourceClassify.isLocked === 1) {
          // 新增维护记录
          this.createBeforeNewClassify(row)
        } else {
          // 修改维护记录
          investmanage.deleteSuggestedPlan(objectId).then((res) => {
            this.createPlanTableData.splice(index, 1)
            this.getClassify()
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    initData (suggestedPlan, classify) {
      suggestedPlan.objectId = null
      suggestedPlan.capitalSource = classify.capitalSourceId
      if (suggestedPlan.declarationForm) {
        suggestedPlan.declarationForm.objectId = null
        suggestedPlan.declarationForm.projectName = suggestedPlan.projectName
        if (classify.capitalSourceId === 0) {
          suggestedPlan.declarationForm.plannedCosts = suggestedPlan.budgetAmount
          suggestedPlan.declarationForm.quantities = suggestedPlan.budgetQuantities
        } else {
          suggestedPlan.declarationForm.plannedCosts = suggestedPlan.currentPlanAmount
          suggestedPlan.declarationForm.quantities = suggestedPlan.currentPlanQuantities
        }
        suggestedPlan.declarationForm.equipmentActualityList.map((equipmentActuality) => {
          equipmentActuality.objectId = null
          return equipmentActuality
        })
        suggestedPlan.declarationForm.accessoriesList.map((accessories) => {
          accessories.objectId = null
          return accessories
        })
        suggestedPlan.declarationForm.investmentCompositionList.map((investmentComposition) => {
          investmentComposition.objectId = null
          return investmentComposition
        })
      }
      return suggestedPlan
    },
    transferSuggestedPlan (row, classify) {
      row.capitalSourceClassifyCode = classify.code
      if (row.classify !== classify.classify) {
        if (row.classify === 1) {
          // 专项资金转出至工程建设资金
          row.budgetQuantities = row.currentPlanQuantities
          row.budgetAmount = row.currentPlanAmount
          row.currentPlanQuantities = null
          row.currentPlanAmount = null
        } else if (row.classify === 2) {
          // 工程建设资金转出至专项资金
          row.currentPlanQuantities = row.budgetQuantities
          row.currentPlanAmount = row.budgetAmount
          row.budgetQuantities = null
          row.budgetAmount = null
        }
        row.classify = classify.classify
        row.declarationForm = null
      } else {
        if (row.declarationForm) {
          row.declarationForm.capitalSource = classify.capitalSourceId
          row.declarationForm.fundPurposeFirst = null
          row.declarationForm.fundPurposeSecond = null
        }
      }
      row.transferToSource = row.capitalSource
      row.capitalSource = classify.capitalSourceId
      row.fundPurposeFirst = null
      row.fundPurposeSecond = null
    },
    createBeforeNewClassify (row) {
      let newClassify = this.capitalSourceClassify
      newClassify.operate = 2
      newClassify.objectId = null
      newClassify.firstClassifyCode = this.firstClassifyCode
      let saveData = this.createPlanTableData.filter(suggestedPlan => {
        if (suggestedPlan.objectId && suggestedPlan.objectId !== row.objectId) {
          return suggestedPlan
        }
      })
      this.recycleTableData.map(suggestedPlan => {
        saveData.push(suggestedPlan)
      })
      let notSaveData = this.createPlanTableData.filter(suggestedPlan => {
        if (!suggestedPlan.objectId) {
          return suggestedPlan
        }
      })
      saveData.map(suggestedPlan => {
        this.initData(suggestedPlan, this.capitalSourceClassify)
      })
      this.$set(newClassify, 'suggestedPlanList', saveData)
      investmanage.saveClassify(newClassify).then((res) => {
        let capitalSourceClassify = res.data.capitalSourceClassify
        if (capitalSourceClassify) {
          this.createPlanTableData = notSaveData
          capitalSourceClassify.suggestedPlanList.map(suggestedPlan => {
            if (suggestedPlan.isDelete === 0 && suggestedPlan.isInRecycleBin === 0) {
              if (suggestedPlan.fundPurposeFirstName) {
                if (suggestedPlan.fundPurposeSecondtName) {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
                } else {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
                }
              }
              this.createPlanTableData.push(suggestedPlan)
            }
          })
          this.newCode = capitalSourceClassify.code
          this.$emit('newCode', this.newCode)
        }
      })
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
    // 导出计划模板
    exportTemplate () {
      window.open(`${commonJs.planTemplateExportUrl}`)
    },
    /** 删除-弹窗事件 */
    removeBtnClick (name) {
      if (this.capitalSourceClassify.isLocked === 1) {
        // 新增修改记录
        if (Object.is(name, 'pro')) {
          this.createPlanTableData[this.createPlanTableData.indexOf(this.row)].isInRecycleBin = 1
        } else if (Object.is(name, 'recycle')) {
          this.createPlanTableData[this.createPlanTableData.indexOf(this.row)].isInRecycleBin = 2
        }
        this.createModifyData(this.row)
      } else {
        if (Object.is(name, 'pro')) {
          this.row.isInRecycleBin = 1
        } else if (Object.is(name, 'recycle')) {
          this.row.isInRecycleBin = 2
        }
        investmanage.saveSuggestedPlan(this.row).then((res) => {
          this.createPlanTableData.splice(this.createPlanTableData.indexOf(this.row), 1)
          this.getClassify()
        })
      }
      this.deleteDialogVisible = false
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
      if (tab.name === 'first') {
        this.createUpLoadData()
        this.getSuggestedPlanList()
      } else if (tab.name === 'second') {
      }
    },
    /** 搜索 */
    searchFun () {
      this.getSuggestedPlanList()
    },
    /** 分页 */
    handleSizeChange (val) {
    },
    handleCurrentChange (nowNum) {
    },
    /** 导入数据 */
    importData () {},
    /** 添加表格行 */
    addTableRow () {
      let obj = {}
      // 新增一行插入第一行
      this.createPlanTableData.unshift(obj)
    },
    /** 弹窗表格-删除 */
    deleteTableBtnClick (row, type) {
      if (Object.is(type, 'equip')) {
        this.dialogBaseInfoForm.equipmentTableData.splice(this.dialogBaseInfoForm.equipmentTableData.indexOf(row), 1)
      } else if (Object.is(type, 'enginer')) {
        this.dialogBaseInfoForm.engineerTableData.splice(this.dialogBaseInfoForm.engineerTableData.indexOf(row), 1)
      }
    },
    addDialogTableRow (type) {
      let obj = {}
      if (Object.is(type, 'equip')) {
        this.dialogBaseInfoForm.equipmentTableData.push(obj)
      } else if (Object.is(type, 'enginer')) {
        this.dialogBaseInfoForm.engineerTableData.push(obj)
      }
    },
    /** 取消-申报表 */
    cancelBtn () {
      this.suggestedPlan = {}
      this.dialogBaseInfoForm = {
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [], // 工程主要内容-表格
        investmentCompositionList: [{}, {}, {}, {}, {}]
      }
      this.totalForm = {} // 小计
      this.autoSearch = ''
      this.dialogVisible = false
      this.enginerDialogViseble = false
    },
    /** 保存-申报表 */
    saveBtn (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.capitalSourceClassify.isLocked === 1) {
            // 新增修改记录
            let suggestedPlan = this.createPlanTableData[this.createPlanTableData.indexOf(this.suggestedPlan)]
            suggestedPlan.projectType = this.dialogBaseInfoForm.projectType
            suggestedPlan.fundPurposeFirst = this.dialogBaseInfoForm.fundPurposeFirst
            if (this.dialogBaseInfoForm.fundPurposeSecond) {
              suggestedPlan.fundPurposeSecond = this.dialogBaseInfoForm.fundPurposeSecond
            } else {
              suggestedPlan.fundPurposeSecond = 0
            }
            suggestedPlan.declarationForm = this.dialogBaseInfoForm
            if (!this.dialogBaseInfoForm.fundPurposeSecond) {
              suggestedPlan.declarationForm.fundPurposeSecond = 0
            }
            // 设备现有状况
            this.$set(suggestedPlan.declarationForm, 'equipmentActualityList', this.dialogBaseInfoForm.equipmentTableData)
            // 备件主材
            this.$set(suggestedPlan.declarationForm, 'accessoriesList', this.dialogBaseInfoForm.engineerTableData)
            // 建设地点
            if (suggestedPlan.declarationForm.area) {
              suggestedPlan.declarationForm.provinceId = suggestedPlan.declarationForm.area[0]
              suggestedPlan.declarationForm.cityId = suggestedPlan.declarationForm.area[1]
              suggestedPlan.declarationForm.countyId = suggestedPlan.declarationForm.area[2]
            }
            // 投资构成
            let investmentCompositionList = suggestedPlan.declarationForm.investmentCompositionList
            // 非空对象集合
            let newInvestmentCompositionList = []
            investmentCompositionList.map((item, index) => {
              if (Object.keys(item).length) {
                item.type = index + 1
                newInvestmentCompositionList.push(item)
              }
            })
            this.$set(suggestedPlan.declarationForm, 'investmentCompositionList', newInvestmentCompositionList)
            this.createModifyData(suggestedPlan)
          } else {
            this.updateSuggestedPlan()
            this.saveDeclarationForm()
          }
          this.autoSearch = ''
          this.dialogVisible = false
          this.enginerDialogViseble = false
        }
      })
    },
    /** 更新-建议计划 */
    updateSuggestedPlan () {
      let index = this.suggestedPlan.index
      this.suggestedPlan.projectType = this.dialogBaseInfoForm.projectType
      this.suggestedPlan.fundPurposeFirst = this.dialogBaseInfoForm.fundPurposeFirst
      if (this.dialogBaseInfoForm.fundPurposeSecond) {
        this.suggestedPlan.fundPurposeSecond = this.dialogBaseInfoForm.fundPurposeSecond
      } else {
        this.suggestedPlan.fundPurposeSecond = 0
      }
      this.suggestedPlan.declarationForm = null
      investmanage.saveSuggestedPlan(this.suggestedPlan).then(res => {
        let suggestedPlan = res.data.suggestedPlan
        if (suggestedPlan.fundPurposeFirstName) {
          if (suggestedPlan.fundPurposeSecondtName) {
            suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
          } else {
            suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
          }
          this.createPlanTableData.splice(index, 1, suggestedPlan)
        }
      })
    },
    /** 保存-申请表 */
    saveDeclarationForm () {
      if (!this.dialogBaseInfoForm.fundPurposeSecond) {
        this.dialogBaseInfoForm.fundPurposeSecond = 0
      }
      this.dialogBaseInfoForm.suggestedPlanCode = this.suggestedPlan.code
      this.dialogBaseInfoForm.capitalSource = this.suggestedPlan.capitalSource
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
      investmanage.saveDeclarationForm(this.dialogBaseInfoForm).then(res => {
        this.getDeclarationList()
      })
    },
    /** 提交-建议计划 */
    submitCreatePlan () {
      if (Number(this.capitalSourceClassify.surplusUsageQuota) < 0) {
        this.$confirm('已使用额度大于可使用额度，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then((res) => {
          this.capitalSourceClassify.status = 2
          this.capitalSourceClassify.isLocked = 1
          this.capitalSourceClassify.operate = 3
          this.capitalSourceClassify.firstClassifyCode = this.firstClassifyCode
          investmanage.saveClassify(this.capitalSourceClassify).then((res) => {
            if (Object.is(res.data.resCode, '0000')) {
              this.$emit('childToParent', 'checkCard')
            } else {
              this.capitalSourceClassify.isLocked = 0
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      } else {
        this.capitalSourceClassify.status = 2
        this.capitalSourceClassify.isLocked = 1
        this.capitalSourceClassify.operate = 3
        this.capitalSourceClassify.firstClassifyCode = this.firstClassifyCode
        investmanage.saveClassify(this.capitalSourceClassify).then((res) => {
          if (Object.is(res.data.resCode, '0000')) {
            this.$emit('childToParent', 'checkCard')
          } else {
            this.capitalSourceClassify.isLocked = 0
          }
        })
      }
    },
    /** 退回 */
    returnPlan () {
      this.dialogReturnVisible = true
    },
    /** 退回-确定-弹窗 */
    confirmReturnReason (val) {
      this.$refs[val].validate((vaild) => {
        if (vaild) {
          if (!this.returnRorm.returnReason.trim()) {
            this.$message({
              type: 'error',
              message: '请输入退回原因'
            })
            return false
          }
          this.updateStatus(3, 4).then(this.saveRecord()).then(this.goBack())
        } else {
          return false
        }
      })
    },
    updateStatus (status, operate) {
      this.capitalSourceClassify.status = status
      this.capitalSourceClassify.isLocked = 1
      this.capitalSourceClassify.operate = operate
      this.capitalSourceClassify.firstClassifyCode = this.firstClassifyCode
      return investmanage.saveClassify(this.capitalSourceClassify)
    },
    saveRecord () {
      let obj = {
        relatedClassifyCode: this.capitalSourceClassify.code,
        relatedFirstClassifyCode: this.firstClassifyCode,
        recordType: 3,
        returnReason: this.returnRorm.returnReason
      }
      return investmanage.saveRecord(obj)
    },
    goBack () {
      this.$router.go(-1)
    },
    /** 查询建议计划-表格操作 */
    tableBtnClick (row) {
      // 查看
      this.dialogVisible = true
    },
    /** 上传成功 */
    handleSuccess (response, file, fileList) {
      // 刷新页面数据
      this.getSuggestedPlanList()
      this.getClassify()
      this.formInfo = {}
      if (response.resCode === '0000') {
        this.$message({
          message: response.resMessage,
          type: 'success',
          duration: 5 * 1000
        })
      } else if (response.resCode !== '0000') {
        this.$message({
          message: response.resMessage,
          type: 'error',
          duration: 5 * 1000
        })
      }
      return response
    },
    /** 表格操作 */
    handleTableClick (row, type, index) {
      if (Object.is(type, 'look')) {
        // 查看
        this.dialogVisible = true
      } else if (Object.is(type, 'edit')) {
        // 编辑
        this.dialogBaseInfoForm = {
          equipmentTableData: [], // 设备现有状况-表格
          engineerTableData: [], // 工程主要内容-表格
          investmentCompositionList: [{}, {}, {}, {}, {}]
        }
        this.totalForm = {} // 小计
        this.dialogBaseInfoForm.suggestedPlanCode = row.code
        this.dialogBaseInfoForm.projectName = row.projectName
        this.dialogBaseInfoForm.capitalSourceName = row.capitalSourceName
        if (this.capitalSourceClassify.capitalSourceId === 0) {
          this.dialogBaseInfoForm.plannedCosts = row.budgetAmount
          this.dialogBaseInfoForm.quantities = row.budgetQuantities
        } else {
          this.dialogBaseInfoForm.plannedCosts = row.currentPlanAmount
          this.dialogBaseInfoForm.quantities = row.currentPlanQuantities
        }
        this.suggestedPlan = row
        this.suggestedPlan.index = index
        this.getFundPurposeFirstList()
        this.getDeclarationForm(row)
        this.getCapitalSource()
        if (row.classify === 1) {
          // 专项资金
          this.dialogVisible = true
        } else {
          // 工程建设
          this.enginerDialogViseble = true
        }
      } else if (Object.is(type, 'save')) {
        // 保存
        this.saveSuggestedPlan(row, index)
      } else if (Object.is(type, 'del')) {
        // 删除
        this.clearValidate('projectNameForm', index)
        this.clearValidate('contentForm', index)
        this.clearValidate('quantitiesUnitForm', index)
        if (this.capitalSourceClassify.capitalSourceId === 0) {
          this.clearValidate('budgetQuantitiesForm', index)
          this.clearValidate('budgetAmountForm', index)
        } else {
          this.clearValidate('quantitiesForm', index)
          this.clearValidate('amountForm', index)
        }
        if (row.objectId) {
          if (this.capitalSourceClassify.status === 0 || !row.declarationForm) {
            this.deleteSuggestedPlan(row)
          } else {
            this.row = row
            this.deleteDialogVisible = true
          }
        } else {
          this.createPlanTableData.splice(this.createPlanTableData.indexOf(row), 1)
        }
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
    /** 清除校验 */
    clearValidate (formName, index) {
      this.$refs[formName + index].clearValidate()
    },
    /** 单条保存 */
    saveSuggestedPlan (row, index) {
      let projectNameValidate = this.validateForm('projectNameForm', index)
      let contentValidate = this.validateForm('contentForm', index)
      let quantitiesUnitValidate = this.validateForm('quantitiesUnitForm', index)
      let quantitiesValidate = false
      let amountValidate = false
      if (this.capitalSourceClassify.capitalSourceId === 0) {
        quantitiesValidate = this.validateForm('budgetQuantitiesForm', index)
        amountValidate = this.validateForm('budgetAmountForm', index)
      } else {
        quantitiesValidate = this.validateForm('quantitiesForm', index)
        amountValidate = this.validateForm('amountForm', index)
      }
      if (projectNameValidate && contentValidate && quantitiesUnitValidate && quantitiesValidate && amountValidate) {
        if (this.capitalSourceClassify.isLocked === 1) {
          // 判断数据是否修改
          let oldRow = this.oldPlanTableData[this.createPlanTableData.indexOf(row)]
          if (oldRow) {
            if (oldRow.projectName === row.projectName &&
              oldRow.content === row.content &&
              oldRow.budgetQuantities === row.budgetQuantities &&
              oldRow.budgetAmount === row.budgetAmount &&
              oldRow.currentPlanQuantities === row.currentPlanQuantities &&
              oldRow.currentPlanAmount === row.currentPlanAmount) {
              this.$message.error('数据未做修改，保存无效！')
              return false
            }
          }
          if (!row.objectId) {
            row.objectId = 11111
            this.createNewPlan(row)
          }
          // 新增维护记录
          this.createModifyData(row)
        } else {
          // 修改维护记录
          if (!row.objectId) {
            this.createNewPlan(row)
          }
          if (row.declarationForm) {
            row.declarationForm.projectName = row.projectName
            if (this.capitalSourceClassify.capitalSourceId === 0) {
              row.declarationForm.plannedCosts = row.budgetAmount
              row.declarationForm.quantities = row.budgetQuantities
            } else {
              row.declarationForm.plannedCosts = row.currentPlanAmount
              row.declarationForm.quantities = row.currentPlanQuantities
            }
          }
          let index = this.createPlanTableData.indexOf(row)
          investmanage.saveSuggestedPlan(row).then((res) => {
            let suggestedPlan = res.data.suggestedPlan
            if (suggestedPlan.fundPurposeFirstName) {
              if (suggestedPlan.fundPurposeSecondtName) {
                suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
              } else {
                suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
              }
            }
            this.createPlanTableData.splice(index, 1, suggestedPlan)
            this.getClassify()
          })
        }
      }
    },
    /** 单条删除 */
    deleteSuggestedPlan (obj) {
      this.$confirm('此操作永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((res) => {
        if (this.capitalSourceClassify.isLocked === 1) {
          this.createPlanTableData[this.createPlanTableData.indexOf(obj)].isDelete = 1
          this.createModifyData(obj)
        } else {
          investmanage.deleteSuggestedPlan(obj.objectId).then((res) => {
            this.createPlanTableData.splice(this.createPlanTableData.indexOf(obj), 1)
            this.getClassify()
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    createNewPlan (row) {
      row.investmentProposalSchemeCode = this.capitalSourceClassify.investmentProposalSchemeCode
      row.capitalSourceClassifyCode = this.capitalSourceClassify.code
      row.enterpriseId = this.$route.query.enterpriseId
      if (this.capitalSourceClassify.capitalSourceId) {
        row.capitalSource = this.capitalSourceClassify.capitalSourceId
      } else {
        row.capitalSource = 0
      }
      row.projectCreateType = 4
      row.isPlaned = 0
      row.isInRecycleBin = 0
    },
    /** 新增修改记录 */
    createModifyData (row) {
      let newClassify = this.capitalSourceClassify
      newClassify.operate = 2
      newClassify.objectId = null
      newClassify.status = 2
      newClassify.firstClassifyCode = this.firstClassifyCode
      newClassify.enterpriseId = this.$route.query.enterpriseId
      let saveData = this.createPlanTableData.filter(suggestedPlan => {
        if (suggestedPlan.objectId) {
          return suggestedPlan
        }
      })
      this.recycleTableData.map(suggestedPlan => {
        saveData.push(suggestedPlan)
      })
      let notSaveData = this.createPlanTableData.filter(suggestedPlan => {
        if (!suggestedPlan.objectId) {
          return suggestedPlan
        }
      })
      saveData.map(suggestedPlan => {
        suggestedPlan.objectId = null
        suggestedPlan.capitalSource = this.capitalSourceClassify.capitalSourceId
        suggestedPlan.enterpriseId = this.$route.query.enterpriseId
        if (suggestedPlan.declarationForm) {
          suggestedPlan.declarationForm.objectId = null
          suggestedPlan.declarationForm.projectName = suggestedPlan.projectName
          if (this.capitalSourceClassify.capitalSourceId === 0) {
            suggestedPlan.declarationForm.plannedCosts = suggestedPlan.budgetAmount
            suggestedPlan.declarationForm.quantities = suggestedPlan.budgetQuantities
          } else {
            suggestedPlan.declarationForm.plannedCosts = suggestedPlan.currentPlanAmount
            suggestedPlan.declarationForm.quantities = suggestedPlan.currentPlanQuantities
          }
          suggestedPlan.declarationForm.equipmentActualityList.map((equipmentActuality) => {
            equipmentActuality.objectId = null
            return equipmentActuality
          })
          suggestedPlan.declarationForm.accessoriesList.map((accessories) => {
            accessories.objectId = null
            return accessories
          })
          suggestedPlan.declarationForm.investmentCompositionList.map((investmentComposition) => {
            investmentComposition.objectId = null
            return investmentComposition
          })
        }
        return suggestedPlan
      })
      this.$set(newClassify, 'suggestedPlanList', saveData)
      investmanage.saveClassify(newClassify).then((res) => {
        let capitalSourceClassify = res.data.capitalSourceClassify
        if (capitalSourceClassify) {
          this.createPlanTableData = notSaveData
          capitalSourceClassify.suggestedPlanList.map(suggestedPlan => {
            if (suggestedPlan.isDelete === 0 && suggestedPlan.isInRecycleBin === 0) {
              if (suggestedPlan.fundPurposeFirstName) {
                if (suggestedPlan.fundPurposeSecondtName) {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
                } else {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
                }
              }
              this.createPlanTableData.push(suggestedPlan)
            }
          })
          this.newCode = capitalSourceClassify.code
          this.$emit('editToMain', this.newCode)
        }
      })
    },
    /** 查询建议计划列表 */
    getSuggestedPlanList () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        capitalSourceClassifyCode: this.capitalSourceClassify.code,
        isInRecycleBin: 0,
        messageLike: this.searchCreateForm.messageLike || null,
        fundPurposeFirst: this.searchCreateForm.fundPurposeFirst || null,
        fundPurposeSecond: this.searchCreateForm.fundPurposeSecond || null,
        transferToSource: this.searchCreateForm.transferToSource
      }
      investmanage.getSuggestedPlanList(query).then((res) => {
        if (res.data.suggestedPlanList) {
          this.createPlanTableData = res.data.suggestedPlanList
          let resultJson = JSON.stringify(this.createPlanTableData)
          this.oldPlanTableData = JSON.parse(resultJson)
          this.createPlanTableData.map(suggestedPlan => {
            if (suggestedPlan.fundPurposeFirstName) {
              if (suggestedPlan.fundPurposeSecondtName) {
                suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
              } else {
                suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
              }
            }
          })
        }
      })
    },
    /** 查询在回收站或项目储备库的建议计划列表 */
    getRecycleList () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        capitalSourceClassifyCode: this.capitalSourceClassify.code,
        includeIsInRecycleBin: '1,2',
        isDelete: 0
      }
      investmanage.getNewestSuggestedPlanList(query).then((res) => {
        if (res.data.suggestedPlanList) {
          this.recycleTableData = res.data.suggestedPlanList
        }
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
          if (!this.dialogBaseInfoForm.fundPurposeSecond) {
            this.dialogBaseInfoForm.fundPurposeSecond = ''
          }
          this.dialogBaseInfoForm.equipmentTableData = this.dialogBaseInfoForm.equipmentActualityList
          this.dialogBaseInfoForm.engineerTableData = this.dialogBaseInfoForm.accessoriesList
          this.dialogBaseInfoForm.area = [this.dialogBaseInfoForm.provinceId, this.dialogBaseInfoForm.cityId, this.dialogBaseInfoForm.countyId]
          let investmentCompositionList = this.dialogBaseInfoForm.investmentCompositionList
          let newInvestmentCompositionList = [{}, {}, {}, {}, {}]
          investmentCompositionList.map(item => {
            newInvestmentCompositionList[item.type - 1] = item
          })
          this.$set(this.dialogBaseInfoForm, 'investmentCompositionList', newInvestmentCompositionList)
          this.calculateTotal()
          this.getFundPurposeSecondList(this.dialogBaseInfoForm.fundPurposeFirst)
        }
      })
    },
    createUpLoadData () {
      this.upLoadData.enterpriseId = this.$store.getters.authUser.enterpriseId
      this.upLoadData.creator = this.$store.getters.authUser.userId
      this.upLoadData.code = this.capitalSourceClassify.code
      this.upLoadData.capitalSourceId = this.capitalSourceClassify.capitalSourceId
      this.upLoadData.investmentProposalSchemeCode = this.$route.query.code
      this.upLoadData.year = this.$route.query.year
    },
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0
      }
      if (this.capitalSourceClassify.capitalSourceId) {
        query.capitalSourceId = this.capitalSourceClassify.capitalSourceId
        query.type = 1
      } else {
        query.type = 2
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
      if (this.capitalSourceClassify.capitalSourceId) {
        query.capitalSourceId = this.capitalSourceClassify.capitalSourceId
        query.type = 1
      } else {
        query.type = 2
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = res.data.fundPurposeList
      })
    },
    /** 查询资金投向 - 二级 */
    getSecondByFirstList (operate) {
      let parentId = null
      if (Object.is(operate, 'query')) {
        if (this.searchCreateForm.fundPurposeSecond) {
          this.searchCreateForm.fundPurposeSecond = ''
        }
        parentId = this.searchCreateForm.fundPurposeFirst
      } else if (Object.is(operate, 'edit')) {
        if (this.dialogBaseInfoForm.fundPurposeSecond) {
          this.dialogBaseInfoForm.fundPurposeSecond = ''
        }
        parentId = this.dialogBaseInfoForm.fundPurposeFirst
      }
      let query = {
        level: 2,
        parentId: parentId
      }
      if (this.capitalSourceClassify.capitalSourceId) {
        query.capitalSourceId = this.capitalSourceClassify.capitalSourceId
        query.type = 1
      } else {
        query.type = 2
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = res.data.fundPurposeList
      })
    },
    getClassify () {
      let query = {
        code: this.classifyCode
      }
      investmanage.getClassify(query).then(res => {
        this.capitalSourceClassify = res.data.capitalSourceClassify
      })
    },
    /** 查询同一资金来源下首个资源管理分类编号 */
    getFirstClassifyCode () {
      let query = {
        investmentProposalSchemeCode: this.capitalSourceClassify.investmentProposalSchemeCode,
        capitalSourceId: this.capitalSourceClassify.capitalSourceId,
        isDelete: 0
      }
      investmanage.getFirstClassifyCode(query).then(res => {
        this.firstClassifyCode = res.data.firstClassifyCode
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
    getDeclarationList () {
      investmanage.getHistoryDeclList(this.$store.getters.authUser.enterpriseId,
        this.capitalSourceClassify.capitalSourceId).then(res => {
        this.declarationFormList = res.data.declarationFormList
      })
    },
    getClassifyList () {
      let query = {
        investmentProposalSchemeCode: this.$route.query.code
      }
      investmanage.getClassifyList(query).then((res) => {
        let data = res.data.capitalSourceClassifyList
        if (data) {
          this.classifyList = data.filter(classify => {
            if (classify.capitalSourceId === 0) {
              classify.capitalSourceName = '工程建设'
            }
            if (classify.capitalSourceId !== this.capitalSourceClassify.capitalSourceId) {
              return classify
            }
          })
        }
      })
    }
  },
  // 实例创建完成
  mounted () {
    let query = {
      code: this.classifyCode
    }
    investmanage.getClassify(query).then(res => {
      this.capitalSourceClassify = res.data.capitalSourceClassify
    }).then(res => {
      this.createUpLoadData()
      this.getSuggestedPlanList()
      this.getRecycleList()
      this.getFundPurposeFirstList()
      this.getFirstClassifyCode()
      this.getDeclarationList()
    })
    this.getClassifyList()
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
    }
  },
  components: {
    treeGrid
  }
}
</script>
<style lang="less">
  #cardedit-page {
    .add-row {
      height: 40px;
      padding-bottom: 15px;
    }
    .table-cont {
      .demo-ruleForm .el-form-item {
        margin-bottom: 0;
        .el-form-item__error {
          display: none;
        }
      }
    }
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
        overflow: hidden;
        display: inline-block;
        border-collapse: collapse;
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
      enginer .table-form {
        .el-form-item .el-form-item__content {
          margin-left: 0 !important;
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
