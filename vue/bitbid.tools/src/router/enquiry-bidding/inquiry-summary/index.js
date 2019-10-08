import enquiryBidInquirySummary from '@/pages/enquiry-bidding/inquiry-summary/summary'

export default [
  // role: 组长(group)、组员(crew)、代理(agent)
  {
    path: '/enquiry-bidding/inquiry-summary/summary/:role',
    name: 'enquiryBidInquirySummary-list',
    meta: {
      title: '汇总',
      layout: 'EnquirySystem'
    },
    component: enquiryBidInquirySummary
  }
]
