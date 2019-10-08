import docindex from '@/pages/system-management/mineral-rights/transfer-documents-file/index'
import docdetail from '@/pages/system-management/mineral-rights/transfer-documents-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/transfer-documents-file',
    name: 'documentsFile-list',
    meta: {
      title: '出让文件备案',
      layout: 'SystemManagement'
    },
    component: docindex
  },
  {
    path: '/system-management/mineral-rights/transfer-documents-file/detail/:objectId',
    name: 'documentsFile-detail',
    meta: {
      title: '出让文件备案查看',
      layout: 'SystemManagement'
    },
    component: docdetail
  }
]
