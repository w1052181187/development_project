export default [
  {
    path: '/admin/contract-agency/contract/index',
    name: 'contract-index',
    meta: {
      title: '合作代理管理',
      active: '/admin/contract-agency/contract/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/contract/index'], resolve)
  },
  {
    path: '/admin/contract-agency/contract/add',
    name: 'contract-add',
    meta: {
      title: '创建',
      active: '/admin/contract-agency/contract/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/contract/add'], resolve)
  },
  {
    path: '/admin/contract-agency/contract/update',
    name: 'contract-update',
    meta: {
      title: '修改',
      active: '/admin/contract-agency/contract/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/contract/update'], resolve)
  },
  {
    path: '/admin/contract-agency/contract/detail/:objectId',
    name: 'contract-detail',
    meta: {
      title: '查看',
      active: '/admin/contract-agency/contract/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/contract/detail'], resolve)
  },
  {
    path: '/admin/contract-agency/contract/associated',
    name: 'contract-associated',
    meta: {
      title: '关联账户',
      active: '/admin/contract-agency/contract/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/contract/associated'], resolve)
  },
  // ******************************************交易系统版本管理*******************************
  {
    path: '/admin/contract-agency/trading-system/index',
    name: 'trading-system',
    meta: {
      title: '交易系统版本管理',
      active: '/admin/contract-agency/trading-system/index'
    },
    component: resolve => require(['@/pages/admin/contract-agency/trading-system/index'], resolve)
  }
]
