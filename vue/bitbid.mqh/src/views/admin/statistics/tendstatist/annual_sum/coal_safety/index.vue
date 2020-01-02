<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="coalsafety-page">
    <div v-if="this.$route.path == '/admin/statistics/coalsafety'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item>年度汇总表</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/coalsafety' }">{{capitalSourceName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline total-page">
          <el-row :gutter="20" style="text-align:left;">
            <el-col :span="10">
              <el-form-item label="金额(万元):">
                <el-select v-model="searchForm.moneyType" clearable placeholder="请选择" style="float:left;width: 125px;" @change="changeMoneyType">
                  <el-option label="计划金额" value="1"></el-option>
                  <el-option label="合计" value="2"></el-option>
                </el-select>
                <span style="float:left;display:inline-block;color:#999;">介于</span>
                <el-input placeholder="请输入内容" v-model="searchForm.mixPrice" class="input-with-select" style="float:left;width:100px;">
                </el-input>
                <span style="display:inline-block;color: #ddd;float:left;">--</span>
                <el-input placeholder="请输入内容" v-model="searchForm.maxPrice" class="input-with-select" style="float:left;width:100px;">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="单位名称:">
                <el-input v-model="searchForm.enterpriseName" placeholder="单位名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="时间:">
                <el-select v-model="searchForm.year" clearable placeholder="请选择" @change="getYear">
                  <el-option :label="item" :value="item" v-for="(item, index) in yearList" :key="index"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="onSearch(searchForm)">搜索</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="4"></el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 搜索区域end -->

      <!-- 主题内容-汇总表start -->
      <div class="summary-box">
       <div class="down-title">
          <el-button type="primary" size="mini" icon="iconfont mqh-icon-transfer" v-if="$store.getters.permissions.includes('/statistics/coalsafety/operation')" @click="expertExcel">导出</el-button>
          <!--<el-button type="primary" size="mini" icon="iconfont mqh-icon-dayin" v-if="$store.getters.permissions.includes('/statistics/coalsafety/operation')">打印</el-button>-->
        </div>
        <div class="summary-table">
          <div class="summary-table-tit">
            <h2>{{year}}年{{capitalSourceName}}计划完成汇总表</h2>
          </div>
          <div class="summary-unit">
            <span>单位：万元</span>
          </div>
          <div :class="{'table1':typeFlag!==4}" v-if="typeFlag!==4&&randomNum">
            <el-table
              ref="tableSum1"
              :data="summaryTableData"
              :row-class-name="tableRowClassName"
              @cell-click="cellClick"
              border
              height="300"
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                align="center"
                :index="indexMethod"
                width="55">
              </el-table-column>
              <el-table-column
                prop="enterpriseName"
                align="center"
                label="单位名称">
                <template slot-scope="scope">
                  <el-button type="text" v-if="scope.$index!=0">{{scope.row.enterpriseName}}</el-button>
                  <span v-if="scope.$index==0">{{scope.row.enterpriseName}}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="planPrice"
                align="center"
                label="计划金额">
              </el-table-column>
              <el-table-column
                prop="totalPrice"
                align="center"
                label="合计">
              </el-table-column>
              <template  v-for="(item, index) in columnList">
                <el-table-column
                  :key="index"
                  :label="item.name"
                  :prop="item.shortName.toLowerCase()"
                  align="center">
                  <template v-if="item.twofundPurposeList">
                    <el-table-column
                      v-if="item.twofundPurposeList.length>0"
                      prop="priceV"
                      align="center"
                      label="小计">
                    </el-table-column>
                    <el-table-column
                      v-for="(child, index) in item.twofundPurposeList"
                      :key="index"
                      :prop="child.shortName.toLowerCase()"
                      align="center"
                      :label="child.name">
                    </el-table-column>
                  </template>
                </el-table-column>
              </template>
              <el-table-column
                prop="bcjh"
                align="center"
                label="补充计划">
              </el-table-column>
            </el-table>
          </div>
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
      </div>
      <!-- 主题内容-汇总表end -->
      <!-- 图标区域start -->
      <div class="charts-box">
        <el-row :gutter="20">
          <el-col :span="7">
            <div class="charts-pic charts-pie">
              <span class="charts-tit">各项目单位计划金额所占比例</span>
              <div id="myChartLeft" class="pie-box" style="height:320px;"></div>
            </div>
          </el-col>
          <el-col :span="7">
            <div class="charts-pic charts-pie">
              <span class="charts-tit">各项目单位累计完成金额所占比例</span>
              <div id="myChartMiddle" class="pie-box" style="height:320px;"></div>
            </div>
          </el-col>
          <el-col :span="10">
            <div class="charts-pic charts-bar">
              <span class="charts-tit">各项目单位计划完成情况</span>
              <div id="myChartRight" class="bar-box" style="height:320px;"></div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 图标区域end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {commonJs} from 'common/js/common'
import {investmanage, yearPlanSummary} from 'api/index'
const Echarts = require('echarts/echarts')
require('echarts/chart/pie')
require('echarts/chart/bar')
export default {
  name: '',
  data () {
    return {
      histogramChart: {}, // 柱状图
      rightChart: {}, // 饼状图右
      leftChart: {}, // 饼状图左
      totalObj: {}, // 总计对象
      randomNum: Math.random(),
      currentView: 'first',
      columnList: [],
      typeFlag: Number(this.$route.query.typeFlag),
      capitalSourceId: null,
      capitalSourceName: '',
      type: null,
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      pageSizeList: [10, 20, 30, 40, 50],
      total: 0,
      year: new Date().getFullYear(),
      yearList: this.getYearMethed(),
      searchForm: {}, // 搜索表单
      fundPurposeFirstList: [], // 资金投向一级
      fundPurposeSecondList: [], // 资金投向二级
      moneyType: '', // 金额类型-搜索
      summaryTableData: [] // 汇总表格数据
    }
  },
  mounted () {
    this.getCapitalSourceList()
  },
  watch: {
    '$route': 'fechData',
    typeFlag: function (newV, oldV) {
      if (newV) {
        this.getCapitalSourceList()
        this.summaryTableData = []
      }
    }
  },
  methods: {
    fechData (to, from, next) {
      this.typeFlag = Number(this.$route.query.typeFlag)
    },
    expertExcel () {
      window.open(`${commonJs.yearPlanExportUrl}?&capitalSource=${this.capitalSourceId}&capitalSourceName=${this.capitalSourceName}&year=${this.year || this.searchForm.year}&enterpriseName=${this.searchForm.enterpriseName}&type=${2}`)
    },
    /** 计算年份-初始值为2017 */
    getYearMethed () {
      // 返回结果
      let arr = []
      // 获得当前年份
      let date = new Date()
      let nextYear = date.getFullYear() + 1
      let previousYear = date.getFullYear() - 1
      for (let i = previousYear; i <= nextYear; i++) {
        arr.push(i)
      }
      arr = arr.reverse()
      return arr
    },
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 序号 */
    indexMethod (index) {
      if (index === 0) {
        return ''
      } else {
        return index + (this.currentPage - 1) * 10
      }
    },
    /** 分页 */
    handleSizeChange (val) {
      this.pageSize = val
      this.getYearPlanSummaryList(this.pageNo)
    },
    handleCurrentChange (nowNum) {
      // 前台页面展示当前页
      this.currentPage = nowNum
      // 后台需要的参数
      this.pageNo = (nowNum - 1) * this.pageSize
      this.getYearPlanSummaryList(this.pageNo)
    },
    // 搜索金额框连带置空
    changeMoneyType (val) {
      if (val === null || val === '') {
        this.searchForm.mixPrice = ''
        this.searchForm.maxPrice = ''
      }
    },
    /** 单元格点击-单位名称点击 */
    cellClick (row, column, cell, event) {
      if (column.label === '单位名称' && row.enterpriseName !== '总计') {
        this.$router.push({path: '/admin/statistics/coalsafety/coalsafety-details',
          query: {enterpriseId: row.enterpriseId, enterpriseName: row.enterpriseName, capitalSourceId: this.capitalSourceId, type: this.type, year: this.year, menuId: this.typeFlag}})
      }
    },
    /** 柱状图 */
    drawLine () {
      // 基于准备好的dom，初始化echarts实例
      let myChart = Echarts.init(document.getElementById('myChartRight'))
      // 获取数据
      let chartData = this.histogramChart
      // 绘制图表
      myChart.setOption({
        legend: {
          data: ['计划金额', '完成金额']
        },
        tooltip: {
          trigger: 'axis'
        },
        dataZoom: {
          show: true,
          realtime: true,
          start: 0,
          end: 50
        },
        calculable: false,
        xAxis: {
          data: chartData.data
        },
        yAxis: {
          name: '金额（万元）'
        },
        series: [
          {
            name: '计划金额',
            type: 'bar',
            data: chartData.dataTwo,
            barGap: '0',
            itemStyle: {
              normal: {
                color: '#5393ff'
              }
            }
          },
          {
            name: '完成金额',
            type: 'bar',
            data: chartData.dataTitle,
            barGap: '0',
            itemStyle: {
              normal: {
                color: '#57cf8c'
              }
            }
          }
        ]
      })
    },
    /** 饼状图-左 */
    drawPieLeft () {
      let myChart = this.$echarts.init(document.getElementById('myChartLeft'))
      // 获得数据
      let chartData = this.leftChart || {}
      // 绘制图表
      myChart.setOption({
        legend: {
          type: 'scroll',
          orient: 'vertical',
          top: 20,
          bottom: 20,
          x: 'right',
          data: chartData.dataTitle,
          formatter: function (name) {
            return name.length > 8 ? name.substr(0, 7) + '...' : name
          }
        },
        tooltip: {
          trigger: 'item',
          enterable: true,
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        calculable: false,
        series: [{
          name: '',
          type: 'pie',
          radius: '50%',
          center: ['40%', '50%'],
          data: chartData.data,
          itemStyle: {
            normal: {
              label: {
                show: false
              },
              labelLine: {
                show: false
              }
            },
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      })
    },
    /** 饼状图-中 */
    drawPieMiddle () {
      let myChart = this.$echarts.init(document.getElementById('myChartMiddle'))
      // 获得数据
      let chartData = this.rightChart
      // 绘制图表
      myChart.setOption({
        legend: {
          type: 'scroll',
          orient: 'vertical',
          top: 20,
          bottom: 20,
          x: 'right',
          data: chartData.dataTitle,
          formatter: function (name) {
            return name.length > 8 ? name.substr(0, 7) + '...' : name
          }
        },
        tooltip: {
          trigger: 'item',
          enterable: true,
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        calculable: false,
        series: [{
          name: '',
          type: 'pie',
          radius: '55%',
          center: ['40%', '50%'],
          data: chartData.data,
          itemStyle: {
            normal: {
              label: {
                show: false
              },
              labelLine: {
                show: false
              }
            },
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      })
    },
    /**
     *  查询资金投向-表头列表
     */
    getFundPurposeList () {
      // 工程建设
      let query = {}
      if (this.capitalSourceId === 0) {
        query.type = 2
      } else {
        query.type = 1
        query.capitalSourceId = this.capitalSourceId
      }
      investmanage.getFundPurposeOneList(query).then((res) => {
        this.columnList = res.data.fundPurposeList
        this.getYearPlanSummaryList(this.pageNo)
      })
    },
    /**
     * 查询资金投向一级
     */
    getFundPurposeFirstList () {
      let query = {
        level: 1,
        parentId: 0,
        capitalSourceId: this.capitalSourceId,
        type: this.type
      }
      investmanage.getFundPurposeList(query).then((res) => {
        this.fundPurposeFirstList = []
        this.fundPurposeFirstList = res.data.fundPurposeList
      })
    },
    /** 图表方法获取 */
    handleChart (data) {
      if (data.length === 0) {
        this.leftChart = {}
        this.rightChart = {}
        this.histogramChart = {}
      } else {
        let resultOne = {
          data: [],
          dataTitle: []
        }
        let resultTwo = {
          data: [],
          dataTitle: []
        }
        let resultThree = {
          data: [],
          dataTitle: [],
          dataTwo: []
        }
        // 处理数据
        this.leftChart = data.map(item => {
          let objData = {
            name: item.enterpriseName,
            value: Number(item.planPrice)
          }
          resultOne.data.push(objData)
          resultOne.dataTitle.push(item.enterpriseName)
        })
        this.leftChart = resultOne
        this.rightChart = data.map(item => {
          let objData = {
            name: item.enterpriseName,
            value: item.totalPrice
          }
          resultTwo.data.push(objData)
          resultTwo.dataTitle.push(item.enterpriseName)
        })
        this.rightChart = resultTwo
        this.histogramChart = data.map(item => {
          resultThree.data.push(item.enterpriseName)
          resultThree.dataTitle.push(item.totalPrice ? item.totalPrice : 0)
          resultThree.dataTwo.push(item.planPrice)
        })
        this.histogramChart = resultThree
      }
      this.drawLine()
      this.drawPieLeft()
      this.drawPieMiddle()
    },
    /** 查询资金投向 - 二级 */
    getSecondByFirstList () {
      let parentId = this.searchForm.fundPurposeFirst
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
    /** 切换年份搜索条件 */
    getYear (year) {
      if (year) {
        this.year = year
      } else {
        this.year = new Date().getFullYear()
      }
    },
    /** 查询 */
    onSearch (searchForm) {
      this.pageNo = 0
      this.currentPage = 1
      this.getYearPlanSummaryList(this.pageNo)
    },
    /** 列表查询 */
    getYearPlanSummaryList (pageNo) {
      this.summaryTableData = []
      if (this.capitalSourceId || this.type) {
        let query = {
          pageNo: pageNo,
          pageSize: this.pageSize,
          capitalSource: this.capitalSourceId,
          year: this.searchForm.year || this.year || null,
          messageLike: this.searchForm.enterpriseName || null,
          moneyType: this.searchForm.moneyType || null,
          mixPrice: this.searchForm.mixPrice || null,
          maxPrice: this.searchForm.maxPrice || null,
          type: 2,
          capitalSourceName: this.capitalSourceName
        }
        yearPlanSummary.getList(query).then((res) => {
          if (res.data && res.data.yearPlanSummaryList.list && res.data.yearPlanSummaryList.list.length) {
            let yearPlanSummaryList = res.data.yearPlanSummaryList.list
            this.total = res.data.yearPlanSummaryList.total
            this.totalObj = res.data.yearPlanSummaryTotal
            this.summaryTableData = yearPlanSummaryList.map(item => {
              if (item.yearPlanSummaryPurposeList.length > 0) {
                // 一通三防或者其他父级
                item.yearPlanSummaryPurposeList.map(child => {
                  let key = ''
                  if (child.fundPurposeShortName) {
                    key = child.fundPurposeShortName.toLowerCase()
                  }
                  child[key] = child.price
                  if (child.yearPlanSummaryPurposeSublilstList && child.yearPlanSummaryPurposeSublilstList.length > 0) {
                    // 一通三防子级
                    child.yearPlanSummaryPurposeSublilstList.map(lastChild => {
                      let key = ''
                      if (lastChild.twoFundPurposeShortName) {
                        key = lastChild.twoFundPurposeShortName.toLowerCase()
                      } else {
                        key = 'priceV'
                      }
                      lastChild[key] = lastChild.price
                      // 合并对象
                      Object.assign(child, lastChild)
                      return lastChild
                    })
                  }
                  // 合并对象
                  Object.assign(item, child)
                  return child
                })
              }
              return item
            })
            // 判断对象是否为空值
            let obj = {}
            if (this.summaryTableData.length) {
              obj = Object.assign({enterpriseName: '总计'}, this.totalObj)
              // 添加总合计
              this.summaryTableData.unshift(obj)
            }
            this.handleChart(yearPlanSummaryList)
          } else {
            this.handleChart([])
          }
        })
      }
    },
    /**
     * 查询资金来源
     */
    getCapitalSourceList () {
      this.searchForm = {}
      if (this.typeFlag === 1) {
        this.capitalSourceId = 10001
        this.type = 1
        this.capitalSourceName = '折旧(更改)费汇总表'
      } else if (this.typeFlag === 2) {
        this.capitalSourceId = 10002
        this.type = 1
        this.capitalSourceName = '井巷工程费汇总表'
      } else if (this.typeFlag === 3) {
        this.capitalSourceId = 10003
        this.type = 1
        this.capitalSourceName = '维简费汇总表'
      } else if (this.typeFlag === 5) {
        this.capitalSourceId = 10005
        this.type = 1
        this.capitalSourceName = '环境治理恢复基金汇总表'
      } else if (this.typeFlag === 6) {
        this.capitalSourceId = 10006
        this.type = 1
        this.capitalSourceName = '危化品安全资金汇总表'
      } else if (this.typeFlag === 7) {
        this.capitalSourceId = 0
        this.type = 2
        this.capitalSourceName = '工程建设汇总表'
      }
      this.getFundPurposeFirstList()
      // 表格表头数据
      this.getFundPurposeList()
      // this.drawPieLeft()
      // this.drawLine()
      // this.drawPieMiddle()
    }
  }
}
</script>
<style lang="less">
#coalsafety-page {
  .search-box .total-page {
    .el-form-item {
      margin-bottom: 0;
    }
  }
  .summary-box {
    margin: 0 20px 20px 20px;
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
        padding-top: 8px;
      }
    }
    .block-page {
      padding: 15px;
    }
  }

  .charts-box {
    margin: 0 20px 100px 20px;
    display: block;
    height: 400px;
    .charts-pic {
      display: block;
      content: '';
      height: 365px;
      background: #fff;
      .charts-tit {
        display: block;
        background: #cfd8fd;
        text-align: left;
        padding: 5px;
        padding-left: 15px;
      }
    }
    // 柱状图
    .bar-box {
      width: 100%;
      height: 100%;
      margin: 0 auto;
    }
    .charts-pie {
      .pie-box {
        width: 100%;
        height: 100%;
        margin: 0 auto;
      }
    }
  }
}
</style>
