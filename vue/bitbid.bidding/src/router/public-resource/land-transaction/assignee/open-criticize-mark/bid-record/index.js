export default[
  {
    path: '/lt/assignee/open-criticize-mark/bid-record/index',
    name: 'bidRecordMark-list',
    meta: {
      title: '查看开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/bid-record/index'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/bid-record/detail/:objectId',
    name: 'bidRecordMark-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/bid-record/detail'], resolve)
  }
]
