<template>
  <div id="negSumm" class="content_bgibox negSummbigbox">
    <el-row>
      <el-col :span="4">
        <leftNavRecord @scoreFlag="scoreFlag" :lefNavArry="lefNavArry"></leftNavRecord>
      </el-col>
      <el-col :span="20" class="negSummrightbox">
        <summaryCommon v-if="flagShow === 1" :checkType = "type" :role="role" :btnSta="btnSta"></summaryCommon>
        <inquiryResults v-if="flagShow === 2" :role="role" :btnSta="btnSta"></inquiryResults>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftNavRecord from '@/components/common/enquiry-bidding/left-nav-record'
import summaryCommon from '@/components/common/enquiry-bidding/summary'
import inquiryResults from '@/pages/enquiry-bidding/evaluation-bid-summary/inquiry-results'
import { reviewSummary } from '@/api'
import {QUALIFICATION_CHECK_TYPE_ID} from '@/assets/js/common'
export default {
  components: {
    leftNavRecord,
    summaryCommon,
    inquiryResults
  },
  data () {
    return {
      flagShow: 1,
      type: QUALIFICATION_CHECK_TYPE_ID,
      // 评审记录
      lefNavArry: [],
      btnSta: true,
      role: this.$route.params.role
    }
  },
  methods: {
    // 侧边导航数据
    leftNavList () {
      reviewSummary.leftNav().then((res) => {
        let startArray = [
          {
            resourceName: '资格评审汇总',
            flag: 1,
            checkType: QUALIFICATION_CHECK_TYPE_ID
          }
        ]
        let middleArray = res.data.BidOpenResourceList
        middleArray.map((ite) => {
          ite.flag = 1
          ite.resourceName = ite.resourceName + '汇总'
        })
        let endingArray = [
          {
            resourceName: '询价结果',
            flag: 2
          }
        ]
        this.lefNavArry = [...startArray, ...middleArray, ...endingArray]
      })
    },
    scoreFlag (scoreProject) {
      this.flagShow = scoreProject.flag
      this.type = scoreProject.checkType
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
