import login from '@/components/login/index'
export default[
  {
    path: '/login',
    name: 'login',
    meta: {
      noRequireAuth: true,
      isshow: true
    },
    component: login
  }
]
