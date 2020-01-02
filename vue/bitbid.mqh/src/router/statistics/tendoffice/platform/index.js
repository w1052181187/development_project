// 平台统计
export default [
  {
    path: 'platform-sum',
    name: '平台统计',
    meta: {
      permission: '/statistics/platform-sum',
      title: '平台统计'
    },
    // component: resolve => require(['@/views/admin/statistics/tendstatist/platform/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/tendstatist/platform/index.vue')
  }
]
