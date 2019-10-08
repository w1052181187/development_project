import bidopeningrecordfile from '@/pages/system-management/government-procurement/bid-opening-record-file/index'
import bidopeningrecordfiledetail from '@/pages/system-management/government-procurement/bid-opening-record-file/detail'
import checkprogress from '@/pages/system-management/government-procurement/bid-opening-record-file/checkprogress'

export default[
  {
    path: '/system-management/government-procurement/bid-opening-record-file',
    name: 'bidOpeningRecordFile-list',
    meta: {
      title: '开标记录表备案',
      layout: 'SystemManagement'
    },
    component: bidopeningrecordfile
  },
  {
    path: '/system-management/government-procurement/bid-opening-record-file/detail/:objectId',
    name: 'bidOpeningRecordFile-detail',
    meta: {
      title: '开标记录表备案详情',
      layout: 'SystemManagement'
    },
    component: bidopeningrecordfiledetail
  },
  {
    path: '/system-management/government-procurement/bid-opening-record-file/check-progress/:objectId/:biaoDuanId',
    name: 'checkProgress-list',
    meta: {
      title: '确认进度查看',
      layout: 'SystemManagement'
    },
    component: checkprogress
  }
]
