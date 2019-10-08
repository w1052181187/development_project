export default [
  {
    path: '/lt/transferor/look-agree-ann',
    name: 'lookAgreeAnn-list',
    meta: {
      title: '查看协议出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/look-agree-ann/index'], resolve)
  },
  {
    path: '/lt/transferor/look-agree-ann/detail/:objectId',
    name: 'lookAgreeAnn-detail',
    meta: {
      title: '查看协议出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/look-agree-ann/detail'], resolve)
  }
]
