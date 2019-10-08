import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/industry-supervision',
    name: 'industry-supervision',
    meta: {
      permissions: [1, 2],
      title: '行业监督' + titleName,
      active: '/index/info-manage/industry-supervision'
    },
    component: resolve => require(['@/pages/info-manage/industry-supervision/index'], resolve)
  },
  {
    path: '/index/industry-supervision/add',
    name: 'industry-add',
    meta: {
      permissions: [1, 2],
      title: '新增行业监督' + titleName,
      active: '/index/info-manage/industry-supervision'
    },
    component: resolve => require(['@/pages/info-manage/industry-supervision/add'], resolve)
  },
  {
    path: '/index/industry-supervision/update',
    name: 'industry-update',
    meta: {
      permissions: [1, 2],
      title: '修改行业监督' + titleName,
      active: '/index/info-manage/industry-supervision'
    },
    component: resolve => require(['@/pages/info-manage/industry-supervision/update'], resolve)
  },
  {
    path: '/index/industry-supervision/detail/:objectId',
    name: 'industry-detail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/industry-supervision'
    },
    component: resolve => require(['@/pages/info-manage/industry-supervision/detail'], resolve)
  }
]
