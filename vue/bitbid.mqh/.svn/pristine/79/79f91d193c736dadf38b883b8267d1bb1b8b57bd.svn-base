<template>
  <!-- 后台主容器 -->
  <div class="admin-layout" id="computer-page">
    <div class="breadcrumb-mqh-tit" >
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/system/computer' }"><span>计算器配置</span></el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="maincont-box">
      <el-tabs type="card" v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="招标代理费计算器" name="first"></el-tab-pane>
        <el-tab-pane label="造价咨询费计算器" name="second"></el-tab-pane>
        <el-tab-pane label="施工监理费计算器" name="third"></el-tab-pane>
        <el-tab-pane label="审计费计算器" name="fourth"></el-tab-pane>
        <el-tab-pane label="资产评估费计算器" name="fifth"></el-tab-pane>
        <el-tab-pane label="勘察费计算器" name="sixth"></el-tab-pane>
      </el-tabs>
      <div class="tab-content">
        <component :is="currentView">
          <!-- 组件在 vm.currentview 变化时改变！ -->
        </component>
      </div>
    </div>
  </div>
</template>
<script>
// 实例
export default {
  name: 'computer',
  data () {
    return {
      activeName: 'first',
      currentView: 'BidAgentCalculator'
    }
  },
  created () {
  },
  methods: {
    /** tabs切换 */
    handleTabClick (tab, event) {
      // console.log(tab)
      if (tab.name === 'first') {
        this.activeName = 'first'
        this.currentView = 'BidAgentCalculator'
      } else if (tab.name === 'second') {
        this.activeName = 'second'
        this.currentView = 'CostCalculator'
      } else if (tab.name === 'third') {
        this.activeName = 'third'
        this.currentView = 'SuperviseCalculator'
      } else if (tab.name === 'fourth') {
        this.activeName = 'fourth'
        this.currentView = 'AuditCalculator'
      } else if (tab.name === 'fifth') {
        this.activeName = 'fifth'
        this.currentView = 'AssetsCalculator'
      } else if (tab.name === 'sixth') {
        this.activeName = 'sixth'
        this.currentView = 'SurveyCalculator'
      }
    }
  },
  // 实例创建完成
  mounted () {
  },
  // 组件
  components: {
    // 招标代理费计算器
    BidAgentCalculator: resolve => require(['./computer-sub/bid-agent'], resolve),
    // 造价咨询费计算器
    CostCalculator: resolve => require(['./computer-sub/cost-const'], resolve),
    // 施工监理费计算器
    SuperviseCalculator: resolve => require(['./computer-sub/supervise'], resolve),
    // 审计费计算器
    AuditCalculator: resolve => require(['./computer-sub/audit'], resolve),
    // 资产评估计算器
    AssetsCalculator: resolve => require(['./computer-sub/assets'], resolve),
    // 勘察计算器
    SurveyCalculator: resolve => require(['./computer-sub/survey'], resolve),
  }
}
</script>
<style lang="less">
#computer-page {

}
</style>
