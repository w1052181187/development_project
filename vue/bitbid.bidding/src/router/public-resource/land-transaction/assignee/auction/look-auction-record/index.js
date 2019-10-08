export default [
  {
    path: '/lt/assignee/auction/look-auction-record',
    name: 'assigneeAuctionRecord-list',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction/look-auction-record/index'], resolve)
  },
  {
    path: '/lt/assignee/auction/look-auction-record/detail/:objectId',
    name: 'assigneeAuctionRecord-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/auction/look-auction-record/detail'], resolve)
  }
]
