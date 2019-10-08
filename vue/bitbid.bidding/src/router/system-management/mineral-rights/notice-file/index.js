import noticeindex from '@/pages/system-management/mineral-rights/notice-file/index'
import noticeaudit from '@/pages/system-management/mineral-rights/notice-file/check'
import noticedetail from '@/pages/system-management/mineral-rights/notice-file/detail'

export default[
  {
    path: '/system-management/mineral-rights/notice-file',
    name: 'noticeFile-list',
    meta: {
      title: '出让公告备案',
      layout: 'SystemManagement'
    },
    component: noticeindex
  },
  {
    path: '/system-management/mineral-rights/notice-file/check',
    name: 'noticeFile-check',
    meta: {
      title: '出让公告备案审核',
      layout: 'SystemManagement'
    },
    component: noticeaudit
  },
  {
    path: '/system-management/mineral-rights/notice-file/detail/:objectId',
    name: 'noticeFile-detail',
    meta: {
      title: '出让公告备案详情',
      layout: 'SystemManagement'
    },
    component: noticedetail
  }
]
