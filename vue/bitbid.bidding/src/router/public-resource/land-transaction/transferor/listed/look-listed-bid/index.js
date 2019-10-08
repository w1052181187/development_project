export default [
  {
    path: '/lt/transferor/listed/look-listed-bid',
    name: 'lookListedBid-list',
    meta: {
      title: '查看挂牌竞价情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/listed/look-listed-bid/index'], resolve)
  },
  {
    path: '/lt/transferor/listed/look-listed-bid/detail/:objectId',
    name: 'lookListedBid-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/listed/look-listed-bid/detail'], resolve)
  }
]
