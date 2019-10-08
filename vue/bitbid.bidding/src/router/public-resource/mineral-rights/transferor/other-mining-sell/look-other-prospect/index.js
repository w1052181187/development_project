/**
 * Created by lixuqiang on 2018/7/28.
 */
import lookOtherProspect from '@/pages/mineral-rights/transferor/other-mining-sell/look-other-prospect/index'

export default [
  {
    path: '/mr/transferor/other-mining-sell/look-other-prospect',
    name: 'subOtherProspectLook-list',
    meta: {
      title: '查看其他方式探矿权出让公示',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: lookOtherProspect
  }
]
