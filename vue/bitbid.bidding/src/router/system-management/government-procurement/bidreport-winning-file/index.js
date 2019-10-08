import bidreportwinningfile from '@/pages/system-management/government-procurement/bidreport-winning-file/index'
import bidreportwinningfiledetail from '@/pages/system-management/government-procurement/bidreport-winning-file/detail'

export default[
  {
    path: '/system-management/government-procurement/bidreport-winning-file',
    name: 'bidReportWinningFile-list',
    meta: {
      title: '评标报告及中标候选人备案',
      layout: 'SystemManagement'
    },
    component: bidreportwinningfile
  },
  {
    path: '/system-management/government-procurement/bidreport-winning-file/detail/:objectId',
    name: 'bidReportWinningFile-detail',
    meta: {
      title: '评标报告及中标候选人备案详情',
      layout: 'SystemManagement'
    },
    component: bidreportwinningfiledetail
  }
]
