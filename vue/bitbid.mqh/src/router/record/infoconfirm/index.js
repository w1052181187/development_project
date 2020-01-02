// 招标信息确认
export default [
  {
    path: 'infoconfirm/',
    name: '招标信息确认',
    meta: {
      permission: '/record/infoconfirm',
      title: '招标信息确认'
    },
    // component: resolve => require(['@/views/admin/record/infoconfirm/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/infoconfirm/index.vue'),
    children: [
      {
        path: 'details',
        name: '招标信息确认详情',
        meta: {
          permission: '/record/infoconfirm/detail',
          title: '招标信息确认详情'
        },
        // component: resolve => require(['@/views/admin/record/infoconfirm/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/infoconfirm/details.vue')
      }
    ]
  }
]
