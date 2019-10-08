import resultindex from '@/pages/system-management/mineral-rights/result-disclosure-file/index'
import resultaudit from '@/pages/system-management/mineral-rights/result-disclosure-file/check'
import resultdetail from '@/pages/system-management/mineral-rights/result-disclosure-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/result-disclosure-file',
    name: 'resultDisclosureFile-list',
    meta: {
      title: '成交结果公示备案',
      layout: 'SystemManagement'
    },
    component: resultindex
  },
  {
    path: '/system-management/mineral-rights/result-disclosure-file/check',
    name: 'resultDisclosureFile-check',
    meta: {
      title: '成交结果公示备案审核',
      layout: 'SystemManagement'
    },
    component: resultaudit
  },
  {
    path: '/system-management/mineral-rights/result-disclosure-file/detail/:objectId',
    name: 'resultDisclosureFile-detail',
    meta: {
      title: '成交结果公示备案详情',
      layout: 'SystemManagement'
    },
    component: resultdetail
  }
]
