/**
 * 在线审批-我发起的
 */
export default[
  {
    path: '/dailyManage/onlineapprove/initiate',
    name: 'dailymanage-onlineapprov-initiate',
    meta: {
      title: '在线审批-我发起的'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/initiate/index'], resolve)
  },
  {
    path: '/dailyManage/onlineapprove/initiate/detail/:objectId',
    name: 'dailymanage-onlineapprov-initiate-detail',
    meta: {
      title: '在线审批-我发起的-查看'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/initiate/detail'], resolve)
  }
]
