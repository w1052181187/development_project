<template>
  <div class="bidding" id="bidding">
    <div class="bidding-tit">
      <label><span>当前拍卖竞价室:</span><span>{{detailsInfoForm.auctionName}}</span></label>
    </div>
    <div class="bidmain">
      <el-row :gutter="15" style="margin-right:0;">
        <!-- 左侧 -->
        <el-col :span="16">
          <div class="bid-left">
            <el-row>
              <el-col :span="12">
                <div class="bidend-time">
                  <span>当前包组预计<b>{{bidEndTime}}</b>结束</span>
                  <img src="./images/clock.png" alt="" class="icon-r">
                  <div class="time">
                    <span class="time-num">{{isEndStatus ? 0 : day}}</span>
                    <span class="time-unit">天</span>
                    <span class="time-num">{{isEndStatus ? 0 : hour}}</span>
                    <span class="time-unit">时</span>
                    <span class="time-num">{{isEndStatus ? 0 : min}}</span>
                    <span class="time-unit">分</span>
                    <span class="time-num">{{isEndStatus ? 0 : second}}</span>
                    <span class="time-unit">秒</span>
                    <span class="time-end">后结束</span>
                  </div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="bidlow-price">
                  <span>当前最低报价</span>
                  <img src="./images/trade.png" alt="" class="icon-r">
                  <div class="bid-price">
                    <span class="price-num">{{lowPrice | filterLowPrice}}</span>
                    <span class="price-unit">元</span>
                    <label class="price-per">竞买人:
                      <span class="bid-per" v-if="currentUser.roleIds[0] === 5">{{currentUser.userName}}</span>
                      <span v-else>{{currentRoomBidPer}}</span>
                    </label>
                  </div>
                </div>
              </el-col>
            </el-row>
            <!-- 出价记录 -->
            <div class="bid-record-box">
              <div class="title-box">
                <p>出价记录<span>当前价格（元）</span></p>
              </div>
              <el-table
                :data="bidRecordTableData"
                style="width: 100%"
                :show-header="false"
                height="302px">
                 <el-table-column
                  prop="userName"
                  label="姓名">
                  <template slot-scope="scope">
                    <span>{{scope.row.userName}}进行了一次举牌</span>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="createDate"
                  label="日期">
                </el-table-column>
                <el-table-column
                  prop="tag"
                  label="标签"
                  width="100"
                  filter-placement="bottom-end">
                  <template slot-scope="scope">
                    <el-tag size="small" type="danger" v-if="scope.row.tag==='1'">领先</el-tag>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="offer"
                  label="价格"
                  align="right">
                  <template slot-scope="scope">
                    <span>￥</span><span>{{scope.row.offer}}</span>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页start -->
              <div class="block-page">
                <el-pagination
                  class="pagebox"
                  @current-change="handlePageChange"
                  :current-page.sync="currentPage"
                  :total="totalPage"
                  :page-size='pageSize'
                  layout="prev, pager, next, jumper">
                </el-pagination>
              </div>
              <!-- 分页end -->
            </div>
          </div>
        </el-col>
        <!-- 右侧 -->
        <el-col :span="8" style="padding-right: 0;">
          <div class="bid-right">
            <!-- 详细信息start -->
            <div class="details-info">
              <div class="title-box">
                <p>详细信息</p>
              </div>
              <div>
                <el-form :model="detailsInfoForm" label-position="right" label-width="120px">
                  <el-form-item label="竞价开始时间:">
                    <span>{{detailsInfoForm.beginTime}}</span>
                  </el-form-item>
                  <el-form-item label="预计结束时间:">
                    <span>{{detailsInfoForm.endTime}}</span>
                  </el-form-item>
                  <el-form-item label="降幅:">
                    <span v-if="detailsInfoForm.priceCut >= 10000 ">{{detailsInfoForm.priceCut/10000}}万元</span>
                    <span v-else>{{detailsInfoForm.priceCut}}元</span>
                  </el-form-item>
                  <div class="division-line"></div>
                  <el-form-item label="竞买人:">
                    <span v-if="currentUser.roleIds[0] === 5">{{currentUser.userName}}</span>
                    <span v-else>{{currentRoomBidPer}}</span>
                  </el-form-item>
                </el-form>
              </div>
            </div>
            <!-- 详细信息end -->
            <!-- 系统消息start -->
            <div class="system-info">
              <div class="title-box">
                <p>系统提示</p>
              </div>
              <label class="system-cont">
                <span>系统通知: </span>
                <span>{{systemInfo.info}}</span>
              </label>
            </div>
            <!-- 系统消息end -->
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- tabs区域 -->
    <div class="bottom-box">
      <el-tabs v-model="activeTabName" type="card" @tab-click="handleClick">
        <el-tab-pane label="报价" name="first" class="first-tab" v-if="currentUser.roleIds[0] === 5">
          <el-form :label-position="labelPosition" label-width="80px" @submit.native.prevent>
            <el-row>
              <el-col :span="12">
                <el-form-item label="快速报价（元）">
                  <div>
                    <el-radio-group v-model="checkboxGroup" :disabled="disabledRadio"  @change="changeQuickPrice" v-loading="loadingTarget">
                      <el-radio-button v-for="priceNum in fastOfferList" :label="priceNum" :key="priceNum" :disabled="isEndStatus">{{priceNum}}</el-radio-button>
                    </el-radio-group>
                  </div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="手动出价（元）">
                  <el-input v-model="manualBidding" style="width: 200px;" @keyup.enter.native="handleSubmitPrice"
                            :disabled="isEndStatus"></el-input>
                  <el-button type="" class="btn-blue-bg" @click="handleSubmitPrice"
                             :disabled="isEndStatus" v-if="currentUser.roleIds[0] === 5">提 交</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="我的出价记录" name="second" v-if="currentUser.roleIds[0] === 5">
          <el-table
            :data="myBidTableData"
            border
            style="width: 100%;margin-top: 15px;">
            <el-table-column
              type="index"
              label="序号"
              align="center"
              :index="indexMethod"
              width="55">
            </el-table-column>
            <el-table-column
              prop="offer"
              label="报价金额(元)">
            </el-table-column>
            <el-table-column
              prop="createDate"
              label="报价时间">
            </el-table-column>
          </el-table>
          <!-- 分页start -->
          <div class="block-page">
            <el-pagination
              class="pagebox"
              @current-change="handleCurrentChange"
              :current-page.sync="currentMyPage"
              :total="total"
              :page-size='pageSize'
              layout="prev, pager, next, jumper">
            </el-pagination>
          </div>
          <!-- 分页end -->
        </el-tab-pane>
        <el-tab-pane label="项目信息" name="third">
          <el-form :model="projectInfoForm" label-width="100px" class="demo-ruleForm">
            <el-row>
              <el-col :span="12">
                <el-form-item label="项目名称:">
                  <span>{{projectInfoForm.projectName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="项目编号:">
                  <span>{{projectInfoForm.projectCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="包组名称:">
                  <span>{{projectInfoForm.sectionName}}</span>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="包组编号:">
                  <span>{{projectInfoForm.sectionCode}}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import {WebPushSocket} from '../../../assets/js/websocketRoom.js'
// import {dateFormat} from '@/assets/js/common'
// 状态
import {mapState} from 'vuex'
import {biddingPrice} from '@/api'
export default {
  name: '',
  data () {
    return {
      totalPage: 0, // 出价记录-总数
      pageNo: 1,
      pageMyNo: 1,
      total: 0, // 总条数
      pageSize: 10, // 每页展示条数
      loadingTarget: false, // 加载动画
      currentRoomBidPer: '', // 当前竞价人
      num: 1,
      webPushSocket: new WebPushSocket(),
      roomId: this.$route.query.roomId,
      relatedCode: '',
      bidRecordTableData: [], // 出价记录
      detailsInfoForm: {}, // 详情
      currentPage: 1,
      currentMyPage: 1,
      labelPosition: 'top',
      activeTabName: 'first',
      myBidTableData: [], // 我的出价记录
      projectInfoForm: {}, // 项目信息-表单
      manualBidding: '', // 手动报价
      checkboxGroup: '', // 快速报价
      fastOfferList: [],
      projectId: this.$store.getters.projectId,
      currentUser: this.$store.getters.authUser,
      methodType: this.$route.query.methodType,
      bidEndTime: '',
      day: 0, // 天
      hour: 0, // 时
      min: 0, // 分
      second: 0, // 秒
      timer: -1,
      memberId: '', // 竞拍人id
      disabledRadio: true,
      priceCut: 0, // 降幅
      lowPrice: 0, // 当前最低价
      systemInfo: {info: '系统消息'},
      browserTime: null,
      isEndStatus: false
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      bidMessages: (state) => {
        return state.messages
      }
    })
  },
  watch: {
    bidMessages: function (newV, oldV) {
      if (newV.record) {
        this.lowPrice = newV.record.offer
        this.systemInfo.info = newV.senderName
        this.getOfferList(this.pageNo)
        this.getMyOfferList(this.pageMyNo)
      }
    },
    lowPrice: function (newV, oldV) {
      if (newV) {
        this.computerFatBidPrice(this.lowPrice, this.priceCut)
      }
    },
    relatedCode: function (newV, oldV) {
      // if (newV) {
      //   this.init()
      // }
    }
  },
  filters: {
    filterLowPrice (val) {
      if (!val) {
        return '0'
      }
      const strArr = (val + '').split('').reverse().join('').replace(/(\d{3})(?=\d)/g, '$1,').split('').reverse()
      return strArr.join('')
    }
  },
  methods: {
    indexMethod (index) {
      return index + (this.currentMyPage - 1) * 10 + 1
    },
    // tabs 切换事件
    handleClick () {},
    // 获得竞价室结束系统时间
    asnBrowserTime () {
      biddingPrice.asnBrowserTime(this.roomId).then(res => {
        if (res.data.browserTime) {
          this.browserTime = res.data.browserTime
          this.getBidRoomData()
        }
      })
    },
    // 出价记录
    handlePageChange (val) {
      this.pageNo = val
      // 查询列表
      this.getOfferList(this.pageNo)
    },
    // 我的出价-分页
    handleCurrentChange (val) {
      this.pageMyNo = val
      // 查询列表
      this.getMyOfferList(this.pageMyNo)
    },
    // 开始倒计时
    startCountDown (time) {
      let times = time + ''
      let startTimes = new Date(Date.parse(times.replace(/-/g, '/'))).getTime()
      let that = this
      // 校正时间差值
      let checkTime = 0
      if (that.browserTime) {
        checkTime = new Date().getTime() - that.browserTime
      }
      this.timer = setInterval(function () {
        let start = new Date().getTime()
        let totalTime = startTimes - start + checkTime
        if (totalTime <= 0) {
          that.isEndStatus = true
          that.stopCountDown()
        } else {
          that.day = parseInt(totalTime / (1000 * 60 * 60 * 24))
          that.hour = parseInt(totalTime / (1000 * 60 * 60) % 24)
          that.min = parseInt(totalTime / (1000 * 60) % 60)
          that.second = parseInt(totalTime / 1000 % 60)
        }
        if (that.day === 0 && that.hour === 0 && that.min === 5 && that.second === 0) {
          that.$set(that.systemInfo, 'info', '距离竞价结束还有5分钟')
        } else if (that.day === 0 && that.hour === 0 && that.min === 2 && that.second === 0) {
          that.$set(that.systemInfo, 'info', '距离竞价结束还有2分钟')
        } else if (that.day === 0 && that.hour === 0 && that.min === 0 && that.second === 0) {
          that.$set(that.systemInfo, 'info', '竞价结束, 请退出竞价室')
        }
      }, 1000)
    },
    // 停止倒计时
    stopCountDown () {
      clearInterval(this.timer)
    },
    // 获得竞价室信息
    getBidRoomData () {
      let obj = {
        roomId: this.roomId,
        userId: this.currentUser.userId
      }
      biddingPrice.getBidRoomData(obj.roomId, obj.userId).then(res => {
        let tempData = res.data
        if (tempData) {
          if (tempData.memberList && tempData.memberList.length > 0) {
            this.currentRoomBidPer = tempData.memberList[0].userName
          }
          if (tempData.roomInfo) {
            // 最低价和降幅
            this.lowPrice = tempData.roomInfo.startingPrice
            this.priceCut = tempData.roomInfo.priceCut
            this.computerFatBidPrice(this.lowPrice, this.priceCut)
            // 项目详细信息
            this.detailsInfoForm = tempData.roomInfo
            // 项目信息
            this.projectInfoForm = JSON.parse(tempData.roomInfo.expand)
            // 倒计时
            this.startCountDown(tempData.roomInfo.endTime)
            if (tempData.currentMember) {
              // 竞拍人员id
              this.memberId = tempData.currentMember.memberId
              this.relatedCode = tempData.currentMember.relatedCode
            } else {
              // 代理机构
              this.relatedCode = tempData.roomInfo.relatedCode
            }
            // 初始化websockt
            this.init()
          }
        }
      })
    },
    // 计算快速报价的数据
    computerFatBidPrice (lowPrice, priceCut) {
      let result = []
      let temp1 = 0
      let temp2 = 0
      let temp3 = 0
      let temp4 = 0
      for (let i = 0; i < 4; i++) {
        if (i === 0) {
          temp1 = lowPrice - priceCut
        } else if (i === 1) {
          temp2 = temp1 - priceCut
        } else if (i === 2) {
          temp3 = temp2 - priceCut
        } else {
          temp4 = temp3 - priceCut
        }
      }
      if (temp1 > 0) {
        result.push(temp1)
      }
      if (temp2 > 0) {
        result.push(temp2)
      }
      if (temp3 > 0) {
        result.push(temp3)
      }
      if (temp4 > 0) {
        result.push(temp4)
      }
      this.fastOfferList = result
    },
    // 手动报价
    handleSubmitPrice () {
      // 加载动画
      this.loadingTarget = true
      // 正则-校验手动报价输入格式
      let priceRegex = /^\d{1,14}$/
      if (!priceRegex.test(this.manualBidding)) {
        this.$message({
          type: 'warning',
          message: '报价金额格式不对，请输入正整数'
        })
        this.loadingTarget = false
        return false
      }
      let gapPrice = Number(this.lowPrice) - Number(this.manualBidding)
      let resultValue = gapPrice % this.detailsInfoForm.priceCut
      if (resultValue === 0) {
        let obj = {
          offer: this.manualBidding,
          userName: this.currentUser.userName,
          userId: this.currentUser.userId,
          roomId: this.roomId,
          memberId: this.memberId
        }
        if (Number(this.lowPrice) <= Number(this.priceCut)) {
          this.$message({
            type: 'warning',
            message: '报价时，报价金额不能小于等于降幅额度'
          })
          this.loadingTarget = false
          return false
        } else {
          this.quotedPrice(obj)
        }
        this.manualBidding = ''
      } else {
        this.$message({
          type: 'warning',
          message: '手动出价时，出价应该是降幅的整数倍'
        })
        this.loadingTarget = false
      }
    },
    // 快速出价
    changeQuickPrice () {
      // 加载动画
      this.loadingTarget = true
      let obj = {
        objectId: '',
        offer: Number(this.checkboxGroup),
        userName: this.currentUser.userName,
        userId: this.currentUser.userId,
        roomId: this.roomId,
        memberId: this.memberId,
        createDate: null,
        comment: ''
      }
      if (Number(this.lowPrice) <= Number(this.priceCut)) {
        this.$message({
          type: 'warning',
          message: '报价时，报价金额不能小于等于降幅额度'
        })
        this.loadingTarget = false
        return false
      } else {
        // 推送消息给其他用户
        this.webPushSocket.sendMsg(this.checkboxGroup)
        this.quotedPrice(obj)
      }
    },
    // 报价
    quotedPrice (obj) {
      biddingPrice.quotedPrice(obj).then(res => {
        // 结束动画
        this.loadingTarget = false
      })
    },
    // 获得出价记录
    getOfferList (pageNo = 1) {
      let obj = {
        roomId: this.roomId,
        pageSize: this.pageSize,
        pageNo: pageNo
      }
      biddingPrice.getOfferList(obj).then(res => {
        this.bidRecordTableData = res.data.RecordList
        // 出价记录
        this.totalPage = res.data.total
      })
    },
    // 获得我的出价记录
    getMyOfferList (pageNo = 1) {
      let obj = {
        roomId: this.roomId,
        pageSize: this.pageSize,
        pageNo: pageNo,
        creator: this.currentUser.userId
      }
      biddingPrice.getOfferList(obj).then(res => {
        // 我的出价记录
        this.myBidTableData = res.data.RecordList
        this.total = res.data.total
      })
    },
    // 初始化webSocket
    init () {
      this.webPushSocket.setSettings(this.roomId, this.currentUser.userId, this.relatedCode)
      this.webPushSocket.init()
    }
  },
  mounted () {
    if (this.currentUser.roleIds[0] === 5) {
      this.disabledRadio = false
    } else {
      this.disabledRadio = true
      this.activeTabName = 'third'
    }
    if (this.roomId) {
      this.getOfferList()
      this.getMyOfferList()
      this.asnBrowserTime()
    }
  },
  destroyed () {
    if (this.webPushSocket) {
      this.webPushSocket.close()
      this.webPushSocket = null
    }
  }
}
</script>

<style lang="scss">
#bidding {
  .bidding-tit {
    background: #fff;
    text-align: left;
    padding: 5px 0;
    padding-left: 15px;
    margin-top: 15px;
  }
  .bidmain {
    margin-top: 15px;
    .bid-left {
      width: 100%;
      overflow: hidden;
      .bidend-time, .bidlow-price {
        background: #fff;
        border: 2px solid #63AF67;
        padding: 8px 12px;
        text-align: left;
      }
      .icon-r {
        float: right;
      }
      .bidend-time {
        margin: 0 15px;
        .time .time-num {
          font-size: 20px;
          font-weight: 800;
        }
        .time .time-unit {
          font-weight: 800;
        }
        .time {
          margin-top: 20px;
          .time-end {
            display: inline-block;
            float: right;
            font-size: 14px;
            font-weight: normal;
          }
        }
      }
      .bid-price {
        margin-top: 20px;
        .price-num {
          font-size: 20px;
          font-weight: 800;
          color: #FF1C19;
        }
        .price-unit {
          color: #FF1C19;
        }
        .price-per {
          display: inline-block;
          float: right;
          line-height: 34px;
          .bid-per {
            font-weight: 800;
          }
        }
      }
      // 出价记录
      .bid-record-box {
        background: #fff;
        padding: 10px 15px;
        margin: 15px 0 0 15px;
        .title-box p span {
          display: inline-block;
          float: right;
        }
      }
    }
    .bid-right {
      width: 100%;
      // margin-left: 15px;
      overflow: hidden;
      .details-info {
        background: #fff;
        padding: 10px 0;
        padding-left: 10px;
        .division-line {
          border: 1px dashed #ddd;
          margin-bottom: 20px;
        }
      }
      .system-info {
        background: #fff;
        margin-top: 15px;
        padding: 10px;
        height: 130px;
        .system-cont {
          text-align: left;
          display: inline-block;
          width: 100%;
          padding-left: 20px;
        }
      }
    }
  }
  .bottom-box {
    background: #fff;
    margin-top: 15px;
    margin-left: 15px;
    padding: 15px;
    .first-tab {
      .el-radio-button {
        margin-right: 20px;
      }
      .el-radio-button__inner {
        border: 1px solid #FEB45D;
        color: #FF8A00;
        font-weight: 800;
        border-radius: 5px;
      }
      .el-radio-button.is-active {
        .el-radio-button__inner {
          border: 0;
          box-shadow: none;
          background: #FF8A00;
          color: #FFF;
          border-radius: 5px;
        }
      }
    }
    .first-tab .el-form-item__label {
      font-weight: 800;
      font-size: 16px;
    }
    .el-tabs__content {
      text-align: left;
    }
  }
}
</style>
