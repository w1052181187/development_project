<template>
  <div id="shenzhennegSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="24" class="negSummrightbox">
        <!--评审-->
        <div class="zztdemo_ruleForm">
          <div class="proinfobox">
            <span class="line_bigbox"></span>
            <span class="title_bigbox">谈判结果</span>
          </div>
          <div style="padding: 20px 0;width: 70%">
            <el-table
              :data="supplierTable"
              border
              style="width: 100%">
              <el-table-column
                prop="tenderName"
                label="供应商名称">
              </el-table-column>
              <el-table-column
                type="index"
                label="报价排序"
                width="140"
                align="left"
                :index='dataIndex'>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <!--评审-->
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { agencyReviewSummary } from '@/api'
export default {
  name: '',
  data () {
    return {
      // 自定义序号
      dataIndex: 1,
      supplierTable: [],
      pageSize: 1000, // 每页展示条数
      pageNo: 0
    }
  },
  methods: {
    // 页面访问
    viewPageList () {
      agencyReviewSummary.clarifyPage(this.$route.params.num).then((res) => {
      })
    },
    // 审核澄清与承诺列表
    clarifyList () {
      agencyReviewSummary.tenderList(this.$route.params.num).then((res) => {
        console.log(res)
        if (res.data.isSend === '1') {
          this.supplierTable = res.data.bidOpenTenderList
        } else {
          this.supplierTable = []
        }
      })
    }
  },
  watch: {
    $route: function () {
      this.clarifyList()
    }
  },
  mounted () {
    this.viewPageList()
    this.clarifyList()
  }
}
</script>

<style lang="less">
  #shenzhennegSumm{
    margin-top: 40px;
  }
</style>
