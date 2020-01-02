<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="coalsafety-use">
    <div v-if="this.$route.path == '/admin/statistics/coalsafe-use'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafe-use' }">煤安费提取和使用上报</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-row>
            <el-col :span="6" style="text-align:left;">
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
      <div class="summary-box" v-loading="isLoading">
        <h2>{{this.title}}</h2>
        <p class="report-name">
          <span>单位名称：{{this.$store.getters.authUser.enterpriseName}}</span>
        </p>
        <el-form :model="coalForm" :rules="rules" ref="coalForm">
          <el-table
            :data="coalForm.tableData"
            border
            style="width: 100%">
            <el-table-column
              prop="beginBalance"
              label="年初结余（万元）"
              align="center">
            </el-table-column>
            <el-table-column
              label="原煤产量（万吨）"
              align="center">
              <el-table-column
                prop="rawCoalMonth"
                label="本月"
                align="center">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'tableData.' + scope.$index + '.rawCoalMonth'"
                    :rules="rules.rawCoalMonth">
                  <el-input v-model="scope.row.rawCoalMonth" @change="changeRawCoalMonth('coalForm', scope.row, scope.$index)" :disabled="inputFlag"></el-input>
                  </el-form-item>
                </template>
              </el-table-column>
              <el-table-column
                prop="rawCoalTotal"
                label="累计"
                align="center">
                <template slot-scope="scope">
                  {{rawCoalTotal}}
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="提取总额（万元）"
              align="center">
              <el-table-column
                prop="extractionTotalMonth"
                label="本月"
                align="center">
                <template slot-scope="scope">
                  {{extractionTotalMonth}}
                </template>
              </el-table-column>
              <el-table-column
                prop="extractionTotalTotal"
                label="累计"
                align="center">
                <template slot-scope="scope">
                  {{extractionTotalTotal}}
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="吨煤提取（元/吨）"
              align="center">
              <el-table-column
                prop="tonsCoalExtractionMonth"
                label="本月"
                align="center">
                <template slot-scope="scope">
                  <el-form-item
                    :prop="'tableData.' + scope.$index + '.tonsCoalExtractionMonth'"
                    :rules="rules.tonsCoalExtractionMonth">
                    <el-input style="float:left;width:60%;" @change="(val) => changeInput(val)" v-model="scope.row.tonsCoalExtractionMonth" :disabled="inputFlag"></el-input>
                    <el-popover
                      placement="right"
                      width="130"
                      trigger="manual"
                      v-model="visible">
                      <el-radio-group @change="changeTonsCoalExtractionMonth"  v-model="tonsCoalExtractionMonth">
                        <el-radio :label="15">15</el-radio>
                        <el-radio :label="30">30</el-radio>
                      </el-radio-group>
                      <el-button type="text" slot="reference" @click="clickSelect(scope.row)" style="float:right" v-if="Number(scope.row.status) !== 1">选择</el-button>
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
              label="使用总额（万元)"
              align="center">
              <el-table-column
                prop="totalCostsMonth"
                label="本月"
                align="center">
              </el-table-column>
              <el-table-column
                prop="totalCostsTotal"
                label="累计"
                align="center">
              </el-table-column>
            </el-table-column>
            <el-table-column
              prop="endingBalance"
              label="期末结余（万元）"
              align="center">
             <template slot-scope="scope">
                {{endingBalance}}
              </template>
            </el-table-column>
          </el-table>
        </el-form>
        <div class="save-boxs">
          <el-button type="info" @click="saveData('coalForm')" v-if="(coalForm.tableData[0].status === '' || coalForm.tableData[0].status === 0) && $store.getters.permissions.includes('/statistics/coalsafe-use/operation')">保 存</el-button>
          <el-button type="primary" @click="reportData('coalForm')" v-if="(coalForm.tableData[0].status === '' || coalForm.tableData[0].status === 0) && $store.getters.permissions.includes('/statistics/coalsafe-use/operation')">上 报</el-button>
        </div>
      </div>
      <!-- 主题内容-end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
