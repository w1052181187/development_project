export default [
  {
    path: '/lt/transferor/prequalification/file-dissent/index',
    name: 'preFileDissent-list',
    meta: {
      title: '资格预审申请文件异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/file-dissent/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/file-dissent/apply-dissent',
    name: 'preFileDissent-apply',
    meta: {
      title: '申请文件异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/file-dissent/apply-dissent'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/file-dissent/dissent',
    name: 'preFileDissent-dissent',
    meta: {
      title: '异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/file-dissent/dissent'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/file-dissent/add',
    name: 'preFileDissent-add',
    meta: {
      title: '添加异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/file-dissent/add'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/file-dissent/detail/:objectId',
    name: 'preFileDissent-detail',
    meta: {
      title: '查看异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/file-dissent/detail'], resolve)
  }
]
