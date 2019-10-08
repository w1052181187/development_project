import explorationindex from '@/pages/system-management/mineral-rights/other-way-exploration-file/index'
import explorationaudit from '@/pages/system-management/mineral-rights/other-way-exploration-file/check'
import explorationdetail from '@/pages/system-management/mineral-rights/other-way-exploration-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/other-way-exploration-file',
    name: 'otherwWayExplorationFile-list',
    meta: {
      title: '其他方式探矿权出让公示备案',
      layout: 'SystemManagement'
    },
    component: explorationindex
  },
  {
    path: '/system-management/mineral-rights/other-way-exploration-file/check',
    name: 'otherwWayExplorationFile-check',
    meta: {
      title: '其他方式探矿权出让公示备案审核',
      layout: 'SystemManagement'
    },
    component: explorationaudit
  },
  {
    path: '/system-management/mineral-rights/other-way-exploration-file/detail/:objectId',
    name: 'otherwWayExplorationFile-detail',
    meta: {
      title: '其他方式探矿权出让公示备案详情',
      layout: 'SystemManagement'
    },
    component: explorationdetail
  }
]
