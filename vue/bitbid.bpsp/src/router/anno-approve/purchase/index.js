import {titleName} from '@/assets/js/common'

export default[
  {
    path: '/annoapprove/ckpurchase',
    name: 'ckpurchase',
    meta: {
      permissions: [1, 2],
      title: '采购公告审核' + titleName,
      active: '/annoapprove/ckpurchase'
    },
    component: resolve => require(['@/pages/anno-approve/ckpurchase/index'], resolve)
  },
  {
    path: '/annoapprove/ckpurchase/check',
    name: 'ckpurchase-add',
    meta: {
      permissions: [1, 2],
      title: '审核采购公告' + titleName,
      active: '/annoapprove/ckpurchase'
    },
    component: resolve => require(['@/pages/anno-approve/ckpurchase/check'], resolve)
  },
  {
    path: '/annoapprove/ckpurchase/detail/:objectId',
    name: 'ckpurchase-detail',
    meta: {
      permissions: [1, 2],
      title: '查看采购公告' + titleName,
      active: '/annoapprove/ckpurchase'
    },
    component: resolve => require(['@/pages/anno-approve/ckpurchase/detail'], resolve)
  }
]
