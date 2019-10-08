/**
 * Created by lixuqiang on 2018/6/29.
 */
import bond from '@/components/project-payments/bond/bondlist'
import bondadd from '@/components/project-payments/bond/add'
import bondupdate from '@/components/project-payments/bond/edit'
import bonddetil from '@/components/project-payments/bond/see'
import bondreturn from '@/components/project-payments/bond/return'
export default [
  {
    path: '/projectpayments/manage/bondlist',
    name: 'projectpayments/manage/bondlist',
    component: bond
  },
  {
    path: '/projectpayments/manage/bondlist/add',
    name: 'projectpayments/manage/bondlist/add',
    component: bondadd
  },
  {
    path: '/projectpayments/manage/bondlist/update',
    name: 'projectpayments/manage/bondlist/update',
    component: bondupdate
  },
  {
    path: '/projectpayments/manage/bondlist/return',
    name: 'projectpayments/manage/bondlist/return',
    component: bondreturn
  },
  {
    path: '/projectpayments/manage/bondlist/detail/:objectId',
    name: 'projectpayments/manage/bondlist/detail/:objectId',
    component: bonddetil
  }
]
