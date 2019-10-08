import consultationEssentials from '@/pages/competitive-consultation/consultation-headman/consultation/consultation-essentials'
import consultationResult from '@/pages/competitive-consultation/consultation-headman/consultation/consultation-result'
import consultationCon from '@/pages/competitive-consultation/consultation-headman/consultation/index'
import consultationReviewSummary from '@/pages/competitive-consultation/consultation-headman/consultation/review-summary'
import consultationReviewSummaryMember from '@/pages/competitive-consultation/consultation-headman/consultation/review-summary-member'
import consultationReviewConsultationSummary from '@/pages/competitive-consultation/consultation-headman/consultation/consultation-summary'

export default [
  {
    path: '/consultation-headman/consultation/consultation-essentials/:num',
    name: 'consultationEssentials-list',
    meta: {
      title: '磋商要点'
    },
    component: consultationEssentials
  },
  {
    path: '/consultation-headman/consultation/:num',
    name: 'consultingCon-list',
    meta: {
      title: '磋商'
    },
    component: consultationCon
  },
  {
    path: '/consultation-headman/consultation/consultation-result/:num',
    name: 'consultationResult-list',
    meta: {
      title: '磋商结果'
    },
    component: consultationResult
  },
  {
    path: '/consultation-headman/consultation/review-summary/:num',
    name: 'consultationReviewSummary-list',
    meta: {
      title: '评审汇总'
    },
    component: consultationReviewSummary
  },
  {
    path: '/consultation-headman/consultation/review-summary-member/:num',
    name: 'consultationReviewSummary-listMember',
    meta: {
      title: '评审汇总'
    },
    component: consultationReviewSummaryMember
  },
  {
    path: '/consultation-headman/consultation/consultation-summary/:num',
    name: 'consultationReviewSummary-consultationSummary',
    meta: {
      title: '磋商纪要'
    },
    component: consultationReviewConsultationSummary
  }
]
