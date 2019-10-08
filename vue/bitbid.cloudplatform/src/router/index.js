import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import store from '../store'
import 'nprogress/nprogress.css'

import resource from './resource/index'
import owner from './owner/index'
import expert from './expert/index'
import archives from './archives/index'
import projectpayments from './project-payments/index'
import index from './index/index'
import login from './login/index'
import notopen from './notopen/index'
import renewal from './renewal/index'
import admin from './admin/index'
import openservice from './openservice/index'
import organization from './organization/index'
import enterprise from './enterprise/index'
import notfound from '../components/notfound/notfound.vue'
import noauth from '../components/notfound/no-authority.vue'
import cloudplatform from './cloudplatform/index'

const router = new VueRouter({
  mode: 'history',
  routes: [
    ...archives,
    ...resource,
    ...projectpayments,
    ...owner,
    ...expert,
    ...notopen,
    ...admin,
    ...openservice,
    ...organization,
    ...enterprise,
    ...index,
    ...login,
    ...renewal,
    ...cloudplatform,
    {
      path: '/no-auth',
      name: 'noauth',
      component: noauth,
      meta: {
        isshow: true
      }
    },
    {
      path: '*',
      name: 'notFound',
      component: notfound,
      meta: {
        isshow: true
      }
    }]
})

router.beforeEach((to, from, next) => {
  let url = to.path
  if (url.slice(-1) == '/' && url != '/') {
    next({path: to.path.slice(0, -1)})
    return false
  }
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
      // 权限啰嗦判断，非动态路由生成情况下先这么做, 之后改为动态路由生成
      // 公共路由，后期改为判断to.meta.permission,如果没有则是不需要权限认证，随便去
      // if (to.name === 'login' || to.name === 'noauth' || to.name === 'notFound') {
      //   next()
      // } else {
      //   if (!store.getters.authUser) {
      //     store.dispatch('GetLoginInfo').then(() => {
      //       if (store.getters.permissions.includes(to.meta.permission)) {
      //         next()
      //       } else {
      //         next('/no-auth')
      //       }
      //     })
      //   } else {
      //     if (store.getters.permissions.includes(to.meta.permission)) {
      //       next()
      //     } else {
      //       next('/no-auth')
      //     }
      //   }
      // }
    } else {
      next({
        path: '/login'
        // 将刚刚要去的路由path（却无权限）作为参数，方便登录成功后直接跳转到该路由
        // query: { redirect: to.fullPath }
      })
    }
  } else {
    // 判断是否已经登录，如果已经登录跳转首页
    if (store.getters.authUser) {
      let path = store.getters.authUser.userType === 1 ? '/admin' : '/index'
      next(path)
    } else {
      next()
    }
  }
})

router.afterEach((to, from) => {
  document.title = to.meta.title || ''
  NProgress.done()
})

export default router
