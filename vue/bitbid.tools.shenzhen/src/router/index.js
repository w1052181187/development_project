import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import index from '@/pages/index'
import welcome from '@/pages/welcome/index'

import logins from './login/index'
import store from '../store/index'
import notfound from '@/pages/notfound/notfound.vue'
// 竞争性谈判
import competitiveNegotiation from './competitive-negotiation/index'
// 询价竞价在线评标系统（询价）
import enquiryBidding from './enquiry-bidding/index'
// 竞争性磋商
import competitiveConsultation from './competitive-consultation/index'
// 单一来源
import singleSource from './single-source/index'
// 公开招标
import openTendering from './open-tendering/index'

const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: index,
      meta: {
        title: '首页'
      },
      children: [
        {
          path: '/welcome',
          name: 'welcome',
          component: welcome,
          meta: {
            title: '欢迎进入系统'
          }
        },
        ...competitiveNegotiation,
        ...enquiryBidding,
        ...competitiveConsultation,
        ...singleSource,
        ...openTendering,
        ...logins
      ]
    },
    { // 暂无页面
      path: '*',
      name: 'notFound',
      component: notfound,
      meta: {
        noRequireAuth: true,
        isshow: true
      }
    }
  ]
})
// 标题改变
router.afterEach((to, from) => {
  document.title = to.meta.title || ''
  NProgress.done()
})
router.beforeEach((to, from, next) => {
  NProgress.start()
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
      let url = to.query.bdsbh ? '?bdsbh=' + to.query.bdsbh : ''
      next({
        path: `/login` + url
      })
    }
  } else {
    // 判断是否已经登录，如果已经登录跳转首页
    if (store.getters.authUser) {
      let path = null
      if (store.getters.authUser.userType === 2) {
        path = this.$router.push({path: '/welcome'})
      }
      next(path)
    } else {
      next()
    }
  }
})
export default router
