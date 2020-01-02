<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="bid-statist">
    <div v-if="this.$route.path == '/admin/statistics/bid-statist'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/bid-statist' }">招标情况统计</el-breadcrumb-item>
        </el-breadcrumb>
        <!--<el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
          返回
        </el-button>-->
      </div>
      <!-- 搜索区域start -->
      <div class="search-box">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline" style="text-align:left;">
          <el-form-item label="项目名称:">
            <el-input v-model="searchForm.suggestedPlanProjectMameLike" placeholder="项目名称" style="width: 160px;"></el-input>
          </el-form-item>
          <el-form-item label="项目类型:">
            <el-select v-model="searchForm.projectType" clearable placeholder="请选择" class="search-sel"  style="width: 160px;">
              <el-option
                v-for="item in typeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="代理机构:">
            <el-input v-model="searchForm.entrustEnterpriseNameLike" placeholder="代理机构"  style="width: 160px;"></el-input>
          </el-form-item>
          <el-form-item label="时间:">
            <el-date-picker
              v-model="searchForm.time"
              @change = "filterTime"
              type="monthrange"
              range-separator="至"
              start-placeholder="开始月份"
              end-placeholder="结束月份">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
          </el-form-item>
        </el-form>
      </div>
      <!-- 搜索区域end -->
      <!-- 表格区域start -->
      <div v-loading="loading">
      <div class="main-box">
        <el-row :gutter="20">
          <el-col :span="24">
            <div class="table-box">
              <div style="height:79px;">
                <h2 style="height:25px;">{{titleStr}}</h2>
                <p style="text-align: right;">
                  <span>投资单位:万元</span>
                </p>
              </div>
              <el-table
                :data="tableData"
                :row-class-name="tableRowClassName"
                :cell-style="setCellStyle"
                border
                height="286"
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
                  show-overflow-tooltip
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="projectType"
                  label="项目类型"
                  align="center"
                 :formatter="filterProjectType">
                </el-table-column>
                <el-table-column
                  prop="completeBidType"
                  label="采购方式"
                  align="center">
                  <template slot-scope="scope">
                    <el-tooltip placement="top">
                      <div slot="content">
                        <p v-if="!String(scope.row.completeBidType).split('-').includes('0')">招采项目采购方式: {{scope.row.completeBidType | filterBidType}}</p>
                        <p>项目采购方式: {{ scope.row.completeBidType | filterProBidType }}</p>
                      </div>
                      <div>
                        {{scope.row.completeBidType | filterCompleteBidType}}
                      </div>
                    </el-tooltip>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="capitalSourceName"
                  label="资金来源"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="fundPurposeName"
                  label="资金投向"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="planAmount"
                  label="计划金额"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="year"
                  label="计划年度"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="entrustEnterpriseName"
                  label="招标代理机构"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="bidderNameList"
                  label="投标单位"
                  show-overflow-tooltip
                  align="center">
                  <template slot-scope="scope">
                    <span v-for="(item, index) in scope.row.bidderNameList" :key="index" class="tbnuit-span">
                      {{item}}
                      <span v-if="scope.row.bidderNameList.length>1">;<br/></span>
                    </span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="bidWinnerName"
                  label="中标单位"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="bidPrice"
                  label="中标金额"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="savePrice"
                  label="节约金额"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="resultNoticeTime"
                  label="公示时间"
                  align="center"
                  :formatter="filterDate">
                </el-table-column>
                <el-table-column
                  prop="enterpriseName"
                  label="单位名称"
                  align="center">
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>

      </div>
      <!-- 表格区域end -->

      <!-- 图表区域start -->
      <div class="charts-box">
        <el-row :gutter="20">
          <el-col :span="14">
            <div class="bar-box">
              <span class="charts-tit">代理机构中标金额统计</span>
              <div id="stack_charts" class="stack-charts" style="height:360px;"></div>
            </div>
          </el-col>
          <el-col :span="5">
            <!-- 字符云 -->
            <div class="wordCloud-box">
              <span class="charts-tit">项目分布</span>
              <div id="wordCloud" class="word-cloud" style="width:100%;height:360px;">
              </div>
            </div>
          </el-col>
          <el-col :span="5">
            <div class="pie-box">
              <span class="charts-tit">项目类型占比</span>
              <div id="pie_charts" class="pie-charts" style="height:360px;"></div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 图表区域end -->
      </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import {tenderStatusStatistic} from 'api/index'
