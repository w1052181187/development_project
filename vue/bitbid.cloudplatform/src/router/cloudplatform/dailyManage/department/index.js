export default[
  {
    path: '/dailyManage/department',
    name: 'cloud-department',
    meta: {
      title: '部门管理'
    },
    component: resolve => require(['@/pages/dailyManage/department/index'], resolve)
  }
]
