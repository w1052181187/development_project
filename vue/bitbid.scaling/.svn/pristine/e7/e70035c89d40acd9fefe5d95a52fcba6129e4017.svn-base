<template>
  <div id="record_box">
    <!--项目信息-->
    <project :projectInfoForm="projectForm"></project>
    <!--项目信息-->
    <!--竞价记录-->
    <pricing-record :projectId="projectId"></pricing-record>
    <!--竞价记录-->
  </div>
</template>

<script>
import project from '@/components/project.vue'
import pricingRecord from '@/components/pricing-record.vue'
import {projectInfo} from '@/api'
export default {
  components: {
    project,
    pricingRecord
  },
  data () {
    return {
      projectForm: {},
      projectId: this.$store.getters.projectId ? this.$store.getters.projectId : this.$route.query.projectId
    }
  },
  methods: {
    detail () {
      if (!this.projectId) {
        return
      }
      projectInfo.detail(this.projectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.projectForm = res.data.ProjectView.project
        }
      })
    },
    init () {
      this.detail()
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style lang="scss">
  #record_box{
  }
</style>
