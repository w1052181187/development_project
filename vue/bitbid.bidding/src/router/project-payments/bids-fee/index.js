/**
 * Created by lixuqiang on 2018/6/29.
 */
import biddocumentlist from '@/components/project-payments/bids-fee/biddocumentlist'
import biddocumentlistonline from '@/components/project-payments/bids-fee/biddocumentlistonline'
import biddadd from '@/components/project-payments/bids-fee/add'
import biddupdate from '@/components/project-payments/bids-fee/edit'
import biddsee from '@/components/project-payments/bids-fee/see'
export default [
  {
    path: '/projectpayments/manage/biddocumentlist',
    name: 'projectpayments/manage/biddocumentlist',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: biddocumentlist
  },
  {
    path: '/projectpayments/manage/biddocumentlist/add',
    name: 'projectpayments/manage/biddocumentlist/add',
    meta: {
      permission: '/projectpayments/charge/add'
    },
    component: biddadd
  },
  {
    path: '/projectpayments/manage/biddocumentlist/update',
    name: 'projectpayments/manage/biddocumentlist/update',
    meta: {
      permission: '/projectpayments/charge/update'
    },
    component: biddupdate
  },
  {
    path: '/projectpayments/manage/biddocumentlist/detail/:objectId',
    name: 'projectpayments/manage/biddocumentlist/detail/:objectId',
    meta: {
      permission: '/projectpayments/charge/detail'
    },
    component: biddsee
  },
  // 线上部分保证金
  {
    path: '/projectpayments/manage/biddocumentlistonline',
    name: 'projectpayments/manage/biddocumentlistonline',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: biddocumentlistonline
  }
]
