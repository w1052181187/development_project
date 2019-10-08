import bidwinningnoticefile from '@/pages/system-management/government-procurement/bid-winning-notice-file/index'
import bidwinningnoticefiledetail from '@/pages/system-management/government-procurement/bid-winning-notice-file/detail'

export default[
  {
    path: '/system-management/government-procurement/bid-winning-notice-file',
    name: 'bidWinningNoticeFile-list',
    meta: {
      title: '中标通知书备案',
      layout: 'SystemManagement'
    },
    component: bidwinningnoticefile
  },
  {
    path: '/system-management/government-procurement/bid-winning-notice-file/detail/:objectId',
    name: 'bidWinningNoticeFile-detail',
    meta: {
      title: '中标通知书备案详情',
      layout: 'SystemManagement'
    },
    component: bidwinningnoticefiledetail
  }
]
