import letterofacceptancefile from '@/pages/system-management/government-procurement/letter-of-acceptance-file/index'
import letterofacceptancefiledetail from '@/pages/system-management/government-procurement/letter-of-acceptance-file/detail'

export default[
  {
    path: '/system-management/government-procurement/letter-of-acceptance-file',
    name: 'letterOfAcceptanceFile-list',
    meta: {
      title: '中标确认函备案',
      layout: 'SystemManagement'
    },
    component: letterofacceptancefile
  },
  {
    path: '/system-management/government-procurement/letter-of-acceptance-file/detail/:objectId',
    name: 'letterOfAcceptanceFile-detail',
    meta: {
      title: '中标确认函备案详情',
      layout: 'SystemManagement'
    },
    component: letterofacceptancefiledetail
  }
]
