<template>
  <div id="stepmenu" class="stepmenu">
    <div class="flow_steps">
      <ul>
        <li :class="(currentActive===1)? 'current' : (currentActive===2)?'current_prev':'done'">{{firstStep}}</li>
        <li :class="(currentActive===1)? '' : (currentActive===2)?'current':(currentActive===2)?'current_prev':'done'">2.标书检查</li>
        <li :class="(currentActive===1)? '' : (currentActive===2)?'':(currentActive===3)?'current':'current_prev'">{{thirdStep}}</li>
        <li :class="(currentActive===4)? 'last current' :'last'">4.生成标书</li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      // active: 0,
      firstStep: '',
      thirdStep: ''
    }
  },
  props: {
    // 父组件传值
    type: Number,
    currentActive: Number
  },
  created () {
    this.changeStepVal()
  },
  methods: {
    changeStepVal () {
      if (Object.is(this.type, 1)) {
        this.firstStep = '1. 导入招标数据'
        this.thirdStep = '3. 填写招标信息'
      }
      if (Object.is(this.type, 2)) {
        this.firstStep = '1. 导入投标数据'
        this.thirdStep = '3. 填写投标信息'
      }
      if (Object.is(this.type, 3)) {
        this.firstStep = '1. 导入招标控制价数据'
        this.thirdStep = '3. 填写招标控制价信息'
      }
    }
  }
}
</script>
<style lang="less">
  #stepmenu{
    width: 1160px;
    overflow: hidden;
    margin: 30px auto;
    .flow_steps ul li { float:left; height:50px;line-height: 50px; width: 220px;padding:0 40px 0 30px;color: #333333;  text-align:center; background:url(../../../../static/images/system-quantybill/step.png) no-repeat 100% 0 #eceff4; }
    .flow_steps ul li.done { background-position:100% -100px; background-color:#3f63f6;
      color: #ffffff;}
    .flow_steps ul li.current_prev { background-position:100% -50px; background-color:#3f63f6; color: #ffffff;}
    .flow_steps ul li.current { color:#fff; background-color:#3f63f6;}
    .flow_steps ul li.last { background-image:none;}
  }
</style>
