export default[
  {
    path: '/lt/assignee/prequalification-file/auction-listing/index',
    name: 'auctionListingTenderPre-list',
    meta: {
      title: '下载资格预审文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/auction-listing/index'], resolve)
  },
  {
    path: '/lt/assignee/prequalification-file/auction-listing/detail/:objectId',
    name: 'auctionListingTenderPre-detail',
    meta: {
      title: '下载',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/auction-listing/detail'], resolve)
  }
]
