import sendInviteList from '@/pages/land-transaction/transferor/remise-invite/send-invite/index'
import sendInviteDetail from '@/pages/land-transaction/transferor/remise-invite/send-invite/detail'
import sendInviteTimeSetting from '@/pages/land-transaction/transferor/remise-invite/send-invite/time-setting'
export default[
  {
    path: '/lt/transferor/remise-invite/send-invite',
    name: 'sendInvite-list',
    meta: {
      title: '发出出让邀请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendInviteList
  },
  {
    path: '/lt/land-transaction/transferor/remise-invite/send-invite/detail/:objectId',
    name: 'sendInvite-detail',
    meta: {
      title: '查看出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendInviteDetail
  },
  {
    path: '/lt/land-transaction/transferor/remise-invite/send-invite/time-setting',
    name: 'sendInvite-timeSetting',
    meta: {
      title: '设置时间',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: sendInviteTimeSetting
  }
]
