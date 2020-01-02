// 招采建议计划审核
export default [
  {
    path: 'bidpurcheck/',
    name: '招采建议计划审核',
    meta: {
      permission: '/planmanage/bidpurcheck',
      title: '招采建议计划审核'
    },
    // component: resolve => require(['@/views/admin/planmanage/tendoffice/bidpurcheck/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/bidpurcheck/index.vue'),
    children: [
      {
        path: 'bidpurapply',
        name: '招采建议计划审核页',
        // component: resolve => require(['@/views/admin/planmanage/tendoffice/bidpurcheck/apply.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/bidpurcheck/apply.vue'),
        meta: {
          title: '招采建议计划审核页'
        }
      },
      {
        path: 'details',
        name: '招采建议计划审核详情',
        // component: resolve => require(['@/views/admin/planmanage/tendoffice/bidpurcheck/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/bidpurcheck/details.vue'),
        meta: {
          title: '招采建议计划审核详情'
        }
      }
    ]
  }
]