// v-show="Number(scope.row.status) !== 1"
import {coalSafetyFeeSummarySublist} from 'api/index'
import {isvalidPrice, isvalidPriceForCoalFee} from '@/assets/js/validate'
import {formatDate} from 'common/js/date'
export default {
  name: '',
  data () {
    let validPrice = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPrice(value)) {
        callback(new Error('输入正数且最多两位小数'))
      } else {
        callback()
      }
    }
    let validPriceFee = (rule, value, callback) => {
      if (!value) {

      } else if (!isvalidPriceForCoalFee(value)) {
        callback(new Error('输入正数且最多四位小数'))
      } else {
        callback()
      }
    }
    return {
      visible: false,
      endingBalance: '',
      rawCoalTotal: '',
      extractionTotalMonth: '',
      extractionTotalTotal: '',
      tonsCoalExtractionMonth: 15,
      searchForm: {
        time: '',
        year: '',
        month: ''
      }, // 搜索表单
      time: '',
      coalForm: {
        tableData: [{beginBalance: '', status: '', rawCoalMonth: '', rawCoalTotal: '', tonsCoalExtractionMonth: 15, tonsCoalExtractionTotal: 15, totalCostsMonth: ''}]
      },
      rules: {
        rawCoalMonth: [
          {required: true, message: '输入正数且最多两位小数', trigger: 'blur'},
          {validator: validPriceFee, trigger: 'blur'}
        ],
        tonsCoalExtractionMonth: [
          {required: true, message: '输入正数且最多两位小数', trigger: ['blur', 'change']},
          {validator: validPrice, trigger: ['blur', 'change']}
        ]
      },
      title: '',
      timeStr: '', // 年-月
      firstTimeStr: '',
      totalInfo: {
        rawCoalTotal: '', // 原煤产量累计
        extractionTotalTotal: '', // 抽取累计
        totalCostsTotal: '', // 使用总额累计
        totalCostsMonth: '', //  使用总额本月
        preBeginBalance: '' // 本年上月的年初结余数据
      },
      isLoading: false,
      inputFlag: false // 是否可以进行编辑
    }
  },
  watch: {
    /* timeStr: function (newV, oldV) {
      if (newV) {
        this.getTotalInfoList()
      }
    } */
  },
  filters: {
    filterNumber (val1, val2) {
      let a = Number(val1 - 0)
      let b = Number(val2 - 0)
      return a + b === 0 ? '' : (a + b)
    },
    filterTotal (val0, val1, val2) {
      let a = Number(val1 - 0)
      let b = Number(val2 - 0)
      return a * b === 0 ? '' : (a * b)
    },
    filterNumberAdd (val0, val1, val2, val3) {
      let a = Number(val1 - 0)
      let b = Number(val2 - 0)
      let c = Number(val3 - 0)
      return a * b + c === 0 ? '' : (a * b + c)
    },
    filterEndingBalance (val0, scope, totalInfo) {
      let sum = ''
      if (totalInfo.extractionTotalTotal === '') {
        sum = scope.row.endingBalance
      } else {
        let beginBalance = scope.row.beginBalance
        let rawCoalMonth = scope.row.extractionTotalMonth
        let tonsCoalExtractionMonth = scope.row.tonsCoalExtractionMonth
        let totalCostsTotal = scope.row.totalCostsTotal
        let extractionTotalTotal = totalInfo.extractionTotalTotal
        sum = Number(beginBalance - 0) + Number(rawCoalMonth - 0) * Number(tonsCoalExtractionMonth - 0) + Number(extractionTotalTotal - 0) - Number(totalCostsTotal - 0)
      }
      return sum === 0 || scope.row.beginBalance === '' ? '' : sum
    }
  },
  created () {
    this.getTitleStr('-1', '-1')
  },
  mounted () {
  },
  methods: {
    clickSelect (row) {
      this.visible = !this.visible
      this.tonsCoalExtractionMonth = Number(row.tonsCoalExtractionMonth)
    },
    /** 动态获取标题信息 */
    getTitleStr (year, month) {
      coalSafetyFeeSummarySublist.getTitle(year, month).then((res) => {
        if (res.data.titleStrs) {
          this.title = res.data.titleStrs[0]
          this.timeStr = res.data.titleStrs[1]
          if (year === '-1' && month === '-1') {
            this.firstTimeStr = res.data.titleStrs[1]
          }
          this.getTableData()
        }
      })
    },
    /** 获取页面上的累计数据以及本年上月的期末结余信息 */
    getTotalInfoList () {
      let strs = this.timeStr.split('-')
      coalSafetyFeeSummarySublist.getTotalInfo(this.$store.getters.authUser.enterpriseId, strs[0], strs[1]).then((res) => {
        if (res && res.data.lists) {
          this.totalInfo.rawCoalTotal = Number(res.data.lists[0])
          this.totalInfo.extractionTotalTotal = Number(res.data.lists[1])
          this.totalInfo.totalCostsTotal = Number(res.data.lists[2])
          this.totalInfo.totalCostsMonth = Number(res.data.lists[3])
          let obj = this.coalForm.tableData[0]
          if (Number(res.data.lists[4]) > 0) {
            this.totalInfo.preBeginBalance = Number(res.data.lists[4])
            obj.beginBalance = Number(res.data.lists[4]) === 0 ? '' : Number(res.data.lists[4]) // 一个页面固定只有一条数据
          }
          // obj.rawCoalTotal = this.totalInfo.rawCoalTotal
          obj.extractionTotalTotal = Number(this.totalInfo.extractionTotalTotal) === 0 ? '' : Number(this.totalInfo.extractionTotalTotal)
          obj.totalCostsTotal = Number(this.totalInfo.totalCostsTotal) + Number(this.totalInfo.totalCostsMonth)
          obj.totalCostsMonth = Number(this.totalInfo.totalCostsMonth)
          this.$set(this.coalForm.tableData, this.coalForm.tableData[0], obj)
        }
      })
    },
    /** 原煤产量本月 */
    changeRawCoalMonth (formName, row, index) {
      this.$refs[formName].validateField(`tableData.${index}.rawCoalMonth`, (vaild) => {
        if (!vaild) {
          if (row.tonsCoalExtractionMonth) {
            this.cal(index)
          }
        }
      })
    },
    /** 吨煤提取 */
    changeTonsCoalExtractionMonth (radioval) {
      this.visible = false
      this.$set(this.coalForm.tableData[0], 'tonsCoalExtractionMonth', radioval)
      this.$set(this.coalForm.tableData[0], 'tonsCoalExtractionTotal', radioval)
      this.$refs['coalForm'].validateField(`tableData.0.tonsCoalExtractionMonth`, (vaild) => {
        if (!vaild) {
          if (this.coalForm.tableData[0].rawCoalMonth) {
            this.cal(0)
          }
        }
      })
    },
    /**
     * 吨煤提取-本月
     * input@change事件
     * 给【累计】赋值
     */
    changeInput (val) {
      this.$nextTick(() => {
        this.$set(this.coalForm.tableData[0], 'tonsCoalExtractionMonth', val)
        this.$set(this.coalForm.tableData[0], 'tonsCoalExtractionTotal', val)
        this.$refs['coalForm'].validateField(`tableData.0.tonsCoalExtractionMonth`, (vaild) => {
          if (!vaild) {
            if (this.coalForm.tableData[0].rawCoalMonth) {
              this.cal(0)
            }
          }
        })
      })
    },
    /** 进行计算 */
    cal (index) {
      let obj = this.coalForm.tableData[index]
      this.rawCoalTotal = (Number(this.totalInfo.rawCoalTotal) + Number(obj.rawCoalMonth)).toFixed(4)
      this.extractionTotalMonth = (Number(obj.rawCoalMonth) * Number(obj.tonsCoalExtractionMonth)).toFixed(4)
      this.extractionTotalTotal = (Number(obj.rawCoalMonth) * Number(obj.tonsCoalExtractionMonth) + Number(this.totalInfo.extractionTotalTotal)).toFixed(4)
      if (this.totalInfo.preBeginBalance !== '') {
        this.endingBalance = (Number(this.totalInfo.preBeginBalance) + Number(this.extractionTotalTotal) - Number(this.coalForm.tableData[index].totalCostsTotal)).toFixed(4)
      } else if (this.coalForm.tableData[index].beginBalance !== '') {
        this.endingBalance = (Number(this.coalForm.tableData[index].beginBalance) + Number(this.extractionTotalTotal) - Number(this.coalForm.tableData[index].totalCostsTotal)).toFixed(4)
      }
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 查询 */
    onSearch () {
      if (!this.searchForm.time) {
        this.$message.warning('请选择查询时间！')
        return false
      } else {
        let searchDate = new Date(this.searchForm.time)
        let searchDateStr = formatDate(searchDate, 'yyyy-MM')
        let searchDateStrList = searchDateStr.split('-')
        this.searchForm.year = Number(searchDateStrList[0])
        this.searchForm.month = Number(searchDateStrList[1])
        let currentDateStrList = this.firstTimeStr.split('-')
        if (Number(searchDateStrList[0]) > Number(currentDateStrList[0]) || (Number(searchDateStrList[0]) === Number(currentDateStrList[0]) && Number(searchDateStrList[1]) > Number(currentDateStrList[1]))) {
          this.$message.warning('该时间不允许录入！')
          return false
        }
        this.getTitleStr(this.searchForm.year, this.searchForm.month)
        // this.getTableData()
      }
    },
    /** 保存 */
    saveData (formName) {
      this.$refs[formName].validate().then((vaild) => {
        if (vaild) {
          let strs = this.timeStr.split('-')
          this.coalForm.tableData[0].status = 0
          this.coalForm.tableData[0].year = strs[0]
          this.coalForm.tableData[0].month = strs[1]
          this.coalForm.tableData[0].enterpriseId = this.$store.getters.authUser.enterpriseId
          /* this.coalForm.tableData[0].rawCoalTotal = Number(this.totalInfo.rawCoalTotal) + Number(this.coalForm.tableData[0].rawCoalMonth)
          this.coalForm.tableData[0].extractionTotalMonth = Number(this.coalForm.tableData[0].rawCoalMonth) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth)
          this.coalForm.tableData[0].extractionTotalTotal = Number(this.coalForm.tableData[0].rawCoalTotal) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth)
          this.coalForm.tableData[0].endingBalance = Number(this.coalForm.tableData[0].beginBalance) + Number(this.coalForm.tableData[0].rawCoalMonth) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth) + Number(this.totalInfo.extractionTotalTotal) - Number(this.coalForm.tableData[0].totalCostsMonth) */
          this.coalForm.tableData[0].rawCoalTotal = this.rawCoalTotal
          this.coalForm.tableData[0].extractionTotalMonth = this.extractionTotalMonth
          this.coalForm.tableData[0].extractionTotalTotal = this.extractionTotalTotal
          this.coalForm.tableData[0].endingBalance = this.endingBalance
          coalSafetyFeeSummarySublist.save(this.coalForm.tableData[0]).then((res) => {
            if (res.data.resCode === '0000') {
              this.getTableData()
            }
          })
        } else {
          return false
        }
      })
    },
    getTableData () {
      this.isLoading = true
      this.inputFlag = false
      let strs = this.timeStr.split('-')
      coalSafetyFeeSummarySublist.getList({
        enterpriseId: this.$store.getters.authUser.enterpriseId,
        year: this.searchForm.year ? this.searchForm.year : strs[0],
        month: this.searchForm.month ? this.searchForm.month : strs[1]
      }).then((res) => {
        if (res.data.coalSafetyFeeSummarySublistList.length > 0) {
          this.coalForm.tableData = res.data.coalSafetyFeeSummarySublistList.map(item => {
            item.rawCoalMonth = Number(item.rawCoalMonth)
            item.tonsCoalExtractionTotal = Number(item.tonsCoalExtractionTotal)
            return item
          })
          if (Number(res.data.coalSafetyFeeSummarySublistList[0].status) === 1) {
            this.inputFlag = true
          }
          this.rawCoalTotal = this.coalForm.tableData[0].rawCoalTotal
          this.endingBalance = this.coalForm.tableData[0].endingBalance
          this.extractionTotalMonth = this.coalForm.tableData[0].extractionTotalMonth
          this.extractionTotalTotal = this.coalForm.tableData[0].extractionTotalTotal
        } else {
          this.coalForm.tableData = [{ beginBalance: '', status: '', rawCoalMonth: '', rawCoalTotal: '', tonsCoalExtractionMonth: 15, tonsCoalExtractionTotal: 15, totalCostsMonth: '' }]
          this.rawCoalTotal = ''
          this.endingBalance = ''
          this.extractionTotalMonth = ''
          this.extractionTotalTotal = ''
          this.getTotalInfoList()
        }
        this.isLoading = false
      })
    },
    /** 上报 */
    reportData (formName) {
      this.$refs[formName].validate().then((vaild) => {
        if (vaild) {
          let msg = '请确认本月使用总额是否正确，上报后将无法修改，是否继续？'
          /* if (Number(this.coalForm.tableData[0].totalCostsMonth) === 0) {
            msg = '您还未进行煤安费完成情况上报，请先完成上报。'
          } else {
            msg = '请确认本月使用总额是否正确，上报后将无法修改，是否继续？'
          } */
          this.$confirm(msg, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let strs = this.timeStr.split('-')
            this.coalForm.tableData[0].status = 1
            this.coalForm.tableData[0].year = strs[0]
            this.coalForm.tableData[0].month = strs[1]
            this.coalForm.tableData[0].enterpriseId = this.$store.getters.authUser.enterpriseId
            // this.coalForm.tableData[0].rawCoalTotal = Number(this.totalInfo.rawCoalTotal) + Number(this.coalForm.tableData[0].rawCoalMonth)
            // this.coalForm.tableData[0].extractionTotalMonth = Number(this.coalForm.tableData[0].rawCoalMonth) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth)
            // this.coalForm.tableData[0].extractionTotalTotal = Number(this.coalForm.tableData[0].rawCoalTotal) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth)
            // this.coalForm.tableData[0].endingBalance = Number(this.coalForm.tableData[0].beginBalance) + Number(this.coalForm.tableData[0].rawCoalMonth) * Number(this.coalForm.tableData[0].tonsCoalExtractionMonth) + Number(this.totalInfo.extractionTotalTotal) - Number(this.coalForm.tableData[0].totalCostsMonth)
            this.coalForm.tableData[0].rawCoalTotal = this.rawCoalTotal
            this.coalForm.tableData[0].extractionTotalMonth = this.extractionTotalMonth
            this.coalForm.tableData[0].extractionTotalTotal = this.extractionTotalTotal
            this.coalForm.tableData[0].endingBalance = this.endingBalance
            coalSafetyFeeSummarySublist.save(this.coalForm.tableData[0]).then((res) => {
              if (res.data.resCode === '0000') {
                this.getTableData()
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            })
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style lang="less">
#coalsafety-use {
  .search-box {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .summary-box {
    margin: 20px 20px 130px 20px;
    padding: 20px 5px;
    background-color: #fff;
    .report-name {
      text-align: left;
    }
    .save-boxs {
      padding: 10px;
    }
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .el-form-item__error {
    display: none;
  }
}
</style>
