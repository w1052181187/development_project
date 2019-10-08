import assRemiseInviteList from '@/pages/land-transaction/assignee/remise-invite/index'
import assRemiseInviteDetail from '@/pages/land-transaction/assignee/remise-invite/detail'
import assRemiseInviteConfirm from '@/pages/land-transaction/assignee/remise-invite/confirm'
export default[
  {
    path: '/lt/assignee/remise-invite/index',
    name: 'assRemiseInvite-list',
    meta: {
      title: '查看出让邀请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assRemiseInviteList
  },
  {
    path: '/lt/assignee/remise-invite/detail/:objectId',
    name: 'assRemiseInvite-detail',
    meta: {
      title: '查看出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assRemiseInviteDetail
  },
  {
    path: '/lt/assignee/remise-invite/confirm/:objectId',
    name: 'assRemiseInvite-confirm',
    meta: {
      title: '确认出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assRemiseInviteConfirm
  }
]
