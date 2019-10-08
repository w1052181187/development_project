export default [
  {
    path: '/admin/invoice-admin/index',
    name: 'invoice-index',
    meta: {
      title: '发票管理',
      active: '/admin/invoice-admin/index'
    },
    component: resolve => require(['@/pages/admin/invoice-admin/index'], resolve)
  },
  {
    path: '/admin/invoice-admin/detail/:objectId',
    name: 'invoice-detail',
    meta: {
      title: '查看',
      active: '/admin/invoice-admin/index'
    },
    component: resolve => require(['@/pages/admin/invoice-admin/detail'], resolve)
  },
  {
    path: '/admin/invoice-admin/noapplydetail/:objectId',
    name: 'invoice-noapplydetail',
    meta: {
      title: '查看',
      active: '/admin/invoice-admin/index'
    },
    component: resolve => require(['@/pages/admin/invoice-admin/noapplydetail'], resolve)
  },
  {
    path: '/admin/invoice-admin/examine/:objectId',
    name: 'invoice-examine',
    meta: {
      title: '审核',
      active: '/admin/invoice-admin/index'
    },
    component: resolve => require(['@/pages/admin/invoice-admin/examine'], resolve)
  }
]
