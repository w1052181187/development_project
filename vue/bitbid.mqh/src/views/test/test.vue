<template>
  <div class="wrap">
    <div class="demo">
      <el-input-number v-model="num8" controls-position="right" @change="handleChange" :min="1" :max="10"></el-input-number>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      num8: 2
    }
  },
  mounted () {
  },
  watch: {
  },
  methods: {
    handleChange (val) {
      this.$nextTick(() => {
        console.log(val)
        this.num8 = val?4:this.num8
      })
   }
  },
  components: {
  }
}
</script>

<style lang="less">
</style>
