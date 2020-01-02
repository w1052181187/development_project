// 招标计划默认页面
export default [
  {
    path: '/admin/plan-default',
    name: 'plan',
    component: resolve => require(['@/views/admin/default/plan-default.vue'], resolve),
    meta: {
      title: '计划管理'
    }
  }
]
