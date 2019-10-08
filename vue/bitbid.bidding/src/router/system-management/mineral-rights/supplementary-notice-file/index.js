import supplementarynoticeindex from '@/pages/system-management/mineral-rights/supplementary-notice-file/index'
import supplementarynoticeaudit from '@/pages/system-management/mineral-rights/supplementary-notice-file/check'
import supplementarynoticedetail from '@/pages/system-management/mineral-rights/supplementary-notice-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/supplementary-notice-file',
    name: 'supplementNoticeFile-list',
    meta: {
      title: '补充公告备案',
      layout: 'SystemManagement'
    },
    component: supplementarynoticeindex
  },
  {
    path: '/system-management/mineral-rights/supplementary-notice-file/check',
    name: 'supplementNoticeFile-check',
    meta: {
      title: '补充公告备案审核',
      layout: 'SystemManagement'
    },
    component: supplementarynoticeaudit
  },
  {
    path: '/system-management/mineral-rights/supplementary-notice-file/detail/:objectId',
    name: 'supplementNoticeFile-detail',
    meta: {
      title: '补充公告备案详情',
      layout: 'SystemManagement'
    },
    component: supplementarynoticedetail
  }
]
