// 计算器
export default [
  {
    path: 'calculator',
    name: '计算器',
    // component: resolve => require(['@/views/admin/workdesk/computer/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/computer/index.vue'),
    meta: {
      title: '计算器'
    }
  }
]
