export default[
  {
    path: '/lt/assignee/post-bid-business/contract-sub',
    name: 'contractSubLt-list',
    meta: {
      title: '合同提交',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/contract-sub/index'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/submit',
    name: 'contractSubLt-submit',
    meta: {
      title: '提交合同',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/contract-sub/sub-contract'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/update',
    name: 'contractSubLt-update',
    meta: {
      title: '修改合同',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/contract-sub/update'], resolve)
  },
  {
    path: '/lt/assignee/post-bid-business/detail/:objectId',
    name: 'contractSubLt-detail',
    meta: {
      title: '查看合同',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/post-bid-business/contract-sub/detail'], resolve)
  }
]
