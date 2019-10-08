import recordindex from '@/pages/system-management/mineral-rights/record-the-contract-file/index'
import recordaudit from '@/pages/system-management/mineral-rights/record-the-contract-file/check'
import recorddetail from '@/pages/system-management/mineral-rights/record-the-contract-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/record-the-contract-file',
    name: 'recordContractFile-list',
    meta: {
      title: '合同备案',
      layout: 'SystemManagement'
    },
    component: recordindex
  },
  {
    path: '/system-management/mineral-rights/record-the-contract-file/check',
    name: 'recordContractFile-check',
    meta: {
      title: '合同备案审核',
      layout: 'SystemManagement'
    },
    component: recordaudit
  },
  {
    path: '/system-management/mineral-rights/record-the-contract-file/detail/:objectId',
    name: 'recordContractFile-detail',
    meta: {
      title: '合同备案详情',
      layout: 'SystemManagement'
    },
    component: recorddetail
  }
]
