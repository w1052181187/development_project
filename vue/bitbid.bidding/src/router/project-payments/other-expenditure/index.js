/**
 * Created by lixuqiang on 2018/6/29.
 */
import otherexpenditure from '@/components/project-payments/other-expenditure/index'
import otherexpenditureadd from '@/components/project-payments/other-expenditure/add'
import otherexpenditureupdate from '@/components/project-payments/other-expenditure/edit'
import otherexpendituredetail from '@/components/project-payments/other-expenditure/see'

export default [
  {
    path: '/projectpayments/manage/other-expenditure',
    name: 'projectpayments/manage/other-expenditure',
    meta: {
      permission: '/projectpayments/charge'
    },
    component: otherexpenditure
  },
  {
    path: '/projectpayments/manage/other-expenditure/add',
    name: 'projectpayments/manage/other-expenditure/add',
    meta: {
      permission: '/projectpayments/charge/add'
    },
    component: otherexpenditureadd
  },
  {
    path: '/projectpayments/manage/other-expenditure/update',
    name: 'projectpayments/manage/other-expenditure/update',
    meta: {
      permission: '/projectpayments/charge/update'
    },
    component: otherexpenditureupdate
  },
  {
    path: '/projectpayments/manage/other-expenditure/detail/:objectId',
    name: 'projectpayments/manage/other-expenditure/detail/:objectId',
    meta: {
      permission: '/projectpayments/charge/detail'
    },
    component: otherexpendituredetail
  }
]
