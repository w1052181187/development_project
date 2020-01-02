<template>
  <el-container class="head-box">
    <el-header :class="{ 'el-header': isActive, 'el-admin': hasAdmin }" style="height:80px;">
      <div class="head-cont">
        <div class="head-title" @click="gotoIndex">
          <span>
            <img :src="logoPath" alt="" style="width: 45px; height:45px;vertical-align: middle;">
          </span>{{this.$store.getters.authUser.systemName}}
        </div>
        <div class="head-icon">
          <span class="skin-change">
            <el-dropdown style="margin-right: 15px;" @command="changeSkinColor">
              <span class="el-dropdown-link" style="color: #fff;">
                <i class="iconfont mqh-icon-yifu"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="a"><span class="skin-set skin-blue"></span>蓝色</el-dropdown-item>
                <el-dropdown-item command="b"><span class="skin-set skin-green"></span>绿色</el-dropdown-item>
                <el-dropdown-item command="c"><span class="skin-set skin-azure"></span>靛蓝</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </span>
          <span class="head-name">{{this.$store.getters.authUser.userName}}</span>
          <span class="out-btn" @click="exit">退出</span>
          <span class="head-avatar" v-if="this.$store.getters.authUser.filePath !== null"><img :src="avatarUrl"></span>
          <span class="head-avatar" v-else>
            <img src="./image/bai.png" alt="">
          </span>
        </div>
      </div>
    </el-header>
  </el-container>
</template>
<script>
import {WebPushSocket} from '../../../common/js/websocket'
import {commonJs} from 'common/js/common'
import {getTheme, setTheme} from 'common/js/auth'
export default {
  name: 'admin-head',
  data () {
    return {
      avatarUrl: '',
      email: '',
      isActive: true,
      hasAdmin: false,
      currThemeColor: 'themea'
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'hasRouterQuery'
  },
  created () {
    this.getAvatorUrl()
    this.hasRouterQuery()
    this.getDrawRoomInfo()
    this.getThemeColor()
  },
  methods: {
    /** 更换主题颜色 */
    changeSkinColor (type) {
      // 当前的主体颜色
      this.currThemeColor = `theme${type}`
      // 把className themea，themeb，themec挂载在app.vue的<div id="app"></div>上
      document.getElementById('app').className = `theme${type}`
      this.setThemeColor(this.currThemeColor, 30)
    },
    /** 获取主体颜色值从cookie */
    getThemeColor () {
      let curr = getTheme()
      if (curr) {
        document.getElementById('app').className = curr
      } else {
        document.getElementById('app').className = 'themea'
      }
      this.currThemeColor = curr
    },
    /** 设置主体颜色值到cookie */
    setThemeColor (currThemeColor, number) {
      setTheme(currThemeColor, number)
    },
    /** 跳转到首页 */
    gotoIndex () {
      if (this.$store.getters.authUser.userType !== 1) {
        this.$router.push('/')
      }
    },
    getAvatorUrl () {
      // 获得用户信息
      let userInfo = this.$store.getters.authUser.filePath
      let logoFilePath = this.$store.getters.authUser.logoPath
      this.avatarUrl = `${commonJs.imageReviewUrl}?filePath=${userInfo}`
      this.logoPath = `${commonJs.imageReviewUrl}?filePath=${logoFilePath}`
    },
    exit () {
      this.$store.dispatch('Logout').then(() => {
        this.$router.push('/')
      })
    },
    hasRouterQuery () {
      let adminName = this.$route.query.flag
      if (!adminName) {
        this.hasAdmin = true
        this.isActive = false
      }
      if (adminName === 1 || adminName === true) {
        this.hasAdmin = true
        this.isActive = false
      }
    },
    getDrawRoomInfo () {
      const webPushSocket = new WebPushSocket()
      webPushSocket.setSettings(this.$store.getters.authUser.userId, this.$store.getters.authUser.enterpriseId, this.$route.query.roomId || '')
      webPushSocket.init()
    }
  },
  components: {
  },
  mounted () {
  }
}
</script>
<style lang="less">
@import url('../../../common/less/theme/color.less');
.head-box {
  height:80px;
  background-color: #3F63F7;
}
.skin-set {
  width: 10px;
  height: 10px;
  display: inline-block;
  margin-right: 5px;
}
.skin-blue {
  background: #3F63F7;
}
.skin-green {
  background: #009688;
}
.skin-azure {
  background: #3F51B5;
}
.el-admin {
  width: 98% !important;
}
.el-header {
  width: 1200px;
  color: #fff;
  line-height: 80px;
  left: 50%;
  margin: 0 auto;
  padding: 0;
  .head-cont {
    .head-title {
      float: left;
      font-size: 20px;
      span {
        font-size: 30px;
        padding-right: 20px;
      }
      &:hover{
        cursor: pointer;
      }
    }
    .head-icon {
      float: right;
      .skin-change {
        &:hover {
          cursor: pointer;
        }
      }
      .head-name {
        width: 50px;
        margin-top: 10px;
      }
      .head-avatar {
        display: block;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        /*background: #fff;*/
        float: right;
        margin: 10px 20px;
        img {
          width: 50px;
          height: 50px;
        }
      }
      .out-btn {
        float: right;
        cursor: pointer;
      }
    }
  }
}
</style>
