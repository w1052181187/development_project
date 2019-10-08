export default [
  {
    path: '/lt/transferor/prequalification-file/look-clarify-file/index',
    name: 'lookClarifyFileLandPre-list',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-clarify-file/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/look-clarify-file/detail/:objectId',
    name: 'lookClarifyFileLandPre-detail',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/look-clarify-file/detail'], resolve)
  }
]
