export default [
  {
    path: '/system-management/land-transaction/make-result/index',
    name: 'systemMakeResult-list',
    meta: {
      title: '成交结果公示备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/make-result/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/make-result/examine/:objectId',
    name: 'systemMakeResult-examine',
    meta: {
      title: '成交结果公示审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/make-result/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/make-result/detail/:objectId',
    name: 'systemMakeResult-detail',
    meta: {
      title: '成交结果公示查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/make-result/detail'], resolve)
  }
]
