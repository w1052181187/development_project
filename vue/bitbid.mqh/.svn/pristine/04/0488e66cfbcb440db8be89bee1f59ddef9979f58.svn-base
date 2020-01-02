// 资金平衡表审核
export default [
  {
    path: 'balancecheck/',
    name: '资金平衡表审核',
    meta: {
      permission: '/planmanage/balancecheck',
      title: '资金平衡表审核'
    },
    // component: resolve => require(['@/views/admin/planmanage/tendoffice/balancecheck/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/balancecheck/index.vue'),
    children: [
      {
        path: 'check',
        name: '资金平衡表审核编辑',
        // component: resolve => require(['@/views/admin/planmanage/tendoffice/balancecheck/check.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/balancecheck/check.vue'),
        meta: {
          title: '资金平衡表审核编辑'
        }
      },
      {
        path: 'details',
        name: '资金平衡表审核详情',
        // component: resolve => require(['@/views/admin/planmanage/tendoffice/balancecheck/details.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/balancecheck/details.vue'),
        meta: {
          title: '资金平衡表审核详情'
        }
      }
    ]
  }
]
