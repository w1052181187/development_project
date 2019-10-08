import contractfile from '@/pages/system-management/government-procurement/contract-file/index'
import contractfileDetailOrCheck from '@/pages/system-management/government-procurement/contract-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/contract-file',
    name: 'contractFile-list',
    meta: {
      title: '合同备案',
      layout: 'SystemManagement'
    },
    component: contractfile
  },
  {
    path: '/system-management/government-procurement/contract-file/detail-or-check/:type/:objectId',
    name: 'contractFile-check',
    meta: {
      title: '合同备案',
      layout: 'SystemManagement'
    },
    component: contractfileDetailOrCheck
  }
]
