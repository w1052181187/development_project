import consultationHeadmanReview from '@/pages/competitive-consultation/consultation-headman/qualification-examination/review'
import consultationHeadmanReviewResults from '@/pages/competitive-consultation/consultation-headman/qualification-examination/review-results'
import consultationHeadmanReviewSummary from '@/pages/competitive-consultation/consultation-headman/qualification-examination/review-summary'

export default [
  {
    path: '/consultation-headman/qualification-examination/review',
    name: 'consultation-headmanReview-list',
    meta: {
      title: '评审'
    },
    component: consultationHeadmanReview
  },
  {
    path: '/consultation-headman/qualification-examination/review-results',
    name: 'consultation-headmanReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: consultationHeadmanReviewResults
  },
  {
    path: '/consultation-headman/qualification-examination/review-summary',
    name: 'consultation-headmanReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: consultationHeadmanReviewSummary
  }
]
