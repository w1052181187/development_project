// 抽签项目管理
export default [
  {
    path: '/draw/drawproject',
    name: '抽签项目管理',
    meta: {
      permission: '/draw/drawproject',
      title: '抽签项目管理'
    },
    // component: resolve => require(['@/views/admin/draw/drawproject/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawproject/index.vue')
  },
  {
    path: '/draw/drawgroup',
    name: '抽签分组',
    meta: {
      permission: '/draw/drawproject/operation',
      title: '抽签分组'
    },
    // component: resolve => require(['@/views/admin/draw/drawproject/drawgroup.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawproject/drawgroup.vue')
  }
]
