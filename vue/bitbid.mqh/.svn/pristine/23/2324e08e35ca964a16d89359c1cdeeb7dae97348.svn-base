// 资金平衡表管理
export default [
  {
    path: 'balancemanage/',
    name: '资金平衡表管理',
    meta: {
      permission: '/planmanage/balancemanage',
      title: '资金平衡表管理'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/balancemanage/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/balancemanage/index.vue'),
    children: [
      {
        path: 'balancedit',
        name: '资金平衡表管理编辑',
        // component: resolve => require(['@/views/admin/planmanage/prounit/balancemanage/balancedit.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/balancemanage/balancedit.vue'),
        meta: {
          title: '资金平衡表管理编辑'
        }
      },
      {
        path: 'balancedetails',
        name: '资金平衡表管理详情',
        // component: resolve => require(['@/views/admin/planmanage/prounit/balancemanage/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/balancemanage/details.vue'),
        meta: {
          title: '资金平衡表管理详情'
        }
      }
    ]
  }
]
