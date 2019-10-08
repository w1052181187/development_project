import auctionbiddingindex from '@/pages/system-management/mineral-rights/auction-bidding/index'
import auctionbiddingdetail from '@/pages/system-management/mineral-rights/auction-bidding/detail'

export default[
  {
    path: '/system-management/mineral-rights/auction-bidding',
    name: 'auctionBidding-list',
    meta: {
      title: '拍卖竞价情况查看',
      layout: 'SystemManagement'
    },
    component: auctionbiddingindex
  },
  {
    path: '/system-management/mineral-rights/auction-bidding/detail',
    name: 'auctionBidding-detail',
    meta: {
      title: '拍卖竞价情况查看详情',
      layout: 'SystemManagement'
    },
    component: auctionbiddingdetail
  }
]
