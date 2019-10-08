import reviewSummary from '@/pages/competitive-negotiation/negotiating-agency/review-summary/review-summary'
import negotiationSummary from '@/pages/competitive-negotiation/negotiating-agency/review-summary/negotiation-summary'
import comNegAuditClarify from '@/pages/competitive-negotiation/negotiating-agency/review-summary/audit-clarify'

export default [
  {
    path: '/negotiating-agency/review-summary/review-summary',
    name: 'reviewSummary-list',
    meta: {
      title: '谈判纪要'
    },
    component: reviewSummary
  },
  {
    path: '/negotiating-agency/review-summary/negotiation-summary',
    name: 'negotiationSummary-list',
    meta: {
      title: '谈判汇总'
    },
    component: negotiationSummary
  },
  {
    path: '/negotiating-agency/review-summary/audit-clarify',
    name: 'comNegAuditClarify-list',
    meta: {
      title: '审核澄清与承诺'
    },
    component: comNegAuditClarify
  }
]
