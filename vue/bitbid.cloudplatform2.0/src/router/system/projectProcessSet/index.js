export default [
  {
    path: '/system/proProcessSet',
    name: '项目流程配置管理',
    meta: {
      title: '项目流程配置管理',
      active: '/system/proProcessSet',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'system' */ '@/pages/system/projectProcessSet/index')
  },
  {
    path: '/system/proProcessSet/update',
    name: '项目流程配置-修改',
    meta: {
      title: '项目流程配置-修改',
      active: '/system/proProcessSet',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'system' */ '@/pages/system/projectProcessSet/update')
  },
  {
    path: '/system/proProcessSet/detail/:objectId',
    name: '项目流程配置-详情',
    meta: {
      title: '项目流程配置-详情',
      active: '/system/proProcessSet',
      permission: 'admin'
    },
    component: () => import(/* webpackChunkName: 'system' */ '@/pages/system/projectProcessSet/detail')
  }
]
