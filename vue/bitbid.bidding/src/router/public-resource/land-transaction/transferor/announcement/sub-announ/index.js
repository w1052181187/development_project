import subAnnounList from '@/pages/land-transaction/transferor/announcement/sub-announ/index'
import selectionProject from '@/pages/land-transaction/transferor/announcement/sub-announ/selection-project'
import subAnnounAdd from '@/pages/land-transaction/transferor/announcement/sub-announ/add'
import subAnnounUpdate from '@/pages/land-transaction/transferor/announcement/sub-announ/update'
import subAnnounDetail from '@/pages/land-transaction/transferor/announcement/sub-announ/detail'
import subAnnounRelease from '@/pages/land-transaction/transferor/announcement/sub-announ/release'
export default[
  {
    path: '/lt/transferor/announcement/sub-announ',
    name: 'subAnnoun-list',
    meta: {
      title: '提交出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAnnounList
  },
  {
    path: '/lt/transferor/announcement/sub-announ/selection-project',
    name: 'selectionProject-list',
    meta: {
      title: '选择项目',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: selectionProject
  },
  {
    path: '/lt/transferor/announcement/sub-announ/add',
    name: 'selectionProject-add',
    meta: {
      title: '添加出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAnnounAdd
  },
  {
    path: '/lt/transferor/announcement/sub-announ/update',
    name: 'selectionProject-update',
    meta: {
      title: '修改出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAnnounUpdate
  },
  {
    path: '/lt/transferor/announcement/sub-announ/detail/:objectId',
    name: 'selectionProject-detail',
    meta: {
      title: '查看出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAnnounDetail
  },
  {
    path: '/lt/transferor/announcement/sub-announ/release/:objectId',
    name: 'selectionProject-release',
    meta: {
      title: '发布出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAnnounRelease
  }
]
