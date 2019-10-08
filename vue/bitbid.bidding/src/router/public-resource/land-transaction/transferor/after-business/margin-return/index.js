export default [
  {
    path: '/lt/transferor/after-business/margin-return',
    name: 'afterBusiness-list',
    meta: {
      title: '提交协议出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/margin-return/index'], resolve)
  },
  {
    path: '/lt/transferor/after-business/margin-return/approve',
    name: 'afterBusiness-approve',
    meta: {
      title: '审批申请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/margin-return/approve'], resolve)
  },
  {
    path: '/lt/transferor/after-business/margin-return/detail/:objectId',
    name: 'afterBusiness-detail',
    meta: {
      title: '查看申请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/margin-return/detail'], resolve)
  },
  {
    path: '/lt/transferor/after-business/margin-return/refund',
    name: 'afterBusiness-refund',
    meta: {
      title: '退回保证金',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/margin-return/refund'], resolve)
  }
]
