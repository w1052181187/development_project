/**
 * Created by lixuqiang on 2018/6/29.
 */
import biddocumentlist from '@/components/project-payments/bids-fee/biddocumentlist'
import biddadd from '@/components/project-payments/bids-fee/add'
import biddupdate from '@/components/project-payments/bids-fee/edit'
import biddsee from '@/components/project-payments/bids-fee/see'
export default [
  {
    path: '/projectpayments/manage/biddocumentlist',
    name: 'projectpayments/manage/biddocumentlist',
    component: biddocumentlist
  },
  {
    path: '/projectpayments/manage/biddocumentlist/add',
    name: 'projectpayments/manage/biddocumentlist/add',
    component: biddadd
  },
  {
    path: '/projectpayments/manage/biddocumentlist/update',
    name: 'projectpayments/manage/biddocumentlist/update',
    component: biddupdate
  },
  {
    path: '/projectpayments/manage/biddocumentlist/detail/:objectId',
    name: 'projectpayments/manage/biddocumentlist/detail/:objectId',
    component: biddsee
  }
]
