export default[
  {
    path: '/lt/assignee/prequalification-file/raise-dissent/index',
    name: 'raiseDissentTenderPre-list',
    meta: {
      title: '提出异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/raise-dissent/index'], resolve)
  },
  {
    path: '/lt/assignee/prequalification-file/raise-dissent/question',
    name: 'raiseDissentTenderPre-question',
    meta: {
      title: '质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/raise-dissent/question'], resolve)
  },
  {
    path: '/lt/assignee/prequalification-file/raise-dissent/add',
    name: 'raiseDissentTenderPre-add',
    meta: {
      title: '添加质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/raise-dissent/add'], resolve)
  },
  {
    path: '/lt/assignee/prequalification-file/raise-dissent/detail/:objectId',
    name: 'raiseDissentTenderPre-detail',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/raise-dissent/detail'], resolve)
  }
]
