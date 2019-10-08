import prequalificationfileindex from '@/pages/system-management/government-procurement/pre-qualification-documents-file/index'
import prequalificationfileDetailOrCheck from '@/pages/system-management/government-procurement/pre-qualification-documents-file/detail-or-check'
import checkdownload from '@/pages/system-management/government-procurement/pre-qualification-documents-file/checkdownload'

export default[
  {
    path: '/system-management/government-procurement/pre-qualification-documents-file',
    name: 'preQualificationFile-list',
    meta: {
      title: '资格预审文件备案',
      layout: 'SystemManagement'
    },
    component: prequalificationfileindex
  },
  {
    path: '/system-management/government-procurement/pre-qualification-documents-file/detail-or-check/:type/:objectId',
    name: 'preQualificationFile-check',
    meta: {
      title: '资格预审文件备案',
      layout: 'SystemManagement'
    },
    component: prequalificationfileDetailOrCheck
  },
  {
    path: '/system-management/government-procurement/pre-qualification-documents-file/check-download/:objectId',
    name: 'check-download-list',
    meta: {
      title: '查看下载情况',
      layout: 'SystemManagement'
    },
    component: checkdownload
  }
]
