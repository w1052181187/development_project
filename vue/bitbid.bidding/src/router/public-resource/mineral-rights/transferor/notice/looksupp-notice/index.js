/**
 * Created by lixuqiang on 2018/7/24.
 */
import list from '@/pages/mineral-rights/transferor/notice/look-supp-notice/index'

export default [
  {
    path: '/mr/transferor/submission/look-supplement-notice',
    name: 'lookSupplementNotice-list',
    meta: {
      title: '查看补充公告',
      layout: 'bidding',
      projectType: 'mineralRights' // 判断项目来展示不同侧边
    },
    component: list
  }
]
