import checklistedbid from '@/pages/mineral-rights/assignee/quotation/check-listed-bids/index'
import listedbiddingroom from '@/pages/mineral-rights/assignee/quotation/check-listed-bids/bidding-room'
import listedbiddetail from '@/pages/mineral-rights/assignee/quotation/check-listed-bids/detail'
export default[
  {
    path: '/mr/assignee/quotation/check-listed-bids',
    name: 'assigneeListedBids-list',
    meta: {
      title: '查看挂牌竞价情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: checklistedbid
  },
  {
    path: '/mr/assignee/quotation/check-listed-bids/bidding-room',
    name: 'assigneeListedBiddingRoom-detail',
    meta: {
      title: '竞价室',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: listedbiddingroom
  },
  {
    path: '/mr/assignee/quotation/check-auction-bids/detail',
    name: 'assigneeListedBidding-detail',
    meta: {
      title: '查看拍卖记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: listedbiddetail
  }
]
