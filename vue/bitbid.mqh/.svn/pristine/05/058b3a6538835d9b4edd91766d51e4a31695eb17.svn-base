<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="fundsdetails-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>计划管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancecheck' }">资金平衡表审核</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/planmanage/balancecheck/details' }">详情
        </el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="$router.go(-1)" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="maincont-box">
      <div class="warp-balance">
        <h2>{{this.chooseYear}}年资金平衡表</h2>
        <div>
          <span class="balance-title right-title">专项资金风险控制比例：{{this.riskRatio}}%</span>
        </div>
        <el-table
          v-loading="loading"
          :data="specialFundsTableData"
          border
          :summary-method="getSummaries"
          show-summary
          style="width: 100%">
          <el-table-column
            prop="fundsFrom"
            label="资金来源"
            align="center">
          </el-table-column>
          <el-table-column
            prop="inputData"
            :label="(this.chooseYear - 1) + '年完成情况（万元）'"
            align="center">
          </el-table-column>
          <el-table-column
            :label="this.chooseYear + '年资金平衡表'"
            align="center">
            <el-table-column
              prop="surplusFunds"
              :label= "'累计结余及' + (this.chooseYear - 1) + '年提取数（万元）'"
              align="center">
            </el-table-column>
            <el-table-column
              prop="riskFunds"
              :label="'减' + this.riskRatio + '%风险资金（万元）'"
              align="center">
            </el-table-column>
            <el-table-column
              prop="availableFunds"
              :label="this.chooseYear + '年建议计划可用资金（万元）'"
              align="center">
            </el-table-column>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!--审核记录start-->
    <div class="record-box udit-record">
      <div class="record-title">
        <span>审核历史</span>
      </div>
      <div class="record-cont">
        <div class="planapp-shenpi">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-steps direction="vertical" :active="1" :space="100">
                <el-step
                  v-for="(item,index) in balanceSheetRecordList"
                  :key="index">
                  <!--:status='fliteredStatus(item.approveResult)'-->
                  <span slot="title" class="step-span span-color span-first">{{item.creatorName}}</span>
                  <span slot="title" class="step-span span-color span-second">{{item.enterpriseName}}</span>
                  <span slot="title" class="step-span span-third">{{item.recordType | filterRecordType}}</span>
                  <span slot="title" class="step-span span-third">
                    <el-button type="text" @click="querySingleRecord(item)">查询</el-button>
                  </span>
                  <span slot="title" class="step-span span-color span-last">{{item.createDate | filterDate}}</span>
                </el-step>
              </el-steps>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
    <!--审核记录end-->
    <!--弹窗区域start-->
    <div class="dialog-box">
      <el-dialog
        title="查看"
        :visible.sync="dialogVisible"
        width="55%"
        center>
        <div class="warp-balance">
          <h2 style="text-align: center;">{{this.chooseYear}}年资金平衡表</h2>
          <div>
            <span class="balance-title right-title">专项资金风险控制比例：{{this.riskRatio}}%</span>
          </div>
          <el-table
            :data="dialogFundsTableData"
            border
            :summary-method="getSummaries"
            show-summary
            style="width: 100%">
            <el-table-column
              prop="fundsFrom"
              label="资金来源"
              align="center">
            </el-table-column>
            <el-table-column
              prop="inputData"
              :label="(this.chooseYear - 1) + '年完成情况（万元）'"
              align="center">
            </el-table-column>
            <el-table-column
              :label="this.chooseYear + '年资金平衡表'"
              align="center">
              <el-table-column
                prop="surplusFunds"
                :label= "'累计结余及' + (this.chooseYear - 1) + '年提取数（万元）'"
                align="center">
              </el-table-column>
              <el-table-column
                prop="riskFunds"
                :label="'减' + this.riskRatio + '%风险资金（万元）'"
                align="center">
              </el-table-column>
              <el-table-column
                prop="availableFunds"
                :label="this.chooseYear + '年建议计划可用资金（万元）'"
                align="center">
              </el-table-column>
            </el-table-column>
          </el-table>
        </div>
      </el-dialog>
    </div>
    <!--弹窗区域end-->
  </div>
