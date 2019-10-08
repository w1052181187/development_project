<template>
  <div class="draw-ready" id="draw-ready">
    <div class="draw-info-box">
      <div class="draw-time">
        <span>时间：</span><span>{{getCurTime()}}</span>
      </div>
      <div class="draw-form">
        <div class="form-group">
          <span class="form-cont-l">项目名称:</span><span class="form-cont-r" :title="projectName">{{projectName}}</span>
        </div>
        <div class="form-group">
          <span class="form-cont-l">项目编号:</span><span class="form-cont-r" :title="projectCode">{{projectCode}}</span>
        </div>
        <div class="form-group">
          <span class="form-cont-l">包组名称:</span><span class="form-cont-r" :title="sectionName">{{sectionName}}</span>
        </div>
        <div class="form-group">
          <span class="form-cont-l">包组编号:</span><span class="form-cont-r" :title="sectionCode">{{sectionCode}}</span>
        </div>
        <div class="form-group" v-if="this.type === 'draw'">
          <span class="form-cont-l">抽签数量:</span><span class="form-cont-r">{{personNum}}家</span>
        </div>
      </div>
    </div>
    <div class="draw-per-box">
      <el-row :gutter="20">
        <el-col :span="12" v-for="(item, index) in tableDataExpert" :key="index">
          <div class="per-ready-group">
            <template v-if="item.initRoleId === 1">
              <span class="bid-role bid-leader">定标组长</span>
              <img src="./images/leader.png" alt="定标组长">
              <el-radio v-model="memberCheck" :label="index+''" class="jianbiao-check" @change="goToSupervisor(item)" v-if="currentUser.roleIds[0] === 4"></el-radio>
            </template>
            <template v-else>
              <span class="bid-role">定标成员</span>
              <img src="./images/member.png" alt="定标成员">
              <el-radio v-model="memberCheck" :label="index+''" class="jianbiao-check" @change="goToSupervisor(item)" v-if="currentUser.roleIds[0] === 4"></el-radio>
            </template>
            <div class="enterp-info">
              <p :title="item.companyName">{{item.companyName}}</p>
              <span>{{item.userName}}</span>
            </div>
            <div v-if="type === 'draw' && isGoInRoom">
              <el-button type="primary" plain round size="small" class="button-position" @click="clickToPlace"
                         v-if="!item.stateChange && String(currentUser.userId) === String(item.objectId)">请就位</el-button>
              <img src="./images/ok.png" alt="ok" class="ok-img" v-if="item.stateChange">
            </div>
          </div>
        </el-col>
      </el-row>
      <div class="in-room-box">
        <el-button class="btn-room-bg" @click="goToDrawRoom" :disabled="disabledBtn" v-if="this.type === 'draw' && currentUser.roleIds[0] !== 4">进入抽签室</el-button>
        <el-button class="btn-room-bg" @click="goToVoteRoom" :disabled="isGoInVoteRoom" v-if="this.type === 'vote' && currentUser.roleIds[0] !== 4">进入投票室</el-button>
        <el-button class="btn-room-bg" @click="goToAccuVoteRoom" :disabled="isGoInVoteRoom" v-if="this.type === 'accu-vote' && currentUser.roleIds[0] !== 4">进入累积投票室</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {scalingPrepare, scalingEnd, drawRecord} from '@/api'
