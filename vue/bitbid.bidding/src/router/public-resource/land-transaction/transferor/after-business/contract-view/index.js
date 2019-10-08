export default [
  {
    path: '/lt/transferor/after-business/contract-view',
    name: 'afterContractView-list',
    meta: {
      title: '合同查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/contract-view/index'], resolve)
  },
  {
    path: '/lt/transferor/after-business/contract-view/detail/:objectId',
    name: 'afterContractView-detail',
    meta: {
      title: '查看合同',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/after-business/contract-view/detail'], resolve)
  }
]
