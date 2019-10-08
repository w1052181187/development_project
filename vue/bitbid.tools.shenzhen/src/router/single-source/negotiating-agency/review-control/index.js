import organizeTrialGrency from '@/pages/single-source/negotiating-agency/review-control/organize-trial-group'
import downTrialStart from '@/pages/single-source/negotiating-agency/review-control/down-trial-start'
import organizeEviewGroup from '@/pages/single-source/negotiating-agency/review-control/organize-eview-group'
import downEviewStart from '@/pages/single-source/negotiating-agency/review-control/down-eview-start'
import reviewProject from '@/pages/single-source/negotiating-agency/review-control/review-project'

export default [
  {
    path: '/single-source/negotiating-agency/review-control/organize-trial-group',
    name: 'single-source-organizeTrialGrency-list',
    meta: {
      title: '组建资审小组'
    },
    component: organizeTrialGrency
  },
  {
    path: '/single-source/negotiating-agency/review-control/down-trial-start',
    name: 'single-source-downTrialStart-list',
    meta: {
      title: '下达开始资审命令'
    },
    component: downTrialStart
  },
  {
    path: '/single-source/negotiating-agency/review-control/organize-eview-group',
    name: 'single-source-organizeEviewGroup-list',
    meta: {
      title: '组建谈判小组'
    },
    component: organizeEviewGroup
  },
  {
    path: '/single-source/negotiating-agency/review-control/down-eview-start',
    name: 'single-source-downEviewStart-list',
    meta: {
      title: '下达开始评标命令'
    },
    component: downEviewStart
  },
  {
    path: '/single-source/negotiating-agency/review-control/review-project',
    name: 'single-source-reviewProject-list',
    meta: {
      title: '查看评审项目'
    },
    component: reviewProject
  }
]
