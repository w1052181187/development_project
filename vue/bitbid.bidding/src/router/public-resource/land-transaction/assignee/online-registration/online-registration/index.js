export default[
  {
    path: '/lt/assignee/online-registration/online-registration',
    name: 'onlineRegLt-list',
    meta: {
      title: '网上报名',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/index'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/online-registration/choose-project',
    name: 'onlineRegLt-project',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/choose-project'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/look-registration/submit',
    name: 'onlineRegLt-submit',
    meta: {
      title: '提交报名信息',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/submit'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/look-registration/update',
    name: 'onlineRegLt-update',
    meta: {
      title: '修改报名信息',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/update'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/look-registration/detail/:objectId',
    name: 'onlineRegLt-detail',
    meta: {
      title: '查看报名信息',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/detail'], resolve)
  },
  {
    path: '/lt/assignee/online-registration/look-registration/see-receipt',
    name: 'onlineRegLt-receipt',
    meta: {
      title: '查看回执',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/online-registration/online-registration/see-receipt'], resolve)
  }
]
