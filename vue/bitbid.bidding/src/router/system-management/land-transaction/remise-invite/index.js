import remiseindexLand from '@/pages/system-management/land-transaction/remise-invite/index'
import remiseExamineLand from '@/pages/system-management/land-transaction/remise-invite/examine'
import remiseDetailLand from '@/pages/system-management/land-transaction/remise-invite/detail'

export default[
  {
    path: '/system-management/land-transaction/remise-invite/index',
    name: 'landRemiseInvite-list',
    meta: {
      title: '出让邀请书备案',
      layout: 'SystemManagement'
    },
    component: remiseindexLand
  },
  {
    path: '/system-management/land-transaction/remise-invite/examine/:objectId',
    name: 'landRemiseInvite-examine',
    meta: {
      title: '出让邀请书审批',
      layout: 'SystemManagement'
    },
    component: remiseExamineLand
  },
  {
    path: '/system-management/land-transaction/remise-invite/detail/:objectId',
    name: 'landRemiseInvite-detail',
    meta: {
      title: '出让邀请书详情',
      layout: 'SystemManagement'
    },
    component: remiseDetailLand
  }
]
