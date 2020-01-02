// 评标专家考评
export default [
  {
    path: 'experteval/',
    name: '评标专家考评',
    meta: {
      permission: '/evaluate/experteval',
      title: '评标专家考评'
    },
    // component: resolve => require(['@/views/admin/evaluate/experteval/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/experteval/index.vue'),
    children: [
      {
        path: 'check',
        name: '专家考评',
        meta: {
          title: '专家考评'
        },
        // component: resolve => require(['@/views/admin/evaluate/experteval/expertcheck.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/experteval/expertcheck.vue')
      }
    ]
  },
  {
    path: 'expertquery/',
    name: '评标专家考评查询',
    meta: {
      permission: '/evaluate/expertquery',
      title: '评标专家考评查询'
    },
    // component: resolve => require(['@/views/admin/evaluate/experteval/expertquery.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/experteval/expertquery.vue'),
    children: [
      {
        path: 'details',
        name: '专家考评详情',
        meta: {
          permission: '/evaluate/expertquery/detail',
          title: '专家考评详情'
        },
        // component: resolve => require(['@/views/admin/evaluate/experteval/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/experteval/details.vue')
      }
    ]
  }
]
