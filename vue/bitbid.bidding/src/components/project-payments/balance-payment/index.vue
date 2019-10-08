<template>
  <div>
    <div id="balancelist" class="content_bigbox">
      <!--面包屑-->
      <el-breadcrumb separator-class="el-icon-arrow-right" class="breadcrumb_box">
        <el-breadcrumb-item :to="{ path: '/index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments' }">项目收支管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/projectpayments/manage',query: {objectIds: this.$route.query.incExpenseIds} }">费用管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{this.$route.query.name}}</el-breadcrumb-item>
      </el-breadcrumb>
      <!--面包屑-->
      <div class="sztjbigbox">
        <div class="tabletitlesbigbox">
          <span class="xuhaobox">序号</span>
          <span class="incometitlebox">标段编号</span>
          <span class="incometitlebox">标段名称</span>
        </div>
        <!--标段开始-->
        <el-collapse accordion v-for="(income,index) in incometableData" :key="index" class="incomebigbox" v-model="activeName" @change="rowExpand">
          <el-collapse-item :name="income.objectId">
            <template slot="title">
              <span class="xuhaobox">{{index + 1}}</span>
              <span class="incomebox">{{income.sectionNumber}}</span>
              <span class="incomebox">{{income.sectionName}}</span>
            </template>
            <div style="padding: 0 1px">
              <el-table
                :data="tableDataSingle"
                border
                show-summary
                style="width: 100%; margin-top: 20px">
                <el-table-column
                  prop="expensesName"
                  width="180" align="center">
                </el-table-column>
                <el-table-column
                  prop="income"
                  label="收入（元）"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="expense"
                  label="支出（元）">
                </el-table-column>
                <el-table-column
                  prop="netIncome"
                  label="净收入（元）">
                </el-table-column>
              </el-table>
            </div>
          </el-collapse-item>
        </el-collapse>
        <!--标段结束-->
        <!--合计开始-->
        <el-collapse class="consistencybig">
          <el-collapse-item title="招标项目合计" class="">
            <el-table
              :data="tableDataTotal"
              border
              show-summary
              header-cell-class-name="header"
              style="width: 100%" class="sztjbox">
              <el-table-column
                prop="expensesName"
                width="180" align="center">
              </el-table-column>
              <el-table-column
                prop="income"
                label="收入（元）"
                width="180">
              </el-table-column>
              <el-table-column
                prop="expense"
                label="支出（元）">
              </el-table-column>
              <el-table-column
                prop="netIncome"
                label="净收入（元）">
              </el-table-column>
            </el-table>
          </el-collapse-item>
        </el-collapse>
        <!--合计结束-->
      </div>
    </div>
  </div>
</template>
<script>
import {addtitle} from '../../../assets/js/common'
export default{
  data () {
    return {
      incometableData: [],
      dataIndex: 1,
      tableDataSingle: [],
      tableDataTotal: [],
      sectionInfoId: '',
      activeName: null
    }
  },
  methods: {
    // 列表
    statisticsList () {
      let url = `section-information?incExpenseId=${this.$route.query.incExpenseIds}&isDelete=${0}&_t=${new Date().getTime()}`
      this.axios.get(url).then((res) => {
        this.incometableData = res.data.sectionInformationPageInfo.list
        this.sectionInfoId = res.data.sectionInformationPageInfo.list[0].objectId
        this.activeName = res.data.sectionInformationPageInfo.list[0].objectId
        addtitle(this)
        this.showOpen()
      })
    },
    // 每个下边的表格数据
    showOpen () {
      this.axios.get(`charge/incomeExpenseCount/${this.sectionInfoId}?_t=${new Date().getTime()}`).then((res) => {
        this.tableDataSingle = res.data.expenseManagements
        this.tableDataSingle.map((item) => {
          item.income = Number(item.income)
          item.expense = Number(item.expense)
          item.netIncome = Number(item.netIncome)
          if (item.income === null) {
            item.income = '0'
          }
          if (item.expense === null) {
            item.expense = '0'
          }
          if (item.netIncome === null) {
            item.netIncome = '0'
          }
        })
      })
    },
    // 表格展开
    rowExpand (row) {
      if (row !== '') {
        this.sectionInfoId = row
        this.showOpen()
      }
    },
    // 总合计
    tableTotal () {
      this.axios.get(`charge/tenderProjectCount/${this.$route.query.incExpenseIds}?_t=${new Date().getTime()}`).then((res) => {
        this.tableDataTotal = res.data.expenseManagements
        this.tableDataTotal.map((item) => {
          item.income = Number(item.income)
          item.expense = Number(item.expense)
          item.netIncome = Number(item.netIncome)
          if (item.income === null) {
            item.income = '0'
          }
          if (item.expense === null) {
            item.expense = '0'
          }
          if (item.netIncome === null) {
            item.netIncome = '0'
          }
        })
      })
    }
  },
  mounted () {
    this.statisticsList()
    this.tableTotal()
  }
}
</script>
<style lang='less'>
  body{
    background:#eaedf1;
  }
  #balancelist{
    .breadcrumb_box{
      width: 100%;
      height: 60px;
      border-bottom: 1px solid #eeeeee;
      padding: 0 20px;
      box-sizing: border-box;
      line-height: 60px;
    }
    .da_xz_ckbigbox {
      padding: 10px 20px;
    }
    .el-breadcrumb__inner{
      color: #999999!important;
    }
    .active_bread>.el-breadcrumb__inner{
      color:#333333!important;
    }
    .el-button--primary {
      color: #fff;
      background-color: #6582f8;
      border-color: #6582f8;
    }
    .header{background: #f5f7f8}
    .da_xz_ckbigbox{
      border: none;
      line-height:60px;
      padding:0 20px;
      box-sizing: border-box;
      .el-form-item{padding: 0px}
      .el-dialog__header{padding-top: 0;padding-bottom: 0px}
      .el-dialog__body{padding-bottom: 0px}
    }
    .content_bigbox{margin: 0px auto;width: 1200px;background: #fff}
    .demo-table-expand {
      font-size: 0;
    }
    .demo-table-expand label {
      width: 90px;
      color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
      margin-right: 0;
      margin-bottom: 0;
      width: 50%;
    }
    .sztjbigbox{
      width: 100%;
      padding: 30px;
      box-sizing: border-box;
    }
    .el-icon-arrow-right:before {
      content: "\E604";
    }
    .sztjbigboxtable{
      padding: 1px;
      box-sizing: border-box;
    }
    .header{
      color:#333;
    }
    .consistencybig{
      margin-top: 15px;
      color: #333333;
    }
    .el-collapse-item__header {
      font-size: 15px;
      font-weight: 700;
      text-align: center;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      height: 23px;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .incomebigbox{
      margin-bottom: 10px;
    }
    .incomebox{
      display: inline-block;
      width: 40%;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
      text-align: left;
      font-weight: normal;
    }
    .tabletitlesbigbox{
      width: 100%;
      height: 49px;
      border: 1px solid #ebeef5;
      border-bottom: none;
    }
    .incometitlebox{
      display: inline-block;
      width: 40%;
      line-height: 49px;
      text-indent: 40px;
    }
    .xuhaobox{
      display: inline-block;
      width: 100px;
      height: 49px;
      line-height: 49px;
      text-align: center;
      vertical-align: top;
      font-weight: normal;
      float: left;
    }
  }
</style>
