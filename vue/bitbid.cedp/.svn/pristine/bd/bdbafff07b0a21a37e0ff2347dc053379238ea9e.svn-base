export default[
  {
    path: '/login',
    name: 'cedp-login',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '登录'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/login')
  },
  {
    path: '/register',
    name: 'cedp-register',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '注册'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/register')
  },
  {
    path: '/certification',
    name: 'cedp-certification',
    meta: {
      layout: 'isLogin',
      title: '企业认证'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/certification')
  },
  {
    path: '/forgetPwd',
    name: 'cedp-forgetPwd',
    meta: {
      noRequireAuth: true,
      layout: 'isLogin',
      title: '忘记密码'
    },
    component: () => import(/* webpackChunkName: 'userProfile' */ '@/pages/userProfile/forgetPwd')
  }
]
