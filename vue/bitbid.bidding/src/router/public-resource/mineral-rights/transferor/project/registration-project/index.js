import registration from '@/pages/mineral-rights/transferor/project/registration-project/index'
import update from '@/pages/mineral-rights/transferor/project/registration-project/update'
import detail from '@/pages/mineral-rights/transferor/project/registration-project/detail'
import add from '@/pages/mineral-rights/transferor/project/registration-project/add'
export default[
  {
    path: '/mr/transferor/project/registration-project',
    name: 'registration-list',
    meta: {
      title: '登记项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: registration
  },
  {
    path: '/mr/transferor/project/registration-project/update',
    name: 'registration-update',
    meta: {
      title: '登记-编辑项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: update
  },
  {
    path: '/mr/transferor/project/registration-project/detail/:objectId',
    name: 'registration-detail',
    meta: {
      title: '登记-查看项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  },
  {
    path: '/mr/transferor/project/registration-project/add',
    name: 'registration-add',
    meta: {
      title: '登记-添加项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: add
  }
]
