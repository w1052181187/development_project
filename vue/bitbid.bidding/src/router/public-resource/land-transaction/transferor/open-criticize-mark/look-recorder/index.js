export default [
  {
    path: '/lt/transferor/open-criticize-mark/look-recorder',
    name: 'lookRecorder-list',
    meta: {
      title: '查看开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/look-recorder/index'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/look-recorder/detail/:objectId',
    name: 'lookRecorder-detail',
    meta: {
      title: '查看开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/look-recorder/detail'], resolve)
  }
]
