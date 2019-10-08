export default [
  {
    path: '/lt/transferor/prequalification-file/look-tender-file/index',
    name: 'lookTenderFilePre-list',
    meta: {
      title: '查看招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-tender-file/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/look-tender-file/detail/:objectId',
    name: 'lookTenderFilePre-detail',
    meta: {
      title: '查看招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-tender-file/detail'], resolve)
  }
]
