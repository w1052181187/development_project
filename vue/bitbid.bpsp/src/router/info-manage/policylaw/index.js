import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/info-manage/policylaw',
    name: 'policylaw',
    meta: {
      permissions: [1, 2],
      title: '政策法规' + titleName,
      active: '/index/info-manage/policylaw'
    },
    component: resolve => require(['@/pages/info-manage/policylaw/index'], resolve)
  },
  {
    path: '/index/policylaw/add',
    name: 'poladd',
    meta: {
      permissions: [1, 2],
      title: '新增政策法规' + titleName,
      active: '/index/info-manage/policylaw'
    },
    component: resolve => require(['@/pages/info-manage/policylaw/add'], resolve)
  },
  {
    path: '/index/policylaw/update',
    name: 'polupdate',
    meta: {
      permissions: [1, 2],
      title: '修改政策法规' + titleName,
      active: '/index/info-manage/policylaw'
    },
    component: resolve => require(['@/pages/info-manage/policylaw/update'], resolve)
  },
  {
    path: '/index/policylaw/detail/:objectId',
    name: 'poldetail',
    meta: {
      permissions: [1, 2],
      title: '详情' + titleName,
      active: '/index/info-manage/policylaw'
    },
    component: resolve => require(['@/pages/info-manage/policylaw/detail'], resolve)
  }
]
