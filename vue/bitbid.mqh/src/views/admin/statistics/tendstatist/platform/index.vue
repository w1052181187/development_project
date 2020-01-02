<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="platform-sum">
    <div v-if="this.$route.path == '/admin/statistics/platform-sum'">
      <div class="breadcrumb-mqh-tit">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>统计分析</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/admin/statistics/platform-sum' }">平台统计</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <!-- 数目统计-start -->
      <div class="summary-box">
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="plat-sum">
              <span class="plat-tit">累计完成公开招标项目总数</span>
              <p class="plat-number">{{tradeStatisticModel.totalNumber || 0}}</p>
              <div class="left-img">
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="plat-sum">
              <span class="plat-tit">累计完成公开交易金额（万元）</span>
              <p class="plat-number">{{tradeStatisticModel.totalTransactionAmount  || 0}}</p>
              <div class="left-img">
              </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="plat-sum">
              <span class="plat-tit">招标人数</span>
              <p class="plat-number">{{tradeStatisticModel.tendereeNumber  || 0}}</p>
              <div class="right-img">
              </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="plat-sum">
              <span class="plat-tit">投标人数</span>
              <p class="plat-number">{{tradeStatisticModel.bidderNumber  || 0}}</p>
              <div class="right-img">
              </div>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="plat-sum">
              <span class="plat-tit">招标代理数</span>
              <p class="plat-number">{{tradeStatisticModel.agencyNumber  || 0}}</p>
              <div class="right-img">
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 数目统计-end -->
      <!-- 各阶段项目数统计start -->
      <div class="phase-prosum">
        <span class="charts-tit">各阶段项目数统计</span>
        <div id="projectSumChart" class="bar-box"></div>
      </div>
      <!-- 各阶段项目数统计end -->

      <!-- 项目类型占比和企业分类start -->
      <div :class="{'project-enterprise': true, 'project-margin': enterpriseType !==1}" v-if="false">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="project-type">
              <span class="charts-tit">项目类型占比</span>
              <div id="projectType" class="pie-box"></div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="enterprise-type" >
              <span class="charts-tit">项目分布</span>
              <div id="enterpriseType" class="pie-box" style="width:663px;height:280px;"></div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 项目类型占比和企业分类end -->
      <!-- 招标代理公司数量统计and投标人数量统计start -->
      <div class="tendering-bid" v-if="$store.getters.authUser.enterpriseType === 1">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="project-type">
              <span class="charts-tit">招标代理公司数量统计</span>
              <div class="table-box">
                <el-table
                  :data="tenderingTableData"
                  :sort-orders="['ascending', 'descending']"
                  border
                  height="300"
                  style="width: 100%">
                  <el-table-column
                    type="index"
                    :index="indexMethod"
                    label="排名"
                    width="55"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="enterpriseName"
                    label="代理机构名称"
                    align="center">
                    <template slot-scope="scope">
                      <el-button type="text" @click="jumpToPage(scope.row, 'tender')" style="padding:0;">{{scope.row.enterpriseName}}</el-button>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="projectNumber"
                    sortable
                    label="项目数"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    sortable
                    prop="favorableRate"
                    label="好评率"
                    align="center">
                    <template slot-scope="scope">{{scope.row.favorableRate ? scope.row.favorableRate:'0.00'}}%</template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="enterprise-type" >
              <span class="charts-tit">投标人数量统计</span>
              <div class="table-box">
                 <el-table
                  :data="bidTableData"
                  @sort-change='sortChange'
                  :sort-orders="['ascending', 'descending']"
                  border
                  height="300"
                  style="width: 100%">
                  <el-table-column
                    type="index"
                    label="排名"
                    width="55"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="supplierName"
                    label="投标人名称"
                    align="center">
                    <template slot-scope="scope">
                      <el-button type="text" @click="jumpToPage(scope.row, 'bid')" style="padding:0;">{{scope.row.supplierName}}</el-button>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="projectNumber"
                    sortable="custom"
                    label="项目数"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="gainRate"
                    sortable="custom"
                    label="中标率"
                    align="center">
                    <template slot-scope="scope">{{scope.row.gainRate}}%</template>
                  </el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 招标代理公司数量统计and投标人数量统计end -->
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import { platform } from 'api/index'
const Echarts = require('echarts/echarts')
require('echarts/chart/pie')
require('echarts/chart/bar')
require('echarts/chart/wordCloud')
export default {
  name: 'platform',
  data () {
    return {
      enterpriseType: this.$store.getters.authUser.enterpriseType,
      currentPage: 1,
      tradeStatisticModel: {}, // 后台返回封装对象的模型
      stageProjectInfoModel: [], // 各阶段数据
      tenderingTableData: [], // 招标代理公司数量统计
      bidTableData: [], // 投标人数量统计
      orderType: 1, // 排序类型 1.项目数降序 2.项目数升序 3.好评率降序 4.好评率升序
      wordCloudInfo: {
        capitalSourceNameList: [],
        wordCloudInfoList: []
      }
    }
  },
  created () {
    this.initBaseInfo() // 基本信息
    this.initAgencyInfo() // 代理机构信息
    this.initSupplier() // 投标人信息
  },
  mounted () {

  },
  methods: {
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    /** 初始代基本信息数据数据 */
    initBaseInfo () {
      platform.queryData().then((res) => {
        if (res.data.tradeStatisticModel) {
          this.tradeStatisticModel = res.data.tradeStatisticModel
          this.stageProjectInfoModel = res.data.tradeStatisticModel.stageProjectInfoModel
          this.getPhaseSumChart()
          // this.getPieChart(res.data.tradeStatisticModel.enterpriseClassifyName, res.data.tradeStatisticModel.enterpriseClassifyList)
          this.wordCloudInfo.wordCloudInfoList = res.data.tradeStatisticModel.capitalSourceStatisticsList
          this.wordCloudInfo.capitalSourceNameList = res.data.tradeStatisticModel.capitalSourceNameList
          if (this.wordCloudInfo.wordCloudInfoList.length > 0) {
            this.wordCloudInfo.wordCloudInfoList.map(item => {
              item.value = Number(item.value)
              item.itemStyle = this.createRandomItemStyle()
              return item
            })
          } else {
            this.createInitWordCloudInfo()
          }
          // this.getPieChart()
          // this.getAnnularChart()
        }
      })
    },
    /** 建立字符云默认值 */
    createInitWordCloudInfo () {
      this.wordCloudInfo.wordCloudInfoList = [
        {
          name: '折旧（更改）',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '井巷工程费',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '维简费',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '煤炭安全资金',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '环境治理恢复基金',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '危化品安全资金',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        },
        {
          name: '工程建设',
          value: 0,
          itemStyle: this.createRandomItemStyle()
        }
      ]
    },
    /** 初始代理机构数据 */
    initAgencyInfo () {
      platform.queryAgencyData(this.orderType).then((res) => {
        if (res.data.agencyProjectStatisticsList) {
          this.tenderingTableData = res.data.agencyProjectStatisticsList
        }
      })
    },
    /** 初始化供应商数据 */
    initSupplier () {
      platform.querySupplierData(this.orderType).then((res) => {
        if (res.data.supplierProjectStatisticsList) {
          this.bidTableData = res.data.supplierProjectStatisticsList
        }
      })
    },
    /** 排序 */
    sortChange (column) {
      if (column.prop === 'gainRate') {
        if (column.order === 'ascending') { // 中标率升序
          this.orderType = 4
        } else if (column.order === 'descending') { // 中标率降序
          this.orderType = 3
        }
      } else if (column.prop === 'projectNumber') {
        if (column.order === 'ascending') { // 项目数升序
          this.orderType = 2
        } else if (column.order === 'descending') { // 项目数降序
          this.orderType = 1
        }
      } else {
        this.orderType = 1 // 默认排序
      }
      this.initSupplier() // 重新加载数据
    },
    /** 跳转到对应页面 */
    jumpToPage (row, type) {
      if (type === 'tender') { // 代理机构详情页面
        this.$router.push({path: '/admin/resource/intermange/detailsinter/', query: {objectId: row.enterpriseId, platFlag: 'platFlag'}})
      } else if (type === 'bid') { // 投标人详情页面
        this.$router.push({path: '/admin/supply/supplypool/details', query: {code: row.supplierCode, platFlag: 'platFlag'}})
      }
    },
    /** 各阶段项目数统计 */
    getPhaseSumChart () {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('projectSumChart'))
      // 绘制图表
      myChart.setOption({
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          data: ['公告阶段', '资格预审文件阶段', '资格预审会阶段', '招标文件阶段', '开评标阶段', '候选人公示阶段', '结果公示阶段']
        },
        yAxis: {
          name: '进行中数量'
        },
        series: [
          {
            name: '进行中',
            type: 'bar',
            data: this.stageProjectInfoModel,
            itemStyle: {
              normal: {
                color: '#5393ff'
              }
            }
          }
        ]
      })
    },
    /** 项目类型占比 */
    getAnnularChart () {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('projectType'))
      let option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          x: 'center',
          data: ['工程', '货物', '服务']
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
              {value: this.tradeStatisticModel.projectNumber, name: '工程'},
              {value: this.tradeStatisticModel.cargoNumber, name: '货物'},
              {value: this.tradeStatisticModel.serveNumber, name: '服务'}
            ]
          }
        ]
      }
      // 绘制图表
      myChart.setOption(option)
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
    /** 企业分类 */
    getPieChart () {
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('enterpriseType'))
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
            textPadding: 0,
            autoSize: {
              enable: true,
              minSize: 14
            },
            data: this.wordCloudInfo.wordCloudInfoList
          }
        ]
      }
      // 绘制图表
      myChart.setOption(option)
    }
  }
}
</script>
<style lang="less">
#platform-sum {
  .summary-box {
    margin: 0 20px;
    .plat-sum {
      height: 110px;
      padding: 8px 12px;
      background: #fff;
      text-align: left;
      .plat-tit {
        color: #999;
      }
      .plat-number {
        margin: 10px 0;
        font-size: 22px;
        font-weight: 800;
      }
    }
    .left-img {
      height: 38px;
      background: url('./images/pot.png') no-repeat;
      background-size: cover;
      position: relative;
      bottom: 0;
    }
    .right-img {
      height: 38px;
      background: url('./images/zhut.png')no-repeat;
      background-size: cover;
      position: relative;
      bottom: 0;
    }
  }
  // 柱状图
  .phase-prosum {
    margin: 20px;
    margin-bottom: 0;
    background: #fff;
    .bar-box {
      width: 100%;
      height: 280px;
      margin: 0 auto;
      padding-top: 10px;
    }
  }
  // 项目类型占比和企业分类
  .project-enterprise,
  .tendering-bid {
    margin: 20px;
    .project-type,
    .enterprise-type {
      background: #fff;
    }
    .pie-box {
      width: 100%;
      height: 280px;
      margin: 0 auto;
      padding-top: 10px;
    }
  }
  .project-margin {
    margin-bottom: 100px;
  }
  // 招标代理公司数量统计and投标人数量统计
  .tendering-bid {
    margin: 20px 20px 100px 20px;
    .table-box {
      padding: 10px 5px;
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
}
</style>
