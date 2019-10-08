<template>
  <div class="cloudcontent"  id="cloud_workApproval">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="发起审批" name="first">
      </el-tab-pane>
      <el-tab-pane label="我发起的" name="second">
      </el-tab-pane>
    </el-tabs>
    <div class="tab-content">
      <component :is="currentView"></component>
    </div>
  </div>
</template>
<script>
import initiate from './initiate'
import myInitiated from './myInitiated'
export default {
  components: {
    initiate,
    myInitiated
  },
  data () {
    return {
      activeName: 'first',
      searchForm: {},
      currentView: 'initiate'
    }
  },
  methods: {
    handleClick (tab, event) {
      if (tab.name === 'first') {
        this.currentView = 'initiate'
      } else if (tab.name === 'second') {
        this.currentView = 'myInitiated'
      }
    }
  },
  mounted () {
  }
}
</script>
<style lang="less">
  #cloud_workApproval{
    .el-tabs__item{
      width: 100px;
      text-align: center;
    }
    .el-tabs__header{
      margin-bottom: 0;
    }
  }
</style>
