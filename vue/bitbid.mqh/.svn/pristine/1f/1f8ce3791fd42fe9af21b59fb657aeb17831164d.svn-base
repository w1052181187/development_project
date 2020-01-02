<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="checkedit-page">
    <!--搜索区域start-->
    <div class="balance-search">
      <el-form :model="searchCreateForm" :inline="true" class="demo-form-inline" style="text-align: left;">
        <el-form-item label="项目名称:">
          <el-input v-model="searchCreateForm.messageLike" placeholder="请填写项目名称" clearable>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchFun" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--搜索区域end-->
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
      </div>
      <el-table
        class="table-cont"
        :data="planTableData"
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
          align="center"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button @click="handleTableClick(scope.row,'save', scope.$index)" type="text" size="medium">保存</el-button>
            <el-button @click="handleTableClick(scope.row,'del', scope.$index)" type="text" size="medium">删除</el-button>
            <el-button @click="handleTableClick(scope.row,'edit')" type="text" size="medium" v-show="scope.row.objectId">编辑申报表</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="submit-btn">
      <el-button type="primary" @click="submitPlan">提 交</el-button>
      <el-button type="primary" @click="returnPlan">退 回</el-button>
    </div>
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
        @close="cancelBtnClick"
        :visible.sync="dialogVisible"
        width="80%"
        center>
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
                    <el-input v-model="dialogBaseInfoForm.plannedCosts"></el-input>
                  </el-form-item>
                </td>
              </tr>
              <tr>
                <td>资金投向</td>
                <td colspan="5">
                  <el-form-item prop="fundPurposeFirst" style="float: left">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeFirst" placeholder="请选择" @change="getSecondByFirstList('edit')" clearable>
                      <el-option
                        v-for="item in fundPurposeFirstList"
                        :key="item.objectId"
                        :label="item.name"
                        :value="item.objectId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <span style="display:inline-block;line-height:40px;float: left;" v-if="fundPurposeSecondList.length>0">-</span>
                  <el-form-item prop="fundPurposeSecond" style="float: left" v-if="fundPurposeSecondList.length>0">
                    <el-select v-model="dialogBaseInfoForm.fundPurposeSecond" placeholder="请选择" clearable>
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
                    <el-input v-model="dialogBaseInfoForm.quantities"></el-input>
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
                <td>拟执行时间</td>
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
                  <el-button @click="deleteTableRowClick(scope.row, 'equip')" type="text" size="small">删除</el-button>
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
                  <el-button @click="deleteTableRowClick(scope.row, 'enginer')" type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="handle-add-btn">
              <el-button icon="el-icon-plus" @click="addDialogTableRow('enginer')" style="width:100%"></el-button>
            </div>
          </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveBtnClick('dialogBaseInfoForm')">保存</el-button>
          <el-button @click="cancelBtnClick">取消</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {investmanage} from 'api/index'
