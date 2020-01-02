// 后台
export default [
  {
    path: '/admin/infoset',
    name: '信息设置',
    meta: {
      permission: 'admin',
      title: '信息设置'
    },
    // component: resolve => require(['@/views/admin/infoset/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/infoset/index.vue')
  }
]
