// 项目单位考评
export default [
  {
    path: 'projecteval/',
    name: '项目单位考评',
    meta: {
      permission: '/evaluate/projecteval',
      title: '项目单位考评'
    },
    // component: resolve => require(['@/views/admin/evaluate/projecteval/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/projecteval/index.vue'),
    children: [
    ]
  },
  {
    path: 'projectquery/',
    name: '项目单位考评查询',
    meta: {
      permission: '/evaluate/projectquery',
      title: '项目单位考评查询'
    },
    // component: resolve => require(['@/views/admin/evaluate/projecteval/proquery.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/projecteval/proquery.vue'),
    children: [
      {
        path: 'details',
        name: '项目单位考评详情',
        meta: {
          permission: '/evaluate/projectquery/detail',
          title: '项目单位考评详情'
        },
        // component: resolve => require(['@/views/admin/evaluate/projecteval/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/projecteval/details.vue')
      }
    ]
  }
]
