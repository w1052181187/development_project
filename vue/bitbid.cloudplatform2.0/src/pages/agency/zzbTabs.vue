<!--tabs切换组件-->
<template>
  <div class="zzb-tabs">
    <el-tabs v-model="state" type="card" @tab-click="handleClick">
      <el-tab-pane v-for="item in tabList" :label="stateNameFn(item)" :name="'tabs'+item.state" :key="item.state"></el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  props: {
    /*
       *tab数组
       *{
            stateName:"",   tab显示名称
            count:0,    	tab对应的表格数据条数
            state:0,    	tab状态
        }
       */
    tabList: {
      type: Array,
      default() {
        return [];
      }
    },
    currentState: {
      default: null
    }
  },
  computed() {
    // stateNameFn: {
    //   //item.stateName+'('+ item.count+')'
    //   return '全bu';
    // }
  },
  data() {
    return {
      state: 'tabs'
      // tabList:[
      // 	{stateName : '张三', state: null, count: 20},
      // 	{stateName : '李四', state: '1', count: 30},
      // 	{stateName : '王五', state: '2', count: 2},
      // 	{stateName : '马六', state: '3', count: 10},
      // ]
    };
  },
  created() {
    this.state = `tabs${this.currentState}`;
  },
  methods: {
    /**
     * 点击函数返回state状态
     */
    handleClick(tab, event) {
      let data = {
        state: null
      };
      data.state = tab.name.substring(4);
      this.state = data.state;
      this.state = `tabs${data.state}`;
      this.$emit('clicktab', data);
    },
    /**
     * 计算是否有条数
     */
    stateNameFn(item) {
      let name = item.stateName;
      if (item.count && item.count > 0) {
        name = `${item.stateName}(${item.count})`;
      }
      return name;
    }
  },
  watch: {
    currentState() {
      this.state = `tabs${this.currentState}`;
    }
  }
};
</script>

<style lang="scss" scoped>
.zzb-tabs {
  padding: 12px 0 0 0;
  .zzb-line {
    width: 100%;
    height: 12px;
    border-left: 1px solid #d5e0ed;
    border-right: 1px solid #d5e0ed;
  }
}
</style>