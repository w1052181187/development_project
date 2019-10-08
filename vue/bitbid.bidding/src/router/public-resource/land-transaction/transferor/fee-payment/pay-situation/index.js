import paySituationLand from '@/pages/land-transaction/transferor/fee-payment/pay-situation/index'
import paySituationLandDetail from '@/pages/land-transaction/transferor/fee-payment/pay-situation/detail'
import paySituationLandApprove from '@/pages/land-transaction/transferor/fee-payment/pay-situation/approve'

export default [
  {
    path: '/lt/transferor/fee-payment/pay-situation',
    name: 'feePaymentSituationLand-list',
    meta: {
      title: '缴纳情况',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: paySituationLand
  },
  {
    path: '/lt/transferor/fee-payment/pay-situation/detail/:objectId',
    name: 'feePaymentSituationLand-detail',
    meta: {
      title: '缴纳详情',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: paySituationLandDetail
  },
  {
    path: '/lt/transferor/fee-payment/pay-situation/approve/:objectId',
    name: 'feePaymentLand-approve',
    meta: {
      title: '缴纳审批',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: paySituationLandApprove
  }
]
