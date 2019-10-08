<template>
  <div id="xunj_hui">
    <summaryCommon :checkType = "reviewType"></summaryCommon>
  </div>
</template>

<script>
import summaryCommon from '@/components/common/enquiry-bidding/summary'
export default {
  name: 'reviewSummary',
  components: {
    summaryCommon
  },
  data () {
    return {
      reviewType: ''
    }
  },
  methods: {
    handleType () {
      this.reviewType = this.$route.params.type
    }
  },
  watch: {
    '$route': 'handleType'
  },
  mounted () {
    this.handleType()
  }
}
</script>

<style lang="less">
#xunj_hui{
  .zztdemo_ruleForm{
    margin-top: 60px;
  }
}
</style>
