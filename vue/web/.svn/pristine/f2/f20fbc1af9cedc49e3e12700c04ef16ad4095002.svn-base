import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/annoapprove/ckresultanno',
    name: 'ckresultanno',
    meta: {
      permissions: [1, 2],
      title: '中标公告审核' + titleName,
      active: '/annoapprove/ckresultanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckresultanno/index'], resolve)
  },
  {
    path: '/annoapprove/ckresultanno/check',
    name: 'ckresultanno-check',
    meta: {
      permissions: [1, 2],
      title: '审核中标公告' + titleName,
      active: '/annoapprove/ckresultanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckresultanno/check'], resolve)
  },
  {
    path: '/annoapprove/ckresultanno/detail/:objectId',
    name: 'ckresultanno-detail',
    meta: {
      permissions: [1, 2],
      title: '查看中标公告' + titleName,
      active: '/annoapprove/ckresultanno'
    },
    component: resolve => require(['@/pages/anno-approve/ckresultanno/detail'], resolve)
  }
]
