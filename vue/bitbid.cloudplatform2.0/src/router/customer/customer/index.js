export default [
  {
    path: '/customerMan/customer',
    name: '客户管理',
    meta: {
      title: '客户管理',
      active: '/customerMan/customer',
      permission: '1040101'
    },
    component: () => import(/* webpackChunkName: 'customer' */ '@/pages/customer/customer/index')
  },
  {
    path: '/customerMan/customer/add',
    name: '新增客户管理',
    meta: {
      title: '新增客户管理',
      active: '/customerMan/customer',
      permission: '1040102'
    },
    component: () => import(/* webpackChunkName: 'customer' */ '@/pages/customer/customer/edit')
  },
  {
    path: '/customerMan/customer/update/:objectId',
    name: '修改客户管理',
    meta: {
      title: '修改客户管理',
      active: '/customerMan/customer',
      permission: '1040102'
    },
    component: () => import(/* webpackChunkName: 'customer' */ '@/pages/customer/customer/edit')
  },
  {
    path: '/customerMan/customer/follow/:relatedCode/:type',
    name: '跟进纪录',
    meta: {
      title: '跟进纪录',
      active: '/customerMan/customer',
      permission: '1040102'
    },
    component: () => import(/* webpackChunkName: 'customer' */ '@/pages/customer/followup/index')
  },
  {
    path: '/customerMan/customer/detail/:code',
    name: '客户详情',
    meta: {
      title: '客户详情',
      active: '/customerMan/customer',
      permission: '1040101'
    },
    component: () => import(/* webpackChunkName: 'customer' */ '@/pages/customer/customer/detail/index')
  }
]
