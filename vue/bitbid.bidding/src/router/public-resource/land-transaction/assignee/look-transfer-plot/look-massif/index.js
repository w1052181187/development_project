import assLookMassifList from '@/pages/land-transaction/assignee/look-transfer-plot/look-massif/index'
import assLookMassifDetail from '@/pages/land-transaction/assignee/look-transfer-plot/look-massif/detail'
export default[
  {
    path: '/lt/assignee/look-transfer-plot/look-massif/index',
    name: 'assLookMassif-list',
    meta: {
      title: '查看出让地块',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assLookMassifList
  },
  {
    path: '/lt/assignee/look-transfer-plot/look-massif/detail/:objectId',
    name: 'assLookMassif-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assLookMassifDetail
  }
]
