<template>
  <div id="goDownStartEview" class="content_bgibox">
    <!--下达资审开始命令-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">下达资审结束命令</span>
      </div>
      <el-button type="success" class="go_down_endbtnbox" @click="downEndBtn" v-if="!orderStatus">下达资审结束命令</el-button>
      <el-button type="success" class="go_down_endbtnbox" disabled v-else>已下达结束命令</el-button>
      <!--操作说明-->
      <!--<div class="operation_bigbox">-->
        <!--<div class="operation_title">操作说明</div>-->
        <!--<div class="operation_content">-->
          <!--<span>1、如果所有评委都提交完成，以及其他的工作都完成之后，就可以“结束本次评标”了。在下达这个命令之后，这个工程将会进入历史工程，不再显示出来。</span>-->
        <!--</div>-->
      <!--</div>-->
      <!--操作说明-->
    </div>
    <!--下达资审开始命令-->
  </div>
</template>
<script>
import { consultingReviewControl } from '@/api'
export default {
  data () {
    return {
      orderStatus: false // 命令状态
    }
  },
  methods: {
    // 查询下达状态
    inquiryDownStatus () {
      consultingReviewControl.retrialCommandEnd().then((res) => {
        if (res.data.isHide === '1') {
          this.orderStatus = true
        } else {
          this.orderStatus = false
        }
      })
    },
    downEndBtn () {
      consultingReviewControl.douwnEndTrial().then((res) => {
        this.inquiryDownStatus()
      })
    }
  },
  mounted () {
    this.inquiryDownStatus()
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
