import Vue from 'vue'
import Router from 'vue-router'
import NProgress from 'nprogress'
import store from '../store'
// import HelloWorld from '@/components/HelloWorld'
// import Admin from '@/views/admin/index/index'
// 默认页面
// import RecordDefault from './default/record-default'
// import PlanDefault from './default/plan-default'
// import DrawDefault from './default/draw-default'
// import ResourceDefault from './default/resource-default'
// import FilesDefault from './default/file-default'
// import EvalDefault from './default/eval-default'
// import SupplyDefault from './default/supply-default'
// import ContractDefault from './default/contract-default'
// import StatisticsDefault from './default/statistics-default'
// 组织机构
import Orgna from './organ/orgna'
// 信息设置
import Infoset from './infoset/infoset'
// 系统
import System from './system/system'
// 后台首页
import WorkDesk from './workdesk/workdesk'
// 抽签
import Draw from './draw/draw'
// 计划管理
import Planmanage from './planmanage/planmanage'
// 资源管理
import Resource from './resource/resource'
// 招标备案
import Record from './record/record'
// 档案管理
import Files from './files/files'
// 考评系统
import Evaluate from './evaluate/evaluate'
// 供应商库
import Supply from './supply/supply'
// 合同备案
import Contract from './contract/contract'
// 统计分析
import Statistics from './statistics/statistics'
Vue.use(Router)
const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      meta: {
        permission: 'business',
        title: '首页'
      },
      // component: resolve => require(['@/views/admin/workdesk/index.vue'], resolve)
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/index.vue')
    },
    {
      path: '/login',
      name: 'login',
      meta: {
        noRequireAuth: true,
        title: '登录'
      },
      // component: resolve => require(['@/views/home/login/login.vue'], resolve)
      component: () => import(/* webpackChunkName: 'project' */ '@/views/home/login/login.vue')
    },
    {
      path: '/admin',
      name: '后台主页',
      meta: {
        permission: 'business',
        title: '后台主页'
      },
      // component: resolve => require(['@/views/admin/workdesk/index.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/index.vue'),
      children: [
        ...WorkDesk
      ]
    },
    {
      path: '/admin/index',
      // component: resolve => require(['@/views/admin/index/menu/admin-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/admin-menu.vue'),
      meta: {
        permission: 'admin',
        title: '管理员'
      },
      children: [
        ...Orgna,
        ...Infoset,
        ...System
      ]
    },
    // 抽签
    {
      path: '/admin/draw',
      // component: resolve => require(['@/views/admin/index/menu/draw-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/draw-menu.vue'),
      meta: {
        permission: 'draw',
        title: '抽签'
      },
      redirect: '/draw/drawresult',
      children: [
        // ...DrawDefault,
        ...Draw
      ]
    },
    // 计划管理
    {
      path: '/admin/planmanage',
      // component: resolve => require(['@/views/admin/index/menu/plan-manage.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/plan-manage.vue'),
      meta: {
        permission: 'files',
        title: '计划管理'
      },
      redirect: '/admin/planmanage/planquery',
      children: [
        // ...PlanDefault,
        ...Planmanage
      ]
    },
    // 资源管理
    {
      path: '/admin/resource',
      // component: resolve => require(['@/views/admin/index/menu/resource.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/resource.vue'),
      meta: {
        permission: 'resource',
        title: '资源管理'
      },
      redirect: '/admin/resource/intermange',
      children: [
        // ...ResourceDefault,
        ...Resource
      ]
    },
    // 招标备案
    {
      path: '/admin/record',
      // component: resolve => require(['@/views/admin/index/menu/tend-record.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/tend-record.vue'),
      redirect: '/admin/record/processrecord',
      meta: {
        title: '招标备案'
      },
      children: [
        // ...RecordDefault,
        ...Record
      ]
    },
    // 档案管理
    {
      path: '/admin/files',
      // component: resolve => require(['@/views/admin/index/menu/files-manage.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/files-manage.vue'),
      redirect: '/admin/files/filequery',
      meta: {
        title: '档案管理'
      },
      children: [
        // ...FilesDefault,
        ...Files
      ]
    },
    // 考评系统
    {
      path: '/admin/evaluate',
      // component: resolve => require(['@/views/admin/index/menu/eval-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/eval-menu.vue'),
      // redirect: (enterpriseType === 1) ? '/admin/evaluate/agentquery' : '/admin/evaluate/agenteval',
      meta: {
        title: '考评系统'
      },
      children: [
        // ...EvalDefault,
        ...Evaluate
      ]
    },
    // 供应商库
    {
      path: '/admin/supply',
      // component: resolve => require(['@/views/admin/index/menu/supply-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/supply-menu.vue'),
      redirect: '/admin/supply/supplypool',
      meta: {
        title: '供应商库'
      },
      children: [
        ...Supply
        // ...SupplyDefault
      ]
    },
    // 合同备案
    {
      path: '/admin/contract',
      // component: resolve => require(['@/views/admin/index/menu/contract-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/contract-menu.vue'),
      // redirect: (enterpriseType === 1) ? '/admin/contract/contractcheck' : '/admin/contract/contractrecord',
      meta: {
        title: '合同备案'
      },
      children: [
        ...Contract
        // ...ContractDefault
      ]
    },
    // 统计分析
    {
      path: '/admin/statistics',
      // component: resolve => require(['@/views/admin/index/menu/statistics-menu.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/index/menu/statistics-menu.vue'),
      // redirect: '/admin/statistics/bid-statist',
      meta: {
        title: '统计分析'
      },
      children: [
        ...Statistics
        // ...StatisticsDefault
      ]
    },
    // 测试
    {
      path: '/test',
      // component: resolve => require(['@/views/test/test.vue'], resolve),
      component: () => import(/* webpackChunkName: 'project' */ '@/views/test/test.vue'),
      meta: {
        title: '测试'
      }
    },
    {
      path: '/auth',
      name: '401',
      meta: {
        noRequireAuth: true,
        title: '401'
      },
      // component: resolve => require(['@/error/auth.vue'], resolve)
      component: () => import(/* webpackChunkName: 'project' */ '@/error/auth.vue')
    },
    {
      path: '*',
      name: '404',
      meta: {
        noRequireAuth: true,
        title: '404'
      },
      // component: resolve => require(['@/error/error.vue'], resolve)
      component: () => import(/* webpackChunkName: 'project' */ '@/error/error.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  NProgress.start()
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  // token权限拦截
  if (!to.meta.noRequireAuth) {
    if (store.getters.token) {
      if (!store.getters.authUser) {
        store.dispatch('GetLoginInfo').then(() => {
          next()
        })
      } else {
        next()
      }
    } else {
      next({
        path: '/login'
        // 将刚刚要去的路由path（却无权限）作为参数，方便登录成功后直接跳转到该路由
        // query: { redirect: to.fullPath }
      })
    }
    // 权限啰嗦判断，非动态路由生成情况下先这么做, 之后改为动态路由生成
    // 公共路由，后期改为判断to.meta.permission,如果没有则是不需要权限认证，随便去
    // if (store.getters.token) {
    //   if (!store.getters.authUser) {
    //     store.dispatch('GetLoginInfo').then(() => {
    //       if (store.getters.permissions.includes(to.meta.permission)) {
    //         next()
    //       } else {
    //         next({name: '401'})
    //       }
    //     })
    //   } else {
    //     if (store.getters.permissions.includes(to.meta.permission)) {
    //       next()
    //     } else {
    //       next({name: '401'})
    //     }
    //   }
    // } else {
    //   next({
    //     path: '/login'
    //     // 将刚刚要去的路由path（却无权限）作为参数，方便登录成功后直接跳转到该路由
    //     // query: { redirect: to.fullPath }
    //   })
    // }
  } else {
    next()
  }
  // 退出抽签准备室时要将redis中的信息进去清空
  // 进入抽签室且未开始抽签则可以进行退出
  if (from.path === '/drawroom/dready' && to.path !== '/drawroom/dready' && to.path !== '/login' && to.path !== '/') {
    store.dispatch('DeleteDramRoomInfo').then(() => {
      next()
    })
    /* if (store.getters.messages.roomStatus === 2 && store.getters.messages.drawStatus === 2) {
      next(false)
    } else {
      store.dispatch('DeleteDramRoomInfo').then(() => {
        next()
      })
    } */
  }
  let enterpriseType = store.getters.authUser.enterpriseType
  if (to.path === '/admin/statistics') {
    if (enterpriseType === 1) {
      next({
        path: '/admin/statistics/bid-statist'
      })
    } else {
      next({
        path: '/admin/statistics/coalsafe-report', query: {capitalSourceId: '10001', capitalName: '折旧（更改）'}
      })
    }
  } else if (to.path === '/admin/contract') {
    if (enterpriseType === 1) {
      next({
        path: '/admin/contract/contractcheck'
      })
    } else {
      next({
        path: '/admin/contract/contractrecord'
      })
    }
  } else if (to.path === '/admin/evaluate') {
    if (enterpriseType === 1) {
      next({
        path: '/admin/evaluate/agentquery'
      })
    } else if (enterpriseType === 4) {
      next({
        path: '/admin/evaluate/projecteval'
      })
    } else {
      next({
        path: '/admin/evaluate/agenteval'
      })
    }
  }
})

router.afterEach((to, from) => {
  document.title = to.meta.title || ''
  NProgress.done()
})
export default router
