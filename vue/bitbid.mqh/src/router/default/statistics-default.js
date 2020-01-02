
// 统计分析默认页面
export default [
  {
    path: '/admin/statistics-default',
    name: 'statistcs',
    component: resolve => require(['@/views/admin/default/statistics-default.vue'], resolve),
    meta: {
      title: '统计分析'
    }
  }
]
