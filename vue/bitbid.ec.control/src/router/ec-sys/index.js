import {titleName} from '@/assets/js/common'
export default[
  {
    path: '/index/ec-trade-record',
    name: 'ec-trade-record',
    meta: {
      roleIds: [999],
      title: '交易记录管理' + titleName,
      active: '/index/ec-trade-record'
    },
    component: resolve => require(['@/pages/ec-sys/index'], resolve)
  },
  {
    path: '/index/ec-trade-details',
    name: 'ec-trade-details',
    meta: {
      roleIds: [999],
      title: '交易记录管理详情' + titleName,
      active: '/index/ec-trade-record'
    },
    component: resolve => require(['@/pages/ec-sys/details'], resolve)
  },
  {
    path: '/index/ec-purch-statis',
    name: 'ec-purch-statis',
    meta: {
      roleIds: [999],
      title: '采购人消费统计' + titleName,
      active: '/index/ec-purch-statis'
    },
    component: resolve => require(['@/pages/ec-sys/purch-statis'], resolve)
  }
]
