export default [
  {
    path: '/lt/transferor/business-confirmation/issue-sales-confirmation',
    name: 'issueSalesCon-list',
    meta: {
      title: '发出成交确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/issue-sales-confirmation/index'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/issue-sales-confirmation/detail/:objectId',
    name: 'issueSalesCon-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/issue-sales-confirmation/detail'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/issue-sales-confirmation/submission/:objectId',
    name: 'issueSalesCon-submission',
    meta: {
      title: '提交',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/issue-sales-confirmation/submission'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/issue-sales-confirmation/send-out/:objectId',
    name: 'issueSalesCon-send',
    meta: {
      title: '发送',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/issue-sales-confirmation/send-out'], resolve)
  }
]
