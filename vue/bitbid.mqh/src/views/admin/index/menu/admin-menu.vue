
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
                :unique-opened="true"
                :default-active="element.active"
                :router="true"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                background-color="#272B34"
                text-color="#fff"
                active-text-color="#ffd04b">
                <el-menu-item index="/admin/infoset" class="menu-fisrt">
                  <i class="el-icon-document"></i>
                  <span slot="title">信息设置</span>
                </el-menu-item>
                <el-submenu index="2">
                  <template slot="title">
                    <i class="el-icon-location"></i>
                    <span slot="title">组织机构</span>
                  </template>
                  <el-menu-item index="/admin/enterprise">集团公司管理</el-menu-item>
                  <el-menu-item index="/admin/branchcomp">供销分公司管理</el-menu-item>
                  <el-menu-item index="/admin/childcomp">二级公司管理</el-menu-item>
                  <el-menu-item index="/admin/tendagency">招标机构管理</el-menu-item>
                  <el-menu-item index="/admin/roles">角色管理</el-menu-item>
                  <el-menu-item index="/admin/users">用户管理</el-menu-item>
                </el-submenu>
                <el-submenu index="3">
                  <template slot="title">
                    <i class="el-icon-setting"></i>
                    <span slot="title">系统管理</span>
                  </template>
                  <el-menu-item index="/system/diction">数据字典管理</el-menu-item>
                  <el-menu-item index="/system/interface">接口授权管理</el-menu-item>
                  <el-menu-item index="/system/notice">公告通知</el-menu-item>
                  <el-menu-item index="/system/link">友情链接维护</el-menu-item>
                  <el-menu-item index="/system/config">业务配置</el-menu-item>
                  <el-menu-item index="/system/computer">计算器配置</el-menu-item>
                  <el-menu-item index="/system/industry">行业资讯管理</el-menu-item>
                  <el-menu-item index="/system/report">报表统计开始日期</el-menu-item>
                </el-submenu>
                <el-menu-item index="/system/journal" class="menu-fisrt">
                  <i class="el-icon-document"></i>
                  <span slot="title">操作日志</span>
                </el-menu-item>
              </el-menu>
            </div>
          </el-col>
          <el-col :span="21" style="min-height:100%;background:#EAEDF2;widht:87.5%;float: right;">
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
      element: {
        active: '',
        opened: true
      },
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
    this.element.active = this.$route.matched[1].path
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
