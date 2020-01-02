// 合同备案审核
export default [
  {
    path: 'contractcheck/',
    name: '合同备案审核',
    meta: {
      permission: '/contract/contractcheck',
      title: '合同备案审核'
    },
    // component: resolve => require(['@/views/admin/contract/tendcontract/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/contract/tendcontract/index.vue'),
    children: [
      {
        path: 'check',
        name: '合同备案-审核',
        meta: {
          permission: '/contract/contractcheck/approval',
          title: '审核'
        },
        // component: resolve => require(['@/views/admin/contract/tendcontract/check.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/contract/tendcontract/check.vue'),
      }
    ]
  }
]
