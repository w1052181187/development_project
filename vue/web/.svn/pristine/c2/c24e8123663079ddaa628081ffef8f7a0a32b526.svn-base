import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/service-guide',
    name: 'service',
    meta: {
      permissions: [1, 2],
      title: '服务指南' + titleName,
      active: '/index/info-manage/service-guide'
    },
    component: resolve => require(['@/pages/info-manage/service-guide/index'], resolve)
  },
  {
    path: '/index/service-guide/add',
    name: 'serviceAdd',
    meta: {
      permissions: [1, 2],
      title: '新增服务指南' + titleName,
      active: '/index/info-manage/service-guide'
    },
    component: resolve => require(['@/pages/info-manage/service-guide/add'], resolve)
  },
  {
    path: '/index/service-guide/update',
    name: 'serviceUpdate',
    meta: {
      permissions: [1, 2],
      title: '修改服务指南' + titleName,
      active: '/index/info-manage/service-guide'
    },
    component: resolve => require(['@/pages/info-manage/service-guide/update'], resolve)
  },
  {
    path: '/index/service-guide/detail/:objectId',
    name: 'serviceDetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/service-guide'
    },
    component: resolve => require(['@/pages/info-manage/service-guide/detail'], resolve)
  }
]
