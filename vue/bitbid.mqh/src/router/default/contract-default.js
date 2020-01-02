// 合同备案默认页面
export default [
  {
    path: '/admin/contract-default',
    name: 'contract',
    component: resolve => require(['@/views/admin/default/contract-default.vue'], resolve),
    meta: {
      title: '合同备案'
    }
  }
]
