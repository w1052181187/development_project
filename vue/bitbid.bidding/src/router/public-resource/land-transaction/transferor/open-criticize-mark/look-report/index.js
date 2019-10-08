export default [
  {
    path: '/lt/transferor/open-criticize-mark/look-report',
    name: 'lookReportLand-list',
    meta: {
      title: '查看评标报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/look-report/index'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/look-report/detail/:objectId',
    name: 'lookReportLand-detail',
    meta: {
      title: '查看报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/look-report/detail'], resolve)
  }
]
