import checkauction from '@/pages/mineral-rights/transferor/auction/check-auction-bids/index'
import biddingroom from '@/pages/mineral-rights/transferor/auction/check-auction-bids/bidding-room'
import createbiddingroom from '@/pages/mineral-rights/transferor/auction/check-auction-bids/create-bidding-room'
import biddetail from '@/pages/mineral-rights/transferor/auction/check-auction-bids/detail'
export default[
  {
    path: '/mr/transferor/auction/check-auction-bids',
    name: 'checkAuctionBids-list',
    meta: {
      title: '查看拍卖竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checkauction
  },
  {
    path: '/mr/transferor/auction/check-auction-bids/create-bidding-room',
    name: 'biddingRoom-create',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: createbiddingroom
  },
  {
    path: '/mr/transferor/auction/check-auction-bids/bidding-room',
    name: 'biddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: biddingroom
  },
  {
    path: '/mr/transferor/auction/check-auction-bids/detail',
    name: 'bidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: biddetail
  }
]
