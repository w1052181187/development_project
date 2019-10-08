export default [
  {
    path: '/register',
    name: 'register',
    meta: {
      title: '注册',
      noRequireAuth: true
    },
    component: resolve => require(['@/pages/register/index'], resolve)
  }
]
