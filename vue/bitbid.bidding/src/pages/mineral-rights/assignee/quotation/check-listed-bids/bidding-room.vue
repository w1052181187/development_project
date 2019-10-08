<template>
    <div id="assbidroom">
      <div class="headertitle">
        <!--面包屑-->
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/mr/index?roledsType=2&showStatus=false' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>挂牌</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/mr/assignee/quotation/check-listed-bids?roledsType=2' }">查看挂牌竞价情况</el-breadcrumb-item>
          <el-breadcrumb-item>竞价室</el-breadcrumb-item>
        </el-breadcrumb>
        <!--面包屑-->
      </div>
      <div class="contentbigbox">
        <!--弹窗-->
        <el-dialog
          title="提示"
          :visible.sync="nullDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 拍卖室尚未建立，请等待</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="waitDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 拍卖未开始，请等待</div>
          <div class="time timecenter">
            距离竞价开始时间
            <div class="countdown"> <span v-html="hour"></span>时<span v-html="min"></span>分<span v-html="second"></span>秒</div>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <el-dialog
          title="提示"
          :visible.sync="endDialog"
          :close-on-click-modal="false"
          width="30%"
          center>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/> 此次拍卖已结束</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <!--弹窗-->
        <!--弹窗-->
        <el-dialog
          :visible.sync="enterNextDialog"
          width="30%"
          center
          :show-close="false">
          <div class="time">
            还有
              <div class="countdown"> <span v-html="min"></span>分<span v-html="second"></span>秒</div>
            自动关闭
          </div>
          <div class="timelistip"><img src="../../../../../../static/images/mineral/public/tan.png"/>请选择是否参与限时竞价</div>
              <template>
                <div class="radio">
                <el-radio v-model="enterLimitRadio" label="2">是</el-radio>
                <el-radio v-model="enterLimitRadio" label="1">否</el-radio>
                </div>
              </template>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="enterNextAuction">确 认</el-button>
            <el-button @click="quit">退出竞价室</el-button>
          </span>
        </el-dialog>
        <!--弹窗-->
        <!--主要内容 iframe-->
        <iframe id="if" src="../../../../../../static/mineral-bidroom/html/assignee/listed-bidroom.html" style="width: 100%; height: 1100px;"></iframe>
        <!--主要内容 iframe-->
      </div>
    </div>
