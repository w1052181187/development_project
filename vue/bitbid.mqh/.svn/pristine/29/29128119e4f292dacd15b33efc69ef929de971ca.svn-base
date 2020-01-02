<template>
  <!-- 后台工作台-首页 -->
  <div class="index-layout" id="work-page">
    <!-- 头部信息 -->
    <adminHead class="work-index-head"></adminHead>
    <div v-if="this.$route.meta.permission == 'business'">
      <div class="main-cont">
      <div class="first-cont">
        <el-row :gutter="20" style="height:100%">
          <el-col :span="15">
            <!--用户信息start-->
            <div class="user-info">
              <div class="user-left">
                <span class="info-avatar">
                  <img src="./image/avatar.png" alt="">
                </span>
                  <ul class="info-list">
                    <li><span>{{this.$store.getters.authUser.userName}}</span></li>
                    <li><i class="iconfont mqh-icon-shouye"><span>{{this.$store.getters.authUser.enterpriseName}}</span></i></li>
                  </ul>
                  <span class="info-span-num" @click="handleOnline">当前在线人数:<em>{{loginUserSize}}</em></span>
                </div>
              <div class="user-right">
                <el-row :gutter="20">
                  <el-col :span="8">
                    <span class="span-title">通知消息</span>
                    <div class="span-pc right-line" @click="handleNumBtn('info')">
                      <span class="span-num">{{messageCount}}</span>
                    </div>
                  </el-col>
                  <el-col :span="8">
                    <span class="span-title">我的审批</span>
                    <div class="span-pc right-line" @click="handleNumBtn('check')">
                      <span class="span-num">{{agendaMessageCount}}</span>
                    </div>
                  </el-col>
                  <el-col :span="8">
                    <span class="span-title">计算器</span>
                    <div class="computer-img" @click="clickComputer">
                      <img src="./image/computer.svg" alt="">
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
            <!--用户信息end-->
            <!--行业资讯start-->
            <div class="second-cont">
              <div class="inform-warp">
                <div class="inform-tit">
                  <span class="inform-l">行业资讯</span>
                  <a><span class="inform-r" @click="clickNewsMore">更多>></span></a>
                </div>
                <div class="inform-list">
                  <el-table
                    @row-click="industryDetail"
                    :show-header="false"
                    border
                    :data="informTableData"
                    style="width: 100%; height: 193px;">
                    <el-table-column
                      type="index"
                      align="center"
                      :index="indexMethod"
                      width="55">
                    </el-table-column>
                    <el-table-column
                      prop="title"
                      label="内容"
                      align="left">
                    </el-table-column>
                    <el-table-column
                      prop="publishDate|"
                      label="日期"
                      width="110"
                      :formatter="filterDate"
                      align="center">
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </div>
            <!--行业资讯end-->
          </el-col>
          <el-col :span="9">
            <!--日历模块start-->
            <div class="calendar-warp">
              <!--<img src="./image/calendar.png" alt="">-->
              <div class="flex">
                <calendar
                  ref="calendar"
                  :events="calendar.events"
                  :lunar="calendar.lunar"
                  :value="calendar.value"
                  :begin="calendar.begin"
                  :end="calendar.end"
                  :weeks="calendar.weeks"
                  :months="calendar.months"
                  @select="calendar.select"
                  @selectMonth="selectMonth"
                  @selectYear="calendar.selectYear"
                  @operate="handleIconBtn">
                </calendar>
                <!--<button @click="calendar1.value=[2018,1,Math.floor(Math.random()*30+1)]">动态设置日期</button>-->
                <!--<button @click="$refs.calendar1.setToday()">返回今天</button>-->
              </div>
            </div>
            <!--日历模块end-->
          </el-col>
        </el-row>
      </div>
      <!-- 我的应用模块 -->
      <div class="third-cont">
        <el-row>
          <el-col :span="24">
            <div class="apply-warp">
              <div class="apply-tit">
                <span class="apply-l">我的应用</span>
              </div>
              <div class="apply-cont" id="modules">
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('planmanage')">
                  <router-link :to="{path:'/admin/planmanage'}" class="grid-cont plan-svg"></router-link>
                  <p>计划管理</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('draw')">
                  <router-link :to="{path:'/admin/draw'}" class="grid-cont draw-svg" ></router-link>
                  <p>抽签系统</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('record')">
                  <router-link :to="{path:'/admin/record'}" class="grid-cont record-svg"></router-link>
                  <p>招标备案</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('files')">
                  <router-link :to="{path:'/admin/files'}" class="grid-cont file-svg"></router-link>
                  <p>档案管理</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('evaluate')">
                  <router-link :to="{path:'/admin/evaluate'}" class="grid-cont check-svg"></router-link>
                  <p>考评系统</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('contract')">
                  <router-link :to="{path:'/admin/contract'}" class="grid-cont contract-svg"></router-link>
                  <p>合同备案</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('statistics')">
                  <router-link :to="{path:'/admin/statistics'}" class="grid-cont count-svg"></router-link>
                  <p>统计分析</p>
                </div>
                <div class="apply-col"  v-if="$store.getters.permissions.includes('supply')">
                  <router-link :to="{path:'/admin/supply'}" class="grid-cont supply-svg"></router-link>
                  <p>供应商库</p>
                </div>
                <div class="apply-col" v-if="this.$store.getters.permissions.includes('resource')">
                  <router-link :to="{path:'/admin/resource'}" class="grid-cont resource-svg"></router-link>
                  <p>资源管理</p>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 我的应用模块结束 -->
      <!-- 招标项目模块 -->
      <div class="fourth-cont">
        <el-row>
          <el-col :span="24">
            <div class="tend-warp">
              <div class="tend-tit">
                <span class="tend-l">招标项目</span>
              </div>
              <div class="tend-tab">
                <el-tabs v-model="activeName" tab-position="top">
                  <el-tab-pane label="所有项目" name="first">
                    <!--招标项目组件-->
                    <project :activeName="activeName"></project>
                  </el-tab-pane>
                 <!-- <el-tab-pane label="备案中" name="second">
                    <project :activeName="activeName"></project>
                  </el-tab-pane>-->
                  <el-tab-pane label="公告中" name="third">
                    <project :activeName="activeName"></project>
                  </el-tab-pane>
                  <el-tab-pane label="报名中" name="fourth">
                    <project :activeName="activeName"></project>
                  </el-tab-pane>
                  <el-tab-pane label="文件发售中" name="fifth">
                    <project :activeName="activeName"></project>
                  </el-tab-pane>
                  <el-tab-pane label="待开标" name="sixth">
                    <project :activeName="activeName"></project>
                  </el-tab-pane>
                </el-tabs>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 招标项目模块结束 -->
      <!-- 通知消息和我的审批模块 -->
      <div class="fifth-cont">
        <el-row :gutter="20">
          <el-col :span="12">
            <div class="message-warp">
              <!--通知消息组件-->
              <notice @noticeInfo="noticeInfo"></notice>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="approval-warp">
              <!--我的审批组件-->
              <todo></todo>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- 通知消息和我的审批模块结束 -->
      <!--友情链接start-->
      <div class="sixth-cont">
        <el-row :gutter="20">
          <el-col :span="24">
            <div class="friendship-warp">
              <div class="friendship-tit">
                <span class="friendship-l">友情链接</span>
              </div>
              <div class="friendship-cont">
                <el-row :gutter="20">
                  <a target="_blank" v-for="(friendLink, index) in this.friendLinkList" :key="index" :href="friendLink.linkUrl">{{friendLink.linkName}}</a>
                </el-row>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!--友情链接end-->
    </div>
    </div>
    <div v-else>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
