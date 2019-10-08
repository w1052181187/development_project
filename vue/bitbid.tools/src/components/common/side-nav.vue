<template>
  <div id="side_nav_bigbox">
    <div class="logo_box">
      <img src="../../../static/img/logo.png" alt="">
    </div>
    <div class="side_bigbox">
      <el-scrollbar style="height: 100%;">
        <el-menu
          ref="leftMenu"
          :default-active="activeNav"
          class="el-menu-vertical-demo"
          background-color="#393c4f"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
          :unique-opened="true">
          <el-submenu :index="index.toString()" v-for="(menus,index) in menuAggregate" :key="index">
            <template slot="title">
              <span class="iconimg">
                <img :src="'../../../static/img/' + menus.resourceIcon" alt="">
              </span>
              <span class="titlesbox" :title="menus.resourceDesc">{{menus.resourceDesc}}</span>
            </template>
            <el-menu-item :index="childrens.resourceUrl" v-for="(childrens,index) in menus.children" :key="index" :title="childrens.resourceDesc">{{childrens.resourceDesc}}</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-scrollbar>
    </div>
  </div>
</template>
<script>
import { menu } from '@/api'
export default {
  data () {
    return {
      activeNav: '',
      menuAggregate: [],
      childrens: [],
      childrensDesc: []
    }
  },
  methods: {
    setLeftMenu () {
      // 获得屏幕的高度
      let screenH = document.body.clientHeight
      // 获得左侧ul
      let leftUl = document.getElementsByClassName('el-menu-vertical-demo')
      // 设置左侧ul的高度
      leftUl[0].style.minHeight = (screenH - 50) + 'px'
    },
    menuLists () {
      menu.menuList(this.$store.getters.authUser.userId).then((res) => {
        this.menuAggregate = res.data.BidOpenResourceList
        this.menuAggregate.map((its) => {
          its.children.map((ite) => {
            this.childrens.push(ite.resourceUrl)
            this.childrensDesc.push(ite.resourceDesc)
          })
        })
        this.$store.commit('SET_ROUTE_LIST', this.childrensDesc)
        this.handleMenuChange()
      })
    },
    handleMenuChange () {
      let curPath = this.$route.path
      let keys = this.childrens
      let len = keys.length
      if (len <= 0) {
        return
      }
      if (curPath === keys[0]) {
        this.activeNav = curPath
        this.$store.commit('SET_CUR_ROUTE_INDEX', 0)
        return
      }
      for (let i = 1; i < len; i++) {
        if (curPath.indexOf(keys[i]) !== -1) {
          this.activeNav = keys[i]
          this.$store.commit('SET_CUR_ROUTE_INDEX', i)
          return
        }
      }
    }
  },
  watch: {
    '$store.getters.releaseStatus': function () {
      this.menuLists()
    },
    $route: function () {
      this.handleMenuChange()
    }
  },
  mounted () {
    this.menuLists()
    this.setLeftMenu()
  }
}
</script>
<style>
</style>
