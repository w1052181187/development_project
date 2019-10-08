import propurchasesfileindex from '@/pages/system-management/government-procurement/procurement-documents-purchases-file/index'
import propurchasesfiledetail from '@/pages/system-management/government-procurement/procurement-documents-purchases-file/detail'

export default[
  {
    path: '/system-management/government-procurement/procurement-documents-purchases-file',
    name: 'proPurchasesFile-list',
    meta: {
      title: '采购文件购买情况备案',
      layout: 'SystemManagement'
    },
    component: propurchasesfileindex
  },
  {
    path: '/system-management/government-procurement/procurement-documents-purchases-file/detail/:objectId',
    name: 'proPurchasesFile-detail',
    meta: {
      title: '采购文件购买情况备案详情',
      layout: 'SystemManagement'
    },
    component: propurchasesfiledetail
  }
]