// 引入头部
import adminHead from './../head/header.vue'
// 引入日历
import calendar from '@/components/calendar/calendar.vue'
// 招标项目
import project from './project'
// 通知消息
import notice from './notice'
// 我的审批
import todo from './todo'
import {homeIndex, message, industry} from '../../../api/index'
import {formatDate} from 'common/js/date'
// 实例
export default {
  data () {
    return {
      calendar: {
        value: [], // 默认日期
        lunar: true, // 显示农历
        // weeks:['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'], // 显示英文周
        // months:['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'], // 显示英文月份
        events: {
          // '2018-12-10': [{type: 1}, {type: 5}, {type: 6}],
          // '2018-12-20': [{type: 1}, {type: 5}, {type: 6}],
          // '2018-12-21': [{type: 1}, {type: 6}],
          // '2018-12-22': [{type: 5}, {type: 6}]
        },
        select (value) {
        },
        selectYear (year) {
        },
        timestamp: Date.now()
      },
      calendarTime: null,
      friendLinkList: [], // 友情链接
      activeName: 'first',
      // 判断是项目单位还是招标办
      isUnitOffice: '',
      currentPage: 1,
      loginUserSize: 0,
      // 行业列表
      informTableData: [
      ],
      messageCount: 0, // 未读的消息数量
      agendaMessageCount: 0, // 未消费的审批消息
      tempOpenBid: [], // 待开标
      tempDraw: [] // 待抽签
    }
  },
  computed: {
  },
  created () {
    this.init()
  },
  mounted () {
    this.getTodayDate()
    this.getCalendarData()
  },
  watch: {
    '$route': 'setCurrentPage',
    calendarTime: function (newV, oldV) {
      this.getCalendarData(newV)
    }
  },
  methods: {
    /** 获取行业资讯数 */
    getIndustryData () {
      let query = {
        pageNo: 1,
        pageSize: 4,
        status: 1,
        isPublished: 1
      }
      industry.getList(query).then((res) => {
        if (res.data.industryNewsList && res.data.industryNewsList.list.length > 0) {
          this.informTableData = res.data.industryNewsList.list
        }
      })
    },
    industryDetail (row, event, column) {
      if (row.type === 2) {
        this.$router.push({path: '/admin/news-details', query: {id: row.objectId}})
      } else {
        window.open(row.url)
      }
    },
    // 行业资讯更多
    clickNewsMore () {
      this.$router.push({path: '/admin/news-list'})
    },
    selectMonth (month, year) {
      this.calendarTime = `${year}-${month}`
    },
    noticeInfo (val) {
      this.messageCount = val
    },
    filterDate (value) {
      if (value) {
        let date = new Date(value.publishedTime)
        return formatDate(date, 'yyyy-MM-dd')
      } else {
        return null
      }
    },
    setCurrentPage () {
      // 通知消息
      this.getMessageCount()
      // 更新在线人数
      this.init()
      this.activeName = 'first'
    },
    indexMethod (index) {
      return index + (this.currentPage - 1) * 10 + 1
    },
    // 初始化页面数据
    init () {
      /**
       * 获取在线用户数量，友情链接，未读消息等基础数据
       */
      homeIndex.getBaseData().then((res) => {
        this.loginUserSize = res.data.loginUserSize
        this.friendLinkList = res.data.friendLinkList
        this.agendaMessageCount = res.data.agendaMessageCount
      })
      /**
       * 获取行业资讯数据
       */
      this.getIndustryData()
    },
    // 显示当前日期-默认日期
    getTodayDate () {
      let date = new Date()
      let Yo = date.getFullYear()
      let Mo = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1)
      let Do = date.getDate()
      this.calendar.value.push(Yo)
      this.calendar.value.push(Mo)
      this.calendar.value.push(Do)
    },
    /**
     * 点击通知消息和我的审批数字跳转
     **/
    handleNumBtn (type) {
      if (Object.is(type, 'info')) {
        this.$router.push({path: '/admin/notice'})
      } else if (Object.is(type, 'check')) {
        this.$router.push({path: '/admin/todo'})
      }
    },
    /** 日历图标操作 */
    handleIconBtn (type) {
      this.$router.push({path: '/admin/canlendar', query: {iconType: type}})
    },
    /** 在线人员 */
    handleOnline () {
      this.$router.push({path: '/admin/online'})
    },
    /** 未读的通知消息 */
    getMessageCount () {
      let query = {
        consumedStatus: 0,
        targetUser: this.$store.getters.authUser.userId
      }
      message.count(query).then(res => {
        this.messageCount = res.data.count
      })
    },
    /**
     * 获取日历上待开标和待抽签数据信息
     */
    getCalendarData (currentTime = formatDate(new Date(), 'yyyy-MM')) {
      homeIndex.getCalendarData(currentTime).then((res) => {
        // 待开标
        let tempOpenBid = []
        let bidData = res.data.bidOpenTimeList
        if (bidData) {
          tempOpenBid = this.filterFormatDate(bidData, 6)
        }
        // 待抽签
        let tempDraw = []
        let drawData = res.data.drawTimeList
        if (drawData) {
          tempDraw = this.filterFormatDate(drawData, 5)
        }
        let newArr = tempOpenBid.concat(tempDraw)
        // 转成map数据
        this.transDataToMap(newArr)
      })
    },
    // 格式化日期并转化数据格式
    filterFormatDate (arr, type) {
      let result = []
      if (arr.length) {
        arr = arr.map(item => {
          // 格式化时间
          item = this.formarDateYMD(item)
          let obj = {
            time: item,
            type: type
          }
          return obj
        })
        return arr
      }
      return result
    },
    /**
     * 格式化时间
     * 将时间格式化为【2018-1-17】
     * 月份天数不满10不补0
     **/
    formarDateYMD (date) {
      let str = ''
      let formatDate = new Date(date)
      let year = `${formatDate.getFullYear()}`
      let month = `${formatDate.getMonth() + 1}`
      let tdate = `${formatDate.getDate()}`
      str = `${year}-${month}-${tdate}`
      return str
    },
    /** 将数据包装成以时间为key，type:1和count:2为value的map集合
     * [
     * {createDae: '2018-9-12',type: 1},
     * {createDae: '2018-12-6',type: 5}
     * {createDae: '2018-9-12',type: 1}
     * ]
     * 如{
     *  '2018-9-12': [{type:1,count:2},{type:5,count:2},{type:6,count:2}],
     *  '2018-12-6': [{type:1,count:2},{type:5,count:2},{type:6,count:2}]
     * }
     **/
    transDataToMap (arr) {
      if (Array.isArray(arr)) {
        let resultMap = {}
        // 将数组中同一天的数据整合在一起
        let sameTimeArr = arr.map(item => {
          return item.time
        })
        // 去重,获得新数据
        let newArr = []
        for (var i = 0; i < sameTimeArr.length; i++) {
          if (newArr.indexOf(sameTimeArr[i]) === -1) {
            newArr.push(sameTimeArr[i])
          }
        }
        newArr.forEach((item) => {
          let tempArr = []
          arr.forEach(child => {
            let obj = {}
            if (String(item) === String(child.time)) {
              obj = {
                type: child.type
              }
            }
            if (JSON.stringify(obj) !== '{}' && JSON.stringify(tempArr).indexOf(JSON.stringify(obj)) === -1) {
              tempArr.push(obj)
            }
          })
          resultMap[item] = tempArr
        })
        this.$set(this.calendar, 'events', resultMap)
      }
    },
    // 点击计算器
    clickComputer () {
      this.$router.push({path: '/admin/calculator'})
    }
  },
  // 组件
  components: {
    // 头部
    adminHead,
    // 日历
    calendar,
    // 招标项目
    project,
    // 通知消息
    notice,
    // 我的审批
    todo
  }
}
</script>
<style lang="less">
/** 日历样式引入 */
@import url('./../../../components/calendar/calendar.less');
#work-page {
  background-color: #E9EDF0;
  height: 100%;
  overflow: auto;
  /** 头部样式 */
  .work-index-head {
    position: fixed;
    width: 100%;
    z-index: 1000;
  }
  .main-cont {
    width: 1200px;
    margin: 100px auto;
  }
  // 用户基本信息
  .first-cont {
    margin-top: 20px;
    background: #E9EDF0;
    /** 用户样式 */
    .user-info {
      height: 130px;
      background: #fff;
      text-align: left;
      padding: 12px;
      .info-avatar {
        float: left;
        width: 80px;
        height: 80px;
        margin: 14px auto;
        border-radius: 50%;
        text-align: center;
        img {
          width: 80px;
          height: 80px;
        }
      }
      .info-list {
        list-style-type: none;
        line-height: 30px;
        li {
          text-align: left;
          padding-left: 60px;
          > span {
            font-size: 20px;
            font-weight: 600;
          }
          .mqh-icon-shouye {
            font-size: 16px;
            color: #FD8C00;
            font-weight: 600;
            span {
              padding-left: 16px;
            }
          }
        }
      }
      .info-span-num {
        display: inline-block;
        margin-left: 14px;
        margin-top: -12px;
        border-radius: 50px;
        width: 120px;
        height: 20px;
        line-height: 20px;
        background-color: #EBEEFD;
        padding: 6px 10px;
        cursor: pointer;
        em {
          color: #7B8CF4;
          padding-left: 10px;
        }
      }
      .user-left {
        float: left;
        width: 65%;
      }
      .user-right {
        width: 35%;
        height: 45px;
        line-height: 45px;
        text-align: center;
        margin-top: 20px;
        float: right;
        .span-title {
          color: #999;
        }
        .span-pc {
          font-size: 24px;
        }
        .right-line {
          margin-right: -8px;
          border-right: 1px solid #ddd;
          .span-num:hover {
            color: #3F63F6;
            text-decoration: underline;
            cursor: pointer;
          }
        }
        .computer-img {
          padding-top: 6px;
        }
        img {
          width: 40px;
          &:hover {
            cursor: pointer;
          }
        }
      }
    }
    .calendar-warp {
      height: 450px;
      background: #fff;
      /*img {*/
        /*width: 440px;*/
        /*height: 452px;*/
      /*}*/
    }
  }
  // 行业资讯和日历
  .second-cont {
    margin-top: 20px;
    background-color: #fff;
    .inform-warp {
      background-color: #fff;
      height: 275px;
      .inform-tit {
        height: 40px;
        background-color: #F7F6FB;
      }
    }
    .inform-list {
      padding: 20px 10px;
      height: 200px;
      .el-table {
        .el-table__row {
          cursor: pointer;
        }
        tr td:first-child {
          background: #F4F7FE;
        }
        tr td:nth-child(2) {
          .cell {
            white-space: nowrap;
          }
        }
        tr td:last-child {
          color: #999;
        }
      }
    }
  }
  // 我的应用
  .third-cont {
    margin-top: 20px;
    background-color: #E9EDF0;
    .apply-warp {
      background-color: #fff;
      height: 260px;
      .apply-tit {
        height: 40px;
        background-color: #F7F6FB;
      }
      .apply-cont {
        margin-left: 70px;
        .apply-col {
          display: block;
          float: left;
          margin-top: 40px;
          margin-right: 40px;
          width: 80px;
          height: 80px;
          border-radius: 50%;
          border: 1px solid #D7E3FD;
          &:hover {
            cursor:pointer;
            background: #6381F7;
            transition: background 0.6s;
            p {
              color: #6381F7;
              transition: color 0.6s;
            }
          }
          p {
            font-weight: 600;
          }
        }
        .grid-cont {
          display: block;
          height: 80px;
          background-position: center center;
          background-repeat: no-repeat;
          background-size: 50px;
        }
        .plan-svg {
          background-image: url('./image/plan.svg');
        }
        .draw-svg {
          background-image: url('./image/draw.svg');
        }
        .record-svg {
          background-image: url('./image/record.svg');
        }
        .file-svg {
          background-image: url('./image/file.svg');
        }
        .check-svg {
          background-image: url('./image/check.svg');
        }
        .contract-svg {
          background-image: url('./image/contract.svg');
        }
        .count-svg {
          background-image: url('./image/count.svg');
        }
        .supply-svg {
          background-image: url('./image/supply.svg');
        }
        .resource-svg {
          background-image: url('./image/resource.svg');
        }
        ul li {
          list-style: none;
        }
      }
    }
  }
  // 招标项目
  .fourth-cont {
    margin-top: 20px;
    background-color: #E9EDF0;
    .tend-warp {
      background-color: #fff;
      height: 455px;
      .tend-tit {
        height: 40px;
        background-color: #F7F6FB;
      }
      .tend-tab {
        padding: 10px 30px;
        .el-tabs__item {
          font-size: 16px;
        }
        .el-tabs__item.is-active {
          border-top: 0;
        }
      }
      .more-btn {
        height: 20px;
        margin-bottom: 10px;
        text-align: right;
        color: #FF8800;
        &:hover {
          cursor: pointer;
        }
      }
      .project-list {
        height: 50px;
        line-height: 50px;
        margin-bottom: 10px;
        border: 1px solid #ddd;
        .list-fspan {
          display: inline-block;
          width: 75%;
          text-align: left;
          float: left;
          padding-left: 20px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .list-sspan {
          display: inline-block;
          float: right;
          padding-right: 60px;
        }
        .list-tspan {
          width: 100px;
          text-align: left;
          display: inline-block;
          float: right;
          .span-slot-red,
          .span-slot-gree,
          .span-slot-blue {
            display: inline-block;
            width: 5px;
            height: 5px;
            border-radius: 50%;
            margin-right: 20px;
          }
          .span-slot-red {
            background: red;
          }
          .span-slot-gree {
            background: #3F62F4;
          }
          .span-slot-blue {
            background: #43BF2D;
          }
        }
        &:hover {
          border-color: #6E8AF7;
          background-color: #ECEFFE;
          cursor: pointer;
          transition: background-color 1s;
          transition: border-color 1s;
        }
      }
    }
  }
  // 通知消息和我的审批
  .fifth-cont {
    margin-top: 20px;
    padding-bottom: 20px;
    background-color: #E9EDF0;
    .message-warp, .approval-warp {
      background-color: #fff;
      height: 360px;
      .message-tit, .approval-tit {
        height: 40px;
        background-color: #F7F6FB;
      }
      .message-cont {
        margin: 15px;
        background-color: #eee;
        border: 1px #fff;
        .mes-span {
          display: inline-block;
          padding-bottom: 10px;
        }
        .btn-del {
          margin-top: 15px;
          visibility: hidden;
          .el-icon-error {
            font-size: 20px;
            color: #9FB3FA;
          }
        }
        &:hover {
          border: 1px solid #6E8AF7;
          transition: border 1s;
          .btn-del {
            visibility: visible;
            transition: visibility 1s;
          }
        }
      }
    }
  }
  //友情链接
  .sixth-cont {
    .friendship-warp {
      height: auto;
      background-color: #fff;
    }
    .friendship-tit {
      height: 40px;
      background-color: #F7F6FB;
    }
    .friendship-cont {
      padding: 20px;
      margin-left: 20px;
      text-align: left;
      a {
        display: inline-block;
        color: #333;
        text-decoration: none;
        padding-right: 15px;
        padding-bottom: 15px;
        &:hover {
          color: #CC3300;
          text-decoration: underline;
        }
      }
    }
  }
  .apply-l,
  .tend-l,
  .inform-l,
  .message-l,
  .approval-l,
  .friendship-l{
    display: table-cell;
    position: absolute;
    margin-left: 30px;
    margin-top: 8px;
    font-size: 18px;
    font-weight: 600;
  }
  .inform-r, .message-r, .approval-r {
    display: block;
    float: right;
    padding: 12px;
    color: #FF8800;
    &:hover {
      cursor: pointer;
    }
  }
  .message-m, .approval-m {
    display: block;
    float: right;
    padding: 8px;
    font-size: 18px;
    &:hover {
      cursor: pointer;
    }
  }
}
</style>
