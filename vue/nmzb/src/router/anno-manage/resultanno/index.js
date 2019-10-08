import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/annomanage/resultanno',
    name: 'resultanno',
    meta: {
      permissions: [1, 2, 3],
      title: '中标公告' + titleName,
      active: '/annomanage/resultanno'
    },
    component: resolve => require(['@/pages/anno-manage/resultanno/index'], resolve)
  },
  {
    path: '/annomanage/resultanno/add/:objectId',
    name: 'resultanno-add',
    meta: {
      permissions: [1, 2, 3],
      title: '添加中标公告' + titleName,
      active: '/annomanage/resultanno'
    },
    component: resolve => require(['@/pages/anno-manage/resultanno/add'], resolve)
  },
  {
    path: '/annomanage/resultanno/update',
    name: 'resultanno-update',
    meta: {
      permissions: [1, 2, 3],
      title: '修改中标公告' + titleName,
      active: '/annomanage/resultanno'
    },
    component: resolve => require(['@/pages/anno-manage/resultanno/update'], resolve)
  },
  {
    path: '/annomanage/resultanno/detail/:objectId',
    name: 'resultanno-detail',
    meta: {
      permissions: [1, 2, 3],
      title: '查看中标公告' + titleName,
      active: '/annomanage/resultanno'
    },
    component: resolve => require(['@/pages/anno-manage/resultanno/detail'], resolve)
  },
  {
    path: '/annomanage/resultanno/change',
    name: 'resultanno-change',
    meta: {
      permissions: [1, 2, 3],
      title: '变更中标公告' + titleName,
      active: '/annomanage/resultanno'
    },
    component: resolve => require(['@/pages/anno-manage/resultanno/change'], resolve)
  }
]
