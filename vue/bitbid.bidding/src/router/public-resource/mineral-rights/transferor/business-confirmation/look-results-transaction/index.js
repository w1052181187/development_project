/**
 * Created by lixuqiang on 2018/7/31.
 */
import lookResultsTran from '@/pages/mineral-rights/transferor/business-confirmation/look-results-transaction/index'
import lookResultsTranDetail from '@/pages/mineral-rights/transferor/business-confirmation/look-results-transaction/detail'

export default [
  {
    path: '/mr/transferor/business-confirmation/look-results-transaction',
    name: 'lookResultsTran-list',
    meta: {
      title: '查看成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookResultsTran
  },
  {
    path: '/mr/transferor/business-confirmation/look-results-transaction/detail/:objectId',
    name: 'lookResultsTran-detail',
    meta: {
      title: '查看成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookResultsTranDetail
  }
]
