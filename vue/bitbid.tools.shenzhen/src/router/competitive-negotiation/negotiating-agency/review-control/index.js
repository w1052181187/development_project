import organizeTrialGrency from '@/pages/competitive-negotiation/negotiating-agency/review-control/organize-trial-group'
import downTrialStart from '@/pages/competitive-negotiation/negotiating-agency/review-control/down-trial-start'
import organizeEviewGroup from '@/pages/competitive-negotiation/negotiating-agency/review-control/organize-eview-group'
import downEviewStart from '@/pages/competitive-negotiation/negotiating-agency/review-control/down-eview-start'
import reviewProject from '@/pages/competitive-negotiation/negotiating-agency/review-control/review-project'

export default [
  {
    path: '/negotiating-agency/review-control/organize-trial-group',
    name: 'organizeTrialGrency-list',
    meta: {
      title: '组建资审小组'
    },
    component: organizeTrialGrency
  },
  {
    path: '/negotiating-agency/review-control/down-trial-start',
    name: 'downTrialStart-list',
    meta: {
      title: '下达开始资审命令'
    },
    component: downTrialStart
  },
  {
    path: '/negotiating-agency/review-control/organize-eview-group',
    name: 'organizeEviewGroup-list',
    meta: {
      title: '组建谈判小组'
    },
    component: organizeEviewGroup
  },
  {
    path: '/negotiating-agency/review-control/down-eview-start',
    name: 'downEviewStart-list',
    meta: {
      title: '下达开始评标命令'
    },
    component: downEviewStart
  },
  {
    path: '/negotiating-agency/review-control/review-project',
    name: 'reviewProject-list',
    meta: {
      title: '查看评审项目'
    },
    component: reviewProject
  }
]
