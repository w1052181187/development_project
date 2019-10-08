import suppAnnounList from '@/pages/land-transaction/transferor/announcement/supp-announ/index'
import suppAnnounSelectionAnnoun from '@/pages/land-transaction/transferor/announcement/supp-announ/selection-announ'
import suppAnnounAdd from '@/pages/land-transaction/transferor/announcement/supp-announ/add'
import suppAnnounUpdate from '@/pages/land-transaction/transferor/announcement/supp-announ/update'
import suppAnnounDetail from '@/pages/land-transaction/transferor/announcement/supp-announ/detail'
import suppAnnounRelease from '@/pages/land-transaction/transferor/announcement/supp-announ/release'
export default[
  {
    path: '/lt/transferor/announcement/supp-announ',
    name: 'suppAnnoun-list',
    meta: {
      title: '提交补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounList
  },
  {
    path: '/lt/transferor/announcement/supp-announ/selection-announ',
    name: 'suppAnnoun-selectionAnnoun',
    meta: {
      title: '选择公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounSelectionAnnoun
  },
  {
    path: '/lt/transferor/announcement/supp-announ/add',
    name: 'suppAnnoun-add',
    meta: {
      title: '添加补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounAdd
  },
  {
    path: '/lt/transferor/announcement/supp-announ/update',
    name: 'suppAnnoun-update',
    meta: {
      title: '修改补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounUpdate
  },
  {
    path: '/lt/transferor/announcement/supp-announ/detail/:objectId',
    name: 'suppAnnoun-detail',
    meta: {
      title: '查看补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounDetail
  },
  {
    path: '/lt/transferor/announcement/supp-announ/release/:objectId',
    name: 'suppAnnoun-release',
    meta: {
      title: '发布补充公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: suppAnnounRelease
  }
]
