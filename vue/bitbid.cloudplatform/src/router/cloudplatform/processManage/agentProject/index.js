export default[
  {
    path: '/processManage/agentProject',
    name: 'cloud-agentProject',
    meta: {
      title: '代理分配项目'
    },
    component: resolve => require(['@/pages/processManage/agentProject/index'], resolve)
  },
  {
    path: '/processManage/agentProject/agent',
    name: 'cloud-agentProject-agent',
    meta: {
      title: '分配项目负责人'
    },
    component: resolve => require(['@/pages/processManage/agentProject/agent'], resolve)
  },
  {
    path: '/processManage/agentProject/detail/:objectId',
    name: 'cloud-agentProject-detail',
    meta: {
      title: '代理分配项目详情'
    },
    component: resolve => require(['@/pages/processManage/agentProject/detail'], resolve)
  }
]
