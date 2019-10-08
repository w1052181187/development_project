import negotiationBrief from '@/pages/competitive-negotiation/bidder/negotiation-brief'
import uploadResponse from '@/pages/competitive-negotiation/bidder/upload-response'

export default [
  {
    path: '/bidder/negotiation-brief/:num',
    name: 'negotiationBrief-list',
    meta: {
      title: '谈判纪要'
    },
    component: negotiationBrief
  },
  {
    path: '/bidder/upload-response/:num',
    name: 'uploadResponse-list',
    meta: {
      title: '上传响应与澄清承诺'
    },
    component: uploadResponse
  }
]
