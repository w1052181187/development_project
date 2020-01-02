// 招标过程备案
export default [
  {
    path: 'processrecord/',
    name: '招标过程备案',
    meta: {
      permission: '/record/processrecord',
      title: '招标过程备案'
    },
    // component: resolve => require(['@/views/admin/record/processrecord/index.vue'], resolve),
    component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/processrecord/index.vue'),
    children: [
      {
        path: 'flowchart',
        name: '招标项目名称',
        // component: resolve => require(['@/views/admin/record/processrecord/flowchart.vue'], resolve),
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/processrecord/flowchart.vue'),
        meta: {
          title: '招标项目名称'
        }
      },
      {
        path: 'proregister',
        name: '项目登记',
        meta: {
          permission: '/record/processrecord/operation',
          title: '项目登记'
        },
        // component: resolve => require(['@/views/admin/record/processrecord/proregister.vue'], resolve)
        component: () => import(/* webpackChunkName: 'project' */ '@/views/admin/record/processrecord/proregister.vue')
      }
    ]
  }

]
