<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="coalsafety-use-sum">
    <div v-if="this.$route.path == '/admin/statistics/coalsafe-use-sum'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafe-use-sum' }">煤安费提取和使用汇总</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-row>
            <el-col :span="10">
              <el-form-item label="金额(万元):">
                <el-select v-model="searchForm.amountType" clearable placeholder="请选择" style="float:left;width:160px;" @change="changeAmount" @clear="clear">
                  <el-option label="年初结余" value="1"></el-option>
                  <el-option label="提取总额本月" value="2"></el-option>
                  <el-option label="提取总额累计" value="3"></el-option>
                  <el-option label="使用总额本月" value="4"></el-option>
                  <el-option label="使用总额累计" value="5"></el-option>
                  <el-option label="期末结余" value="6"></el-option>
                </el-select>
                <span style="display:inline-block;color: #999;float:left;">介于</span>
                <el-input placeholder="请输入数字" :disabled="limitFlag" @input="handleLowerInput" v-model="searchForm.lowerLimit" class="input-with-select" style="float:left;width:100px;">
                </el-input>
                <span style="display:inline-block;color: #ddd;float:left;">--</span>
                <el-input placeholder="请输入数字" :disabled="limitFlag" @input="handleUpperInput" v-model="searchForm.upperLimit" class="input-with-select" style="float:left;width:100px;">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="单位名称:">
                <el-input v-model="searchForm.projectUnitNameLike"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="时间:">
                <el-date-picker
                  v-model="searchForm.time"
                  type="month"
                  placeholder="选择月">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->

      <!-- 主题内容-start -->
      <div class="summary-box">
        <div class="down-title">
          <el-button type="primary" size="mini" icon="iconfont mqh-icon-transfer" v-if="$store.getters.permissions.includes('/statistics/coalsafe-use-sum/operation')" @click="exportExcel">导出</el-button>
          <el-button type="primary" size="mini" icon="iconfont mqh-icon-dayin" v-if="$store.getters.permissions.includes('/statistics/coalsafe-use-sum/operation') && false">打印</el-button>
        </div>
        <div class="table-box" v-loading="isLoading">
          <h2>{{this.title}}</h2>
          <el-form :model="coalForm" :rules="coalFormRules" ref="coalForm">
          <el-table
            :data="coalForm.tableData"
            :row-class-name="tableRowClassName"
            border
            style="width: 100%">
            <el-table-column
              prop="enterpriseName"
              label="单位名称"
              align="center">
            </el-table-column>
            <el-table-column
              prop="beginBalance"
              label="年初结余（万元）"
              align="center">
              <template slot-scope="scope">
                <el-form-item
                  :prop="'tableData.' + scope.$index + '.beginBalance'"
                  :rules="coalFormRules.beginBalance">
                  <span v-if="scope.$index === 0">{{scope.row.beginBalance}}</span>
                  <el-input v-model="scope.row.beginBalance" @change="changeBeginBalance('coalForm', scope.row, scope.$index)" :disabled="inputFlag" v-else></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              label="原煤产量（万吨）"
              align="center">
              <el-table-column
                prop="rawCoalMonth"
                label="本月"
                align="center">
              </el-table-column>
              <el-table-column
                prop="rawCoalTotal"
                label="累计"
                align="center">
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="提取总额(万元)"
              align="center">
              <el-table-column
                prop="extractionTotalMonth"
                label="本月"
                align="center">
              </el-table-column>
              <el-table-column
                prop="extractionTotalTotal"
                label="累计"
                align="center">
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="吨煤提取(元/吨)"
              align="center">
              <el-table-column
                prop="tonsCoalExtractionMonth"
                label="本月"
                align="center">
                <template slot-scope="scope" v-if="scope.$index != 0">
                  <el-form-item
                    :prop="'tableData.' + scope.$index + '.tonsCoalExtractionMonth'"
                    :rules="coalFormRules.tonsCoalExtractionMonth">
                    <el-input v-model="scope.row.tonsCoalExtractionMonth" style="float:left;width:60%;" @change="(val) => changeInput(val, scope.$index)" :disabled="inputFlag"></el-input>
                    <el-popover
                      placement="right"
                      width="130"
                      trigger="click">
                      <el-radio-group v-model="tonsCoalExtractionMonth" @change="(val) => changeTonsCoalExtractionMonth(val, scope.$index)">
                        <el-radio :label="15">15</el-radio>
                        <el-radio :label="30">30</el-radio>
                      </el-radio-group>
                      <el-button type="text" slot="reference" @click="clickSelect(scope.row)" style="float:right" v-if="!inputFlag">选择</el-button>
                    </el-popover>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                prop="tonsCoalExtractionTotal"
                label="累计"
                align="center">
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="使用总额(万元)"
              align="center">
              <el-table-column
                prop="totalCostsMonth"
                label="本月"
                align="center"
                width="180">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'tableData.' + scope.$index + '.tenderTotalCostsMonth'"
                    :rules="coalFormRules.tenderTotalCostsMonth">
                    <span style="display:block;float:left;line-height: 35px;">{{scope.row.totalCostsMonth}}</span>
                    <el-input v-model="scope.row.tenderTotalCostsMonth" style="float:right;width:100px;" v-if="scope.$index != 0" @change="changeTotalCostsMonth('coalForm', scope.row, scope.$index)" :disabled="inputFlag"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <!--<el-table-column
                prop="totalCostsMonth"
                label="本月"
                align="center">
              </el-table-column>-->
              <el-table-column
                prop="tenderTotalCostsTotal"
                label="累计"
                align="center">
              </el-table-column>
            </el-table-column>
            <el-table-column
              prop="tenderEndingBalance"
              label="期末结余(万元)"
              align="center">
            </el-table-column>
          </el-table>
          </el-form>
          <div class="note-box">
            <el-row>
              <el-col :span="18">
                <p v-html="formTop.first" style="padding-left:35px;">{{formTop.first}}</p>
              </el-col>
              <el-col :span="2">
                <el-button type="text" size="middle" @click="editClick('top')" icon="el-icon-edit" v-if="!inputFlag"></el-button>
              </el-col>
            </el-row>
          </div>
          <div class="desc-box">
            <el-row>
              <el-col :span="18">
                <p style="padding-left:35px;">
                  <!-- <span>备注：</span> -->
                  <span v-html="formBottom.first"></span>
                </p>
              </el-col>
              <el-col :span="2">
                <el-button type="text" size="middle" @click="editClick('bottom')" icon="el-icon-edit" v-if="!inputFlag"></el-button>
              </el-col>
            </el-row>

          </div>
        </div>
      </div>
      <!-- 主题内容-end -->
      <div class="sum-formInfo">
        <el-form :model="suMRuleForm" :rules="suMRuleFormRules" ref="suMRuleForm" label-width="100px" class="demo-ruleForm">
          <el-row>
            <el-col :span="6">
              <el-form-item label="部门负责人:" prop="divisionPrincipal">
                <el-input v-model="suMRuleForm.divisionPrincipal" :disabled="inputFlag"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="填表人:" prop="preparerName">
                <el-input v-model="suMRuleForm.preparerName" :disabled="inputFlag"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="填表日期:" prop="fillingDate">
                <el-date-picker
                  v-model="suMRuleForm.fillingDate"
                  type="date"
                  placeholder="选择日期"
                  :disabled="inputFlag"
                  style="width:245px;">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="填表电话:" prop="fillingPhone">
                <el-input v-model="suMRuleForm.fillingPhone" :disabled="inputFlag"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="save-foot-btn">
          <el-button type="primary" @click="submitForm('coalForm', 'suMRuleForm')" v-if="!inputFlag && $store.getters.permissions.includes('/statistics/coalsafe-use-sum/operation')">保 存</el-button>
        </div>
      </div>
      <div class="dialog-box">
        <el-dialog
          title="编辑"
          :visible.sync="topDialogVisible"
          width="40%"
          center>
          <el-form ref="formInfo" :model="formInfo" :rules="formInfoRules" label-width="80px">
            <el-form-item label="编辑" prop="fileContent">
              <ueditor
                ref="ueditorDialog"
                :content="formInfo.fileContent"
                class="ueditor">
              </ueditor>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="topDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmClick('formInfo')">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
