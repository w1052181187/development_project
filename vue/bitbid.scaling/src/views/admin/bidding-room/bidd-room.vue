<template>
  <div id="bid_room">
    <div class="contentbigbox">
      <p class="bidtitle">当前拍卖竞价室：{{projectInfoForm.projectName}}</p>
      <div class="bidcontain">
        <div class="time">
          <div class="countdown">
            <span v-html="!isIntoStatus ? day : 0"></span>天
            <span v-html="!isIntoStatus ? hour : 0"></span>时
            <span v-html="!isIntoStatus ? min : 0"></span>分
            <span v-html="!isIntoStatus ? second : 0"></span>秒
          </div>
          <p class="prompt">距离竞价开始时间</p>
        </div>
        <div class="price">
          <el-form abel-width="100px" :model="projectInfoForm" style="background: #eee;padding: 10px 15px;">
            <el-form-item label="包组名称：">
              <span>{{projectInfoForm.sectionName}}</span>
            </el-form-item>
            <el-form-item label="包组编号：">
              <span>{{projectInfoForm.sectionCode}}</span>
            </el-form-item>
          </el-form>
        </div>
        <div class="button">
          <el-button size="small" class="btn-blue-bg" :disabled="disabledBtn" @click="createdRoom" v-loading="loadingScroll" v-if="currentUser.roleIds[0] === 3">{{isRoomCreated ? '进入竞价室' : '创建竞价室'}}</el-button>
          <el-button size="small" class="btn-blue-bg" :disabled="disabledBtn" @click="bulidRoom" v-loading="loadingScroll" v-if="currentUser.roleIds[0] !== 3">{{'进入竞价室'}}</el-button>
        </div>
      </div>
    </div>
    <!--底部-->
    <footers :bidRoomStatus="bidRoomStatus"></footers>
    <!--底部-->
  </div>
</template>

