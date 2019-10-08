export default[
  {
    path: '/lt/assignee/prequalification/look-notification/index',
    name: 'lookNotificationPre-list',
    meta: {
      title: '查看资格预审结果通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/look-notification/index'], resolve)
  },
  {
    path: '/lt/assignee/prequalification/look-notification/detail/:objectId',
    name: 'lookNotificationPre-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/look-notification/detail'], resolve)
  }
]
