<template>
  <div class="menu_green" id="menu_green">
    <div class="menu_box">
      <el-menu
        :default-active="activeNav"
        class="slider"
        text-color="#fff"
        :router="true"
      >
        <el-submenu v-for="(item,index) in menuList" :key="index" :index='item.id'>
          <template slot="title">
            <span :class="item.picname">{{item.title}}</span>
          </template>
          <el-menu-item-group >
            <el-menu-item v-for="child in item.children" :key="child.id" :index='child.index'>
              {{child.childtitle}}
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Slider',
  data () {
    return {
      activeNav: '',
      adminEnterpriseIds: [127658],
      menuList: [
        {
          id: '1',
          title: '首页',
          picname: 'leftico_home',
          children: [
            {
              index: '/project',
              childtitle: '项目总览',
              moduleId: 201
            },
            {
              index: '/info',
              childtitle: '信息总览',
              moduleId: 201
            },
            {
              index: '/data',
              childtitle: '数据统计',
              moduleId: 201
            }
          ]
        },
        {
          id: '3',
          title: '项目管理',
          picname: 'leftico_pro',
          children: [
            {
              index: '/project',
              childtitle: '采购意向'
            },
            {
              index: '/supplier/directPurchase',
              childtitle: '直接采购'
            },
            {
              index: '/data',
              childtitle: '招标采购'
            },
            {
              index: '/data',
              childtitle: '询价采购'
            },
            {
              index: '/data',
              childtitle: '竞价采购'
            }
          ]
        },
        {
          id: '4',
          title: '企业管理',
          picname: 'leftico_com',
          children: [
            {
              index: '/supInfoManage',
              childtitle: '信息管理'
            },
            {
              index: '/info',
              childtitle: '组织结构'
            },
            {
              index: '/data',
              childtitle: '人员管理'
            },
            {
              index: '/data',
              childtitle: '权限管理'
            }
          ]
        }
      ]
    }
  },
  methods: {
    setActiveNav () {
      document.getElementById('content-wrap').scrollTop = 0
      if (this.$route.meta) {
        this.activeNav = this.$route.meta.active
      }
    },
    setHeight () {
      // 获得屏幕的高度
      let screenH = document.body.clientHeight
      // 获得右侧内容区
      let navWrap = document.getElementsByClassName('menu_box')
      // 设置右侧内容区的高度
      navWrap[0].style.height = (screenH - 82) + 'px'
    }
  },
  watch: {
    '$route': 'setActiveNav'
  },
  mounted () {
    this.setActiveNav()
    this.setHeight()
  }
}
</script>
<style lang='less'>
  #menu_green{
    width: 100%;
    background: #e5e5e5;
    padding-top: 10px;
    position: relative;
    .menu_box{
      overflow-x: hidden;
      overflow-y: auto;
      width: 100%;
      position: relative;
    }
    .slider{
      background: #e5e5e5;
      color: #333333;
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
    }
    /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
    .slider::-webkit-scrollbar
    {
      width: 8px;
      height: 8px;
      background-color: #F5F5F5;
    }

    /*定义滚动条轨道 内阴影+圆角*/
    .slider::-webkit-scrollbar-track
    {
      -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
      border-radius: 10px;
      background-color: #F5F5F5;
    }

    /*定义滑块 内阴影+圆角*/
    .slider::-webkit-scrollbar-thumb
    {
      border-radius: 10px;
      -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
      background-color: #cccccc}
    .el-menu{
      background: #e5e5e5;
    }
    .el-submenu__title, .el-menu-item{
      color: #333333!important;
    }
    .el-submenu__title:hover{
      color: #ffffff!important;
      background: #009688;
    }
    .el-menu-item, .el-submenu__title{
      height: 36px;
      line-height: 36px;
      background: none;
    }
    .el-menu-item:hover,.is-active{
      color: #ffffff!important;
      background: #009688;
    }
    .is-opened .el-submenu__title{
      color: #ffffff!important;
      background: #009688;
    }
    .el-icon-arrow-down:before{
      color: #3d3d3d;
    }
    .is-opened .el-icon-arrow-down:before,.el-submenu__title:hover .el-icon-arrow-down:before{
      color: #ffffff;
    }
    .leftico_home{
      background: url("../../static/images/leftico_home.png") left center no-repeat;
      padding-left: 17px;
    }
    .el-submenu__title:hover .leftico_home,.is-opened .leftico_home{
      background: url("../../static/images/leftico_home_h.png") left center no-repeat;
      padding-left: 17px;
    }
    .leftico_plan{
      background: url("../../static/images/leftico_plan.png") left center no-repeat;
      padding-left: 17px;
    }
    .el-submenu__title:hover .leftico_plan,.is-opened .leftico_plan{
      background: url("../../static/images/leftico_plan_h.png") left center no-repeat;
      padding-left: 17px;
    }
    .leftico_pro{
      background: url("../../static/images/leftico_pro.png") left center no-repeat;
      padding-left: 17px;
    }
    .el-submenu__title:hover .leftico_pro,.is-opened .leftico_pro{
      background: url("../../static/images/leftico_pro_h.png") left center no-repeat;
      padding-left: 17px;
    }
    .leftico_com{
      background: url("../../static/images/leftico_com.png") left center no-repeat;
      padding-left: 17px;
    }
    .el-submenu__title:hover .leftico_com,.is-opened .leftico_com{
      background: url("../../static/images/leftico_com_h.png") left center no-repeat;
      padding-left: 17px;
    }
    .menu_switch{
      width: 9px;
      height: 55px;
      position:absolute;
      left: 100%;
      top: 50%;
      margin-top: -22px;
    }
    .menu_switch_open{
      background: url("../../static/images/menu_switch_open.png") no-repeat;
    }
    .menu_switch_close{
      background: url("../../static/images/menu_switch_close.png") no-repeat;
    }
    .el-menu-item-group .el-menu-item-group__title{
      height: 0;
      padding: 3px 0;
    }
  }
</style>
