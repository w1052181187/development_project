<template>
  <div id="revResults" class="content_bgibox">
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
          <template slot-scope="scope">
            <span class="red_tag" v-if="scope.row[index] === '不合格'">{{scope.row[index]}}</span>
            <span v-else>{{scope.row[index]}}</span>
          </template>
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
import { qualificationReview } from '@/api'
export default {
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      // 表头动态获取
      tabScoreTitle: [],
      // 每个评审项目的不同数据
      tableDataScore: [],
      orderStatus: false // 命令状态
    }
  },
  methods: {
    // 列表
    reviewResultsLists () {
      let url = {
        pageNo: this.pageNo,
        pageSize: this.pageSize,
        checkType: 'ZGXSH'
      }
      qualificationReview.reviewResultsList(url).then((res) => {
        this.tabScoreTitle = res.data.ResultTitle
        this.tableDataScore = res.data.ResultList
        if (res.data.isHide === '1') {
          this.orderStatus = true
        } else {
          this.orderStatus = false
        }
      })
    },
    submitBtn () {
      this.tableDataScore.map((its) => {
        let result = its.filter(function (item, index, array) {
          return (item)
        })
        if (result.length !== this.tabScoreTitle.length) {
          this.$message({
            type: 'warning',
            message: '还未评审全部供应商，请继续评审环节'
          })
          return false
        } else {
          qualificationReview.qualificationSubmit().then((res) => {
            this.reviewResultsLists()
          })
        }
      })
    }
  },
  mounted () {
    this.reviewResultsLists()
  }
}
</script>
<style lang="less">
  #revResults{
    margin-top: 20px;
    .score_bigbox{
      padding:20px;
    }
  }
</style>
