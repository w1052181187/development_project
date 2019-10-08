import auctionbid from '@/pages/mineral-rights/assignee/auction/auction-bidding/index'
import auctionbiddingroom from '@/pages/mineral-rights/assignee/auction/auction-bidding/bidding-room'
import auctionbiddetail from '@/pages/mineral-rights/assignee/auction/auction-bidding/detail'
export default[
  {
    path: '/mr/assignee/auction/auction-bids',
    name: 'auctionBid-list',
    meta: {
      title: '拍卖竞价',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionbid
  },
  {
    path: '/mr/assignee/auction/auction-bids/bidding-room',
    name: 'assigneeBiddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionbiddingroom
  },
  {
    path: '/mr/assignee/auction/auction-bids/detail',
    name: 'assigneeBidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionbiddetail
  }
]
