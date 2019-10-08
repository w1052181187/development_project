import consultationDownBidEvalu from '@/pages/competitive-consultation/consultation-headman/bid-evaluation-end/down-bid-evalu'
import consultationReportSignature from '@/pages/competitive-consultation/consultation-headman/bid-evaluation-end/report-signature'

export default [
  {
    path: '/consultation-headman/bid-evaluation-end/down-bid-evalu',
    name: 'consultationDownBidEvalu-list',
    meta: {
      title: '下达评标结束命令'
    },
    component: consultationDownBidEvalu
  },
  {
    path: '/consultation-headman/bid-evaluation-end/report-signature',
    name: 'consultationReportSignature-list',
    meta: {
      title: '评标报告签名'
    },
    component: consultationReportSignature
  }
]
