import consultationBrief from '@/pages/competitive-consultation/bidder/consultation-brief'
import uploadResponse from '@/pages/competitive-consultation/bidder/upload-response'

export default [
  {
    path: '/consultation-bidder/consultation-brief/:num',
    name: 'consultationBrief-list',
    meta: {
      title: '磋商纪要'
    },
    component: consultationBrief
  },
  {
    path: '/consultation-bidder/upload-response/:num',
    name: 'consultation-uploadResponse-list',
    meta: {
      title: '上传响应与澄清承诺'
    },
    component: uploadResponse
  }
]
