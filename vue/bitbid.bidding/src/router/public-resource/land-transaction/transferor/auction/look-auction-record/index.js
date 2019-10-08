export default [
  {
    path: '/lt/transferor/auction/look-auction-record',
    name: 'lookAuctionRecord-list',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/auction/look-auction-record/index'], resolve)
  },
  {
    path: '/lt/transferor/auction/look-auction-record/detail/:objectId',
    name: 'lookAuctionRecord-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/auction/look-auction-record/detail'], resolve)
  }
]
