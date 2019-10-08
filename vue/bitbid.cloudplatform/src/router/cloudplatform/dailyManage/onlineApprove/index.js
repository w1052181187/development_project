import need from '../onlineApprove/need'
import initiate from '../onlineApprove/initiate'
import config from '../onlineApprove/config'

/**
 * 在线审批
 */
export default[
  {
    path: '/dailyManage/onlineapprove',
    name: 'dailymanage-onlineapprove',
    meta: {
      title: '在线审批'
    },
    component: resolve => require(['@/pages/dailyManage/onlineApprove/index'], resolve)
  },
  ...need,
  ...initiate,
  ...config
]
