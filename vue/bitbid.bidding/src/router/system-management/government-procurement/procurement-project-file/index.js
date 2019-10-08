import procurementproject from '@/pages/system-management/government-procurement/procurement-project-file/index'
import procurementProjectDetailOrCheck from '@/pages/system-management/government-procurement/procurement-project-file/detail-or-check'
import packagegrouplist from '@/pages/system-management/government-procurement/procurement-project-file/package-group-list'
import packagegroupdetail from '@/pages/system-management/government-procurement/procurement-project-file/package-group-detail'

export default[
  {
    path: '/system-management/government-procurement/procurement-project-file',
    name: 'procurementProFile-list',
    meta: {
      title: '采购项目备案',
      layout: 'SystemManagement'
    },
    component: procurementproject
  },
  {
    path: '/system-management/government-procurement/procurement-project-file/detail-or-check/:type/:objectId',
    name: 'procurementProFile-check',
    meta: {
      title: '采购项目备案',
      layout: 'SystemManagement'
    },
    component: procurementProjectDetailOrCheck
  },
  {
    path: '/system-management/government-procurement/procurement-project-file/package-group',
    name: 'package-group-list',
    meta: {
      title: '包组信息列表',
      layout: 'SystemManagement'
    },
    component: packagegrouplist
  },
  {
    path: '/system-management/government-procurement/procurement-project-file/package-group/detail/:objectId',
    name: 'package-group-detail',
    meta: {
      title: '包组信息详情',
      layout: 'SystemManagement'
    },
    component: packagegroupdetail
  }
]
