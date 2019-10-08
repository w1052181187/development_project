import negotiationsummaryBidEvaluation from '@/pages/competitive-negotiation/negotiation-headman/review-summary/summary-bid-evaluation'
import negotiationGroupLeader from '@/pages/competitive-negotiation/negotiation-headman/review-summary/group-leader'

export default [
  {
    path: '/negotiation-headman/review-summary/summary-bid-evaluation',
    name: 'negotiationsummaryBidEvaluation-list',
    meta: {
      title: '评标汇总'
    },
    component: negotiationsummaryBidEvaluation
  },
  {
    path: '/negotiation-headman/review-summary/group-leader',
    name: 'negotiationGroupLeader-list',
    meta: {
      title: '组长汇总'
    },
    component: negotiationGroupLeader
  }
]
