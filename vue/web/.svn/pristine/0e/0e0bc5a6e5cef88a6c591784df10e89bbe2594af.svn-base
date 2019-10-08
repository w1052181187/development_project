import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/violation',
    name: 'violation',
    meta: {
      permissions: [1, 2],
      title: '违规公示' + titleName,
      active: '/index/info-manage/violation'
    },
    component: resolve => require(['@/pages/info-manage/violation/index'], resolve)
  },
  {
    path: '/index/violation/add',
    name: 'vioadd',
    meta: {
      permissions: [1, 2],
      title: '新增违规公示' + titleName,
      active: '/index/info-manage/violation'
    },
    component: resolve => require(['@/pages/info-manage/violation/add'], resolve)
  },
  {
    path: '/index/violation/update',
    name: 'vioupdate',
    meta: {
      permissions: [1, 2],
      title: '修改违规公示' + titleName,
      active: '/index/info-manage/violation'
    },
    component: resolve => require(['@/pages/info-manage/violation/update'], resolve)
  },
  {
    path: '/index/violation/detail/:objectId',
    name: 'viodetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/violation'
    },
    component: resolve => require(['@/pages/info-manage/violation/detail'], resolve)
  }
]
