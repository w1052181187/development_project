import subOtherProspect from '@/pages/mineral-rights/transferor/other-mining-sell/sub-other-prospect/index'
import subOtherProspectAdd from '@/pages/mineral-rights/transferor/other-mining-sell/sub-other-prospect/add'
import subOtherProspectUpdate from '@/pages/mineral-rights/transferor/other-mining-sell/sub-other-prospect/update'
import subOtherProspectDetail from '@/pages/mineral-rights/transferor/other-mining-sell/sub-other-prospect/detail'
import subOtherProspectRelease from '@/pages/mineral-rights/transferor/other-mining-sell/sub-other-prospect/release'

export default [
  {
    path: '/mr/transferor/other-mining-sell/sub-other-prospect',
    name: 'subOtherProspect-list',
    meta: {
      title: '提交其他方式探矿权出让公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subOtherProspect
  },
  {
    path: '/mr/transferor/other-mining-sell/sub-other-prospect/add',
    name: 'subOtherProspect-add',
    meta: {
      title: '添加公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subOtherProspectAdd
  },
  {
    path: '/mr/transferor/other-mining-sell/sub-other-prospect/update',
    name: 'subOtherProspect-update',
    meta: {
      title: '修改公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subOtherProspectUpdate
  },
  {
    path: '/mr/transferor/other-mining-sell/sub-other-prospect/detail/:objectId',
    name: 'subOtherProspect-detail',
    meta: {
      title: '查看公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subOtherProspectDetail
  },
  {
    path: '/mr/transferor/other-mining-sell/sub-other-prospect/release',
    name: 'subOtherProspect-release',
    meta: {
      title: '发布公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: subOtherProspectRelease
  }
]
