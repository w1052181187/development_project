/**
 * Created by lixuqiang on 2018/6/29.
 */
import otherincome from '@/components/project-payments/other-income/index'
import otherincomeadd from '@/components/project-payments/other-income/add'
import otherincomeupdate from '@/components/project-payments/other-income/edit'
import otherincomedetail from '@/components/project-payments/other-income/see'

export default [
  {
    path: '/projectpayments/manage/other-income',
    name: 'projectpayments/manage/other-income',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: otherincome
  },
  {
    path: '/projectpayments/manage/other-income/add',
    name: 'projectpayments/manage/other-income/add',
    meta: {
      permission: '/projectpayments/charge/add'
    },
    component: otherincomeadd
  },
  {
    path: '/projectpayments/manage/other-income/update',
    name: 'projectpayments/manage/other-income/update',
    meta: {
      permission: '/projectpayments/charge/update'
    },
    component: otherincomeupdate
  },
  {
    path: '/projectpayments/manage/other-income/detail/:objectId',
    name: 'projectpayments/manage/other-income/detail/:objectId',
    meta: {
      permission: '/projectpayments/charge/detail'
    },
    component: otherincomedetail
  }
]
