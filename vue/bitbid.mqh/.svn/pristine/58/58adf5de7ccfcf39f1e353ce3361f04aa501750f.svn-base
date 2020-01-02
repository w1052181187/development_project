<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="coalsafety-details-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>统计分析</el-breadcrumb-item>
        <el-breadcrumb-item>年度汇总表</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafety?typeFlag='+ menuId }">{{menuId | filterMenu}}</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafety/coalsafety-details' }">煤安费汇总表详情</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <!-- 搜索区域start -->
    <div class="search-box">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-row :gutter="20" style="text-align: left;">
          <el-col :span="6" style="width: 330px;">
            <el-form-item label="资金投向:">
              <el-select v-model="searchForm.fundPurposeFirst" clearable placeholder="请选择" @change="changeSecondByFirstList" style="width:100px">
                <el-option
                  v-for="item in fundPurposeFirstList"
                  :key="item.objectId"
                  :label="item.name"
                  :value="item.objectId">
                </el-option>
              </el-select>
              <span style="color: #ddd">--</span>
              <el-select v-model="searchForm.fundPurposeSecond" clearable placeholder="请选择" style="width:100px">
                <el-option
                  v-for="item in fundPurposeSecondList"
                  :key="item.objectId"
                  :label="item.name"
                  :value="item.objectId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8" style="width: 440px;">
            <el-form-item label="金额(万元):">
              <el-select v-model="searchForm.moneyType" clearable placeholder="请选择" style="float:left;width:100px;">
                <el-option label="计划金额" value="1"></el-option>
                <el-option label="累积完成" value="2"></el-option>
                <el-option label="全年预计完成" value="3"></el-option>
              </el-select>
              <span style="display:inline-block;float:left;color:#999;">介于</span>
              <el-input placeholder="请输入" v-model="searchForm.areaL" class="input-with-select" style="float:left;width:85px;">
              </el-input>
              <span style="display:inline-block;color: #ddd;float:left;">--</span>
              <el-input placeholder="请输入" v-model="searchForm.areaR" class="input-with-select" style="float:left;width:85px;">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="状态:">
              <el-select v-model="searchForm.isFinish" clearable placeholder="请选择" style="width:110px;">
                <el-option label="未完成" value="0"></el-option>
                <el-option label="已完成" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="年份:">
              <el-date-picker
                style="width:110px;"
                v-model="searchForm.year"
                type="year"
                placeholder="选择年"
                value-format="yyyy"
                @change="changeYear">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" style="text-align: left;">
          <el-col :span="7">
            <el-form-item label="期数:" class="period-left">
              <el-select v-model="searchForm.stagingNumber" clearable placeholder="请选择" @change="changeNumberPeriodsList" style="width:218px">
                <el-option
                  v-for="item in numberPeriodsList"
                  :key="item.objectId"
                  :label="item.stagingNumber"
                  :value="item.stagingNumber">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="month-check-group">
        <el-row>
          <el-col :span="1">
            <el-checkbox :indeterminate="isIndeterminate" v-model="monthCheckAll" @change="handleCheckAllChange">全选</el-checkbox>
          </el-col>
          <el-col :span="23">
            <el-checkbox-group v-model="checkedMonths" @change="handleCheckedMonthsChange" style="text-align:left;">
              <el-checkbox v-for="month in monthsList" :label="month.value" :key="month.value">{{month.name}}</el-checkbox>
            </el-checkbox-group>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 搜索区域end -->

    <!-- 主题内容-汇总表start -->
    <div class="details-box">
      <div class="down-title">
        <el-button type="primary" size="mini" icon="iconfont mqh-icon-transfer" @click="exportExcel">导出</el-button>
        <!--<el-button type="primary" size="mini" icon="iconfont mqh-icon-dayin">打印</el-button>-->
      </div>
      <div class="summary-table" style="margin-bpttom:100px;padding-bottom: 20px">
        <div class="summary-table-tit" style="padding-top:8px;">
          <h2>{{this.year}}年{{this.stagingNumber ? '第' + this.stagingNumber + '期' : ''}}{{this.capitalSourceName}}计划统计完成情况表</h2>
        </div>
        <div class="summary-unit">
          <span style="display:inline-block;float:left;">单位名称：{{this.$route.query.enterpriseName}}</span>
          <span>单位：万元</span>
        </div>
        <!-- 树结构表格组件 -->
        <div class="tree-table" style="padding-bottom: 15px;">
          <tree-grid
            border
            :months="checkedMonths"
            :columns="columns"
            :tree-structure="true"
            :data="summaryTableData"
            @operate="tableBtnClick">
          </tree-grid>
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
          v-model="remarkMes"
          disabled>
        </el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>
    </div>
    <!-- 弹窗区域end -->
  </div>
