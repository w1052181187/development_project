export default [
  {
    path: '/lt/assignee/business-confir/receive-sales/index',
    name: 'receiveSalesLt-list',
    meta: {
      title: '接收成交确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/business-confir/receive-sales-confirmation/index'], resolve)
  },
  {
    path: '/lt/assignee/business-confir/receive-sales/confirm/:objectId',
    name: 'receiveSalesLt-confirm',
    meta: {
      title: '确认',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/business-confir/receive-sales-confirmation/confirm'], resolve)
  },
  {
    path: '/lt/assignee/business-confir/receive-sales/detail/:objectId',
    name: 'receiveSalesLt-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/business-confir/receive-sales-confirmation/detail'], resolve)
  }
]
