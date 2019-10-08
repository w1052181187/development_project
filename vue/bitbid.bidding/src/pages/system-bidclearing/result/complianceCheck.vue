<template>
  <div id="result" class="result">
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
          <el-menu-item class="submenu active" index="1-1" @click="leftMenu2">符合性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-2" @click="leftMenu3">算术性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-3" @click="leftMenu4">合理性检查</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="21" class="setheight">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="清单检查" name="first">
            <div class="toplist">
              <el-table
                :data="inventory"
                ref="inventory"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题数量"
                  width="180"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 2)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查投标文件中分部分项清单以及单价措施清单项目是否与招标文件一致，有无增项、漏项和错项的情况。</p>
              <el-table
                :data="inventoryResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <template slot="empty">
                  <span>暂无不合格项</span>
                </template>
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="singleProject"
                  label="单项工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unitProject"
                  label="单位工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="location"
                  label="位置"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="code"
                  label="清单编码"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="清单名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unit"
                  label="清单单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="workAmount"
                  label="清单工程量"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题描述"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="措施项目检查" name="second">
            <div class="toplist">
              <el-table
                :data="measurePro"
                ref="measurePro"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题数量"
                  width="180"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 3)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查投标文件中总价措施项目是否与招标文件一致，有无增项、漏项和错项的情况。</p>
              <el-table
                :data="measureProResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <template slot="empty">
                  <span>暂无不合格项</span>
                </template>
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="singleProject"
                  label="单项工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unitProject"
                  label="单位工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="location"
                  label="位置"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="code"
                  label="清单编码"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="项目名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.rate"
                  label="费率"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题描述"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="其他项目、税金检查" name="third">
            <div class="toplist">
              <el-table
                :data="otherProTax"
                ref="otherProTax"
                highlight-current-row
                height="165"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="投标单位"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="totalPrice"
                  label="投标报价（元）"
                  width="300"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题数量"
                  width="180"
                  align="center"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  label="操作" width="100" align="center">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 8)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查投标文件中其他项目列项、税金税率是否与招标文件一致，有无增项、漏项和错项的情况。</p>
              <el-table
                :data="otherProTaxResult"
                style="width: 100%"
                border
                header-cell-class-name="tabletitles">
                <template slot="empty">
                  <span>暂无不合格项</span>
                </template>
                <el-table-column
                  type="index"
                  label="序号"
                  width="50"
                  align="center">
                </el-table-column>
                <el-table-column
                  prop="singleProject"
                  label="单项工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="unitProject"
                  label="单位工程"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="location"
                  label="位置"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="question"
                  label="问题描述"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { billClearData } from '@/api'
export default {
  data () {
    return {
      inventory: [],
      inventoryResult: [],
      measurePro: [],
      measureProResult: [],
      otherProTax: [],
      otherProTaxResult: [],
      activeName: 'first'
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
      let rightH = document.getElementsByClassName('bottomresult')

      // 设置主要内容区的高度
      topUl[0].style.height = (screenH - 40) + 'px'
      rightH[0].style.height = (screenH - 380) + 'px'
      rightH[1].style.height = (screenH - 380) + 'px'
      rightH[2].style.height = (screenH - 380) + 'px'
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
    handleClick (tab) {
      switch (Number(tab.index)) {
        case 0:
          this._getCheckData(2)
          break
        case 1:
          this._getCheckData(3)
          break
        case 2:
          this._getCheckData(8)
          break
      }
    },
    // 检查投标单位列表
    _getCheckData (index) {
      billClearData.unitList(this.$route.query.objectId, index).then((res) => {
        switch (Number(index)) {
          case 2:
            this.inventory = res.data.enters
            this._getDefaultResult(this.inventory[0].objectId, 2)
            break
          case 3:
            this.measurePro = res.data.enters
            this._getDefaultResult(this.measurePro[0].objectId, 3)
            break
          case 8:
            this.otherProTax = res.data.enters
            this._getDefaultResult(this.otherProTax[0].objectId, 8)
            break
        }
      })
    },
    // 检查投标单位列表
    _getCheckResult (scope, index) {
      billClearData.checkResult(this.$route.query.objectId, index, scope.row.objectId).then((res) => {
        switch (Number(index)) {
          case 2:
            this.inventoryResult = []
            this.inventoryResult = res.data.models
            break
          case 3:
            this.measureProResult = []
            this.measureProResult = res.data.models
            break
          case 8:
            this.otherProTaxResult = []
            this.otherProTaxResult = res.data.models
            break
        }
      })
    },
    // 取投标单位列表 默认数据
    _getDefaultResult (id, index) {
      billClearData.checkResult(this.$route.query.objectId, index, id).then((res) => {
        switch (Number(index)) {
          case 2:
            this.$refs.inventory.setCurrentRow(this.$refs.inventory.data[0])
            this.inventoryResult = []
            this.inventoryResult = res.data.models
            break
          case 3:
            this.$refs.measurePro.setCurrentRow(this.$refs.measurePro.data[0])
            this.measureProResult = []
            this.measureProResult = res.data.models
            break
          case 8:
            this.$refs.otherProTax.setCurrentRow(this.$refs.otherProTax.data[0])
            this.otherProTaxResult = []
            this.otherProTaxResult = res.data.models
            break
        }
      })
    }
  },
  mounted () {
    this.setMain()
    this._getCheckData(2)
  }
}
</script>
<style lang="less">
  #result{
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
    }
    .result_button{
      margin: 20px auto;
      text-align: center;
    }
    .active{
      background-color: rgb(26, 41, 74)!important;
    }
    .el-tabs{
      margin: 10px 20px;
    }
    .el-tabs__header{
      margin: 0px;
      padding-left: 20px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
    }
    .el-tabs__item{
      padding: 0 40px;
    }
    // 顶部表格
    .toplist{
      padding: 20px;
      margin-top: 10px;
      border: 1px solid #cdcdcd;
      background: #ffffff;
    }
    .toplist .el-table{
      width: 70%!important;
    }
    .toplist table td{
      height: 26px;
      line-height: 26px;
      padding: 1px 0;
    }
    .bottomresult{
      padding: 20px;
      margin-top: 20px;
      background: #ffffff;
      border: 1px solid #cdcdcd;
      overflow-y: auto;
    }
    .bottomresult p{
      color: #ff4200;
      margin-bottom: 15px;
      font-size: 14px;
    }
    .bottomresult p span{
      font-size: 16px;
      margin-right: 13px;
    }
    .bottomresult table td{
      height: 26px;
      line-height: 26px;
      padding: 7px 0;
    }
    .el-table .cell{
      height: 23px;
    }
    .toplist .el-table th{
      padding: 5px 0!important;
    }
    .toplist table td{
      padding: 1px 0!important;
    }
    .el-button--small, .el-button--small.is-round{
      padding: 5px 0px;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
  }
</style>
