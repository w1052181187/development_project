import reviewSummary from '@/pages/competitive-consultation/consultation-agency/review-summary/review-summary'
import consultationSummary from '@/pages/competitive-consultation/consultation-agency/review-summary/consultation-summary'
import comConsulAuditClarify from '@/pages/competitive-consultation/consultation-agency/review-summary/audit-clarify'
import comConsulAuditOffer from '@/pages/competitive-consultation/consultation-agency/review-summary/look-offer'

export default [
  {
    path: '/consultation-agency/review-summary/review-summary',
    name: 'consultation-reviewSummary-list',
    meta: {
      title: '磋商纪要'
    },
    component: reviewSummary
  },
  {
    path: '/consultation-agency/review-summary/consultation-summary',
    name: 'consultationSummary-list',
    meta: {
      title: '磋商汇总'
    },
    component: consultationSummary
  },
  {
    path: '/consultation-agency/review-summary/audit-clarify',
    name: 'comConsulAuditClarify-list',
    meta: {
      title: '审核澄清与承诺'
    },
    component: comConsulAuditClarify
  },
  {
    path: '/consultation-agency/review-summary/look-offer',
    name: 'comConsulAudit-offer',
    meta: {
      title: '查看报价'
    },
    component: comConsulAuditOffer
  }
]
