export default [
  {
    path: '/lt/assignee/auction/index',
    name: 'auctionBidLt-list',
    meta: {
      title: '拍卖竞价',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction/look-auction-bid/index'], resolve)
  },
  {
    path: '/lt/assignee/auction/detail/:objectId',
    name: 'auctionBidLt-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction/look-auction-bid/detail'], resolve)
  }
]
