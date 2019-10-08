export default [
  {
    path: '/lt/transferor/prequalification-file/tender-submit/index',
    name: 'submitDetailLandPre-list',
    meta: {
      title: '查看投标文件递交情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/tender-submit/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification-file/tender-submit/detail/:objectId',
    name: 'submitDetailLandPre-details',
    meta: {
      title: '递交详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification-file/tender-submit/submit-detail'], resolve)
  }
]
