// 投资建议计划管理
export default [
  {
    path: 'investmanage/',
    name: '投资建议计划管理',
    meta: {
      permission: '/planmanage/investmanage',
      title: '投资建议计划管理'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/investmanage/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/investmanage/index.vue'),
    children: [
      {
        path: 'investmain',
        name: '投资建议计划管理编辑',
        // component: resolve => require(['@/views/admin/planmanage/prounit/investmanage/investmain.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/investmanage/investmain.vue'),
        meta: {
          title: '投资建议计划管理编辑'
        }
      },
      {
        path: 'investlook',
        name: '投资建议计划管理查看',
        // component: resolve => require(['@/views/admin/planmanage/prounit/investmanage/investlook.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/investmanage/investlook.vue'),
        meta: {
          title: '投资建议计划管理查看'
        }
      },
      {
        path: 'details',
        name: '投资建议计划管理详情',
        // component: resolve => require(['@/views/admin/planmanage/prounit/investmanage/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/investmanage/details.vue'),
        meta: {
          title: '投资建议计划管理详情'
        }
      }
    ]
  }
]
