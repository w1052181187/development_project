import proindex from '@/pages/system-management/mineral-rights/project-file/index'
import proaudit from '@/pages/system-management/mineral-rights/project-file/check'
import prodetail from '@/pages/system-management/mineral-rights/project-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/project-file',
    name: 'projectFile-list',
    meta: {
      title: '项目备案',
      layout: 'SystemManagement'
    },
    component: proindex
  },
  {
    path: '/system-management/mineral-rights/project-file/check',
    name: 'projectFile-check',
    meta: {
      title: '项目备案审核',
      layout: 'SystemManagement'
    },
    component: proaudit
  },
  {
    path: '/system-management/mineral-rights/project-file/detail/:objectId',
    name: 'projectFile-detail',
    meta: {
      title: '项目备案详情',
      layout: 'SystemManagement'
    },
    component: prodetail
  }
]
