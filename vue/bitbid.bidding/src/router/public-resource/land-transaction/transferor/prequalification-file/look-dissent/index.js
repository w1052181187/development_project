export default [
  {
    path: '/lt/transferor/prequalification-file/look-dissent/index',
    name: 'lookDissentPre-list',
    meta: {
      title: '查看异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-dissent/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/look-dissent/dissent',
    name: 'lookDissentPre-dissent',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-dissent/dissent'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/look-dissent/accept',
    name: 'lookDissentPre-accept',
    meta: {
      title: '受理',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-dissent/accept'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/look-dissent/detail/:objectId',
    name: 'lookDissentLookPre-detail',
    meta: {
      title: '查看受理',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-dissent/look-accept'], resolve)
  }
]
