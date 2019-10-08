import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/home-manage/bay-window',
    name: 'bay-window',
    meta: {
      permissions: [1, 2],
      title: '飘窗信息' + titleName,
      active: '/index/home-manage/bay-window'
    },
    component: resolve => require(['@/pages/home-manage/bay-window/index'], resolve)
  },
  {
    path: '/index/bay-window/add',
    name: 'bay-windowAdd',
    meta: {
      permissions: [1, 2],
      title: '新增飘窗信息' + titleName,
      active: '/index/home-manage/bay-window'
    },
    component: resolve => require(['@/pages/home-manage/bay-window/add'], resolve)
  },
  {
    path: '/index/bay-window/update',
    name: 'bay-windowUpdate',
    meta: {
      permissions: [1, 2],
      title: '修改飘窗信息' + titleName,
      active: '/index/home-manage/bay-window'
    },
    component: resolve => require(['@/pages/home-manage/bay-window/update'], resolve)
  }
]
