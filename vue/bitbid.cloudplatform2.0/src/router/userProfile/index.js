export default[
  {
    path: '/login',
    name: 'cloud-login',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '登录'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/login')
  },
  {
    path: '/register',
    name: 'cloud-register',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '注册'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/register')
  },
  {
    path: '/forgetPwd',
    name: 'cloud-forgetPwd',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '忘记密码'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/forgetPwd')
  }
]
