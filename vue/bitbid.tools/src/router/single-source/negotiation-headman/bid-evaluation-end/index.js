import negotiationDownBidEvalu from '@/pages/single-source/negotiation-headman/bid-evaluation-end/down-bid-evalu'
import negotiationReportSignature from '@/pages/single-source/negotiation-headman/bid-evaluation-end/report-signature'

export default [
  {
    path: '/single-source/negotiation-headman/bid-evaluation-end/down-bid-evalu',
    name: 'single-source-negotiationDownBidEvalu-list',
    meta: {
      title: '下达评标结束命令'
    },
    component: negotiationDownBidEvalu
  },
  {
    path: '/single-source/negotiation-headman/bid-evaluation-end/report-signature',
    name: 'single-source-negotiationReportSignature-list',
    meta: {
      title: '评标报告签名'
    },
    component: negotiationReportSignature
  }
]
