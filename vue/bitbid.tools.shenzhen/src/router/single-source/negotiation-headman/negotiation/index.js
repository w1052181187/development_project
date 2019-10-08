import negotiationEssentials from '@/pages/single-source/negotiation-headman/negotiation/negotiation-essentials'
import negotiationResult from '@/pages/single-source/negotiation-headman/negotiation/negotiation-result'
import negotiationCon from '@/pages/single-source/negotiation-headman/negotiation/negotiation'
import negotiationReviewSummary from '@/pages/single-source/negotiation-headman/negotiation/review-summary'
import negotiationReviewSummaryMember from '@/pages/single-source/negotiation-headman/negotiation/review-summary-member'
import negotiationReviewNegotiateSummary from '@/pages/single-source/negotiation-headman/negotiation/negotiate-summary'

export default [
  {
    path: '/single-source/negotiation-headman/negotiation/negotiation-essentials/:num',
    name: 'single-source-negotiationEssentials-list',
    meta: {
      title: '谈判要点'
    },
    component: negotiationEssentials
  },
  {
    path: '/single-source/negotiation-headman/negotiation/negotiation/:num',
    name: 'single-source-negotiationCon-list',
    meta: {
      title: '谈判'
    },
    component: negotiationCon
  },
  {
    path: '/single-source/negotiation-headman/negotiation/negotiation-result/:num',
    name: 'single-source-negotiationResult-list',
    meta: {
      title: '谈判结果'
    },
    component: negotiationResult
  },
  {
    path: '/single-source/negotiation-headman/negotiation/review-summary/:num',
    name: 'single-source-negotiationReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: negotiationReviewSummary
  },
  {
    path: '/single-source/negotiation-headman/negotiation/review-summary-member/:num',
    name: 'single-source-negotiationReviewSummary-listMember',
    meta: {
      title: '评审汇总'
    },
    component: negotiationReviewSummaryMember
  },
  {
    path: '/single-source/negotiation-headman/negotiation/negotiate-summary/:num',
    name: 'single-source-negotiationReviewSummary-negotiateSummary',
    meta: {
      title: '谈判纪要'
    },
    component: negotiationReviewNegotiateSummary
  }
]
