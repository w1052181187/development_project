export default [
  {
    path: '/lt/transferor/auction/look-auction-bid',
    name: 'lookAuctionBid-list',
    meta: {
      title: '查看拍卖竞价情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/auction/look-auction-bid/index'], resolve)
  },
  {
    path: '/lt/transferor/auction/look-auction-bid/detail/:objectId',
    name: 'lookAuctionBid-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/auction/look-auction-bid/detail'], resolve)
  }
]
