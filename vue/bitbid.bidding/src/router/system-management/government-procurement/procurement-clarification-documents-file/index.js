import procurementclarifyfileindex from '@/pages/system-management/government-procurement/procurement-clarification-documents-file/index'
import procurementclarifyfileDetailOrCheck from '@/pages/system-management/government-procurement/procurement-clarification-documents-file/detail-or-check'

export default[
  {
    path: '/system-management/government-procurement/procurement-clarification-documents-file',
    name: 'procurementFile-list',
    meta: {
      title: '采购澄清修改文件备案备案',
      layout: 'SystemManagement'
    },
    component: procurementclarifyfileindex
  },
  {
    path: '/system-management/government-procurement/procurement-clarification-documents-file/detail-or-check/:type/:objectId',
    name: 'procurementFile-check',
    meta: {
      title: '采购澄清修改文件备案',
      layout: 'SystemManagement'
    },
    component: procurementclarifyfileDetailOrCheck
  }
]
