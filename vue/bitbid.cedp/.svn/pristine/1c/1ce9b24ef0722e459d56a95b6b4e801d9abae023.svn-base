<template>
  <div class="home" id="home">
    <div v-if="$route.meta.layout === 'purchaser'">
      <el-row>
        <topHeadPurchaser></topHeadPurchaser>
      </el-row>
      <el-row style="margin: 0;">
        <el-col :span="leftnum" class="col2">
          <!-- 左侧导航栏 -->
          <sliderPurchaser></sliderPurchaser>
        </el-col>
        <el-col :span='rightnum' class="col22">
          <div :class="show ? 'menu_switch menu_switch_close': 'menu_switch menu_switch_open'" @click="menuToggle"></div>
          <div class="content-wrap" id="content-wrap">
            <router-view></router-view>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-else>
      <el-row>
        <topHeadSupplier></topHeadSupplier>
      </el-row>
      <el-row style="margin: 0;">
        <el-col :span="leftnum" class="col2">
          <!-- 左侧导航栏 -->
          <sliderSupplier></sliderSupplier>
        </el-col>
        <el-col :span='rightnum' class="col22">
          <div :class="show ? 'menu_switch menu_switch_close': 'menu_switch menu_switch_open'"></div>
          <div class="content-wrap" id="content-wrap">
            <router-view></router-view>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import topHeadPurchaser from '@/components/header.vue'
import sliderPurchaser from '@/components/slider.vue'
import sliderSupplier from '@/components/slider_green.vue'
import topHeadSupplier from '@/components/header_green.vue'
export default {
  name: 'Home',
  components: {
    sliderPurchaser,
    topHeadPurchaser,
    sliderSupplier,
    topHeadSupplier
  },
  data () {
    return {
      leftnum: 2,
      rightnum: 22,
      show: true
    }
  },
  methods: {
    setHeight () {
      // 获得屏幕的高度
      let screenH = document.body.clientHeight
      // 获得右侧内容区
      let appWrap = document.getElementsByClassName('content-wrap')
      // 设置右侧内容区的高度
      appWrap[0].style.height = (screenH - 82) + 'px'
    },
    handleScroll () {
      document.getElementById('content-wrap').scrollTop = 0
    },
    menuToggle () {
      if (this.show) {
        this.leftnum = 0
        this.rightnum = 24
        this.show = false
      } else {
        this.leftnum = 2
        this.rightnum = 22
        this.show = true
      }
    }
  },
  mounted () {
    this.handleScroll()
    this.setHeight()
  },
  computed: {},
  watch: {}
}
</script>
<style lang='less'>
  body,p,h1,h2,h3,h4,h5,h6,ul,li,ol,dl,dt,dd,input,form{
    margin:0;
    list-style:none;/*清除小圆点*/
    padding:0px;
    color: #333333;
  }
  body,html{
    height: 100%;
  }
  /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
  ::-webkit-scrollbar
  {
    width: 5px;
    height: 5px;
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track
  {
    background-color: #b8b8b8;
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb
  {
    border-radius: 5px;
    -webkit-box-shadow: inset 0 0 2px rgba(0,0,0,0.3);
    background-color: #cccccc
  }
  #home{
    .col2{
      position: fixed;
      height: 100%;
      padding: 0!important;
    }
    .col22{
      float: right;
      position: relative;
    }
    .app-wrap{
      margin-top: 48px;
    }
    .content-wrap{
      margin: 10px 10px 0 20px;
      overflow-y: auto;
      overflow-x: hidden;
    }
    .menu_switch{
      width: 9px;
      height: 55px;
      position:absolute;
      left: 0px;
      top: 50%;
      margin-top: -22px;
      cursor: pointer;
    }
    .menu_switch_open{
      background: url("../../static/images/menu_switch_open.png") no-repeat;
    }
    .menu_switch_close{
      background: url("../../static/images/menu_switch_close.png") no-repeat;
    }
  }
</style>
