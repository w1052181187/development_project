export default[
  {
    path: '/lt/assignee/open-criticize-mark/join-bid/index',
    name: 'joinBidMark-index',
    meta: {
      title: '参加开标会',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/join-bid/index'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/join-bid/opening',
    name: 'joinBidMark-opening',
    meta: {
      title: '网上开标室',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/join-bid/opening'], resolve)
  },
  {
    path: '/lt/assignee/open-criticize-mark/join-bid/decryption',
    name: 'joinBidMark-decryption',
    meta: {
      title: '网上解密',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/assignee/open-criticize-mark/join-bid/decryption'], resolve)
  }
]
