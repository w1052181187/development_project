<template>
  <div id="goDownStartEview" class="content_bgibox">
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">{{handleTitle()}}</span>
      </div>
      <!--<el-button v-if="Object.is(commandParam, 'end') && Object.is(role, 'agent')" type="success" class="go_down_endbtnbox filedownbtn" @click="downloadClick">下载评标报告</el-button>-->
      <el-button type="success" class="go_down_btnbox go_down_endbtnbox" @click="downBtn" v-if="!orderStatus">{{handleBtnTitle()}}</el-button>
      <el-button type="success" class="go_down_btnbox" disabled v-else>已下达命令</el-button>
      <!--操作说明-->
      <!--<div class="operation_bigbox">-->
        <!--<div class="operation_title">操作说明</div>-->
        <!--<div class="operation_content">-->
          <!--<span>{{handleContent()}}</span>-->
        <!--</div>-->
      <!--</div>-->
      <!--操作说明-->
      <!--专家签到情况-->
      <template v-if="Object.is(this.commandParam, 'start')">
        <el-button type="success" class="go_down_btnbox" @click="loginInformation">获取登录情况</el-button>
        <div class="expertSignIn_box">
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
              prop="realName"
              label="专家姓名"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="signIn"
              label="登录情况"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </div>
      </template>
        <!--专家签到情况-->
    </div>
  </div>
</template>
<script>
import { evaBidCommand } from '@/api'
import {downloadFile} from '@/assets/js/common'
// 查询状态函数对象集
const statusActions = {
  'start_agent': 'inquiryCommandStatus',
  'end_agent': 'inquiryCommandEnd',
  'start_trial': 'inquiryTrialCommandStart',
  'end_trial': 'inquiryTrialCommandEnd'
}
// 下达命令函数对象集
const commandActions = {
  'start_agent': 'startEvaluation',
  'end_agent': 'endEvaluation',
  'start_trial': 'startTrial',
  'end_trial': 'endTrial'
}
const titles = {
  'start_agent': '下达开始评标命令',
  'end_agent': '如果所有评标已完成，请下达结束本次评标命令！',
  'start_trial': '下达开始资审命令',
  'end_trial': '如果所有资审已完成，请下达结束本次资审命令！'
}
const btnTitles = {
  'start_agent': '下达开始评标命令',
  'end_agent': '下达结束评标命令',
  'start_trial': '下达开始资审命令',
  'end_trial': '下达资审结束命令'
}
const contents = {
  'start_agent': '如果所有评委都已准备就绪，就可以下达开始评标的命令。这时候评委可以开始评标。',
  'end_agent': '如果所有评委都提交完成，以及其他的工作都完成之后，就可以“结束本次评标”了。在下达这个命令之后，这个工程将会进入历史工程，不再显示出来。',
  'start_trial': '如果所有评委都已准备就绪，就可以下达开始资审的命令。这时候评委可以开始资审。',
  'end_trial': '如果所有评委都提交完成，以及其他的工作都完成之后，就可以“结束本次评标”了。在下达这个命令之后，这个工程将会进入历史工程，不再显示出来。'
}
export default {
  name: 'start-or-end',
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      // 专家签到情况数组
      tableDataSignIn: [],
      orderStatus: false, // 命令状态
      role: this.$route.params.role,
      commandParam: this.$route.params.command
    }
  },
  methods: {
    // 查询下达状态
    inquiryDownStatus () {
      let action = statusActions[`${this.commandParam}_${this.role}`]
      evaBidCommand[`${action}`]().then((res) => {
        if (res.data.isHide === '1') {
          this.orderStatus = true
        } else {
          this.orderStatus = false
        }
      })
    },
    // 下达开始命令
    downBtn () {
      let action = commandActions[`${this.commandParam}_${this.role}`]
      console.log(action)
      evaBidCommand[`${action}`]().then(() => {
        this.inquiryDownStatus()
      })
    },
    // 登陆信息
    loginInformation () {
      let parameter = 'tp'
      switch (this.role) {
        case 'agent':
          parameter = 'tp'
          break
        case 'trial':
          parameter = 'zs'
          break
      }
      evaBidCommand.loginInfo(parameter).then((res) => {
        this.tableDataSignIn = res.data.activeUserList
      })
    },
    // 下载评标报告
    downloadClick () {
      evaBidCommand.downloadReport().then((res) => {
        if (!res.data.BidOpenReport) {
          this.$message({
            type: 'warning',
            message: '暂无评标报告'
          })
        } else {
          let filePath = res.data.BidOpenReport.fileId
          downloadFile('评标报告.pdf', filePath)
        }
      })
    },
    handleTitle () {
      return titles[`${this.commandParam}_${this.role}`]
    },
    handleBtnTitle () {
      return btnTitles[`${this.commandParam}_${this.role}`]
    },
    handleContent () {
      return contents[`${this.commandParam}_${this.role}`]
    },
    init () {
      this.handleTitle()
      this.handleContent()
      this.inquiryDownStatus()
      this.loginInformation()
    }
  },
  watch: {
    $route: function () {
      this.role = this.$route.params.role
      this.commandParam = this.$route.params.command
      this.init()
    }
  },
  mounted () {
    this.init()
  }
}
</script>
<style lang="less">
  #goDownStartEview{
    margin-top: 20px;
    .operation_bigbox{
      width: 100%;
      padding: 12px 30px;
      border: 1px solid #5dc35f;
      background:#f2ffea;
      box-sizing: border-box;
      margin-top: 25px;
    }
    .operation_title{
      color: #333;
      font-size: 14px;
    }
    .operation_content{
      color: #333;
      font-size: 14px;
      margin-top: 20px;
      line-height: 2.2em;
    }
    .expertSignIn_box{
      width: 45%;
      margin-top: 25px;
    }
    .setup_bigbox{
      box-shadow: 0 2px 0px 0px rgba(0,0,0,0.08) inset;
    }
  }
</style>
