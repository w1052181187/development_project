import transferAnnounindexLand from '@/pages/system-management/land-transaction/transfer-announ/index'
import transferAnnounExamineLand from '@/pages/system-management/land-transaction/transfer-announ/examine'
import transferAnnounDetailLand from '@/pages/system-management/land-transaction/transfer-announ/detail'

export default[
  {
    path: '/system-management/land-transaction/transfer-announ/index',
    name: 'landTransferAnnoun-list',
    meta: {
      title: '出让公告备案',
      layout: 'SystemManagement'
    },
    component: transferAnnounindexLand
  },
  {
    path: '/system-management/land-transaction/transfer-announ/examine/:objectId',
    name: 'landTransferAnnoun-examine',
    meta: {
      title: '出让公告审批',
      layout: 'SystemManagement'
    },
    component: transferAnnounExamineLand
  },
  {
    path: '/system-management/land-transaction/transfer-announ/detail/:objectId',
    name: 'landTransferAnnoun-detail',
    meta: {
      title: '出让公告详情',
      layout: 'SystemManagement'
    },
    component: transferAnnounDetailLand
  }
]
