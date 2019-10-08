import bidresultfile from '@/pages/system-management/government-procurement/bid-result-file/index'
import bidresultfileDetailOrCheck from '@/pages/system-management/government-procurement/bid-result-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/bid-result-file',
    name: 'bidResultFile-list',
    meta: {
      title: '中标结果公告备案',
      layout: 'SystemManagement'
    },
    component: bidresultfile
  },
  {
    path: '/system-management/government-procurement/bid-result-file/detail-or-check/:type/:objectId',
    name: 'bidResultFile-check',
    meta: {
      title: '中标结果公告备案',
      layout: 'SystemManagement'
    },
    component: bidresultfileDetailOrCheck
  }
]
