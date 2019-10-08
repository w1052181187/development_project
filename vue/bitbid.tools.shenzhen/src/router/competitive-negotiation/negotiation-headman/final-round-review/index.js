import negotiationFinalRoundReview from '@/pages/competitive-negotiation/negotiation-headman/final-round-review/review'
import negotiationFinalRoundResults from '@/pages/competitive-negotiation/negotiation-headman/final-round-review/review-results'
import negotiationHeadmanReviewSummaryFina from '@/pages/competitive-negotiation/negotiation-headman/final-round-review/review-summary'

export default [
  {
    path: '/negotiation-headman/final-round-review/review',
    name: 'negotiationHeadmanReview-list',
    meta: {
      title: '评审'
    },
    component: negotiationFinalRoundReview
  },
  {
    path: '/negotiation-headman/final-round-review/review-results',
    name: 'negotiationHeadmanReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: negotiationFinalRoundResults
  },
  {
    path: '/negotiation-headman/final-round-review/review-summary',
    name: 'negotiationHeadmanReviewSummaryFina-list',
    meta: {
      title: '评审汇总'
    },
    component: negotiationHeadmanReviewSummaryFina
  }
]
