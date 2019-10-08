import organizeTrialGrency from '@/pages/competitive-consultation/consultation-agency/review-control/organize-trial-group'
import downTrialStart from '@/pages/competitive-consultation/consultation-agency/review-control/down-trial-start'
import organizeEviewGroup from '@/pages/competitive-consultation/consultation-agency/review-control/organize-eview-group'
import downEviewStart from '@/pages/competitive-consultation/consultation-agency/review-control/down-eview-start'
import reviewProject from '@/pages/competitive-consultation/consultation-agency/review-control/review-project'

export default [
  {
    path: '/consultation-agency/review-control/organize-trial-group',
    name: 'consultation-organizeTrialGrency-list',
    meta: {
      title: '组建资审小组'
    },
    component: organizeTrialGrency
  },
  {
    path: '/consultation-agency/review-control/down-trial-start',
    name: 'consultation-downTrialStart-list',
    meta: {
      title: '下达开始资审命令'
    },
    component: downTrialStart
  },
  {
    path: '/consultation-agency/review-control/organize-eview-group',
    name: 'consultation-organizeEviewGroup-list',
    meta: {
      title: '组建磋商小组'
    },
    component: organizeEviewGroup
  },
  {
    path: '/consultation-agency/review-control/down-eview-start',
    name: 'consultation-downEviewStart-list',
    meta: {
      title: '下达开始评标命令'
    },
    component: downEviewStart
  },
  {
    path: '/consultation-agency/review-control/review-project',
    name: 'consultation-reviewProject-list',
    meta: {
      title: '查看评审项目'
    },
    component: reviewProject
  }
]
