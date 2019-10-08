/**
 * Created by lixuqiang on 2018/8/1.
 */
import feePayment from '@/pages/mineral-rights/assignee/fee-payment/index'
import feePaymentDetail from '@/pages/mineral-rights/assignee/fee-payment/detail'
import feePaymentLookReceipt from '@/pages/mineral-rights/assignee/fee-payment/look-receipt'
import feePaymentPayment from '@/pages/mineral-rights/assignee/fee-payment/payment'

export default [
  {
    path: '/mr/assignee/fee-payment',
    name: 'feePayment-list',
    meta: {
      title: '缴纳竞买保证金',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: feePayment
  },
  {
    path: '/mr/assignee/fee-payment/detail',
    name: 'feePayment-detail',
    meta: {
      title: '缴纳详情',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: feePaymentDetail
  },
  {
    path: '/mr/assignee/fee-payment/look-receipt',
    name: 'feePayment-receipt',
    meta: {
      title: '查看回执',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: feePaymentLookReceipt
  },
  {
    path: '/mr/assignee/fee-payment/payment',
    name: 'feePayment-payment',
    meta: {
      title: '支付',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: feePaymentPayment
  }
]
