export default[
  {
    path: '/lt/assignee/auction-listing/index',
    name: 'auctionListingLt-list',
    meta: {
      title: '下载出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction-listing/index'], resolve)
  },
  {
    path: '/lt/assignee/auction-listing/detail/:objectId',
    name: 'auctionListingLt-detail',
    meta: {
      title: '下载',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction-listing/detail'], resolve)
  }
]
