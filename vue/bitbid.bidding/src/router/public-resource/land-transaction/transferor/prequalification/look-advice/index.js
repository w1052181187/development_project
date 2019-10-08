export default [
  {
    path: '/lt/transferor/prequalification/look-advice/index',
    name: 'preLookAdvice-list',
    meta: {
      title: '查看资格预审结果通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/look-advice/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/look-advice/detail/:objectId',
    name: 'preLookAdvice-detail',
    meta: {
      title: '查看报告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/look-advice/detail'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/look-advice/review-result/:objectId',
    name: 'preLookAdvice-result',
    meta: {
      title: '查看审查结果',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/look-advice/review-result'], resolve)
  }
]
