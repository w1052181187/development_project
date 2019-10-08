import negotiationFinalRoundReview from '@/pages/single-source/negotiation-headman/final-round-review/review'
import negotiationFinalRoundResults from '@/pages/single-source/negotiation-headman/final-round-review/review-results'
import negotiationHeadmanReviewSummaryFina from '@/pages/single-source/negotiation-headman/final-round-review/review-summary'

export default [
  {
    path: '/single-source/negotiation-headman/final-round-review/review',
    name: 'single-source-negotiationHeadmanReview-list',
    meta: {
      title: '评审'
    },
    component: negotiationFinalRoundReview
  },
  {
    path: '/single-source/negotiation-headman/final-round-review/review-results',
    name: 'single-source-negotiationHeadmanReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: negotiationFinalRoundResults
  },
  {
    path: '/single-source/negotiation-headman/final-round-review/review-summary',
    name: 'single-source-negotiationHeadmanReviewSummaryFina-list',
    meta: {
      title: '评审汇总'
    },
    component: negotiationHeadmanReviewSummaryFina
  }
]
