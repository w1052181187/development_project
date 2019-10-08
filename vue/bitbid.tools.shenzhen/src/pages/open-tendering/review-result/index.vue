<template>
  <div id="revResultsNegHeadman" class="content_bgibox">
    <!--评分-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">评审结果</span>
      </div>
    </div>
    <div class="score_bigbox">
      <el-table
        :data="tableDataScore"
        border
        style="width: 100%" max-height="400">
        <el-table-column
          :prop="index.toString()"
          :label="tabScore"
          width="150"
          align="left" v-for="(tabScore, index) in tabScoreTitle" :key="index">
        </el-table-column>
      </el-table>
      <!--提交按钮-->
      <div class="sub_btnbigbox">
        <el-button type="primary" class="sub_btn" @click="submitBtn" v-if="!orderStatus">提 交</el-button>
        <el-button type="primary" class="sub_btn" disabled v-else>已提交</el-button>
      </div>
      <!--提交按钮-->
    </div>
    <!--评分-->
  </div>
</template>

<script>
import { openTenderReview } from '@/api'
export default {
  name: 'reviewResult',
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      // 表头动态获取
      tabScoreTitle: [],
      // 每个评审项目的不同数据
      tableDataScore: [],
      orderStatus: false, // 命令状态
      reviewType: ''
    }
  },
  methods: {
    reviewResultsLists () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        checkType: this.reviewType
      }
      openTenderReview.reviewResultsList(url).then((res) => {
        this.tabScoreTitle = res.data.ResultTitle
        this.tableDataScore = res.data.ResultList
        this.orderStatus = res.data.isHide === '1'
      })
    },
    submitBtn () {
      this.tableDataScore.map((its) => {
        let result = its.filter(function (item) {
          return (item)
        })
        if (result.length !== this.tabScoreTitle.length) {
          this.$message({
            type: 'warning',
            message: '还未评审全部供应商，请继续评审环节'
          })
          return false
        } else {
          openTenderReview.reviewResultsSubmit(this.reviewType).then(() => {
            this.reviewResultsLists()
          })
        }
      })
    },
    handleType () {
      this.reviewType = this.$route.params.type
    },
    init () {
      this.handleType()
      this.reviewResultsLists()
    }
  },
  watch: {
    '$route': 'init'
  },
  mounted () {
    this.init()
  }
}
</script>

<style lang="less">
  #revResultsNegHeadman{
    margin-top: 20px;
    .score_bigbox{
      padding:20px;
    }
  }
</style>
