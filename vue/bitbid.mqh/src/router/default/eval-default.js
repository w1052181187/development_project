// 考评系统默认页面
export default [
  {
    path: '/admin/eval-default',
    name: 'eval',
    component: resolve => require(['@/views/admin/default/eval-default.vue'], resolve),
    meta: {
      title: '考评系统'
    }
  }
]
