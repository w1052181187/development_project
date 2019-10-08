import prepurchasesfileindex from '@/pages/system-management/government-procurement/pre-qualification-purchases-file/index'
import prepurchasesfiledetail from '@/pages/system-management/government-procurement/pre-qualification-purchases-file/detail'

export default[
  {
    path: '/system-management/government-procurement/pre-qualification-purchases-file',
    name: 'prePurchasesFile-list',
    meta: {
      title: '资格预审文件购买情况备案',
      layout: 'SystemManagement'
    },
    component: prepurchasesfileindex
  },
  {
    path: '/system-management/government-procurement/pre-qualification-purchases-file/detail/:objectId',
    name: 'prePurchasesFile-detail',
    meta: {
      title: '资格预审文件购买情况备案详情',
      layout: 'SystemManagement'
    },
    component: prepurchasesfiledetail
  }
]
