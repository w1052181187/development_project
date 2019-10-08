export default [
  {
    path: '/lt/transferor/prequalification/send-advice/index',
    name: 'preSendAdvice-list',
    meta: {
      title: '发出资格预审结果通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/send-advice/index'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/send-advice/send-review-result',
    name: 'preSendAdvice-result',
    meta: {
      title: '发出审查结果',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/send-advice/send-review-result'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/send-advice/send',
    name: 'preSendAdvice-send',
    meta: {
      title: '发出通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/send-advice/send'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/send-advice/update',
    name: 'preSendAdvice-update',
    meta: {
      title: '变更通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/send-advice/update'], resolve)
  },
  {
    path: '/lt/transferor/prequalification/send-advice/detail/:objectId',
    name: 'preSendAdvice-detail',
    meta: {
      title: '查看通知书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/prequalification/send-advice/detail'], resolve)
  }
]
