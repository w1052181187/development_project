export default [
  {
    path: '/lt/transferor/tender-file/look-tender-file/index',
    name: 'lookTenderFile-list',
    meta: {
      title: '查看招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-tender-file/index'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/look-tender-file/detail/:objectId',
    name: 'lookTenderFile-detail',
    meta: {
      title: '查看招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-tender-file/detail'], resolve)
  }
]
