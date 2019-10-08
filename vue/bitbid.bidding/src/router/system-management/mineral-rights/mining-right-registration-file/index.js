import miningrightindex from '@/pages/system-management/mineral-rights/mining-right-registration-file/index'
import miningrightaudit from '@/pages/system-management/mineral-rights/mining-right-registration-file/check'
import miningrightdetail from '@/pages/system-management/mineral-rights/mining-right-registration-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/mining-right-registration-file',
    name: 'miningRightRegistrationFile-list',
    meta: {
      title: '矿业权登记公告备案',
      layout: 'SystemManagement'
    },
    component: miningrightindex
  },
  {
    path: '/system-management/mineral-rights/mining-right-registration-file/check',
    name: 'miningRightRegistrationFile-check',
    meta: {
      title: '矿业权登记公告备案审核',
      layout: 'SystemManagement'
    },
    component: miningrightaudit
  },
  {
    path: '/system-management/mineral-rights/mining-right-registration-file/detail/:objectId',
    name: 'miningRightRegistrationFile-detail',
    meta: {
      title: '矿业权登记公告备案详情',
      layout: 'SystemManagement'
    },
    component: miningrightdetail
  }
]
