export default [
  {
    path: '/lt/transferor/business-confirmation/confirm-people',
    name: 'confirmPeopleLand-list',
    meta: {
      title: '确认竞得人',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/confirm-people/index'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/confirm-people/confirm/:objectId',
    name: 'confirmPeopleLand-confirm',
    meta: {
      title: '确认竞得人',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/confirm-people/confirm-people'], resolve)
  },
  {
    path: '/lt/transferor/business-confirmation/confirm-people/detail/:objectId',
    name: 'confirmPeopleLand-detail',
    meta: {
      title: '查看确认竞得人',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/business-confirmation/confirm-people/detail'], resolve)
  }
]
