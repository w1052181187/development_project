/**
 * Created by lixuqiang on 2018-11-12.
 */
import review from '@/pages/open-tendering/review/index'
import reviewResult from '@/pages/open-tendering/review-result/index'
import reviewSummary from '@/pages/open-tendering/review-summary/index'

export default [
  // type(qualification:资格性审核、form-response:形式性响应审核、rationality:合理性检查)
  {
    path: '/open-tendering/review/:type',
    name: 'openTendering-review',
    meta: {
      title: '评审',
      layout: 'EnquirySystem'
    },
    component: review
  },
  {
    path: '/open-tendering/review-result/:type',
    name: 'openTendering-reviewResult',
    meta: {
      title: '评审结果',
      layout: 'EnquirySystem'
    },
    component: reviewResult
  },
  {
    path: '/open-tendering/review-summary/:type',
    name: 'openTendering-reviewSummary',
    meta: {
      title: '评审汇总',
      layout: 'EnquirySystem'
    },
    component: reviewSummary
  }
]
