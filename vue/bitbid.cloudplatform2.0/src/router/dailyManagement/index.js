export default[
  {
    path: '/dailyManagement/noticeAnnouncement',
    name: '通知公告管理',
    meta: {
      title: '通知公告管理',
      active: '/dailyManagement/noticeAnnouncement',
      type: 'notice',
      permission: '1080101'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/index')
  },
  {
    path: '/dailyManagement/noticeAnnouncement/detail/:objectId',
    name: '查看通知公告',
    meta: {
      title: '查看通知公告',
      active: '/dailyManagement/noticeAnnouncement',
      type: 'notice'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/detail')
  },
  {
    path: '/dailyManagement/noticeAnnouncement/add',
    name: '添加通知公告',
    meta: {
      title: '添加通知公告',
      active: '/dailyManagement/noticeAnnouncement',
      type: 'notice',
      permission: '1080102'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/add')
  },
  {
    path: '/dailyManagement/noticeAnnouncement/update/:objectId',
    name: '修改通知公告',
    meta: {
      title: '修改通知公告',
      active: '/dailyManagement/noticeAnnouncement',
      type: 'notice',
      permission: '1080102'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/add')
  },
  {
    path: '/dailyManagement/companyNews',
    name: '公司动态管理',
    meta: {
      title: '公司动态管理',
      active: '/dailyManagement/companyNews',
      type: 'news',
      permission: '1080102'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/index')
  },
  {
    path: '/dailyManagement/companyNews/detail/:objectId',
    name: '查看公司动态',
    meta: {
      title: '查看公司动态',
      active: '/dailyManagement/companyNews',
      type: 'news'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/detail')
  },
  {
    path: '/dailyManagement/companyNews/add',
    name: '添加公司动态',
    meta: {
      title: '添加公司动态',
      active: '/dailyManagement/companyNews',
      type: 'news',
      permission: '1080102'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/add')
  },
  {
    path: '/dailyManagement/companyNews/update/:objectId',
    name: '修改公司动态',
    meta: {
      title: '修改公司动态',
      active: '/dailyManagement/companyNews',
      type: 'news',
      permission: '1080102'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/add')
  },
  {
    path: '/dailyManagement/homeCompanyNews',
    name: '公司动态',
    meta: {
      title: '公司动态',
      active: '/main',
      type: 'homeNews',
      permission: 'business'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/index')
  },
  {
    path: '/dailyManagement/homeNoticeAnnouncement',
    name: '通知公告',
    meta: {
      title: '通知公告',
      active: '/main',
      type: 'homeNotice',
      permission: 'business'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/dailyManagement/index')
  }
]
