export default [
  {
    path: '/system-management/land-transaction/clarify/index',
    name: 'systemClarify-list',
    meta: {
      title: '澄清/修改文件备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/clarify/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/clarify/examine/:objectId',
    name: 'systemClarify-examine',
    meta: {
      title: '澄清/修改文件审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/clarify/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/clarify/detail/:objectId',
    name: 'systemClarify-detail',
    meta: {
      title: '澄清/修改文件查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/clarify/detail'], resolve)
  }
]
