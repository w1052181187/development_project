import buyingsituationfile from '@/pages/system-management/government-procurement/buying-situation-file/index'
import buyingsituationfiledetail from '@/pages/system-management/government-procurement/buying-situation-file/detail'

export default[
  {
    path: '/system-management/government-procurement/buying-situation-file',
    name: 'buyingSituationFile-list',
    meta: {
      title: '采购情况报告备案',
      layout: 'SystemManagement'
    },
    component: buyingsituationfile
  },
  {
    path: '/system-management/government-procurement/buying-situation-file/detail/:objectId',
    name: 'buyingSituationFile-detail',
    meta: {
      title: '采购情况报告备案详情',
      layout: 'SystemManagement'
    },
    component: buyingsituationfiledetail
  }
]
