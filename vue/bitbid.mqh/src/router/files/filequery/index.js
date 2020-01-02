// 档案查询
export default [
  {
    path: 'filequery/',
    name: '档案查询',
    meta: {
      permission: '/files/filequery',
      title: '档案查询'
    },
    // component: resolve => require(['@/views/admin/files/filequery/index.vue'], resolve)
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/files/filequery/index.vue')
  }
]
