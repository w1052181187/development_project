<template>
  <div id="index">
    <commonheader></commonheader>
    <el-row style="margin-top:50px;position:absolute;width:100%;height:100%;overflow:auto;">
      <leftMenu></leftMenu>
      <el-col
        :span="21"
        style="height:100%;padding-bottom: 50px;"
        :class="{'marginLeftA': isA, 'marginLeftB': isB, 'marginLeftC': isC}">
        <div class="content-warp">
          <router-view/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import leftMenu from '../components/left.vue'
import commonheader from '../components/header.vue'
// import commonfooter from '../components/footer.vue'
export default {
  components: {
    leftMenu,
    commonheader
  },
  data () {
    return {
      isA: false,
      isB: false,
      isC: false
    }
  },
  wacth: {
    marginLeft (newV, oldV) {
      if (newV) {
        this.getLeftMenuWidth()
      }
    }
  },
  methods: {
    // 获取左侧导航的宽度
    getLeftMenuWidth () {
      let marginLeft = document.getElementsByClassName('el-scrollbar')[0].offsetWidth
      if (marginLeft > 200) {
        this.isA = true
        this.isB = false
        this.isC = false
      } else if (marginLeft === 200) {
        this.isA = false
        this.isB = true
        this.isC = false
      } else if (marginLeft < 200) {
        this.isA = false
        this.isB = false
        this.isC = true
      }
    }
  },
  mounted () {
    this.getLeftMenuWidth()
  }
}
</script>
<style lang="less">
  @import '../assets/css/common.css';
  #index {
    color: #2c3e50;
    background:#fff;
    height: 100%;
    .el-col-21{
      float: right;
      position: relative;
    }
    .el-row-width {
      width: 100%;
    }
    .marginLeftA {
      margin-left: 240px;
    }
    .marginLeftB {
      margin-left: 200px;
    }
    .marginLeftC {
      margin-left: 170px;
    }
    .sidebox{
      border: none;
    }
    .indexs{
      text-align: left;
    }
    .el-main{
      padding: 0;
    }
    .content-warp {
      height: 100%;
      overflow: auto;
    }
  }
</style>
