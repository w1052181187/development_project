export default [
  {
    path: '/lt/assignee/business-confir/look-results/index',
    name: 'lookResultsLt-list',
    meta: {
      title: '查看结果公示',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/business-confir/look-results-transaction/index'], resolve)
  },
  {
    path: '/lt/assignee/business-confir/look-results/detail/:objectId',
    name: 'lookResultsLt-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/business-confir/look-results-transaction/detail'], resolve)
  }
]
