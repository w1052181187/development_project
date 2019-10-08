import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/notice',
    name: 'notice',
    meta: {
      permissions: [1, 2],
      title: '通知动态' + titleName,
      active: '/index/info-manage/notice'
    },
    component: resolve => require(['@/pages/info-manage/notice/index'], resolve)
  },
  {
    path: '/index/notice/add',
    name: 'notadd',
    meta: {
      permissions: [1, 2],
      title: '新增通知动态' + titleName,
      active: '/index/info-manage/notice'
    },
    component: resolve => require(['@/pages/info-manage/notice/add'], resolve)
  },
  {
    path: '/index/notice/update',
    name: 'notupdate',
    meta: {
      permissions: [1, 2],
      title: '修改通知动态' + titleName,
      active: '/index/info-manage/notice'
    },
    component: resolve => require(['@/pages/info-manage/notice/update'], resolve)
  },
  {
    path: '/index/notice/detail/:objectId',
    name: 'notdetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/notice'
    },
    component: resolve => require(['@/pages/info-manage/notice/detail'], resolve)
  }
]
