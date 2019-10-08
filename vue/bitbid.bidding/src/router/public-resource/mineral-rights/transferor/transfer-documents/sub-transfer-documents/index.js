/**
 * Created by lixuqiang on 2018/7/26.
 */
import list from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/index'
import selections from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/selection-notice'
import add from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/add'
import update from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/update'
import detail from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/detail'
import release from '@/pages/mineral-rights/transferor/transfer-documents/sub-transfer-documents/release'

export default[
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents',
    name: 'subTransferDocuments-list',
    meta: {
      title: '提交出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  },
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents/selection-notice',
    name: 'subTransferDocuments-selection',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: selections
  },
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents/add',
    name: 'subTransferDocuments-add',
    meta: {
      title: '添加出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: add
  },
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents/update',
    name: 'subTransferDocuments-update',
    meta: {
      title: '修改出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: update
  },
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents/detail/:objectId',
    name: 'subTransferDocuments-detail/:objectId',
    meta: {
      title: '查看出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  },
  {
    path: '/mr/transferor/transfer-documents/sub-transfer-documents/release',
    name: 'subTransferDocuments-release',
    meta: {
      title: '发布出让文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: release
  }
]
