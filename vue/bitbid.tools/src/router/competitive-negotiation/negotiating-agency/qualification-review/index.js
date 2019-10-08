/**
 * Created by lixuqiang on 2018/9/18.
 */
import qualificationReview from '@/pages/competitive-negotiation/negotiating-agency/qualification-review/review'
import qualificationReviewResults from '@/pages/competitive-negotiation/negotiating-agency/qualification-review/review-results'
import qualificationReviewSummary from '@/pages/competitive-negotiation/negotiating-agency/qualification-review/review-summary'
import qualificationDownEviewEnd from '@/pages/competitive-negotiation/negotiating-agency/qualification-review/down-eview-end'

export default [
  {
    path: '/negotiating-agency/qualification-review/review',
    name: 'qualificationReview-list',
    meta: {
      title: '评审'
    },
    component: qualificationReview
  },
  {
    path: '/negotiating-agency/qualification-review/review-results',
    name: 'qualificationReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: qualificationReviewResults
  },
  {
    path: '/negotiating-agency/qualification-review/review-summary',
    name: 'qualificationReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: qualificationReviewSummary
  },
  {
    path: '/negotiating-agency/qualification-review/down-eview-end',
    name: 'qualificationDownEviewEnd-list',
    meta: {
      title: '下达资审结束命令'
    },
    component: qualificationDownEviewEnd
  }
]
