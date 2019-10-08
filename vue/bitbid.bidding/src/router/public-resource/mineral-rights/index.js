import assignee from './assignee/index'
import transferor from './transferor/index'
import mrindex from '@/pages/mineral-rights/index'

export default[
  {
    path: '/mr/index',
    name: 'mrindex',
    meta: {
      title: '首页',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: mrindex
  },
  ...assignee,
  ...transferor
]
