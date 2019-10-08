/**
 * Created by lixuqiang on 2018/7/30.
 */
import contractView from '@/pages/mineral-rights/transferor/post-bid-business/contract-view/index'
import contractDetail from '@/pages/mineral-rights/transferor/post-bid-business/contract-view/detail'

export default[
  {
    path: '/mr/transferor/post-bid-business/contract-view',
    name: 'contractView-list',
    meta: {
      title: '合同查看',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: contractView
  },
  {
    path: '/mr/transferor/post-bid-business/contract-view/detail/:objectId',
    name: 'contractView-detail',
    meta: {
      title: '查看合同',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: contractDetail
  }
]
