import evaBidPreOrganGroup from '@/pages/enquiry-bidding/evaluation-bid-prepare/organize-group'
import evaBidPreReviewProject from '@/pages/enquiry-bidding/evaluation-bid-prepare/review-project'

export default [
  // type: enquiry(询价)、trial（资审）
  {
    path: '/enquiry-bidding/evaluation-bid-prepare/organize-group/:type',
    name: 'evaBidPreOrganGroup-list',
    meta: {
      title: '组建小组',
      layout: 'EnquirySystem'
    },
    component: evaBidPreOrganGroup
  },
  {
    path: '/enquiry-bidding/evaluation-bid-prepare/review-project',
    name: 'evaBidPreReviewProject-list',
    meta: {
      title: '查看评审项目',
      layout: 'EnquirySystem'
    },
    component: evaBidPreReviewProject
  }
]
