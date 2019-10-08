/**
 * Created by lixuqiang on 2018/7/30.
 */
import feePayment from '@/pages/mineral-rights/transferor/fee-payment/index'
import paySituation from './pay-situation/index'

export default [
  {
    path: '/mr/transferor/fee-payment',
    name: 'feePaymentLook-list',
    meta: {
      title: '查看竞买保证金缴纳情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: feePayment
  },
  ...paySituation
]
