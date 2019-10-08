<template>
  <div id="down_scaling">
    <!--项目信息-->
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>{{handleTitleName()}}</p>
      </div>
      <div class="down_scaling_box">
        <el-button size="small" class="btn-blue-bg" @click="downBtn" v-if="!orderStatus">{{handleStartCmdName()}}</el-button>
        <el-button size="small" class="btn-blue-bg" disabled v-else>{{handleStartedCmdName()}}</el-button>
      </div>
      <!--操作说明-->
      <div class="operation_bigbox">
        <div class="operation_title">操作说明</div>
        <div class="operation_content">
          <span>1.如果所有定标委员会成员都已准备就绪，就可以下达开始定标的命令。这时所有定标委员会成员可以开始定标。</span>
        </div>
      </div>
      <!--操作说明-->
      <!--专家签到情况-->
      <div class="login_bigbox">
        <el-button size="small" class="btn-blue-bg" @click="loginInformation">获取登录情况</el-button>
        <div class="signin_box">
          <el-table
            :data="tableDataSignIn"
            border
            style="width: 100%">
            <el-table-column
              type="index"
              label="序号"
              width="80"
              :index='dataIndex'
              align="center">
            </el-table-column>
            <el-table-column
              prop="userName"
              label="定标人"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="signIn"
              label="登录情况"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--专家签到情况-->
    </div>
    <!--项目信息-->
    <!--底部-->
    <footers></footers>
    <!--底部-->
  </div>
</template>
<script>
import {WebPushSocket} from '../../../assets/js/websocket-inst.js'
import footers from '@/views/admin/footer/footer.vue'
import { scalingPrepare } from '@/api'
export default {
  components: {
    footers
  },
  data () {
    return {
      currentUser: this.$store.getters.authUser,
      webPushSocket: null,
      projectId: this.$store.getters.projectId ? this.$store.getters.projectId : this.$route.query.projectId,
      methodType: this.$route.query.methodType,
      num: this.$route.query.num || 1,
      dataIndex: 1, // 自定义序号
      // 下达命令状态
      orderStatus: false,
      tableDataSignIn: [],
      flag: Number(this.$route.query.flag),
      roundIndex: this.$route.query.roundIndex
    }
  },
  watch: {
    $route: function () {
      this.num = this.$route.query.num
      this.methodType = this.$route.query.methodType
      this.flag = this.$route.query.flag
      this.inquiryDownStatus()
    }
  },
  methods: {
    handleTitleName () {
      if (this.flag === 0 || this.flag === 1) {
        return `下达开始第${this.roundIndex}轮定标命令`
      } else {
        return this.num > 1 ? `下达开始第${this.num}轮定标命令` : '下达开始定标命令'
      }
    },
    handleStartCmdName () {
      if (this.flag === 0 || this.flag === 1) {
        return `下达开始第${this.roundIndex}轮定标命令`
      } else {
        return this.num > 1 ? `下达开始第${this.num}轮定标命令` : '下达开始定标命令'
      }
      // return this.num > 1 ? `下达开始第${this.num}轮定标指令` : '下达开始定标指令'
    },
    handleStartedCmdName () {
      if (this.flag === 0 || this.flag === 1) {
        return `已下达第${this.roundIndex}轮命令`
      } else {
        return this.num > 1 ? `已下达第${this.num}轮命令` : '已下达命令'
      }
    },
    // 查询下达状态
    inquiryDownStatus () {
      if (!this.projectId) {
        return
      }
      let params = {
        methodType: this.methodType,
        num: this.num,
        flag: this.flag || 0
      }
      scalingPrepare.getStartScaling(this.projectId, params).then((res) => {
        this.orderStatus = res.data.isHide === '1'
      })
    },
    // 下达开始命令
    downBtn () {
      if (!this.projectId) {
        return
      }
      let params = {
        methodType: this.methodType,
        num: this.num,
        flag: this.flag || 0
      }
      // console.log(params)
      scalingPrepare.startScaling(this.projectId, params).then((res) => {
        if (res.data.resCode === '0000') {
          this.orderStatus = true
          let megs = '下达开始命令'
          this.webPushSocket.sendMsg(megs)
        }
      })
    },
    // 登陆信息
    loginInformation () {
      if (!this.projectId) {
        return
      }
      scalingPrepare.getActiveUser(this.projectId).then((res) => {
        // console.log(res)
        this.tableDataSignIn = res.data.activeUserList
      })
    },
    initWebsocket () {
      this.webPushSocket = WebPushSocket.getInstance(this.projectId, this.currentUser.userId, this.currentUser.userName, 'All', this.methodType, this.num)
      this.webPushSocket.init()
    }
  },
  mounted () {
    this.inquiryDownStatus()
    this.initWebsocket()
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
  #down_scaling{
    .proinfo-info-box{
      background: #fff;
      margin-top: 15px;
      padding: 20px;
    }
    .down_scaling_box{
      text-align: left;
      padding-top: 25px;
    }
    .operation_bigbox {
      width: 100%;
      padding: 12px 30px;
      border: 1px solid #5dc35f;
      background: #f2ffea;
      box-sizing: border-box;
      margin-top: 25px;
    }
    .operation_title{
      color: #333;
      font-size: 14px;
      text-align: left;
    }
    .operation_content{
      color: #333;
      font-size: 14px;
      margin-top: 20px;
      line-height: 2.2em;
      text-align: left;
    }
    .login_bigbox{
      text-align: left;
      width: 50%;
      margin-top: 20px;
    }
    .signin_box{
      margin-top: 25px;
    }
  }
</style>
