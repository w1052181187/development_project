// 招采建议计划管理
export default [
  {
    path: 'bidpurmanage/',
    name: '招采建议计划管理',
    meta: {
      permission: '/planmanage/bidpurmanage',
      title: '招采建议计划管理'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/bidpurmanage/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/bidpurmanage/index.vue'),
    children: [
      {
        path: 'bidpuredit',
        name: '招采建议计划管理编辑',
        // component: resolve => require(['@/views/admin/planmanage/prounit/bidpurmanage/bidpuredit.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/bidpurmanage/bidpuredit.vue'),
        meta: {
          title: '招采建议计划管理编辑'
        }
      },
      {
        path: 'bidpurdetails',
        name: '招采建议计划管理详情',
        // component: resolve => require(['@/views/admin/planmanage/prounit/bidpurmanage/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/bidpurmanage/details.vue'),
        meta: {
          title: '招采建议计划管理详情'
        }
      }
    ]
  }
]
