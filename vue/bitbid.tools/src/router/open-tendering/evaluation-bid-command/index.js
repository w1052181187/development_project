/**
 * Created by lixuqiang on 2018-11-12.
 */
import startOrFinishBidEva from '@/pages/open-tendering/evaluation-bid-command/start-or-end'
import reportDetail from '@/pages/open-tendering/evaluation-bid-command/report-detail'

export default [
  {
    // command（start:开始、end:结束)  role (agent:代理机构 trial:评标)
    path: '/open-tendering/evaluation-bid-command/start-or-end/:command/:role',
    name: 'openTendering-startOrFinishBidEva',
    meta: {
      title: '下达命令',
      layout: 'EnquirySystem'
    },
    component: startOrFinishBidEva
  },
  {
    path: '/open-tendering/evaluation-bid-command/report-detail/:role',
    name: 'openTendering-reportDetail',
    meta: {
      title: '查看评标报告',
      layout: 'EnquirySystem'
    },
    component: reportDetail
  }
]
