export default [
  {
    path: '/admin/default',
    name: 'default',
    meta: {
      title: '首页'
    },
    component: resolve => require(['@/views/admin/default/index.vue'], resolve)
  }
]
