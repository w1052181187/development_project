export default[
  {
    path: '/lt/assignee/open-criticize-mark/tender-documents/index',
    name: 'tenderDocumentsMark-list',
    meta: {
      title: '投标文件澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/tender-documents/index'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/tender-documents/look-question',
    name: 'tenderDocumentsMark-question',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/tender-documents/look-question'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/tender-documents/clarify',
    name: 'tenderDocumentsMark-clarify',
    meta: {
      title: '澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/tender-documents/clarify'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/tender-documents/detail/:objectId',
    name: 'tenderDocumentsMark-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/tender-documents/detail'], resolve)
  }
]