import {commonJs} from 'common/js/common'
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
      upLoad: commonJs.investExcelUploadUrl,
      upLoadData: {},
      fileList: [],
      currentPage: 1,
      dialogReturnVisible: false, // 退回-弹窗
      returnRorm: {},
      searchCreateForm: {}, // 搜索-创建建议计划表单
      capitalSourceList: [], // 资金来源
      planTableData: [],
      oldPlanTableData: [],
      dialogVisible: false,
      dialogBaseInfoForm: {
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [] // 工程主要内容-表格
      },
      dialogBaseInfoRules: {
        plannedCosts: [
          { required: true, message: '请填写计划费用', trigger: 'blur' },
          {validator: checkAmount}
        ],
        fundPurposeFirst: { required: true, message: '请填写资金投向', trigger: 'blur' },
        fundPurposeSecond: { required: true, message: '请填写资金投向', trigger: 'blur' },
        equipmentAbsentee: {validator: checkQuantities},
        equipmentInUse: {validator: checkQuantities},
        equipmentReserve: {validator: checkQuantities},
        quantities: [
          { required: true, message: '请填写工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ],
        unitType: [
          { required: true, message: '请填写新增设备型号、参数', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        maintenanceCycle: [
          { required: true, message: '请填写（大修、更新）周期', trigger: 'blur' },
          { min: 1, max: 50, message: '请输入1~50个字符', trigger: 'blur' }
        ],
        lastMaintenanceTime: { required: true, message: '请选择上次（大修、更新）时间', trigger: 'change' },
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
      projectTypeList: [
        {label: '工程', value: 1},
        {label: '货物', value: 2},
        {label: '服务', value: 3}
      ],
      isPlanList: [
        {label: '是', value: 1},
        {label: '否', value: 0}
      ],
      fundPurposeFirstList: [],
      fundPurposeSecondList: [],
      suggestedPlan: {},
      firstClassifyCode: '', // 首个资金来源分类编号
      rules: {
        returnReason: [
          { required: true, message: '请填写退回原因', trigger: 'blur' }
        ]
      },
      projectNameRules: {
        projectName: [
          { required: true, message: '请填写项目名称', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      }, // 项目名称校验
      contentRules: {
        content: [
          { required: true, message: '请填写工程简要内容及设备型号', trigger: 'blur' },
          { min: 1, max: 200, message: '请输入1~200个字符', trigger: 'blur' }
        ]
      },
      quantitiesUnitRules: {
        quantitiesUnit: [
          { required: true, message: '请填写工程量单位', trigger: 'blur' },
          { min: 1, max: 10, message: '请输入1~10个字符', trigger: 'blur' }
        ]
      },
      // 工程简要内容及设备型号校验
      budgetQuantitiesRules: {
        budgetQuantities: {validator: checkQuantities}
      }, // 工程量（概算）
      budgetAmountRules: {
        budgetAmount: {validator: checkAmount}
      }, // 概算金额
      quantitiesRules: {
        currentPlanQuantities: [
          { required: true, message: '请填写本年计划工程量', trigger: 'blur' },
          {validator: checkQuantities}
        ]
      }, // 工程量（本年计划）
      amountRules: {
        currentPlanAmount: [
          { required: true, message: '请填写本年计划金额', trigger: 'blur' },
          {validator: checkAmount}
        ]
      }, // 本年计划金额
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
      classify: {},
      capitalSourceName: '', // 工程建设项目资金来源
      newCode: '' // 当前tabs页code
    }
  },
  computed: {
  },
  watch: {
    activeCode: function (newV, oldV) {
      if (newV !== 'recycle') {
        let query = {
          code: this.activeCode
        }
        investmanage.getClassify(query).then(res => {
          this.classify = res.data.capitalSourceClassify
        }).then(res => {
          this.createUpLoadData()
          this.getFirstClassifyCode()
          if (this.newCode !== this.activeCode) {
            this.newCode = ''
            this.getSuggestedPlanList()
          }
        })
      }
    }
  },
  props: {
    activeCode: String
  },
  created () {
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 搜索 */
    searchFun () {
      this.getSuggestedPlanList()
    },
    /** 添加表格行 */
    addTableRow () {
      let obj = {}
      // 新增一行插入第一行
      this.planTableData.unshift(obj)
    },
    /** 上传成功 */
    handleSuccess (response, file, fileList) {
      // 刷新页面数据
      this.getSuggestedPlanList()
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
    /** 申报表-取消 */
    cancelBtnClick () {
      this.suggestedPlan = {}
      this.dialogBaseInfoForm = {
        equipmentTableData: [], // 设备现有状况-表格
        engineerTableData: [] // 工程主要内容-表格
      }
      this.dialogVisible = false
    },
    /** 申报表-保存 */
    saveBtnClick (form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          if (this.classify.isLocked === 1) {
            // 新增修改记录
            let suggestedPlan = this.planTableData[this.planTableData.indexOf(this.suggestedPlan)]
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
            this.createModifyData(suggestedPlan)
          } else {
            let that = this
            this.updateSuggestedPlan().then(this.saveDeclarationForm()).then(function () {
              setTimeout(that.getSuggestedPlanList, 500)
            })
          }
          this.dialogVisible = false
        }
      })
    },
    /** 更新-建议计划 */
    updateSuggestedPlan () {
      this.suggestedPlan.projectType = this.dialogBaseInfoForm.projectType
      this.suggestedPlan.fundPurposeFirst = this.dialogBaseInfoForm.fundPurposeFirst
      if (this.dialogBaseInfoForm.fundPurposeSecond) {
        this.suggestedPlan.fundPurposeSecond = this.dialogBaseInfoForm.fundPurposeSecond
      } else {
        this.suggestedPlan.fundPurposeSecond = 0
      }
      this.suggestedPlan.declarationForm = null
      return investmanage.saveSuggestedPlan(this.suggestedPlan)
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
      return investmanage.saveDeclarationForm(this.dialogBaseInfoForm)
    },
    /** 弹窗-表格删除行 */
    deleteTableRowClick (row, type) {
      if (Object.is(type, 'equip')) {
        this.dialogBaseInfoForm.equipmentTableData.splice(this.dialogBaseInfoForm.equipmentTableData.indexOf(row), 1)
      } else if (Object.is(type, 'enginer')) {
        this.dialogBaseInfoForm.engineerTableData.splice(this.dialogBaseInfoForm.engineerTableData.indexOf(row), 1)
      }
    },
    /** 弹窗-表格自增行 */
    addDialogTableRow (type) {
      let obj = {}
      if (Object.is(type, 'equip')) {
        this.dialogBaseInfoForm.equipmentTableData.push(obj)
      } else if (Object.is(type, 'enginer')) {
        this.dialogBaseInfoForm.engineerTableData.push(obj)
      }
    },
    /** 表格操作 */
    handleTableClick (row, type, index) {
      if (Object.is(type, 'del')) {
        this.clearValidate('projectNameForm', index)
        this.clearValidate('contentForm', index)
        this.clearValidate('quantitiesUnitForm', index)
        this.clearValidate('quantitiesForm', index)
        this.clearValidate('amountForm', index)
        this.clearValidate('budgetQuantitiesForm', index)
        this.clearValidate('budgetAmountForm', index)
        this.deleteClick(row)
      } else if (Object.is(type, 'edit')) {
        this.dialogBaseInfoForm = {
          equipmentTableData: [], // 设备现有状况-表格
          engineerTableData: [] // 工程主要内容-表格
        }
        this.suggestedPlan = row
        this.dialogBaseInfoForm.projectName = row.projectName
        this.dialogBaseInfoForm.capitalSourceName = row.capitalSourceName
        this.getFundPurposeFirstList()
        this.getDeclarationForm(row)
        this.getCapitalSource()
        this.dialogVisible = true
      } else if (Object.is(type, 'save')) {
        this.saveClick(row, index)
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
    saveClick (row, index) {
      let projectNameValidate = this.validateForm('projectNameForm', index)
      let contentValidate = this.validateForm('contentForm', index)
      let quantitiesUnitValidate = this.validateForm('quantitiesUnitForm', index)
      let quantitiesValidate = this.validateForm('quantitiesForm', index)
      let amountValidate = this.validateForm('amountForm', index)
      let budgetQuantitiesValidate = this.validateForm('budgetQuantitiesForm', index)
      let budgetAmountValidate = this.validateForm('budgetAmountForm', index)
      if (projectNameValidate && contentValidate && quantitiesUnitValidate &&
        quantitiesValidate && amountValidate && budgetQuantitiesValidate && budgetAmountValidate) {
        if (this.classify.isLocked === 1) {
          // 判断数据是否修改
          let oldRow = this.oldPlanTableData[this.planTableData.indexOf(row)]
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
          }
          let index = this.planTableData.indexOf(row)
          investmanage.saveSuggestedPlan(row).then((res) => {
            this.$set(this.planTableData[index], 'objectId', res.data.suggestedPlan.objectId)
            this.planTableData[index] = res.data.suggestedPlan
          })
        }
      }
    },
    /** 单条删除 */
    deleteSuggestedPlan (obj) {
      if (obj.objectId) {
        if (this.classify.isLocked === 1) {
          // 新增修改记录
          this.planTableData[this.planTableData.indexOf(obj)].isInRecycleBin = 1
          this.planTableData[this.planTableData.indexOf(obj)].isDelete = 1
          this.createModifyData(obj)
        } else {
          obj.isInRecycleBin = 1
          obj.isDelete = 1
          investmanage.saveSuggestedPlan(obj).then((res) => {
            this.planTableData.splice(this.planTableData.indexOf(obj), 1)
          })
        }
      } else {
        this.planTableData.splice(this.planTableData.indexOf(obj), 1)
      }
    },
    createNewPlan (row) {
      row.investmentProposalSchemeCode = this.classify.investmentProposalSchemeCode
      row.capitalSourceClassifyCode = this.classify.code
      row.enterpriseId = this.$store.getters.authUser.enterpriseId
      if (this.classify.capitalSourceId) {
        row.capitalSource = this.classify.capitalSourceId
      } else {
        row.capitalSource = 0
      }
      row.projectCreateType = 4
      row.isPlaned = 0
      row.isInRecycleBin = 0
    },
    /** 新增修改记录 */
    createModifyData (row) {
      let newClassify = this.classify
      newClassify.operate = 2
      newClassify.objectId = null
      newClassify.firstClassifyCode = this.firstClassifyCode
      let saveData = this.planTableData.filter(suggestedPlan => {
        if (suggestedPlan.objectId) {
          return suggestedPlan
        }
      })
      let notSaveData = this.planTableData.filter(suggestedPlan => {
        if (!suggestedPlan.objectId) {
          return suggestedPlan
        }
      })
      saveData.map(suggestedPlan => {
        if (suggestedPlan.objectId) {
          suggestedPlan.objectId = null
          suggestedPlan.capitalSource = this.classify.capitalSourceId
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
        }
      })
      this.$set(newClassify, 'suggestedPlanList', saveData)
      investmanage.saveClassify(newClassify).then((res) => {
        let capitalSourceClassify = res.data.capitalSourceClassify
        if (capitalSourceClassify) {
          this.planTableData = notSaveData
          capitalSourceClassify.suggestedPlanList.map(suggestedPlan => {
            if (suggestedPlan.isDelete === 0) {
              if (suggestedPlan.fundPurposeFirstName) {
                if (suggestedPlan.fundPurposeSecondtName) {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}/` + `${suggestedPlan.fundPurposeSecondtName}`
                } else {
                  suggestedPlan.fundPurpose = `${suggestedPlan.fundPurposeFirstName}`
                }
              }
              this.planTableData.push(suggestedPlan)
            }
          })
          this.newCode = capitalSourceClassify.code
          this.$emit('getChildValue', capitalSourceClassify.code)
        }
      })
    },
    /** 提交 */
    submitPlan () {
      if (Number(this.classify.surplusUsageQuota) < 0) {
        this.$message.error('已使用额度大于可使用额度，不能提交！')
      } else {
        this.updateStatus(2, 3).then(this.goBack())
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
      this.classify.status = status
      this.classify.isLocked = 1
      this.classify.operate = operate
      this.classify.firstClassifyCode = this.firstClassifyCode
      return investmanage.saveClassify(this.classify)
    },
    saveRecord () {
      let obj = {
        relatedClassifyCode: this.activeCode,
        relatedFirstClassifyCode: this.firstClassifyCode,
        recordType: 3,
        returnReason: this.returnRorm.returnReason
      }
      return investmanage.saveRecord(obj)
    },
    goBack () {
      this.$router.push({path: '.'})
    },
    // 删除
    deleteClick (row) {
      this.$confirm('此操作将归入项目储备库, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((res) => {
        // 执行方法
        this.deleteSuggestedPlan(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /** 查询建议计划列表 */
    getSuggestedPlanList () {
      this.planTableData = []
      let query = {
        investmentProposalSchemeCode: this.$route.query.code,
        capitalSourceClassifyCode: this.activeCode,
        messageLike: this.searchCreateForm.messageLike || null
      }
      investmanage.getSuggestedPlanList(query).then((res) => {
        if (res.data.suggestedPlanList) {
          this.planTableData = res.data.suggestedPlanList
          let resultJson = JSON.stringify(this.planTableData)
          this.oldPlanTableData = JSON.parse(resultJson)
          this.planTableData.map(suggestedPlan => {
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
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0
      }
      if (this.classify.capitalSourceId) {
        query.capitalSourceId = this.classify.capitalSourceId
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
      if (this.classify.capitalSourceId) {
        query.capitalSourceId = this.classify.capitalSourceId
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
      if (this.classify.capitalSourceId) {
        query.capitalSourceId = this.classify.capitalSourceId
        query.type = 1
      } else {
        query.type = 2
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeSecondList = res.data.fundPurposeList
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
          this.getFundPurposeSecondList(this.dialogBaseInfoForm.fundPurposeFirst)
        }
      })
    },
    createUpLoadData () {
      this.upLoadData.enterpriseId = this.$store.getters.authUser.enterpriseId
      this.upLoadData.creator = this.$store.getters.authUser.userId
      this.upLoadData.code = this.classify.code
      this.upLoadData.capitalSourceId = this.classify.capitalSourceId
      this.upLoadData.isLocked = this.classify.isLocked
      this.upLoadData.investmentProposalSchemeCode = this.$route.query.code
      this.upLoadData.year = this.$route.query.year
      this.upLoadData.operate = 2
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
    let query = {
      code: this.activeCode
    }
    investmanage.getClassify(query).then(res => {
      this.classify = res.data.capitalSourceClassify
    }).then(res => {
      this.createUpLoadData()
      this.getFirstClassifyCode()
      this.getSuggestedPlanList()
    })
  },
  components: {
  }
}
</script>
<style lang="less">
#checkedit-page {
  .table-cont {
    .demo-ruleForm .el-form-item {
      margin-bottom: 0;
      .el-form-item__error {
        display: none;
      }
    }
  }
}
</style>
