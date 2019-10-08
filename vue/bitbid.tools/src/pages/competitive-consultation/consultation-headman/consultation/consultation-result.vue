<template>
  <div id="revResults" class="content_bgibox">
    <!--评分-->
    <div class="setup_bigbox">
      <div class="proinfobox">
        <span class="line_bigbox"></span>
        <span class="title_bigbox">磋商结果</span>
      </div>
    </div>
    <div class="score_bigbox" style="padding: 25px 50px;box-sizing: border-box">
      <el-table
        :data="tableDataScore"
        border
        style="width: 100%">
        <el-table-column
          prop="logIndex"
          label="磋商顺序"
          width="100">
        </el-table-column>
        <el-table-column
          prop="tenderName"
          label="供应商名称">
        </el-table-column>
        <el-table-column
            prop="logPrice"
          label="报价（元）">
        </el-table-column>
        <el-table-column
          prop="isPass"
          label="是否进入下一阶段">
          <template slot-scope="scope">
            <div class="statusbigbox" v-if="scope.row.isPass === 1">
              <span class="statusbox">是</span>
            </div>
            <div class="statusbigbox red_tag" v-if="scope.row.isPass === 0">
              <span class="statusbox">否</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="logReason"
          label="不合格理由">
        </el-table-column>
      </el-table>
      <!--提交按钮-->
      <div class="sub_btnbigbox">
        <el-button type="primary" class="sub_btn" @click="subBtn" v-if="!subStatus">提 交</el-button>
        <el-button type="primary" class="sub_btn" disabled v-else>已提交</el-button>
      </div>
      <!--提交按钮-->
    </div>
    <!--评分-->
  </div>
</template>
<script>
import { consultingPoints } from '@/api'
export default {
  data () {
    return {
      pageSize: 1000, // 每页展示条数
      pageNo: 0,
      tableDataScore: [],
      subStatus: false,
      isSubStatus: false
    }
  },
  methods: {
    // 供应商数据
    consultingOutcomeList () {
      consultingPoints.consultingOutcomeList(this.$route.params.num).then((res) => {
        this.isSubStatus = res.data.IsPass === 0
        this.tableDataScore = res.data.ResultList
        this.subStatus = res.data.isHide === '1'
      })
    },
    // 提交
    subBtn () {
      if (this.isSubStatus) {
        this.$message({
          type: 'warning',
          message: '还未评审全部供应商，请继续评审环节'
        })
        return false
      } else {
        consultingPoints.consultingOutcomeSub(this.$route.params.num).then((res) => {
          this.consultingOutcomeList()
        })
      }
    }
  },
  watch: {
    '$route': 'consultingOutcomeList'
  },
  mounted () {
    this.consultingOutcomeList()
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
