// 抽签
export default [
  {
    path: '/draw/drawroom',
    name: '抽签',
    meta: {
      permission: '/draw/drawroom',
      title: '抽签'
    },
    // component: resolve => require(['@/views/admin/draw/drawroom/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawroom/index.vue')
  },
  {
    path: '/drawroom/dready',
    name: '抽签准备',
    meta: {
      permission: '/draw/drawroom/drawLots',
      title: '抽签准备'
    },
    // component: resolve => require(['@/views/admin/draw/drawroom/dready.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawroom/dready.vue')
  },
  // {
  //   path: '/drawroom/droom',
  //   name: '抽签室',
  //   meta: {
  //     permission: '/draw/drawroom/drawLots'
  //   },
  //   component: resolve => require(['@/views/admin/draw/drawroom/droom.vue'], resolve)
  // },
  {
    path: '/drawroom/details',
    name: '抽签详情页',
    meta: {
      permission: '/draw/drawroom/drawLots',
      title: '抽签详情页'
    },
    // component: resolve => require(['@/views/admin/draw/drawroom/details.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawroom/details.vue')
  }
]
