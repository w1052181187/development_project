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
            :data="tableDataSupplier"
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
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="firstPrice"
              label="首次报价"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="price"
              label="最终报价"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="score"
              label="综合得分"
              width="120"
              align="center"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="ranking"
              label="排名"
              width="120"
              show-overflow-tooltip>
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
import { consultingReview } from '@/api'
export default {
  props: ['checkType'],
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      // 供应商选择后数据
      tableDataSupplier: []
    }
  },
  methods: {
    reviewSummaryLists () {
      let params = {
        checkType: this.$props.checkType
      }
      consultingReview.reviewSummaryList(params).then((res) => {
        this.tableDataSupplier = res.data.ResultList
      })
    }
  },
  mounted () {
    this.reviewSummaryLists()
  }
}
</script>
<style scoped>

</style>
