<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="dready-page">
    <div class="breadcrumb-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>抽签系统</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/draw/drawroom' }">抽签</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/drawroom/dready' }">抽签准备</el-breadcrumb-item>
      </el-breadcrumb>
      <el-button type="default" icon="el-icon-back" @click="deleteRoomInfo" class="go-back-btn">
        返回
      </el-button>
    </div>
    <div class="dready-cont" id="dreadyCont">
      <el-row :gutter="20">
        <el-col :span="17" style="float: left">
          <component :is="currentView" :roomId = "roomId" @roomId="getRoomId" @webSocket="getWebSocket" >
            <!-- 组件在 vm.currentview 变化时改变！ -->
          </component>
        </el-col>
        <el-col :span="7" style="float: right">
          <!-- 视频区 -->
          <div class="dready-right">
            <div class="video-tit">视频区</div>
            <div class="video-cont" id="videoCont">
              <!--视频区自动添加-->
            </div>
            <canvas id="canvas" width="640" height="480" ></canvas>
          </div>
          <!-- 视频区结束 -->
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import {WebRTC} from '@/common/js/webRTC'
import {drawroom} from 'api/index'
const adapter = require('webrtc-adapter')
// 实例
export default {
  data () {
    return {
      currentView: 'readyBar',
      roomId: '',
      webSocket: null,
      webRTC: null
    }
  },
  components: {
    readyBar: resolve => require(['./ready'], resolve),
    roomBar: resolve => require(['./room'], resolve)
  },
  computed: {
    getDreadyMessages () {
      return this.$store.getters.messages
    }
  },
  watch: {
    getDreadyMessages () {
      this.getStreamSrc(this.$store.getters.messages.enterprises, this.$store.getters.messages.rtcMessage)
    }
  },
  created () {
    if (this.$route.query.roomId && this.$store.getters.messages.roomStatus === 2) {
      this.currentView = 'roomBar'
    } else {
      this.currentView = 'readyBar'
    }
    this.roomId = this.$route.query.roomId
  },
  mounted () {
  },
  methods: {
    /** 清空房间信息 */
    deleteRoomInfo () {
      if (this.roomId && this.roomId !== 'undefined') {
        drawroom.delete(this.roomId).then((res) => {
          if (res.data.resCode === '0000') {
            this.$router.go(-1)
          }
        })
      } else {
        this.$router.go(-1)
      }
    },
    /** 获取房间号信息 */
    getRoomId (roomId) {
      if (roomId) {
        this.currentView = 'roomBar'
      } else {
        this.currentView = 'readyBar'
      }
      this.roomId = roomId
    },
    getWebSocket (webSocket) {
      this.webSocket = webSocket
    },
    getStreamSrc (enterpriseInfo, message) {
      var that = this
      // 本地已创建peerconnetion
      if (that.webRTC != null) {
        // 新增用户
        for (var i = 0; i < enterpriseInfo.length; i++) {
          if (this.$store.getters.authUser.userId !== enterpriseInfo[i].userId && JSON.stringify(that.webRTC.enterprises).indexOf(enterpriseInfo[i].userId) < 0) {
            that.webRTC.emit('_new_peer', enterpriseInfo[i].userId)
            that.webRTC.enterprises.push(enterpriseInfo[i])
          }
        }
        // 移除退出抽签室用户
        for (var j = 0; j < that.webRTC.enterprises.length; j++) {
          if (this.$store.getters.authUser.userId !== that.webRTC.enterprises[j].userId && JSON.stringify(enterpriseInfo).indexOf(that.webRTC.enterprises[j].userId) < 0) {
            let vieWarp = document.getElementById('videoCont')
            let div = document.getElementById('row_' + that.webRTC.enterprises[j].userId)
            if (div !== null) {
              vieWarp.removeChild(div)
            }
            that.webRTC.closePeerConnection(that.webRTC.peerConnections[that.webRTC.enterprises[j].userId])
            delete that.webRTC.peerConnections[that.webRTC.enterprises[j].userId]
            that.webRTC.enterprises.splice(that.webRTC.enterprises[j])
          }
        }
        if (message != null) {
          var json = JSON.parse(message)
          if (json.receiverId === this.$store.getters.authUser.userId) {
            if (json.eventName) {
              this.webRTC.emit(json.eventName, json.data)
            } else {
              this.webRTC.emit('socket_receive_message', this.webSocket, json)
            }
          }
        }
      } else {
        const webRtc = new WebRTC()
        webRtc.setSettings(that.$store.getters.authUser.userId, that.$store.getters.authUser.enterpriseId, that.$route.query.roomId || '', that.webSocket, that.$store.getters.authUser.enterpriseType, enterpriseInfo)
        webRtc.init()
        that.webRTC = webRtc
        // 创建本地摄像头
        webRtc.createStream({video: { width: 360, height: 200 }, audio: true})
        // 展示视频
        webRtc.on('stream_created', function (stream) {
          let vieWarp = document.getElementById('videoCont')
          let div = document.createElement('div')
          div.setAttribute('class', 'el-row')
          div.setAttribute('id', 'row_' + that.$store.getters.authUser.userId)
          div.innerHTML = '<div class="el-col el-col-24"><div class="video-img"><video id="other_' + that.$store.getters.authUser.userId + '"  autoplay="true" width="320" height="200"></video></div></div>'
          vieWarp.appendChild(div)
          document.getElementById('other_' + that.$store.getters.authUser.userId).srcObject = stream
        })
      }
    }
  },
  filters: {
  }
}
</script>
<style lang="less">
#dready-page {
  .dready-cont {
    margin: 20px 20px 130px;
    background-color: #EAEDF2;
  }
  // 右侧部分
  .dready-right {
    height: 960px;
    background: #fff;
  }
  .video-first {
    padding-top: 20px;
  }
  .video-img:nth-of-type(odd) {
    padding-bottom: 20px;
  }
  .video-img:nth-of-type(even) {
    padding-top: 20px;
  }
  .project-tit, .video-tit {
    height: 30px;
    background: #F7F6FB;
    text-align: left;
    padding-top: 10px;
    padding-left: 20px;
    font-size: 14px;
    color: black;
  }
}
</style>
