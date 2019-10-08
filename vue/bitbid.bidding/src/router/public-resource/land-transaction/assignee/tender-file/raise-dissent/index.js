export default[
  {
    path: '/lt/assignee/tender-file/raise-dissent/index',
    name: 'raiseDissentTender-list',
    meta: {
      title: '提出异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/raise-dissent/index'], resolve)
  },
  {
    path: '/lt/assignee/tender-file/raise-dissent/question',
    name: 'raiseDissentTender-question',
    meta: {
      title: '质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/raise-dissent/question'], resolve)
  },
  {
    path: '/lt/assignee/tender-file/raise-dissent/add',
    name: 'raiseDissentTender-add',
    meta: {
      title: '添加质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/raise-dissent/add'], resolve)
  },
  {
    path: '/lt/assignee/tender-file/raise-dissent/detail/:objectId',
    name: 'raiseDissentTender-detail',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/raise-dissent/detail'], resolve)
  }
]
