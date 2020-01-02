// 抽签系统默认页面
export default [
  {
    path: '/admin/draw-default',
    name: 'draw',
    component: resolve => require(['@/views/admin/default/draw-default.vue'], resolve),
    meta: {
      title: '抽签系统'
    }
  }
]
