export default [
  {
    path: '/lt/transferor/open-criticize-mark/opening-recorder',
    name: 'openingRecorder-list',
    meta: {
      title: '提交开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/opening-recorder/index'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/opening-recorder/select-plot',
    name: 'openingRecorder-plot',
    meta: {
      title: '选择地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/opening-recorder/select-plot'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/opening-recorder/add',
    name: 'openingRecorder-add',
    meta: {
      title: '添加开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/opening-recorder/add'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/opening-recorder/update',
    name: 'openingRecorder-update',
    meta: {
      title: '修改开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/opening-recorder/update'], resolve)
  },
  {
    path: '/lt/transferor/open-criticize-mark/opening-recorder/detail/:objectId',
    name: 'openingRecorder-detail',
    meta: {
      title: '查看开标记录表',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: resolve => require(['@/pages/land-transaction/transferor/open-criticize-mark/opening-recorder/detail'], resolve)
  }
]
