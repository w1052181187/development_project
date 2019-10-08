/**
 * Created by lixuqiang on 2018-11-12.
 */
import review from '../../../pages/enquiry-bidding/review/index'
import reviewResult from '../../../pages/enquiry-bidding/review-result/index'
import reviewSummary from '../../../pages/enquiry-bidding/review-summary/index'

export default [
  // type(qualification:资格性审核、form-response:形式性响应审核、rationality:合理性检查)
  {
    path: '/enquiry-bidding/review/:type',
    name: 'review',
    meta: {
      title: '评审',
      layout: 'EnquirySystem'
    },
    component: review
  },
  {
    path: '/enquiry-bidding/review-result/:type',
    name: 'reviewResult',
    meta: {
      title: '评审结果',
      layout: 'EnquirySystem'
    },
    component: reviewResult
  },
  {
    path: '/enquiry-bidding/review-summary/:type',
    name: 'reviewSummary',
    meta: {
      title: '评审汇总',
      layout: 'EnquirySystem'
    },
    component: reviewSummary
  }
]
