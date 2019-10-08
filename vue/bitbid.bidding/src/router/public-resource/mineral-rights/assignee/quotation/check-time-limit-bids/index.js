import checktimelistedbid from '@/pages/mineral-rights/assignee/quotation/check-time-limit-bids/index'
import timelistedbiddingroom from '@/pages/mineral-rights/assignee/quotation/check-time-limit-bids/bidding-room'
import timelistedbiddetail from '@/pages/mineral-rights/assignee/quotation/check-time-limit-bids/detail'
export default[
  {
    path: '/mr/assignee/quotation/check-time-limit-bids',
    name: 'assigneeTimelistedBids-list',
    meta: {
      title: '查看限时竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checktimelistedbid
  },
  {
    path: '/mr/assignee/quotation/check-time-limit-bids/bidding-room',
    name: 'assigneeTimelistedBiddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: timelistedbiddingroom
  },
  {
    path: '/mr/assignee/quotation/check-time-limit-bids/detail',
    name: 'assigneeTimelistedBidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: timelistedbiddetail
  }
]