// 引入编辑器
import ueditor from '@/components/ueditor/ueditor.vue'
import {coalSafetyFeeSummarySublist, coalSafetyFeeSummary} from 'api/index'
import {isvalidPrice, isvalidFixedPhone, isvalidPriceForCoalFee} from '@/assets/js/validate'
import {formatDate} from 'common/js/date'
import {commonJs} from 'common/js/common'
export default {
  name: '',
  data () {
    let validPrice = (rule, value, callback) => {
      if (value && isvalidPrice(value)) {
        callback()
      } else {
        callback(new Error('输入正数且最多两位小数'))
      }
    }
    let validPhoneUser = (rule, value, callback) => {
      if (value && isvalidFixedPhone(value)) {
        callback()
      } else {
        callback(new Error('请输入正确的11位手机号码或带区号的固话'))
      }
    }
    let validPriceFee = (rule, value, callback) => {
      if (value && isvalidPriceForCoalFee(value)) {
        callback()
      } else {
        callback(new Error('输入正数且最多四位小数'))
      }
    }
    return {
      tonsCoalExtractionMonth: 15,
      topDialogVisible: false,
      formTop: {
        first: '目前我公司所属全部生产矿井费用提取和使用执行标准均为15元/吨。<br/>执行文件为财政部、安全生产监督管理总局《关于印发<企业安全生产费用提取和使用管理办法>的通知》（财建[2005]168号）'
      },
      formBottom: {
        first: '备注：1、吨煤提取（元/吨）本月和累计栏内可填写区间值<br/> 2、期末余额=年初结余+提取总额累计-使用总额累计'
      },
      searchForm: {
        time: '',
        year: '',
        month: '',
        projectUnitNameLike: '',
        amountType: '',
        lowerLimit: '',
        upperLimit: ''
      }, // 搜索表单
      suMRuleForm: {
        divisionPrincipal: '',
        preparerName: '',
        fillingDate: '',
        fillingPhone: ''
      },
      coalFormRules: {
        beginBalance: [
          {validator: validPriceFee, trigger: 'blur'}
        ],
        tonsCoalExtractionMonth: [
          {validator: validPrice, trigger: 'blur'}
        ],
        tenderTotalCostsMonth: [
          {validator: validPriceFee, trigger: 'blur'}
        ]
      },
      suMRuleFormRules: {
        divisionPrincipal: [
          {required: true, message: '请输入部门负责人名称', trigger: 'blur'}
        ],
        preparerName: [
          {required: true, message: '请输入填表人名称', trigger: 'blur'}
        ],
        fillingDate: [
          {required: true, message: '请选择日期', trigger: 'blur'}
        ],
        fillingPhone: [
          {required: true, message: '请输入正确的11位手机号码或带区号的固话', trigger: 'blur'},
          {validator: validPhoneUser, trigger: 'blur'}
        ]
      },
      formInfoRules: {
        fileContent: [
          {required: true, message: '请输入内容', trigger: ['blur', 'change']}
        ]
      },
      title: '', // 标题
      timeStr: '', // 年-月
      isLoading: false,
      coalForm: {
        tableData: [{enterpriseName: '合计', beginBalance: '', rawCoalMonth: '', rawCoalTotal: '', extractionTotalMonth: '', extractionTotalTotal: '', totalCostsMonth: '', totalCostsTotal: '', endingBalance: ''}]
      },
      inputFlag: false,
      formInfo: {
        fileContent: '' // 弹框中的数据
      },
      openDialog: '', // 确定打开的弹窗带入的取值是哪一个
      limitFlag: true // 模糊查询中金额输入框是否可用
    }
  },
  /** 组件注册 */
  components: {
    // 编辑器
    ueditor
  },
  created () {
    this.getTitleStr('-1', '-1')
  },
  mounted () {
  },
  methods: {
    exportExcel () {
      window.open(`${commonJs.coalSafetyFeeSummaryExportUrl}?year=${this.timeStr.split('-')[0]}&month=${this.timeStr.split('-')[1]}`)
    },
    /** 模糊查询中判断输入的金额格式是否正确，不是数字则变为0 */
    handleLowerInput (e) {
      this.searchForm.lowerLimit = e.replace(/[^\d]/g, '')
    },
    handleUpperInput (e) {
      this.searchForm.upperLimit = e.replace(/[^\d]/g, '')
    },
    changeAmount (value) {
      if (value) {
        this.limitFlag = false
      }
    },
    /** 清空下拉选项 */
    clear () {
      if (this.searchForm.lowerLimit) {
        this.searchForm.lowerLimit = ''
      }
      if (this.searchForm.upperLimit) {
        this.searchForm.upperLimit = ''
      }
      this.limitFlag = true
    },
    clickSelect (row) {
      this.tonsCoalExtractionMonth = Number(row.tonsCoalExtractionMonth)
    },
    /** 动态获取标题信息 */
    getTitleStr (year, month) {
      coalSafetyFeeSummarySublist.getTitle(year, month).then((res) => {
        if (res.data.titleStrs) {
          this.timeStr = res.data.titleStrs[1]
          let strs = this.timeStr.split('-')
          this.title = '太原煤气化集团公司' + strs[0] + '年' + strs[1] + '月煤炭生产安全费用提取和使用情况表'
          this.getTableData()
        }
      })
    },
    /** 列表数据展示 */
    getTableData () {
      this.isLoading = true
      this.inputFlag = false
      let strs = this.timeStr.split('-')
      coalSafetyFeeSummary.getList({
        year: strs[0],
        month: strs[1],
        status: 1,
        projectUnitNameLike: this.searchForm.projectUnitNameLike || null,
        amountType: this.searchForm.amountType || null,
        lowerLimit: this.searchForm.lowerLimit || null,
        upperLimit: this.searchForm.upperLimit || null
      }).then((res) => {
        this.coalForm.tableData = [{enterpriseName: '合计', beginBalance: '', rawCoalMonth: '', rawCoalTotal: '', extractionTotalMonth: '', extractionTotalTotal: '', totalCostsMonth: '', tenderTotalCostsTotal: '', tenderEndingBalance: ''}]
        this.suMRuleForm = {
          divisionPrincipal: '',
          preparerName: '',
          fillingDate: '',
          fillingPhone: ''
        }
        this.formTop.first = '目前我公司所属全部生产矿井费用提取和使用执行标准均为15元/吨。<br/>执行文件为财政部、安全生产监督管理总局《关于印发<企业安全生产费用提取和使用管理办法>的通知》（财建[2005]168号）'
        this.formBottom.first = '备注：1、吨煤提取（元/吨）本月和累计栏内可填写区间值<br/> 2、期末余额=年初结余+提取总额累计-使用总额累计'
        if (res.data.coalSafetyFeeSummary && res.data.coalSafetyFeeSummarySublistList.length > 0) {
          // this.coalForm.tableData[0] = Object.assign(this.coalForm.tableData[0], res.data.coalSafetyFeeSummary)
          res.data.coalSafetyFeeSummarySublistList.forEach(item => {
            item.beginBalance = Number(item.beginBalance)
            item.tonsCoalExtractionMonthNum = Number(item.tonsCoalExtractionMonth)
            item.baseTenderTotalCostsTotal = Number(item.totalCostsTotal) - Number(item.totalCostsMonth) // 本月之前的使用累计总额
            this.coalForm.tableData.push(item)
          })
          this.suMRuleForm = res.data.coalSafetyFeeSummary
          this.formTop.first = this.suMRuleForm.illustration
          this.formBottom.first = this.suMRuleForm.remark
          // this.inputFlag = true // 代表该数据已保存，不能进行修改(暂时不限制所有的操作按钮)
        } else if (res.data.coalSafetyFeeSummarySublistList.length > 0) {
          res.data.coalSafetyFeeSummarySublistList.forEach(item => {
            item.beginBalance = Number(item.beginBalance)
            item.tonsCoalExtractionMonthNum = Number(item.tonsCoalExtractionMonth)
            item.baseTenderTotalCostsTotal = Number(item.totalCostsTotal) - Number(item.totalCostsMonth) // 本月之前的使用累计总额
            this.coalForm.tableData.push(item)
          })
        }
        this.calSummary(0)
        this.isLoading = false
      })
    },
    /** 原煤产量本月 */
    changeBeginBalance (formName, row, index) {
      this.$refs[formName].validateField(`tableData.${index}.beginBalance`, (vaild) => {
        if (!vaild) {
          let obj = this.coalForm.tableData[index]
          if (obj.tonsCoalExtractionMonth) {
            this.cal(index)
          }
        }
      })
    },
    /** 吨煤提取 */
    changeTonsCoalExtractionMonth (radioval, index) {
      this.$set(this.coalForm.tableData[index], 'tonsCoalExtractionMonth', radioval)
      this.$set(this.coalForm.tableData[index], 'tonsCoalExtractionTotal', radioval)
      this.$refs['coalForm'].validateField(`tableData.${index}.tonsCoalExtractionMonth`, (vaild) => {
        if (!vaild) {
          if (this.coalForm.tableData[index].rawCoalMonth) {
            this.cal(index)
          }
        }
      })
    },
    /** 使用总额本月 */
    changeTotalCostsMonth (formName, row, index) {
      this.$refs[formName].validateField(`tableData.${index}.tenderTotalCostsMonth`, (vaild) => {
        if (!vaild) {
          let obj = this.coalForm.tableData[index]
          if (obj.beginBalance && obj.tonsCoalExtractionMonth) {
            this.cal(index)
          }
        }
      })
    },
    /**
     * 吨煤提取-本月
     * input@change事件
     * 给【累计】赋值
     */
    changeInput (val, index) {
      this.$nextTick(() => {
        this.$set(this.coalForm.tableData[index], 'tonsCoalExtractionMonth', val)
        this.$set(this.coalForm.tableData[index], 'tonsCoalExtractionTotal', val)
        this.$refs['coalForm'].validateField(`tableData.${index}.tonsCoalExtractionMonth`, (vaild) => {
          if (!vaild) {
            if (this.coalForm.tableData[index].rawCoalMonth) {
              this.cal(index)
            }
          }
        })
      })
    },
    /** 进行计算 */
    cal (index) {
      let obj = this.coalForm.tableData[index]
      obj.extractionTotalMonth = (Number(obj.rawCoalMonth) * Number(obj.tonsCoalExtractionMonth)).toFixed(4)
      obj.extractionTotalTotal = (Number(obj.rawCoalTotal) * Number(obj.tonsCoalExtractionMonth)).toFixed(4)
      if (obj.tenderTotalCostsMonth) {
        obj.tenderTotalCostsMonth = Number(obj.tenderTotalCostsMonth)
        obj.tenderTotalCostsTotal = (Number(obj.baseTenderTotalCostsTotal) + Number(obj.tenderTotalCostsMonth)).toFixed(4)
        obj.tenderEndingBalance = (Number(obj.beginBalance) + Number(obj.extractionTotalTotal) - Number(obj.tenderTotalCostsTotal)).toFixed(4)
      } else {
        obj.tenderEndingBalance = (Number(obj.beginBalance) + Number(obj.extractionTotalTotal) - Number(obj.totalCostsTotal)).toFixed(4)
      }
      if (!obj.endingBalance) {
        obj.endingBalance = obj.tenderEndingBalance
      }
      this.$set(this.coalForm.tableData, this.coalForm.tableData[index], obj)
      this.calSummary(0)
    },
    /** 进行合计计算 */
    calSummary (index) {
      this.coalForm.tableData[index] = {enterpriseName: '合计', beginBalance: '', rawCoalMonth: '', rawCoalTotal: '', extractionTotalMonth: '', extractionTotalTotal: '', totalCostsMonth: '', tenderTotalCostsTotal: '', tenderEndingBalance: ''}
      let obj = this.coalForm.tableData[index]
      let rows = this.coalForm.tableData
      for (let i = 1; i < rows.length; i++) {
        obj.beginBalance = (Number(obj.beginBalance) + Number(rows[i].beginBalance)).toFixed(4)
        obj.rawCoalMonth = (Number(obj.rawCoalMonth) + Number(rows[i].rawCoalMonth)).toFixed(4)
        obj.rawCoalTotal = (Number(obj.rawCoalTotal) + Number(rows[i].rawCoalTotal)).toFixed(4)
        obj.extractionTotalMonth = (Number(obj.extractionTotalMonth) + Number(rows[i].extractionTotalMonth)).toFixed(4)
        obj.extractionTotalTotal = (Number(obj.extractionTotalTotal) + Number(rows[i].extractionTotalTotal)).toFixed(4)
        if (!rows[i].tenderTotalCostsMonth) {
          obj.totalCostsMonth = (Number(obj.totalCostsMonth) + Number(rows[i].totalCostsMonth)).toFixed(4)
          obj.tenderTotalCostsTotal = (Number(obj.tenderTotalCostsTotal) + Number(rows[i].totalCostsTotal)).toFixed(4)
          obj.tenderEndingBalance = (Number(obj.tenderEndingBalance) + Number(rows[i].endingBalance)).toFixed(4)
        } else {
          obj.totalCostsMonth = (Number(obj.totalCostsMonth) + Number(rows[i].tenderTotalCostsMonth)).toFixed(4)
          obj.tenderTotalCostsTotal = (Number(obj.tenderTotalCostsTotal) + Number(rows[i].tenderTotalCostsTotal)).toFixed(4)
          obj.tenderEndingBalance = (Number(obj.tenderEndingBalance) + Number(rows[i].tenderEndingBalance)).toFixed(4)
        }
      }
      obj.tenderTotalCostsMonth = Number(obj.totalCostsMonth).toString()
      this.$set(this.coalForm.tableData, this.coalForm.tableData[index], obj)
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 查询 */
    onSearch () {
      if (this.searchForm.time) {
        let date = new Date(this.searchForm.time)
        let timeStr = formatDate(date, 'yyyy-MM')
        let strs = timeStr.split('-')
        this.searchForm.year = Number(strs[0])
        this.searchForm.month = Number(strs[1])
        this.getTitleStr(this.searchForm.year, this.searchForm.month)
      } else {
        this.getTableData()
      }
    },
    /** 保存 */
    submitForm (formName1, formName2) {
      this.$refs[formName1].validate((vaild) => {
        if (vaild) {
          this.$refs[formName2].validate((vaild) => {
            if (vaild) {
              let summaryInfo = Object.assign(this.suMRuleForm, this.coalForm.tableData[0])
              let strs = this.timeStr.split('-')
              summaryInfo.year = strs[0]
              summaryInfo.month = strs[1]
              summaryInfo.illustration = this.formTop.first
              summaryInfo.remark = this.formBottom.first.replace('<p>', '').replace('<\/p>', '')
              summaryInfo.totalCostsTotal = summaryInfo.tenderTotalCostsTotal
              summaryInfo.endingBalance = summaryInfo.tenderEndingBalance
              let tableData = this.coalForm.tableData
              summaryInfo.coalSafetyFeeSummarySublistList = tableData.filter((item, index) => index !== 0)
              coalSafetyFeeSummary.save(summaryInfo).then((res) => {
                if (res.data.resCode === '0000') {
                  this.getTableData()
                }
              })
            } else {
              return false
            }
          })
        } else {
          return false
        }
      })
    },
    /** 编辑 */
    editClick (type) {
      if (type === 'top') {
        this.topDialogVisible = true
        this.openDialog = 'top'
        // 编辑上半部分
        this.$nextTick(() => {
          this.$refs.ueditorDialog.setContent(this.formInfo.fileContent = this.formTop.first)
        })
      } else if (type === 'bottom') {
        // 编辑下半部分
        this.topDialogVisible = true
        this.openDialog = 'bottom'
        this.$nextTick(() => {
          this.$refs.ueditorDialog.setContent(this.formInfo.fileContent = this.formBottom.first)
        })
      }
    },
    /** 保存-弹窗 */
    confirmClick (formName) {
      this.$refs[formName].validate().then((vaild) => {
        if (vaild) {
          if (this.openDialog === 'top') {
            this.formTop.first = this.$refs.ueditorDialog.getContent()
          } else if (this.openDialog === 'bottom') {
            this.formBottom.first = this.$refs.ueditorDialog.getContent()
          }
          this.topDialogVisible = false
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style lang="less">
#coalsafety-use-sum {
  .summary-box {
    margin: 20px;
    background-color: #fff;
    .report-name {
      text-align: left;
    }
    .save-boxs {
      padding: 10px;
    }
    .table-box {
      padding: 25px 5px;
    }
    .down-title {
      text-align: right;
      background: #f7f7fa;
      padding: 5px;
      padding-right: 20px
    }
    .note-box,
    .desc-box {
      text-align: left;
      color: #999;
    }
    .note-box {
      border-bottom: 1px solid #ddd;
    }
    .el-form-item {
      margin-bottom: 0px;
    }
    .el-form-item__error {
      display: none;
    }
  }
  .sum-formInfo {
    margin: 20px 20px 100px 20px;
    padding: 20px 5px;
    background-color: #fff;
  }
  .dialog-box {
    .el-form-item__content {
      line-height: 0;
    }
  }
}
</style>
