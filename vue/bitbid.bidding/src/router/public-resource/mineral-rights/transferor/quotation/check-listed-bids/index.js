import checklistedbid from '@/pages/mineral-rights/transferor/quotation/check-listed-bids/index'
import listedbiddingroom from '@/pages/mineral-rights/transferor/quotation/check-listed-bids/bidding-room'
import listedcreatebiddingroom from '@/pages/mineral-rights/transferor/quotation/check-listed-bids/create-bidding-room'
import listedbiddetail from '@/pages/mineral-rights/transferor/quotation/check-listed-bids/detail'
export default[
  {
    path: '/mr/transferor/quotation/check-listed-bids',
    name: 'listedBids-list',
    meta: {
      title: '查看挂牌竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checklistedbid
  },
  {
    path: '/mr/transferor/quotation/check-listed-bids/create-bidding-room',
    name: 'listedBidsCreate-list',
    meta: {
      title: '创建竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: listedcreatebiddingroom
  },
  {
    path: '/mr/transferor/quotation/check-listed-bids/bidding-room',
    name: 'listedBiddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: listedbiddingroom
  },
  {
    path: '/mr/transferor/quotation/check-listed-bids/detail',
    name: 'listedBidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: listedbiddetail
  }
]
