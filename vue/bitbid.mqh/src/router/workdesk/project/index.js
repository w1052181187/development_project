// 招标项目
export default [
  {
    path: 'project',
    name: '招标项目',
    // component: resolve => require(['@/views/admin/workdesk/project/details.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/workdesk/project/details.vue'),
    meta: {
      title: '招标项目'
    }
  }
]
