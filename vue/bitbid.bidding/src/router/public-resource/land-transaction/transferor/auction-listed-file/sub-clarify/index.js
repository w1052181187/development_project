import subClarifyList from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/index'
import subClarifyselectionAnnoun from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/selection-announ'
import subClarifyAdd from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/add'
import subClarifyUpdate from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/update'
import subClarifyDetail from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/detail'
import subClarifyRelease from '@/pages/land-transaction/transferor/auction-listed-file/sub-clarify/release'

export default [
  {
    path: '/lt/transferor/auction-listed-file/sub-clarify',
    name: 'subClarify-list',
    meta: {
      title: '提交澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyList
  },
  {
    path: '/lt/transferor/auction-listed-file/clarify-announ',
    name: 'clarifyAnnoun-list',
    meta: {
      title: '选择出让公告',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyselectionAnnoun
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-clarify/add',
    name: 'subClarify-add',
    meta: {
      title: '添加澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyAdd
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-clarify/update',
    name: 'subClarify-update',
    meta: {
      title: '修改澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyUpdate
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-clarify/detail/:objectId',
    name: 'subClarify-detail',
    meta: {
      title: '查看澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyDetail
  },
  {
    path: '/lt/transferor/auction-listed-file/sub-clarify/release/:objectId',
    name: 'subClarify-release',
    meta: {
      title: '发布澄清/修改文件',
      layout: 'bidding',
      projectType: 'landTransaction' // 判断项目来展示不同侧边
    },
    component: subClarifyRelease
  }
]
