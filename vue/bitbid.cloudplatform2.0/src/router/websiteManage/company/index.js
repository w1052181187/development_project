export default [
  {
    path: '/websiteManage/company',
    name: '公司概况',
    meta: {
      title: '公司概况',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/index')
  },
  {
    path: '/websiteManage/company/updateProfile',
    name: '修改公司简介',
    meta: {
      title: '修改公司简介',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/updateProfile')
  },
  {
    path: '/websiteManage/company/detailProfile',
    name: '查看公司简介',
    meta: {
      title: '查看公司简介',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/detailProfile')
  },
  {
    path: '/websiteManage/company/updateTeam',
    name: '修改团队介绍',
    meta: {
      title: '修改团队介绍',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/updateTeam')
  },
  {
    path: '/websiteManage/company/detailTeam',
    name: '查看团队介绍',
    meta: {
      title: '查看团队介绍',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/detailTeam')
  },
  {
    path: '/websiteManage/company/updateHonor',
    name: '修改资质荣誉',
    meta: {
      title: '修改资质荣誉',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/updateHonor')
  },
  {
    path: '/websiteManage/company/detailHonor',
    name: '查看资质荣誉',
    meta: {
      title: '查看资质荣誉',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/detailHonor')
  },
  {
    path: '/websiteManage/company/updateAddress',
    name: '修改公司地址',
    meta: {
      title: '修改公司地址',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/updateAddress')
  },
  {
    path: '/websiteManage/company/detailAddress',
    name: '查看公司地址',
    meta: {
      title: '查看公司地址',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/detailAddress')
  },
  {
    path: '/websiteManage/company/updateContact',
    name: '修改联系方式',
    meta: {
      title: '修改联系方式',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/updateContact')
  },
  {
    path: '/websiteManage/company/detailContact',
    name: '查看联系方式',
    meta: {
      title: '查看联系方式',
      active: '/websiteManage/company'
    },
    component: () => import(/* webpackChunkName: 'websoteManage' */ '@/pages/websiteManage/company/detailContact')
  }
]
