import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/home-manage/notice',
    name: 'notice',
    meta: {
      permissions: [1, 2],
      title: '通知动态' + titleName,
      active: '/index/home-manage/notice'
    },
    component: resolve => require(['@/pages/home-manage/notice/index'], resolve)
  },
  {
    path: '/index/notice/add',
    name: 'notadd',
    meta: {
      permissions: [1, 2],
      title: '新增通知动态' + titleName,
      active: '/index/home-manage/notice'
    },
    component: resolve => require(['@/pages/home-manage/notice/add'], resolve)
  },
  {
    path: '/index/notice/update',
    name: 'notupdate',
    meta: {
      permissions: [1, 2],
      title: '修改通知' + titleName,
      active: '/index/home-manage/notice'
    },
    component: resolve => require(['@/pages/home-manage/notice/update'], resolve)
  },
  {
    path: '/index/notice/detail/:objectId',
    name: 'notdetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/home-manage/notice'
    },
    component: resolve => require(['@/pages/home-manage/notice/detail'], resolve)
  }
]
