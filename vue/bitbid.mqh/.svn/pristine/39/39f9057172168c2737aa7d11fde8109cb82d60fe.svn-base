// 合同备案
export default [
  {
    path: 'contractrecord/',
    name: '合同备案',
    meta: {
      permission: '/contract/contractrecord',
      title: '合同备案'
    },
    // component: resolve => require(['@/views/admin/contract/procontract/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/contract/procontract/index.vue'),
    children: [
      {
        path: 'details',
        name: '合同备案详情',
        meta: {
          permission: '/contract/contractrecord/detail',
          title: '合同备案详情'
        },
        // component: resolve => require(['@/views/admin/contract/procontract/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/contract/procontract/details.vue')
      },
      {
        path: 'edit',
        name: '合同备案编辑',
        meta: {
          permission: '/contract/contractrecord/operation',
          title: '合同备案编辑'
        },
        // component: resolve => require(['@/views/admin/contract/procontract/edit.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/contract/procontract/edit.vue')
      }
    ]
  }
]
