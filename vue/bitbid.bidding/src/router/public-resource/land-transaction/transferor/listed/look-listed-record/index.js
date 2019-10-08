export default [
  {
    path: '/lt/transferor/listed/look-listed-record',
    name: 'lookListedRecord-list',
    meta: {
      title: '查看限时竞价情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/listed/look-listed-record/index'], resolve)
  },
  {
    path: '/lt/transferor/listed/look-listed-record/detail/:objectId',
    name: 'lookListedRecord-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/listed/look-listed-record/detail'], resolve)
  }
]
