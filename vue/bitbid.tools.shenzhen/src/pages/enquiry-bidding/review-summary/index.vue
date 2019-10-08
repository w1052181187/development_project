<template>
  <summaryCommon :checkType = "reviewType"></summaryCommon>
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

<style scoped>

</style>
