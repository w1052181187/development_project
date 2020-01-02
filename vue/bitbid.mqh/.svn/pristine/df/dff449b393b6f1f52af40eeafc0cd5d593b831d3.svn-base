// 档案管理
export default [
  {
    path: 'filemanage/',
    name: '档案管理',
    meta: {
      permission: '/files/filemanage',
      title: '档案管理'
    },
    // component: resolve => require(['@/views/admin/files/filemanage/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/files/filemanage/index.vue'),
    children: [
      {
        path: 'editfile',
        name: '编辑档案',
        meta: {
          permission: '/files/filemanage/operation',
          title: '编辑档案'
        },
        // component: resolve => require(['@/views/admin/files/filemanage/editfile.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/files/filemanage/editfile.vue')
      }
    ]
  }
]
