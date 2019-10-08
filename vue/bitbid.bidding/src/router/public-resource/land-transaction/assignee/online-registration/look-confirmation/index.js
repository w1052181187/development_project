export default[
  {
    path: '/lt/assignee/online-registration/look-confirmation',
    name: 'lookConfirmationLan-list',
    meta: {
      title: '查看资格确认书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/look-confirmation/index'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/look-confirmation/detail/:objectId',
    name: 'lookConfirmationLan-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/look-confirmation/detail'], resolve)
  }
]
