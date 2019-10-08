export default [
  {
    path: '/system-management/land-transaction/prequalification-file/index',
    name: 'systemPrequalification-list',
    meta: {
      title: '资格预审文件备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/prequalification-file/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/prequalification-file/examine/:objectId',
    name: 'systemPrequalification-examine',
    meta: {
      title: '资格预审文件审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/prequalification-file/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/prequalification-file/detail/:objectId',
    name: 'systemPrequalification-detail',
    meta: {
      title: '资格预审文件查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/prequalification-file/detail'], resolve)
  }
]
