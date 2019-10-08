import miningareaindex from '@/pages/system-management/mineral-rights/delimit-mining-area-file/index'
import miningareaaudit from '@/pages/system-management/mineral-rights/delimit-mining-area-file/check'
import miningareadetail from '@/pages/system-management/mineral-rights/delimit-mining-area-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/delimit-mining-area-file',
    name: 'DelimitMiningAreaFile-list',
    meta: {
      title: '划定矿区范围公示备案',
      layout: 'SystemManagement'
    },
    component: miningareaindex
  },
  {
    path: '/system-management/mineral-rights/delimit-mining-area-file/check',
    name: 'DelimitMiningAreaFile-check',
    meta: {
      title: '划定矿区范围公示备案审核',
      layout: 'SystemManagement'
    },
    component: miningareaaudit
  },
  {
    path: '/system-management/mineral-rights/delimit-mining-area-file/detail/:objectId',
    name: 'DelimitMiningAreaFile-detail',
    meta: {
      title: '划定矿区范围公示备案详情',
      layout: 'SystemManagement'
    },
    component: miningareadetail
  }
]
