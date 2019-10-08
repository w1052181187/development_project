export default [
  // *************************************CA*******************************************
  {
    path: '/admin/record/ca/index',
    name: 'ca-index',
    meta: {
      title: 'CA',
      active: '/admin/record/ca/index'
    },
    component: resolve => require(['@/pages/admin/record/ca/index'], resolve)
  },
  {
    path: '/admin/record/ca/detail/:objectId',
    name: 'ca-detail',
    meta: {
      title: '详情',
      active: '/admin/record/ca/index'
    },
    component: resolve => require(['@/pages/admin/record/ca/detail'], resolve)
  },
  // *************************************平台使用费*******************************************
  {
    path: '/admin/record/platform/index',
    name: 'platform-index',
    meta: {
      title: '平台使用费',
      active: '/admin/record/platform/index'
    },
    component: resolve => require(['@/pages/admin/record/platform/index'], resolve)
  },
  {
    path: '/admin/record/platform/detail/:objectId',
    name: 'platform-detail',
    meta: {
      title: '详情',
      active: '/admin/record/platform/index'
    },
    component: resolve => require(['@/pages/admin/record/platform/detail'], resolve)
  },
  // *************************************标书费*******************************************
  {
    path: '/admin/record/tender-fee/index',
    name: 'tender-index',
    meta: {
      title: '标书费',
      active: '/admin/record/tender-fee/index'
    },
    component: resolve => require(['@/pages/admin/record/tender-fee/index'], resolve)
  },
  {
    path: '/admin/record/tender-fee/detail/:objectId',
    name: 'tender-detail',
    meta: {
      title: '详情',
      active: '/admin/record/tender-fee/index'
    },
    component: resolve => require(['@/pages/admin/record/tender-fee/detail'], resolve)
  },
  // *************************************中标服务费*******************************************
  {
    path: '/admin/record/winning/index',
    name: 'winning-index',
    meta: {
      title: '中标服务费',
      active: '/admin/record/winning/index'
    },
    component: resolve => require(['@/pages/admin/record/winning/index'], resolve)
  },
  {
    path: '/admin/record/winning/detail/:objectId',
    name: 'winning-detail',
    meta: {
      title: '详情',
      active: '/admin/record/winning/index'
    },
    component: resolve => require(['@/pages/admin/record/winning/detail'], resolve)
  },
  // *************************************结算汇总*******************************************
  {
    path: '/admin/record/settlement/index',
    name: 'settlement-index',
    meta: {
      title: '结算汇总',
      active: '/admin/record/settlement/index'
    },
    component: resolve => require(['@/pages/admin/record/settlement/index'], resolve)
  },
  {
    path: '/admin/record/settlement/detail/:objectId',
    name: 'settlement-detail',
    meta: {
      title: '详情',
      active: '/admin/record/settlement/index'
    },
    component: resolve => require(['@/pages/admin/record/settlement/detail'], resolve)
  }
]
