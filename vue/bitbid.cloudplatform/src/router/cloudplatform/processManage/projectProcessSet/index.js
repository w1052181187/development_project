export default [
  {
    path: '/processManage/proProcessSet',
    name: 'proProcessSet-list',
    meta: {
      title: '项目流程配置管理'
    },
    component: resolve => require(['@/pages/processManage/projectProcessSet/index'], resolve)
  },
  {
    path: '/processManage/proProcessSet/update',
    name: 'proProcessSet-update',
    meta: {
      title: '项目流程配置-修改'
    },
    component: resolve => require(['@/pages/processManage/projectProcessSet/update'], resolve)
  },
  {
    path: '/processManage/proProcessSet/detail/:objectId',
    name: 'proProcessSet-detail',
    meta: {
      title: '项目流程配置-详情'
    },
    component: resolve => require(['@/pages/processManage/projectProcessSet/detail'], resolve)
  }
]
