/**
 * Created by lixuqiang on 2018-11-12.
 */
import startOrFinishBidEva from '@/pages/enquiry-bidding/evaluation-bid-command/start-or-end'
import reportDetail from '@/pages/enquiry-bidding/evaluation-bid-command/report-detail'

export default [
  {
    // command（start:开始、end:结束)  role (agent:代理机构 trial:评标)
    path: '/enquiry-bidding/evaluation-bid-command/start-or-end/:command/:role',
    name: 'startOrFinishBidEva',
    meta: {
      title: '下达命令',
      layout: 'EnquirySystem'
    },
    component: startOrFinishBidEva
  },
  {
    // role (agent:代理机构 trial:评标)
    path: '/enquiry-bidding/evaluation-bid-command/report-detail/:role',
    name: 'reportDetail',
    meta: {
      title: '查看评标报告',
      layout: 'EnquirySystem'
    },
    component: reportDetail
  }
]
