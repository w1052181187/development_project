export default [
  {
    path: '/project/projectProcess/exploration/add',
    name: '项目进度-添加踏勘记录',
    meta: {
      title: '项目进度-添加踏勘记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/exploration/add')
  },
  {
    path: '/project/projectProcess/exploration/update',
    name: '项目进度-修改踏勘记录',
    meta: {
      title: '项目进度-修改踏勘记录',
      active: '/project/projectProcess',
      permission: '1010402'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/exploration/update')
  },
  {
    path: '/project/projectProcess/exploration/detail/:objectId',
    name: '项目进度-查看踏勘记录',
    meta: {
      title: '项目进度-查看踏勘记录',
      active: '/project/projectProcess',
      permission: '1010401'
    },
    component: () => import(/* webpackChunkName: 'project' */ '@/pages/project/projectProcess/exploration/detail')
  }
]
