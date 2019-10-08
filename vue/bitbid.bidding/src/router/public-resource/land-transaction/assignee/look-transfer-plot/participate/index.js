/**
 * Created by lixuqiang on 2018-10-29.
 */
import assParticipateList from '@/pages/land-transaction/assignee/look-transfer-plot/participate/index'
import assParticipateDetail from '@/pages/land-transaction/assignee/look-transfer-plot/participate/detail'
export default[
  {
    path: '/lt/assignee/look-transfer-plot/participate/index',
    name: 'assParticipate-list',
    meta: {
      title: '我参与的',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assParticipateList
  },
  {
    path: '/lt/assignee/look-transfer-plot/participate/detail/:objectId',
    name: 'assParticipate-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: assParticipateDetail
  }
]
