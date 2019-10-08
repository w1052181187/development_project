export default [
  {
    path: '/lt/transferor/sub-agree-ann',
    name: 'subAgreeAnn-list',
    meta: {
      title: '提交协议出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/index'], resolve)
  },
  {
    path: '/lt/transferor/sub-agree-ann/selection-project',
    name: 'subAgreeAnn-project',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/selection-project'], resolve)
  },
  {
    path: '/lt/transferor/sub-agree-ann/add',
    name: 'subAgreeAnn-add',
    meta: {
      title: '添加出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/add'], resolve)
  },
  {
    path: '/lt/transferor/sub-agree-ann/update',
    name: 'subAgreeAnn-update',
    meta: {
      title: '修改出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/update'], resolve)
  },
  {
    path: '/lt/transferor/sub-agree-ann/detail/:objectId',
    name: 'subAgreeAnn-detail',
    meta: {
      title: '查看出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/detail'], resolve)
  },
  {
    path: '/lt/transferor/sub-agree-ann/release/:objectId',
    name: 'subAgreeAnn-release',
    meta: {
      title: '发布出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/agree-announ/sub-agree-ann/release'], resolve)
  }
]
