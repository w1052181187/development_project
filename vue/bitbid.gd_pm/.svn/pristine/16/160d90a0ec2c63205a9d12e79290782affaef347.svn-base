<template>
  <div id="big_box">
    <el-col :span="3" class="left_con_box">
      <div class="logo_box" style="background-color: #1b315a;">
        <img src="../../../../static/img/admin_logo.png" alt="logo">
      </div>
      <div class="side_bigbox" style="background-color: #1b315a;height:100%;">
        <el-scrollbar style="height: 100%;">
          <el-menu
            ref="leftMenu"
            :default-active="activeNav"
            class="el-menu-vertical-demo"
            background-color="#393c4f"
            text-color="#bcdcff"
            active-text-color="#ffd04b"
            :router="true"
            :unique-opened="true">
            <el-submenu :index="item.index" class="side_nav" v-for="(item, index) in menuData" :key="index">
              <template slot="title">
                <span class="iconimg">
                  <img :src="item.src" alt="pic">
                </span>
                <span class="titlesbox" :title="item.title">{{item.title}}</span>
              </template>
              <el-menu-item :index="child.src" :title="child.title" v-for="(child, index) in item.childMenu" :key="index">
                {{child.title}}
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-scrollbar>
      </div>
    </el-col>
    <el-col :span="21" style="background:#f1f1f1;float:right;">
      <!--头部-->
      <headers></headers>
      <!--头部-->
      <!-- 子页面区域 -->
      <router-view></router-view>
      <!-- 子页面区域 -->
      <!--底部-->
      <div class="footer_box">广东比比信息技术服务有限公司 版权所有</div>
      <!--底部-->
    </el-col>
  </div>
</template>
<script>
import headers from './header.vue'
export default {
  components: {
    headers
  },
  data () {
    return {
      activeNav: '',
      // 代理侧边导航数据
      agencyNav: [
        {
          index: '1',
          src: '../../../../static/img/hetong.png',
          title: '企业信息',
          childMenu: [
            {
              title: '企业信息',
              src: '/admin/enterprise/index'
            }
          ]
        },
        {
          index: '2',
          src: '../../../../static/img/shoukuan.png',
          title: '收款账号',
          childMenu: [
            {
              title: '收款账号',
              src: '/admin/collection/index'
            }
          ]
        },
        {
          index: '3',
          src: '../../../../static/img/jyjl.png',
          title: '交易记录',
          childMenu: [
            {
              title: 'CA记录',
              src: '/admin/record/ca/index'
            },
            {
              title: '平台使用费',
              src: '/admin/record/platform/index'
            },
            {
              title: '标书费',
              src: '/admin/record/tender-fee/index'
            },
            {
              title: '中标服务费',
              src: '/admin/record/winning/index'
            },
            {
              title: '交易记录',
              src: '/admin/record/settlement/index'
            }
          ]
        },
        {
          index: '4',
          src: '../../../../static/img/menhu.png',
          title: '结算',
          childMenu: [
            {
              title: '结算单',
              src: '/admin/settlement/index'
            }
          ]
        }
      ],
      // 个人账户
      isPersonalNav: [
        {
          index: '1',
          src: '../../../../static/img/jyjl.png',
          title: '交易记录',
          childMenu: [
            {
              title: 'CA记录',
              src: '/admin/record/ca/index'
            },
            {
              title: '平台使用费',
              src: '/admin/record/platform/index'
            },
            {
              title: '标书费',
              src: '/admin/record/tender-fee/index'
            },
            {
              title: '中标服务费',
              src: '/admin/record/winning/index'
            },
            {
              title: '交易记录',
              src: '/admin/record/settlement/index'
            }
          ]
        },
        {
          index: '2',
          src: '../../../../static/img/menhu.png',
          title: '结算',
          childMenu: [
            {
              title: '结算单',
              src: '/admin/settlement/index'
            }
          ]
        }
      ],
      // 运营侧边导航数据
      operatingNav: [
        {
          index: '1',
          src: '../../../../static/img/hetong.png',
          title: '合作代理管理',
          childMenu: [
            {
              title: '合作代理管理',
              src: '/admin/contract-agency/contract/index'
            },
            {
              title: '交易系统版本管理',
              src: '/admin/contract-agency/trading-system/index'
            }
          ]
        },
        {
          index: '2',
          src: '../../../../static/img/jyjl.png',
          title: '交易记录',
          childMenu: [
            {
              title: 'CA记录',
              src: '/admin/record/ca/index'
            },
            {
              title: '平台使用费',
              src: '/admin/record/platform/index'
            },
            {
              title: '标书费',
              src: '/admin/record/tender-fee/index'
            },
            {
              title: '中标服务费',
              src: '/admin/record/winning/index'
            },
            {
              title: '交易记录',
              src: '/admin/record/settlement/index'
            }
          ]
        },
        {
          index: '3',
          src: '../../../../static/img/menhu.png',
          title: '门户管理',
          childMenu: [
            {
              title: '轮换信息',
              src: '/admin/portal/rotation-info/index'
            },
            {
              title: '入驻申请',
              src: '/admin/portal/enter-apply/index'
            }
          ]
        },
        {
          index: '4',
          src: '../../../../static/img/menhu.png',
          title: '结算',
          childMenu: [
            {
              title: '结算单',
              src: '/admin/operating-settl/index'
            }
          ]
        },
        {
          index: '5',
          src: '../../../../static/img/menhu.png',
          title: '发票管理',
          childMenu: [
            {
              title: '发票管理',
              src: '/admin/invoice-admin/index'
            }
          ]
        },
        {
          index: '6',
          src: '../../../../static/img/lunbo_icon.png',
          title: '小程序后台管理',
          childMenu: [
            {
              title: '公告页面管理',
              src: '/admin/slideshow/anno-index'
            },
            {
              title: '项目页面管理',
              src: '/admin/slideshow/project-index'
            },
            {
              title: '系统通知',
              src: '/admin/system-notice/index'
            }
          ]
        }
      ],
      menuData: []
    }
  },
  methods: {
    initMunu () {
      if (this.$store.getters.authUser.userType === 2) {
        if (this.$store.getters.authUser.isQyUser === 1) {
          // 表示此账号为代理个人
          this.menuData = this.isPersonalNav
        } else {
          // 表示此账号为代理企业
          this.menuData = this.agencyNav
        }
      } else if (this.$store.getters.authUser.userType === 1) {
        // 表示此账号为运营
        this.menuData = this.operatingNav
      }
    }
  },
  watch: {
    $route: function () {
      this.initMunu()
      this.activeNav = this.$route.matched[1].path
    }
  },
  mounted () {
    this.activeNav = this.$route.meta.active
    this.initMunu()
  }
}
</script>
<style>
</style>
