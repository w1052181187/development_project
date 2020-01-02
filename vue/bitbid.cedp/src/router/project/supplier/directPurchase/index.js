export default[
  {
    path: '/supplier/directPurchase',
    name: 'sDirectPurchase',
    meta: {
      active: '/supplier/directPurchase',
      title: '直接采购',
      layout: 'supplier'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/project/supplier/directPurchase/index')
  },
  {
    path: '/supplier/directPurchase/offer/:objectId',
    name: 'sDirectPurchaseOffer',
    meta: {
      active: '/supplier/directPurchase',
      title: '直采报价',
      layout: 'supplier'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/project/supplier/directPurchase/offer')
  },
  {
    path: '/supplier/directPurchase/detail/:objectId',
    name: 'sDirectPurchaseDetail',
    meta: {
      active: '/supplier/directPurchase',
      title: '查看直采信息',
      layout: 'supplier'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/project/supplier/directPurchase/detail')
  },
  {
    path: '/supplier/directPurchase/offerDetail/:objectId',
    name: 'sDirectPurchaseOfferDetail',
    meta: {
      active: '/supplier/directPurchase',
      title: '查看直采报价',
      layout: 'supplier'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/project/supplier/directPurchase/offerDetail')
  }
]
