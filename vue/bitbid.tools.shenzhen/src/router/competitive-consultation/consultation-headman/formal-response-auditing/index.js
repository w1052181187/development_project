import consultationHeadmanReviewFor from '@/pages/competitive-consultation/consultation-headman/formal-response-auditing/review'
import consultationHeadmanReviewResultsFor from '@/pages/competitive-consultation/consultation-headman/formal-response-auditing/review-results'
import consultationHeadmanReviewSummary from '@/pages/competitive-consultation/consultation-headman/formal-response-auditing/review-summary'

export default [
  {
    path: '/consultation-headman/formal-response-auditing/review',
    name: 'consultationHeadmanReviewFor-list',
    meta: {
      title: '评审'
    },
    component: consultationHeadmanReviewFor
  },
  {
    path: '/consultation-headman/formal-response-auditing/review-results',
    name: 'consultationHeadmanReviewResultsFor-list',
    meta: {
      title: '评审结果'
    },
    component: consultationHeadmanReviewResultsFor
  },
  {
    path: '/consultation-headman/formal-response-auditing/review-summary',
    name: 'consultationHeadmanReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: consultationHeadmanReviewSummary
  }
]
