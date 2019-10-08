import lookAnnounList from '@/pages/land-transaction/transferor/announcement/look-announ/index'
import lookAnnounDetail from '@/pages/land-transaction/transferor/announcement/look-announ/detail'
export default[
  {
    path: '/lt/transferor/announcement/look-announ',
    name: 'lookAnnoun-list',
    meta: {
      title: '查看出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookAnnounList
  },
  {
    path: '/lt/transferor/announcement/look-announ/detail/:objectId',
    name: 'lookAnnoun-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookAnnounDetail
  }
]
