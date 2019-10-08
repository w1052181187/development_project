export default [
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction',
    name: 'subResultsTranLand-list',
    meta: {
      title: '提交成交结果公示',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/index'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction/select-publicity',
    name: 'subResultsTranLand-announcement',
    meta: {
      title: '选择公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/select-publicity'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction/add',
    name: 'subResultsTranLand-add',
    meta: {
      title: '添加',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/add'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction/update',
    name: 'subResultsTranLand-update',
    meta: {
      title: '修改',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/update'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction/release/:objectId',
    name: 'subResultsTranLand-release',
    meta: {
      title: '发布',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/release'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/sub-results-transaction/detail/:objectId',
    name: 'subResultsTranLand-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/sub-results-transaction/detail'], resolve)
  }
]