</template>
<script>
import {factory} from '@/assets/js/auctionHandler'
import { parseDate } from '@/assets/js/common'
import { room, quaCon } from '@/api'
export default {
  data () {
    return {
      nullDialog: false,
      waitDialog: false,
      endDialog: false,
      enterNextDialog: false,
      waitNextDialog: false,
      startTime: new Date(),
      endTime: new Date(),
      hour: 0,
      min: 0,
      second: 0,
      enterLimitRadio: 0,
      auctionHandler: {},
      enterTimer: {}
    }
  },
  methods: {
    startCountDown () {
      let date = this.startTime
      let end = parseDate(date).getTime()
      let start, totalTime
      let that = this
      let timer = setInterval(function () {
        start = new Date().getTime()
        totalTime = end - start
        if (totalTime <= 0) {
          that.waitDialog = false
          window.auctionRoom.start = true
          clearInterval(timer)
        } else {
          that.waitDialog = true
          that.hour = parseInt(totalTime / (1000 * 60 * 60))
          that.min = parseInt(totalTime / (1000 * 60) % 60)
          that.second = parseInt(totalTime / 1000 % 60)
        }
      }, 500)
    },
    quit () {
      this.$router.go(-1)
    },
    stop () {
      this.endDialog = true
    },
    enterNextAuction () {
      let that = this
      if (this.enterLimitRadio > 0) {
        quaCon.enterLimitRoom({objectId: that.$route.query.quaConObjectId, isApply: that.enterLimitRadio, code: that.auctionHandler.relatedCode}).then(function (data) {
          if (data.data.resCode == '0000') {
            that.quit()
          } else {
            alert(data.data.resMessage)
          }
        })
      }
    },
    enterNextShow () {
      this.enterNextDialog = true
      let start, totalTime
      let end = parseDate(this.endTime).getTime()
      let that = this
      this.enterTimer = setInterval(function () {
        start = new Date().getTime()
        totalTime = end - start
        if (totalTime <= 0) {
          that.enterLimitRadio = 1
          that.enterNextAuction()
          that.stopEnterNext()
        } else {
          that.hour = parseInt(totalTime / (1000 * 60 * 60))
          that.min = parseInt(totalTime / (1000 * 60) % 60)
          that.second = parseInt(totalTime / 1000 % 60)
        }
      }, 500)
    },
    stopEnterNext () {
      clearInterval(this.enterTimer)
    }
  },
  mounted () {
    let that = this
    quaCon.quaConDetail({objectId: this.$route.query.quaConObjectId}).then(res => {
      that.auctionHandler = factory.create({
        auctionType: factory.AuctionType['hang'],
        userJob: factory.UserJobType['bidder'],
        isApply: res.data.qualiConfirmation.isApply,
        section: res.data.qualiConfirmation.section
      })
      that.auctionHandler.initDataStore({
        userId: that.$store.getters.authUser.userId,
        roomPath: room.getBase(),
        quit: that.quit,
        stop: that.stop,
        enterNext: that.enterNextShow
      })
      that.startTime = that.auctionHandler.getStartTime()
      that.endTime = that.auctionHandler.getHangEndTime()
      that.startCountDown()
    })
  },
  destroyed () {
    this.stopEnterNext()
  }
}
</script>
<style lang="less">
  #assbidroom{
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
    margin-top: 50px;
    .headertitle{
      width: 100%;
      height: 60px;
      line-height: 60px;
      color:#333333;
      font-size: 16px;
      text-align: left;
      padding: 0 20px;
      box-sizing: border-box;
    }
    .el-breadcrumb{
      height: 60px;
      line-height: 60px;
    }
    /*新增按钮*/
    .addbutton{
      float: left;
      background: #2c972f;
      border-color: #2c972f;
    }
    /*搜索*/
    .seacher_box {
      width: 540px;
      height: 36px;
      min-width: 50px;
      float: right;
      margin-bottom: 20px;
    }
    .select{
      width: 20%;
    }
    .search{
      width: 79%;
    }
    /*弹出框*/
    .el-form-item__content .el-select{
      float: left;
    }
    .el-dialog__header{
      overflow: hidden;
      border-bottom: 1px solid #f4f4f4;
    }
    .el-dialog__header .el-dialog__title{
      float: left;
    }
    .contentbigbox{
      width: 100%;
      min-height: 770px;
      padding:20px;
      box-sizing: border-box;
    }
    .el-table .cell {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      word-break: break-all;
      line-height: 23px;
      overflow: hidden;
      text-overflow:ellipsis;
      white-space: nowrap;
    }
    .el-button--text {
      color: #108cee;
      background: 0 0;
      padding-left: 0;
      padding-right: 0;
    }
    .el-table td{
      min-width: 0;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      text-overflow: ellipsis;
      vertical-align: middle;
      position: relative;
      padding-bottom: 5px;
    }
    .statusbox i{
      width: 6px;
      height: 6px;
      display: inline-block;
      margin-right: 5px;
      background: #9a9a9a;
      -webkit-border-radius: 50%;
      -moz-border-radius: 50%;
      border-radius: 50%;
    }
    .blue i{
      background: #118be1;
    }
    .green i{
      background: #2f8d35;
    }
    .red i{
      background: #ff4144;
    }
    .pagebox{
      margin-top: 15px;
      text-align: right;
    }
    iframe{
      border: 0px;
    }
    /* 弹窗样式 */
    .el-dialog{
      border: 2px solid #ff8a00;
    }
    .timelistip{
      width: 100%;
      margin-top: 20px;
      margin-bottom: 20px;
      text-align: center;
      color: #333333;
      font-weight: 600;
      font-size: 16px;
    }
    .timelistip img{
      vertical-align: middle;
      margin: 0 5px;
    }
    .radio{
      width: 100%;
      text-align: center
    }
    .el-radio__input.is-checked .el-radio__inner{
      border-color: #2c972f;
      background: #2c972f;
    }
    .el-radio__input.is-checked+.el-radio__label{
      color: #2c972f;
    }
    .el-dialog__header{ display: none}
    .time{
      overflow: hidden;
      text-align: right;
      margin-top: -20px;
    }
    .timecenter{
      text-align: center;
      margin-top: 0px;
    }
    .time .countdown{
      color: #ff4440;
      display: inline-block;
    }
    .el-button{
      background: #ff8a00;
      border: 1px solid #ff8a00;
      color: #ffffff;
    }
    .el-button--primary{
      background-color: #2c972f;
      border-color: #2c972f;
    }
  }
</style>
