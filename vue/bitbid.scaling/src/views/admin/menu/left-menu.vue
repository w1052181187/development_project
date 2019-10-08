<template>
  <div class="app-box">
    <!-- 头部信息 -->
    <adminHead ></adminHead>
    <div class="left-warp">
      <el-row style="width:100%;height:100%;">
        <el-col :span="3" style="height:100%;position:fixed;">
          <div class="left-menu" style="height:100%">
            <el-menu
              :default-active="activeNav"
              :router="true"
              class="el-menu-vertical-demo"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"
              :unique-opened="true"
              >
              <template v-if="this.userType === 1">
                <el-submenu index="0">
                  <template slot="title">
                    <span>项目信息</span>
                  </template>
                  <el-menu-item-group>
                    <el-menu-item index="/admin/proinfo">项目信息</el-menu-item>
                  </el-menu-item-group>
                </el-submenu>
              </template>
              <div v-for="(menus, index) in menuAggregate"
              :key="index">
                <el-submenu :index="handleIndex(index)" v-if="menus.children" >
                  <template slot="title">
                    <span class="titlesbox" :title="menus.resourceDesc">{{menus.resourceDesc}}</span>
                  </template>
                  <el-menu-item :index="item.resourceUrl" v-for="(item, index) in menus.children" :key="index" :title="item.resourceDesc">{{item.resourceDesc}}</el-menu-item>
                </el-submenu>
                <el-menu-item v-else :index="menus.resourceUrl" :title="menus.resourceDesc">{{menus.resourceDesc}}</el-menu-item>
              </div>
            </el-menu>
          </div>
        </el-col>
        <el-col :span="21" style="height:100%;background:#EAEDF2;float:right;">
          <!-- 子页面区域 -->
          <router-view></router-view>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import adminHead from '@/views/admin/header/header.vue'
import { leftMenu } from '@/api'
export default {
  name: 'leftmenu',
  data () {
    return {
      userId: this.$store.getters.authUser.userId,
      userType: this.$store.getters.authUser.userType,
      // projectId: this.userType === 2 ? this.$store.getters.authUser.uscCode : this.$store.getters.projectId,
      projectId: this.$store.getters.projectId,
      activeNav: '',
      menuAggregate: [],
      resourceList: []
    }
  },
  methods: {
    handleIndex (index) {
      return this.userType === 1 ? (index + 1) + '' : index.toString()
    },
    getMenuList (projectId) {
      if (!projectId) {
        this.menuAggregate = []
        return
      }
      leftMenu.getMenuList(this.userId, projectId).then((res) => {
        if (res.data.resCode === '0000') {
          this.menuAggregate = res.data.ResourceList
          this.menuAggregate.map((its) => {
            if (its.children) {
              its.children.map((ite) => {
                this.resourceList.push(ite.resourceUrl)
              })
            }
            if (its.resourceUrl) {
              this.resourceList.push(its.resourceUrl)
            }
          })
          this.handleMenuChange()
        }
      })
    },
    handleMenuChange () {
      let curPath = this.$route.fullPath
      let keys = this.resourceList
      let len = keys.length
      if (len <= 0) {
        return
      }
      if (curPath === keys[0]) {
        this.activeNav = curPath
        return
      }
      for (let i = 1; i < len; i++) {
        if (curPath.indexOf(keys[i]) !== -1) {
          this.activeNav = keys[i]
          // return
        }
      }
    }
  },
  mounted () {
    this.getMenuList(this.projectId)
  },
  watch: {
    '$store.getters.projectId': function () {
      let projectId = this.$store.getters.projectId
      this.getMenuList(projectId)
    },
    '$store.getters.isRefreshMenu': function () {
      let isRefreshMenu = this.$store.getters.isRefreshMenu
      if (isRefreshMenu) {
        this.$store.commit('SET_IS_REFRESH_MENU', false)
        let projectId = this.projectId
        this.getMenuList(projectId)
      }
    },
    $route: function () {
      this.handleMenuChange()
    }
  },
  components: {
    // 头部
    adminHead
  }
}
</script>
<style lang="scss">
.left-warp {
  height: 100%;
  margin-top: 60px;
}
.left-warp .left-menu {
  background: #393C4F;
}
.el-menu-item-group__title {
  padding: 0 ;
}
.el-menu-vertical-demo.el-menu li {
  // border-bottom: 0 !important;
}
.el-menu-vertical-demo.el-menu {
  // height: 730px;
  height: 94%;
  text-align: left;
  padding-left: 0;
  overflow: auto;
  .el-menu-item {
    min-width:15px;
  }
  // 一级导航
  .menu-fisrt.is-active {
    background-color: rgb(55,54,65) !important;
    color: #fff !important;
  }
  // 二级导航
  .is-opened, .is-active {
    ul li {
      background-color: rgb(55,54,65) !important;
    }
    ul li:hover {
      background-color: rgb(0,0,0) !important;
    }
    .is-active {
      background-color: rgb(55,54,65) !important;
      color: #fff !important;
    }
  }
   /*选中样式*/
  .el-menu-item.is-active{
    background:#333!important;
    border-left: 4px solid #ff8a00;
    box-sizing: border-box;
    color:#fff!important;
  }
}
</style>
