import lookSuppAnnounList from '@/pages/land-transaction/transferor/announcement/look-supp-announ/index'
import lookSuppAnnounDetail from '@/pages/land-transaction/transferor/announcement/look-supp-announ/detail'
export default[
  {
    path: '/lt/transferor/announcement/look-supp-announ',
    name: 'lookSuppAnnoun-list',
    meta: {
      title: '查看补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookSuppAnnounList
  },
  {
    path: '/lt/transferor/announcement/look-supp-announ/detail/:objectId',
    name: 'lookSuppAnnoun-detail',
    meta: {
      title: '查看',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: lookSuppAnnounDetail
  }
]
