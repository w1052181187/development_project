// 项目计划申请
export default [
  {
    path: 'proplanapp/',
    name: '项目计划申请',
    meta: {
      permission: '/planmanage/proplanapp',
      title: '项目计划申请'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/proplanapp/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/proplanapp/index.vue')
  }
]
