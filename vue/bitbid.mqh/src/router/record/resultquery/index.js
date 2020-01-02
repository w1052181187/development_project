// 招标结果查询
export default [
  {
    path: 'resultquery/',
    name: '招标结果查询',
    meta: {
      permission: '/record/resultquery',
      title: '招标结果查询'
    },
    // component: resolve => require(['@/views/admin/record/resultquery/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/resultquery/index.vue'),
    children: [
      {
        path: 'details',
        name: '招标结果详情页',
        meta: {
          permission: '/record/resultquery/detail',
          title: '招标结果详情页'
        },
        // component: resolve => require(['@/views/admin/record/resultquery/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/resultquery/details.vue')
      }
    ]
  }
]
