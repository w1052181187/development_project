export default [
  {
    path: '/admin/roles',
    name: '角色管理',
    meta: {
      permission: 'admin',
      title: '角色管理'
    },
    // component: resolve => require(['@/views/admin/organ/role/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/role/index.vue')
  },
  {
    path: '/admin/assignrole',
    name: '分配角色',
    meta: {
      permission: 'admin',
      title: '分配角色'
    },
    // component: resolve => require(['@/views/admin/organ/role/assignrole.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/role/assignrole.vue')
  },
  {
    path: '/role/details',
    name: '角色详情',
    meta: {
      permission: 'admin',
      title: '角色详情'
    },
    // component: resolve => require(['@/views/admin/organ/role/details.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/role/details.vue')
  }
]
