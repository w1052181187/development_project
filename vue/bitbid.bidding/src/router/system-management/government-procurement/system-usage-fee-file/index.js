import systemusagefeefileindex from '@/pages/system-management/government-procurement/system-usage-fee-file/index'
import systemusagefeefiledetail from '@/pages/system-management/government-procurement/system-usage-fee-file/detail'

export default[
  {
    path: '/system-management/government-procurement/system-usage-fee-file',
    name: 'systemUsageFeeFile-list',
    meta: {
      title: '系统使用费缴纳情况备案',
      layout: 'SystemManagement'
    },
    component: systemusagefeefileindex
  },
  {
    path: '/system-management/government-procurement/system-usage-fee-file/detail/:objectId',
    name: 'systemUsageFeeFile-detail',
    meta: {
      title: '系统使用费缴纳情况备案详情',
      layout: 'SystemManagement'
    },
    component: systemusagefeefiledetail
  }
]
