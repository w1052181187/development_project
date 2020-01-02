
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
                <el-submenu index="2" v-if="$store.getters.permissions.includes('/statistics/coalsafe-report')">
                  <template slot="title">
                    <i class="el-icon-tickets"></i>
                    <span slot="title">计划完成情况上报</span>
                  </template>
                  <el-menu-item
                    class="menu-fisrt"
                    v-for="(item, index) in leftMenuList"
                    :key="index"
                    :index="'/admin/statistics/coalsafe-report?capitalSourceId='+ item.capitalSourceId + '&capitalName=' + item.capitalSourceName" >
                    <label v-if="$store.getters.permissions.includes('/statistics/coalsafe-report')">
                      <span slot="title">{{item.capitalSourceName}}上报</span>
                    </label>
                  </el-menu-item>
                </el-submenu>
                <el-submenu index="1" v-if="$store.getters.permissions.includes('/statistics/coalsafety') || $store.getters.permissions.includes('/statistics/coalsafety-pass')">
                  <template slot="title">
                    <i class="el-icon-tickets"></i>
                    <span slot="title">计划完成汇总表</span>
                  </template>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=1" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">折旧(更改)费汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=2" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">井巷工程费汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=3" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">维简费汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety-pass?typeFlag=4" v-if="$store.getters.permissions.includes('/statistics/coalsafety') || $store.getters.permissions.includes('/statistics/coalsafety-pass')">煤炭安全资金汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=5" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">环境治理恢复基金汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=6" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">危化品安全资金汇总表</el-menu-item>
                  <el-menu-item index="/admin/statistics/coalsafety?typeFlag=7" v-if="$store.getters.permissions.includes('/statistics/coalsafety')">工程建设汇总表</el-menu-item>
                </el-submenu>
                <el-menu-item index="/admin/statistics/coalsafe-use-sum" class="menu-fisrt" v-if="$store.getters.permissions.includes('/statistics/coalsafe-use-sum')">
                  <i class="el-icon-document"></i>
                  <span slot="title">煤安费提取和使用汇总</span>
                </el-menu-item>
                <el-menu-item index="/admin/statistics/platform-sum" class="menu-fisrt" v-if="$store.getters.permissions.includes('/statistics/platform-sum')">
                  <i class="el-icon-document "></i>
                  <span slot="title">平台统计</span>
                </el-menu-item>
                <el-menu-item index="/admin/statistics/bid-statist" class="menu-fisrt" v-if="$store.getters.permissions.includes('/statistics/bid-statist')">
                  <i class="el-icon-document "></i>
                  <span slot="title">招标情况统计</span>
                </el-menu-item>
                <el-menu-item index="/admin/statistics/coalsafe-use" class="menu-fisrt" v-if="$store.getters.permissions.includes('/statistics/coalsafe-use') && $store.getters.authUser.enterpriseClassify === 1">
                  <i class="el-icon-document"></i>
                  <span slot="title">煤安费提取和使用上报</span>
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
import {investmanage} from 'api/index'
export default {
  name: 'leftmenu',
  data () {
    return {
      element: {
        active: '',
        opened: true
      },
      isCollapse: false,
      adminValue: false,
      enterprise: this.$store.getters.authUser,
      leftMenuList: [] // 动态生成左侧菜单
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
    },
    getMenuList () {
      if (Object.is(this.enterprise.enterpriseType, 1)) {
        // 招标办拥有所有资金来源权限
        let query = {
          isAvailableFunds: 1
        }
        investmanage.getCapitalSourceList(query).then(res => {
          let tempResult = res.data.capitalSourceList
          tempResult.map(item => {
            item.capitalSourceId = item.objectId
            item.capitalSourceName = item.name
          })
          this.handleData(tempResult)
        })
      } else {
        let enterpriseClassify = this.enterprise.enterpriseClassify
        if (enterpriseClassify) {
          investmanage.selectCapitalSourceByEnterpriseType(enterpriseClassify).then(res => {
            this.handleData(res.data.enterpriseTypeCapitalSourceList)
          })
        }
      }
    },
    handleData (data) {
      if (data) {
        let tempData = data
        // 临时数组
        let tempArr = []
        // 最终结果
        let resultArr = []
        // 处理数据
        let str = ''
        let filterArr = tempData.filter(item => {
          if (item.capitalSourceName === '未分配利润') {
            return
          } else if (item.capitalSourceName === '股东注资' || item.capitalSourceName === '政府补助' || item.capitalSourceName === '项目借款') {
            str += `${item.capitalSourceId}` + ','
            item.capitalSourceId = 0
            item.capitalSourceName = '工程建设'
            tempArr.push(item)
          }
          return item
        })
        // 去除重复
        resultArr = filterArr.filter(item => {
          if (item.capitalSourceId === 0 && item.capitalSourceName === '工程建设') {
            return
          }
          return item
        })
        // 合并数组
        let strAllObj = tempArr[0].capitalSourceId = `${str}${0}`
        resultArr.push(Object.assign(tempArr[0], {capitalSourceId: strAllObj}))
        this.leftMenuList = resultArr
      }
    }
  },
  mounted () {
    this.getMenuList()
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
