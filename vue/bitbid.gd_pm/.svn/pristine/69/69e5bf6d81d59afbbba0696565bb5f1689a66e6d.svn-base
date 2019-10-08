<template>
  <div class="center_box" id="settlement-detail">
    <el-form :model="projectForm" ref="projectForm" label-width="120px" class="demo-ruleForm">
      <div class="coninfor_box">
        <div class="coninfor_title">详情</div>
        <!--结算日期-->
        <div class="coninfor_con">
          <el-row>
            <el-col :span="24">
              <el-form-item label="结算日期：">
                <span>{{projectForm.submitStartDate}} - {{projectForm.submitEndDate}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--结算日期-->
        <!--结算单-->
        <div class="coninfor_con">
          <!--标题-->
          <div class="statements-title-box">
            <span>结算单</span>
            <span class="detail-btn" @click="particularsBtn">明细</span>
          </div>
          <!--标题-->
          <!--明细弹框-->
          <el-dialog title="明细" :visible.sync="detailVisible" width="60%">
            <el-table :data="detailData" height="380" border header-cell-class-name="table_header" show-summary :summary-method="detailSummaries">
              <el-table-column
                prop="payUser.namezh"
                label="缴费单位"
                align="left"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="chargeitem"
                label="费用类型"
                align="left"
                :formatter="catypeFormatter"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="paymentchannel"
                label="缴费方式"
                align="left"
                :formatter="paymentChannelFormatter"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="createdate"
                label="缴费时间"
                align="left"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="price"
                label="缴费金额（元）"
                align="left"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                label="操作" width="80" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" class="handle_btn" @click="detailBtn(scope)">
                    详情
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <div class="detail-dialog-box">
              <el-button class="detail-dialog-btn" @click="closeBtn">关闭</el-button>
            </div>
          </el-dialog>
          <!--明细弹框-->
          <!--结算单位和时间-->
          <div class="statements-note-box">
            <div class="statements-note-left">结算单位：{{cooperationAgency}}</div>
            <div class="statements-note-right">提交时间：{{projectForm.createtime}}</div>
          </div>
          <!--结算单位和时间-->
          <!--结算列表数据-->
          <div class="statements-table">
            <el-table
              :data="statementsData"
              border
              :summary-method="getSummaries"
              show-summary
              header-cell-class-name="table_header">
              <el-table-column
                type="index"
                label="序号"
                width="50"
                align="center"
                :index="indexMethod">
              </el-table-column>
              <el-table-column
                prop="chargeItem"
                label="费用项"
                align="left"
                :formatter="catypeChargeItem"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="price"
                label="费用合计（元）"
                align="left"
                show-overflow-tooltip>
              </el-table-column>
              <el-table-column
                prop="remark"
                label="备注"
                align="left"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input
                    type="textarea"
                    readonly
                    v-model="scope.row.remark"
                    resize="none" class="textarea-box">
                  </el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!--结算列表数据-->
          <!--确认人和审核人-->
          <div class="reviewer-box">
            <div class="reviewer-son-box">制单人：{{projectForm.createrUser}}</div>
            <div class="reviewer-son-box">确认人：{{projectForm.affirmUser}}</div>
            <div class="reviewer-son-box">审核人：{{projectForm.checkUser}}</div>
          </div>
          <!--确认人和审核人-->
        </div>
        <!--结算单-->
        <!--审核-->
        <div class="coninfor_con" v-if="projectForm.remark">
          <el-row>
            <el-col :span="12">
              <el-form-item label="备注：">
                <span>{{projectForm.remark}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--审核-->
        <!--确认记录-->
        <div class="coninfor_con">
          <div class="record-title-box">确认记录</div>
          <el-table
            :data="recordData"
            border
            header-cell-class-name="table_header">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="left"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="approvestatus"
              label="确认结果"
              align="left"
              width="240"
              :formatter="statusFormatter"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="createrDlName"
              label="操作人"
              align="left"
              width="240"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="createtime"
              label="操作时间"
              align="left"
              width="240"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approveopinion"
              label="确认意见"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--确认记录-->
        <!--审核记录-->
        <div class="coninfor_con">
          <div class="record-title-box">审核记录</div>
          <el-table
            :data="auditData"
            border
            header-cell-class-name="table_header">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="left"
              :index="indexMethod">
            </el-table-column>
            <el-table-column
              prop="approvestatus"
              label="审核结果"
              align="left"
              width="240"
              :formatter="statusFormatter"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="createrDlName"
              label="操作人"
              align="left"
              width="240"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="createtime"
              label="操作时间"
              align="left"
              width="240"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="approveopinion"
              label="审核意见"
              align="left"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
        <!--审核记录-->
        <el-form-item class="operation_btn_box">
          <el-button @click="$router.go(-1)" class="cancel_btn">取消</el-button>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import {settlement, agencyEnterprise} from '@/api'
export default {
  name: '',
  data () {
    return {
      projectForm: {},
      priceUp: '', // 大写
      cooperationAgency: '', // 合作代理
      indexMethod: 1, // 序号
      statementsData: [],
      recordData: [], // 确认记录
      auditData: [], // 审核记录
      projectData: [], // 合作代理
      detailVisible: false, // 明细弹框
      detailData: [] // 明细数据
    }
  },
  methods: {
    // 结算合计方法
    getSummaries (param) {
      const { columns, data } = param
      const sums = []
      let sumPrice = 0
      data.map((item) => {
        sumPrice += Number(item.price)
      })
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        if (index === 1) {
          sums[index] = '大写：' + this.priceUp
          return
        }
        if (index === 2) {
          sums[index] = sumPrice.toFixed(2)
        } else {
          sums[index] = ''
        }
      })
      return sums
    },
    // 明细合计方法
    detailSummaries (param) {
      const { columns, data } = param
      const sums = []
      let sumPrice = 0
      data.map((item) => {
        sumPrice += Number(item.price)
      })
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计'
          return
        }
        if (index === 4) {
          sums[index] = sumPrice.toFixed(2)
        } else {
          sums[index] = ''
        }
      })
      return sums
    },
    // 费用类型（明细单）
    catypeFormatter (row, column) {
      switch (row.chargeitem) {
        case 0 :
          return 'CA费用'
        case 1 :
          return '标书费'
        case 2 :
          return '标书费'
        case 3 :
          return '投标保证金'
        case 4 :
          return '平台使用费'
        case 5 :
          return '中标服务费'
      }
    },
    // 费用类型(结算单)
    catypeChargeItem (row, column) {
      switch (row.chargeItem) {
        case 0 :
          return 'CA费用'
        case 1 :
          return '标书费'
        case 2 :
          return '标书费'
        case 3 :
          return '投标保证金'
        case 4 :
          return '平台使用费'
        case 5 :
          return '中标服务费'
      }
    },
    // 缴费方式
    paymentChannelFormatter (row, column) {
      if (row) {
        switch (row.paymentchannel) {
          case 1 :
            return '交行企业支付'
          case 2 :
            return '交行个人支付'
          case 3 :
            return '其他'
          case 4 :
            return '支付宝'
        }
      } else {
        return '------'
      }
    },
    // 确认结果
    statusFormatter (row, column) {
      switch (row.approvestatus) {
        case 0 :
          return '驳回'
        case 1 :
          return '确认通过'
      }
    },
    // ***************************************************************接口方法************************************************************
    // 查询所有的合作代理机构
    queryAgency () {
      let url = {}
      url = {
        isvalid: 1
      }
      agencyEnterprise.queryNotPageList(url).then((res) => {
        res.data.agencyEnterprises.forEach((item) => {
          let agencyEnterprise = {}
          agencyEnterprise.value = item.id
          agencyEnterprise.label = item.enterpriseName
          this.projectData.push(agencyEnterprise)
          // 合作代理机构
          let unitAccountObj = this.projectData.find((item) => item.value === this.projectForm.agencyenterpriseid)
          if (unitAccountObj) {
            this.cooperationAgency = unitAccountObj.label
          }
        })
      })
    },
    // 当前信息
    detailInfo () {
      settlement.queryDeail(this.$route.params.id).then((res) => {
        this.projectForm = res.data.settlementSummary
        this.statementsData = res.data.settlementSummary.settlSumPriceList // 结算单
        this.recordData = res.data.settlementSummary.approveRecordsClassify // 确认记录
        this.auditData = res.data.settlementSummary.approveRecords // 审核记录
        if (res.data.settlementSummary.priceUp) {
          this.priceUp = res.data.settlementSummary.priceUp
        }
        // 合作代理机构
        this.queryAgency()
      })
    },
    // 明细
    particularsBtn () {
      this.detailVisible = true
      settlement.detailList(this.$route.params.id).then((res) => {
        this.detailData = res.data.zbOrderFlowPageInfo.list
        this.detailData.map((ite) => {
          ite.price = Number(ite.price).toFixed(2)
        })
      })
    },
    // 弹框关闭
    closeBtn () {
      this.detailVisible = false
    },
    // 弹框详情
    detailBtn (scope) {
      let url = null
      if (scope.row.chargeitem === 0) {
        url = this.$router.resolve({path: `/admin/record/ca/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      } else if (scope.row.chargeitem === 1) {
        url = this.$router.resolve({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      } else if (scope.row.chargeitem === 2) {
        url = this.$router.resolve({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      } else if (scope.row.chargeitem === 3) {
        url = this.$router.resolve({path: `/admin/record/tender-fee/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      } else if (scope.row.chargeitem === 4) {
        url = this.$router.resolve({path: `/admin/record/platform/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      } else if (scope.row.chargeitem === 5) {
        url = this.$router.resolve({path: `/admin/record/winning/detail/${scope.row.id}`})
        window.open(url.href, '_blank')
      }
    }
  },
  mounted () {
    this.detailInfo()
  }
}
</script>

<style lang="less">
  #settlement-detail{
    .coninfor_con{
      margin-bottom: 10px;
      padding: 10px 20px;
    }
    .statements-title-box{
      font-size: 18px;
      height: 25px;
      line-height: 25px;
      color: #333;
      font-weight: bold;
      text-align: center;
      padding-top: 36px;
      width: 70%;
      margin: 0 auto;
    }
    .detail-btn{
      float: right;
      color: #0096ff;
      font-size: 14px;
      font-weight: normal;
      cursor: pointer;
    }
    .statements-note-box{
      width: 70%;
      height: 20px;
      line-height: 20px;
      color: #999;
      font-size: 14px;
      margin: 0 auto;
      margin-top: 40px;
    }
    .statements-note-left{
      text-align: left;
      width: 50%;
      float: left;
      height: 100%;
    }
    .statements-note-right{
      text-align: right;
      width: 50%;
      float: left;
      height: 100%;
    }
    .statements-table{
      width: 70%;
      margin: 0 auto;
      margin-top: 10px;
    }
    .textarea-box textarea{
      border: none;
      background: transparent;
      padding: 10px 5px;
      line-height: 1.6em;
    }
    .el-table td, .el-table th {
      padding: 5px 0;
    }
    .reviewer-box{
      width: 70%;
      height: 20px;
      line-height: 20px;
      margin: 0 auto;
      padding-bottom: 30px;
    }
    .reviewer-son-box{
      width: 33.33%;
      height: 100%;
      float: left;
      font-size: 14px;
      color: #333;
      text-align: left;
      margin-top: 15px;
    }
    .combined-box{
      width: 100%;
      height: 40px;
      border: 1px solid #ebeef5;
      border-top: none;
      box-sizing: border-box;
    }
    .combined-title{
      width: 5.5%;
      height: 100%;
      float: left;
      line-height: 40px;
      font-size: 12px;
      text-align: center;
      border-right:1px solid #ebeef5;
      box-sizing: border-box;
    }
    .combined-price{
      width: 31.5%;
      height: 100%;
      float: left;
      line-height: 40px;
      font-size: 12px;
      text-align: left;
      border-right:1px solid #ebeef5;
      padding: 0 10px;
      box-sizing: border-box;
    }
    .combined-price:last-child{
      border-right: none;
    }
    .el-dialog__header{
      text-align: center;
    }
    .el-dialog__headerbtn{
      display: none;
    }
    .detail-dialog-box{
      text-align: center;
      margin-top: 15px;
    }
    .detail-dialog-btn{
      background: #fff;
      border: 1px solid #dcdfe6;
      color: #606266;
    }
    .detail-dialog-btn:focus, .detail-dialog-btn:hover {
      background: #fff;
      border: 1px solid #dcdfe6;
      color: #606266;
    }
    .record-title-box{
      font-size: 15px;
      color: #333;
      padding-bottom: 20px;
    }
  }
</style>
