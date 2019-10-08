import subAucListedList from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/index'
import subAucselectionAnnoun from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/selection-announ'
import subAucListedAdd from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/add'
import subAucListedUpdate from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/update'
import subAucListedDetail from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/detail'
import subAucListedRelease from '@/pages/land-transaction/transferor/auction-listed-file/sub-auc-listed/release'

export default [
  {
    path: '/lt/transferor/auction-listed-file/sub-auc-listed',
    name: 'subAucListed-list',
    meta: {
      title: '提交出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucListedList
  },
  {
    path: '/lt/transferor/auction-listed-file/selection-announ',
    name: 'selectionAnnoun-list',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucselectionAnnoun
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-auc-listed/add',
    name: 'subAucListed-add',
    meta: {
      title: '添加出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucListedAdd
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-auc-listed/update',
    name: 'subAucListed-update',
    meta: {
      title: '修改出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucListedUpdate
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-auc-listed/detail/:objectId',
    name: 'subAucListed-detail',
    meta: {
      title: '查看出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucListedDetail
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-auc-listed/release/:objectId',
    name: 'subAucListed-release',
    meta: {
      title: '发布出让文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subAucListedRelease
  }
]
