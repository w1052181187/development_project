/**
 * Created by lixuqiang on 2018/7/30.
 */
import paySituation from '@/pages/mineral-rights/transferor/fee-payment/pay-situation/index'
import paySituationDetail from '@/pages/mineral-rights/transferor/fee-payment/pay-situation/detail'
import paySituationApprove from '@/pages/mineral-rights/transferor/fee-payment/pay-situation/approve'

export default [
  {
    path: '/mr/transferor/fee-payment/pay-situation',
    name: 'feePaymentSituation-list',
    meta: {
      title: '缴纳情况',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: paySituation
  },
  {
    path: '/mr/transferor/fee-payment/pay-situation/detail',
    name: 'feePaymentSituation-detail',
    meta: {
      title: '缴纳详情',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: paySituationDetail
  },
  {
    path: '/mr/transferor/fee-payment/pay-situation/approve',
    name: 'feePayment-approve',
    meta: {
      title: '缴纳审批',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: paySituationApprove
  }
]
