
<template>
  <div class="admin-warp">
    <!-- 头部信息 -->
    <adminHead></adminHead>
    <!-- 主体内容 -->
    <div class="admin-cont">
      <el-container style="width:100%;height:100%;">
        <el-row style="width:100%;overflow-y:auto;overflow-x:hidden;">
          <el-col :span="3" style="height:100%;position:fixed;width: 12.5%;">
            <div class="left-menu" style="height:100%">
              <el-menu
                :default-active="activeNav"
                :router="true"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                background-color="#272B34"
                text-color="#fff"
                active-text-color="#ffd04b">
                <el-menu-item index="/admin/resource/intermange" class="menu-fisrt" v-if="$store.getters.permissions.includes('/resource/intermange')">
                  <i class="iconfont mqh-icon-infoedit" style="font-size:26px"></i>
                  <span slot="title">中介机构管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/resource/expertmange" class="menu-fisrt" v-if="$store.getters.permissions.includes('/resource/expertmange')">
                  <i class="iconfont mqh-icon-profilesetting" style="font-size:26px"></i>
                  <span slot="title">评标专家管理</span>
                </el-menu-item>
              </el-menu>
            </div>
          </el-col>
          <el-col :span="21" style="min-height:100%;background:#EAEDF2;width:87.5%;float:right;">
            <!-- 子页面区域 -->
            <router-view></router-view>
          </el-col>
        </el-row>
      </el-container>
    </div>
    <!-- 主体内容结束 -->
  </div>
</template>

<script>
// 引入头部
import adminHead from './../../head/header.vue'
export default {
  name: 'leftmenu',
  data () {
    return {
      activeNav: '',
      isCollapse: false,
      adminValue: false
    }
  },
  computed: {},
  created () {
  },
  watch: {
  },
  methods: {
    handleOpen () {},
    handleClose () {},
    // 设置左侧导航最小高度
    setLeftMenu () {
      // 获得屏幕的高度
      let screenH = document.body.clientHeight
      // 获得左侧ul
      let leftUl = document.getElementsByClassName('el-menu-vertical-demo')
      // 设置左侧ul的高度
      leftUl[0].style.minHeight = (screenH - 80) + 'px'
    }
  },
  mounted () {
    this.setLeftMenu()
    if (this.$route.matched.length > 1) {
      this.activeNav = this.$route.matched[1].path
    }
  },
  // 组件
  components: {
    // 头部
    adminHead
  }
}
</script>
<style lang="less" scoped>
@import url('../../../../common/less/theme/color.less');
.admin-warp {
  height: 100%;
}
.admin-cont {
  height: 100%;
}
.el-menu-vertical-demo.el-menu {
  min-height: 905px;
  height: 100%;
  text-align: left;
  padding-left: 0;
  .el-menu-item {
    min-width:15px;
  }
  li {
    border-bottom: 1px solid #30343D;
  }
  // 一级导航
  .menu-fisrt.is-active {
    background-color: rgb(82,114,247) !important;
    color: #fff !important;
  }
  // 二级导航
  .is-opened, .is-active {
    ul li {
      background-color: rgb(55,54,65) !important;
    }
    ul li:hover {
      background-color: rgb(82,114,247) !important;
    }
    .is-active {
      background-color: rgb(82,114,247) !important;
      color: #fff !important;
    }
  }
}
.grid-content {
  width: 100%;
  height: auto;
}
</style>