</template>
<script>
import treeGrid from '@/components/treetable/TreeFoldQuery.vue'
import {investmanage} from 'api/index'
import {commonJs} from 'common/js/common'
export default {
  name: '',
  data () {
    return {
      stagingNumber: null,
      numberPeriodsList: [],
      dialogVisible: false,
      remarkMes: null,
      menuId: this.$route.query.menuId,
      isIndeterminate: true,
      monthsList: this.createMonthsList(),
      checkedMonths: [],
      monthCheckAll: false,
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
      year: '',
      capitalSourceName: '',
      query: {} // 查询条件
    }
  },
  mounted () {
    this.getFundPurposeFirstList()
    this.getData()
    this.getCapitalSource()
    this.getNumberPeriods()
  },
  filters: {
    filterMenu (val) {
      if (val === 1) {
        return '折旧(更改)费汇总表'
      } else if (val === 2) {
        return '井巷工程费汇总表'
      } else if (val === 3) {
        return '维简费汇总表'
      } else if (val === 4) {
        return '煤炭安全资金汇总表'
      }else if (val === 5) {
        return '环境治理恢复基金汇总表'
      } else if (val === 6) {
        return '危化品安全资金汇总表'
      } else if (val === 7) {
        return '工程建设汇总表'
      }
    }
  },
  methods: {
    changeNumberPeriodsList (val) {},
    /** 获取期数-list */
    getNumberPeriods () {
      this.numberPeriodsList = []
      let obj = {
        enterpriseId: this.$route.query.enterpriseId,
        year: this.searchForm.year || this.$route.query.year
      }
      investmanage.getList(obj).then(res => {
        let tempArr = res.data.investmentProposalSchemeList.list
        if (tempArr.length) {
          this.numberPeriodsList = tempArr
        }
      })
    },
    changeYear (val) {
      this.getNumberPeriods()
    },
    /** 表格操作 */
    tableBtnClick (row) {
      this.dialogVisible = true
      this.remarkMes = row.remark
    },
    // 创建月份
    createMonthsList () {
      let result = []
      for (let i = 1; i < 13; i++) {
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
    },
    handleCheckedMonthsChange (val) {
      let checkedCount = val.length
      this.monthCheckAll = checkedCount === this.monthsList.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.monthsList.length
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    exportExcel () {
      if (this.query.capitalSource) {
        if (this.stagingNumber) {
          window.open(`${commonJs.suggestedPlanExportUrl}?year=${this.query.year}&enterpriseId=${this.query.enterpriseId}
            &enterpriseName=${this.$route.query.enterpriseName}&capitalSource=${this.query.capitalSource}
            &capitalSourceName=${this.capitalSourceName}&months=${this.checkedMonths}&operate=${2}&stagingNumber=${this.stagingNumber}`)
        } else {
          window.open(`${commonJs.suggestedPlanExportUrl}?year=${this.query.year}&enterpriseId=${this.query.enterpriseId}
            &enterpriseName=${this.$route.query.enterpriseName}&capitalSource=${this.query.capitalSource}
            &capitalSourceName=${this.capitalSourceName}&months=${this.checkedMonths}&operate=${2}`)
        }
      } else {
        if (this.stagingNumber) {
          window.open(`${commonJs.suggestedPlanExportUrl}?year=${this.query.year}&enterpriseId=${this.query.enterpriseId}
            &enterpriseName=${this.$route.query.enterpriseName}&capitalSources=${this.query.capitalSources}
            &capitalSourceName=${this.capitalSourceName}&months=${this.checkedMonths}&operate=${2}&stagingNumber=${this.stagingNumber}`)
        } else {
          window.open(`${commonJs.suggestedPlanExportUrl}?year=${this.query.year}&enterpriseId=${this.query.enterpriseId}
            &enterpriseName=${this.$route.query.enterpriseName}&capitalSources=${this.query.capitalSources}
            &capitalSourceName=${this.capitalSourceName}&months=${this.checkedMonths}&operate=${2}`)
        }
      }
    },
    /** 查询 */
    onSearch () {
      this.getData()
    },
    getCapitalSource () {
      if (Object.is(this.$route.query.capitalSourceId, 0)) {
        this.capitalSourceName = '工程建设'
      } else {
        investmanage.getOneCapitalSource(this.$route.query.capitalSourceId).then(res => {
          let capitalSource = res.data.capitalSource
          if (capitalSource) {
            this.capitalSourceName = capitalSource.name
          }
        })
      }
    },
    /** 查询资金投向 - 一级 */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0
      }
      if (this.$route.query.capitalSourceId) {
        if (Object.is(this.$route.query.capitalSourceId, 0)) {
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
    },
    getData () {
      investmanage.getCapitalSourceList({classify: 2, isAvailableFunds: 1}).then(res => {
        let capitalSourceList = res.data.capitalSourceList
        return capitalSourceList
      }).then(capitalSourceList => {
        this.getStatisticsElementTree(capitalSourceList)
      })
    },
    getStatisticsElementTree (capitalSourceList) {
      this.query = {}
      this.year = this.searchForm.year ? this.searchForm.year : this.$route.query.year
      this.stagingNumber = this.searchForm.stagingNumber || null
      this.query = {
        status: 3,
        year: this.year,
        enterpriseId: this.$route.query.enterpriseId,
        fundPurposeFirst: this.searchForm.fundPurposeFirst || null,
        fundPurposeSecond: this.searchForm.fundPurposeSecond || null,
        messageLike: this.searchForm.messageLike || null,
        isFinish: this.searchForm.isFinish,
        operate: 2,
        isInRecycleBin: 0,
        stagingNumber: this.searchForm.stagingNumber
      }
      if (this.$route.query.type === 1) {
        this.query.capitalSource = this.$route.query.capitalSourceId
      } else if (this.$route.query.type === 2) {
        let capitalSources = ''
        if (capitalSourceList) {
          capitalSourceList.map(capitalSource => {
            if (capitalSources) {
              capitalSources = capitalSources + capitalSource.objectId + ','
            } else {
              capitalSources = capitalSource.objectId + ','
            }
          })
          capitalSources = capitalSources + '0'
        }
        this.query.capitalSources = capitalSources
      }
      // 金额搜索条件
      if (Object.is(this.searchForm.moneyType, '1')) {
        // 计划金额
        if (this.searchForm.areaL) {
          this.query.amountLowerIsQuery = true
          this.query.amountLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          this.query.amountUpperIsQuery = true
          this.query.amountUpperLimit = this.searchForm.areaR
        }
      } else if (Object.is(this.searchForm.moneyType, '2')) {
        // 累计完成金额
        if (this.searchForm.areaL) {
          this.query.cumulativeLowerIsQuery = true
          this.query.cumulativeLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          this.query.cumulativeUpperIsQuery = true
          this.query.cumulativeUpperLimit = this.searchForm.areaR
        }
      } else if (Object.is(this.searchForm.moneyType, '3')) {
        // 预计完成金额
        if (this.searchForm.areaL) {
          this.query.expectedLowerIsQuery = true
          this.query.expectedLowerLimit = this.searchForm.areaL
        }
        if (this.searchForm.areaR) {
          this.query.expectedUpperIsQuery = true
          this.query.expectedUpperLimit = this.searchForm.areaR
        }
      }
      investmanage.getStatisticsElementTree(this.query).then(res => {
        let resultData = res.data.suggestedPlanList
        if (resultData) {
          if (resultData.length > 0) {
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
    }
  },
  components: {
    treeGrid
  }
}
</script>
<style lang="less">
#coalsafety-details-page {
  .details-box {
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
      padding-bottom: 30px;
    }
    .block-page {
      padding: 15px;
    }
  }
  .month-check-group {
    text-align: left;
    margin-top: 10px;
  }
  .period-left {
    .el-form-item__label {
      margin-left: 28px;
    }
  }
}
.el-tooltip__popper.is-dark {
  width: 170px !important;
}
</style>
