<template>
  <div id="shenzhennegSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="4">
        <leftNavRecord :lefNavArry="lefNavArry" @scoreFlag="scoreFlag"></leftNavRecord>
      </el-col>
      <el-col :span="20" class="negSummrightbox">
        <!--评审-->
        <div class="zztdemo_ruleForm">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">审核澄清与承诺</span>
          </div>
          <div style="padding: 20px 0;width: 100%">
            <el-table
              :data="supplierTable"
              border
              style="width: 100%">
              <el-table-column
                type="index"
                label="序号"
                width="80"
                align="center"
                :index='dataIndex'>
              </el-table-column>
              <el-table-column
                prop="createrName"
                label="供应商">
              </el-table-column>
              <el-table-column
                prop="tenderPrice"
                label="投标报价小写（元）">
              </el-table-column>
              <el-table-column
                prop="upperPrice"
                label="投标报价大写（元）">
              </el-table-column>
              <el-table-column
                prop="isSubmit"
                label="是否提交澄清与承诺">
                <template slot-scope="scope">
                  <span v-if="scope.row.isSubmit === null">未提交</span>
                  <span v-if="scope.row.isSubmit === 1">提交</span>
                </template>
              </el-table-column>
              <!--<el-table-column-->
                <!--prop="qaStatus"-->
                <!--label="确认状态">-->
                <!--<template slot-scope="scope">-->
                  <!--<span v-if="scope.row.qaStatus === 1">确认</span>-->
                  <!--<span v-if="scope.row.qaStatus === 0 || scope.row.qaStatus === null">未确认</span>-->
                <!--</template>-->
              <!--</el-table-column>-->
              <el-table-column
                label="操作"
                width="180" align="center">
                <template slot-scope="scope">
                  <el-button type="text" size="small" @click="tenderFileBtn(scope)" v-if="scope.row.isSubmit === 1">查看</el-button>
                  <el-button type="text" size="small" disabled v-else>查看</el-button>
                  <el-button type="text" size="small" v-if="scope.row.offerFile && scope.row.isSubmit === 1" @click="downOfferFileBtn(scope)">下载</el-button>
                  <el-button type="text" size="small" disabled v-else>下载</el-button>
                  <!--<el-button type="text" size="small" v-if="(scope.row.qaStatus === null || scope.row.qaStatus === 0) && !startStatus && scope.row.isSubmit === null" disabled>确认</el-button>-->
                  <!--<el-button type="text" size="small" v-if="(scope.row.qaStatus === null || scope.row.qaStatus === 0) && !startStatus && scope.row.isSubmit !== null" @click="confirmBtn(scope)">确认</el-button>-->
                  <!--<el-button type="text" size="small" @click="undoBtn(scope)" v-if="scope.row.qaStatus === 1 && !startStatus">撤销</el-button>-->
                </template>
              </el-table-column>
            </el-table>
            <div style="text-align: center;margin-top: 50px" v-if="isData">
              <el-button type="primary" class="sub_btn_next" @click="startClickBtn" v-if="!startStatus">开始下一轮谈判</el-button>
              <el-button type="primary" class="sub_btn_next" disabled v-else>已开始下一轮谈判</el-button>
              <el-button type="primary" class="sub_btn_next go_addbtnbox" v-if="isUltimately && isSame && !startStatus" @click="addNumBtn">加轮</el-button>
            </div>
          </div>
        </div>
        <!--评审-->
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { agencyReviewSummary } from '@/api'
import leftNavRecord from '@/components/common/competitive-negotiation/left-nav-record'
import {downloadFile, pdfUrl} from '@/assets/js/common'
export default {
  name: '',
  components: {
    leftNavRecord
  },
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      // 侧边导航数据
      lefNavArry: [],
      lookStatus: true, // 确认
      undoStatus: true, // 撤销
      supplierTable: [],
      nextOfferData: [], // 下一轮报价
      startStatus: false,
      firstNum: 1, // 第一次进入之后
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      isStart: false,
      isData: false,
      winHeight: 0,
      winWidth: 0,
      isUltimately: false, // 是否最终轮
      isHideBtn: '0', // 是否开始下一轮
      isSame: false,
      submitTimePice: '' // 提交时间
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      agencyReviewSummary.clarifyPage(this.firstNum).then((res) => {
      })
    },
    // 切换时数据改变
    scoreFlag (scoreProject) {
      this.firstNum = scoreProject.num
    },
    // 侧边导航数据
    leftNavList () {
      agencyReviewSummary.leftNav().then((res) => {
        this.lefNavArry = res.data.BidOpenResourceList
        if (this.lefNavArry.length === 0) {
          this.broadsideStatus = true
        }
      })
    },
    // 提交时间
    reviewAgencySummaryLists () {
      this.submitTimePice = ''
      agencyReviewSummary.reviewAgencySummaryList(this.firstNum).then((res) => {
        this.submitTimePice = res.data.BidOpenNegotiatingBrief.submitTime
      })
    },
    // 审核澄清与承诺列表
    clarifyList () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        answerNum: this.firstNum
      }
      agencyReviewSummary.clarifys(url).then((res) => {
        this.supplierTable = res.data.BidOpenNegotiatingAnswerList
        this.isUltimately = res.data.isEnd
        this.isHideBtn = res.data.isHide
        if (this.supplierTable.length === 0) {
          this.isData = false
        } else {
          this.isData = true
        }
        let priceArry = []
        this.supplierTable.map((item) => {
          if (item.qaStatus === null) {
            item.qaStatus = 0
          }
          priceArry.push(item.tenderPrice)
        })
        let newPriceArr = Array.from(new Set(priceArry))
        if (this.supplierTable.length !== newPriceArr.length) {
          this.isSame = true
        } else {
          this.isSame = false
        }
        let array = this.supplierTable.map((its) => {
          return its.qaStatus
        })
        let newArr = Array.from(new Set(array))
        if (newArr.length === 1) {
          newArr.map((its) => {
            if (its === 0) {
              this.isStart = false
            } else {
              this.isStart = true
            }
          })
        } else {
          this.isStart = true
        }
        this.lookNextStatus()
      })
    },
    // 确认
    confirmBtn (scope) {
      let url = {
        objectId: scope.row.objectId,
        qaStatus: 1
      }
      agencyReviewSummary.confirmClarifys(url).then((res) => {
        this.clarifyList()
      })
    },
    // 撤销
    undoBtn (scope) {
      let url = {
        objectId: scope.row.objectId,
        qaStatus: 0
      }
      agencyReviewSummary.confirmClarifys(url).then((res) => {
        this.clarifyList()
      })
    },
    // 查看下一轮状态
    lookNextStatus (scope) {
      agencyReviewSummary.lookNext(this.firstNum).then((res) => {
        if (res.data.isHide === '1') {
          this.startStatus = true
        } else {
          this.startStatus = false
        }
      })
    },
    // 开始下一轮谈判
    startClickBtn () {
      let summitTime = new Date(Date.parse(this.submitTimePice.replace(/-/g, '/'))).getTime()
      let curTime = new Date().getTime()
      if (curTime < summitTime) {
        this.$message({
          type: 'warning',
          message: '上传文件截止时间未到，不能进行下一轮谈判'
        })
        return false
      }
      //      if (!this.isStart) {
      //        this.$message({
      //          type: 'warning',
      //          message: '至少有一位供应商为确认状态'
      //        })
      //        return false
      //      }
      agencyReviewSummary.getNextOpenBid().then((res) => {
        this.lookNextStatus()
      })
    },
    // 添加新的轮数
    addNumBtn () {
      agencyReviewSummary.addNum(this.firstNum).then((res) => {
        this.leftNavList()
        this.isUltimately = false
      })
    },
    // 下载澄清承诺
    downOfferFileBtn (scope) {
      let index1 = scope.row.offerFilePath.lastIndexOf('.')
      let index2 = scope.row.offerFilePath.length
      let suffixName = scope.row.offerFilePath.substring(index1, index2)
      downloadFile('澄清与承诺文件' + suffixName, scope.row.offerFilePath)
    },
    getWinWH () {
      let body = document.body
      this.winHeight = (body.clientHeight || body.offsetHeight) - 20
      this.winWidth = body.clientWidth || body.offsetWidth
    },
    tenderFileBtn (scope) {
      let url = pdfUrl + '?doc=' + scope.row.pdfPath +
        '&height=' + this.winHeight + '&width=' + this.winWidth
      window.open(url)
    }
  },
  watch: {
    firstNum: function () {
      this.clarifyList()
      this.lookNextStatus()
      this.getWinWH()
      this.reviewAgencySummaryLists()
    }
  },
  mounted () {
    this.leftNavList()
    this.viewPageList()
    this.clarifyList()
    this.getWinWH()
    this.reviewAgencySummaryLists()
  }
}
</script>

<style lang="less">
  #shenzhennegSumm{
    .go_addbtnbox{
      width: 154px;
      height: 40px;
      background:#2c972f;
      border-color: #2c972f;
      font-size: 14px;
      color:#fff;
      padding: 0;
      margin-top: 15px;
    }
    .go_addbtnbox:focus, .go_addbtnbox:hover {
      background: #2c972f;
      border-color: #2c972f;
      color: #fff;
    }
  }
</style>
