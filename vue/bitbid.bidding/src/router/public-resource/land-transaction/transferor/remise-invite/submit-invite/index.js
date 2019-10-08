import submitInviteList from '@/pages/land-transaction/transferor/remise-invite/submit-invite/index'
import submitInviteSelectProject from '@/pages/land-transaction/transferor/remise-invite/submit-invite/selection-project'
import submitInviteAdd from '@/pages/land-transaction/transferor/remise-invite/submit-invite/add'
import submitInviteUpdate from '@/pages/land-transaction/transferor/remise-invite/submit-invite/update'
import submitInviteDetail from '@/pages/land-transaction/transferor/remise-invite/submit-invite/detail'
export default[
  {
    path: '/lt/transferor/remise-invite/submit-invite',
    name: 'submitInvite-list',
    meta: {
      title: '提交出让邀请',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: submitInviteList
  },
  {
    path: '/lt/transferor/remise-invite/submit-invite/selection-project',
    name: 'submitInvite-selectProject',
    meta: {
      title: '选择出让项目',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: submitInviteSelectProject
  },
  {
    path: '/lt/land-transaction/transferor/remise-invite/submit-invite/add',
    name: 'submitInvite-add',
    meta: {
      title: '添加出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: submitInviteAdd
  },
  {
    path: '/lt/land-transaction/transferor/remise-invite/submit-invite/update',
    name: 'submitInvite-update',
    meta: {
      title: '修改出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: submitInviteUpdate
  },
  {
    path: '/lt/land-transaction/transferor/remise-invite/submit-invite/detail/:objectId',
    name: 'submitInvite-detail',
    meta: {
      title: '查看出让邀请书',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: submitInviteDetail
  }
]
