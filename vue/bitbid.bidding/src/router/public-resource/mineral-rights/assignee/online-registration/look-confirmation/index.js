import lookconfirmation from '@/pages/mineral-rights/assignee/online-registration/look-confirmation'
import confirmationdetail from '@/pages/mineral-rights/assignee/online-registration/look-confirmation/detail'
export default[
  {
    path: '/mr/assignee/online-registration/look-confirmation',
    name: 'lookConfirmation-list',
    meta: {
      title: '查看资格确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookconfirmation
  },
  {
    path: '/mr/assignee/online-registration/look-confirmation/detail',
    name: 'lookConfirmation-detail',
    meta: {
      title: '查看资格确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: confirmationdetail
  }
]
