export default [
  {
    path: '/system-management/land-transaction/tender-clarify/index',
    name: 'systemTenClarify-list',
    meta: {
      title: '招标澄清/修改文件备案',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/tender-clarify/index'], resolve)
  },
  {
    path: '/system-management/land-transaction/tender-clarify/examine/:objectId',
    name: 'systemTenClarify-examine',
    meta: {
      title: '招标澄清/修改文件审批',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/tender-clarify/examine'], resolve)
  },
  {
    path: '/system-management/land-transaction/tender-clarify/detail/:objectId',
    name: 'systemTenClarify-detail',
    meta: {
      title: '招标澄清/修改文件查看',
      layout: 'SystemManagement'
    },
    component: resolve => require(['@/pages/system-management/land-transaction/tender-clarify/detail'], resolve)
  }
]
