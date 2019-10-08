import listingbiddingindex from '@/pages/system-management/mineral-rights/listing-bidding/index'
import listingbiddingdetail from '@/pages/system-management/mineral-rights/listing-bidding/detail'

export default[
  {
    path: '/system-management/mineral-rights/listing-bidding',
    name: 'listingBidding-list',
    meta: {
      title: '挂牌竞价情况查看',
      layout: 'SystemManagement'
    },
    component: listingbiddingindex
  },
  {
    path: '/system-management/mineral-rights/listing-bidding/detail',
    name: 'listingBidding-detail',
    meta: {
      title: '挂牌竞价情况查看详情',
      layout: 'SystemManagement'
    },
    component: listingbiddingdetail
  }
]
