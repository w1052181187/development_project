import feePaymentLand from '@/pages/land-transaction/transferor/fee-payment/index'
import paySituation from './pay-situation/index'

export default [
  {
    path: '/lt/transferor/fee-payment',
    name: 'feePaymentLand-list',
    meta: {
      title: '查看竞买保证金缴纳情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: feePaymentLand
  },
  ...paySituation
]
