import evaBidPreInquiryResults from '@/pages/open-tendering/evaluation-bid-summary/inquiry-results'

export default [
  {
    path: '/open-tendering/evaluation-bid-summary/inquiry-results/:role',
    name: 'openTenderEvaBidPreInquiryResults',
    meta: {
      title: '询价结果',
      layout: 'EnquirySystem'
    },
    component: evaBidPreInquiryResults
  }
]
