export default [
  {
    path: '/system-management/land-transaction/opening-record/index',
    name: 'systemOpeningRecord-list',
    meta: {
      title: '开标记录表备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/opening-record/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/opening-record/detail/:objectId',
    name: 'systemOpeningRecord-detail',
    meta: {
      title: '开标记录表查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/opening-record/detail'], resolve)
  }
]
