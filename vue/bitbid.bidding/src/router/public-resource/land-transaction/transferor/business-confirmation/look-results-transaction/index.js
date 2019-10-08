export default [
  {
    path: '/lt/transferor/business-confirmation/look-results-transaction',
    name: 'lookResultsTranLand-list',
    meta: {
      title: '提交成交结果公示',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/look-results-transaction/index'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/look-results-transaction/detail/:objectId',
    name: 'lookResultsTranLand-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/look-results-transaction/detail'], resolve)
  }
]
