import correctnotice from '@/pages/system-management/government-procurement/correct-notice-file/index'
import correctnoticeDetailOrCheck from '@/pages/system-management/government-procurement/correct-notice-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/correct-notice-file',
    name: 'correctNotice-list',
    meta: {
      title: '更正公告备案',
      layout: 'SystemManagement'
    },
    component: correctnotice
  },
  {
    path: '/system-management/government-procurement/correct-notice-file/detail-or-check/:type/:objectId',
    name: 'correctNotice-check',
    meta: {
      title: '更正公告备案',
      layout: 'SystemManagement'
    },
    component: correctnoticeDetailOrCheck
  }
]
