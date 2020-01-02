// 抽签审批
export default [
  {
    path: '/draw/drawreview',
    name: '抽签申请审批',
    meta: {
      permission: '/draw/drawreview',
      title: '抽签申请审批'
    },
    // component: resolve => require(['@/views/admin/draw/drawreview/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawreview/index.vue')
  },
  {
    path: '/drawreview/dreview',
    name: '审批',
    meta: {
      permission: '/draw/drawreview/approval',
      title: '审批'
    },
    // component: resolve => require(['@/views/admin/draw/drawreview/dreview.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawreview/dreview.vue')
  },
  {
    path: '/drawreview/indreview',
    name: '中介审批',
    meta: {
      permission: '/draw/drawreview/approval',
      title: '中介审批'
    },
    // component: resolve => require(['@/views/admin/draw/drawreview/indreview.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/draw/drawreview/indreview.vue')
  }
]
