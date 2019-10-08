export default [
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/index',
    name: 'subTenderFilePre-list',
    meta: {
      title: '提交资格预审文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/select-announ',
    name: 'subTenderFilePre-announ',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/select-announ'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/add',
    name: 'subTenderFilePre-add',
    meta: {
      title: '添加招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/add'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/update',
    name: 'subTenderFilePre-update',
    meta: {
      title: '修改招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/update'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/detail/:objectId',
    name: 'subTenderFilePre-detail',
    meta: {
      title: '查看招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/detail'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/sub-tender-file/release/:objectId',
    name: 'subTenderFilePre-release',
    meta: {
      title: '发布招标文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/sub-tender-file/release'], resolve)
  }
]
