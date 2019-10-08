import auctionbidsrecord from '@/pages/mineral-rights/assignee/auction/check-auction-bids/index'
import auctionbidsrecorddetail from '@/pages/mineral-rights/assignee/auction/check-auction-bids/detail'
export default[
  {
    path: '/mr/assignee/auction/check-auction-bids',
    name: 'checkAuctionBidsLook-list',
    meta: {
      title: '查看拍卖竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionbidsrecord
  },
  {
    path: '/mr/assignee/auction/check-auction-bids/detail',
    name: 'checkAuctionBids-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionbidsrecorddetail
  }
]
