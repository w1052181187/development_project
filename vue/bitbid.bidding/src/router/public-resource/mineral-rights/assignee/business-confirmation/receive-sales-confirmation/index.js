import receiveSalesConfirmationList from '@/pages/mineral-rights/assignee/business-confirmation/receive-sales-confirmation/index'
import receiveDetail from '@/pages/mineral-rights/assignee/business-confirmation/receive-sales-confirmation/detail'
import receiveConfirm from '@/pages/mineral-rights/assignee/business-confirmation/receive-sales-confirmation/confirm'

export default [
  {
    path: '/mr/assignee/business-confirmation/receive-sales-confirmation',
    name: 'receiveSalesConfirmation-list',
    meta: {
      title: '接收成交确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: receiveSalesConfirmationList
  },
  {
    path: '/mr/assignee/business-confirmation/receive-sales-confirmation/detail/:objectId',
    name: 'receiveSalesConfirmation-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: receiveDetail
  },
  {
    path: '/mr/assignee/business-confirmation/receive-sales-confirmation/confirm/:objectId',
    name: 'receiveSalesConfirmationConfirm-confirm',
    meta: {
      title: '确认',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: receiveConfirm
  }
]
