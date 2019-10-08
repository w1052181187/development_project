import resultsfileindex from '@/pages/system-management/government-procurement/pre-qualification-results-file/index'
import checkresult from '@/pages/system-management/government-procurement/pre-qualification-results-file/check-result'
import resultsfiledetail from '@/pages/system-management/government-procurement/pre-qualification-results-file/detail'

export default[
  {
    path: '/system-management/government-procurement/pre-qualification-results-file',
    name: 'resultsFile-list',
    meta: {
      title: '资格预审结果通知书备案',
      layout: 'SystemManagement'
    },
    component: resultsfileindex
  },
  {
    path: '/system-management/government-procurement/pre-qualification-results-file/check-result/:objectId',
    name: 'checkresult-list',
    meta: {
      title: '查看审查结果',
      layout: 'SystemManagement'
    },
    component: checkresult
  },
  {
    path: '/system-management/government-procurement/pre-qualification-results-file/detail/:objectId',
    name: 'resultsFile-detail',
    meta: {
      title: '查看报告详情',
      layout: 'SystemManagement'
    },
    component: resultsfiledetail
  }
]
