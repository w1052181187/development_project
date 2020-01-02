// 供应商库
export default [
  {
    path: 'supplypool/',
    name: '供应商库',
    meta: {
      permission: '/supply/supplypool',
      title: '供应商库'
    },
    // component: resolve => require(['@/views/admin/supply/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/supply/index.vue'),
    children: [
      {
        path: 'details',
        name: '供应商库详情页',
        meta: {
          permission: '/supply/supplypool/detail',
          title: '供应商库详情页'
        },
        // component: resolve => require(['@/views/admin/supply/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/supply/details.vue')
      }
    ]
  },
  {
    path: 'supplyblack/',
    name: '供应商黑名单',
    meta: {
      title: '供应商黑名单'
    },
    // component: resolve => require(['@/views/admin/supply/blacklist.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/supply/blacklist.vue')
  }
]
