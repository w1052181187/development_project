import bidpaymentfileindex from '@/pages/system-management/government-procurement/bid-bond-payment-file/index'
import bidpaymentfiledetail from '@/pages/system-management/government-procurement/bid-bond-payment-file/detail'

export default[
  {
    path: '/system-management/government-procurement/bid-bond-payment-file',
    name: 'bidPaymentFile-list',
    meta: {
      title: '投标保证金缴纳情况备案',
      layout: 'SystemManagement'
    },
    component: bidpaymentfileindex
  },
  {
    path: '/system-management/government-procurement/bid-bond-payment-file/detail/:objectId',
    name: 'bidPaymentFile-detail',
    meta: {
      title: '投标保证金缴纳情况备案详情',
      layout: 'SystemManagement'
    },
    component: bidpaymentfiledetail
  }
]
