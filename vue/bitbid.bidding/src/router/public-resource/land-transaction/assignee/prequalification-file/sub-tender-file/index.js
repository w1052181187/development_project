export default[
  {
    path: '/lt/assignee/prequalification-file/sub-tender-file/index',
    name: 'subTenderFileIndexPre-list',
    meta: {
      title: '递交资格预审申请文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/sub-tender-file/index'], resolve)
  },
  {
    path: '/lt/assignee/prequalification-file/sub-tender-file/submit/:objectId',
    name: 'subTenderFilePre-submit',
    meta: {
      title: '递交',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification-file/sub-tender-file/submit'], resolve)
  }
]
