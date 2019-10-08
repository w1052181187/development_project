<template>
  <div id="negSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="4">
        <leftNavRecord @scoreFlag="scoreFlag" :lefNavArry="lefNavArry"></leftNavRecord>
      </el-col>
      <el-col :span="20" class="negSummrightbox">
        <bidSummaryCommon v-if="flagShow === 1" :checkType = "type"></bidSummaryCommon>
        <bidConsultationSummary v-if="flagShow === 2" :negName="negName" :frequency="frequency" :lefNavArry="lefNavArry" :checkType = "type"></bidConsultationSummary>
        <bidFinalReview v-if="flagShow === 3" :checkType = "type"></bidFinalReview>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftNavRecord from '@/components/common/competitive-consultation/left-nav-record'
import bidSummaryCommon from '@/components/common/competitive-consultation/bid-summany'
import bidConsultationSummary from '@/components/common/competitive-consultation/bid-consultation-summary'
import bidFinalReview from '@/components/common/competitive-consultation/bid-final-review'
import { consultingSummary } from '@/api'
export default {
  components: {
    leftNavRecord,
    bidSummaryCommon,
    bidConsultationSummary,
    bidFinalReview
  },
  data () {
    return {
      flagShow: 1,
      type: 'ZGXSH',
      negName: '',
      frequency: '', // 次数
      // 评审记录
      lefNavArry: []
    }
  },
  methods: {
    // 侧边导航数据
    leftNavList () {
      consultingSummary.leftNav().then((res) => {
        let startArray = [
          {
            resourceName: '资格评审汇总',
            flag: 1,
            checkType: 'ZGXSH'
          },
          {
            resourceName: '符合性评审汇总',
            flag: 1,
            checkType: 'FHXPS'
          }
        ]
        let middleArray = res.data.BidOpenResourceList
        middleArray.map((ite) => {
          ite.flag = 2
        })
        let endingArray = [
          {
            resourceName: '详细评审',
            objectId: 1005,
            flag: 3,
            checkType: 'HLXSH'
          }
        ]
        this.lefNavArry = [...startArray, ...middleArray, ...endingArray]
      })
    },
    scoreFlag (scoreProject) {
      this.flagShow = scoreProject.flag
      this.type = scoreProject.checkType
      this.negName = scoreProject.resourceName
      this.frequency = scoreProject.num
    }
  },
  mounted () {
    this.leftNavList()
  }
}
</script>
<style lang="less">
  #negSumm{
    padding-left:20px;
    padding-right:20px;
    box-sizing: border-box;
    .tableDataSupplier_sonbox{
      width: 80%;
      padding-bottom: 30px;
    }
    .project_summary_fatherboxleft{
      padding-right: 10px;
      box-sizing: border-box;
    }
    .project_summary_fatherboxright{
      padding-left: 10px;
      box-sizing: border-box;
    }
    .zztdemo_ruleForm{
      padding-bottom: 50px;
    }
    .project_summary_bigbox{
      margin-top: 20px;
    }
    .project_summary_box{
      width: 100%;
      min-height: 100px;
      padding: 20px;
      box-sizing: border-box;
      box-shadow: 0 0 10px rgba(0,0,0,0.2);
    }
    .negotiation_brief{
      margin-top: 35px;
    }
    .cancel_btn{
      width: 83px;
      height: 32px;
      border-radius: 0px;
      background: #ededed;
      border: 1px solid #ededed;
      padding: 0;
      color:#828282;
    }
  }
</style>
