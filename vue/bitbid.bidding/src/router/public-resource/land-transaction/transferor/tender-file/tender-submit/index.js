export default [
  {
    path: '/lt/transferor/tender-file/tender-submit/index',
    name: 'submitDetailLand-list',
    meta: {
      title: '查看投标文件递交情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/tender-submit/index'], resolve)
  },
  {
    path: '/lt/transferor/tender-file/tender-submit/detail/:objectId',
    name: 'submitDetailLand-details',
    meta: {
      title: '递交详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/tender-file/tender-submit/submit-detail'], resolve)
  }
]
