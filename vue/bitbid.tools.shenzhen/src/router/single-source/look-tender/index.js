import biddingFile from '@/pages/single-source/look-tender/bidding-file'
import tenderFile from '@/pages/single-source/look-tender/tender-file'

export default [
  {
    path: '/single-source/look-tender/bidding-file',
    name: 'single-source-lookTender-biddingFile',
    meta: {
      title: '招标文件',
      isShowLeft: true
    },
    component: biddingFile
  },
  {
    path: '/single-source/look-tender/tender-file/:fileId',
    name: 'single-source-lookTender-tenderFile',
    meta: {
      title: '投标文件',
      isShowLeft: true
    },
    component: tenderFile
  }
]
