/**
 * Created by lixuqiang on 2018/7/30.
 */
import issueSalesConfirmationList from '@/pages/mineral-rights/transferor/business-confirmation/issue-sales-confirmation/index'
import issueSubmission from '@/pages/mineral-rights/transferor/business-confirmation/issue-sales-confirmation/submission'
import issueSendOut from '@/pages/mineral-rights/transferor/business-confirmation/issue-sales-confirmation/send-out'
import issueDetail from '@/pages/mineral-rights/transferor/business-confirmation/issue-sales-confirmation/detail'

export default [
  {
    path: '/mr/transferor/business-confirmation/issue-sales-confirmation',
    name: 'issueSalesConfirmation-list',
    meta: {
      title: '发出成交确认书',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: issueSalesConfirmationList
  },
  {
    path: '/mr/transferor/business-confirmation/issue-sales-confirmation/submission/:objectId',
    name: 'issueSalesConfirmation-submission/:objectId',
    meta: {
      title: '提交',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: issueSubmission
  },
  {
    path: '/mr/transferor/business-confirmation/issue-sales-confirmation/send-out/:objectId',
    name: 'issueSalesConfirmation-sendOut/:objectId',
    meta: {
      title: '发送',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: issueSendOut
  },
  {
    path: '/mr/transferor/business-confirmation/issue-sales-confirmation/detail/:objectId',
    name: 'issueSalesConfirmation-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: issueDetail
  }
]
