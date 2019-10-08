export default [
  {
    path: '/system-management/land-transaction/preliminary-results/index',
    name: 'systemPreliminaryResults-list',
    meta: {
      title: '资格预审结果通知书备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/preliminary-results/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/preliminary-results/detail/:objectId',
    name: 'systemPreliminaryResults-detail',
    meta: {
      title: '通知书查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/preliminary-results/detail'], resolve)
  }
]
