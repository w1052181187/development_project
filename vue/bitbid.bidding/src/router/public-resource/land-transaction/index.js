import assignee from './assignee/index'
import transferor from './transferor/index'
import ltindex from '@/pages/land-transaction/index'

export default[
  {
    path: '/lt/index',
    name: 'ltindex',
    meta: {
      title: '首页',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: ltindex
  },
  ...assignee,
  ...transferor
]
