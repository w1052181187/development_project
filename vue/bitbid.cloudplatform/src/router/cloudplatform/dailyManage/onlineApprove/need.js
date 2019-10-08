/**
 * 在线审批-待我审批
 */
export default[
  {
    path: '/dailyManage/onlineapprove/need',
    name: 'dailymanage-onlineapprov-need',
    meta: {
      title: '在线审批-待我审批'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/need/index'], resolve)
  },
  {
    path: '/dailyManage/onlineapprove/need/detail/:objectId',
    name: 'dailymanage-onlineapprov-need-detail',
    meta: {
      title: '在线审批-待我审批-查看'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/need/detail'], resolve)
  },
  {
    path: '/dailyManage/onlineapprove/need/approve',
    name: 'dailymanage-onlineapprov-need-approve',
    meta: {
      title: '在线审批-待我审批-审批'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/need/approve'], resolve)
  }
]
