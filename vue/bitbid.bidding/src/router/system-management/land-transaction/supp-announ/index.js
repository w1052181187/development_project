import suppAnnounindexLand from '@/pages/system-management/land-transaction/supp-announ/index'
import suppAnnounExamineLand from '@/pages/system-management/land-transaction/supp-announ/examine'
import suppAnnounDetailLand from '@/pages/system-management/land-transaction/supp-announ/detail'

export default[
  {
    path: '/system-management/land-transaction/supp-announ/index',
    name: 'landSuppAnnoun-list',
    meta: {
      title: '补充公告备案',
      layout: 'SystemManagement'
    },
    component: suppAnnounindexLand
  },
  {
    path: '/system-management/land-transaction/supp-announ/examine/:objectId',
    name: 'landSuppAnnoun-examine',
    meta: {
      title: '补充公告审批',
      layout: 'SystemManagement'
    },
    component: suppAnnounExamineLand
  },
  {
    path: '/system-management/land-transaction/supp-announ/detail/:objectId',
    name: 'landSuppAnnoun-detail',
    meta: {
      title: '补充公告详情',
      layout: 'SystemManagement'
    },
    component: suppAnnounDetailLand
  }
]
