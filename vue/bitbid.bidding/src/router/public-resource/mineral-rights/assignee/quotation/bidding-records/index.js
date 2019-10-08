import records from '@/pages/mineral-rights/assignee/quotation/bidding-records/index'
import recordsdetail from '@/pages/mineral-rights/assignee/quotation/bidding-records/detail'
export default[
  {
    path: '/mr/assignee/quotation/bidding-records',
    name: 'assigneeBidRecords-list',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: records
  },
  {
    path: '/mr/assignee/quotation/bidding-records/detail',
    name: 'assigneeBidRecords-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: recordsdetail
  }
]
