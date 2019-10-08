import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import store from '../store/index'
// 后台路由
import admin from './admin/index'
// 登陆路由
import login from './login/index'
// 注册路由
import register from './register/index'
const router = new VueRouter({
  mode: 'history',
  routes: [
    ...admin,
    ...login,
    ...register
  ]
})
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
      next({path: '/login'})
    }
  } else {
    // 判断是否已经登录，如果已经登录跳转首页
    if (store.getters.authUser) {
      let path = this.$router.push({path: '/admin/contract-agency/contract/index'})
      next(path)
    } else {
      next()
    }
  }
})
export default router
