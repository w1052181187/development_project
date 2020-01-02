// 煤安费汇总表
export default [
  {
    path: 'coalsafety',
    name: '煤安费汇总表',
    meta: {
      permission: '/statistics/coalsafety',
      title: '煤安费汇总表'
    },
    // component: resolve => require(['@/views/admin/statistics/tendstatist/annual_sum/coal_safety/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/tendstatist/annual_sum/coal_safety/index.vue'),
    children: [
      {
        path: 'coalsafety-details',
        name: '汇总表详情',
        meta: {
          title: '汇总表详情'
        },
        // component: resolve => require(['@/views/admin/statistics/tendstatist/annual_sum/coal_safety/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/tendstatist/annual_sum/coal_safety/details.vue')
      }
    ]
  },
  {
    path: 'coalsafety-pass',
    name: '煤炭安全资金汇总表',
    meta: {
      permission: '/statistics/coalsafety-pass',
      title: '煤炭安全资金汇总表'
    },
    // component: resolve => require(['@/views/admin/statistics/tendstatist/annual_sum/coal_safety/coalpass.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/tendstatist/annual_sum/coal_safety/coalpass.vue')
  },
  {
    path: 'coalsafe-use-sum',
    name: '煤安费提取和使用汇总',
    meta: {
      permission: '/statistics/coalsafe-use-sum',
      title: '煤安费提取和使用汇总'
    },
    // component: resolve => require(['@/views/admin/statistics/tendstatist/annual_sum/coal_use/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/statistics/tendstatist/annual_sum/coal_use/index.vue')
  }
]
