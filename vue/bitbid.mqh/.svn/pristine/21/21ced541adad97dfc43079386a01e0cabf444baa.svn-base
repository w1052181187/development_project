export default [
  {
    path: '/admin/users',
    name: '用户管理',
    meta: {
      permission: 'admin',
      title: '用户管理'
    },
    // component: resolve => require(['@/views/admin/organ/user/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/user/index.vue')
  },
  {
    path: '/admin/add-user',
    name: '新增用户',
    meta: {
      permission: 'admin',
      title: '新增用户'
    },
    // component: resolve => require(['@/views/admin/organ/user/addUser.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/user/addUser.vue')
  },
  {
    path: '/admin/details',
    name: '用户详情',
    meta: {
      permission: 'admin',
      title: '用户详情'
    },
    // component: resolve => require(['@/views/admin/organ/user/details.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/organ/user/details.vue')
  }
]
