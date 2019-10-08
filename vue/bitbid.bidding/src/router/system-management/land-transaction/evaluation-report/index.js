export default [
  {
    path: '/system-management/land-transaction/evaluation-report/index',
    name: 'systemEvaluationReport-list',
    meta: {
      title: '招标评标报告备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/evaluation-report/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/evaluation-report/detail/:objectId',
    name: 'systemEvaluationReport-detail',
    meta: {
      title: '招标评标报告查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/evaluation-report/detail'], resolve)
  }
]
