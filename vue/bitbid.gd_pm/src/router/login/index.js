export default [
  {
    path: '/login',
    name: 'login',
    meta: {
      title: '登录',
      noRequireAuth: true
    },
    component: resolve => require(['@/pages/login/index'], resolve)
  }
]
