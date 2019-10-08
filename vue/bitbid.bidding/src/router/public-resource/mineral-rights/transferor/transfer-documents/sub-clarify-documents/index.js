/**
 * Created by lixuqiang on 2018/7/26.
 */
import list from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/index'
import selections from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/selection-notice'
import add from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/add'
import update from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/update'
import detail from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/detail'
import release from '@/pages/mineral-rights/transferor/transfer-documents/sub-clarify-documents/release'

export default[
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents',
    name: 'subClarifyDocuments-list',
    meta: {
      title: '提交澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  },
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents/selection-notice',
    name: 'subClarifyDocuments-selection',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: selections
  },
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents/add',
    name: 'subClarifyDocuments-add',
    meta: {
      title: '添加澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: add
  },
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents/update',
    name: 'subClarifyDocuments-update',
    meta: {
      title: '修改澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: update
  },
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents/detail/:objectId',
    name: 'subClarifyDocuments-detail/:objectId',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: detail
  },
  {
    path: '/mr/transferor/transfer-documents/sub-clarify-documents/release',
    name: 'subClarifyDocuments-release',
    meta: {
      title: '发布澄清/修改文件',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: release
  }
]
