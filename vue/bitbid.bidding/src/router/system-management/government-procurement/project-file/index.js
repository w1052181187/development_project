import governmentproindex from '@/pages/system-management/government-procurement/project-file/index'
import governmentprodetail from '@/pages/system-management/government-procurement/project-file/detail'

export default[
  {
    path: '/system-management/government-procurement/project-file',
    name: 'gprojectFile-list',
    meta: {
      title: '项目备案',
      layout: 'SystemManagement'
    },
    component: governmentproindex
  },
  {
    path: '/system-management/government-procurement/project-file/detail/:objectId',
    name: 'gprojectFile-detail',
    meta: {
      title: '项目备案详情',
      layout: 'SystemManagement'
    },
    component: governmentprodetail
  }
]
