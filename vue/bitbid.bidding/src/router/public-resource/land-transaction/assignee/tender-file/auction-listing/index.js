export default[
  {
    path: '/lt/assignee/tender-file/auction-listing/index',
    name: 'auctionListingTender-list',
    meta: {
      title: '下载出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/auction-listing/index'], resolve)
  },
  {
    path: '/lt/assignee/tender-file/auction-listing/detail/:objectId',
    name: 'auctionListingTender-detail',
    meta: {
      title: '下载',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/auction-listing/detail'], resolve)
  }
]
