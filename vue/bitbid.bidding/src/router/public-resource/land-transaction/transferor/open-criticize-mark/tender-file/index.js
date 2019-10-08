export default [
  {
    path: '/lt/transferor/open-criticize-mark/tender-file/index',
    name: 'tenderFile-list',
    meta: {
      title: '投标文件异议',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/tender-file/index'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/tender-file/question',
    name: 'tenderFile-question',
    meta: {
      title: '质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/tender-file/question'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/tender-file/add',
    name: 'tenderFile-add',
    meta: {
      title: '添加质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/tender-file/add'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/tender-file/update',
    name: 'tenderFile-update',
    meta: {
      title: '修改质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/tender-file/update'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/tender-file/detail/:objectId',
    name: 'tenderFile-detail',
    meta: {
      title: '查看质疑',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/tender-file/detail'], resolve)
  }
]
