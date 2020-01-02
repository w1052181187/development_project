// 投资建议计划审核
export default [
  {
    path: 'investcheck/',
    name: '投资建议计划审核',
    meta: {
      permission: '/planmanage/investcheck',
      title: '投资建议计划审核'
    },
    // component: resolve => require(['@/views/admin/planmanage/tendoffice/investcheck/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/investcheck/index.vue'),
    children: [
      {
        path: 'checkmain',
        name: '投资建议计划审核编辑',
        // component: resolve => require(['@/views/admin/planmanage/tendoffice/investcheck/checkmain.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/planmanage/tendoffice/investcheck/checkmain.vue'),
        meta: {
          title: '投资建议计划审核编辑'
        }
      }
    ]
  }
]
