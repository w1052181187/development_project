export default[
  {
    path: '/biddingSources/announcement',
    name: '比选公告',
    meta: {
      title: '比选公告',
      active: '/biddingSources/announcement',
      permission: '1030101'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/announcement/index')
  },
  {
    path: '/biddingSources/announcementList',
    name: '比选公告列表',
    meta: {
      title: '比选公告列表',
      active: '/biddingSources/announcementList',
      permission: '1030101'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/announcement/announcementList')
  },
  {
    path: '/biddingSources/announcementDetail/:relatedCode',
    name: '公告详情',
    meta: {
      title: '公告详情',
      active: '/biddingSources/announcementDetail',
      permission: '1030101'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/announcement/announcementDetail')
  },
  {
    path: '/biddingSources/competitorDetail/:relatedCode',
    name: '竞争对手详情',
    meta: {
      title: '竞争对手详情',
      active: '/biddingSources/competitorDetail',
      permission: '1030101'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/announcement/competitorDetail')
  },
  {
    path: '/biddingSources/buildProject',
    name: '拟在建项目',
    meta: {
      title: '拟在建项目',
      active: '/biddingSources/buildProject',
      permission: '1030201'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/buildProject/index')
  },
  {
    path: '/biddingSources/buildProject/detail/:code',
    name: '拟在建项目-详情',
    meta: {
      title: '拟在建项目-详情',
      active: '/biddingSources/buildProject',
      permission: '1030201'
    },
    component: () => import(/* webpackChunkName: 'agency' */ '@/pages/biddingSources/buildProject/detail')
  }
]
