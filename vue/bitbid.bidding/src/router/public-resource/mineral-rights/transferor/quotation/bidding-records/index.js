import records from '@/pages/mineral-rights/transferor/quotation/bidding-records/index'
import recordsdetail from '@/pages/mineral-rights/transferor/quotation/bidding-records/detail'
export default[
  {
    path: '/mr/transferor/quotation/bidding-records',
    name: 'bidRecords-list',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: records
  },
  {
    path: '/mr/transferor/quotation/bidding-records/detail',
    name: 'bidRecords-detail',
    meta: {
      title: '查看竞价记录',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: recordsdetail
  }
]
