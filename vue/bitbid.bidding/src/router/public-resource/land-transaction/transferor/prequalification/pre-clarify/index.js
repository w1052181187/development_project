export default [
  {
    path: '/lt/transferor/prequalification/pre-clarify/index',
    name: 'prequalificationPreClar-list',
    meta: {
      title: '查看资格预审申请文件澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/pre-clarify/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/pre-clarify/dissent',
    name: 'prequalificationPreClar-dissent',
    meta: {
      title: '查看澄清',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/pre-clarify/dissent'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/pre-clarify/detail/:objectId',
    name: 'prequalificationPreClar-detail',
    meta: {
      title: '澄清详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/pre-clarify/detail'], resolve)
  }
]
