<template>
  <div id="downBidEvalu" class="content_bgibox">
    <!--评标控制-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">如果确定废标，请下达废标指令！</span>
      </div>
      <el-button type="success" class="go_down_endbtnbox filedownbtn" @click="scrapBtn" v-if="!orderStatus">下达废标指令</el-button>
      <el-button type="success" class="go_down_endbtnbox" v-else disabled>已下达废标指令</el-button>
      <!--废标理由-->
      <el-dialog title="废标理由" :visible.sync="dialogFormVisible" width="40%" :before-close="cancelBtn">
        <el-form :model="rejectedFrom" label-width="120px" ref="rejectedFrom" :rules="rules">
          <el-form-item label="废标理由：" prop="briefReason">
            <el-input type="textarea" v-model="rejectedFrom.briefReason"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="determineBtn('rejectedFrom')">确 定</el-button>
        </div>
      </el-dialog>
      <!--驳回理由-->
      <!--操作说明-->
      <div class="operation_bigbox">
        <div class="operation_title">操作说明</div>
        <div class="operation_content">
          <span>下达废标指令后，评标流程将会终止，该工程进入历史工程。</span>
        </div>
      </div>
      <!--操作说明-->
    </div>
    <!--评标控制-->
  </div>
</template>
<script>
import { consultingReviewControl } from '@/api'
export default {
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      dialogFormVisible: false, // 废标弹框
      rejectedFrom: {}, // 废标表单
      orderStatus: false, // 命令状态
      rules: {
        briefReason: [
          { required: true, message: '请输入废标理由', trigger: ['blur', 'change'] },
          {min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: ['blur', 'change']}
        ]
      }
    }
  },
  methods: {
    // 查询废标状态
    inquiryDownStatus () {
      consultingReviewControl.inquiryScrapInstruct().then((res) => {
        this.orderStatus = res.data.task.isUnused === 1
      })
    },
    // 下达废标
    downEnd (reason) {
      let param = {
        unusedReason: reason
      }
      consultingReviewControl.downScrapInstruct(param).then((res) => {
        if (res.data.resCode === '0000') {
          this.dialogFormVisible = false
          this.$refs['rejectedFrom'].resetFields()
          this.inquiryDownStatus()
        }
      })
    },
    // 废标
    scrapBtn () {
      this.dialogFormVisible = true
    },
    // 确定驳回
    determineBtn (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.downEnd(this.rejectedFrom.briefReason)
        } else {
          return false
        }
      })
    },
    // 关闭内容
    cancelBtn () {
      this.dialogFormVisible = false
      this.$refs['rejectedFrom'].resetFields()
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
