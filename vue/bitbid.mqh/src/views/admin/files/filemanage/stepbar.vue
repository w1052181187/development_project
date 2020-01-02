<template>
  <!-- 步骤条 -->
  <div class="admin-layout" id="stepbar-page">
    <el-steps :active="activeStep" align-center>
      <el-step title="基本信息"></el-step>
      <el-step title="备案及公告文件" v-if="this.secondStepBarFlag"></el-step>
      <el-step title="标段文件"></el-step>
    </el-steps>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  props: ['activeStep', 'secondStepBarFlag'],
  computed: {},
  created () {
  },
  /** 方法集 */
  methods: {
  },
  /** 实例创建完成 */
  mounted () {
  }
}
</script>
<style lang="less">
</style>
