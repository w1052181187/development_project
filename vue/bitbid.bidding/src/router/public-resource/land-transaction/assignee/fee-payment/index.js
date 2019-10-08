export default [
  {
    path: '/lt/assignee/fee-payment',
    name: 'feePaymentLt-list',
    meta: {
      title: '缴纳竞买保证金',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/fee-payment/index'], resolve)
  },
  {
    path: '/lt/assignee/fee-payment/payment',
    name: 'feePaymentLt-payment',
    meta: {
      title: '支付',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/fee-payment/payment'], resolve)
  },
  {
    path: '/lt/assignee/fee-payment/detail/:objectId',
    name: 'feePaymentLt-detail',
    meta: {
      title: '缴纳详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/fee-payment/detail'], resolve)
  },
  {
    path: '/lt/assignee/fee-payment/receipt/:objectId',
    name: 'feePaymentLt-receipt',
    meta: {
      title: '查看回执',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/fee-payment/look-receipt'], resolve)
  }
]
