// 货物采购管理
export default [
  {
    path: 'goodsmanage/',
    name: '货物采购管理',
    meta: {
      permission: '/planmanage/goodsmanage',
      title: '货物采购管理'
    },
    // component: resolve => require(['@/views/admin/planmanage/prounit/goodsmanage/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/prounit/goodsmanage/index.vue')
  }
]
