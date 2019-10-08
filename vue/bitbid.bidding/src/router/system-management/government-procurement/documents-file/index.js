import documentfileindex from '@/pages/system-management/government-procurement/documents-file/index'
import documentfiledetail from '@/pages/system-management/government-procurement/documents-file/detail'

export default[
  {
    path: '/system-management/government-procurement/documents-file',
    name: 'documentFile-list',
    meta: {
      title: '文件备案',
      layout: 'SystemManagement'
    },
    component: documentfileindex
  },
  {
    path: '/system-management/government-procurement/documents-file/detail/:objectId',
    name: 'documentFile-detail',
    meta: {
      title: '文件备案详情',
      layout: 'SystemManagement'
    },
    component: documentfiledetail
  }
]
