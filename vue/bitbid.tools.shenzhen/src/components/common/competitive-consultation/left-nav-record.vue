<template>
    <div class="">
      <div class="review_summary_bigbox">
        <div class="review_record_title">评审记录</div>
        <div class="review_record_content">
          <div v-for="(scoreProject,index) in lefNavArry" :key="index" :class="{'curr_summary' : index === addrSelected}" @click="chooseAddr(index, scoreProject)">
            <span>{{scoreProject.resourceName}}</span>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
export default {
  props: ['lefNavArry'],
  data () {
    return {
      // 默认选中
      addrSelected: 0
    }
  },
  methods: {
    // 点击切换
    chooseAddr (index, scoreProject) {
      this.addrSelected = index
      this.$emit('scoreFlag', scoreProject)
    }
  },
  mounted () {
  }
}
</script>
<style scoped>

</style>
