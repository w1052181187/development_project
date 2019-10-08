import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/bidstate',
    name: 'bidstate',
    meta: {
      permissions: [1, 2],
      title: '招标声明' + titleName,
      active: '/index/info-manage/bidstate'
    },
    component: resolve => require(['@/pages/info-manage/bidstate/index'], resolve)
  },
  {
    path: '/index/bidstate/add',
    name: 'bidadd',
    meta: {
      permissions: [1, 2],
      title: '新增招标声明' + titleName,
      active: '/index/info-manage/bidstate'
    },
    component: resolve => require(['@/pages/info-manage/bidstate/add'], resolve)
  },
  {
    path: '/index/bidstate/update',
    name: 'bidupdate',
    meta: {
      permissions: [1, 2],
      title: '修改招标声明' + titleName
    },
    component: resolve => require(['@/pages/info-manage/bidstate/update'], resolve)
  },
  {
    path: '/index/bidstate/detail/:objectId',
    name: 'biddetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/bidstate'
    },
    component: resolve => require(['@/pages/info-manage/bidstate/detail'], resolve)
  }
]
