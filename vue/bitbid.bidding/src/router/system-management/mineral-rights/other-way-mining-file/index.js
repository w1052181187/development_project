import miningindex from '@/pages/system-management/mineral-rights/other-way-mining-file/index'
import miningaudit from '@/pages/system-management/mineral-rights/other-way-mining-file/check'
import miningdetail from '@/pages/system-management/mineral-rights/other-way-mining-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/other-way-mining-file',
    name: 'otherwWayMiningFile-list',
    meta: {
      title: '其他方式采矿权出让公示备案',
      layout: 'SystemManagement'
    },
    component: miningindex
  },
  {
    path: '/system-management/mineral-rights/other-way-mining-file/check',
    name: 'otherwWayMiningFile-check',
    meta: {
      title: '其他方式采矿权出让公示备案审核',
      layout: 'SystemManagement'
    },
    component: miningaudit
  },
  {
    path: '/system-management/mineral-rights/other-way-mining-file/detail/:objectId',
    name: 'otherwWayMiningFile-detail',
    meta: {
      title: '其他方式采矿权出让公示备案详情',
      layout: 'SystemManagement'
    },
    component: miningdetail
  }
]
