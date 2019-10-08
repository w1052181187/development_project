<template>
  <div id="mainresult" class="result">
    <el-row>
      <div class="l_header">
        <div class="wrap">
          <div class="l_header_left">
            <span>您好，欢迎来到 {{$store.getters.authUser.enterpriseName}}</span>
          </div>
          <div class="l_header_right">
            <div class="username">
              <span :title="$store.getters.authUser.userName">{{$store.getters.authUser.userName}}</span>
              <span class="iconjian">
              <img src="../../../../static/images/system-clearbid/jiantou.png" alt="" class="jiantouimg">
            </span>
              <div class="layoutbtn" @click="logout">退出</div>
            </div>
          </div>
        </div>
      </div>
    </el-row>
    <el-row>
      <el-col :span="3" width="300px" class="setheight">
        <el-menu
          class="el-menu-vertical-demo sidebox"
          background-color="#20335d"
          text-color="#cccccc">
          <el-menu-item index="1" class="indexs" @click="leftMenu1">
            <template slot="title">
              <span slot="title" class="titlesbox" :title="'清标结果'">清标结果</span>
            </template>
          </el-menu-item>
          <el-menu-item class="submenu" index="1-1" @click="leftMenu2">符合性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-2" @click="leftMenu3">算术性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-3" @click="leftMenu4">合理性检查</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="21" class="setheight">
        <div class="rightcontent">
          <el-table
            :data="tableData"
            style="width: 100%"
            tooltip-effect
            header-cell-class-name="tabletitles">
            <el-table-column
              type="index"
              label="序号"
              width="50"
              align="center">
            </el-table-column>
            <el-table-column
              prop="bidder"
              label="投标单位"
              width="150"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column label="符合性检查" align="center">
              <el-table-column
                prop="inventoryCheck"
                label="清单检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.inventoryCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.inventoryCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.inventoryCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="measureProCheck"
                label="措施项目检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.measureProCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.measureProCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.measureProCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="otherProTaxCheck"
                label="其他项目、税金检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="算术性检查" align="center">
              <el-table-column
                prop="priceOmit"
                label="单价遗漏"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.priceOmit) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.priceOmit) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.priceOmit) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="inventoryPriceMath"
                label="清单单价算术性"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.inventoryPriceMath) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.inventoryPriceMath) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.inventoryPriceMath) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="totalMeasureMath"
                label="总价措施项目算术性"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.totalMeasureMath) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.totalMeasureMath) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.totalMeasureMath) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="otherProTaxMath"
                label="其他项目、税金算术性"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxMath) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxMath) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.otherProTaxMath) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="priceMath"
                label="合价算术性"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.priceMath) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.priceMath) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.priceMath) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
            </el-table-column>
            <el-table-column label="合理性检查" align="center">
              <el-table-column
                prop="tenderCheck"
                label="标书信息检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.tenderCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.tenderCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.tenderCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="unbalancePriceCheck"
                label="不平衡报价检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.unbalancePriceCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.unbalancePriceCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.unbalancePriceCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="sameCheck"
                label="雷同性检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.sameCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.sameCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.sameCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="cotrolCheck"
                label="招标控制价检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.cotrolCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.cotrolCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.cotrolCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
              <el-table-column
                prop="lowcostCheck"
                label="低于成本检查"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <span class="imgbox" v-if="Number(scope.row.lowcostCheck) === 0"><img src="../../../../static/images/system-clearbid/correct.png"/> </span>
                  <span class="imgbox" v-if="Number(scope.row.lowcostCheck) > 0"><img src="../../../../static/images/system-clearbid/error.png"/></span>
                  <span class="imgbox" v-if="Number(scope.row.lowcostCheck) === -1"><img src="../../../../static/images/system-clearbid/nocheck.png"/></span>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table>
          <div class="result_button">
            <el-button type="primary" @click="importResult">导出结果报告</el-button>
            <el-button type="primary" class="returnBtn" @click="returnIndex">返回首页</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import $ from 'jquery'
