export default[
  {
    path: '/lt/assignee/prequalification/tender-documents/index',
    name: 'tenderDocumentsPre-list',
    meta: {
      title: '资格预审申请文件澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/tender-documents/index'], resolve)
  },
  {
    path: '/lt/assignee/prequalification/tender-documents/look-question',
    name: 'tenderDocumentsPre-question',
    meta: {
      title: '澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/tender-documents/look-question'], resolve)
  },
  {
    path: '/lt/assignee/prequalification/tender-documents/clarify',
    name: 'tenderDocumentsPre-clarify',
    meta: {
      title: '添加澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/tender-documents/clarify'], resolve)
  },
  {
    path: '/lt/assignee/prequalification/tender-documents/detail/:objectId',
    name: 'tenderDocumentsPre-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/prequalification/tender-documents/detail'], resolve)
  }
]
