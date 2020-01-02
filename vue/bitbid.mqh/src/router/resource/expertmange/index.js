// 评标专家管理
export default [
  {
    path: 'expertmange/',
    name: '评标专家管理',
    meta: {
      permission: '/resource/expertmange',
      title: '评标专家管理'
    },
    // component: resolve => require(['@/views/admin/resource/expertmange/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/resource/expertmange/index.vue'),
    children: [
      {
        path: 'expertdetail',
        name: '评标专家详情页',
        meta: {
          permission: '/resource/expertmange/detail',
          title: '评标专家详情页'
        },
        // component: resolve => require(['@/views/admin/resource/expertmange/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/resource/expertmange/details.vue')
      }
    ]
  }
]