import {WebPushSocket} from '../../../assets/js/websocket-inst.js'
// 状态
import {mapState} from 'vuex'
export default {
  name: 'draw-ready',
  data () {
    return {
      memberCheck: null, // 监标人
      currentUser: this.$store.getters.authUser,
      projectId: this.$store.getters.projectId,
      methodType: this.$route.query.methodType,
      num: this.$route.query.num || 1, // 逐轮票决法的轮数
      projectName: '',
      projectCode: '',
      sectionName: '',
      sectionCode: '',
      personNum: 0,
      tableDataExpert: [],
      showOk: false,
      type: '',
      disabledBtn: true,
      isGoInRoom: false, // 流程
      isGoInVoteRoom: true,
      // disabledPlace: true, // 用户是否都在线
      webPushSocket: null,
      // 是否就位的用户
      expertsReady: []
    }
  },
  computed: {
    // 获取状态
    ...mapState({
      drawMessages: (state) => {
        return state.messages
      }
    })
  },
  watch: {
    $route: function () {
      this.num = this.$route.query.num || 1
      this.methodType = this.$route.query.methodType
      this.init()
    },
    drawMessages: function (newV, oldV) {
      if (newV) {
        this.getReadyUser()
      }
      if (newV.message === '进入抽签室') {
        this.$router.push({path: '/admin/draw-room', query: {methodType: this.methodType, num: this.num}})
      }
      if (newV.message === '进入投票室') {
        this.$router.push({path: '/admin/vote-room', query: {methodType: this.methodType, num: this.num}})
      }
      if (newV.message === '进入累积投票室') {
        this.$router.push({path: '/admin/accu-vote-room', query: {methodType: this.methodType, num: this.num}})
      }
    },
    expertsReady (value) {
      if (value && value.length === this.tableDataExpert.length) {
        this.refreshReadyUser()
      }
    }
  },
  methods: {
    /** 监标人-进入抽签室 */
    goToSupervisor (item) {
      // console.log(item)
      if (this.methodType === '1-1') {
        this.$router.push({path: '/admin/draw-room', query: {methodType: this.methodType, num: this.num, roleType: 4}})
      } else if (this.methodType === '2-3') {
        this.$router.push({path: '/admin/accu-vote-room', query: {methodType: this.methodType, num: this.num, userId: item.objectId}})
      } else if (this.methodType.substring(0, 1) === '2') {
        if (this.num) {
          this.$router.push({path: '/admin/vote-room', query: {methodType: this.methodType, num: this.num, userId: item.objectId}})
        } else {
          this.$router.push({path: '/admin/vote-room', query: {methodType: this.methodType, num: this.num, userId: item.objectId}})
        }
      }
    },
    /** 请就位 */
    clickToPlace () {
      let query = {
        userId: this.currentUser.userId
      }
      drawRecord.setReadyUser(this.projectId, query).then((res) => {
        if (res.data.resCode === '0000') {
          this.webPushSocket.sendMsg('已就位')
        }
      })
    },
    // 判断是否可以进入抽签室
    isGoIntoDrawRoom (data = this.tableDataExpert) {
      if (data.length) {
        for (let i = 0; i < data.length; i++) {
          if (data[i].stateChange && this.isGoInRoom) {
            this.disabledBtn = false
          } else {
            this.disabledBtn = true
            break
          }
        }
      }
    },
    /** 进入抽签室 */
    goToDrawRoom () {
      if (!this.disabledBtn) {
        if (this.currentUser.roleIds[0] === 1) {
          this.webPushSocket.sendMsg('进入抽签室')
        }
        this.$router.push({path: '/admin/draw-room', query: {methodType: this.methodType, num: this.num}})
      }
    },
    goToVoteRoom () {
      if (this.num) {
        this.$router.push({path: '/admin/vote-room', query: {methodType: this.methodType, num: this.num}})
      } else {
        this.$router.push({path: '/admin/vote-room', query: {methodType: this.methodType, num: this.num}})
      }
    },
    goToAccuVoteRoom () {
      this.$router.push({path: '/admin/accu-vote-room', query: {methodType: this.methodType, num: this.num}})
    },
    getCurTime () {
      let date = new Date()
      let seperator = ':'
      let month = date.getMonth() + 1
      let strDate = date.getDate()
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      let currentdate = date.getFullYear() + '年' + month + '月' + strDate + '日' + date.getHours() + seperator + date.getMinutes() + seperator + date.getSeconds()
      return currentdate
    },
    projectInfo () {
      if (!this.projectId) {
        return
      }
      scalingPrepare.getDetail(this.projectId).then((res) => {
        this.tableDataExpert = []
        if (res.data.resCode === '0000') {
          this.projectName = res.data.ProjectView.project.projectName
          this.projectCode = res.data.ProjectView.project.projectCode
          this.sectionName = res.data.ProjectView.project.sectionName
          this.sectionCode = res.data.ProjectView.project.sectionCode
          let methods = res.data.ProjectView.scalingSystemMethods
          if (methods) {
            methods.map((item) => {
              if (item.methodType === 1) {
                this.personNum = item.number
              }
            })
          }
          res.data.ProjectView.users.map((item) => {
            if (item.initRoleId === 1) {
              this.tableDataExpert.unshift(item)
            } else {
              this.tableDataExpert.push(item)
            }
          })
          this.getReadyUser()
        }
      })
    },
    // 流程控制（先流程，后项目，再用户就位情况）
    getVoteRoomPage () {
      scalingEnd.getVoteRoomPage(this.projectId, {methodType: this.methodType, num: this.num}).then(res => {
        // 1已经执行-不可操作
        // 0未执行-可操作
        // console.log(res)
        if (res.data.resCode === '0000') {
          this.isGoInVoteRoom = false
          this.isGoInRoom = res.data.isHide === '0'
          this.projectInfo()
        } else {
          this.isGoInVoteRoom = true
        }
      })
    },
    getReadyUser () {
      if (this.type === 'draw' && this.isGoInRoom) {
        this.expertsReady = []
        this.tableDataExpert.map((item) => {
          let query = {
            userId: item.objectId
          }
          drawRecord.getReadyUser(this.projectId, query).then((res) => {
            if (res.data.resCode === '0000') {
              item.stateChange = res.data.isReady === 1
            }
            this.expertsReady.push(item)
          })
        })
      }
    },
    // 是否就位用户处理完之后再排序更新
    refreshReadyUser () {
      let temp = []
      this.expertsReady.map((item) => {
        temp.push(item)
      })
      temp.sort((a, b) => {
        return Number(a.objectId) - Number(b.objectId)
      })
      this.tableDataExpert = temp
      this.isGoIntoDrawRoom(this.tableDataExpert)
    },
    init () {
      this.type = this.$route.params.type
      this.getVoteRoomPage()
      this.webPushSocket = WebPushSocket.getInstance(this.$store.getters.projectId, this.$store.getters.authUser.userId, this.$store.getters.authUser.userName, 'All', this.methodType, this.num)
      this.webPushSocket.init()
    }
  },
  created () {
    // this.webPushSocket.setSettings(this.$store.getters.projectId, this.$store.getters.authUser.userId, this.$store.getters.authUser.userName, 'All', this.methodType, this.num)
    // this.webPushSocket.init()
  },
  mounted () {
    this.init()
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
#draw-ready {
  .draw-info-box {
    margin-top: 15px;
    padding: 10px;
    background: #fff;
    .draw-time {
      padding-top: 10px;
      font-size: 18px;
    }
    .draw-form {
      padding-top: 20px;
      .form-group {
        line-height: 2;
        .form-cont-l {
          display: inline-block;
          width: 32%;
          text-align: right;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .form-cont-r {
          display: inline-block;
          width: 40%;
          text-align: left;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
      .form-group > span {
        padding-right: 10px;
      }
    }
  }
  .draw-per-box {
    margin-top: 15px;
    padding: 20px 40px;
    background: #fff;
    .per-ready-group {
      height: 70px;
      background: #eee;
      border-radius: 5px;
      margin-bottom: 15px;
      .jianbiao-check {
        float: left;
        line-height: 70px;
        margin-left: 20px;
      }
      .el-radio__label {
        display: none;
      }
      .bid-role {
        width: 20px;
        display: inline-block;
        line-height: 1.2;
        float: left;
        background: #2E76EC;
        color: #fff;
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
        padding: 3px 6px;
        margin-right: 50px;
      }
      .bid-leader {
        background: #FF8900;
      }
      img {
        position: relative;
        top: 5px;
        width: 60px;
        height: 60px;
        float: left;
        border-radius: 5px;
      }
      .button-position {
        float: right;
        display: inline-block;
        margin-top: 20px;
        margin-right: 30px;
      }
      .enterp-info {
        float: left;
        margin-left: 30px;
        width: 50%;
        text-align: left;
        p {
          margin: 10px 0;
          text-align: left;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        span {
          display: inline-block;
          text-align: left;
          color: #999;
        }
      }
      .ok-img {
        width: 40px;
        height: 25px;
        position: relative;
        top: 20px;
        float: right;
        right: 40px;
      }
    }
    .in-room-box {
      margin-top: 30px;
      .btn-room-bg {
        background: #2E76EC;
        color: #fff;
      }
    }
  }
}
</style>
