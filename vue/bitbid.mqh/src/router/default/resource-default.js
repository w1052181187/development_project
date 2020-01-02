// 资源管理默认页面
export default [
  {
    path: '/admin/resource-default',
    name: 'resource',
    component: resolve => require(['@/views/admin/default/resource-default.vue'], resolve),
    meta: {
      title: '资源管理'
    }
  }
]
