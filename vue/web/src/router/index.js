import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import adminuser from './adminuser/index'
import annoapprove from './anno-approve/index'
import annomanage from './anno-manage/index'
import commonuser from './commonuser/index'
import homemanage from './home-manage/index'
import infomanage from './info-manage/index'
import store from '../store'
import {titleName} from '@/assets/js/common'

const router = new VueRouter({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      redirect: '/main',
      component: resolve => require(['@/pages/index'], resolve),
      meta: {
        permissions: [1, 2, 3]
      },
      children: [{
        path: '/main',
        name: 'main',
        component: resolve => require(['@/pages/main'], resolve),
        meta: {
          permissions: [1, 2, 3],
          title: '首页' + titleName
        }
      },
      ...adminuser,
      ...annoapprove,
      ...annomanage,
      ...commonuser,
      ...homemanage,
      ...infomanage
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: {
        noRequireAuth: true,
        title: '登录' + titleName
      },
      component: resolve => require(['@/pages/login/login'], resolve)
    },
    {
      path: '/register',
      name: 'register',
      meta: {
        noRequireAuth: true,
        title: '注册' + titleName
      },
      component: resolve => require(['@/pages/login/register'], resolve)
    },
    {
      path: '/notFound',
      name: 'notFound',
      meta: {
        noRequireAuth: true,
        title: '出错啦' + titleName
      },
      component: resolve => require(['@/pages/error/index'], resolve)
    }
  ]
})

router.beforeEach((to, from, next) => {
  NProgress.start()
  // token权限拦截
  if (!to.meta.noRequireAuth) {
    if (store.getters.token) {
      // 开发用
      // if (!store.getters.authUser) {
      //   store.dispatch('GetLoginInfo').then(() => {
      //     next()
      //   })
      // } else {
      //   next()
      // }
      // 生产环境用
      if (!store.getters.authUser) {
        store.dispatch('GetLoginInfo').then(() => {
          if (to.meta.permissions.includes(store.getters.authUser.userType)) {
            next()
          } else {
            next({name: 'notFound'})
          }
        })
      } else {
        if (to.meta.permissions.includes(store.getters.authUser.userType)) {
          next()
        } else {
          next({name: 'notFound'})
        }
      }
    } else {
      next({
        path: '/login'
      })
    }
  } else {
    // 判断是否已经登录，如果已经登录跳转首页
    if (store.getters.authUser) {
      next({path: '/main'})
    } else {
      next()
    }
  }
})

// 标题改变
router.afterEach((to, from) => {
  document.title = to.meta.title || ''
  NProgress.done()
})

export default router
