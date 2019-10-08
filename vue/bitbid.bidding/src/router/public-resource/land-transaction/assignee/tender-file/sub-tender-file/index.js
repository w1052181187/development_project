export default[
  {
    path: '/lt/assignee/tender-file/sub-tender-file/index',
    name: 'subTenderFileIndex-list',
    meta: {
      title: '递交投标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/sub-tender-file/index'], resolve)
  },
  {
    path: '/lt/assignee/tender-file/sub-tender-file/submit/:objectId',
    name: 'subTenderFile-submit',
    meta: {
      title: '递交',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/tender-file/sub-tender-file/submit'], resolve)
  }
]
