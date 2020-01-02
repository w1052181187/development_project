// 资源管理
export default [
  {
    path: 'intermange/',
    name: '中介机构管理',
    meta: {
      permission: '/resource/intermange',
      title: '中介机构管理'
    },
    // component: resolve => require(['@/views/admin/resource/intermange/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/resource/intermange/index.vue'),
    children: [
      {
        path: 'addinter',
        name: '新增管理',
        meta: {
          permission: '/resource/intermange/operation',
          title: '新增管理'
        },
        // component: resolve => require(['@/views/admin/resource/intermange/add.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/resource/intermange/add.vue')
      },
      {
        path: 'detailsinter',
        name: '中介机构管理详情',
        meta: {
          permission: '/resource/intermange/detail',
          title: '中介机构管理详情'
        },
        // component: resolve => require(['@/views/admin/resource/intermange/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/resource/intermange/details.vue')
      }
    ]
  }
]
