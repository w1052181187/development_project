/**
 * Created by lixuqiang on 2018/7/30.
 */
import marginReturn from '@/pages/mineral-rights/transferor/post-bid-business/margin-return/index'
import marginReturnRefund from '@/pages/mineral-rights/transferor/post-bid-business/margin-return/refund'
import marginReturnApprove from '@/pages/mineral-rights/transferor/post-bid-business/margin-return/approve'
import marginReturnDetail from '@/pages/mineral-rights/transferor/post-bid-business/margin-return/detail'
export default[
  {
    path: '/mr/transferor/post-bid-business/margin-return',
    name: 'marginReturn-list',
    meta: {
      title: '保证金退回',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturn
  },
  {
    path: '/mr/transferor/post-bid-business/margin-return/refund',
    name: 'marginReturn-refund',
    meta: {
      title: '退回保证金',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnRefund
  },
  {
    path: '/mr/transferor/post-bid-business/margin-return/approve',
    name: 'marginReturn-approve',
    meta: {
      title: '审批申请',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnApprove
  },
  {
    path: '/mr/transferor/post-bid-business/margin-return/detail/:objectId',
    name: 'marginReturn-detail',
    meta: {
      title: '查看申请',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: marginReturnDetail
  }
]
