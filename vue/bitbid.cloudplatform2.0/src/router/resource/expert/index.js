export default [
  {
    path: '/resource/expert',
    name: '专家管理',
    meta: {
      title: '专家管理',
      active: '/resource/expert',
      permission: '1020301'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/expert/index')
  },
  {
    path: '/resource/expert/add',
    name: '专家管理-新增',
    meta: {
      title: '专家管理-新增',
      active: '/resource/expert',
      permission: '1020302'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/expert/edit')
  },
  {
    path: '/resource/expert/update/:objectId',
    name: '专家管理-编辑',
    meta: {
      title: '专家管理-编辑',
      active: '/resource/expert',
      permission: '1020302'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/expert/edit')
  },
  {
    path: '/resource/expert/details/:objectId',
    name: '专家管理-详情',
    meta: {
      title: '专家管理-详情',
      active: '/resource/expert',
      permission: '1020301'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/expert/detail')
  },
  {
    path: '/resource/expert/cooperate/:relatedCode',
    name: '专家管理-合作项目',
    meta: {
      title: '专家管理-合作项目',
      active: '/resource/expert',
      permission: '1020302'
    },
    component: () => import(/* webpackChunkName: 'resource' */ '@/pages/resource/expert/cooperators/index')
  }
]
