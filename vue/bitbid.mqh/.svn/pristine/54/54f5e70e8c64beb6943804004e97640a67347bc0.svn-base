<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="coalsafety-report">
    <div v-if="this.$route.path == '/admin/statistics/coalsafe-report'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafe-report' }">{{`${breadcrumbTitle}上报`}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :model="searchForm">
          <el-row :gutter="20">
            <el-col :span="9" style="padding-left: 18px;">
              <el-form-item label="资金投向:" style="text-align:left;">
                <el-select v-model="searchForm.fundPurposeFirst" clearable placeholder="请选择" @change="changeSecondByFirstList" style="width:165px">
                  <el-option
                    v-for="item in fundPurposeFirstList"
                    :key="item.objectId"
                    :label="item.name"
                    :value="item.objectId">
                  </el-option>
                </el-select>
                <span style="color: #ddd">--</span>
                <el-select v-model="searchForm.fundPurposeSecond" clearable placeholder="请选择" style="width:165px">
                  <el-option
                    v-for="item in fundPurposeSecondList"
                    :key="item.objectId"
                    :label="item.name"
                    :value="item.objectId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" style="padding-left: 19px;">
              <el-form-item label="项目名称:" style="text-align:left;">
                <el-input v-model="searchForm.messageLike" placeholder="项目名称" style="width:160px"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="年份:" style="text-align:left;">
                <el-date-picker
                  v-model="searchForm.year"
                  type="year"
                  placeholder="选择年"
                  value-format="yyyy"
                  @change="changeYear">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="9">
              <el-form-item label="金额(万元):">
                <el-select v-model="searchForm.moneyType" clearable placeholder="请选择" @change="changeMoneyType" style="float:left;width:108px;">
                  <el-option label="计划金额" value="1"></el-option>
                  <el-option label="累积完成" value="2"></el-option>
                  <el-option label="全年预计完成" value="3"></el-option>
                </el-select>
                <span style="display:inline-block;float:left;color:#999;">介于</span>
                <el-input placeholder="请输入内容" v-model="searchForm.areaL" class="input-with-select" style="float:left;width:100px;">
                </el-input>
                <span style="display:inline-block;color: #ddd;float:left;">--</span>
                <el-input placeholder="请输入内容" v-model="searchForm.areaR" class="input-with-select" style="float:left;width:100px;">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="备注(状态):" style="text-align:left;">
                <el-select v-model="searchForm.isFinish" clearable placeholder="请选择" style="width:160px">
                  <el-option label="未完成" value="0"></el-option>
                  <el-option label="已完成" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div class="group-check" v-if="showCheckBox">
          <el-checkbox-group v-model="checkList">
            <el-checkbox label="1" :disabled="disabledCheckbox">显示未上报数量或金额的项目</el-checkbox>
            <el-checkbox label="2">显示未上报全年预计完成情况的项目</el-checkbox>
          </el-checkbox-group>
        </div>
        <div class="month-check-group" v-if="showCheckBox">
          <el-row>
            <el-col :span="2">
              <el-checkbox :indeterminate="isIndeterminate" v-model="monthCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
            </el-col>
            <el-col :span="22">
              <el-checkbox-group v-model="checkedMonths" @change="handleCheckedMonthsChange" style="text-align:left;">
                <el-checkbox v-for="month in monthsList" :label="month.value" :key="month.value">{{month.name}}</el-checkbox>
              </el-checkbox-group>
            </el-col>
          </el-row>
        </div>
      </div>
      <!-- 搜索区域end -->

      <!-- 主题内容-汇总表start -->
      <div class="summary-box">
        <div class="summary-table">
          <div class="summary-table-tit">
            <h2>{{this.year}}年{{this.$route.query.capitalName}}计划统计完成情况表</h2>
          </div>
          <div class="summary-unit">
            <span style="display:inline-block;float:left;">单位名称：{{$store.getters.authUser.enterpriseName}}</span>
            <span>单位：万元</span>
          </div>
          <!-- 树结构表格组件 -->
          <div class="tree-table">
            <tree-grid
              border
              :columns="columns"
              :months="checkedMonths"
              :tree-structure="true"
              :data="summaryTableData"
              @operate="tableBtnClick">
            </tree-grid>
          </div>
          <div class="save-boxs" v-if="showCheckBox">
            <el-button type="info" @click="saveData" v-if="$store.getters.permissions.includes('/statistics/coalsafe-report/operation')">保 存</el-button>
            <el-button type="primary" @click="reportData" v-if="$store.getters.permissions.includes('/statistics/coalsafe-report/operation')">上 报</el-button>
          </div>
        </div>
      </div>
      <!-- 主题内容-汇总表end -->
      <!-- 弹窗区域start -->
      <div class="dialog-box">
        <el-dialog
          title="备注"
          :visible.sync="dialogVisible"
          width="40%"
          center>
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="remarkMes">
          </el-input>
          <span slot="footer" class="dialog-footer">
            <el-button @click="cancelRemark">取 消</el-button>
            <el-button type="primary" @click="saveRemark">确 定</el-button>
          </span>
        </el-dialog>
      </div>
      <!-- 弹窗区域end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import treeGrid from '@/components/treetable/TreeFold.vue'
import {investmanage, suggestedPlanAnnualTotal} from 'api/index'
export default {
  name: '',
  data () {
    return {
      showCheckBox: true, // 显示隐藏搜索checkbox
      dialogVisible: false, // 备注弹窗
      remarkMes: '',
      tableRow: {},
      isIndeterminate: true,
      monthsList: this.createMonthsList(),
      checkedMonths: [],
      monthCheckAll: false,
      disabledCheckbox: true, // 禁用
      breadcrumbTitle: this.$route.query.capitalName, // 面包屑导航
      checkList: [], // 搜索-check
      searchForm: {}, // 搜索表单
      fundPurposeFirstList: [], // 资金投向一级
      fundPurposeSecondList: [], // 资金投向二级
      moneyType: '', // 金额类型-搜索
      summaryTableData: [], // 汇总表格数据
      columns: [
        {
          text: '项目名称',
          value: 'label',
          width: 200
        }
      ],
      operate: null, // 1.保存 2.上报
      year: new Date().getFullYear(),
      batchSuggestedPlan: [],
      batchMonthStatistics: [],
      batchYearStatistics: [],
      annualTotal: {},
      notEnteredMonth: false, // 未录入数量、金额
      notEnteredYear: false, // 未录入全年预计完成
      capitalSourceId: null // 资金来源id
    }
  },
  mounted () {
    this.getStatisticsElementTree()
    this.getFundPurposeFirstList()
  },
  watch: {
    '$route': 'fetchDate',
    capitalSourceId: function (newV, oldV) {
      if (newV) {
        this.searchForm = {}
      }
    },
    breadcrumbTitle: function (newV, oldV) {
      if (newV) {
        this.breadcrumbTitle = this.$route.query.capitalName
      }
    }
  },
  methods: {
    /** 保存备注 */
    saveRemark () {
      this.summaryTableData.map((item, index) => {
        if (item.children) {
          item.children.map(child => {
            if (child.children) {
              child.children.map(last => {
                if (last.suggestedPlan.objectId) {
                  if (this.tableRow.objectId === last.suggestedPlan.objectId) {
                    this.$set(last.suggestedPlan, 'remark', this.remarkMes)
                    return false
                  }
                }
              })
            } else {
              if (this.tableRow.objectId === child.suggestedPlan.objectId) {
                this.$set(child.suggestedPlan, 'remark', this.remarkMes)
                return false
              }
            }
          })
        }
      })
      this.dialogVisible = false
    },
    /** 取消保存 */
    cancelRemark () {
      this.remarkMes = ''
      this.dialogVisible = false
    },
    // 创建月份
    createMonthsList () {
      // 获得当前月份
      let date = new Date()
      let year = new Date().getFullYear()
      let month = 0
      if (this.searchForm && this.searchForm.year && Number(this.searchForm.year) < year) {
        month = 12
      } else {
        month = date.getMonth() + 1
      }
      let result = []
      for (let i = 1; i <= month; i++) {
        let obj = {
          name: `${i}月`,
          value: i
        }
        result.push(obj)
      }
      return result
    },
    // 月份全选
    handleCheckAllChange (val) {
      let monthArr = this.createMonthsList().map(item => {
        item = item.value
        return item
      })
      this.checkedMonths = val ? monthArr : []
      this.isIndeterminate = false
      if (val) {
        this.disabledCheckbox = false
      } else {
        this.disabledCheckbox = true
      }
    },
    handleCheckedMonthsChange (val) {
      let checkedCount = val.length
      this.monthCheckAll = checkedCount === this.monthsList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.monthsList.length
      if (val.length) {
        this.disabledCheckbox = false
      } else {
        this.disabledCheckbox = true
      }
    },
    fetchDate () {
      this.checkedMonths = []
      this.$nextTick(() => {
        this.year = new Date().getFullYear().toString()
        this.monthsList = this.createMonthsList()
      })
      this.getStatisticsElementTree()
      this.getFundPurposeFirstList()
      // 面包屑
      this.breadcrumbTitle = this.$route.query.capitalName
    },
    /** 搜索-年份改变 */
    changeYear (val) {
      this.checkedMonths = []
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    changeMoneyType (val) {
      if (val === null || val === '') {
        this.searchForm.areaL = ''
        this.searchForm.areaR = ''
      }
    },
    /** 查询 */
    onSearch () {
      let year = new Date().getFullYear()
      if (this.searchForm && this.searchForm.year && Number(this.searchForm.year) < year) {
        this.checkedMonths = this.checkedMonths
      }
      if (this.searchForm.year > year) {
        this.showCheckBox = false
      } else {
        this.showCheckBox = true
      }
      this.monthsList = this.createMonthsList()
      this.getStatisticsElementTree()
    },
    /** 计算 */
    allCalculate () {
      this.summaryTableData.map(first => {
        if (first.children) {
          first.children.map(second => {
            // 有二级资金投向
            if (second.children) {
              this.calculate(second)
            }
          })
          this.calculate(first)
        }
      })
      // 计算总计
      this.calculateTotal()
    },
    calculateTotal () {
      // 建议计划
      if (!this.summaryTableData[0].suggestedPlan) {
        this.summaryTableData[0].suggestedPlan = {}
      }
      let budgetAmount = 0
      // 建议计划年度统计
      if (!this.summaryTableData[0].suggestedPlanYearStatistics) {
        this.summaryTableData[0].suggestedPlanYearStatistics = {}
      }
      let cumulativeCompleteNumber = 0
      let cumulativeCompletePrice = 0
      let expectedComplete = 0
      // 建议计划月度统计
      // 一月
      if (!this.summaryTableData[0].januaryMonthStatistics) {
        this.summaryTableData[0].januaryMonthStatistics = {}
      }
      let januaryNumber = 0
      let januaryPrice = 0
      // 二月
      if (!this.summaryTableData[0].februaryMonthStatistics) {
        this.summaryTableData[0].februaryMonthStatistics = {}
      }
      let februaryNumber = 0
      let februaryPrice = 0
      // 三月
      if (!this.summaryTableData[0].marchMonthStatistics) {
        this.summaryTableData[0].marchMonthStatistics = {}
      }
      let marchNumber = 0
      let marchPrice = 0
      // 四月
      if (!this.summaryTableData[0].aprilMonthStatistics) {
        this.summaryTableData[0].aprilMonthStatistics = {}
      }
      let aprilNumber = 0
      let aprilPrice = 0
      // 五月
      if (!this.summaryTableData[0].mayMonthStatistics) {
        this.summaryTableData[0].mayMonthStatistics = {}
      }
      let mayNumber = 0
      let mayPrice = 0
      // 六月
      if (!this.summaryTableData[0].juneMonthStatistics) {
        this.summaryTableData[0].juneMonthStatistics = {}
      }
      let juneNumber = 0
      let junePrice = 0
      // 七月
      if (!this.summaryTableData[0].julyMonthStatistics) {
        this.summaryTableData[0].julyMonthStatistics = {}
      }
      let julyNumber = 0
      let julyPrice = 0
      // 八月
      if (!this.summaryTableData[0].augustMonthStatistics) {
        this.summaryTableData[0].augustMonthStatistics = {}
      }
      let augustNumber = 0
      let augustPrice = 0
      // 九月
      if (!this.summaryTableData[0].septemberMonthStatistics) {
        this.summaryTableData[0].septemberMonthStatistics = {}
      }
      let septemberNumber = 0
      let septemberPrice = 0
      // 十月
      if (!this.summaryTableData[0].octoberMonthStatistics) {
        this.summaryTableData[0].octoberMonthStatistics = {}
      }
      let octoberNumber = 0
      let octoberPrice = 0
      // 十一月
      if (!this.summaryTableData[0].novemberMonthStatistics) {
        this.summaryTableData[0].novemberMonthStatistics = {}
      }
      let novemberNumber = 0
      let novemberPrice = 0
      // 十二月
      if (!this.summaryTableData[0].decemberMonthStatistics) {
        this.summaryTableData[0].decemberMonthStatistics = {}
      }
      let decemberNumber = 0
      let decemberPrice = 0
      this.summaryTableData.map(first => {
        if (!Object.is(first.label, '总计')) {
          if (first.suggestedPlan) {
            budgetAmount = (Number(budgetAmount) +
              Number(first.suggestedPlan.budgetAmount ? first.suggestedPlan.budgetAmount : first.suggestedPlan.currentPlanAmount)).toFixed(2)
          }
          if (first.suggestedPlanYearStatistics) {
            cumulativeCompleteNumber = (Number(cumulativeCompleteNumber) +
              Number(first.suggestedPlanYearStatistics.cumulativeCompleteNumber)).toFixed(2)
            cumulativeCompletePrice = (Number(cumulativeCompletePrice) +
              Number(first.suggestedPlanYearStatistics.cumulativeCompletePrice)).toFixed(2)
            expectedComplete = (Number(expectedComplete) +
              Number(first.suggestedPlanYearStatistics.expectedComplete)).toFixed(2)
          }
          // 一月
          if (first.januaryMonthStatistics.number) {
            januaryNumber = (Number(januaryNumber) + Number(first.januaryMonthStatistics.number)).toFixed(2)
          }
          if (first.januaryMonthStatistics.price) {
            januaryPrice = (Number(januaryPrice) + Number(first.januaryMonthStatistics.price)).toFixed(2)
          }
          // 二月
          if (first.februaryMonthStatistics.number) {
            februaryNumber = (Number(februaryNumber) + Number(first.februaryMonthStatistics.number)).toFixed(2)
          }
          if (first.februaryMonthStatistics.price) {
            februaryPrice = (Number(februaryPrice) + Number(first.februaryMonthStatistics.price)).toFixed(2)
          }
          // 三月
          if (first.marchMonthStatistics.number) {
            marchNumber = (Number(marchNumber) + Number(first.marchMonthStatistics.number)).toFixed(2)
          }
          if (first.marchMonthStatistics.price) {
            marchPrice = (Number(marchPrice) + Number(first.marchMonthStatistics.price)).toFixed(2)
          }
          // 四月
          if (first.aprilMonthStatistics.number) {
            aprilNumber = (Number(aprilNumber) + Number(first.aprilMonthStatistics.number)).toFixed(2)
          }
          if (first.aprilMonthStatistics.price) {
            aprilPrice = (Number(aprilPrice) + Number(first.aprilMonthStatistics.price)).toFixed(2)
          }
          // 五月
          if (first.mayMonthStatistics.number) {
            mayNumber = (Number(mayNumber) + Number(first.mayMonthStatistics.number)).toFixed(2)
          }
          if (first.mayMonthStatistics.price) {
            mayPrice = (Number(mayPrice) + Number(first.mayMonthStatistics.price)).toFixed(2)
          }
          // 六月
          if (first.juneMonthStatistics.number) {
            juneNumber = (Number(juneNumber) + Number(first.juneMonthStatistics.number)).toFixed(2)
          }
          if (first.juneMonthStatistics.price) {
            junePrice = (Number(junePrice) + Number(first.juneMonthStatistics.price)).toFixed(2)
          }
          // 七月
          if (first.julyMonthStatistics.number) {
            julyNumber = (Number(julyNumber) + Number(first.julyMonthStatistics.number)).toFixed(2)
          }
          if (first.julyMonthStatistics.price) {
            julyPrice = (Number(julyPrice) + Number(first.julyMonthStatistics.price)).toFixed(2)
          }
          // 八月
          if (first.augustMonthStatistics.number) {
            augustNumber = (Number(augustNumber) + Number(first.augustMonthStatistics.number)).toFixed(2)
          }
          if (first.augustMonthStatistics.price) {
            augustPrice = (Number(augustPrice) + Number(first.augustMonthStatistics.price)).toFixed(2)
          }
          // 九月
          if (first.septemberMonthStatistics.number) {
            septemberNumber = (Number(septemberNumber) + Number(first.septemberMonthStatistics.number)).toFixed(2)
          }
          if (first.septemberMonthStatistics.price) {
            septemberPrice = (Number(septemberPrice) + Number(first.septemberMonthStatistics.price)).toFixed(2)
          }
          // 十月
          if (first.octoberMonthStatistics.number) {
            octoberNumber = (Number(octoberNumber) + Number(first.octoberMonthStatistics.number)).toFixed(2)
          }
          if (first.octoberMonthStatistics.price) {
            octoberPrice = (Number(octoberPrice) + Number(first.octoberMonthStatistics.price)).toFixed(2)
          }
          // 十一月
          if (first.novemberMonthStatistics.number) {
            novemberNumber = (Number(novemberNumber) + Number(first.novemberMonthStatistics.number)).toFixed(2)
          }
          if (first.novemberMonthStatistics.price) {
            novemberPrice = (Number(novemberPrice) + Number(first.novemberMonthStatistics.price)).toFixed(2)
          }
          // 十二月
          if (first.decemberMonthStatistics.number) {
            decemberNumber = (Number(decemberNumber) + Number(first.decemberMonthStatistics.number)).toFixed(2)
          }
          if (first.decemberMonthStatistics.price) {
            decemberPrice = (Number(decemberPrice) + Number(first.decemberMonthStatistics.price)).toFixed(2)
          }
        }
      })
      this.summaryTableData[0].suggestedPlan.budgetAmount = budgetAmount
      this.summaryTableData[0].suggestedPlanYearStatistics.cumulativeCompleteNumber = cumulativeCompleteNumber
      this.summaryTableData[0].suggestedPlanYearStatistics.cumulativeCompletePrice = cumulativeCompletePrice
      this.summaryTableData[0].suggestedPlanYearStatistics.expectedComplete = expectedComplete
      this.summaryTableData[0].januaryMonthStatistics.number = januaryNumber
      this.summaryTableData[0].januaryMonthStatistics.price = januaryPrice
      this.summaryTableData[0].februaryMonthStatistics.number = februaryNumber
      this.summaryTableData[0].februaryMonthStatistics.price = februaryPrice
      this.summaryTableData[0].marchMonthStatistics.number = marchNumber
      this.summaryTableData[0].marchMonthStatistics.price = marchPrice
      this.summaryTableData[0].aprilMonthStatistics.number = aprilNumber
      this.summaryTableData[0].aprilMonthStatistics.price = aprilPrice
      this.summaryTableData[0].mayMonthStatistics.number = mayNumber
      this.summaryTableData[0].mayMonthStatistics.price = mayPrice
      this.summaryTableData[0].juneMonthStatistics.number = juneNumber
      this.summaryTableData[0].juneMonthStatistics.price = junePrice
      this.summaryTableData[0].julyMonthStatistics.number = julyNumber
      this.summaryTableData[0].julyMonthStatistics.price = julyPrice
      this.summaryTableData[0].augustMonthStatistics.number = augustNumber
      this.summaryTableData[0].augustMonthStatistics.price = augustPrice
      this.summaryTableData[0].septemberMonthStatistics.number = septemberNumber
      this.summaryTableData[0].septemberMonthStatistics.price = septemberPrice
      this.summaryTableData[0].octoberMonthStatistics.number = octoberNumber
      this.summaryTableData[0].octoberMonthStatistics.price = octoberPrice
      this.summaryTableData[0].novemberMonthStatistics.number = novemberNumber
      this.summaryTableData[0].novemberMonthStatistics.price = novemberPrice
      this.summaryTableData[0].decemberMonthStatistics.number = decemberNumber
      this.summaryTableData[0].decemberMonthStatistics.price = decemberPrice
    },
    calculate (item) {
      // 建议计划表
      if (!item.suggestedPlan) {
        item.suggestedPlan = {}
      }
      let budgetAmount = 0
      // 建议计划年度统计表
      if (!item.suggestedPlanYearStatistics) {
        item.suggestedPlanYearStatistics = {}
      }
      // 累计完成数量
      let completeNumber = 0
      // 累计完成金额
      let completePrice = 0
      // 全年预计完成
      let expectedComplete = 0
      // 一月
      if (!item.januaryMonthStatistics) {
        item.januaryMonthStatistics = {}
      }
      let januaryNumber = 0
      let januaryPrice = 0
      // 二月
      if (!item.februaryMonthStatistics) {
        item.februaryMonthStatistics = {}
      }
      let februaryNumber = 0
      let februaryPrice = 0
      // 三月
      if (!item.marchMonthStatistics) {
        item.marchMonthStatistics = {}
      }
      let marchNumber = 0
      let marchPrice = 0
      // 四月
      if (!item.aprilMonthStatistics) {
        item.aprilMonthStatistics = {}
      }
      let aprilNumber = 0
      let aprilPrice = 0
      // 五月
      if (!item.mayMonthStatistics) {
        item.mayMonthStatistics = {}
      }
      let mayNumber = 0
      let mayPrice = 0
      // 六月
      if (!item.juneMonthStatistics) {
        item.juneMonthStatistics = {}
      }
      let juneNumber = 0
      let junePrice = 0
      // 七月
      if (!item.julyMonthStatistics) {
        item.julyMonthStatistics = {}
      }
      let julyNumber = 0
      let julyPrice = 0
      // 八月
      if (!item.augustMonthStatistics) {
        item.augustMonthStatistics = {}
      }
      let augustNumber = 0
      let augustPrice = 0
      // 九月
      if (!item.septemberMonthStatistics) {
        item.septemberMonthStatistics = {}
      }
      let septemberNumber = 0
      let septemberPrice = 0
      // 十月
      if (!item.octoberMonthStatistics) {
        item.octoberMonthStatistics = {}
      }
      let octoberNumber = 0
      let octoberPrice = 0
      // 十一月
      if (!item.novemberMonthStatistics) {
        item.novemberMonthStatistics = {}
      }
      let novemberNumber = 0
      let novemberPrice = 0
      // 十二月
      if (!item.decemberMonthStatistics) {
        item.decemberMonthStatistics = {}
      }
      let decemberNumber = 0
      let decemberPrice = 0
      item.children.map(child => {
        // 建议计划年度统计表
        if (!child.suggestedPlanYearStatistics) {
          child.suggestedPlanYearStatistics = {}
        }
        // 累计完成数量
        let childCompleteNumber = 0
        // 累计完成金额
        let childCompletePrice = 0
        // 一月
        if (child.januaryMonthStatistics.number) {
          januaryNumber = (Number(januaryNumber) + Number(child.januaryMonthStatistics.number)).toFixed(2)
        }
        if (child.januaryMonthStatistics.price) {
          januaryPrice = (Number(januaryPrice) + Number(child.januaryMonthStatistics.price)).toFixed(2)
        }
        // 二月
        if (child.februaryMonthStatistics.number) {
          februaryNumber = (Number(februaryNumber) + Number(child.februaryMonthStatistics.number)).toFixed(2)
        }
        if (child.februaryMonthStatistics.price) {
          februaryPrice = (Number(februaryPrice) + Number(child.februaryMonthStatistics.price)).toFixed(2)
        }
        // 三月
        if (child.marchMonthStatistics.number) {
          marchNumber = (Number(marchNumber) + Number(child.marchMonthStatistics.number)).toFixed(2)
        }
        if (child.marchMonthStatistics.price) {
          marchPrice = (Number(marchPrice) + Number(child.marchMonthStatistics.price)).toFixed(2)
        }
        // 四月
        if (child.aprilMonthStatistics.number) {
          aprilNumber = (Number(aprilNumber) + Number(child.aprilMonthStatistics.number)).toFixed(2)
        }
        if (child.aprilMonthStatistics.price) {
          aprilPrice = (Number(aprilPrice) + Number(child.aprilMonthStatistics.price)).toFixed(2)
        }
        // 五月
        if (child.mayMonthStatistics.number) {
          mayNumber = (Number(mayNumber) + Number(child.mayMonthStatistics.number)).toFixed(2)
        }
        if (child.mayMonthStatistics.price) {
          mayPrice = (Number(mayPrice) + Number(child.mayMonthStatistics.price)).toFixed(2)
        }
        // 六月
        if (child.juneMonthStatistics.number) {
          juneNumber = (Number(juneNumber) + Number(child.juneMonthStatistics.number)).toFixed(2)
        }
        if (child.juneMonthStatistics.price) {
          junePrice = (Number(junePrice) + Number(child.juneMonthStatistics.price)).toFixed(2)
        }
        // 七月
        if (child.julyMonthStatistics.number) {
          julyNumber = (Number(julyNumber) + Number(child.julyMonthStatistics.number)).toFixed(2)
        }
        if (child.julyMonthStatistics.price) {
          julyPrice = (Number(julyPrice) + Number(child.julyMonthStatistics.price)).toFixed(2)
        }
        // 八月
        if (child.augustMonthStatistics.number) {
          augustNumber = (Number(augustNumber) + Number(child.augustMonthStatistics.number)).toFixed(2)
        }
        if (child.augustMonthStatistics.price) {
          augustPrice = (Number(augustPrice) + Number(child.augustMonthStatistics.price)).toFixed(2)
        }
        // 九月
        if (child.septemberMonthStatistics.number) {
          septemberNumber = (Number(septemberNumber) + Number(child.septemberMonthStatistics.number)).toFixed(2)
        }
        if (child.septemberMonthStatistics.price) {
          septemberPrice = (Number(septemberPrice) + Number(child.septemberMonthStatistics.price)).toFixed(2)
        }
        // 十月
        if (child.octoberMonthStatistics.number) {
          octoberNumber = (Number(octoberNumber) + Number(child.octoberMonthStatistics.number)).toFixed(2)
        }
        if (child.octoberMonthStatistics.price) {
          octoberPrice = (Number(octoberPrice) + Number(child.octoberMonthStatistics.price)).toFixed(2)
        }
        // 十一月
        if (child.novemberMonthStatistics.number) {
          novemberNumber = (Number(novemberNumber) + Number(child.novemberMonthStatistics.number)).toFixed(2)
        }
        if (child.novemberMonthStatistics.price) {
          novemberPrice = (Number(novemberPrice) + Number(child.novemberMonthStatistics.price)).toFixed(2)
        }
        // 十二月
        if (child.decemberMonthStatistics.number) {
          decemberNumber = (Number(decemberNumber) + Number(child.decemberMonthStatistics.number)).toFixed(2)
        }
        if (child.decemberMonthStatistics.price) {
          decemberPrice = (Number(decemberPrice) + Number(child.decemberMonthStatistics.price)).toFixed(2)
        }
        childCompleteNumber = (Number(child.januaryMonthStatistics.number) + Number(child.februaryMonthStatistics.number) +
        Number(child.marchMonthStatistics.number) + Number(child.aprilMonthStatistics.number) +
        Number(child.mayMonthStatistics.number) + Number(child.juneMonthStatistics.number) +
        Number(child.julyMonthStatistics.number) + Number(child.augustMonthStatistics.number) +
        Number(child.septemberMonthStatistics.number) + Number(child.octoberMonthStatistics.number) +
        Number(child.novemberMonthStatistics.number) + Number(child.decemberMonthStatistics.number)).toFixed(2)
        childCompletePrice = (Number(child.januaryMonthStatistics.price) + Number(child.februaryMonthStatistics.price) +
        Number(child.marchMonthStatistics.price) + Number(child.aprilMonthStatistics.price) +
        Number(child.mayMonthStatistics.price) + Number(child.juneMonthStatistics.price) +
        Number(child.julyMonthStatistics.price) + Number(child.augustMonthStatistics.price) +
        Number(child.septemberMonthStatistics.price) + Number(child.octoberMonthStatistics.price) +
        Number(child.novemberMonthStatistics.price) + Number(child.decemberMonthStatistics.price)).toFixed(2)
        child.suggestedPlanYearStatistics.cumulativeCompleteNumber = childCompleteNumber
        child.suggestedPlanYearStatistics.cumulativeCompletePrice = childCompletePrice
        if (!child.suggestedPlanYearStatistics.isSubmitted) {
          child.suggestedPlanYearStatistics.isSubmitted = 0
        }
        if (child.suggestedPlanYearStatistics.cumulativeCompleteNumber &&
          child.suggestedPlanYearStatistics.cumulativeCompleteNumber !== null) {
          completeNumber = (Number(completeNumber) + Number(child.suggestedPlanYearStatistics.cumulativeCompleteNumber)).toFixed(2)
        }
        if (child.suggestedPlanYearStatistics.cumulativeCompletePrice &&
          child.suggestedPlanYearStatistics.cumulativeCompletePrice !== null) {
          completePrice = (Number(completePrice) + Number(child.suggestedPlanYearStatistics.cumulativeCompletePrice)).toFixed(2)
        }
        if (child.suggestedPlanYearStatistics.expectedComplete &&
          child.suggestedPlanYearStatistics.expectedComplete !== null) {
          expectedComplete = (Number(expectedComplete) + Number(child.suggestedPlanYearStatistics.expectedComplete)).toFixed(2)
        }
        budgetAmount = (Number(budgetAmount) +
          Number(child.suggestedPlan.budgetAmount ? child.suggestedPlan.budgetAmount : child.suggestedPlan.currentPlanAmount)).toFixed(2)
      })
      item.suggestedPlan.budgetAmount = budgetAmount
      item.suggestedPlanYearStatistics.cumulativeCompleteNumber = completeNumber
      item.suggestedPlanYearStatistics.cumulativeCompletePrice = completePrice
      item.suggestedPlanYearStatistics.expectedComplete = expectedComplete
      if (!item.suggestedPlanYearStatistics.isSubmitted) {
        item.suggestedPlanYearStatistics.isSubmitted = 0
      }
      item.januaryMonthStatistics.number = januaryNumber
      item.januaryMonthStatistics.price = januaryPrice
      item.februaryMonthStatistics.number = februaryNumber
      item.februaryMonthStatistics.price = februaryPrice
      item.marchMonthStatistics.number = marchNumber
      item.marchMonthStatistics.price = marchPrice
      item.aprilMonthStatistics.number = aprilNumber
      item.aprilMonthStatistics.price = aprilPrice
      item.mayMonthStatistics.number = mayNumber
      item.mayMonthStatistics.price = mayPrice
      item.juneMonthStatistics.number = juneNumber
      item.juneMonthStatistics.price = junePrice
      item.julyMonthStatistics.number = julyNumber
      item.julyMonthStatistics.price = julyPrice
      item.augustMonthStatistics.number = augustNumber
      item.augustMonthStatistics.price = augustPrice
      item.septemberMonthStatistics.number = septemberNumber
      item.septemberMonthStatistics.price = septemberPrice
      item.octoberMonthStatistics.number = octoberNumber
      item.octoberMonthStatistics.price = octoberPrice
      item.novemberMonthStatistics.number = novemberNumber
      item.novemberMonthStatistics.price = novemberPrice
      item.decemberMonthStatistics.number = decemberNumber
      item.decemberMonthStatistics.price = decemberPrice
    },
    /** 保存 */
    saveData () {
      this.operate = 1
      // 校验表格保存数据
      let result = this.validateTableData(this.summaryTableData)
      if (result) {
        this.save()
      } else {
        this.$message({
          type: 'warning',
          message: '请检查数据是否填写正确'
        })
      }
    },
    /** 上报 */
    reportData () {
      this.operate = 2
      // 校验表格保存数据
      let result = this.validateTableData(this.summaryTableData)
      if (result) {
        this.save()
      } else {
        this.$message({
          type: 'warning',
          message: '请检查数据是否填写正确'
        })
      }
    },
    /** 校验 */
    validateTableData (data) {
      // 月份
      let month = ['januaryMonthStatistics', 'februaryMonthStatistics', 'marchMonthStatistics', 'aprilMonthStatistics', 'mayMonthStatistics', 'juneMonthStatistics', 'julyMonthStatistics', 'augustMonthStatistics', 'septemberMonthStatistics', 'octoberMonthStatistics', 'novemberMonthStatistics', 'decemberMonthStatistics']
      let result = false
      let reg = /^\d{1,14}(\.\d{1,2})?$/
      if (!data.length) {
        return false
      }
      for (let i = 1, len = data.length; i < len; i++) {
        let first = data[i].children
        if (!first || !first.length) {
          return false
        } else {
          for (let j = 0, len = first.length; j < len; j++) {
            let second = first[j]
            if (!second) {
              return false
            } else {
              for (let k = 0, len = month.length; k < len; k++) {
                // let number = second[month[k]].number
                let price = second[month[k]].price
                let expectedComplete = second['suggestedPlanYearStatistics'].expectedComplete
                // (number || price) && (!reg.test(number) || !reg.test(price))
                if ((price) && (!reg.test(price))) {
                  return false
                } else if (expectedComplete && (!reg.test(expectedComplete))) {
                  return false
                } else {
                  result = true
                }
              }
              if (second.children && second.children.length) {
                for (let l = 0, len = second.children.length; l < len; l++) {
                  let third = second.children[l]
                  if (!third) {
                    return false
                  } else {
                    for (let k = 0, len = month.length; k < len; k++) {
                      // let number = third[month[k]].number
                      let price = third[month[k]].price
                      let expectedComplete = third['suggestedPlanYearStatistics'].expectedComplete
                      if ((price) && (!reg.test(price))) {
                        return false
                      } else if (expectedComplete && (!reg.test(expectedComplete))) {
                        return false
                      } else {
                        result = true
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      return result
    },
    save () {
      this.notEnteredMonth = false
      this.notEnteredYear = false
      window.setTimeout(() => {
        this.allCalculate()
      }, 10)
      window.setTimeout(() => {
        this.summaryTableData.map(first => {
          if (first.children) {
            first.children.map(second => {
              if (second.suggestedPlan.objectId) {
                // 无二级资金投向
                this.checkData(second)
              } else {
                // 有二级资金投向
                if (second.children) {
                  second.children.map(third => {
                    this.checkData(third)
                  })
                }
              }
            })
          }
        })
      }, 200)
      window.setTimeout(() => {
        this.callingInterface()
      }, 300)
    },
    checkData (item) {
      if (item.suggestedPlan.objectId !== null) {
        // 建议计划年度统计
        if (!item.suggestedPlanYearStatistics.expectedComplete ||
          item.suggestedPlanYearStatistics.expectedComplete === null) {
          this.notEnteredYear = true
        }
        // 建议计划月度统计
        let monthStatistics = ['januaryMonthStatistics', 'februaryMonthStatistics', 'marchMonthStatistics',
          'aprilMonthStatistics', 'mayMonthStatistics', 'juneMonthStatistics', 'julyMonthStatistics',
          'augustMonthStatistics', 'septemberMonthStatistics', 'octoberMonthStatistics', 'novemberMonthStatistics',
          'decemberMonthStatistics']
        monthStatistics.map((monthStatistics, index) => {
          if (this.year < new Date().getFullYear()) {
            // 小于当前年处理12个月的数据
            // 非公开招标判断是否录入月度统计数据
            if (item[monthStatistics]) {
              if (!item[monthStatistics].number || item[monthStatistics].number === null ||
                !item[monthStatistics].price || item[monthStatistics].price === null) {
                this.notEnteredMonth = true
              }
            }
          } else {
            // 为当前年或者大于当前年处理小于等于当前月的数据
            if (index <= new Date().getMonth()) {
              // 非公开招标判断是否录入月度统计数据
              if (item[monthStatistics]) {
                if (!item[monthStatistics].number || item[monthStatistics].number === null ||
                  !item[monthStatistics].price || item[monthStatistics].price === null) {
                  this.notEnteredMonth = true
                }
              }
            } else {
              return false
            }
          }
        })
      }
    },
    processingdata (item) {
      if (item.suggestedPlan.objectId !== null) {
        this.batchSuggestedPlan.push(item.suggestedPlan)
        // 建议计划年度统计
        // 判断是否上报：未上报，保存数据
        if (item.suggestedPlanYearStatistics) {
          if (item.suggestedPlanYearStatistics.isSubmitted !== 1) {
            item.suggestedPlanYearStatistics.suggestedPlanCode = item.suggestedPlan.code
            item.suggestedPlanYearStatistics.year = this.year
            item.suggestedPlanYearStatistics.sourceType = 2
            if (Object.is(this.operate, 1)) {
              // 保存
              item.suggestedPlanYearStatistics.isSubmitted = 0
            } else if (Object.is(this.operate, 2)) {
              // 上报
              if (item.suggestedPlanYearStatistics.expectedComplete &&
                item.suggestedPlanYearStatistics.expectedComplete !== null &&
                item.suggestedPlanYearStatistics.expectedComplete !== '') {
                item.suggestedPlanYearStatistics.isSubmitted = 1
              }
            }
            this.batchYearStatistics.push(item.suggestedPlanYearStatistics)
          }
        }
        // 建议计划月度统计
        let monthStatistics = ['januaryMonthStatistics', 'februaryMonthStatistics', 'marchMonthStatistics',
          'aprilMonthStatistics', 'mayMonthStatistics', 'juneMonthStatistics', 'julyMonthStatistics',
          'augustMonthStatistics', 'septemberMonthStatistics', 'octoberMonthStatistics', 'novemberMonthStatistics',
          'decemberMonthStatistics']
        monthStatistics.map((monthStatistics, index) => {
          if (this.year < new Date().getFullYear()) {
            // 小于当前年处理12个月的数据
            // 非公开招标判断是否录入月度统计数据
            this.checkMonthData(item, monthStatistics, index)
          } else {
            // 为当前年或者大于当前年处理小于等于当前月的数据
            if (index <= new Date().getMonth()) {
              // 非公开招标判断是否录入月度统计数据
              this.checkMonthData(item, monthStatistics, index)
            } else {
              return false
            }
          }
        })
      }
    },
    checkMonthData (item, monthStatistics, index) {
      if (item[monthStatistics]) {
        // 判断是否上报：金额/数量是否有值，未上报，保存数据
        if (item[monthStatistics].numberIsSubmitted === 0 || item[monthStatistics].priceIsSubmitted === 0) {
          item[monthStatistics].suggestedPlanCode = item.suggestedPlan.code
          item[monthStatistics].year = this.year
          item[monthStatistics].month = index + 1
          if (Object.is(this.operate, 1)) {
            // 保存
            item[monthStatistics].numberIsSubmitted = 0
            item[monthStatistics].priceIsSubmitted = 0
          } else if (Object.is(this.operate, 2)) {
            // 数量上报
            if (item[monthStatistics].number && item[monthStatistics].number !== null && item[monthStatistics].number !== '') {
              item[monthStatistics].numberIsSubmitted = 1
            }
            // 金额上报
            if (item[monthStatistics].price && item[monthStatistics].price !== null && item[monthStatistics].price !== '') {
              item[monthStatistics].priceIsSubmitted = 1
            }
          }
          item[monthStatistics].sourceType = 2
          this.batchMonthStatistics.push(item[monthStatistics])
        }
      } else {
        // 公开招标
        // 判断是否上报
        if (item[monthStatistics].numberIsSubmitted === 0 || item[monthStatistics].priceIsSubmitted === 0) {
          if (Object.is(this.operate, 2)) {
            // 数量上报
            if (item[monthStatistics].number !== null && item[monthStatistics].number !== '') {
              item[monthStatistics].numberIsSubmitted = 1
            }
            // 金额上报
            if (item[monthStatistics].price !== null && item[monthStatistics].price !== '') {
              item[monthStatistics].priceIsSubmitted = 1
            }
          }
          this.batchMonthStatistics.push(item[monthStatistics])
        }
      }
    },
    createAnnualTotal () {
      // 封装总计数据
      this.annualTotal.objectId = this.summaryTableData[0].parentId
      this.annualTotal.year = this.year
      this.annualTotal.planPrice = this.summaryTableData[0].suggestedPlan.budgetAmount
      this.annualTotal.cumulativeCompleteNumber = this.summaryTableData[0].suggestedPlanYearStatistics.cumulativeCompleteNumber
      this.annualTotal.cumulativeCompletePrice = this.summaryTableData[0].suggestedPlanYearStatistics.cumulativeCompletePrice
      this.annualTotal.yearExpectedComplete = this.summaryTableData[0].suggestedPlanYearStatistics.expectedComplete
      this.annualTotal.capitalSourceId = Number(this.capitalSourceId)
      let capitalSourceIds = this.$route.query.capitalSourceId.split(',')
      if (capitalSourceIds.length > 1) {
        this.annualTotal.capitalSourceIds = capitalSourceIds
      }
      this.annualTotal.capitalSourceName = this.$route.query.capitalName
      if (Object.is(this.operate, 1)) {
        // 保存
        this.annualTotal.operate = 0
      } else if (Object.is(this.operate, 2)) {
        // 上报
        this.annualTotal.operate = 1
      }
      let monthTotals = []
      let monthStatistics = ['januaryMonthStatistics', 'februaryMonthStatistics', 'marchMonthStatistics',
        'aprilMonthStatistics', 'mayMonthStatistics', 'juneMonthStatistics', 'julyMonthStatistics',
        'augustMonthStatistics', 'septemberMonthStatistics', 'octoberMonthStatistics', 'novemberMonthStatistics',
        'decemberMonthStatistics']
      monthStatistics.map((item, index) => {
        if (this.summaryTableData[0][item]) {
          let monthTotal = {}
          monthTotal.year = this.year
          monthTotal.month = index + 1
          monthTotal.objectId = this.summaryTableData[0][item].objectId
          monthTotal.number = this.summaryTableData[0][item].number
          monthTotal.price = this.summaryTableData[0][item].price
          monthTotal.capitalSourceId = Number(this.capitalSourceId)
          monthTotal.enterpriseId = this.$store.getters.authUser.enterpriseId
          monthTotals.push(monthTotal)
        }
      })
      this.annualTotal.suggestedPlanMonthsTotals = []
      this.annualTotal.suggestedPlanMonthsTotals = monthTotals
      this.annualTotal.suggestedPlanList = []
      this.annualTotal.suggestedPlanList = this.batchSuggestedPlan
      this.annualTotal.monthStatisticsList = []
      this.annualTotal.monthStatisticsList = this.batchMonthStatistics
      this.annualTotal.yearStatisticsList = []
      this.annualTotal.yearStatisticsList = this.batchYearStatistics
    },
    callingInterface () {
      if (Object.is(this.operate, 2)) {
        // 上报
        if (this.notEnteredMonth || this.notEnteredYear) {
          let message = ''
          if (this.notEnteredMonth && !this.notEnteredYear) {
            // 未录入数量、金额
            message = '存在数量、金额未上报'
          } else if (!this.notEnteredMonth && this.notEnteredYear) {
            // 未录入全年预计完成
            message = '全年预计完成情况未上报'
          } else if (this.notEnteredMonth && this.notEnteredYear) {
            // 未录入数量、金额、全年预计完成
            message = '存在数量、金额、全年预计完成情况未上报'
          }
          this.$confirm(message + ', 是否继续?已上报的数据不能修改', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.updateData()
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
        } else {
          this.updateData()
        }
      } else if (Object.is(this.operate, 1)) {
        // 保存
        this.updateData()
      }
    },
    updateData () {
      this.batchYearStatistics = []
      this.batchMonthStatistics = []
      this.summaryTableData.map(first => {
        if (first.children) {
          first.children.map(second => {
            if (second.suggestedPlan.objectId) {
              // 无二级资金投向
              this.processingdata(second)
            } else {
              // 有二级资金投向
              if (second.children) {
                second.children.map(third => {
                  this.processingdata(third)
                })
              }
            }
          })
        }
      })
      this.createAnnualTotal()
      suggestedPlanAnnualTotal.update(this.annualTotal).then(res => {
        this.getStatisticsElementTree()
      })
    },
    /** 表格操作 */
    tableBtnClick (row, index) {
      this.dialogVisible = true
      this.remarkMes = row.remark
      this.tableRow = row
    },
    getStatisticsElementTree () {
      this.year = this.searchForm.year ? this.searchForm.year : new Date().getFullYear()
      let query = {
        status: 3,
        year: this.year,
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        fundPurposeFirst: this.searchForm.fundPurposeFirst || null,
        fundPurposeSecond: this.searchForm.fundPurposeSecond || null,
        messageLike: this.searchForm.messageLike || null,
        isFinish: this.searchForm.isFinish,
        operate: 1,
        isInRecycleBin: 0
      }
      if (this.$route.query.capitalSourceId) {
        let capitalSourceIds = this.$route.query.capitalSourceId.split(',')
        if (capitalSourceIds.length > 1) {
          this.capitalSourceId = 0
          query.capitalSources = this.$route.query.capitalSourceId
        } else {
          this.capitalSourceId = this.$route.query.capitalSourceId
          query.capitalSource = this.$route.query.capitalSourceId
        }
      }
      if (this.checkList.length) {
        if (Object.is(this.checkList.length, 1)) {
          if (Object.is(this.checkList[0], '1')) {
            query.searchOperate = 1
          } else if (Object.is(this.checkList[0], '2')) {
            query.searchOperate = 2
          }
        } else if (Object.is(this.checkList.length, 2)) {
          query.searchOperate = 3
        }
        if (this.checkedMonths && this.checkedMonths.length > 0) {
          let months = ''
          this.checkedMonths.map(month => {
            if (month) {
              months += month + ','
            } else {
              months = month + ','
            }
          })
          query.months = months.substring(0, months.length - 1)
          query.monthsLength = this.checkedMonths.length
        }
      }
      // 金额搜索条件
      if (Object.is(this.searchForm.moneyType, '1')) {
        // 计划金额
        if (this.searchForm.areaL) {
          query.amountLowerIsQuery = true
          query.amountLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          query.amountUpperIsQuery = true
          query.amountUpperLimit = this.searchForm.areaR
        }
      } else if (Object.is(this.searchForm.moneyType, '2')) {
        // 累计完成金额
        if (this.searchForm.areaL) {
          query.cumulativeLowerIsQuery = true
          query.cumulativeLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          query.cumulativeUpperIsQuery = true
          query.cumulativeUpperLimit = this.searchForm.areaR
        }
      } else if (Object.is(this.searchForm.moneyType, '3')) {
        // 预计完成金额
        if (this.searchForm.areaL) {
          query.expectedLowerIsQuery = true
          query.expectedLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          query.expectedUpperIsQuery = true
          query.expectedUpperLimit = this.searchForm.areaR
        }
      }
      investmanage.getStatisticsElementTree(query).then(res => {
        let resultData = res.data.suggestedPlanList
        if (resultData) {
          if (resultData.length > 0) {
            let total = resultData[0]
            if (!total.suggestedPlanYearStatistics) {
              total.suggestedPlanYearStatistics = {}
              total.suggestedPlanYearStatistics.isSubmitted = 0
            }
            this.summaryTableData = resultData
            this.summaryTableData.map(first => {
              if (first.children) {
                first.children.map(second => {
                  // 有二级资金投向
                  if (second.children) {
                    this.initData(second)
                  }
                })
                this.initData(first)
              }
            })
            this.allCalculate()
          } else {
            this.summaryTableData = []
          }
        } else {
          this.summaryTableData = []
        }
      })
    },
    initData (item) {
      // 建议计划年度统计表
      if (!item.suggestedPlanYearStatistics) {
        item.suggestedPlanYearStatistics = {}
      }
      item.children.map(child => {
        // 建议计划年度统计表
        if (!child.suggestedPlanYearStatistics) {
          child.suggestedPlanYearStatistics = {}
          // objectId不为空，则说明该层级为建议计划
          if (child.suggestedPlan.objectId !== null) {
            child.suggestedPlanYearStatistics.objectId = null
          }
        }
      })
    },
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0
      }
      if (this.$route.query.capitalSourceId) {
        let capitalSourceIds = this.$route.query.capitalSourceId.split(',')
        if (capitalSourceIds.length > 1) {
          // 工程建设
          query.type = 2
        } else {
          query.type = 1
          query.capitalSourceId = this.$route.query.capitalSourceId
        }
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeFirstList = res.data.fundPurposeList
      })
    },
    /** 切换资金投向 */
    changeSecondByFirstList () {
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
    }
  },
  components: {
    treeGrid
  }
}
</script>
<style lang="less">
#coalsafety-report {
  .summary-box {
    margin: 0 20px 120px 20px;
    background: #fff;
    .down-title {
      text-align: right;
      background: #f7f7fa;
      padding: 5px;
      padding-right: 20px
    }
    .summary-unit {
      text-align: right;
      margin-bottom: 5px;
    }
    .summary-table {
      padding: 0 5px;
      .summary-table-tit {
        padding-top: 10px;
      }
    }
    .block-page {
      padding: 15px;
    }
  }
  .save-boxs {
    padding: 10px;
  }
  .group-check {
    text-align: left;
  }
  .month-check-group {
    margin-top: 10px;
    text-align: left;
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
  .tree-table .eltable-tree {
    .el-table__fixed,
    .el-table__fixed-right {
      z-index: 2 !important;
    }
  }
}
</style>
