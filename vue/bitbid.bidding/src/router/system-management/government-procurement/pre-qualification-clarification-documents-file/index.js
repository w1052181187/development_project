import clarificationfileindex from '@/pages/system-management/government-procurement/pre-qualification-clarification-documents-file/index'
import clarificationfileDetailOrCheck from '@/pages/system-management/government-procurement/pre-qualification-clarification-documents-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/pre-qualification-clarification-documents-file',
    name: 'preclarFile-list',
    meta: {
      title: '资格预审澄清/修改文件备案',
      layout: 'SystemManagement'
    },
    component: clarificationfileindex
  },
  {
    path: '/system-management/government-procurement/pre-qualification-clarification-documents-file/detail-or-check/:type/:objectId',
    name: 'preclarFile-check',
    meta: {
      title: '资格预审澄清/修改文件备案',
      layout: 'SystemManagement'
    },
    component: clarificationfileDetailOrCheck
  }
]
