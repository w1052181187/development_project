// 招标变更查询
export default [
  {
    path: 'changequery/',
    name: '招标变更查询',
    meta: {
      permission: '/record/changequery',
      title: '招标变更查询'
    },
    // component: resolve => require(['@/views/admin/record/changequery/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/changequery/index.vue'),
    children: [
      {
        path: 'details',
        name: '招标变更详情页',
        meta: {
          permission: '/record/changequery/detail',
          title: '招标变更详情页'
        },
        // component: resolve => require(['@/views/admin/record/changequery/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/changequery/details.vue')
      }
    ]
  }
]
