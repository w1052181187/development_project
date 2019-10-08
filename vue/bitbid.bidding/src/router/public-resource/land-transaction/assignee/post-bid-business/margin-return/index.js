export default[
  {
    path: '/lt/assignee/post-bid-business/margin-return',
    name: 'marginReturnApplyLt-list',
    meta: {
      title: '申请保证金退回',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/margin-return/index'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/margin-return/refund',
    name: 'marginReturnApplyLt-refund',
    meta: {
      title: '提交申请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/margin-return/refund'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/margin-return/update',
    name: 'marginReturnApplyLt-update',
    meta: {
      title: '修改申请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/margin-return/update'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/margin-return/detail/:objectId',
    name: 'marginReturnApplyLt-detail',
    meta: {
      title: '查看申请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/margin-return/detail'], resolve)
  }
]