import { billClearData } from '@/api'
export default {
  data () {
    return {
      tableData: []
    }
  },
  methods: {
    // 退出当前账号（头部右上角）
    logout () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push({path: '/login'})
      })
    },
    setMain () {
      // 获得屏幕的高度
      let screenH = window.innerHeight
      // 获得主要内容区
      let topUl = document.getElementsByClassName('setheight')
      let rightH = document.getElementsByClassName('rightcontent')

      // 设置主要内容区的高度
      topUl[0].style.height = (screenH - 40) + 'px'
      rightH[0].style.height = (screenH - 102) + 'px'
    },
    // 左侧菜单
    leftMenu1 () {
      this.$router.push({path: `/system-bidclearing/result`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu2 () {
      this.$router.push({path: `/system-bidclearing/result/complianceCheck`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu3 () {
      this.$router.push({path: `/system-bidclearing/result/arithmeticCheck`, query: {objectId: this.$route.query.objectId}})
    },
    leftMenu4 () {
      this.$router.push({path: `/system-bidclearing/result/reasonablenessCheck`, query: {objectId: this.$route.query.objectId}})
    },
    // 清标结果汇总
    _getAllData () {
      billClearData.totalResult(this.$route.query.objectId).then((res) => {
        let temArr = res.data.collections
        this.tableData = temArr.map(item => {
          return {
            bidder: item[0],
            inventoryCheck: item[1],
            measureProCheck: item[2],
            otherProTaxCheck: item[3],
            priceOmit: item[4],
            inventoryPriceMath: item[5],
            totalMeasureMath: item[6],
            otherProTaxMath: item[7],
            priceMath: item[8],
            tenderCheck: item[9],
            unbalancePriceCheck: item[10],
            sameCheck: item[11],
            cotrolCheck: item[12],
            lowcostCheck: item[13]
          }
        })
      })
    },
    returnIndex () {
      this.$router.push({path: `/system-bidclearing`})
    },
    importResult () {
      // billClearData.importResult(this.$route.query.objectId).then((res) => {
      //   console.log(res)
      // })
      let comurl = process.env.COMPONENTS_BASE_API
      let url = comurl + 'comptend/project/download/cleaning/' + this.$route.query.objectId
      window.open(url)
    }
  },
  watch: {},
  mounted () {
    this.setMain()
    this._getAllData()
  }
}
</script>
<style lang="less">
  .el-tooltip__popper{
    background: #000000!important;
    color: #ffffff!important;
  }
  #mainresult{
    background: #e7e8eb;
    .wrap{
      width: 98%;
    }
    img{
      border: 0px;
    }
    .el-header{
      padding: 0px;
    }
    .l_header{
      background: #3f63f6;
      color: #ffffff;
    }
    .iconjian{
      width: 12px;
    }
    .username:hover{
      background:#3f63f6;
      border: 1px solid #b5b5b5;
      box-sizing: border-box;
      border-bottom: none;
      border-top: none;
    }
    .layoutbtn{
      background: #3f63f6;
      color: #ffffff;
    }
    .el-col-3{
      background: #20335d;
    }
    .el-submenu__title{
      text-align: left;
      height: 50px;
      line-height: 50px;
    }
    .iconimg{
      display: inline-block;
      width: 15px;
      height: 17%;
      /*background: red;*/
      position: relative;
      vertical-align: middle;
    }
    .iconimg>img{
      width: 90%;
      position: absolute;
      left: 0;
      right: 0;
      top:0;
      bottom: 0;
      margin: auto;
    }
    .titlesbox{
      display: inline-block;
      width: 100%;
      padding: 0 5px;
      box-sizing: border-box;
      vertical-align: middle;
      line-height: 40px;
      background-image: url(../../../../static/images/system-clearbid/leftjiantou.png);
      background-position: right center;
      background-repeat: no-repeat;
    }
    .el-aside{
      background:#19233c;
    }
    .indexs{
      background: rgb(25, 35, 60) url(../../../../static/images/system-clearbid/leftico.png) 20px center no-repeat!important;
      padding-left: 40px!important;
    }
    .submenu{
      padding-left: 60px!important;
    }
    .el-menu-item.is-active{
      background: rgb(25, 35, 60)!important;
    }
    .rightcontent{
      margin: 20px 20px 0 20px;
      padding: 20px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
      overflow: auto;
    }
    .result_button{
      margin: 20px auto;
      text-align: center;
    }
    .imgbox{
      text-align: center;
      width: 100%;
      display: inline-block;
    }
    .el-table th .cell{
      height: auto;
      overflow: hidden;
      text-overflow: inherit;
      white-space: normal;
    }
    .el-button--primary{
      background-color: #3f63f6;
      border-color: #3f63f6;
    }
    .returnBtn{
      background-color: #ffffff;
      border: 1px solid #dddddd;
      color: #3f63f6;
    }
    .returnBtn span{
      color: #3f63f6!important;
    }
    .el-table td, .el-table th{
      padding: 12px 0!important;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
  }
</style>
