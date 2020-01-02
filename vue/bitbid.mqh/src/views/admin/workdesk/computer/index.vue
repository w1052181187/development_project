<template>
  <div class="calculator-box" id="calculator">
    <div class="workdesk-mqh-tit">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">当前位置:首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/admin/calculator' }">计算器</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tab-menu">
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="科学计算器" name="first"></el-tab-pane>
        <el-tab-pane label="招标代理费计算器" name="second"></el-tab-pane>
        <el-tab-pane label="造价咨询费计算器" name="third"></el-tab-pane>
        <el-tab-pane label="施工监理费计算器" name="fourth"></el-tab-pane>
        <!-- <el-tab-pane label="审计费计算器" name="fifth"></el-tab-pane>
        <el-tab-pane label="资产评估费计算器" name="sixth"></el-tab-pane>
        <el-tab-pane label="勘察设计费计算器" name="seventh"></el-tab-pane> -->
      </el-tabs>
      <div class="btn-change">
        <el-button type="text" @click="changePattern">{{showBtnValue?'煤气化模式':'其他模式'}}</el-button>
      </div>
    </div>
    <div class="tab-content">
      <component :is="currentView" :parentsToChild="currentModel">
        <!-- 组件在 vm.currentview 变化时改变！ -->
      </component>
    </div>
  </div>
</template>
<script>
import ScienceCalculator from '@/components/computer/computer.vue'
export default {
  name: 'computer',
  data () {
    return {
      currentModel: '1',
      activeName: 'second',
      showBtnValue: true,
      searchValue: '',
      currentView: 'BidAgentCalculator'
    }
  },
  // 实例创建
  created () {},
  methods: {
    /** 模式切换 */
    changePattern () {
      this.showBtnValue = !this.showBtnValue
      this.currentModel = this.showBtnValue ? '1' : '2'
    },
    /** tabs切换 */
    handleTabClick (tab, event) {
      // console.log(tab)
      if (tab.name === 'first') {
        this.currentView = 'ScienceCalculator'
      } else if (tab.name === 'second') {
        this.currentView = 'BidAgentCalculator'
      } else if (tab.name === 'third') {
        this.currentView = 'CostCalculator'
      } else if (tab.name === 'fourth') {
        this.currentView = 'SuperviseCalculator'
      }
    }
  },
  // 组件
  components: {
    // 科学计算器
    ScienceCalculator: ScienceCalculator,
    // 招标代理费计算器
    BidAgentCalculator: resolve => require(['./bidagent'], resolve),
    // 造价咨询费计算器
    CostCalculator: resolve => require(['./costconsult'], resolve),
    // 施工监理费计算器
    SuperviseCalculator: resolve => require(['./supervise'], resolve)
  }
}
</script>
<style lang="less">
#calculator {
  width: 1200px;
  margin: auto;
  margin-top: 80px;
  .workdesk-mqh-tit {
    padding: 15px 0;
  }
  .tab-menu {
    position: relative;
    background: #fff;
    .btn-change {
      position: absolute;
      top: 0;
      right: 0;
      margin-right: 20px;
    }
    .el-tabs__nav {
      margin-left: 20px;
    }
    .el-tabs__item.is-active {
      border: 0;
    }
    .tabsearch-box {
      text-align: left;
      padding-left: 20px;
      padding-bottom: 20px;
      .input-with-search {
        width: 260px;
      }
    }
  }
}
</style>