<script>
// import {projectInfo, biddingPrice, scalingPrepare, scalingEnd} from '@/api'
import {projectInfo, scalingPrepare, scalingEnd} from '@/api'
import footers from '@/views/admin/footer/footer.vue'
export default {
  components: {
    footers
  },
  data () {
    return {
      loadingScroll: false, // 点击加载
      disabledBtn: false,
      projectInfoForm: {},
      day: 0, // 天
      hour: 0, // 时
      min: 0, // 分
      second: 0, // 秒
      timer: -1,
      bidRoomStatus: true,
      isIntoStatus: false,
      projectId: this.$store.getters.projectId,
      currentUser: this.$store.getters.authUser,
      methodType: this.$route.query.methodType,
      num: this.$route.query.num,
      bidStartTime: '',
      roomId: '', // 房间号
      isRoomCreated: false // 是否已经建立竞价室
    }
  },
  methods: {
    // 获取竞价信息
    getBiddingInfo () {
      let query = {
        methodType: this.methodType,
        num: this.num
      }
      projectInfo.getProjectInfo(this.projectId, query).then(res => {
        this.projectInfoForm = res.data.ProjectView.project
        this.bidStartTime = res.data.ProjectView.scalingSystemMethods[0].beginDate
        this.isRoomCreated = !!res.data.ProjectView.project.roomId
        // 开始倒计时
        this.startCountDown(this.bidStartTime)
      })
    },
    // 开始倒计时
    startCountDown (time) {
      let times = time
      // console.log(times)
      let startTimes = new Date(Date.parse(times.replace(/-/g, '/'))).getTime()
      let that = this
      this.timer = setInterval(function () {
        let start = new Date().getTime()
        let totalTime = startTimes - start
        if (totalTime <= 0) {
          that.isIntoStatus = true
          that.stopCountDown()
        } else {
          that.day = parseInt(totalTime / (1000 * 60 * 60 * 24))
          that.hour = parseInt(totalTime / (1000 * 60 * 60) % 24)
          that.min = parseInt(totalTime / (1000 * 60) % 60)
          that.second = parseInt(totalTime / 1000 % 60)
        }
      }, 1000)
    },
    // 停止倒计时
    stopCountDown () {
      clearInterval(this.timer)
    },
    // 同步数据
    synDataToBidRoom () {
      let obj = {
        num: this.num,
        flag: 0,
        methodType: this.methodType
      }
      projectInfo.synDataToBidRoom(this.projectId, obj).then(res => {
        if (res.data.resCode === '0000') {
          // 同步完数据走流程（先执行下达命令-同步数据-走流程）
          // 流程
          this.getBidRoomPage()
          // 代理机构-3 监标人-4 定标人-2 委员会组长-1
          this.$router.push({path: '/admin/bidding-room/into-room', query: {methodType: this.methodType}})
        }
      })
    },
    // 创建竞价室(如果已经创建过直接进入竞价室列表)（代理）
    createdRoom () {
      // 同步数据
      if (!this.isRoomCreated) {
        this.synDataToBidRoom()
      } else {
        this.$router.push({path: '/admin/bidding-room/into-room', query: {methodType: this.methodType}})
      }
    },
    // 进入竞价室（非代理）
    bulidRoom () {
      // 非投标人进入竞价室列表
      if (this.currentUser.roleIds[0] !== 5) {
        this.$router.push({path: '/admin/bidding-room/into-room', query: {methodType: this.methodType}})
        return false
      }
      let that = this
      that.loadingScroll = true
      if (this.currentUser.roleIds[0] === 5) {
        scalingPrepare.getUserInfo(this.currentUser.userId).then(res => {
          this.roomId = res.data.User.roomId
          if (!this.roomId) {
            that.$message({
              type: 'warning',
              message: '请先创建竞价室'
            })
            that.loadingScroll = false
            return false
          } else {
            // 投标人
            if (that.currentUser.roleIds[0] === 5) {
              window.setTimeout(function () {
                that.loadingScroll = false
                if (!that.isIntoStatus) {
                  that.$message({
                    type: 'warning',
                    message: '竞价还未开始，不能进入竞价室'
                  })
                  return false
                } else {
                  that.$router.push({path: '/admin/bidding-room/bidding', query: {methodType: that.methodType, roomId: that.roomId}})
                }
              }, 2000)
            }
          }
        })
      }
    },
    // 竞价室-下达开始命令
    roomDataPage () {
      let obj = {
        num: this.num,
        flag: 0,
        methodType: this.methodType
      }
      projectInfo.roomDataPage(this.projectId, obj).then(res => {
        if (res.data.resCode === '4444') {
          return false
        } else if (res.data.resCode === '0000') {
          if (res.data.isHide === '1') {
            this.getBidRoomPage()
          }
        }
      })
    },
    // 竞价室-流程
    getBidRoomPage () {
      let obj = {
        methodType: this.methodType
      }
      scalingEnd.getBidRoomPage(this.projectId, obj).then(res => {
        if (res.data.resCode === '0000') {
          this.disabledBtn = res.data.isHide === '1'
        }
      })
    }
  },
  mounted () {
    // 获取竞价信息
    this.getBiddingInfo()
    // 下达开始命令
    this.roomDataPage()
  }
}
</script>

<style lang="scss">
  #bid_room{
    width: 100%;
    height: 88vh;
    background: url("../../../../static/img/beijing.png");
    background-size: auto 100%;
    .contentbigbox{
      width: 100%;
      padding:20px;
      box-sizing: border-box;
    }
    .bidtitle{
      width: 98%;
      height: 42px;
      line-height: 42px;
      background: #ffffff;
      padding: 0 20px;
      text-align: left;
      color: #333333;
      font-size: 14px;
    }
    .prompt{
      color: #999999
    }
    .bidcontain .time{
      width: 100%;
      overflow: hidden;
      margin: 50px 0 20px 0;
      text-align: center;
    }
    .bidcontain .time .countdown{
      overflow: hidden;
      height: 36px;
      line-height: 36px;
      color: #181319;
      font-size: 18px;
    }
    .bidcontain .time p{
      color: #999999;
      line-height: 32px;
      margin-top: 10px;
    }
    .bidcontain .time .countdown span{
      padding: 0 10px;
      margin: 0 5px;
      display: inline-block;
      background: #f5f5f5;
      color: #120d14;
      font-weight: bold;
      font-size: 24px;
    }
    .price{
      width: 30%;
      padding: 30px 0;
      margin: 0 auto;
      text-align: left;
    }
  }
</style>
