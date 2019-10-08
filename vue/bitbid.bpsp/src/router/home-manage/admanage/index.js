import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/home-manage/admanage',
    name: 'admanage',
    meta: {
      permissions: [1, 2],
      title: '广告管理' + titleName,
      active: '/index/home-manage/admanage'
    },
    component: resolve => require(['@/pages/home-manage/admanage/index'], resolve)
  },
  {
    path: '/index/admanage/add',
    name: 'adadd',
    meta: {
      permissions: [1, 2],
      title: '新增广告' + titleName,
      active: '/index/home-manage/admanage'
    },
    component: resolve => require(['@/pages/home-manage/admanage/add'], resolve)
  },
  {
    path: '/index/admanage/update',
    name: 'adupdate',
    meta: {
      permissions: [1, 2],
      title: '修改广告' + titleName,
      active: '/index/home-manage/admanage'
    },
    component: resolve => require(['@/pages/home-manage/admanage/update'], resolve)
  }
]
