// 煤安费上报
export default [
  {
    path: 'coalsafe-report',
    name: '煤安费上报',
    meta: {
      permission: '/statistics/coalsafe-report',
      title: '煤安费上报'
    },
    // component: resolve => require(['@/views/admin/statistics/prostatist/coal_safety/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/prostatist/coal_safety/index.vue'),
    children: [
    ]
  },
  {
    path: 'coalsafe-use',
    name: '煤安费提取和使用情况上报',
    meta: {
      permission: '/statistics/coalsafe-use',
      title: '煤安费提取和使用情况上报'
    },
    // component: resolve => require(['@/views/admin/statistics/prostatist/coal_use/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/prostatist/coal_use/index.vue')
  }
]
