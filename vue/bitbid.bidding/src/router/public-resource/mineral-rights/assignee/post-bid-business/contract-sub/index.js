import contractSub from '@/pages/mineral-rights/assignee/post-bid-business/contract-sub/index'
import subContract from '@/pages/mineral-rights/assignee/post-bid-business/contract-sub/sub-contract'
import updateContract from '@/pages/mineral-rights/assignee/post-bid-business/contract-sub/update'
import detailContract from '@/pages/mineral-rights/assignee/post-bid-business/contract-sub/detail'

export default[
  {
    path: '/mr/assignee/post-bid-business/contract-sub',
    name: 'contractSub-list',
    meta: {
      title: '合同提交',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: contractSub
  },
  {
    path: '/mr/assignee/post-bid-business/contract-sub/sub-contract',
    name: 'contractSub-sub',
    meta: {
      title: '提交合同',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subContract
  },
  {
    path: '/mr/assignee/post-bid-business/contract-sub/update',
    name: 'contractSub-update',
    meta: {
      title: '修改合同',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: updateContract
  },
  {
    path: '/mr/assignee/post-bid-business/contract-sub/detail/:objectId',
    name: 'contractSub-detail',
    meta: {
      title: '查看合同',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detailContract
  }
]
