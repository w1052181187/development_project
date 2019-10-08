export default [
  {
    path: '/quickMenu',
    name: '快捷功能自定义',
    meta: {
      title: '快捷功能自定义',
      active: '/main',
      permission: 'business'
    },
    component: () => import(/* webpackChunkName: 'index' */ '@/pages/home/index')
  },
  {
    path: '/bidOpening',
    name: '待开标',
    meta: {
      title: '待开标',
      active: '/main',
      permission: '1040301'
    },
    component: () => import(/* webpackChunkName: 'index' */ '@/pages/home/bidOpening')
  },
  {
    path: '/notify',
    name: '知会与我',
    meta: {
      title: '知会与我',
      active: '/main',
      permission: 'business'
    },
    component: () => import(/* webpackChunkName: 'index' */ '@/pages/home/notify')
  },
  {
    path: '/history',
    name: '版本历程',
    meta: {
      title: '版本历程',
      active: '/main'
    },
    component: () => import(/* webpackChunkName: 'index' */ '@/pages/home/history')
  }
]
