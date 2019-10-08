import massifRregister from '@/pages/land-transaction/transferor/project/massif-register/index'
import massifRregisterAdd from '@/pages/land-transaction/transferor/project/massif-register/add'
import massifRregisterUpdate from '@/pages/land-transaction/transferor/project/massif-register/update'
import massifRregisterDetail from '@/pages/land-transaction/transferor/project/massif-register/detail'
export default[
  {
    path: '/lt/transferor/project/massif-register',
    name: 'massifRregister-list',
    meta: {
      title: '地块登记',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: massifRregister
  },
  {
    path: '/lt/transferor/project/massif-register/add',
    name: 'massifRregister-add',
    meta: {
      title: '添加地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: massifRregisterAdd
  },
  {
    path: '/lt/transferor/project/massif-register/update',
    name: 'massifRregister-update',
    meta: {
      title: '修改地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: massifRregisterUpdate
  },
  {
    path: '/lt/transferor/project/massif-register/detail/:objectId',
    name: 'massifRregister-detail',
    meta: {
      title: '查看地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: massifRregisterDetail
  }
]