</template>
<script>
// 实例
import {balanceSheet, balanceSheetRecord, investmanage} from 'api/index'
import {formatDate} from 'common/js/date.js'
import {capitalSourceShortNameList} from 'common/js/balanceSheet'
export default {
  data () {
    return {
      specialFundsTableData: [], // 专项资金
      balanceSheetRecordList: [], // 审批记录
      dialogVisible: false,
      dialogFundsTableData: [],
      currentEnterpriseClassify: this.$route.query.enterpriseClassify, // 当前用户所在企业的企业类型
      projectEnterpriseId: this.$route.query.projectEnterpriseId,
      currentBalanceSheetCode: this.$route.query.code,
      chooseYear: this.$route.query.year,
      riskRatio: this.$route.query.riskRatio,
      checkDialogFlag: false, // 测试是否打开审批记录的弹框
      loading: true
    }
  },
  created () {
    // 获取列表数据
    // this.getEnterpriseClassify(this.projectEnterpriseId, this.currentBalanceSheetCode, this.specialFundsTableData, true)
    this.createTableFundsType(this.currentEnterpriseClassify, this.currentBalanceSheetCode, this.specialFundsTableData, true)
  },
  filters: {
    filterRecordType (recordType) {
      if (recordType === 1) {
        return '提交'
      } else {
        return '修改维护'
      }
    },
    // 格式化申请时间
    filterDate (createDate) {
      if (createDate) {
        let date = new Date(createDate)
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      } else {
        return null
      }
    }
  },
  methods: {
    getBalanceSheetRecordList (firstBalanceSheetCode) {
      balanceSheetRecord.getList({
        firstBalanceSheetCode: firstBalanceSheetCode
      }).then((res) => {
        this.balanceSheetRecordList = res.data.balanceSheetRecordList
      })
    },
    /** 获取当前列表上的数据 */
    getCurrentData (balanceSheetCode, form, flag) {
      if (flag) {
        balanceSheet.getOneByCode(balanceSheetCode).then((res) => {
          this.getFormInfo(res.data.fundsInfo, form)
          // 获取审批历史
          this.getBalanceSheetRecordList(res.data.fundsInfo.balanceSheet.firstBalanceCode)
        })
      } else {
        balanceSheet.getOneByCode(balanceSheetCode).then((res) => {
          this.getFormInfo(res.data.fundsInfo, form)
        })
      }
    },
    /** 获取每行的数据 (资金来源对象,上一年资金平衡表信息，当前年的资金平衡表信息) */
    getRowInfo (item, previousYearBalanceInfo, specialFundsList, constructionFundsList) {
      if (item.capitalSourceShortName === capitalSourceShortNameList[0]) {
        item.inputData = previousYearBalanceInfo.undistributedProfit
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[1]) {
        item.inputData = previousYearBalanceInfo.depreciationFee
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[2]) {
        item.inputData = previousYearBalanceInfo.wellRoadwayEngineering
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[3]) {
        item.inputData = previousYearBalanceInfo.dimensionalityFee
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[4]) {
        item.inputData = previousYearBalanceInfo.coalSafetyFund
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[5]) {
        item.inputData = previousYearBalanceInfo.environmentalControlDeposit
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[6]) {
        item.inputData = previousYearBalanceInfo.chemicalsSafetyFund
        let rowInfo = this.getInfo(specialFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[7]) {
        item.inputData = previousYearBalanceInfo.shareholdersInjection
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[8]) {
        item.inputData = previousYearBalanceInfo.governmentGrants
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      } else if (item.capitalSourceShortName === capitalSourceShortNameList[9]) {
        item.inputData = previousYearBalanceInfo.projectLoan
        let rowInfo = this.getInfo(constructionFundsList, item.capitalSourceId)
        item.surplusFunds = rowInfo.surplusFunds
        item.riskFunds = rowInfo.riskFunds
        item.availableFunds = rowInfo.availableFunds
      }
      return item
    },
    /** 获取页面上的数据 */
    getFormInfo (fundsInfo, form) {
      // 上一年资金平衡表信息，当前年的资金平衡表信息
      let previousYearBalanceInfo = {}
      fundsInfo.balanceSheet.previousYearBalanceInfoList.forEach(item => {
        if (item.sourceType === 1) {
          previousYearBalanceInfo = item
        }
      })
      // 专项资金
      let specialFundsList = fundsInfo.specialFundsList
      // 工程建设资金
      let constructionFundsList = fundsInfo.constructionFundsList
      let index = 0
      form.forEach(item => {
        index++
        item = this.getRowInfo(item, previousYearBalanceInfo, specialFundsList, constructionFundsList)
        this.$set(form, form[index], item)
      })
      if (this.checkDialogFlag) {
        this.dialogVisible = true
      }
    },
    getInfo (arr, capitalSourceId) {
      let rowInfo = {}
      for (let i = 0; i < arr.length; i++) {
        if (Number(arr[i].capitalSourceId) === Number(capitalSourceId)) {
          rowInfo = arr[i]
          break
        }
      }
      return rowInfo
    },
    /** 构建当前用户的资金来源数据 */
    createTableFundsType (enterpriseClassify, balanceSheetcode, form, flag) {
      if (enterpriseClassify === null || enterpriseClassify === '') {
        investmanage.getCapitalSourceList({}).then((res) => {
          res.data.capitalSourceList.forEach(item => {
            let obj = {
              fundsFrom: item.name,
              capitalSourceShortName: item.shortName,
              capitalSourceId: item.objectId
            }
            form.push(obj)
          })
          this.getCurrentData(balanceSheetcode, form, flag)
        })
      } else {
        investmanage.selectCapitalSourceByEnterpriseType(enterpriseClassify).then((res) => {
          res.data.enterpriseTypeCapitalSourceList.forEach(item => {
            let obj = {
              fundsFrom: item.capitalSourceName,
              capitalSourceShortName: item.capitalSourceShortName,
              capitalSourceId: item.capitalSourceId
            }
            form.push(obj)
          })
          this.getCurrentData(balanceSheetcode, form, flag)
        })
      }
      this.loading = false
    },
    /** 表格合计 */
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总计'
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          sums[index] = sums[index].toFixed(2)
          // sums[index] += ' 元'
        } else {
          sums[index] = 'N/A'
        }
      })
      return sums
    },
    /** 查询 */
    querySingleRecord (item) {
      this.checkDialogFlag = true
      // 打开弹窗
      this.dialogFundsTableData = []
      // this.getEnterpriseClassify(item.enterpriseId, item.relatedBalanceSheetCode, this.dialogFundsTableData, false)
      this.createTableFundsType(this.currentEnterpriseClassify, item.relatedBalanceSheetCode, this.dialogFundsTableData, false)
    },
    fliteredStatus (status) {
      if (status === 1) {
        return 'success'
      } else if (status === 2) {
        return 'error'
      } else if (status === 3) {
        return 'wait'
      }
    }
  },
  computed: {},
  // 实例创建完成
  mounted () {
  }
}
</script>
<style lang="less">
  #fundsdetails-page {
    .maincont-box {
      margin: 20px;
    }
    /*资金平衡表*/
    .warp-balance, .warp-engineer {
      .balance-title {
        display: inline-block;
        padding-bottom: 10px;
        font-weight: 800;
      }
      .left-title {
        float: left;
      }
      .right-title {
        float: right;
      }
    }
    .warp-engineer {
      padding-top: 30px;
    }
    .handle-btn {
      padding-top: 40px;
    }
    /** 审批历史 */
    .record-box {
      background-color: #fff;
      margin: 20px 20px 120px;
      .record-title {
        height: 30px;
        line-height: 30px;
        background: #eee;
        text-align: left;
        padding-left: 10px;
        font-weight: 800;
      }
      .record-cont {
        height: 400px;
        overflow-y: auto;
      }
    }
    /*审批记录css*/
    .planapp-shenpi {
      margin: 20px 20px 100px;
      padding: 20px;
      background: #fff;
      .step-span {
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
        width: 160px;
        overflow: hidden;
        display: inline;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
      .span-last {
        float: right;
      }
      .apply-option {
        span {
          font-size: 14px;
          color: #999;
        }
      }
      .span-third {
        width: 180px;
      }
      .span-color {
        color: #999;
      }
    }
  }
</style>
