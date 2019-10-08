import qualificationconfirmationindex from '@/pages/system-management/mineral-rights/qualification-confirmation/index'
import qualificationconfirmationdetail from '@/pages/system-management/mineral-rights/qualification-confirmation/detail'

export default[
  {
    path: '/system-management/mineral-rights/qualification-confirmation',
    name: 'qualificationConfirmation-list',
    meta: {
      title: '资格确认情况查看',
      layout: 'SystemManagement'
    },
    component: qualificationconfirmationindex
  },
  {
    path: '/system-management/mineral-rights/qualification-confirmation/detail/:objectId',
    name: 'qualificationConfirmation-detail',
    meta: {
      title: '资格确认情况查看详情',
      layout: 'SystemManagement'
    },
    component: qualificationconfirmationdetail
  }
]
