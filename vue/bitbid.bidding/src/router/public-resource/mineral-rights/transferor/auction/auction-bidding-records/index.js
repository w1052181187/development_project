import auctionrecords from '@/pages/mineral-rights/transferor/auction/auction-bidding-records/index'
import auctionrecordsdetail from '@/pages/mineral-rights/transferor/auction/auction-bidding-records/detail'
export default[
  {
    path: '/mr/transferor/auction/auction-bidding-records',
    name: 'auctionBidRecords-list',
    meta: {
      title: '查看拍卖竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionrecords
  },
  {
    path: '/mr/transferor/auction/auction-bidding-records/detail',
    name: 'auctionBidRecords-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: auctionrecordsdetail
  }
]
