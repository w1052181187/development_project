export default [
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/index',
    name: 'subClarifyFileLandPre-list',
    meta: {
      title: '提交澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/select-announ',
    name: 'subClarifyFileLandPre-announ',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/select-announ'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/add',
    name: 'subClarifyFileLandPre-add',
    meta: {
      title: '添加澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/add'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/update',
    name: 'subClarifyFileLandPre-update',
    meta: {
      title: '修改澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/update'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/detail/:objectId',
    name: 'subClarifyFileLandPre-detail',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/detail'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-clarify-file/release/:objectId',
    name: 'subClarifyFileLandPre-release',
    meta: {
      title: '发布澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-clarify-file/release'], resolve)
  }
]
