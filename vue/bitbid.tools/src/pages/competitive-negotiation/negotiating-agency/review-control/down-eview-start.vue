<template>
  <div id="goDownStartEview" class="content_bgibox">
    <!--下达资审开始命令-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">下达开始谈判命令</span>
      </div>
      <el-button type="success" class="go_down_btnbox" @click="downBtn" v-if="!orderStatus">下达开始谈判命令</el-button>
      <el-button type="success" class="go_down_btnbox" disabled v-else>已下达谈判命令</el-button>
      <!--操作说明-->
      <div class="operation_bigbox">
        <div class="operation_title">操作说明</div>
        <div class="operation_content">
          <span>如果所有评委都已准备就绪，就可以下达开始谈判的命令。这时候评委可以开始评标。</span>
        </div>
      </div>
      <!--操作说明-->
      <!--专家签到情况-->
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
      <!--专家签到情况-->
    </div>
    <!--下达资审开始命令-->
  </div>
</template>
<script>
import { reviewControl } from '@/api'
export default {
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      // 专家签到情况数组
      tableDataSignIn: [],
      orderStatus: false, // 命令状态
      timer: ''
    }
  },
  methods: {
    // 查询下达状态
    inquiryDownStatus () {
      reviewControl.inquiryCommandStatus().then((res) => {
        if (res.data.isHide === '1') {
          this.orderStatus = true
        } else {
          this.orderStatus = false
        }
      })
    },
    // 下达开始命令
    downBtn () {
      reviewControl.douwnStartEvaluation().then((res) => {
        this.inquiryDownStatus()
      })
    },
    // 登陆信息
    loginInformation () {
      let parameter = 'tp'
      reviewControl.loginInfo(parameter).then((res) => {
        this.tableDataSignIn = res.data.activeUserList
      })
    }
  },
  mounted () {
    this.loginInformation()
    this.inquiryDownStatus()
    this.timer = setInterval(this.loginInformation, 4000)
  },
  beforeDestroy: function () {
    clearInterval(this.timer)
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
