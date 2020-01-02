// 供应商库默认页面
export default [
  {
    path: '/admin/supply-default',
    name: 'supply',
    component: resolve => require(['@/views/admin/default/supply-default.vue'], resolve),
    meta: {
      title: '供应商库'
    }
  }
]
