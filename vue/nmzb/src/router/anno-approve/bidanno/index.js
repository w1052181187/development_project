import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/annoapprove/ckbidanno',
    name: 'ckbidanno',
    meta: {
      permissions: [1, 2],
      title: '招标公告审核' + titleName,
      active: '/annoapprove/ckbidanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckbidanno/index'], resolve)
  },
  {
    path: '/annoapprove/ckbidanno/check',
    name: 'ckbidanno-check',
    meta: {
      permissions: [1, 2],
      title: '审核招标公告' + titleName,
      active: '/annoapprove/ckbidanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckbidanno/check'], resolve)
  },
  {
    path: '/annoapprove/ckbidanno/detail/:objectId',
    name: 'ckbidanno-detail',
    meta: {
      permissions: [1, 2],
      title: '查看招标公告' + titleName,
      active: '/annoapprove/ckbidanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckbidanno/detail'], resolve)
  }
]
