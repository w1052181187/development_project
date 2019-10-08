/**
 * Created by lixuqiang on 2018/6/29.
 */
import bond from '@/components/project-payments/bond/bondlist'
import bondonline from '@/components/project-payments/bond/bondlistonline'
import bondadd from '@/components/project-payments/bond/add'
import bondupdate from '@/components/project-payments/bond/edit'
import bonddetil from '@/components/project-payments/bond/see'
import bondreturn from '@/components/project-payments/bond/return'
export default [
  {
    path: '/projectpayments/manage/bondlist',
    name: 'projectpayments/manage/bondlist',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: bond
  },
  {
    path: '/projectpayments/manage/bondlist/add',
    name: 'projectpayments/manage/bondlist/add',
    meta: {
      permission: '/projectpayments/charge/add'
    },
    component: bondadd
  },
  {
    path: '/projectpayments/manage/bondlist/update',
    name: 'projectpayments/manage/bondlist/update',
    meta: {
      permission: '/projectpayments/charge/update'
    },
    component: bondupdate
  },
  {
    path: '/projectpayments/manage/bondlist/return',
    name: 'projectpayments/manage/bondlist/return',
    meta: {
      permission: '/projectpayments/charge/update'
    },
    component: bondreturn
  },
  {
    path: '/projectpayments/manage/bondlist/detail/:objectId',
    name: 'projectpayments/manage/bondlist/detail/:objectId',
    meta: {
      permission: '/projectpayments/charge/detail'
    },
    component: bonddetil
  },
  // 线上部分保证金
  {
    path: '/projectpayments/manage/bondlistonline',
    name: 'projectpayments/manage/bondlistonline',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: bondonline
  }
]
