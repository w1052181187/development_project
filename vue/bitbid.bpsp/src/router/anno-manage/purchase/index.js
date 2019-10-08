import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/annomanage/purchase',
    name: 'purchase',
    meta: {
      permissions: [1, 2, 3],
      title: '采购公告' + titleName,
      active: '/annomanage/purchase'
    },
    component: resolve => require(['@/pages/anno-manage/purchase/index'], resolve)
  },
  {
    path: '/annomanage/purchase/add',
    name: 'purchase-add',
    meta: {
      permissions: [1, 2, 3],
      title: '添加采购公告' + titleName,
      active: '/annomanage/purchase'
    },
    component: resolve => require(['@/pages/anno-manage/purchase/add'], resolve)
  },
  {
    path: '/annomanage/purchase/update',
    name: 'purchase-update',
    meta: {
      permissions: [1, 2, 3],
      title: '修改采购公告' + titleName,
      active: '/annomanage/purchase'
    },
    component: resolve => require(['@/pages/anno-manage/purchase/update'], resolve)
  },
  {
    path: '/annomanage/purchase/detail/:objectId',
    name: 'purchase-detail',
    meta: {
      permissions: [1, 2, 3],
      title: '查看采购公告' + titleName,
      active: '/annomanage/purchase'
    },
    component: resolve => require(['@/pages/anno-manage/purchase/detail'], resolve)
  }
]
