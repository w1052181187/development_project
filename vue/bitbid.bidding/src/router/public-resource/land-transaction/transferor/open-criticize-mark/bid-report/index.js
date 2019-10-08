export default [
  {
    path: '/lt/transferor/open-criticize-mark/bid-report',
    name: 'bidReportLand-list',
    meta: {
      title: '提交评标报告及中标候选人',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/bid-report/index'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/bid-report/add',
    name: 'bidReportLand-add',
    meta: {
      title: '提交报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/bid-report/add'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/bid-report/detail/:objectId',
    name: 'bidReportLand-detail',
    meta: {
      title: '查看报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/bid-report/detail'], resolve)
  }
]
