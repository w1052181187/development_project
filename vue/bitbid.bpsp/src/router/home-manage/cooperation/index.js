import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/index/home-manage/cooperation',
    name: 'cooperation',
    meta: {
      permissions: [1, 2],
      title: '合作单位' + titleName,
      active: '/index/home-manage/cooperation'
    },
    component: resolve => require(['@/pages/home-manage/cooperation/index'], resolve)
  },
  {
    path: '/index/cooperation/add',
    name: 'coadd',
    meta: {
      permissions: [1, 2],
      title: '新增合作单位' + titleName,
      active: '/index/home-manage/cooperation'
    },
    component: resolve => require(['@/pages/home-manage/cooperation/add'], resolve)
  },
  {
    path: '/index/cooperation/update',
    name: 'coupdate',
    meta: {
      permissions: [1, 2],
      title: '修改合作单位' + titleName,
      active: '/index/home-manage/cooperation'
    },
    component: resolve => require(['@/pages/home-manage/cooperation/update'], resolve)
  }
]
