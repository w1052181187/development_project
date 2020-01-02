// 代理机构考评
export default [
  {
    path: 'agenteval/',
    name: '代理机构考评',
    meta: {
      permission: '/evaluate/agenteval',
      title: '代理机构考评'
    },
    // component: resolve => require(['@/views/admin/evaluate/agenteval/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/index.vue'),
    children: [
      {
        path: 'evalpage/',
        name: '考评',
        meta: {
          permission: '/evaluate/agenteval/operation',
          title: '考评'
        },
        // component: resolve => require(['@/views/admin/evaluate/agenteval/evalpage.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/evalpage.vue')
      }
    ]
  },
  {
    path: 'agentcheck/',
    name: '代理机构考评审核',
    meta: {
      permission: '/evaluate/agentcheck',
      title: '代理机构考评审核'
    },
    // component: resolve => require(['@/views/admin/evaluate/agenteval/agentcheck.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/agentcheck.vue'),
    children: [
      {
        path: 'check',
        name: '审核',
        meta: {
          permission: '/evaluate/agentcheck/approval',
          title: '审核'
        },
        // component: resolve => require(['@/views/admin/evaluate/agenteval/check.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/check.vue')
      }
    ]
  },
  {
    path: 'agentquery/',
    name: '代理机构考评查询',
    meta: {
      permission: '/evaluate/agentquery',
      title: '代理机构考评查询'
    },
    // component: resolve => require(['@/views/admin/evaluate/agenteval/agentquery.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/agentquery.vue'),
    children: [
      {
        path: 'details',
        name: '详情',
        meta: {
          permission: '/evaluate/agentquery/detail',
          title: '详情'
        },
        // component: resolve => require(['@/views/admin/evaluate/agenteval/details.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/evaluate/agenteval/details.vue')
      }
    ]
  }
]
