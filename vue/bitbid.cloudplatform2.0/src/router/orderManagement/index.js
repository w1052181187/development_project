export default[
  {
    path: '/orderManagement/myOrder',
    name: '我的订单',
    meta: {
      title: '我的订单',
      active: '/orderManagement/myOrder',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/orderManagement/myOrder/myOrderList')
  },
  {
    path: '/orderManagement/myOrder/myOrderDetail/:objectId',
    name: '我的订单详情',
    meta: {
      title: '我的订单详情',
      active: '/orderManagement/myOrder',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/orderManagement/myOrder/myOrderDetail')
  }
]
