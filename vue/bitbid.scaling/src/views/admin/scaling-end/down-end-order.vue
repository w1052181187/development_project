<template>
  <div id="down_end_scaling">
    <!--项目信息-->
    <div class="proinfo-info-box">
      <div class="title-box">
        <p>下达定标结束指令</p>
      </div>
      <div class="down_scaling_box">
        <el-button size="small" class="btn-blue-bg" @click="downReportBtn">下载定标报告</el-button>
        <el-button size="small" class="btn-warning-bg" @click="downBtn" v-if="!orderStatus" :disabled="disabledBtn">下达结束指令</el-button>
        <el-button size="small" class="btn-warning-bg" disabled v-else>已下达结束指令</el-button>
      </div>
      <!--操作说明-->
      <div class="operation_bigbox">
        <div class="operation_title">操作说明</div>
        <div class="operation_content">
          <span>1.如果所有定标委员会都提交完成，以及其他的工作都完成之后，就可以“结束本次定标”了。在下达这个命令之后，这个工程将会进入历史工程，不再显示出来。</span>
        </div>
      </div>
      <!--操作说明-->
    </div>
    <!--项目信息-->
  </div>
</template>

<script>
import { scalingEnd, report } from '@/api'
import {downloadFile} from '@/assets/js/common'
export default {
  data () {
    return {
      projectId: this.$store.getters.projectId ? this.$store.getters.projectId : this.$route.query.projectId,
      methodType: this.$route.query.methodType,
      // 下达命令状态
      orderStatus: false,
      disabledBtn: false

    }
  },
  methods: {
    // 查询下达状态
    inquiryDownStatus () {
      if (!this.projectId) {
        return
      }
      let params = {
        methodType: this.methodType
      }
      scalingEnd.getEndScaling(this.projectId, params).then((res) => {
        // console.log(res)
        if (res.data.resCode === '4444') {
          this.disabledBtn = true
        } else if (res.data.resCode === '0000') {
          this.orderStatus = res.data.isHide === '1'
        }
      })
    },
    downReportBtn () {
      if (!this.projectId) {
        return
      }
      report.downloadReport(this.projectId).then((res) => {
        if (!res.data.Report) {
          this.$message({
            type: 'warning',
            message: '暂无定标报告'
          })
        } else {
          let filePath = res.data.Report.fileId
          downloadFile('定标报告.pdf', filePath)
        }
      })
    },
    // 下达结束命令
    downBtn () {
      if (!this.projectId) {
        return
      }
      this.$confirm('在下达这个命令之后，这个工程将会进入历史工程，不再显示出来，将会无法下载定标报告?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          methodType: this.methodType
        }
        scalingEnd.endScaling(this.projectId, params).then((res) => {
          if (res.data.resCode === '0000') {
            this.inquiryDownStatus()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消下达结束命令'
        })
      })
    }
  },
  mounted () {
    this.inquiryDownStatus()
  }
}
</script>

<style lang="scss">
  #down_end_scaling{
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
