import lookInviteList from '@/pages/land-transaction/transferor/remise-invite/look-invite/index'
import lookInviteLookConfirm from '@/pages/land-transaction/transferor/remise-invite/look-invite/look-confirm'
import lookInviteSellInvitations from '@/pages/land-transaction/transferor/remise-invite/look-invite/sell-invitations'
import lookInviteAssigneeSituation from '@/pages/land-transaction/transferor/remise-invite/look-invite/assignee-situation'
import lookInviteAssigneeDetail from '@/pages/land-transaction/transferor/remise-invite/look-invite/assignee-detail'
import lookInviteDetail from '@/pages/land-transaction/transferor/remise-invite/look-invite/detail'
export default[
  {
    path: '/lt/transferor/remise-invite/look-invite',
    name: 'lookInvite-list',
    meta: {
      title: '查看出让邀请确认情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteList
  },
  {
    path: '/lt/transferor/remise-invite/look-invite/look-confirm/:objectId',
    name: 'lookInvite-lookConfirm',
    meta: {
      title: '查看确认情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteLookConfirm
  },
  {
    path: '/lt/transferor/remise-invite/look-invite/sell-invitations/:objectId',
    name: 'lookInvite-sellInvitations',
    meta: {
      title: '出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteSellInvitations
  },
  {
    path: '/lt/transferor/remise-invite/look-invite/assignee-situation/:objectId',
    name: 'lookInvite-assigneeSituation',
    meta: {
      title: '受让人确认情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteAssigneeSituation
  },
  {
    path: '/lt/transferor/remise-invite/look-invite/assignee-situation/detail/:objectId',
    name: 'lookInvite-assigneeDetail',
    meta: {
      title: '详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteAssigneeDetail
  },
  {
    path: '/lt/transferor/remise-invite/look-invite/detail/:objectId',
    name: 'sendInviteLook-detail',
    meta: {
      title: '查看出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookInviteDetail
  }
]
