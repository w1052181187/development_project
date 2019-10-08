/**
 * Created by lixuqiang on 2018/9/18.
 */
import qualificationReview from '@/pages/single-source/negotiating-agency/qualification-review/review'
import qualificationReviewResults from '@/pages/single-source/negotiating-agency/qualification-review/review-results'
import qualificationReviewSummary from '@/pages/single-source/negotiating-agency/qualification-review/review-summary'
import qualificationDownEviewEnd from '@/pages/single-source/negotiating-agency/qualification-review/down-eview-end'

export default [
  {
    path: '/single-source/negotiating-agency/qualification-review/review',
    name: 'single-source-qualificationReview-list',
    meta: {
      title: '评审'
    },
    component: qualificationReview
  },
  {
    path: '/single-source/negotiating-agency/qualification-review/review-results',
    name: 'single-source-qualificationReviewResults-list',
    meta: {
      title: '评审结果'
    },
    component: qualificationReviewResults
  },
  {
    path: '/single-source/negotiating-agency/qualification-review/review-summary',
    name: 'single-source-qualificationReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: qualificationReviewSummary
  },
  {
    path: '/single-source/negotiating-agency/qualification-review/down-eview-end',
    name: 'single-source-qualificationDownEviewEnd-list',
    meta: {
      title: '下达资审结束命令'
    },
    component: qualificationDownEviewEnd
  }
]
