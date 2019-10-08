<template>
  <div id="negSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="4">
        <leftNavRecord @scoreFlag="scoreFlag" :lefNavArry="lefNavArry"></leftNavRecord>
      </el-col>
      <el-col :span="20" class="negSummrightbox">
        <summaryCommon v-if="flagShow === 1" :checkType = "type"></summaryCommon>
        <negotiationSummary v-if="flagShow === 2" :negName="negName" :frequency="frequency" :lefNavArry="lefNavArry" :checkType = "type"></negotiationSummary>
        <finalReview v-if="flagShow === 3" :checkType = "type"></finalReview>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftNavRecord from '@/components/common/competitive-negotiation/left-nav-record'
import summaryCommon from '@/components/common/competitive-negotiation/summary'
import negotiationSummary from '@/components/common/competitive-negotiation/negotiation-summary'
import finalReview from '@/components/common/competitive-negotiation/final-review'
import { agencyReviewSummary } from '@/api'
export default {
  components: {
    leftNavRecord,
    summaryCommon,
    negotiationSummary,
    finalReview
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
      agencyReviewSummary.leftNav().then((res) => {
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
            resourceName: '最终轮谈判',
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
      this.negName = scoreProject.name
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
  }
</style>
