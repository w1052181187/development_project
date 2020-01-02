// 计划备案查询
export default [
  {
    path: 'planquery/',
    name: '计划备案查询',
    meta: {
      permission: '/planmanage/planquery',
      title: '计划备案查询'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/planquery/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/planquery/index.vue'),
    children: [
      {
        path: 'details',
        name: '计划备案查询详情',
        // component: resolve => require(['@/views/admin/planmanage/prounit/planquery/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/planquery/details.vue'),
        meta: {
          title: '计划备案查询详情'
        }
      }
    ]
  }
]
