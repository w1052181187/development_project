import consultationFinalRoundReview from '@/pages/competitive-consultation/consultation-headman/final-round-review/review'
import consultationFinalRoundResults from '@/pages/competitive-consultation/consultation-headman/final-round-review/review-results'
import consultationHeadmanReviewSummaryFina from '@/pages/competitive-consultation/consultation-headman/final-round-review/review-summary'

export default [
  {
    path: '/consultation-headman/final-round-review/review',
    name: 'consultationHeadmanReview-list',
    meta: {
      title: '评审'
    },
    component: consultationFinalRoundReview
  },
  {
    path: '/consultation-headman/final-round-review/review-results',
    name: 'consultationHeadmanReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: consultationFinalRoundResults
  },
  {
    path: '/consultation-headman/final-round-review/review-summary',
    name: 'consultationHeadmanReviewSummaryFina-list',
    meta: {
      title: '评审汇总'
    },
    component: consultationHeadmanReviewSummaryFina
  }
]
