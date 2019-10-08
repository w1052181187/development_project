import clatifydocindex from '@/pages/system-management/mineral-rights/clarify-documents-file/index'
import clatifydocdetail from '@/pages/system-management/mineral-rights/clarify-documents-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/clarify-documents-file',
    name: 'clatifyDocumentsFile-list',
    meta: {
      title: '澄清修改文件备案',
      layout: 'SystemManagement'
    },
    component: clatifydocindex
  },
  {
    path: '/system-management/mineral-rights/clarify-documents-file/detail/:objectId',
    name: 'clatifyDocumentsFile-detail',
    meta: {
      title: '澄清修改文件备案查看',
      layout: 'SystemManagement'
    },
    component: clatifydocdetail
  }
]
