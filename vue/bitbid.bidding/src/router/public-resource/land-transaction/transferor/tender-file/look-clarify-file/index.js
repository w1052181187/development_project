export default [
  {
    path: '/lt/transferor/tender-file/look-clarify-file/index',
    name: 'lookClarifyFileLand-list',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-clarify-file/index'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/look-clarify-file/detail/:objectId',
    name: 'lookClarifyFileLand-detail',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/look-clarify-file/detail'], resolve)
  }
]
