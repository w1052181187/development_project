export default [
  {
    path: '/system-management/land-transaction/listed/index',
    name: 'systemListed-list',
    meta: {
      title: '挂牌竞价情况备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/listed/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/listed/detail/:objectId',
    name: 'systemListed-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/listed/detail'], resolve)
  }
]
