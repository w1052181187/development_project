export default [
  {
    path: '/lt/transferor/tender-file/look-dissent/index',
    name: 'lookDissent-list',
    meta: {
      title: '查看异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-dissent/index'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/look-dissent/dissent',
    name: 'lookDissent-dissent',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-dissent/dissent'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/look-dissent/accept',
    name: 'lookDissent-accept',
    meta: {
      title: '受理',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-dissent/accept'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/look-dissent/detail/:objectId',
    name: 'lookDissentLook-detail',
    meta: {
      title: '查看受理',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-dissent/look-accept'], resolve)
  }
]
