import marginReturn from '@/pages/mineral-rights/assignee/post-bid-business/margin-return/index'
import marginReturnRefund from '@/pages/mineral-rights/assignee/post-bid-business/margin-return/refund'
import marginReturnDetail from '@/pages/mineral-rights/assignee/post-bid-business/margin-return/detail'
import marginReturnUpdate from '@/pages/mineral-rights/assignee/post-bid-business/margin-return/update'
export default[
  {
    path: '/mr/assignee/post-bid-business/margin-return',
    name: 'marginReturnApply-list',
    meta: {
      title: '申请保证金退回',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturn
  },
  {
    path: '/mr/assignee/post-bid-business/margin-return/refund',
    name: 'marginReturnApply-refund',
    meta: {
      title: '退回保证金',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnRefund
  },
  {
    path: '/mr/assignee/post-bid-business/margin-return/detail/:objectId',
    name: 'marginReturnApply-detail',
    meta: {
      title: '查看申请',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnDetail
  },
  {
    path: '/mr/assignee/post-bid-business/margin-return/update',
    name: 'marginReturnApply-update',
    meta: {
      title: '修改申请',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnUpdate
  }
]
