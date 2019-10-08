<template>
  <div id="downBidEvalu" class="content_bgibox">
    <!--下达评标结束命令-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">如果所有评标已完成，请下达结束本次评标命令！</span>
      </div>
      <!--<el-button type="success" class="go_down_endbtnbox filedownbtn" @click="downloadClick">{{reportBtnName}}</el-button>-->
      <el-button type="success" class="go_down_endbtnbox" @click="downEnd" v-if="!orderStatus">结束本次评标</el-button>
      <el-button type="success" class="go_down_endbtnbox" v-else disabled>已下达命令</el-button>
      <!--操作说明-->
      <div class="operation_bigbox">
        <div class="operation_title">操作说明</div>
        <div class="operation_content">
          <span>1、如果所有评委都提交完成，以及其他的工作都完成之后，就可以“结束本次评标”了。在下达这个命令之后，这个工程将会进入历史工程，不再显示出来。</span>
        </div>
      </div>
      <!--操作说明-->
    </div>
    <!--下达评标结束命令-->
  </div>
</template>
<script>
import { reviewControl } from '@/api'
import {downloadFile} from '@/assets/js/common'
export default {
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      orderStatus: false, // 命令状态
      reportBtnName: '下载评标报告'
    }
  },
  methods: {
    // 查询下达状态
    inquiryDownStatus () {
      reviewControl.inquiryCommandEnd().then((res) => {
        this.orderStatus = res.data.isHide === '1'
      })
    },
    // 下达结束
    downEnd () {
      reviewControl.douwnEndEvaluation().then((res) => {
        this.inquiryDownStatus()
      })
    },
    // 下载评标报告
    downloadClick () {
      reviewControl.downloadReport().then((res) => {
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
    }
  },
  mounted () {
    this.inquiryDownStatus()
  }
}
</script>
<style lang="less">
  #downBidEvalu{
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
