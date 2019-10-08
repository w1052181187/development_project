/**
 * Created by lixuqiang on 2018/4/20.
 */
import projectpayments from '@/components/project-payments/index'
import additem from '@/components/project-payments/additem'
import seeitem from '@/components/project-payments/seeitem'
import manage from '@/components/project-payments/manage'
import bidsfee from './bids-fee/index'
import bond from './bond/index'
import otherincome from './other-income/index'
import otherexpenditure from './other-expenditure/index'
import balancepayment from './balance-payment/index'

export default [
  {
    path: '/projectpayments',
    name: 'projectpayments',
    meta: {
      permission: '/projectpayments'
    },
    component: projectpayments
  },
  {
    path: '/projectpayments/add',
    name: 'projectpayments/add',
    meta: {
      permission: '/projectpayments/add'
    },
    component: additem
  },
  {
    path: '/projectpayments/seeitem',
    name: 'projectpayments/seeitem',
    meta: {
      permission: '/projectpayments/detail'
    },
    component: seeitem
  },
  {
    path: '/projectpayments/manage',
    name: 'projectpayments/manage',
    meta: {
      permission: '/projectpayments/expense'
    },
    component: manage
  },
  ...bidsfee,
  ...bond,
  ...otherincome,
  ...otherexpenditure,
  ...balancepayment
]
