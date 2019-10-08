/**
 * Created by lixuqiang on 2018/8/1.
 */
import lookTransfer from '@/pages/mineral-rights/assignee/look-transfer-project/index'
import lookTransferDetail from '@/pages/mineral-rights/assignee/look-transfer-project/detail'

export default[
  {
    path: '/mr/assignee/look-transfer-project',
    name: 'lookTransfer-list',
    meta: {
      title: '查看出让项目',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookTransfer
  },
  {
    path: '/mr/assignee/look-transfer-project/detail/:objectId',
    name: 'lookTransfer-detail',
    meta: {
      title: '出让项目详情',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookTransferDetail
  }
]
