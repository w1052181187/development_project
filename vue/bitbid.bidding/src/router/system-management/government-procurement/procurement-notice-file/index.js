import procurementnotice from '@/pages/system-management/government-procurement/procurement-notice-file/index'
import procurementNoticeDetailOrCheck from '@/pages/system-management/government-procurement/procurement-notice-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/procurement-notice-file',
    name: 'procurementNotice-list',
    meta: {
      title: '采购公告备案',
      layout: 'SystemManagement'
    },
    component: procurementnotice
  },
  {
    path: '/system-management/government-procurement/procurement-notice-file/detail-or-check/:type/:objectId',
    name: 'procurementNotice-check',
    meta: {
      title: '采购公告备案',
      layout: 'SystemManagement'
    },
    component: procurementNoticeDetailOrCheck
  }
]
