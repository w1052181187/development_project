import proindexLand from '@/pages/system-management/land-transaction/project/index'
import proExamineLand from '@/pages/system-management/land-transaction/project/examine'
import proDetailLand from '@/pages/system-management/land-transaction/project/detail'

export default[
  {
    path: '/system-management/land-transaction/project/index',
    name: 'landProject-list',
    meta: {
      title: '项目备案',
      layout: 'SystemManagement'
    },
    component: proindexLand
  },
  {
    path: '/system-management/land-transaction/project/examine/:objectId',
    name: 'landProject-examine',
    meta: {
      title: '项目备案审核',
      layout: 'SystemManagement'
    },
    component: proExamineLand
  },
  {
    path: '/system-management/land-transaction/project/detail/:objectId',
    name: 'landProject-detail',
    meta: {
      title: '项目备案详情',
      layout: 'SystemManagement'
    },
    component: proDetailLand
  }
]
