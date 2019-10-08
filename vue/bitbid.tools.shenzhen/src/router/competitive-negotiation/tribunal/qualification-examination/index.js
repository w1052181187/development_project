import tribunalReview from '@/pages/competitive-negotiation/tribunal/qualification-examination/review'
import tribunalReviewResults from '@/pages/competitive-negotiation/tribunal/qualification-examination/review-results'
import tribunalReviewSummary from '@/pages/competitive-negotiation/tribunal/qualification-examination/review-summary'

export default [
  {
    path: '/tribunal/qualification-examination/review',
    name: 'tribunalReview-list',
    meta: {
      title: '评审'
    },
    component: tribunalReview
  },
  {
    path: '/tribunal/qualification-examination/review-results',
    name: 'tribunalReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: tribunalReviewResults
  },
  {
    path: '/tribunal/qualification-examination/review-summary',
    name: 'tribunalReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: tribunalReviewSummary
  }
]
