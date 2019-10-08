export default [
  {
    path: '/lt/assignee/listed/index',
    name: 'lookListedBidAss-list',
    meta: {
      title: '挂牌竞价',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/listed/look-listed-bid/index'], resolve)
  },
  {
    path: '/lt/assignee/listed/look-listed-bid/detail/:objectId',
    name: 'lookListedBidAss-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/listed/look-listed-bid/detail'], resolve)
  }
]
