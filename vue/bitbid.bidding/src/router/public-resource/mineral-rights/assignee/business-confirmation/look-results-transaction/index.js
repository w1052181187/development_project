/**
 * Created by lixuqiang on 2018/7/31.
 */
import lookResultsAss from '@/pages/mineral-rights/assignee/business-confirmation/look-results-transaction/index'
import lookResultsAssDetail from '@/pages/mineral-rights/assignee/business-confirmation/look-results-transaction/detail'

export default [
  {
    path: '/mr/assignee/business-confirmation/look-results-transaction',
    name: 'lookResultsAss-list',
    meta: {
      title: '查看成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookResultsAss
  },
  {
    path: '/mr/assignee/business-confirmation/look-results-transaction/detail/:objectId',
    name: 'lookResultsAss-detail',
    meta: {
      title: '查看成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookResultsAssDetail
  }
]
