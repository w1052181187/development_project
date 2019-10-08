<template>
  <div id="process" class="process">
    <div class="top">
      <el-button type="text">&lt; 首页</el-button>
      <el-button class="lookbtn" type="primary"><img src="../../../../static/images/system-quantybill/ico2.png"/> 查看标书</el-button>
    </div>
    <div class="processmain">
      <step :type="type" :currentActive="currentActive"></step>
      <!--<bidinfo v-if="type === '1'&& currentActive === 2" ref="bidinfo" v-on:childSubValue="childSubValue"></bidinfo>-->
      <!--<tenderinfo v-if="type === '2'&& currentActive === 1"></tenderinfo>-->
      <!--<bidpriceinfo v-if="type === '3'&& currentActive === 1"></bidpriceinfo>-->
      <!--<checkbid v-if="type === '1'&& currentActive === 3"></checkbid>-->
      <!--<checkunbid v-if="type !== '1'&& currentActive === 3"></checkunbid>-->
      <!--<treebidphase :columns="columns" :tree-structure="true" :data-source="dataSource" v-if="type === '1'&& currentActive === 1"></treebidphase>-->
      <!--<treeunitproject :columns="columns2" :tree-structure="true" :data-source="dataSource2" v-if="type !== '1'&& currentActive === 2"></treeunitproject>-->
      <!--<tenderbook v-if="currentActive === 4"></tenderbook>-->
      <component :is="currentView" v-on:childSubValue="childSubValue" ref="childNode" :columns="columns2" :tree-structure="true" :data-source="dataSource2">
        <!-- 组件在 vm.currentview 变化时改变！ -->
      </component>
    </div>
    <div class="process_button">
      <el-button type="primary" v-if="currentActive !== 1" @click="prevStep">上一步</el-button>
      <el-button type="primary" v-if="currentActive !== 4" @click="nextStep">下一步</el-button>
      <el-button type="primary" v-if="currentActive === 4">生成标书</el-button>
      <el-button type="primary" v-if="currentActive === 4">查看报表</el-button>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      currentActive: 1,
      // 步骤条
      type: this.$route.query.type,
      currentView: '',
      columns: [
        {
          text: '工程编码',
          dataIndex: 'num'
        }, {
          text: '工程名称',
          dataIndex: 'name'
        }, {
          text: '工程级别',
          dataIndex: 'age'
        }
      ],
      dataSource: [{
        id: 1,
        parentId: 0,
        num: '01',
        name: '测试1',
        age: '标段',
        children: [{
          id: 2,
          parentId: 1,
          num: '0101',
          name: '测试1-2',
          age: '单项',
          children: [{
            id: '1-2-1',
            parentId: 2,
            num: '010101',
            name: '测试1-2-1',
            age: '单位'
          }, {
            id: '1-2-2',
            parentId: 2,
            num: '010102',
            name: '测试1-2-2',
            age: '单位'
          }]
        }, {
          id: 3,
          parentId: 1,
          num: '0102',
          name: '测试1-3',
          age: '单项',
          children: [{
            id: '1-3-1',
            parentId: 3,
            num: '010201',
            name: '测试1-3-1',
            age: '单位'
          }, {
            id: '1-3-2',
            parentId: 3,
            num: '010202',
            name: '测试1-3-2',
            age: '单位'
          }]
        }]
      }],
      columns2: [
        {
          text: '工程名称',
          dataIndex: 'name'
        }, {
          text: '状态',
          dataIndex: 'age'
        }, {
          text: '文件名称',
          dataIndex: 'sex'
        }
      ],
      dataSource2: [{
        id: 1,
        parentId: 0,
        name: '测试1',
        age: '',
        sex: '',
        children: [{
          id: 2,
          parentId: 1,
          name: '测试1-2',
          age: '',
          sex: '',
          children: [{
            id: '1-2-1',
            parentId: 2,
            name: '测试1-2-1',
            age: '未导入',
            sex: '文件名称'
          }, {
            id: '1-2-2',
            parentId: 2,
            name: '测试1-2-2',
            age: '未导入',
            sex: '文件名称'
          }]
        }, {
          id: 3,
          parentId: 1,
          name: '测试1-3',
          age: '',
          sex: '',
          children: [{
            id: '1-3-1',
            parentId: 3,
            name: '测试1-3-1',
            age: '未导入',
            sex: '文件名称'
          }, {
            id: '1-3-2',
            parentId: 3,
            name: '测试1-3-2',
            age: '未导入',
            sex: '文件名称'
          }]
        }]
      }],
      isSubmit: ''
    }
  },
  /** 注册组件 */
  components: {
    // 步骤条
    step: resolve => require(['./step-menu'], resolve),
    // 填写招标信息
    bidinfo: resolve => require(['./bid-info'], resolve),
    // 填写投标信息
    tenderinfo: resolve => require(['./tender-info'], resolve),
    // 填写招标控制价信息
    bidpriceinfo: resolve => require(['./bidprice-info'], resolve),
    // 招标标书检查
    checkbid: resolve => require(['./check-bid'], resolve),
    // 非招标标书检查
    checkunbid: resolve => require(['./check-unbid'], resolve),
    // 标段工程导入方式
    treebidphase: resolve => require(['./tree-bidphase'], resolve),
    // 单位工程导入方式
    treeunitproject: resolve => require(['./tree-unitproject'], resolve),
    // 生成标书
    tenderbook: resolve => require(['./tender-book'], resolve)
  },
  methods: {
    childSubValue (childSubValue) {
      // childSubValue就是子组件传过来的值
      this.isSubmit = childSubValue
    },
    // 懒加载步骤 根据type不一样，默认显示不同组件
    showComponents () {
      if (this.type === '1') {
        this.currentView = 'treebidphase'
      }
      if (this.type === '2') {
        this.currentView = 'tenderinfo'
      }
      if (this.type === '3') {
        this.currentView = 'bidpriceinfo'
      }
    },
    // 下一步
    nextStep () {
      if (this.type === '1') {
        this.currentActive++
        if (this.currentActive === 2) {
          this.currentView = 'bidinfo'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
      if (this.type === '2') {
        this.currentActive++
        if (this.currentActive === 2) {
          this.currentView = 'treeunitproject'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkunbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
      if (this.type === '3') {
        this.currentActive++
        if (this.currentActive === 2) {
          this.currentView = 'treeunitproject'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkunbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
    },
    // 上一步
    prevStep () {
      if (this.type === '1') {
        this.currentActive--
        if (this.currentActive === 2) {
          this.currentView = 'bidinfo'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
      if (this.type === '2') {
        this.currentActive--
        if (this.currentActive === 2) {
          this.currentView = 'treeunitproject'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkunbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
      if (this.type === '3') {
        this.currentActive--
        if (this.currentActive === 2) {
          this.currentView = 'treeunitproject'
        }
        if (this.currentActive === 3) {
          this.currentView = 'checkunbid'
        }
        if (this.currentActive === 4) {
          this.currentView = 'tenderbook'
        }
      }
    }
  },
  created () {
    this.showComponents()
  }
}
</script>
<style lang="less">
  #process{
    width: 1200px;
    min-height: 300px;
    background: #ffffff;
    margin: 20px auto;
    padding-bottom: 50px;
    .top{
      width: 98%;
      padding: 0 1%;
      height: 62px;
      line-height: 62px;
      border-bottom: 1px solid #eeeeee;
    }
    .top .el-button--text span{
      font-size: 16px;
    }
    .top .el-button--primary{
      margin-top: 11px;
      float: right;
      background-color: #ff8a00;
      border-color: #ff8a00;
    }
    .process_button{
      text-align: center;
    }
  }
</style>
