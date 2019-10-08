import checktimelistedbid from '@/pages/mineral-rights/transferor/quotation/check-time-limit-bids/index'
import timelistedbiddingroom from '@/pages/mineral-rights/transferor/quotation/check-time-limit-bids/bidding-room'
import timelistedcreatebiddingroom from '@/pages/mineral-rights/transferor/quotation/check-time-limit-bids/create-bidding-room'
import timelistedbiddetail from '@/pages/mineral-rights/transferor/quotation/check-time-limit-bids/detail'
export default[
  {
    path: '/mr/transferor/quotation/check-time-limit-bids',
    name: 'timelistedBids-list',
    meta: {
      title: '查看限时竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checktimelistedbid
  },
  {
    path: '/mr/transferor/quotation/check-time-limit-bids/create-bidding-room',
    name: 'timelistedBiddingRoom-create',
    meta: {
      title: '创建竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: timelistedcreatebiddingroom
  },
  {
    path: '/mr/transferor/quotation/check-time-limit-bids/bidding-room',
    name: 'timelistedBiddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: timelistedbiddingroom
  },
  {
    path: '/mr/transferor/quotation/check-time-limit-bids/detail',
    name: 'timelistedBidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: timelistedbiddetail
  }
]
