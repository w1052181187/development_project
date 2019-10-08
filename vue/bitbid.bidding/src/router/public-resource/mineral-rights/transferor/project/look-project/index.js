import look from '@/pages/mineral-rights/transferor/project/look-project/index'
import detail from '@/pages/mineral-rights/transferor/project/look-project/detail'
export default[
  {
    path: '/mr/transferor/project/look-project',
    name: 'look-list',
    meta: {
      title: '查看项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: look
  },
  {
    path: '/mr/transferor/project/look-project/detail/:objectId',
    name: 'look-detail',
    meta: {
      title: '查看项目-查看详情',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  }
]