import {formatDate} from 'common/js/date.js'
const Echarts = require('echarts/echarts')
require('echarts/chart/pie')
require('echarts/chart/bar')
require('echarts/chart/wordCloud')
export default {
  name: 'bid-statist',
  data () {
    return {
      searchForm: {},
      tableData: [{projectName: '合计', planAmount: '', bidPrice: '', savePrice: ''}],
      currentPage: 1,
      pageNo: 0,
      pageSize: 10,
      total: null, // 总条数
      pageSizeList: [10, 20, 30, 40, 50],
      titleStr: '',
      pieChartsInfo: {
        tenderProjectTypeName: [],
        tenderProjectTypeList: []
      },
      wordCloudInfo: {
        wordCloudInfoList: []
      },
      stackChartsInfo: {
        capitalSourceList: [],
        agencyEnterpriseNameList: [],
        seriesList: []
      },
      capitalSourceList: [],
      agencyEnterpriseNameList: [],
      typeList: [
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
        }],
      lowerLimit: null,
      upperLimit: null,
      loading: false
    }
  },
  created () {
    this.getTableData()
  },
  mounted () {
  },
  filters: {
    filterBidType (val) {
      if (!val) return ''
      let resultStr = ''
      function filterType (val) {
        let obj = {
          0: '',
          1: '公开招标',
          2: '邀请招标',
          3: '谈判采购',
          4: '询比采购',
          5: '竞价采购',
          6: '直接采购',
          7: '框架协议'
        }
        return obj[val]
      }
      let strList = val.split('-').map(item => {
        return item - 0
      })
      if (strList[0] === 0) {
        resultStr = ''
        return resultStr
      } else {
        resultStr = filterType(strList[0])
      }
      return resultStr
    },
    filterProBidType (val) {
      if (!val) return ''
      let resultStr = ''
      function filterType (val) {
        let obj = {
          0: '',
          1: '公开招标',
          2: '邀请招标',
          3: '谈判采购',
          4: '询比采购',
          5: '竞价采购',
          6: '直接采购',
          7: '框架协议'
        }
        return obj[val]
      }
      let strList = val.split('-').map(item => {
        return item - 0
      })
      resultStr = filterType(strList[1])
      return resultStr
    },
    filterCompleteBidType (completeBidType) {
      let resultStr = ''
      let strF = ''
      let strS = ''
      function filterType (val) {
        let obj = {
          0: '',
          1: '公开招标',
          2: '邀请招标',
          3: '谈判采购',
          4: '询比采购',
          5: '竞价采购',
          6: '直接采购',
          7: '框架协议'
        }
        return obj[val]
      }
      if (completeBidType) {
        let strList = completeBidType.split('-').map(item => {
          return item - 0
        })
        if (strList.length > 0) {
          if (strList.includes(0)) {
            strF = filterType(strList[0])
            strS = filterType(strList[1])
            resultStr = `${strF}${strS}`
            return resultStr
          } else {
            strF = filterType(strList[0])
            strS = filterType(strList[1])
            resultStr = `${strF}(${strS})`
            return resultStr
          }
        }
      }
    }
  },
  methods: {
    filterProjectType (row) {
      if (row.projectType) {
        if (Number(row.projectType) === 1) {
          return '工程'
        } else if (Number(row.projectType) === 2) {
          return '货物'
        } else {
          return '服务'
        }
      }
    },
    // 格式化申请时间
    filterDate (row) {
      if (row.resultNoticeTime) {
        let date = new Date(row.resultNoticeTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
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
    tableRowClassName ({row, rowIndex}) {
      if (rowIndex % 2 !== 0) {
        return 'table-row-bg'
      }
    },
    /** 设置单元格样式 */
    setCellStyle ({row, column, rowIndex, columnIndex}) {
      if (column.label === '中标单位') {
        return 'tbnuit'
      }
    },
    filterTime (value) {
      if (value) {
        this.lowerLimit = formatDate(value[0], 'yyyy-MM')
        this.upperLimit = formatDate(value[1], 'yyyy-MM')
      } else {
        this.lowerLimit = null
        this.upperLimit = null
      }
    },
    /** 获取列表数据 */
    getTableData () {
      this.loading = true
      tenderStatusStatistic.getList({
        biddingType: 1,
        sourceType: 1,
        isConfirm: 1,
        searchTimeType: this.upperLimit ? 1 : 0,
        lowerLimit: this.lowerLimit ? this.lowerLimit : formatDate(new Date(), 'yyyy-MM'),
        suggestedPlanProjectMameLike: this.searchForm.suggestedPlanProjectMameLike || null,
        entrustEnterpriseNameLike: this.searchForm.entrustEnterpriseNameLike || null,
        suggestedPlanProjectType: this.searchForm.projectType || null,
        upperLimit: this.upperLimit
      }).then((res) => {
        // 清空
        this.tableData = [{projectName: '合计', planAmount: '', bidPrice: '', savePrice: '0.00'}]
        this.pieChartsInfo.tenderProjectTypeName = []
        this.pieChartsInfo.tenderProjectTypeList = []
        this.wordCloudInfo.wordCloudInfoList = []
        this.stackChartsInfo.capitalSourceList = []
        this.stackChartsInfo.agencyEnterpriseNameList = []
        this.stackChartsInfo.seriesList = []
        this.canvasPie()
        this.canvasWordCloud()
        this.canvasStack()
        // 查询
        if (res.data.tenderStatusStatisticModel) {
          // 标题
          this.titleStr = res.data.tenderStatusStatisticModel.titleName
          // 列表
          if (res.data.tenderStatusStatisticModel.tenderStatusInfoList.length > 0) {
            let obj = this.tableData[0]
            let rows = res.data.tenderStatusStatisticModel.tenderStatusInfoList
            for (let i = 0; i < rows.length; i++) {
              obj.planAmount = Number(obj.planAmount) + Number(rows[i].planAmount)
              obj.bidPrice = Number(obj.bidPrice) + Number(rows[i].bidPrice)
              if (rows[i].bidPrice) {
                rows[i].savePrice = (Number(rows[i].planAmount) - Number(rows[i].bidPrice)).toFixed(2)
                obj.savePrice = Number(obj.savePrice) + Number(rows[i].savePrice)
              } else {
                rows[i].savePrice = ''
              }
            }
            obj.planAmount = Number(obj.planAmount).toFixed(2)
            obj.bidPrice = Number(obj.bidPrice).toFixed(2)
            obj.savePrice = Number(obj.savePrice).toFixed(2)
            this.tableData[0] = obj
            this.tableData = this.tableData.concat(res.data.tenderStatusStatisticModel.tenderStatusInfoList)
          }
          // 饼状图
          if (res.data.tenderStatusStatisticModel.tenderProjectTypeList && res.data.tenderStatusStatisticModel.tenderProjectTypeName) {
            this.pieChartsInfo.tenderProjectTypeName = res.data.tenderStatusStatisticModel.tenderProjectTypeName
            this.pieChartsInfo.tenderProjectTypeList = res.data.tenderStatusStatisticModel.tenderProjectTypeList
            this.canvasPie()
          }
          // 字符云
          if (res.data.tenderStatusStatisticModel) {
            this.wordCloudInfo.wordCloudInfoList = res.data.tenderStatusStatisticModel.capitalSourceStatisticsList
            this.wordCloudInfo.wordCloudInfoList.map(item => {
              item.value = Number(item.value)
              item.itemStyle = this.createRandomItemStyle()
              return item
            })
            this.canvasWordCloud()
          }
          // 堆叠图
          if (res.data.tenderStatusStatisticModel && res.data.tenderStatusStatisticModel.suggestedPlanSummary) {
            let suggestedPlanSummary = res.data.tenderStatusStatisticModel.suggestedPlanSummary
            this.capitalSourceList = []
            this.agencyEnterpriseNameList = []
            let zjggMap = suggestedPlanSummary.zjggMap
            let jxgcfMap = suggestedPlanSummary.jxgcfMap
            let wjfMap = suggestedPlanSummary.wjfMap
            let mtaqzjMap = suggestedPlanSummary.mtaqzjMap
            let hjzlbzjMap = suggestedPlanSummary.hjzlbzjMap
            let whpaqzjMap = suggestedPlanSummary.whpaqzjMap
            let gcjsMap = suggestedPlanSummary.gcjsMap
            this.getMapInfo(zjggMap)
            this.getMapInfo(jxgcfMap)
            this.getMapInfo(wjfMap)
            this.getMapInfo(mtaqzjMap)
            this.getMapInfo(hjzlbzjMap)
            this.getMapInfo(whpaqzjMap)
            this.getMapInfo(gcjsMap)
            this.stackChartsInfo.capitalSourceList = this.capitalSourceList
            this.stackChartsInfo.agencyEnterpriseNameList = this.agencyEnterpriseNameList
            if (this.stackChartsInfo.agencyEnterpriseNameList.length > 0) {
              this.canvasStack()
            }
          }
        }
        this.loading = false
      })
    },
    /** 判断map集合获取对应的数据 */
    getMapInfo (mapInfo) {
      if (mapInfo && Object.keys(mapInfo).length > 0) {
        this.agencyEnterpriseNameList = []
        let key0 = Object.keys(mapInfo)[0]
        this.capitalSourceList.push(mapInfo[key0].capitalSourceName) // 资金来源名称
        let obj = {
          name: mapInfo[key0].capitalSourceName,
          type: 'bar',
          stack: 'sum',
          data: []
        }
        let keys = Object.keys(mapInfo)
        keys.map((key, index) => {
          this.agencyEnterpriseNameList.push(key) // 代理名称
          obj.data.push(Number(mapInfo[key].capitalSourceValue))
        })
        this.stackChartsInfo.seriesList.push(obj)
      }
    },
    /** 搜索 */
    onSearch () {
      this.getTableData()
    },
    createRandomItemStyle () {
      return {
        normal: {
          color: 'rgb(' + [
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160),
            Math.round(Math.random() * 160)
          ].join(',') + ')'
        }
      }
    },
    /** 字符云 */
    canvasWordCloud () {
      let myChart = Echarts.init(document.getElementById('wordCloud'))
      let option = {
        tooltip: {
          show: true
        },
        series: [
          {
            name: '',
            type: 'wordCloud',
            shape: 'circle',
            size: ['100%', '100%'],
            sizeRange: [12, 30],
            textRotation: [0, 0],
            textPadding: 1,
            autoSize: {
              enable: true,
              minSize: 16
            },
            data: this.wordCloudInfo.wordCloudInfoList
          }
        ]
      }
      myChart.setOption(option)
    },
    /** 饼状图绘制 */
    canvasPie () {
      let myChart = Echarts.init(document.getElementById('pie_charts'))
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        color: ['#9FC2C5', '#DEB2A1', '#B9D6C9'],
        legend: {
          orient: 'vertical',
          x: 'right',
          data: this.pieChartsInfo.tenderProjectTypeName
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '50%',
            center: ['50%', '60%'],
            data: this.pieChartsInfo.tenderProjectTypeList,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      myChart.setOption(option)
    },
    /** 堆叠图绘制 */
    canvasStack () {
      let colorList = ['#C25552', '#51616D', '#77A8AD', '#D0917A', '#9CC5B2', '#86A692', '#C89447']
      let myChart = Echarts.init(document.getElementById('stack_charts'))
      let option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
          textStyle: {
            align: 'left'
          }
        },
        dataZoom: {
          show: true,
          realtime: true,
          start: 0,
          end: 50
        },
        legend: {
          data: this.capitalSourceList
        },
        // calculable: true,
        grid: {
          borderWidth: 0
        },
        xAxis: [
          {
            type: 'category',
            data: this.agencyEnterpriseNameList
          }
        ],
        yAxis: [
          {
            name: '万元',
            type: 'value'
          }
        ],
        series: this.stackChartsInfo.seriesList.map((item, index) => {
          item.itemStyle = {
            // 设置颜色
            normal: {
              color: colorList[index]
            }
          }
          return item
        })
      }
      myChart.setOption(option)
    }
  }
}
</script>
<style lang="less">
#bid-statist {
  .main-box {
    margin: 0 20px;
    .table-box {
      background: #fff;
      padding: 5px;
      .el-table__body-wrapper {
        tr td:nth-child(8) {
          .cell {
            padding: 0;
            .tbnuit-span {
              display: block;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              padding: 8px 5px;
            }
            .tbnuit-span:not(:last-child) {
              border-bottom: 1px solid #ddd;
            }
          }
        }
      }
    }
  }
  .charts-tit {
    display: block;
    content: '';
    height: 30px;
    line-height: 30px;
    text-align: left;
    padding-left: 15px;
    border-bottom: 1px solid #ddd;
    background: #cfd8fd;
  }
  .charts-box {
    margin: 20px;
    margin-bottom: 130px;
    .bar-box,
    .pie-box {
      background: #fff;
    }
    .wordCloud-box {
      background: #fff;
      .word-cloud {
        width: 100%;
        height: 100%;
      }
    }
  }
}
.el-tooltip__popper {
  width: 160px;
}
</style>
