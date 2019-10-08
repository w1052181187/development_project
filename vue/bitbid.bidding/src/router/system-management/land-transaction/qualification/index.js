import systemQualifcation from '@/pages/system-management/land-transaction/qualification/index'
import systemQualifcationDetail from '@/pages/system-management/land-transaction/qualification/detail'

export default [
  {
    path: '/system-management/land-transaction/qualification/index',
    name: 'landQualifcation-list',
    meta: {
      title: '资格确认情况备案',
      layout: 'SystemManagement'
    },
    component: systemQualifcation
  },
  {
    path: '/system-management/land-transaction/qualification/detail/:objectId',
    name: 'landQualifcation-detail',
    meta: {
      title: '资格确认详情',
      layout: 'SystemManagement'
    },
    component: systemQualifcationDetail
  }
]
