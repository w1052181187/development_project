/**
 * Created by lixuqiang on 2018/7/31.
 */
import subResultsTrans from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/index'
import selectPublicity from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/select-publicity'
import subResultsTransAdd from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/add'
import subResultsTransUpdate from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/update'
import subResultsTransDetail from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/detail'
import subResultsTransRelease from '@/pages/mineral-rights/transferor/business-confirmation/sub-results-transaction/release'

export default [
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction',
    name: 'subResultsTransaction-list',
    meta: {
      title: '提交成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subResultsTrans
  },
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction/select-publicity',
    name: 'subResultsTransaction-select',
    meta: {
      title: '选择公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: selectPublicity
  },
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction/add',
    name: 'subResultsTransaction-add',
    meta: {
      title: '添加成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subResultsTransAdd
  },
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction/update',
    name: 'subResultsTransaction-update',
    meta: {
      title: '修改成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subResultsTransUpdate
  },
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction/detail/:objectId',
    name: 'subResultsTransaction-detail',
    meta: {
      title: '查看成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subResultsTransDetail
  },
  {
    path: '/mr/transferor/business-confirmation/sub-results-transaction/release',
    name: 'subResultsTransaction-release',
    meta: {
      title: '发布成交结果公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subResultsTransRelease
  }
]
