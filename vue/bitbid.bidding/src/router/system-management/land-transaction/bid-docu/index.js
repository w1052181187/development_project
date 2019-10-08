export default [
  {
    path: '/system-management/land-transaction/bid-docu/index',
    name: 'systemBidDoc-list',
    meta: {
      title: '招标文件备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/bid-docu/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/bid-docu/examine/:objectId',
    name: 'systemBidDoc-examine',
    meta: {
      title: '招标文件审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/bid-docu/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/bid-docu/detail/:objectId',
    name: 'systemBidDoc-detail',
    meta: {
      title: '招标文件查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/bid-docu/detail'], resolve)
  }
]
