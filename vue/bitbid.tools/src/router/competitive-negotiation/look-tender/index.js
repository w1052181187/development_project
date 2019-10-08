import biddingFile from '@/pages/competitive-negotiation/look-tender/bidding-file'
import tenderFile from '@/pages/competitive-negotiation/look-tender/tender-file'

export default [
  {
    path: '/look-tender/bidding-file',
    name: 'lookTender-biddingFile',
    meta: {
      title: '招标文件',
      isShowLeft: true
    },
    component: biddingFile
  },
  {
    path: '/look-tender/tender-file/:fileId',
    name: 'lookTender-tenderFile',
    meta: {
      title: '投标文件',
      isShowLeft: true
    },
    component: tenderFile
  }
]
