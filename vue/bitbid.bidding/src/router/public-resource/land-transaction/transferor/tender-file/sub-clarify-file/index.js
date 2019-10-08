export default [
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/index',
    name: 'subClarifyFileLand-list',
    meta: {
      title: '提交澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/index'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/select-announ',
    name: 'subClarifyFileLand-announ',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/select-announ'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/add',
    name: 'subClarifyFileLand-add',
    meta: {
      title: '添加澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/add'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/update',
    name: 'subClarifyFileLand-update',
    meta: {
      title: '修改澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/update'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/detail/:objectId',
    name: 'subClarifyFileLand-detail',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/detail'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/sub-clarify-file/release/:objectId',
    name: 'subClarifyFileLand-release',
    meta: {
      title: '发布澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/sub-clarify-file/release'], resolve)
  }
]
