<template>
  <div id="twoResult" class="result">
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
          <el-menu-item class="submenu active" index="1-2" @click="leftMenu3">算术性检查</el-menu-item>
          <el-menu-item class="submenu" index="1-3" @click="leftMenu4">合理性检查</el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="21" class="setheight">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="单价遗漏" name="first">
            <div class="toplist">
              <el-table
                :data="tabFirstData"
                ref="tabFirstData"
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
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 9)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查分部分项清单及措施项目单价、合价是否有报价为0或负数的情况。</p>
              <el-table
                :data="tabFirstResult"
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
                  prop="map.price"
                  label="单价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.totalPrice"
                  label="合价"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="清单单价算术性" name="second">
            <div class="toplist">
              <el-table
                :data="tabSecondData"
                ref="tabSecondData"
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
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 10)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查分部分项清单、单价措施项目清单单价与合价是否有计算错误的情况。</p>
              <el-table
                :data="tabSecondResult"
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
                  prop="map.price"
                  label="单价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.calPrice"
                  label="计算单价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.totalPrice"
                  label="合价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.calTotal"
                  label="计算合价"
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
          <el-tab-pane label="总价措施项目算术性" name="third">
            <div class="toplist">
              <el-table
                :data="tabThirdData"
                ref="tabThirdData"
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
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 11)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查措施项目单价是否有计算错误的情况。</p>
              <el-table
                :data="tabThirdResult"
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
                  prop="map.price"
                  label="单价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.calPrice"
                  label="计算单价"
                  show-overflow-tooltip>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="其他项目、税金算术性" name="fourth">
            <div class="toplist">
              <el-table
                :data="tabFourthData"
                ref="tabFourthData"
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
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 12)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查其他项目与税金金额是否有计算错误的情况。</p>
              <el-table
                :data="tabFourthResult"
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
                  prop="map.price"
                  label="金额"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.calPrice"
                  label="计算金额"
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
          <el-tab-pane label="合价算术性" name="fifth">
            <div class="toplist">
              <el-table
                :data="tabFifthData"
                ref="tabFifthData"
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
                    <el-button type="text" size="small" @click="_getCheckResult(scope, 13)">
                      查看
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="bottomresult">
              <p><span>检查规则</span>     检查标段工程、各单项工程、各单位工程报价及投标总价是否有计算错误的情况。</p>
              <el-table
                :data="tabFifthResult"
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
                  prop="map.type"
                  label="项目类型"
                  width="120">
                  <template slot-scope="scope">
                    <span v-if="scope.row.map.type === 1">标段</span>
                    <span v-if="scope.row.map.type === 2">单项工程</span>
                    <span v-if="scope.row.map.type === 3">单位工程</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="name"
                  label="项目名称"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.unitPartial"
                  label="分部分项合价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.unitKill"
                  label="施工技术"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.unitOm"
                  label="施工组织"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.unitOther"
                  label="其他項目"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.unitTax"
                  label="稅金"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.totalPrice"
                  label="合价"
                  show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                  prop="map.calTotal"
                  label="计算合价"
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
      tabFirstData: [],
      tabFirstResult: [],
      tabSecondData: [],
      tabSecondResult: [],
      tabThirdData: [],
      tabThirdResult: [],
      tabFourthData: [],
      tabFourthResult: [],
      tabFifthData: [],
      tabFifthResult: [],
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
      rightH[3].style.height = (screenH - 380) + 'px'
      rightH[4].style.height = (screenH - 380) + 'px'
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
          this._getCheckData(9)
          break
        case 1:
          this._getCheckData(10)
          break
        case 2:
          this._getCheckData(11)
          break
        case 3:
          this._getCheckData(12)
          break
        case 4:
          this._getCheckData(13)
          break
      }
    },
    // 检查投标单位列表
    _getCheckData (index) {
      billClearData.unitList(this.$route.query.objectId, index).then((res) => {
        switch (Number(index)) {
          case 9:
            this.tabFirstData = res.data.enters
            this._getDefaultResult(this.tabFirstData[0].objectId, 9)
            break
          case 10:
            this.tabSecondData = res.data.enters
            this._getDefaultResult(this.tabSecondData[0].objectId, 10)
            break
          case 11:
            this.tabThirdData = res.data.enters
            this._getDefaultResult(this.tabThirdData[0].objectId, 11)
            break
          case 12:
            this.tabFourthData = res.data.enters
            this._getDefaultResult(this.tabFourthData[0].objectId, 12)
            break
          case 13:
            this.tabFifthData = res.data.enters
            this._getDefaultResult(this.tabFifthData[0].objectId, 13)
            break
        }
      })
    },
    // 检查投标单位列表
    _getCheckResult (scope, index) {
      billClearData.checkResult(this.$route.query.objectId, index, scope.row.objectId).then((res) => {
        switch (Number(index)) {
          case 9:
            this.tabFirstResult = []
            this.tabFirstResult = res.data.models
            break
          case 10:
            this.tabSecondResult = []
            this.tabSecondResult = res.data.models
            break
          case 11:
            this.tabThirdResult = []
            this.tabThirdResult = res.data.models
            break
          case 12:
            this.tabFourthResult = []
            this.tabFourthResult = res.data.models
            break
          case 13:
            this.tabFifthResult = []
            this.tabFifthResult = res.data.models
            break
        }
      })
    },
    // 取投标单位列表 默认数据
    _getDefaultResult (id, index) {
      billClearData.checkResult(this.$route.query.objectId, index, id).then((res) => {
        switch (Number(index)) {
          case 9:
            this.$refs.tabFirstData.setCurrentRow(this.$refs.tabFirstData.data[0])
            this.tabFirstResult = []
            this.tabFirstResult = res.data.models
            break
          case 10:
            this.$refs.tabSecondData.setCurrentRow(this.$refs.tabSecondData.data[0])
            this.tabSecondResult = []
            this.tabSecondResult = res.data.models
            break
          case 11:
            this.$refs.tabThirdData.setCurrentRow(this.$refs.tabThirdData.data[0])
            this.tabThirdResult = []
            this.tabThirdResult = res.data.models
            break
          case 12:
            this.$refs.tabFourthData.setCurrentRow(this.$refs.tabFourthData.data[0])
            this.tabFourthResult = []
            this.tabFourthResult = res.data.models
            break
          case 13:
            this.$refs.tabFifthData.setCurrentRow(this.$refs.tabFifthData.data[0])
            this.tabFifthResult = []
            this.tabFifthResult = res.data.models
            break
        }
      })
    }
  },
  mounted () {
    this.setMain()
    this._getCheckData(9)
  }
}
</script>
<style lang="less">
  #twoResult{
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
    .el-button--small, .el-button--small.is-round{
      padding: 5px 0px;
    }
    .tabletitles{
      background:#f7f8fa!important;
    }
  }
</style>
