export default [
  {
    path: '/system-management/land-transaction/auction/index',
    name: 'systemAuction-list',
    meta: {
      title: '拍卖竞价情况备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/auction/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/auction/detail/:objectId',
    name: 'systemAuction-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/auction/detail'], resolve)
  }
]
