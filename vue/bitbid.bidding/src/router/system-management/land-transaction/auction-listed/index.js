import systemQualifcation from '@/pages/system-management/land-transaction/auction-listed/index'
import systemQualifcationDetail from '@/pages/system-management/land-transaction/auction-listed/detail'

export default [
  {
    path: '/system-management/land-transaction/auction-listed/index',
    name: 'auctionListed-list',
    meta: {
      title: '拍卖/挂牌出让文件备案',
      layout: 'SystemManagement'
    },
    component: systemQualifcation
  },
  {
    path: '/system-management/land-transaction/auction-listed/detail/:objectId',
    name: 'auctionListed-detail',
    meta: {
      title: '出让文件查看',
      layout: 'SystemManagement'
    },
    component: systemQualifcationDetail
  }
]
