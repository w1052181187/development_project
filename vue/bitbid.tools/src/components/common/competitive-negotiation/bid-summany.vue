<template>
  <div class="">
    <!--评审-->
    <div class="zztdemo_ruleForm">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">评审汇总</span>
      </div>
      <!--汇总结果数据-->
      <div class="tableDataSupplier_box">
        <div class="tableDataSupplier_sonbox">
          <el-table
            :data="tableReviewSummary"
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
              prop="name"
              label="供应商名称"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="result"
              label="结论"
              width="120"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span class="red_tag" v-if="scope.row.result === '不合格'">{{scope.row.result}}</span>
                <span v-if="scope.row.result === '合格'">{{scope.row.result}}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!--汇总结果数据-->
    </div>
    <!--评审-->
  </div>
</template>
<script>
import { qualificationReview } from '@/api'
export default {
  props: ['checkType'],
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      // 供应商选择后数据
      tableReviewSummary: []
    }
  },
  methods: {
    reviewSummaryLists () {
      let params = {
        checkType: this.$props.checkType
      }
      qualificationReview.reviewSummaryList(params).then((res) => {
        this.tableReviewSummary = res.data.ResultList
      })
    }
  },
  watch: {
    checkType: function () {
      this.reviewSummaryLists()
    }
  },
  mounted () {
    this.reviewSummaryLists()
  }
}
</script>
<style scoped>

</style>
