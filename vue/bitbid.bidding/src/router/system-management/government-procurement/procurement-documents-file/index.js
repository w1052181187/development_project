import procurementfileindex from '@/pages/system-management/government-procurement/procurement-documents-file/index'
import procurementfileDetailOrCheck from '@/pages/system-management/government-procurement/procurement-documents-file/detail-or-check'
import procheckdownload from '@/pages/system-management/government-procurement/procurement-documents-file/checkdownload'

export default[
  {
    path: '/system-management/government-procurement/procurement-documents-file',
    name: 'procureFile-list',
    meta: {
      title: '采购文件备案',
      layout: 'SystemManagement'
    },
    component: procurementfileindex
  },
  {
    path: '/system-management/government-procurement/procurement-documents-file/detail-or-check/:type/:objectId',
    name: 'procureFile-check',
    meta: {
      title: '采购文件备案审批',
      layout: 'SystemManagement'
    },
    component: procurementfileDetailOrCheck
  },
  {
    path: '/system-management/government-procurement/procurement-documents-file/check-download/:biaoDuanId',
    name: 'procurement-check-download-list',
    meta: {
      title: '查看下载情况',
      layout: 'SystemManagement'
    },
    component: procheckdownload
  }
]
